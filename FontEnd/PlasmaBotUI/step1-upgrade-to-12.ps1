# Step 1: Upgrade Angular 11 → 12
# Run this script from the Angular project root directory

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "Step 1: Upgrading Angular 11 → 12" -ForegroundColor Cyan
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

# Check if we're in the right directory
if (-not (Test-Path "package.json")) {
    Write-Host "Error: package.json not found. Please run this script from the Angular project root." -ForegroundColor Red
    exit 1
}

# Backup package.json
Write-Host "[1/5] Backing up package.json..." -ForegroundColor Yellow
$backupFile = "package.json.backup-step1-$(Get-Date -Format 'yyyyMMdd-HHmmss')"
Copy-Item package.json $backupFile
Write-Host "       Backup saved to: $backupFile" -ForegroundColor Green

# Remove old dependencies
Write-Host "[2/5] Removing old dependencies..." -ForegroundColor Yellow
if (Test-Path "node_modules") {
    Remove-Item -Recurse -Force node_modules
    Write-Host "       Removed node_modules" -ForegroundColor Green
}
if (Test-Path "package-lock.json") {
    Remove-Item -Force package-lock.json
    Write-Host "       Removed package-lock.json" -ForegroundColor Green
}

# Clean npm cache
Write-Host "[3/5] Cleaning npm cache..." -ForegroundColor Yellow
npm cache clean --force
Write-Host "       Cache cleaned" -ForegroundColor Green

# Install new dependencies
Write-Host "[4/5] Installing Angular 12 dependencies..." -ForegroundColor Yellow
Write-Host "       This may take a few minutes..." -ForegroundColor Yellow
npm install

if ($LASTEXITCODE -ne 0) {
    Write-Host ""
    Write-Host "Error: npm install failed!" -ForegroundColor Red
    Write-Host "Restoring backup..." -ForegroundColor Yellow
    Copy-Item $backupFile package.json -Force
    exit 1
}

# Verify installation
Write-Host "[5/5] Verifying installation..." -ForegroundColor Yellow
$ngVersion = ng version 2>&1 | Select-String "Angular CLI"
Write-Host "       $ngVersion" -ForegroundColor Cyan

Write-Host ""
Write-Host "========================================" -ForegroundColor Green
Write-Host "Step 1 Complete!" -ForegroundColor Green
Write-Host "========================================" -ForegroundColor Green
Write-Host ""
Write-Host "Next steps:" -ForegroundColor Yellow
Write-Host "1. Test your application: npm start" -ForegroundColor White
Write-Host "2. Run build: npm run build" -ForegroundColor White
Write-Host "3. Fix any TypeScript errors if they occur" -ForegroundColor White
Write-Host "4. Once everything works, proceed to Step 2 (Angular 12 → 13)" -ForegroundColor White
Write-Host ""
Write-Host "See STEP_1_ANGULAR_11_TO_12.md for detailed information." -ForegroundColor Cyan
Write-Host ""
