# Cashflow
Teste para vaga backend empresa omega


## Tecnologias

| Tecnologia  | Versão                |
|-------------|-----------------------|
| Docker      | 26.0.0, build 2ae903e |
| Java        | OpenJDK 17            |
| Maven       | 4.0.0                 |
| Spring Boot | 3.3.4                 |
| PostgreSQL  | 16.2-1                |

## PostgreSQL
As credenciais de acesso e portas devem ser alteradas antes de subir o docker através das variáveis de arquivo
criando um novo arquivo `.env` .

|       Porta        |        Nome           |  Usuário  |    Senha    |
|:------------------:|:---------------------:|:---------:|:-----------:|
|        3306        |    dbcontrolecaixa    |   omega   | adminOm3g@  |


## Deploy
O projeto está dockerizado, caso não tenha instalado, clickar no link e seguir os passos a baixo.

* [Docker](https://docs.docker.com/desktop/)
  * Para verificar se o docker foi instalado corretamente execute o comando `docker --version`
* [Docker Compose](https://docs.docker.com/compose/install/)
  * Para verificar se o docker-compose foi instalado corretamente execute o comando `docker-compose --version`

O projeto está dockerizado e pode ser executado com o comando abaixo:

```bash
docker-compose up -d
```