# assemblage-service

Assemblage-service é um projeto que tem como objetivo o controle de uma sessão de votos.

# Pré-Requisitos
## Docker
## Docker compose
## Java 8
## Gradle

# Disponibilizando a infra-estrutura

Você deve ir até a raiz do projeto abrir um terminal e executar o comendo **docker-compose up**, esse comando irá disponibilizar 
a infraestrutura necessária para o projeto.

# Rodando o projeto 

Vá até a raiz do projeto abra um tereminal e execute o comando: **./gradlew build**

Depois que o projeto terminar de buildar, execute: **java -jar build/libs/{jarname}.jar**

Lembre-se de deixar a porta 8080 liberada, Após o serviço subir você pode acessar http://localhost:8080/swagger-ui.html#

# Rodando os testes

Lembre-se é sempre legal rodas os testes da aplicação antes de executa-la para isso, execute: **./gradlew test**
