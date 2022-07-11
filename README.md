# Golden Raspberry Awards

Desenvolvido uma API RESTful para possibilitar a leitura da lista de indicados e vencedores
da categoria Pior Filme do Golden Raspberry Awards.

## Tecnologias utilizadas

* Java >= 11
* Spring Boot 2.7.1

## Passos para execução

`mvn install`

`mvn spring-boot:run` 

## Endpoints

`GET /api/award/max-min`

curl --location --request GET 'localhost:8080/api/award/max-min'
    
## Swagger para mais detalhes

`http://localhost:8080/swagger-ui/index.html`
