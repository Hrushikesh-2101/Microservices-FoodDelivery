#!/usr/bin/env python3
import os
import json
import re
from typing import Optional

import click
from notion_client import Client
from dotenv import load_dotenv

# Very small helper for rich_text arrays

def rich_text_to_plain(rich_text):
	return ''.join([t.get('plain_text', '') for t in (rich_text or [])])


def sanitize_filename(name: str) -> str:
	name = name.strip().replace(' ', '_')
	return re.sub(r'[^A-Za-z0-9._-]', '', name) or 'notion_export'


@click.group()
def cli():
	"""Notion export CLI: fetch page content as JSON/Markdown."""


@cli.command()
@click.option('--page-id', required=False, help='Notion page ID (UUID or URL).')
@click.option('--out-dir', required=False, default='out/notion', help='Output directory.')
@click.option('--format', 'fmt', type=click.Choice(['json', 'md'], case_sensitive=False), default='md')
@click.option('--token', required=False, help='Notion integration token (overrides env).')
@click.option('--verbose', is_flag=True, default=False)
def export(page_id: Optional[str], out_dir: str, fmt: str, token: Optional[str], verbose: bool):
	"""Export a Notion page to JSON or Markdown."""
	load_dotenv()
	notion_token = token or os.getenv('NOTION_TOKEN')
	if not notion_token:
		raise click.ClickException('NOTION_TOKEN is required (set in env or pass --token).')

	client = Client(auth=notion_token)

	# Resolve page id: accept full URL or id
	resolved_page_id = resolve_page_id(page_id or os.getenv('NOTION_DEFAULT_PAGE_ID'))
	if not resolved_page_id:
		raise click.ClickException('Provide --page-id or set NOTION_DEFAULT_PAGE_ID.')

	if verbose:
		click.echo(f'Fetching page {resolved_page_id}...')

	page = client.pages.retrieve(page_id=resolved_page_id)
	db_title = extract_page_title(page)
	os.makedirs(out_dir, exist_ok=True)
	base = sanitize_filename(db_title or resolved_page_id)

	if fmt.lower() == 'json':
		out_path = os.path.join(out_dir, f'{base}.json')
		with open(out_path, 'w', encoding='utf-8') as f:
			json.dump(page, f, ensure_ascii=False, indent=2)
		if verbose:
			click.echo(f'Wrote {out_path}')
		return

	# Markdown export: best-effort using the page properties and child blocks
	blocks = fetch_all_blocks(client, resolved_page_id)
	markdown = render_blocks_to_markdown(blocks)
	# Prepend title
	title = f'# {db_title}\n\n' if db_title else ''
	content = title + markdown

	out_path = os.path.join(out_dir, f'{base}.md')
	with open(out_path, 'w', encoding='utf-8') as f:
		f.write(content)
	if verbose:
		click.echo(f'Wrote {out_path}')


def resolve_page_id(page_id_or_url: Optional[str]) -> Optional[str]:
	if not page_id_or_url:
		return None
	# Extract UUID-ish from URL if provided
	m = re.search(r'([0-9a-fA-F]{32})', page_id_or_url.replace('-', ''))
	if m:
		u = m.group(1)
		# Reformat with hyphens 8-4-4-4-12
		return f"{u[0:8]}-{u[8:12]}-{u[12:16]}-{u[16:20]}-{u[20:32]}"
	# If already a UUID with hyphens
	if re.match(r'^[0-9a-fA-F]{8}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{4}-[0-9a-fA-F]{12}$', page_id_or_url):
		return page_id_or_url
	return None


def extract_page_title(page: dict) -> Optional[str]:
	props = page.get('properties', {})
	for prop in props.values():
		if prop.get('type') == 'title':
			return rich_text_to_plain(prop.get('title'))
	return page.get('id')


def fetch_all_blocks(client: Client, block_id: str):
	blocks = []
	cursor = None
	while True:
		resp = client.blocks.children.list(block_id=block_id, start_cursor=cursor)
		results = resp.get('results', [])
		blocks.extend(results)
		if not resp.get('has_more'):
			break
		cursor = resp.get('next_cursor')
	return blocks


BLOCK_TYPE_TO_MD = {
	'heading_1': lambda b: f"# {rich_text_to_plain(b['heading_1'].get('rich_text'))}\n",
	'heading_2': lambda b: f"## {rich_text_to_plain(b['heading_2'].get('rich_text'))}\n",
	'heading_3': lambda b: f"### {rich_text_to_plain(b['heading_3'].get('rich_text'))}\n",
	'paragraph': lambda b: f"{rich_text_to_plain(b['paragraph'].get('rich_text'))}\n",
}


def render_block_to_markdown(block: dict) -> str:
	type_ = block.get('type')
	if type_ in BLOCK_TYPE_TO_MD:
		return BLOCK_TYPE_TO_MD[type_](block)
	# Fallback: stringify rich_text if present
	obj = block.get(type_, {})
	if isinstance(obj, dict) and 'rich_text' in obj:
		return f"{rich_text_to_plain(obj['rich_text'])}\n"
	return ''


def render_blocks_to_markdown(blocks):
	return '\n'.join(render_block_to_markdown(b) for b in blocks)


if __name__ == '__main__':
	cli()