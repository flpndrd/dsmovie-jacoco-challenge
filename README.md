# Desafio — DSMovie Jacoco

## 📄 Descrição

Este projeto implementa os testes unitários da camada de service do DSMovie, uma API REST de filmes e avaliações, com medição de cobertura de código pelo JaCoCo.

## 🎯 Objetivo do desafio

Implementar os testes unitários de service do projeto base, de forma que o JaCoCo reporte pelo menos 90% de cobertura. O projeto entrega 100% de linhas e ramos nos services.

## 🛠 Tecnologias utilizadas

- Java 25
- Spring Boot 4.1.1
- Spring Data JPA
- Hibernate
- Bean Validation
- Spring Security (Resource Server com JWT)
- Banco de dados H2
- JUnit
- Mockito
- AssertJ
- JaCoCo

## 📐 Modelo de Domínio

- **User**: `userId`, `name`, `username` (único), `password`, com um ou mais papéis
- **Role**: `roleId`, `authority`
- **Movie**: `movieId`, `title`, `score`, `count`, `image`, com as suas avaliações
- **Score**: `scoreValue`, com o filme e o usuário como chave composta

No JSON, o id do filme continua como `id`, como no contrato do curso.

## 📋 Regras de negócio

- A consulta de filmes é pública.
- A inserção, a atualização e a exclusão de filmes são permitidas apenas para ADMIN.
- A avaliação de filmes é permitida para qualquer usuário logado (CLIENT ou ADMIN).
- A avaliação guarda uma nota de 0 a 5 por usuário e filme; uma nova avaliação do mesmo usuário substitui a anterior.
- Sempre que uma nota é registrada, o filme guarda a média das notas (`score`) e a quantidade de avaliações (`count`).

## 📦 Carga Inicial do Banco de Dados

A aplicação carrega automaticamente 29 filmes, 5 avaliações e 6 usuários, todos com a senha `123456`:

| Usuário | Papel |
|---|---|
| `alex@gmail.com` | CLIENT |
| `maria@gmail.com` | CLIENT, ADMIN |
| `joao@gmail.com` | CLIENT |
| `ana@gmail.com` | CLIENT |
| `lucia@gmail.com` | CLIENT |
| `joaquim@gmail.com` | CLIENT |

## 🔎 Funcionalidades

### Login

| Método | Endpoint | Acesso |
|---|---|---|
| POST | /oauth2/token | Cliente da aplicação (Basic) |

### Filmes

| Método | Endpoint | Acesso |
|---|---|---|
| GET | /movies | Público |
| GET | /movies/{movieId} | Público |
| POST | /movies | ADMIN |
| PUT | /movies/{movieId} | ADMIN |
| DELETE | /movies/{movieId} | ADMIN |

### Avaliações

| Método | Endpoint | Acesso |
|---|---|---|
| PUT | /scores | CLIENT, ADMIN |

- A listagem de filmes é paginada (`content` e `page`), ordenada pelo id e filtra pelo título no parâmetro `title`, sem diferenciar maiúsculas de minúsculas.
- A avaliação é gravada para o usuário logado e devolve o filme com a média e a quantidade de avaliações atualizadas.

## ⚠️ Tratamento de Exceções

Os erros seguem o formato `ProblemDetail` (RFC 9457).

- `400 Bad Request` — filme inexistente na avaliação, `grant_type` diferente de `password` ou parâmetro obrigatório ausente
- `401 Unauthorized` — token ausente ou inválido, credenciais de login inválidas ou usuário do token inexistente
- `403 Forbidden` — usuário logado sem o papel exigido pela rota
- `404 Not Found` — filme inexistente
- `409 Conflict` — exclusão de filme que tem avaliações
- `422 Unprocessable Content` — erro de validação, com uma entrada por erro na propriedade `errors` (`fieldName` e `message`); um campo pode ter mais de uma
  - filme — `title`: obrigatório, entre 5 e 80 caracteres; `score` e `count`: maiores ou iguais a zero; `image`: obrigatória e com URL válida
  - avaliação — `movieId`: obrigatório; `score`: obrigatório, entre 0 e 5

## 🔐 Segurança

- **Login:** o `POST /oauth2/token` valida o cliente da aplicação (Basic) e o usuário (`username` e `password` no formulário), e devolve um JWT com as claims `username` e `authorities`.

## ⚙️ Variáveis de ambiente

| Variável | Padrão | Uso |
|---|---|---|
| `CLIENT_ID` | `myclientid` | Id do cliente da aplicação no login |
| `CLIENT_SECRET` | `myclientsecret` | Segredo do cliente da aplicação no login |
| `JWT_DURATION` | `86400` | Duração do token, em segundos |
| `CORS_ORIGINS` | `http://localhost:3000,http://localhost:5173` | Origens liberadas para o front-end |

Os valores padrão de `CLIENT_ID` e `CLIENT_SECRET` são os do ambiente do Postman do curso.

## 🚀 Como Executar

1. Clonar o repositório
2. Importar o projeto em sua IDE como projeto Maven
3. Executar a classe principal da aplicação (anotada com `@SpringBootApplication`) pela IDE ou via terminal:

```bash
./mvnw spring-boot:run
```

## ✅ Validação

Após executar a aplicação, o banco de dados pode ser acessado via H2 Console para verificação das tabelas e dados inseridos.

- URL: http://localhost:8080/h2-console
- JDBC URL: `jdbc:h2:mem:testdb`
- Usuário: sa
- Senha: (vazia)

## 🧪 Testes

Os nomes dos testes unitários dos services vêm do projeto base do desafio, e a implementação é deste projeto. Os testes rodam sem o contexto do Spring:

- **MovieServiceTest:** listagem paginada, consulta por id, inserção, atualização e exclusão, com filme inexistente (404) e filme com avaliações (409)
- **ScoreServiceTest:** registro da avaliação com a média e a quantidade atualizadas, e filme inexistente (400)
- **UserServiceTest:** usuário logado e carga do usuário no login, com usuário inexistente

```bash
./mvnw clean verify
```

## 📊 Relatório de Cobertura

O relatório do JaCoCo é gerado no `verify` em:

```
target/site/jacoco/index.html
```

A medição cobre os services do desafio (`MovieService`, `ScoreService` e `UserService`).

## 📌 Exemplos de Requisições

### Login

```http
POST /oauth2/token
Authorization: Basic bXljbGllbnRpZDpteWNsaWVudHNlY3JldA==
Content-Type: application/x-www-form-urlencoded
```

```
grant_type=password&username=maria@gmail.com&password=123456
```

Nas requisições seguintes, o token vai no cabeçalho `Authorization: Bearer <access_token>`.

### Listagem paginada de filmes por título

```http
GET /movies?title=star&page=0&size=12
```

### Consulta de filme

```http
GET /movies/1
```

### Inserção de filme (ADMIN)

```http
POST /movies
```

```json
{
  "title": "Test Movie",
  "score": 0.0,
  "count": 0,
  "image": "https://www.themoviedb.org/t/p/w533_and_h300_bestv2/jBJWaqoSCiARWtfV0GlqHrcdidd.jpg"
}
```

### Avaliação de filme (CLIENT ou ADMIN)

```http
PUT /scores
```

```json
{
  "movieId": 1,
  "score": 4.5
}
```
