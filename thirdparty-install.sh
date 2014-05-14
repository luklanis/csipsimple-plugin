#!/bin/bash

wget http://www.twofortyfouram.com/developer/display.zip
unzip display.zip

mkdir -p thirdparty/locale-api/src/main/java

cp -r display/locale-api/src/com thirdparty/locale-api/src/main/java/
cp -r display/locale-api/res thirdparty/locale-api/src/main/

cp display/lacale-api/AndroidManifest.xml thirdparty/locale-api/src/main/

rm -rf display

cp locale-api-scripts/* thirdparty/locale-api/
