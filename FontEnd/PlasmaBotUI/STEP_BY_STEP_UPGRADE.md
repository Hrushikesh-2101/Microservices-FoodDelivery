# Step-by-Step Angular Upgrade Guide: 11 → 19

## Overview
This guide will help you upgrade Angular from version 11 to 19 in 8 incremental steps.

## Upgrade Path
1. ✅ Angular 11.2.14 (Current)
2. ⏳ Angular 12.x
3. ⏳ Angular 13.x
4. ⏳ Angular 14.x
5. ⏳ Angular 15.x
6. ⏳ Angular 16.x
7. ⏳ Angular 17.x
8. ⏳ Angular 18.x
9. ⏳ Angular 19.x (Target)

---

## Step 1: Angular 11 → 12

### Key Changes:
- TypeScript 4.2+ required
- Webpack 5 support
- Stricter type checking
- Ivy compiler improvements

### Breaking Changes:
- `@angular-devkit/build-angular` updated
- Some deprecated APIs removed

---

## Step 2: Angular 12 → 13

### Key Changes:
- TypeScript 4.4+ required
- Angular Package Format (APF) changes
- View Engine removed (Ivy only)
- Dynamic component creation API changes

### Breaking Changes:
- `ViewChild` and `ContentChild` behavior changes
- `@angular/forms` API updates

---

## Step 3: Angular 13 → 14

### Key Changes:
- Standalone components introduced
- Angular CLI autocomplete
- Optional injectors
- TypeScript 4.6+ required

### Breaking Changes:
- `@angular/forms` strict typing
- Router changes

---

## Step 4: Angular 14 → 15

### Key Changes:
- Standalone APIs stable
- MDC-based Angular Material components
- TypeScript 4.8+ required
- Image directive

### Breaking Changes:
- Angular Material component API changes
- Router guard changes

---

## Step 5: Angular 15 → 16

### Key Changes:
- Required inputs
- Standalone ng new projects
- Signals (experimental)
- TypeScript 4.9+ required

### Breaking Changes:
- `@angular/material` MDC migration
- Router API updates

---

## Step 6: Angular 16 → 17

### Key Changes:
- Signals stable
- New control flow syntax (@if, @for, @switch)
- Standalone by default
- TypeScript 5.2+ required

### Breaking Changes:
- Control flow syntax changes
- Router updates

---

## Step 7: Angular 17 → 18

### Key Changes:
- Material 3 support
- Zoneless change detection (experimental)
- TypeScript 5.4+ required

### Breaking Changes:
- Material component updates
- Router improvements

---

## Step 8: Angular 18 → 19

### Key Changes:
- Full Node.js 22 support
- Material 3 stable
- Improved performance
- TypeScript 5.4+ required

### Breaking Changes:
- Final cleanup of deprecated APIs
- Build optimizations

---

## Before Starting

1. **Commit your current code to git**
2. **Create a backup branch**: `git checkout -b backup-angular-11`
3. **Ensure you have the latest npm**: `npm install -g npm@latest`

---

## How to Use This Guide

Each step includes:
1. Package.json updates
2. Command to run
3. Code changes needed
4. Testing checklist

**Follow each step completely before moving to the next!**
