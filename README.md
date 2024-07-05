# Sistema de Emissão de Cartões de Crédito

Este projeto consiste em dois microservices que juntos gerenciam propostas de crédito e a emissão de cartões de crédito. O `cartoes-ms` é responsável pela criação e gerenciamento das propostas de crédito, enquanto o `emissor-ms` é responsável pela emissão física do cartão e envio de notificações por e-mail.

## Estrutura do Projeto

- **cartoes-ms**: Microservice responsável pelo gerenciamento de propostas de crédito.
- **emissor-ms**: Microservice responsável pela emissão física do cartão e notificação por e-mail.

## Tecnologias Utilizadas

- Java 17
- Spring Boot
- Spring Data JPA
- H2 Database
- Spring Cloud OpenFeign
- Spring Mail
- Maven

## Configuração e Execução

### Pré-requisitos

- JDK 17
- Maven
- Docker (opcional, para ambientes de produção)

### Configuração

1. Clone o repositório
   
2. Configure o e-mail no emissor-ms:
No arquivo emissor-ms/src/main/resources/application.properties, adicione suas credenciais SMTP:
  spring.mail.host=smtp.gmail.com
  spring.mail.port=587
  spring.mail.username=seu-email@gmail.com
  spring.mail.password=sua-senha
  spring.mail.properties.mail.smtp.auth=true
  spring.mail.properties.mail.smtp.starttls.enable=true
  spring.mail.properties.mail.smtp.starttls.required=true

3.Execução:
  3.1. Navegue até o diretório do cartoes-ms e execute o seguinte comando:
    cd cartoes-ms
    mvn spring-boot:run -Dspring-boot.run.profiles=local
  3.2. Abra outro terminal, navegue até o diretório do emissor-ms e execute o comando:
    cd emissor-ms
    mvn spring-boot:run -Dspring-boot.run.profiles=local
   

