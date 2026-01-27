# Step 3: Upgrade Angular 13 → 14

## Prerequisites
- ✅ Step 2 completed (Angular 13 installed and tested)

## Key Changes in Angular 14
- **Standalone components** introduced (optional)
- **Angular CLI autocomplete**
- **Optional injectors**
- **TypeScript 4.6+** required
- **Strict forms** by default

## Step 3.1: Update package.json

```json
{
  "dependencies": {
    "@angular/animations": "~14.3.0",
    "@angular/cdk": "^14.2.7",
    "@angular/common": "~14.3.0",
    "@angular/compiler": "~14.3.0",
    "@angular/core": "~14.3.0",
    "@angular/forms": "~14.3.0",
    "@angular/material": "^14.2.7",
    "@angular/platform-browser": "~14.3.0",
    "@angular/platform-browser-dynamic": "~14.3.0",
    "@angular/router": "~14.3.0",
    "rxjs": "~7.5.0",
    "tslib": "^2.3.0",
    "zone.js": "~0.11.4"
  },
  "devDependencies": {
    "@angular-devkit/build-angular": "~14.2.13",
    "@angular/cli": "~14.2.13",
    "@angular/compiler-cli": "~14.3.0",
    "@types/jasmine": "~4.0.0",
    "@types/node": "^14.18.0",
    "codelyzer": "^6.0.2",
    "cross-env": "^7.0.3",
    "jasmine-core": "~4.3.0",
    "jasmine-spec-reporter": "~7.0.0",
    "karma": "~6.4.0",
    "karma-chrome-launcher": "~3.1.0",
    "karma-coverage": "~2.1.0",
    "karma-jasmine": "~5.1.0",
    "karma-jasmine-html-reporter": "^1.7.0",
    "protractor": "~7.0.0",
    "ts-node": "~10.9.1",
    "tslint": "~6.1.0",
    "typescript": "~4.7.4"
  }
}
```

## Step 3.2: Code Changes Required

### 1. Update Forms (if using reactive forms)

Angular 14 has stricter form typing. Update form controls:

```typescript
// Before
this.form = this.fb.group({
  name: '',
  email: ''
});

// After (more explicit typing)
this.form = this.fb.group({
  name: [''],
  email: ['']
});
```

### 2. Optional: Migrate to Standalone Components

This is optional but recommended for future versions. You can keep using NgModule for now.

## Step 3.3: Commands to Run

```powershell
Remove-Item -Recurse -Force node_modules
Remove-Item -Force package-lock.json
npm cache clean --force
npm install
npm install -g @angular/cli@14.2.13
```

## Step 3.4: Testing Checklist

- [ ] `npm start` - Application starts
- [ ] `npm run build` - Production build succeeds
- [ ] Forms work correctly (check for type errors)
- [ ] All components render
- [ ] HTTP requests work

## Next Step

Once Step 3 is complete, proceed to **Step 4: Angular 14 → 15**
