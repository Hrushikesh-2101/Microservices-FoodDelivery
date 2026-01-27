# Running Angular Application - Quick Guide

## ✅ Fixed: OpenSSL Legacy Provider Error

This project is configured to work with **Node.js 17+** and **Angular 11** without changing versions.

## 🚀 How to Run (Choose One Method)

### **Method 1: Using npm scripts (RECOMMENDED)**
```powershell
cd FontEnd\PlasmaBotUI
npm start
```

### **Method 2: Using PowerShell script**
```powershell
cd FontEnd\PlasmaBotUI
.\start-dev.ps1
```

### **Method 3: Using Batch script**
```cmd
cd FontEnd\PlasmaBotUI
start-dev.bat
```

### **Method 4: Manual (if running ng directly)**
```powershell
cd FontEnd\PlasmaBotUI
$env:NODE_OPTIONS = "--openssl-legacy-provider"
ng serve
```

## 📋 Available Commands

All these commands automatically include the OpenSSL legacy provider flag:

- `npm start` - Start development server
- `npm run build` - Build for production
- `npm test` - Run unit tests
- `npm run lint` - Run linting
- `npm run e2e` - Run end-to-end tests

## 🔧 What Was Fixed

1. **Created `.npmrc` file** - Sets `node-options=--openssl-legacy-provider` globally for npm commands
2. **Updated `package.json` scripts** - All Angular CLI commands include the flag via `cross-env`
3. **Enhanced startup scripts** - `start-dev.ps1` and `start-dev.bat` set the environment variable

## ⚠️ Important Notes

- **Always use npm scripts** (`npm start`, `npm run build`) instead of running `ng` commands directly
- The `.npmrc` file ensures the flag is set automatically for all npm commands
- If you must run `ng` directly, set `$env:NODE_OPTIONS = "--openssl-legacy-provider"` first in PowerShell

## 🐛 Troubleshooting

If you still see the error:
1. Make sure you're in the `FontEnd\PlasmaBotUI` directory
2. Use `npm start` instead of `ng serve`
3. Check that `.npmrc` file exists in the project root
4. Restart your terminal/PowerShell session
