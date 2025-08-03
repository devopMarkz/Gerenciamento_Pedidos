# Sistema de Gerenciamento de Pedidos com Microsserviços

Este projeto implementa um sistema de gerenciamento de pedidos utilizando uma arquitetura de microsserviços, com foco em escalabilidade, resiliência e segurança. A solução incorpora um Gateway API para roteamento e segurança, um servidor de autenticação para gerenciamento de usuários e JWT, um servidor Eureka para descoberta de serviços, e microsserviços dedicados para clientes, produtos e pedidos.

## 🚀 Execução Rápida com Docker Compose

Para facilitar a execução de todos os microsserviços e seus respectivos bancos de dados, este projeto inclui um arquivo `docker-compose.yml`. Com ele, é possível subir toda a infraestrutura com um único comando.

### Pré-requisitos

- Docker Desktop instalado

### Como usar

1.  Navegue até o diretório raiz do projeto onde o arquivo `docker-compose.yml` está localizado.
2.  Execute o seguinte comando para construir e iniciar todos os serviços:

    ```bash
    docker-compose up --build
    ```

    O `--build` garante que as imagens sejam construídas a partir do código-fonte mais recente. Se as imagens já estiverem construídas e você quiser apenas iniciá-las, pode usar:

    ```bash
    docker-compose up
    ```

3.  Para parar e remover os contêineres, redes e volumes criados pelo `docker-compose`:

    ```bash
    docker-compose down
    ```

## 🐳 Integração Contínua e Docker Hub

Este projeto está configurado com um pipeline de Integração Contínua (CI) que automatiza a construção e o push das imagens Docker para o Docker Hub. Sempre que há um push para a branch `main`, o workflow `Docker Images CI` é acionado, garantindo que as imagens mais recentes dos microsserviços estejam disponíveis no Docker Hub com a tag `latest`.

Você pode acessar as imagens diretamente do Docker Hub utilizando a tag `latest` para cada serviço:

- `devopmarkz/eureka-server:latest`
- `devopmarkz/auth-server:latest`
- `devopmarkz/gestao-pedidos-api-gateway:latest`
- `devopmarkz/cliente-service:latest`
- `devopmarkz/produto-service:latest`
- `devopmarkz/pedido-service:latest`

## 🏗️ Arquitetura

O sistema é composto pelos seguintes microsserviços:

- **Eureka Server**: Servidor de descoberta de serviços.
- **Auth Server**: Serviço de autenticação e autorização com Spring Security e JWT.
- **API Gateway**: Ponto de entrada unificado para os microsserviços, responsável por roteamento, segurança e balanceamento de carga. O roteamento é feito com base em prefixos de URL, como `/cliente-service/**`, `/produto-service/**`, etc., que são mapeados para os respectivos microsserviços. Além disso, o Gateway garante que os microsserviços só possam ser acessados através dele, injetando um cabeçalho secreto (`X-Secret-Access`) nas requisições que encaminha, que é validado pelos microsserviços para permitir o acesso.
- **Cliente Service**: Gerencia informações de clientes.
- **Produto Service**: Gerencia informações de produtos e estoque.
- **Pedido Service**: Gerencia o ciclo de vida dos pedidos.

## 🔄 Gerenciamento de Transações Distribuídas (Padrão SAGA)

Para garantir a consistência dos dados em um ambiente de microsserviços, onde transações podem envolver múltiplos serviços, este projeto utiliza o padrão SAGA. Especificamente, o `Pedido Service` orquestra as operações de criação de pedidos e a atualização de estoque no `Produto Service`. Caso ocorra uma falha em qualquer etapa da transação (por exemplo, estoque insuficiente ou cancelamento de pedido), o padrão SAGA permite a execução de transações compensatórias para reverter as operações já realizadas, garantindo a integridade dos dados.

Por exemplo, se um pedido for cancelado após a dedução do estoque, uma transação compensatória será acionada para realizar o "rollback" do estoque no `Produto Service`, devolvendo os itens ao inventário.

## 🛠️ Tecnologias Utilizadas

- Java 21
- Spring Boot 3.x
- Spring Cloud (Eureka, Gateway, OpenFeign)
- Spring Security
- JWT (JSON Web Tokens)
- Maven
- PostgreSQL (para Auth Service, Cliente Service, Pedido Service, Produto Service)
- Flyway (para migrações de banco de dados)
- H2 Database (para testes e desenvolvimento local em alguns serviços)
- Docker & Docker Compose

## ⚙️ Configuração e Execução Manual

Caso prefira executar os serviços manualmente (sem Docker), siga os passos abaixo:

### Pré-requisitos

- Java Development Kit (JDK) 21 ou superior
- Maven 3.x ou superior
- PostgreSQL (para os bancos de dados)

