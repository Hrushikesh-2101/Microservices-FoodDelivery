# PowerShell script to start Angular dev server with OpenSSL legacy provider
# This ensures compatibility with Node.js 17+ and Angular 11/webpack 4

# Set environment variable for this session
$env:NODE_OPTIONS = "--openssl-legacy-provider"

# Load .env file if exists
$envFile = Join-Path $PSScriptRoot ".env"
if (Test-Path $envFile) {
    Get-Content $envFile | ForEach-Object {
        if ($_ -match "^([^=]+)=(.*)$") {
            [System.Environment]::SetEnvironmentVariable($matches[1], $matches[2], "Process")
        }
    }
}

Write-Host "Starting Angular dev server with OpenSSL legacy provider..." -ForegroundColor Green
Write-Host "NODE_OPTIONS is set to: $env:NODE_OPTIONS" -ForegroundColor Yellow
ng serve

