# 📘 Bethunter API

API desenvolvida em **Java Spring Boot** para um aplicativo mobile de **educação financeira com gamificação**.  
O sistema oferece **aulas**, **tópicos de aprendizado**, **quiz de perguntas e alternativas**, além de mecânicas de **gamificação** como pontos, moedas virtuais e uma **roleta de recompensas**.

---

## 🚀 Tecnologias

- Java 17+
- Spring Boot 3.x
- Spring Security (JWT)
- Maven
- MySQL
- JPA/Hibernate

---

## ⚙️ Configuração do Projeto

### 1. Pré-requisitos
- [Java 17](https://adoptium.net/)
- [Maven](https://maven.apache.org/)
- [MySQL](https://dev.mysql.com/downloads/)

### 2. Clonar o repositório
```bash
git clone https://github.com/seu-usuario/bethunter-api.git
cd bethunter-api
```

### 3. Configurar banco de dados
Crie um banco no MySQL:
```sql
CREATE DATABASE bethunter_db;
```

Atualize o arquivo `application.properties` ou `application.yml`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/bethunter_db
spring.datasource.username=root
spring.datasource.password=senha
spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
```

### 4. Executar a aplicação
```bash
mvn spring-boot:run
```

A API estará disponível em:  
👉 `http://localhost:8080`

---

## 📖 Endpoints Principais

### 🔑 Autenticação
**Login**
```http
POST /auth/login
```

📤 **Request**
```json
{
  "email": "user@email.com",
  "password": "123456"
}
```

📥 **Response**
```json
{
  "token": "eyJhbGciOiJIUzI1NiIsInR..."
}
```

---

### 👤 Usuários
**Buscar usuário por ID**
```http
GET /users/{id}
```

📥 **Response**
```json
{
  "id": "123",
  "email": "user@email.com",
  "name": "John Doe",
  "cellphone": "11999999999",
  "money": 50.00,
  "points": 200
}
```

**Atualizar usuário**
```http
PUT /users/{id}
```

📤 **Request**
```json
{
  "email": "new@email.com",
  "name": "Jane Doe",
  "cellphone": "11888888888",
  "money": 75.00,
  "points": 300
}
```

📥 **Response**
```json
{
  "id": "123",
  "email": "new@email.com",
  "name": "Jane Doe",
  "cellphone": "11888888888",
  "money": 75.00,
  "points": 300
}
```

---

### 📚 Aulas
**Criar aula**
```http
POST /lessons
```

📤 **Request**
```json
{
  "title": "Introdução à Educação Financeira"
}
```

📥 **Response**
```json
{
  "id": "1",
  "title": "Introdução à Educação Financeira"
}
```

**Listar aulas**
```http
GET /lessons
```

📥 **Response**
```json
[
  {
    "id": "1",
    "title": "Introdução à Educação Financeira"
  },
  {
    "id": "2",
    "title": "Investimentos Básicos"
  }
]
```

---

### 🎮 Roleta de Recompensas
**Girar a roleta**
```http
GET /users/roulete
Authorization: Bearer <token>
```

📥 **Response**
```json
{
  "reward": 15.00
}
```

---

## 🛠️ Erros e Exceções

- **401 Unauthorized** → Token inválido ou ausente  
- **404 Not Found** → Recurso não encontrado  
- **400 Bad Request** → Requisição inválida  
- **409 Conflict** → Já respondido (no caso de quizzes)  

Exemplo:
```json
{
  "error": "InvalidToken",
  "message": "Provided token is invalid or expired."
}
```

---

## 📄 Licença
Este projeto está sob a licença MIT.
MIT License

Copyright (c) 2025 BetHunter Team

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
