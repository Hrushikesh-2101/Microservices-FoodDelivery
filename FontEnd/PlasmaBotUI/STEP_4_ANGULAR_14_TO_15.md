# Step 4: Upgrade Angular 14 → 15

## Prerequisites
- ✅ Step 3 completed (Angular 14 installed and tested)

## Key Changes in Angular 15
- **Standalone APIs stable**
- **MDC-based Angular Material** components
- **TypeScript 4.8+** required
- **Image directive** introduced
- **Router guard** changes

## Step 4.1: Update package.json

```json
{
  "dependencies": {
    "@angular/animations": "~15.2.11",
    "@angular/cdk": "^15.2.9",
    "@angular/common": "~15.2.11",
    "@angular/compiler": "~15.2.11",
    "@angular/core": "~15.2.11",
    "@angular/forms": "~15.2.11",
    "@angular/material": "^15.2.9",
    "@angular/platform-browser": "~15.2.11",
    "@angular/platform-browser-dynamic": "~15.2.11",
    "@angular/router": "~15.2.11",
    "rxjs": "~7.8.0",
    "tslib": "^2.3.0",
    "zone.js": "~0.12.0"
  },
  "devDependencies": {
    "@angular-devkit/build-angular": "~15.2.9",
    "@angular/cli": "~15.2.9",
    "@angular/compiler-cli": "~15.2.11",
    "@types/jasmine": "~4.3.0",
    "@types/node": "^18.7.0",
    "codelyzer": "^6.0.2",
    "cross-env": "^7.0.3",
    "jasmine-core": "~4.5.0",
    "jasmine-spec-reporter": "~7.0.0",
    "karma": "~6.4.0",
    "karma-chrome-launcher": "~3.2.0",
    "karma-coverage": "~2.2.0",
    "karma-jasmine": "~5.1.0",
    "karma-jasmine-html-reporter": "^2.1.0",
    "protractor": "~7.0.0",
    "ts-node": "~10.9.1",
    "typescript": "~4.9.5"
  }
}
```

## Step 4.2: Code Changes Required

### 1. Angular Material MDC Migration

Angular Material 15 uses MDC (Material Design Components). Some component APIs changed:

**Common changes:**
- `mat-form-field` appearance may need updates
- Button styles may change slightly
- Checkbox/radio button APIs updated

### 2. Router Guard Changes

If using route guards, update the return types:

```typescript
// Before
canActivate(): boolean {
  return true;
}

// After (more explicit)
canActivate(): boolean | UrlTree {
  return true;
}
```

## Step 4.3: Commands to Run

```powershell
Remove-Item -Recurse -Force node_modules
Remove-Item -Force package-lock.json
npm cache clean --force
npm install
npm install -g @angular/cli@15.2.9
```

## Step 4.4: Testing Checklist

- [ ] `npm start` - Application starts
- [ ] `npm run build` - Production build succeeds
- [ ] Material components display correctly
- [ ] Forms work
- [ ] Routing works (including guards)

## Next Step

Once Step 4 is complete, proceed to **Step 5: Angular 15 → 16**
