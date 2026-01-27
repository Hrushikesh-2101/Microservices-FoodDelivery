@echo off
echo Setting NODE_OPTIONS for OpenSSL legacy provider compatibility...
set NODE_OPTIONS=--openssl-legacy-provider
echo NODE_OPTIONS=%NODE_OPTIONS%
echo Starting Angular dev server...
ng serve

