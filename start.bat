@echo off
cd .\LibreTranslate\
docker run --name translate-api -d -it -p 5000:5000 libretranslate
cd ..
cd .\elasticsearch\
docker-compose up -d
cd ..
