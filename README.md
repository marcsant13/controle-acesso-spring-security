# controle-acesso-spring-security
Projeto de autenticação, autorização e controle de acessos usando spring security

# Sistema de Gerenciamento de Permissões (RBAC)

Este projeto implementa um sistema de controle de acesso baseado em roles (RBAC) utilizando Spring Security.  
O objetivo é demonstrar autenticação, autorização, regras de negócio, modelagem de dados e segurança, com foco em backend.

O sistema permite que usuários com a role **ADMIN** gerenciem as permissões de outros usuários, controlando o acesso a rotas específicas da aplicação.

---

## Tecnologias Utilizadas

- Java 17
- Spring Boot
- Spring Security
- Spring Data JPA
- PostgreSQL
- Docker / Docker Compose

---

## Regras de Negócio

- Apenas usuários com **ROLE_ADMIN** podem alterar permissões de outros usuários
- Um usuário **ADMIN** não pode alterar as permissões de outro usuário **ADMIN**
- Um usuário **ADMIN** pode alterar suas próprias permissões
- Um novo usuário é criado **sem nenhuma role atribuída**
- Todas as roles devem ser enviadas **exatamente uma vez** na requisição de atribuição
- O primeiro usuário **ADMIN** é inserido manualmente no banco de dados para fins de teste

---

## Roles Disponíveis

- ADMIN
- ACESSO_ROTA_A
- ACESSO_ROTA_B
- ACESSO_ROTA_C
- ACESSO_ROTA_D
- ACESSO_ROTA_E

---

## Endpoints

### Endpoints Públicos

POST `/auth/login`  
POST `/auth/register`

#### Exemplo de JSON (login e registro)

```json
{
  "id": null
  "username": "usuario.teste",
  "password": "password123"
}
```

## Endpoints Protegidos

| Endpoint | Permissão Necessária |
|----------|----------------------|
| GET /teste/rota-a | ACESSO_ROTA_A |
| GET /teste/rota-b | ACESSO_ROTA_B |
| GET /teste/rota-c | ACESSO_ROTA_C |
| GET /teste/rota-d | ACESSO_ROTA_D |
| GET /teste/rota-e | ACESSO_ROTA_E |
| POST /teste/atribuir-permissoes | ADMIN |

---

## Atribuição de Permissões

**POST** `/teste/atribuir-permissoes`

### Exemplo de Requisição

```json
{
  "idUsuario": 1,
  "listaRoles": [
    { "role": "ADMIN", "autorizado": true },
    { "role": "ACESSO_ROTA_A", "autorizado": true },
    { "role": "ACESSO_ROTA_B", "autorizado": false },
    { "role": "ACESSO_ROTA_C", "autorizado": true },
    { "role": "ACESSO_ROTA_D", "autorizado": false },
    { "role": "ACESSO_ROTA_E", "autorizado": true }
  ]
}
```
## Modelo de Dados

O banco de dados é composto pelas seguintes tabelas:

### tb_usuario

| Campo    | Tipo    | Descrição                  |
|---------|---------|----------------------------|
| id      | PK      | Identificador do usuário   |
| username| varchar | Nome de usuário            |
| password| varchar | Senha criptografada        |

### tb_permissoes

| Campo | Tipo | Descrição              |
|------|------|------------------------|
| id   | PK   | Identificador da role  |
| role | varchar | Nome da role        |

### tb_usuario_role

| Campo        | Tipo    | Descrição                                  |
|-------------|---------|--------------------------------------------|
| id          | PK      | Identificador                              |
| id_usuario  | FK      | Referência para tb_usuario.id              |
| id_permissao| FK      | Referência para tb_permissoes.id           |
| autorizado  | boolean | Define se a permissão está ativa            |

**Observações:**
- Cada usuário possui um registro para cada role na tabela `tb_usuario_role`
- A coluna `autorizado` define se o usuário possui ou não determinada permissão
- O controle de acesso é realizado exclusivamente no backend

## Docker Compose

O projeto utiliza Docker Compose para subir o banco de dados PostgreSQL e o pgAdmin para gerenciamento.

```yaml
version: "3.8"

services:
  postgres:
    image: postgres:16
    container_name: postgres_local
    restart: always
    environment:
      POSTGRES_USER: admin
      POSTGRES_PASSWORD: admin
    ports:
      - "5432:5432"
    volumes:
      - postgres_data:/var/lib/postgresql/data

  pgadmin:
    image: dpage/pgadmin4
    container_name: pgadmin
    restart: always
    environment:
      PGADMIN_DEFAULT_EMAIL: admin@admin.com
      PGADMIN_DEFAULT_PASSWORD: admin
    ports:
      - "5050:80"
    depends_on:
      - postgres

volumes:
  postgres_data:
```

## Como Executar o Projeto

### Pré-requisitos

Certifique-se de ter instalado:

- **Docker**
- **Java 17** ou superior
- **Git** (opcional, para clonar o repositório)

---

### Passos para execução

#### 1. Clonar o repositório

```bash
git clone https://github.com/seu-usuario/seu-repositorio.git
cd seu-repositorio
```
#### 2. Subir o banco de dados com Docker

```bash
docker compose up -d
```
---

Isso irá subir os seguintes serviços:
- PostgreSQL na porta 5432
- pgAdmin na porta 5050

---

#### 3. Configurar o banco de dados no pgAdmin

- Abra o navegador e acesse:
```bash
http://localhost:5050
```
---

Faça login com as credenciais padrão:
- Email: admin@admin.com
- Senha: admin

Adicione uma nova conexão para o PostgreSQL:
- Host: postgres
- Porta: 5432
- Usuário: admin
- Senha: admin

---

## Objetivo do Projeto

Este projeto foi desenvolvido com foco em:

- **Autenticação e autorização** com Spring Security  
- **Controle de acesso** baseado em roles (RBAC)  
- **Regras de negócio** implementadas no backend  
- **Modelagem relacional** do banco de dados  
- **Boas práticas de segurança** em aplicações web  
- **Estruturação para portfólio backend**, demonstrando habilidades completas em Java, Spring Boot e PostgreSQL
