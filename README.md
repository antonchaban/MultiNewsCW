# MultiNews
### Project for aggregation and automatic translation of news


#### How to run 

Using [LibreTranslate API](https://github.com/LibreTranslate/LibreTranslate)


`cd .\LibreTranslate\`

`docker run --name translate-api -d -it -p 5000:5000 libretranslate`

`cd ..\`

`docker run --name mnews-db -e POSTGRES_PASSWORD='qwerty' -p 5436:5432 -d --rm postgres`

`migrate -path ./schema -database 'postgres://postgres:qwerty@localhost:5436/postgres?sslmode=disable' up`

`cd .\elasticsearch\`

`docker-compose up -d `