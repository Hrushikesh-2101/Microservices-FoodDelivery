# Step 7: Upgrade Angular 17 → 18

## Prerequisites
- ✅ Step 6 completed (Angular 17 installed and tested)

## Key Changes in Angular 18
- **Material 3** support
- **Zoneless change detection** (experimental)
- **TypeScript 5.4+** required
- **Improved performance**

## Step 7.1: Update package.json

```json
{
  "dependencies": {
    "@angular/animations": "~18.2.0",
    "@angular/cdk": "^18.2.0",
    "@angular/common": "~18.2.0",
    "@angular/compiler": "~18.2.0",
    "@angular/core": "~18.2.0",
    "@angular/forms": "~18.2.0",
    "@angular/material": "^18.2.0",
    "@angular/platform-browser": "~18.2.0",
    "@angular/platform-browser-dynamic": "~18.2.0",
    "@angular/router": "~18.2.0",
    "rxjs": "~7.8.0",
    "tslib": "^2.3.0",
    "zone.js": "~0.14.0"
  },
  "devDependencies": {
    "@angular-devkit/build-angular": "~18.2.0",
    "@angular/cli": "~18.2.0",
    "@angular/compiler-cli": "~18.2.0",
    "@types/jasmine": "~5.1.0",
    "@types/node": "^18.7.0",
    "cross-env": "^7.0.3",
    "jasmine-core": "~5.1.0",
    "jasmine-spec-reporter": "~7.0.0",
    "karma": "~6.4.0",
    "karma-chrome-launcher": "~3.2.0",
    "karma-coverage": "~2.2.0",
    "karma-jasmine": "~5.1.0",
    "karma-jasmine-html-reporter": "^2.1.0",
    "ts-node": "~10.9.1",
    "typescript": "~5.4.5"
  }
}
```

## Step 7.2: Code Changes Required

### 1. Material 3 Updates

Material 3 has new theming. Update your theme if needed.

### 2. TypeScript 5.4

Update TypeScript and fix any new type errors.

## Step 7.3: Commands to Run

```powershell
Remove-Item -Recurse -Force node_modules
Remove-Item -Force package-lock.json
npm cache clean --force
npm install
npm install -g @angular/cli@18.2.0
```

## Step 7.4: Testing Checklist

- [ ] `npm start` - Application starts
- [ ] `npm run build` - Production build succeeds
- [ ] Material components work with Material 3
- [ ] All functionality works

## Next Step

Once Step 7 is complete, proceed to **Step 8: Angular 18 → 19** (Final Step!)
