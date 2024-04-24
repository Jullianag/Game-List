# Game List 

# Sobre o projeto

Este projeto é uma aplicação back end desenvolvida com base no Intensivão Java Spring do curso **DevSuperior** do professor Nélio Alves. 

O Game List tem como referência o projeto [DSList](https://github.com/devsuperior/dslist-backend?tab=readme-ov-file) do aulão citado, porém com várias implementações diferentes: 
- Criação de endpoints para New Game, Update Game e Delete Game
- Tratamento de exceções
- Controle de acesso com login
- Validações na camada dto
- Novos repositories com consultas SQL e JPQL

O usuário cadastrado como administrador tem acesso as modificações do Game, enqquanto o usuário cliente faz as consultas básicas como: consultar games (todos e por id), gêneros e informações da sua conta. Portanto
o projeto consiste em um CRUD com controle de acesso para as funções mais delicadas.

## Modelo conceitual
![Modelo Conceitual](https://github.com/Jullianag/Game-List/blob/main/assets/Class%20Diagram%20games.png)

## Alguns endpoints no Postman das novas funcionalidades
![Demonstração 1](https://github.com/Jullianag/Game-List/blob/main/assets/Captura%20de%20tela%202024-04-24%20165907.png)

![Demonstração 2](https://github.com/Jullianag/Game-List/blob/main/assets/Captura%20de%20tela%202024-04-24%20170024.png)

![Demonstração 3](https://github.com/Jullianag/Game-List/blob/main/assets/Captura%20de%20tela%202024-04-24%20170125.png)

![Demonstração 4](https://github.com/Jullianag/Game-List/blob/main/assets/Captura%20de%20tela%202024-04-24%20170318.png)

## Collection Postman
[Arquivo JSON](https://github.com/Jullianag/commerce-JAVA/blob/main/assets/commerce.postman_collection.json)

# Tecnologias utilizadas
## Back end
- Java
- Spring Boot
- JPA / Hibernate
- Maven

## Banco de dados:
- Banco de dados: teste H2

# Como executar o projeto

```bash
# clonar repositório
git clone https://github.com/Jullianag/Game-List

# entrar na pasta do projeto back end
cd backend

# executar o projeto
./mvnw spring-boot:run
```

## Back end
Pré-requisitos: Java 21

# Autor

Julliana Gnecco

https://www.linkedin.com/in/julliana-gnecco/
