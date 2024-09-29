# Projeto de Gerenciamento de Vídeos

Este projeto é uma aplicação Java baseada em Spring Boot que permite o gerenciamento de vídeos e categorias. O sistema oferece funcionalidades para cadastrar, listar, buscar, modificar e remover vídeos e categorias.

## Funcionalidades

- **Cadastro de Vídeos**: Permite adicionar novos vídeos à aplicação, associando-os a uma categoria.
- **Listagem de Vídeos**: Lista todos os vídeos cadastrados, com opções de filtragem por título e categoria.
- **Busca por ID**: Possui a funcionalidade de buscar vídeos ou categorias pelo ID.
- **Atualização de Vídeos**: Permite modificar as informações de vídeos existentes.
- **Remoção de Vídeos e Categorias**: Possui funcionalidade para remover vídeos e categorias da base de dados.

## Tecnologias Utilizadas

- **Java**: Linguagem de programação utilizada.
- **Spring Boot**: Framework para construção de aplicações web.
- **JPA (Java Persistence API)**: Para interagir com a base de dados.
- **JUnit**: Para testes unitários.
- **Mockito**: Para simular objetos em testes unitários.
- **Jakarta Validation**: Para validação de dados.

## Estrutura do Projeto

- `src/main/java/com/semana/demo`: Contém o código-fonte da aplicação.
  - `categorias`: Pacote responsável pelas funcionalidades relacionadas a categorias.
  - `videos`: Pacote responsável pelas funcionalidades relacionadas a vídeos.
  - `controller`: Pacote que contém os controladores que gerenciam as requisições HTTP.
  - `exceptions`: Pacote para tratar exceções personalizadas.
- `src/test/java/com/semana/demo`: Contém os testes unitários da aplicação.

## Como Executar o Projeto

1. Clone este repositório em sua máquina local:
   ```bash
   git clone https://github.com/seu_usuario/nome_do_repositorio.git
   cd nome_do_repositorio
  ´´´
2. Abra o projeto em sua IDE de preferência (como IntelliJ ou Eclipse).

3. Certifique-se de que você tenha o JDK 17 ou superior instalado.

4 . Execute a aplicação usando o seguinte comando Maven:

  ```bash
  mvn spring-boot:run
  ```
5. Acesse a aplicação no navegador em http://localhost:8080.

##
Testes

Os testes unitários podem ser executados utilizando o Maven. Para isso, utilize o seguinte comando:
  ```bash
  mvn test
  ```

##Contribuição

Contribuições são bem-vindas! Sinta-se à vontade para abrir issues ou pull requests.



