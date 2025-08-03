# Sistema de Gerenciamento de Pedidos com Microsserviços

Este projeto implementa um sistema de gerenciamento de pedidos utilizando uma arquitetura de microsserviços, com foco em escalabilidade, resiliência e segurança. A solução incorpora um Gateway API para roteamento e segurança, um servidor de autenticação para gerenciamento de usuários e JWT, um servidor Eureka para descoberta de serviços, e microsserviços dedicados para clientes, produtos e pedidos.

## Arquitetura

O sistema é composto pelos seguintes microsserviços:

- **Eureka Server**: Servidor de descoberta de serviços.
- **Auth Server**: Serviço de autenticação e autorização com Spring Security e JWT.
- **API Gateway**: Ponto de entrada unificado para os microsserviços, responsável por roteamento, segurança e balanceamento de carga.
- **Cliente Service**: Gerencia informações de clientes.
- **Produto Service**: Gerencia informações de produtos e estoque.
- **Pedido Service**: Gerencia o ciclo de vida dos pedidos.

## Tecnologias Utilizadas

- Java 21
- Spring Boot 3.x
- Spring Cloud (Eureka, Gateway, OpenFeign)
- Spring Security
- JWT (JSON Web Tokens)
- Maven
- PostgreSQL (para Auth Service, Cliente Service, Pedido Service, Produto Service)
- Flyway (para migrações de banco de dados)
- H2 Database (para testes e desenvolvimento local em alguns serviços)

## Configuração e Execução

Para configurar e executar o projeto localmente, siga os passos abaixo:

### Pré-requisitos

- Java Development Kit (JDK) 21 ou superior
- Maven 3.x ou superior
- Docker e Docker Compose (opcional, para execução dos bancos de dados)

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

## Detalhes dos Microsserviços

### Eureka Server

- **Porta Padrão**: 8761
- **Funcionalidade**: Atua como o servidor de descoberta de serviços, permitindo que os microsserviços se registrem e se descubram mutuamente.

### Auth Server

- **Porta Padrão**: 8080 (ou configurada no `application.yaml`)
- **Funcionalidade**: Responsável pela autenticação de usuários e geração de JWTs. Integra-se com o Spring Security para gerenciar usuários e suas permissões.
- **Banco de Dados**: PostgreSQL (gerencia usuários e roles).

### API Gateway

- **Porta Padrão**: 8081 (ou configurada no `application.yaml`)
- **Funcionalidade**: Roteia as requisições para os microsserviços apropriados. Implementa filtros de segurança para validar JWTs e aplicar políticas de autorização antes de encaminhar as requisições.

### Cliente Service

- **Porta Padrão**: 8082 (ou configurada no `application.yaml`)
- **Funcionalidade**: Gerencia o cadastro, consulta, atualização e exclusão de informações de clientes.
- **Banco de Dados**: PostgreSQL.

### Produto Service

- **Porta Padrão**: 8083 (ou configurada no `application.yaml`)
- **Funcionalidade**: Gerencia o cadastro, consulta, atualização e exclusão de informações de produtos, incluindo controle de estoque.
- **Banco de Dados**: PostgreSQL.

### Pedido Service

- **Porta Padrão**: 8084 (ou configurada no `application.yaml`)
- **Funcionalidade**: Gerencia a criação, consulta e atualização de pedidos, orquestrando chamadas para o Cliente Service e Produto Service.
- **Banco de Dados**: PostgreSQL.

## Segurança

A segurança é implementada utilizando Spring Security e JWT. O `Auth Server` é responsável por emitir os tokens JWT após a autenticação bem-sucedida. O `API Gateway` intercepta as requisições, valida os JWTs e garante que apenas usuários autorizados acessem os recursos dos microsserviços.

## Contribuição

Contribuições são bem-vindas! Sinta-se à vontade para abrir issues e pull requests.

## Licença

Este projeto está licenciado sob a licença MIT. Veja o arquivo `LICENSE` para mais detalhes.




## Gerenciamento de Transações Distribuídas (Padrão SAGA)

Para garantir a consistência dos dados em um ambiente de microsserviços, onde transações podem envolver múltiplos serviços, este projeto utiliza o padrão SAGA. Especificamente, o `Pedido Service` orquestra as operações de criação de pedidos e a atualização de estoque no `Produto Service`. Caso ocorra uma falha em qualquer etapa da transação (por exemplo, estoque insuficiente ou cancelamento de pedido), o padrão SAGA permite a execução de transações compensatórias para reverter as operações já realizadas, garantindo a integridade dos dados.

Por exemplo, se um pedido for cancelado após a dedução do estoque, uma transação compensatória será acionada para realizar o "rollback" do estoque no `Produto Service`, devolvendo os itens ao inventário.


