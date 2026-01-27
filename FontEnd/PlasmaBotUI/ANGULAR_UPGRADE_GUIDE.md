# Angular Upgrade Guide

## Current System Versions
- **Node.js**: v22.14.0 ✅
- **npm**: 10.9.2 ✅
- **Angular CLI (Global)**: 10.2.4
- **Project Angular**: 11.2.14

## Recommended Upgrade Path

### Option 1: Update to Angular 19 (Recommended for Node.js 22)
**Why**: Angular 19+ officially supports Node.js 22, eliminating the need for `--openssl-legacy-provider` flag.

**Target Versions**:
- Angular: 19.x (latest stable)
- Angular CLI: 19.x
- TypeScript: 5.4+
- Node.js: 22.14.0 ✅ (already installed)

### Option 2: Update to Angular 20/21 (Latest)
**Why**: Latest features and best Node.js 22 support.

**Target Versions**:
- Angular: 20.x or 21.x
- Angular CLI: 20.x or 21.x
- TypeScript: 5.4+
- Node.js: 22.14.0 ✅ (already installed)

### Option 3: Match Global Angular CLI 10.2.4 (NOT Recommended)
**Why**: This is older and won't work well with Node.js 22. You'll still need the OpenSSL legacy provider.

---

## Step-by-Step Upgrade Process

### Prerequisites
1. **Backup your project** (commit to git)
2. **Update global Angular CLI** to latest version:
   ```powershell
   npm install -g @angular/cli@latest
   ```

### Method 1: Using Angular Update Tool (Recommended)

```powershell
cd FontEnd\PlasmaBotUI

# Update Angular CLI globally first
npm install -g @angular/cli@latest

# Check current version
ng version

# Use Angular's update tool (it will guide you through the upgrade)
ng update @angular/core@19 @angular/cli@19

# Or for Angular 20:
ng update @angular/core@20 @angular/cli@20
```

### Method 2: Manual Update (Step-by-Step)

#### Step 1: Update Angular CLI globally
```powershell
npm uninstall -g @angular/cli
npm cache clean --force
npm install -g @angular/cli@latest
```

#### Step 2: Update package.json dependencies

For **Angular 19**, update your `package.json`:

```json
{
  "dependencies": {
    "@angular/animations": "^19.0.0",
    "@angular/cdk": "^19.0.0",
    "@angular/common": "^19.0.0",
    "@angular/compiler": "^19.0.0",
    "@angular/core": "^19.0.0",
    "@angular/forms": "^19.0.0",
    "@angular/material": "^19.0.0",
    "@angular/platform-browser": "^19.0.0",
    "@angular/platform-browser-dynamic": "^19.0.0",
    "@angular/router": "^19.0.0",
    "rxjs": "^7.8.0",
    "tslib": "^2.3.0",
    "zone.js": "^0.15.0"
  },
  "devDependencies": {
    "@angular-devkit/build-angular": "^19.0.0",
    "@angular/cli": "^19.0.0",
    "@angular/compiler-cli": "^19.0.0",
    "@types/jasmine": "^5.1.0",
    "@types/node": "^20.0.0",
    "jasmine-core": "^5.1.0",
    "karma": "^6.4.0",
    "karma-chrome-launcher": "^3.2.0",
    "karma-coverage": "^2.2.0",
    "karma-jasmine": "^5.1.0",
    "karma-jasmine-html-reporter": "^2.1.0",
    "typescript": "~5.4.0"
  }
}
```

#### Step 3: Remove old dependencies
```powershell
# Delete node_modules and package-lock.json
Remove-Item -Recurse -Force node_modules
Remove-Item -Force package-lock.json

# Clean npm cache
npm cache clean --force
```

#### Step 4: Install new dependencies
```powershell
npm install
```

#### Step 5: Update code for breaking changes
Angular 11 → Angular 19 has many breaking changes. Key areas to check:

1. **TypeScript**: Update to 5.4+
2. **RxJS**: Update to 7.x (major changes)
3. **Standalone Components**: Angular 19 prefers standalone components
4. **Removed APIs**: Check Angular migration guide
5. **Material**: Update to v19
6. **Build Configuration**: Update `angular.json`

#### Step 6: Remove OpenSSL legacy provider (no longer needed!)
After upgrading to Angular 19+, you can remove:
- `NODE_OPTIONS=--openssl-legacy-provider` from scripts
- `.npmrc` file (or remove the node-options line)

---

## Quick Upgrade Script

Create a file `upgrade-angular.ps1`:

```powershell
# PowerShell script to upgrade Angular project
Write-Host "Starting Angular upgrade process..." -ForegroundColor Green

# Step 1: Update global Angular CLI
Write-Host "Updating global Angular CLI..." -ForegroundColor Yellow
npm install -g @angular/cli@latest

# Step 2: Navigate to project
Set-Location "FontEnd\PlasmaBotUI"

# Step 3: Backup package.json
Copy-Item package.json package.json.backup

# Step 4: Use Angular update tool
Write-Host "Running Angular update tool..." -ForegroundColor Yellow
ng update @angular/core@19 @angular/cli@19 --force

# Step 5: Clean install
Write-Host "Cleaning and reinstalling dependencies..." -ForegroundColor Yellow
Remove-Item -Recurse -Force node_modules -ErrorAction SilentlyContinue
Remove-Item -Force package-lock.json -ErrorAction SilentlyContinue
npm cache clean --force
npm install

Write-Host "Upgrade complete! Check for any errors and update your code." -ForegroundColor Green
```

---

## Important Breaking Changes (Angular 11 → 19)

### 1. **Standalone Components** (Recommended)
- Angular 19 prefers standalone components
- Remove `NgModule` declarations
- Use `imports` array in component decorator

### 2. **RxJS Changes**
- Update all RxJS operators
- `rxjs/operators` → direct imports
- Update subscription patterns

### 3. **TypeScript Strict Mode**
- Enable strict mode in `tsconfig.json`
- Fix type errors

### 4. **Material Design**
- Update to Angular Material 19
- Check component API changes

### 5. **Build System**
- Webpack 5 (instead of 4)
- New build optimizations

---

## After Upgrade Checklist

- [ ] Remove `--openssl-legacy-provider` from all scripts
- [ ] Remove `.npmrc` or update it
- [ ] Update all imports and dependencies
- [ ] Fix TypeScript errors
- [ ] Update RxJS operators
- [ ] Test all components
- [ ] Update Angular Material components
- [ ] Run `ng build` successfully
- [ ] Run `ng serve` successfully
- [ ] Test all API calls

---

## Alternative: Gradual Upgrade

If the jump from Angular 11 → 19 is too large, consider:

1. **Angular 11 → 12** (minor)
2. **Angular 12 → 13** (major)
3. **Angular 13 → 14** (major)
4. **Angular 14 → 15** (major)
5. **Angular 15 → 16** (major)
6. **Angular 16 → 17** (major)
7. **Angular 17 → 18** (major)
8. **Angular 18 → 19** (major)

Use `ng update` for each step.

---

## Need Help?

- [Angular Update Guide](https://update.angular.dev/)
- [Angular Migration Guide](https://angular.dev/reference/migrations)
- [Angular Version Compatibility](https://angular.dev/reference/versions)

---

## Recommendation

**Upgrade to Angular 19** because:
1. ✅ Full Node.js 22 support
2. ✅ No OpenSSL legacy provider needed
3. ✅ Modern features and performance
4. ✅ Better TypeScript support
5. ✅ Improved developer experience
