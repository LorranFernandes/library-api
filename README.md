# 📚 Library API

Uma API RESTful desenvolvida em **Java com Spring Boot** para o gerenciamento de livros, autores e usuários, utilizando **autenticação baseada em JWT (OAuth2)**.

## 🚀 Tecnologias Utilizadas

- Java 17+
- Spring Boot
- Spring Security com OAuth2 Authorization Server
- JWT (JSON Web Token)
- Spring Data JPA
- Hibernate
- PostgreSQL (pode ser adaptado para outros SGBDs)
- Lombok
- Swagger / OpenAPI para documentação

## 🔐 Autenticação e Segurança

A autenticação segue o padrão **OAuth2 Authorization Server**, com suporte a:

- Geração de token JWT via endpoint `/oauth2/token`
- Introspecção de token via `/oauth2/introspect`
- Revogação via `/oauth2/revoke`
- Endpoints protegidos com `@PreAuthorize` baseados em **roles**
- Chaves RSA geradas para assinatura dos tokens

---

# 📚 Sistema de Gerenciamento de Livros

## 📦 Estrutura de Domínio

### Entidades principais
- **Usuario**: Representa o usuário autenticado da plataforma, contendo login, senha e roles.
- **Client**: Credenciais OAuth2 para autenticação do cliente.
- **Autor**: Informações sobre os autores dos livros.
- **Livro**: Contém dados como título, ISBN, gênero, autor, preço e data de publicação.

## 📄 Principais Endpoints (Controlador de Livros)

| Método | Endpoint         | Descrição                          | Autorização         |
|--------|------------------|------------------------------------|---------------------|
| POST   | `/livros`        | Cadastra um novo livro             | OPERADOR, GERENTE   |
| GET    | `/livros/{id}`   | Busca os detalhes de um livro      | OPERADOR, GERENTE   |
| GET    | `/livros`        | Pesquisa por vários parâmetros     | OPERADOR, GERENTE   |
| PUT    | `/livros/{id}`   | Atualiza os dados de um livro      | OPERADOR, GERENTE   |
| DELETE | `/livros/{id}`   | Remove um livro                    | OPERADOR, GERENTE   |

**Parâmetros de pesquisa aceitos:**
- `isbn`
- `nome-autor`
- `titulo`
- `genero`
- `ano-publicacao`

---

## 🔧 Como Executar Localmente

### Pré-requisitos
- Java 17 ou superior
- PostgreSQL ou outro banco de dados compatível
- Maven

### Passos

1. Clone o repositório:
```bash
git clone https://github.com/LorranFernandes/library-api.git
cd library-api
```

2. Configure o arquivo `application.yml` com as credenciais do seu banco de dados e configurações desejadas.

3. Execute a aplicação:
```bash
./mvnw spring-boot:run
```

4. Acesse a documentação interativa no Swagger UI:
```bash
http://localhost:8080/swagger-ui.html
```

## 🧩 Configuração Inicial no Banco de Dados
Antes de gerar o token JWT, é necessário cadastrar manualmente um usuário e um client no banco de dados.

🗄️ Scripts SQL para PostgreSQL
```bash
CREATE EXTENSION IF NOT EXISTS "uuid-ossp";

INSERT INTO usuario
(id, login, senha, email, roles)
VALUES (
uuid_generate_v4(),
'gerente',
'$2a$12$czeY88a82G1m8/FAzPfrtOD/Jwaij86iM5l9npuFemOrXU67VdzIe',
'gerente@email.com',
'{GERENTE}'
);

INSERT INTO client
(id, client_id, client_secret, redirect_uri, scope)
VALUES (
uuid_generate_v4(),
'client-production',
'$2a$12$8jgZk5CAvp90fFcPka8d0eXZ.hFRSTxLueduVyoNDW124.J5G6pb2',
'http://localhost:8080/authorized',
'GERENTE'
);
```

---

## 🔑 Exemplo de Autenticação com Token JWT

```bash
curl --location 'http://localhost:8080/oauth2/token' --header 'Content-Type: application/x-www-form-urlencoded' --data-urlencode 'grant_type=client_credentials' --data-urlencode 'client_id=CLIENT_ID' --data-urlencode 'client_secret=CLIENT_SECRET'
```

---
## 📘 Exemplo de Requisição - Cadastro de Autor

```json
{
  "nome": "Mario",
  "dataNascimento": "2025-03-03",
  "nacionalidade": "brasileira"
}
```

## 📘 Exemplo de Requisição - Cadastro de Livro

```json
{
  "isbn": "978-3-16-148410-0",
  "titulo": "O Senhor dos Anéis",
  "genero": "FANTASIA",
  "preco": 79.90,
  "dataPublicacao": "1954-07-29",
  "idAutor": "UUID-do-autor-existente"
}
```

---

## ✍️ Autor

Desenvolvido por [**Lorran Fernandes**](https://github.com/LorranFernandes)

---
