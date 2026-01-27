# Step 2: Upgrade Angular 12 → 13

## Prerequisites
- ✅ Step 1 completed (Angular 12 installed and tested)

## Key Changes in Angular 13
- **View Engine removed** - Ivy is now the only compiler
- **TypeScript 4.4+** required
- **Dynamic component creation** API changes
- **Angular Package Format (APF)** updates

## Step 2.1: Update package.json

```json
{
  "dependencies": {
    "@angular/animations": "~13.3.11",
    "@angular/cdk": "^13.3.9",
    "@angular/common": "~13.3.11",
    "@angular/compiler": "~13.3.11",
    "@angular/core": "~13.3.11",
    "@angular/forms": "~13.3.11",
    "@angular/material": "^13.3.9",
    "@angular/platform-browser": "~13.3.11",
    "@angular/platform-browser-dynamic": "~13.3.11",
    "@angular/router": "~13.3.11",
    "rxjs": "~7.5.0",
    "tslib": "^2.3.0",
    "zone.js": "~0.11.4"
  },
  "devDependencies": {
    "@angular-devkit/build-angular": "~13.3.9",
    "@angular/cli": "~13.3.9",
    "@angular/compiler-cli": "~13.3.11",
    "@types/jasmine": "~3.10.0",
    "@types/node": "^12.20.0",
    "codelyzer": "^6.0.2",
    "cross-env": "^7.0.3",
    "jasmine-core": "~4.0.0",
    "jasmine-spec-reporter": "~7.0.0",
    "karma": "~6.4.0",
    "karma-chrome-launcher": "~3.1.0",
    "karma-coverage": "~2.1.0",
    "karma-jasmine": "~5.1.0",
    "karma-jasmine-html-reporter": "^1.7.0",
    "protractor": "~7.0.0",
    "ts-node": "~10.9.1",
    "tslint": "~6.1.0",
    "typescript": "~4.6.4"
  }
}
```

## Step 2.2: Code Changes Required

### 1. Update RxJS Imports (IMPORTANT!)

RxJS 7 has breaking changes. Update all RxJS imports:

**Before (RxJS 6):**
```typescript
import { map, filter } from 'rxjs/operators';
```

**After (RxJS 7):**
```typescript
import { map, filter } from 'rxjs';
```

### 2. Update ViewChild/ContentChild (if used)

If you use `@ViewChild` or `@ContentChild` without `static: true`, you may need to add it:

```typescript
// Before
@ViewChild('myElement') element: ElementRef;

// After (if needed)
@ViewChild('myElement', { static: false }) element: ElementRef;
```

### 3. Update TypeScript Configuration

Update `tsconfig.json`:

```json
{
  "compilerOptions": {
    "target": "ES2020",
    "module": "ES2020",
    "lib": ["ES2020", "dom"],
    "moduleResolution": "node",
    "strict": false,
    "skipLibCheck": true,
    "forceConsistentCasingInFileNames": true
  }
}
```

## Step 2.3: Commands to Run

```powershell
# Remove old dependencies
Remove-Item -Recurse -Force node_modules
Remove-Item -Force package-lock.json

# Clean npm cache
npm cache clean --force

# Install new dependencies
npm install

# Update Angular CLI globally
npm install -g @angular/cli@13.3.9
```

## Step 2.4: Testing Checklist

- [ ] `npm start` - Application starts
- [ ] `npm run build` - Production build succeeds
- [ ] All RxJS operators work correctly
- [ ] Components render correctly
- [ ] HTTP requests work
- [ ] Forms work

## Step 2.5: Common Issues & Fixes

### Issue: RxJS operator errors
**Fix**: Update all imports from `rxjs/operators` to `rxjs`

### Issue: ViewChild errors
**Fix**: Add `{ static: false }` to ViewChild decorators if needed

### Issue: Build errors
**Fix**: Ensure TypeScript 4.6+ is installed

## Next Step

Once Step 2 is complete, proceed to **Step 3: Angular 13 → 14**
