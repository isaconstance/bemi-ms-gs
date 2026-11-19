# BEMI – Rede de cuidado com alerta de risco para idosos

**Global Solution 2026 – 2º Semestre**  
**3º SIPF – Microservice and Web Engineering & IT Services**  
**FIAP – Faculdade de Informática e Administração Paulista**  
**Professora:** Aparecida F. Castello Rosa

## Integrantes

- Isabella Constance – RM 559219
- Daniela Grego – RM 555808

## 1. Sobre o projeto

O envelhecimento da população traz desafios relacionados à segurança, ao acompanhamento e ao cuidado de pessoas idosas. Idosos que moram sozinhos ou que passam longos períodos sem contato com familiares podem enfrentar situações de risco sem que alguém perceba rapidamente.

O BEMI foi desenvolvido com o objetivo de contribuir para esse cenário, oferecendo uma plataforma de apoio ao acompanhamento de idosos e ao registro de alertas relacionados à sua segurança.

## 2. Solução proposta

A solução é composta por dois microsserviços principais:

- **MS-Idoso:** responsável pelo cadastro, consulta, atualização e gerenciamento das informações e do status dos idosos.
- **MS-Alerta:** responsável pelo registro e gerenciamento de alertas relacionados aos idosos, permitindo acompanhar e resolver ocorrências.

Os microsserviços se comunicam para que o registro de alertas também possa refletir alterações no status do idoso acompanhado.

## 3. Estrutura do projeto

O projeto está organizado em um único repositório, dividido nos seguintes módulos:

| Módulo | Responsabilidade |
|---|---|
| `eureka-server` | Registro e descoberta dos microsserviços |
| `api-gateway` | Ponto de entrada para as requisições |
| `ms-idoso` | Gerenciamento dos idosos |
| `ms-alerta` | Gerenciamento dos alertas |
| `mysql_846` | Configuração do banco de dados MySQL |

## 4. Pré-requisitos

Antes de executar o projeto, é necessário ter instalado:

- Git;
- JDK 25;
- IntelliJ IDEA;
- Docker Desktop com Docker Compose;
- Acesso à internet para baixar as dependências do projeto.

## 5. Como obter o projeto

Clone o repositório:

```bash
git clone https://github.com/isaconstance/bemi-ms-gs.git
```

Entre na pasta do projeto:

```bash
cd bemi-ms-gs
```

Abra o projeto no IntelliJ IDEA e aguarde o carregamento e a sincronização das dependências do Maven.

## 6. Configuração e execução

A execução deve seguir a ordem abaixo para garantir que os serviços necessários estejam disponíveis.

### 6.1. Iniciar o banco de dados

Na pasta raiz do projeto, execute:

```bash
docker compose -f mysql_846/docker-compose.yml up -d
```

Esse comando inicia o container do MySQL.

A configuração utiliza:

- **Porta:** `3306`
- **Usuário:** `root`
- **Senha:** `root`

Os bancos de dados utilizados pelos microsserviços são `db_idoso` e `db_alerta`. A criação e a carga inicial das estruturas são realizadas pelas migrations do Flyway durante a inicialização dos microsserviços.

### 6.2. Iniciar o Eureka Server

1. No IntelliJ IDEA, localize o módulo `eureka-server`.
2. Abra a classe principal da aplicação.
3. Clique no botão **Run (▶)** ao lado do método `main`.

Aguarde a inicialização do servidor antes de continuar.

### 6.3. Iniciar o MS-Idoso

1. Localize o módulo `ms-idoso`.
2. Abra a classe principal da aplicação.
3. Na configuração de execução do IntelliJ, selecione o perfil `dev` em **Active profiles**.
4. Clique em **Run (▶)**.

### 6.4. Iniciar o MS-Alerta

1. Localize o módulo `ms-alerta`.
2. Abra a classe principal da aplicação.
3. Na configuração de execução do IntelliJ, selecione o perfil `dev` em **Active profiles**.
4. Clique em **Run (▶)**.

### 6.5. Iniciar o API Gateway

1. Localize o módulo `api-gateway`.
2. Abra a classe principal da aplicação.
3. Clique no botão **Run (▶)**.

**Importante:** cada serviço deve permanecer em execução. Inicie-os na ordem apresentada e aguarde a inicialização de cada um antes de executar o próximo.

## 7. Verificação da execução

Após iniciar os serviços, é possível verificar se o ambiente está funcionando pelos seguintes endereços:

### Eureka Server

http://localhost:8761

A página permite visualizar os serviços registrados no servidor de descoberta.

### Swagger – MS-Idoso

http://localhost:8082/ms-idoso/swagger-ui/index.html

### Swagger – MS-Alerta

http://localhost:8082/ms-alerta/swagger-ui/index.html

O Swagger permite visualizar a documentação das APIs e executar requisições diretamente pelo navegador.

## 8. Como testar as APIs

Os testes das funcionalidades podem ser realizados pelas interfaces Swagger disponibilizadas pelo API Gateway.

Em cada documentação:

1. Localize o endpoint que deseja testar.
2. Selecione a operação desejada.
3. Clique em **Try it out**.
4. Preencha os parâmetros ou o corpo da requisição, conforme indicado pela documentação.
5. Clique em **Execute** para enviar a requisição.
6. Confira a resposta retornada pela API.

### Principais operações – MS-Idoso

| Método | Endpoint | Descrição |
|---|---|---|
| GET | `/api/v1/idosos` | Listar idosos |
| GET | `/api/v1/idosos/{id}` | Consultar idoso por ID |
| POST | `/api/v1/idosos` | Cadastrar idoso |
| PUT | `/api/v1/idosos/{id}` | Atualizar dados do idoso |
| DELETE | `/api/v1/idosos/{id}` | Excluir idoso |
| PUT | `/api/v1/idosos/{id}/status?status=ALERTA` | Atualizar status do idoso |

### Principais operações – MS-Alerta

| Método | Endpoint | Descrição |
|---|---|---|
| GET | `/api/v1/alertas` | Listar alertas |
| GET | `/api/v1/alertas/{id}` | Consultar alerta por ID |
| POST | `/api/v1/alertas` | Registrar alerta |
| PUT | `/api/v1/alertas/{id}` | Atualizar alerta |
| DELETE | `/api/v1/alertas/{id}` | Excluir alerta |
| PATCH | `/api/v1/alertas/{id}/acompanhar` | Acompanhar alerta |
| PATCH | `/api/v1/alertas/{id}/resolver` | Resolver alerta |

Para as operações que exigem parâmetros ou dados no corpo da requisição, consulte os modelos apresentados no próprio Swagger.

## 9. Encerramento da aplicação

Para encerrar a aplicação:

1. Interrompa cada microsserviço pelo botão **Stop (■)** do IntelliJ IDEA.
2. Na pasta raiz do projeto, execute:

```bash
docker compose -f mysql_846/docker-compose.yml down
```

Esse comando encerra e remove o container do MySQL, preservando os dados armazenados no volume configurado.

## 10. Repositório

Código-fonte e arquivos do projeto:

https://github.com/isaconstance/bemi-ms-gs
