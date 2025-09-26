> 🇧🇷 **Versão em Português** | 🇬🇧 [Read this page in English](README-en.md)

# 🎵 Game Soundtracks API

API RESTful para catalogar e explorar trilhas sonoras de videojogos. Este projeto foi desenvolvido como parte de um portfólio de estudos, focando em boas práticas de desenvolvimento backend com o ecossistema Spring.

## 📖 Sobre o Projeto

O objetivo da Game Soundtracks API é fornecer um serviço onde usuários possam buscar informações sobre jogos, seus álbuns de trilha sonora, as músicas que os compõem e seus artistas. Também irá incluir funcionalidades de autenticação para que usuários possam salvar suas músicas e álbuns favoritos.

## ✨ Features

- [ ] CRUD completo para Jogos, Álbuns, Músicas e Artistas.
- [ ] Sistema de busca flexível para encontrar músicas por jogo, álbum ou artista.
- [x] Autenticação de usuários via JWT (JSON Web Token).
- [ ] Endpoint para usuários favoritarem suas músicas preferidas.
- [ ] Documentação da API gerada automaticamente e interativa com Swagger UI.

## 🛠️ Tecnologias Utilizadas

Esta API foi construída utilizando as seguintes tecnologias:

- **Linguagem:** [Kotlin](https://kotlinlang.org/) / [Java 21](https://www.oracle.com/java/)
- **Framework Principal:** [Spring Boot 3](https://spring.io/projects/spring-boot)
  - **Spring Web:** Para a construção de endpoints RESTful.
  - **Spring Data JPA:** Para a persistência de dados.
  - **Spring Security:** Para a implementação da autenticação e autorização.
- **Build Tool:** [Gradle](https://gradle.org/)
- **Utilitários:** [Lombok](https://projectlombok.org/)
- **Autenticação:** [JWT (jjwt)](https://github.com/jwtk/jjwt)
- **Banco de Dados:**
  - [PostgreSQL](https://www.postgresql.org/): Para ambiente de produção.
  - [H2 Database](https://www.h2database.com/): Para ambiente de desenvolvimento e testes.
- **Documentação:** [Springdoc OpenAPI (Swagger UI)](https://springdoc.org/)

## 🚀 Como Executar o Projeto

Para executar este projeto localmente, siga os passos abaixo.

### Pré-requisitos

- Java (JDK) 21 ou superior.
- Git.
- (Opcional) Uma instância de PostgreSQL rodando.

### Passos

1. **Clone o repositório:**

    ```bash
    git clone [https://github.com/manojohnsons/game-soundtracks-api.git](https://github.com/manojohnsons/game-soundtracks-api.git)
    cd game-soundtracks-api
    ```

2. **Execute a aplicação:**
    O projeto utiliza o Gradle Wrapper, então você não precisa ter o Gradle instalado na sua máquina.

    ```bash
    # Em sistemas Linux ou macOS
    ./gradlew bootRun

    # Em sistemas Windows
    .\gradlew.bat bootRun
    ```

3. A API estará disponível em `http://localhost:8080`.

## 📚 Documentação da API

Graças ao Springdoc OpenAPI, a documentação completa e interativa da API é gerada automaticamente. Após iniciar a aplicação, você pode acessá-la em:

- **[http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)**

Nesta página, você poderá ver todos os endpoints disponíveis, seus parâmetros, os formatos de requisição/resposta e até mesmo testar a API diretamente do seu navegador.

## 📝 Licença

Este projeto está sob a licença MIT. Veja o arquivo [LICENSE](LICENSE) para mais detalhes.
