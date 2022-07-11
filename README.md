# Golden Raspberry Awards

Desenvolvido uma API RESTful para possibilitar a leitura da lista de indicados e vencedores
da categoria Pior Filme do Golden Raspberry Awards.

## Tecnologias utilizadas

* Java >= 11
* Spring Boot 2.7.1
* Gradle 6.8.x, 6.9.x ou 7.x

## Passos para execução

`gradle bootRun` 

## Endpoints

`GET /api/award/max-min`

curl --location --request GET 'localhost:8080/api/award/max-min'
    
## Swagger para mais detalhes

`http://localhost:8080/swagger-ui/index.html`
