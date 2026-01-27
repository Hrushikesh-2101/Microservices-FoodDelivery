# Step 1: Upgrade Angular 11 → 12

## Current Status
- Angular: 11.2.14
- Target: Angular 12.x

## Step 1.1: Update package.json

Update your dependencies to Angular 12:

```json
{
  "dependencies": {
    "@angular/animations": "~12.2.16",
    "@angular/cdk": "^12.2.13",
    "@angular/common": "~12.2.16",
    "@angular/compiler": "~12.2.16",
    "@angular/core": "~12.2.16",
    "@angular/forms": "~12.2.16",
    "@angular/material": "^12.2.13",
    "@angular/platform-browser": "~12.2.16",
    "@angular/platform-browser-dynamic": "~12.2.16",
    "@angular/router": "~12.2.16",
    "rxjs": "~6.6.7",
    "tslib": "^2.3.0",
    "zone.js": "~0.11.4"
  },
  "devDependencies": {
    "@angular-devkit/build-angular": "~12.2.18",
    "@angular/cli": "~12.2.18",
    "@angular/compiler-cli": "~12.2.16",
    "@types/jasmine": "~3.8.0",
    "@types/node": "^12.20.0",
    "codelyzer": "^6.0.2",
    "cross-env": "^7.0.3",
    "jasmine-core": "~3.8.0",
    "jasmine-spec-reporter": "~7.0.0",
    "karma": "~6.3.0",
    "karma-chrome-launcher": "~3.1.0",
    "karma-coverage": "~2.0.3",
    "karma-jasmine": "~4.0.0",
    "karma-jasmine-html-reporter": "^1.7.0",
    "protractor": "~7.0.0",
    "ts-node": "~10.0.0",
    "tslint": "~6.1.0",
    "typescript": "~4.3.5"
  }
}
```

## Step 1.2: Commands to Run

```powershell
# Navigate to project directory
cd FontEnd\PlasmaBotUI

# Remove old dependencies
Remove-Item -Recurse -Force node_modules
Remove-Item -Force package-lock.json

# Clean npm cache
npm cache clean --force

# Install new dependencies
npm install

# Update Angular CLI globally (optional but recommended)
npm install -g @angular/cli@12.2.18
```

## Step 1.3: Code Changes Required

### 1. Update TypeScript Configuration (tsconfig.json)

Update `tsconfig.json`:

```json
{
  "compileOnSave": false,
  "compilerOptions": {
    "baseUrl": "./",
    "outDir": "./dist/out-tsc",
    "sourceMap": true,
    "declaration": false,
    "downlevelIteration": true,
    "experimentalDecorators": true,
    "moduleResolution": "node",
    "importHelpers": true,
    "target": "ES2020",
    "module": "ES2020",
    "lib": [
      "ES2020",
      "dom"
    ],
    "strict": false,
    "skipLibCheck": true,
    "forceConsistentCasingInFileNames": true
  }
}
```

### 2. Update angular.json

The `angular.json` should work as-is, but verify the builder is correct:
- Builder: `@angular-devkit/build-angular:browser`

### 3. No Major Code Changes Required

Angular 11 → 12 is relatively smooth. Most code should work without changes.

## Step 1.4: Testing Checklist

After upgrading, test:

- [ ] `npm start` - Application starts without errors
- [ ] `npm run build` - Production build succeeds
- [ ] All components render correctly
- [ ] HTTP requests work (ProductService, OrderService, CartService)
- [ ] Angular Material components display correctly
- [ ] Routing works (navigate between pages)
- [ ] Forms work (if using reactive forms)

## Step 1.5: Common Issues & Fixes

### Issue: TypeScript errors
**Fix**: Update `tsconfig.json` as shown above

### Issue: Build errors
**Fix**: Run `npm install` again and check for version conflicts

### Issue: Material components not rendering
**Fix**: Ensure all Material modules are imported correctly in `app.module.ts`

## Step 1.6: Verify Upgrade

```powershell
# Check Angular version
ng version

# Should show Angular CLI: 12.2.18
# And Angular: 12.2.16
```

## Next Step

Once Step 1 is complete and tested, proceed to **Step 2: Angular 12 → 13**