### Banco de Dados

Cada microsserviço que persiste dados (Auth, Cliente, Produto, Pedido) utiliza PostgreSQL. Você pode configurar as credenciais do banco de dados nos arquivos `application.yaml` ou `application-dev.yaml` de cada serviço. As migrações de banco de dados são gerenciadas pelo Flyway.

Exemplo de configuração no `application.yaml`:

```yaml
spring:
  datasource:
    url: jdbc:postgresql://localhost:5432/nome_do_banco
    username: seu_usuario
    password: sua_senha
  jpa:
    hibernate:
      ddl-auto: update
    properties:
      hibernate:
        dialect: org.hibernate.dialect.PostgreSQLDialect
```

### Executando os Microsserviços

É recomendável iniciar os serviços na seguinte ordem:

1.  **Eureka Server**
2.  **Auth Server**
3.  **API Gateway**
4.  **Cliente Service**
5.  **Produto Service**
6.  **Pedido Service**

Para cada serviço, navegue até o diretório raiz do microsserviço e execute o comando Maven:

```bash
mvn spring-boot:run
```

Alternativamente, você pode importar o projeto em sua IDE (IntelliJ IDEA, Eclipse) e executar as classes principais de cada aplicação.

## 📋 Detalhes dos Microsserviços

### 🔄 Padrão de Roteamento via Gateway

Todos os microsserviços são acessados **exclusivamente através do API Gateway**, disponível na porta `8765`. Para isso, cada serviço exige um **prefixo de rota**, conforme exemplo abaixo:

| Serviço         | Prefixo no Gateway           | Exemplo de URL                                 |
|-----------------|------------------------------|------------------------------------------------|
| Cliente Service | `/cliente-service/**`        | `http://localhost:8765/cliente-service/clientes` |
| Produto Service | `/produto-service/**`        | `http://localhost:8765/produto-service/produtos` |
| Pedido Service  | `/pedido-service/**`         | `http://localhost:8765/pedido-service/pedidos` |

Esse padrão é configurado no gateway com `.stripPrefix(1)` para remover o prefixo antes de encaminhar ao serviço via Eureka (load balancer).

---

### 🔧 Eureka Server
- **Porta**: `8761`
- **Função**: Registro e descoberta de serviços.
- **URL**: `http://localhost:8761`

---

### 🔐 Auth Server
- **Porta**: `9090`
- **Função**: Autenticação de usuários, geração e validação de JWTs.
- **Banco de Dados**: PostgreSQL.
- **URL Token**: `http://localhost:9090/auth/login`
- **URL Criação de Usuário**: `http://localhost:9090/usuarios`


---

### 🚪 API Gateway
- **Porta**: `8765`
- **Função**: Roteamento inteligente de requisições + segurança (filtros de autenticação e autorização).
- **Configuração principal**:
  - Prefixos: `/cliente-service`, `/produto-service`, `/pedido-service`
  - StripPrefix: Remove o prefixo antes de encaminhar para o respectivo serviço via `lb://<service-name>`

---

### 👤 Cliente Service
- **Porta interna**: `8081`
- **Função**: CRUD de clientes.
- **Banco**: PostgreSQL.
- **URL via Gateway**: `http://localhost:8765/cliente-service/clientes`

---

### 📦 Produto Service
- **Porta interna**: `8080`
- **Função**: CRUD de produtos e controle de estoque.
- **Banco**: PostgreSQL.
- **URL via Gateway**: `http://localhost:8765/produto-service/produtos`

---

### 🧾 Pedido Service
- **Porta interna**: `8082`
- **Função**: Gerenciamento de pedidos, integrando com Cliente e Produto.
- **Banco**: PostgreSQL.
- **URL via Gateway**: `http://localhost:8765/pedido-service/pedidos`

---

## ✅ Observações

- As rotas internas não são acessíveis diretamente (ex: `localhost:8080/produtos` está bloqueado).
- Toda comunicação externa deve passar pelo API Gateway, que valida autenticação, aplica políticas e redireciona para o serviço correto.
- O sistema utiliza Eureka para descoberta de serviços e balanceamento de carga.

## 🔐 Segurança

A segurança é implementada utilizando Spring Security e JWT. O `Auth Server` é responsável por emitir os tokens JWT após a autenticação bem-sucedida. O `API Gateway` intercepta as requisições, valida os JWTs e garante que apenas usuários autorizados acessem os recursos dos microsserviços.

## 🤝 Contribuição

Contribuições são bem-vindas! Sinta-se à vontade para abrir issues e pull requests.

## 📄 Licença

Este projeto está licenciado sob a licença MIT. Veja o arquivo `LICENSE` para mais detalhes.

