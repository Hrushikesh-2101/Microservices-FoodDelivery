# Step 6: Upgrade Angular 16 → 17

## Prerequisites
- ✅ Step 5 completed (Angular 16 installed and tested)

## Key Changes in Angular 17
- **Signals stable**
- **New control flow** syntax (@if, @for, @switch)
- **Standalone by default**
- **TypeScript 5.2+** required
- **SSR improvements**

## Step 6.1: Update package.json

```json
{
  "dependencies": {
    "@angular/animations": "~17.3.9",
    "@angular/cdk": "^17.3.9",
    "@angular/common": "~17.3.9",
    "@angular/compiler": "~17.3.9",
    "@angular/core": "~17.3.9",
    "@angular/forms": "~17.3.9",
    "@angular/material": "^17.3.9",
    "@angular/platform-browser": "~17.3.9",
    "@angular/platform-browser-dynamic": "~17.3.9",
    "@angular/router": "~17.3.9",
    "rxjs": "~7.8.0",
    "tslib": "^2.3.0",
    "zone.js": "~0.14.0"
  },
  "devDependencies": {
    "@angular-devkit/build-angular": "~17.3.8",
    "@angular/cli": "~17.3.8",
    "@angular/compiler-cli": "~17.3.9",
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
    "typescript": "~5.2.2"
  }
}
```

## Step 6.2: Code Changes Required

### 1. New Control Flow Syntax (Optional but Recommended)

You can migrate from `*ngIf` to `@if`:

**Before:**
```html
<div *ngIf="condition">Content</div>
```

**After:**
```html
@if (condition) {
  <div>Content</div>
}
```

**For loops:**
```html
<!-- Before -->
<div *ngFor="let item of items">{{ item }}</div>

<!-- After -->
@for (item of items; track item.id) {
  <div>{{ item }}</div>
}
```

### 2. Remove TSLint (Deprecated)

TSLint is deprecated. Remove it and use ESLint instead:

```powershell
npm uninstall tslint codelyzer
npm install --save-dev @angular-eslint/eslint-plugin @angular-eslint/template-parser @typescript-eslint/eslint-plugin @typescript-eslint/parser eslint
```

### 3. Update angular.json

Remove tslint builder and add eslint if migrating.

## Step 6.3: Commands to Run

```powershell
Remove-Item -Recurse -Force node_modules
Remove-Item -Force package-lock.json
npm cache clean --force
npm install
npm install -g @angular/cli@17.3.8
```

## Step 6.4: Testing Checklist

- [ ] `npm start` - Application starts
- [ ] `npm run build` - Production build succeeds
- [ ] Consider migrating to new control flow syntax
- [ ] All components work

## Next Step

Once Step 6 is complete, proceed to **Step 7: Angular 17 → 18**
