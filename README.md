# Tech Challenge - Sistema de Produção do Pedido

O sistema tem como intuito fornecer o processo de operacionalização da produção de um pedido, oferecendo as funcionalidade de iniciar e finalizar o preparo de um pedido.

## Integrantes do Grupo
- RM354032 - Alysson Gustavo Rodrigues Maciel
- RM355969 - Vinicius Duarte Mendes Nepomuceno
- RM354090 - Lucas Pugliese de Morais Barros
- RM353273 - Felipe Pinheiro Dantas

## Event Storming do Projeto
```url
https://miro.com/app/board/uXjVKSt4Gq8=/?share_link_id=968579577663
```

## Para acessar o swagger e realizar os testes
Rota para acessar Swagger
```url
http://localhost:8080/producao/v1/swagger-ui
```
Rota para acessar Swagger.yml
```url
http://localhost:8080/producao/v1/api-docs
```
Dentro do Projeto no diretório "postman" há um arquivo com uma collection postman com todas as rotas mapeadas para teste
```
./postman/Postech 4 - Sistema de Produção.postman_collection.json
```

## Iniciar preparo do pedido

Para criar iniciar ou finalizar o preparo de um pedido deve ser informado apenas o "id do pedido" em uma requisição sem body.

```url
POST http://localhost:8080/producao/v1/preparar/{idPedido}
```

O retorno do inicio de preparação do pedido com sucesso será:

```url
{
    "id": 1,
    "idPedido": 56,
    "status": "PREPARANDO",
    "inicioPreparo": "2024-11-27T15:23:46.2834791"
}
```

## Finalizar preparo do pedido

Quando o preparo do pedido for finalizado deve-se acionar o mesmo endpoint com os mesmos parâmetros porém com o método Patch para realizar a finalização.

```url
PATCH http://localhost:8080/producao/v1/preparar/{idPedido}
```

O retorno do finalização do preparo do pedido com sucesso será:

```url
{
    "id": 1,
    "idPedido": 56,
    "status": "PRONTO",
    "inicioPreparo": "2024-11-27T15:23:46.283479",
    "fimPreparo": "2024-11-27T15:23:57.3577887",
    "tempoTotalPreparo": "00:00:11"
}
```
