# Step 5: Upgrade Angular 15 → 16

## Prerequisites
- ✅ Step 4 completed (Angular 15 installed and tested)

## Key Changes in Angular 16
- **Required inputs** introduced
- **Standalone ng new** projects by default
- **Signals** (experimental)
- **TypeScript 4.9+** required
- **Router** API updates

## Step 5.1: Update package.json

```json
{
  "dependencies": {
    "@angular/animations": "~16.2.14",
    "@angular/cdk": "^16.2.12",
    "@angular/common": "~16.2.14",
    "@angular/compiler": "~16.2.14",
    "@angular/core": "~16.2.14",
    "@angular/forms": "~16.2.14",
    "@angular/material": "^16.2.12",
    "@angular/platform-browser": "~16.2.14",
    "@angular/platform-browser-dynamic": "~16.2.14",
    "@angular/router": "~16.2.14",
    "rxjs": "~7.8.0",
    "tslib": "^2.3.0",
    "zone.js": "~0.14.0"
  },
  "devDependencies": {
    "@angular-devkit/build-angular": "~16.2.13",
    "@angular/cli": "~16.2.13",
    "@angular/compiler-cli": "~16.2.14",
    "@types/jasmine": "~4.3.0",
    "@types/node": "^18.7.0",
    "codelyzer": "^6.0.2",
    "cross-env": "^7.0.3",
    "jasmine-core": "~5.1.0",
    "jasmine-spec-reporter": "~7.0.0",
    "karma": "~6.4.0",
    "karma-chrome-launcher": "~3.2.0",
    "karma-coverage": "~2.2.0",
    "karma-jasmine": "~5.1.0",
    "karma-jasmine-html-reporter": "^2.1.0",
    "protractor": "~7.0.0",
    "ts-node": "~10.9.1",
    "typescript": "~5.1.3"
  }
}
```

## Step 5.2: Code Changes Required

### 1. Update TypeScript to 5.1+

TypeScript 5.1 has stricter type checking. Fix any new type errors.

### 2. Router Updates

Router API has some updates. Most code should work as-is.

## Step 5.3: Commands to Run

```powershell
Remove-Item -Recurse -Force node_modules
Remove-Item -Force package-lock.json
npm cache clean --force
npm install
npm install -g @angular/cli@16.2.13
```

## Step 5.4: Testing Checklist

- [ ] `npm start` - Application starts
- [ ] `npm run build` - Production build succeeds
- [ ] Fix any TypeScript errors
- [ ] All components work
- [ ] Routing works

## Next Step

Once Step 5 is complete, proceed to **Step 6: Angular 16 → 17**
