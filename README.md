# Tech Challenge - Sistema de Produção do Pedido

O sistema tem como intuito orquestrar as chamadas do upload, processamento e download de um video.

## Integrantes do Grupo
- RM354032 - Alysson Gustavo Rodrigues Maciel
- RM355969 - Vinicius Duarte Mendes Nepomuceno
- RM354090 - Lucas Pugliese de Morais Barros
- RM353273 - Felipe Pinheiro Dantas

## Para acessar o swagger e realizar os testes
Rota para acessar Swagger
```url
http://localhost:8080/upload-download/v1/process
```
Rota para acessar Swagger.yml
```url
http://localhost:8080/upload-download/v1/api-docs
```
Dentro do Projeto no diretório "postman" há um arquivo com uma collection postman com todas as rotas mapeadas para teste
```
./postman/Sistema de Upload-Download.postman_collection.json
```

## Fazer upload do video e iniciar processamento

Para fazer o upload e iniciar o processamento, deve-se chamar o endpoint a seguir informando o header "token" (token jwt do usuário cadastrado), e os videos enviados no body no padrão "form-data", a key deve ser "files", e selecionar de 1 a 3 videos com no máximo 100 megas por vídeo.

```url
POST http://localhost:8080/upload-download/v1/process
    
    Body: form-data
    key: files
    value: selecione os vídeos

```

O retorno do processamento será:

```url
{
    "id": "b3ce2f4a-ac52-4b7d-aaa8-9b7e01690100",
    "files": [
        {
            "id": "a95a5f60-7423-4134-80bc-df870a320770",
            "name": "video-exemplo.mp4"
        }
    ],
    "status": "REQUESTED",
    "created_at": "2025-02-09T01:13:55.758461900"
}
```

## Buscar processamento de vídeo

Quando o processamento for enviado, será retornado um id do processamento, nessa chamada será necessário o id e o token jwt.

```url
GET http://localhost:8080/upload-download/v1/process/{processId}
```

O retorno da consulta de processamento será:

```url
{
    "id": "b3ce2f4a-ac52-4b7d-aaa8-9b7e01690100",
    "files": [
        {
            "id": "a95a5f60-7423-4134-80bc-df870a320770",
            "name": "HINO DOS DESBRAVADORES .mp4",
            "link: "https://download-link.com"
        }
    ],
    "status": "AVAILABLE",
    "created_at": "2025-02-09T01:13:55.758461900"
}
```
