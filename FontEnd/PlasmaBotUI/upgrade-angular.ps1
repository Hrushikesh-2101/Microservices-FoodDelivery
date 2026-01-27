# PowerShell script to upgrade Angular project to version 19
# This script upgrades Angular 11 to Angular 19 for Node.js 22 compatibility

param(
    [string]$TargetVersion = "19"
)

Write-Host "========================================" -ForegroundColor Cyan
Write-Host "Angular Upgrade Script" -ForegroundColor Cyan
Write-Host "Current: Angular 11.2.14" -ForegroundColor Yellow
Write-Host "Target: Angular $TargetVersion" -ForegroundColor Green
Write-Host "========================================" -ForegroundColor Cyan
Write-Host ""

# Check if we're in the right directory
if (-not (Test-Path "package.json")) {
    Write-Host "Error: package.json not found. Please run this script from the Angular project root." -ForegroundColor Red
    exit 1
}

# Step 1: Backup package.json
Write-Host "[1/6] Backing up package.json..." -ForegroundColor Yellow
$backupFile = "package.json.backup.$(Get-Date -Format 'yyyyMMdd-HHmmss')"
Copy-Item package.json $backupFile
Write-Host "       Backup saved to: $backupFile" -ForegroundColor Green

# Step 2: Update global Angular CLI
Write-Host "[2/6] Updating global Angular CLI to latest..." -ForegroundColor Yellow
npm install -g @angular/cli@latest
if ($LASTEXITCODE -ne 0) {
    Write-Host "       Warning: Global CLI update had issues. Continuing..." -ForegroundColor Yellow
}

# Step 3: Check current Angular version
Write-Host "[3/6] Checking current Angular version..." -ForegroundColor Yellow
$ngVersion = ng version 2>&1 | Select-String "Angular CLI"
Write-Host "       $ngVersion" -ForegroundColor Cyan

# Step 4: Use Angular update tool
Write-Host "[4/6] Running Angular update tool..." -ForegroundColor Yellow
Write-Host "       This may take several minutes..." -ForegroundColor Yellow
Write-Host ""
Write-Host "       Command: ng update @angular/core@$TargetVersion @angular/cli@$TargetVersion" -ForegroundColor Cyan
Write-Host ""

$response = Read-Host "       Do you want to proceed with the upgrade? (Y/N)"
if ($response -ne "Y" -and $response -ne "y") {
    Write-Host "       Upgrade cancelled." -ForegroundColor Yellow
    exit 0
}

ng update @angular/core@$TargetVersion @angular/cli@$TargetVersion --force
if ($LASTEXITCODE -ne 0) {
    Write-Host "       Warning: ng update encountered issues. You may need to update manually." -ForegroundColor Yellow
    Write-Host "       See ANGULAR_UPGRADE_GUIDE.md for manual steps." -ForegroundColor Yellow
}

# Step 5: Clean and reinstall
Write-Host "[5/6] Cleaning old dependencies..." -ForegroundColor Yellow
if (Test-Path "node_modules") {
    Remove-Item -Recurse -Force node_modules
    Write-Host "       Removed node_modules" -ForegroundColor Green
}
if (Test-Path "package-lock.json") {
    Remove-Item -Force package-lock.json
    Write-Host "       Removed package-lock.json" -ForegroundColor Green
}

Write-Host "[6/6] Installing new dependencies..." -ForegroundColor Yellow
npm cache clean --force
npm install

if ($LASTEXITCODE -eq 0) {
    Write-Host ""
    Write-Host "========================================" -ForegroundColor Green
    Write-Host "Upgrade process completed!" -ForegroundColor Green
    Write-Host "========================================" -ForegroundColor Green
    Write-Host ""
    Write-Host "Next steps:" -ForegroundColor Yellow
    Write-Host "1. Review the changes in package.json" -ForegroundColor White
    Write-Host "2. Update your code for breaking changes" -ForegroundColor White
    Write-Host "3. Remove --openssl-legacy-provider from scripts (no longer needed!)" -ForegroundColor White
    Write-Host "4. Test your application: npm start" -ForegroundColor White
    Write-Host "5. See ANGULAR_UPGRADE_GUIDE.md for detailed migration steps" -ForegroundColor White
    Write-Host ""
} else {
    Write-Host ""
    Write-Host "========================================" -ForegroundColor Red
    Write-Host "Upgrade encountered errors!" -ForegroundColor Red
    Write-Host "========================================" -ForegroundColor Red
    Write-Host ""
    Write-Host "Please:" -ForegroundColor Yellow
    Write-Host "1. Check the error messages above" -ForegroundColor White
    Write-Host "2. Restore package.json from backup: $backupFile" -ForegroundColor White
    Write-Host "3. See ANGULAR_UPGRADE_GUIDE.md for manual upgrade steps" -ForegroundColor White
    Write-Host ""
}
