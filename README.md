# Documentação do Projeto

## Introdução
### Visão geral do projeto

O projeto tem como objetivo demonstrar os conhecimentos em Java e REST APIS por meio de um desafio proposto pelo Dental Simples.

### Objetivos e escopo
Executar e desenvolver uma aplicação SpringBoot utilizando tecnologias de Hibernate, JUnit, Swagger. Lombok e Docker. Além da criação de recursos de README, JavaDOC e utilização de boas práticas de desenvolvimento tais como Testes Unitários.

### Arquitetura
O sistema é desenvolvido utilizando a arquitetura MVC (Model-View-Controller) 
e segue as melhores práticas de desenvolvimento web. 
A aplicação é construída com o framework Springboot, que é baseado em Java e utiliza o padrão de projeto Convention over Configuration o que permite um ganho de produtividade.

A arquitetura do sistema é composta pelos seguintes componentes principais:
- Controladores (Controllers): Responsáveis por receber as requisições HTTP, processá-las e direcionar as ações para os respectivos serviços.
- Serviços (Services): Lidam com a lógica de negócio da aplicação, realizando operações como criação, atualização e exclusão de tarefas, atribuição de responsáveis, etc.
- Modelos (Models): Divididos entre Entity e DTO. As Entities representam as entidades do sistema, como Profissional e Contato e são responsáveis pela persistência dos dados no banco de dados. Os DTOS são responsáveis por agrupar um conjunto de atributos numa classe e facilitar o transporte dos dados entre diferentes componentes de um sistema.

## Instalação e Configuração
### Requisitos do sistema
- Docker
- Java 17+
- Postgres ( opcional, já que podemos usar um container Docker para disponibiilizar uma instancia do banco de dados )
- Maven 3.9.2 (opcional)

### Procedimentos de instalação
- Faça o download e instale o Java JDK a partir do site oficial da Oracle.
- Instale a IDE sua preferência (Intellij, Ecplise ou VsCode por exemplo)
- Caso prefira, instalar o banco de dados Postgres


### Configuração do ambiente de desenvolvimento
1. Clone o repositório do projeto do GitHub: `https://github.com/pvrsouza/desafio-simples-dental`
2. Acesse o diretório `desafio-simples-dental`
3. Caso esteja usando um banco de dados diferente do disponibilizado no docker, configure as informações de conexão com o banco de dados no arquivo `application.properties`. Caso contrário o projeto já encontra-se configurado para apontar para a base de desenvovlimento do container docker.
4. Execute o comando `.\mvnw clean package` para gerar o pacote  `.jar` da aplicação.
5. Execute o comando `docker compose up -d` para executar os containers em modo deamon.


### Configuração do banco de dados
O banco de dados de desenvolvimento já será disponibilizado através do container docker que está pré-configurado no `docker-compose.yaml`.

No docker-compose temos alguns pontos de destaque:

1. Credenciais de acesso ao banco

```
    environment:
      - POSTGRES_USER=postgres
      - POSTGRES_PASSWORD=postgres
```
2. Scripts de dados, localizados na pasta `./init-script/` do porjeto, que serão incluidos automaticamente ao subir o container dokcer:

```
    volumes:
  - ./postgres-data:/var/lib/postgresql/data
   # copia o script para criar as tabelas
  - ./init-scripts/01_ddl_initial_script.sql:/docker-entrypoint-initdb.d/01_ddl_initial_script.sql
   # copia o script para preencher as tabelas com dados
  - ./init-scripts/02_dml_initial_script.sql:/docker-entrypoint-initdb.d/02_dml_initial_script.sql
```

## Guia de Desenvolvimento
### Estrutura do projeto
- `src/main/java/br/com/test/simplesdental/controllers`: Contém os controladores da aplicação.
- `src/main/java/br/com/test/simplesdental/enums`: Contém os Enums da aplicação.
- `src/main/java/br/com/test/simplesdental/exceptions`: Contém as exceptions e o controlador genérico de exceptions da aplicação.
- `src/main/java/br/com/test/simplesdental/services`: Contém os serviços da aplicação.
- `src/main/java/br/com/test/simplesdental/repository`: Contém os repository da aplicação.
- `src/main/java/br/com/test/simplesdental/model`: Contém as entities e dtos da aplicação.
- `src/main/java/br/com/test/simplesdental/exceptions`: Contém as visualizações da aplicação.
- `src/main/java/br/com/test/simplesdental/swagger`: Contem uma classe de constantes centralizadas para apoio na documentação.
- `src/main/java/resources`: Contém arquivos de recursos, como arquivos de configuração

### Docker

### Comandos utilitários. 
Executar os comandos no dirétorio raiz do projeto, onde o `docker-compose.yaml` está presente.
1. Disponibilizar somente o container do banco de dados
```bash
docker compose up postgres
```
2. Acessar o container de banco de dados para listar `databases` e `tables`
```bash
#caso o container não estiver rodando exetucar o comando
docker compose up postgres

#listar os containers em execução
docker ps

#executar em modo interativo o shell do container que foi informado
docker exec -it <id-do-container> psql -U postgres

#lista os databases
postgres=# \l

#conecta no database
\c postgres

#lista as tabelas
\dt

```
3. Parar a execução dos containers
```bash
docker compose down
```

### docker-compose.yaml

A configuração criada permite que a subida dos containers sejam feitas de forma sincronizada. Primeiro é disponibilizado o container do banco de dados `postgres` e após a execução garantir que 
o banco está disponível para novas conexões ai o docker inicia a subir o container da api `desafio-simples-dental`.

Esse comportamento é garantido pelas configurações abaixo: 

```yaml
version: '3'
services:
  postgres:
    ...
    healthcheck:
      test: ["CMD", "pg_isready", "-U", "postgres", "-h", "postgres"]
      interval: 10s
      timeout: 5s
      retries: 3

  desafio-simples-dental:
    ...
    depends_on:
      postgres:
        condition: service_healthy
```


## API
### Swagger
http://localhost:8080/swagger-ui/index.html?url=/v3/api-docs


## Gerenciamento de Erros e Exceções
### Tratamento de erros e exceções

Os erros das chamadas de api basicamente são capturados pela implementação da classe `RestExceptionHandler` decorada pelo `@RestControllerAdvice`.

Com isso é possível padronizar as respostas devolvidas ao cliente tornando a API mais coesa no que tange os retornos para erros encontrados no processamento. Sendo assim, ficou definido
que para os erros a api retornar um objeto contendo informações básicas e amigáveis juntamente com o respectivo código HTTP:

Exemplo de retorno:
```json
{
  "codigo": "Código para ajudar na identificação do erro",
  "descricao": "Mensagem do erro"
}
```
Afim de facilitar e centralizar os códigos e mensagens dos erros foi criada uma classe que armazena essas informações. Nela é possível
definir o Código, Mensagem e HttpStatus para cada erro conforme necessidade.

```java
public enum ApiErros {
    API9999("Ocorreu um erro não mapeado", HttpStatus.INTERNAL_SERVER_ERROR),
    API0001("Profissional não encontrado com o Id informado", HttpStatus.NOT_FOUND);

    @Getter
    private String descricao;

    @Getter
    private HttpStatus httpStatusCode;


    ApiErros(String description, HttpStatus httpStatusCode) {
        this.descricao = description;
        this.httpStatusCode = httpStatusCode;
    }

    ApiErros(String description) {
        this.descricao = description;
    }

    public String getCode() {
        return this.name();
    }
}
```

Com isso as classes de exception ficam mais flexíveis e de fácil entendimento tais como o exemplo abaixo:

```java
// classe genérica de exceptions relacianadas a chamadas da API
public class ApiException extends RuntimeException{

    @Getter
    private ApiErros error;

    public ApiException(ApiErros error){
        super(error.getDescricao());
        this.error = error;
    }
}
```

```java
// implementação com a semantica especifica para erros relacioandos a registros profissionais não encontrados
public class NotFoundException extends ApiException {
    public NotFoundException(ApiErros error) {
        super(error);
    }
}
```

```java
// Exemplo de utilização da exception NotFoundException
@Service
@RequiredArgsConstructor
public class ProfissionalService {

    private final ProfessionalRepository professionalRepository;
    

    public ProfessionalEntity getById(String id) throws ApiException {
        return this.professionalRepository.findById(Integer.valueOf(id))
                .orElseThrow(() -> new NotFoundException(ApiErros.API0001));
    }
}
```

```java
//exemplo de como pode ser tratado pelo RestControllerAdvice
@Slf4j
@RestControllerAdvice
public class RestExceptionHandler {

    private ResponseEntity<Object> handleExceptionInternal(Exception ex, ErrorResponse errorResponse, HttpStatus httpStatus) {
        // registra o log da exeception para fins de investigação
        logException(ex);
        // devolver o response com o objeto padronizado
        return new ResponseEntity<>(errorResponse, httpStatus);
    }
    
    //captura qualquer exception que herda de ApiException
    @ExceptionHandler(value = {ApiException.class})
    protected ResponseEntity<Object> handleBusinessException(ApiException ex, WebRequest request) {
        logException(ex);
        ErrorResponse errorResponse = ErrorResponse.builder()
                .codigo(ex.getError().getCode())
                .descricao(ex.getError().getDescricao())
                .build();
        // já defini o HttpStatus definido nas constantes de ApiErros.java
        return handleExceptionInternal(ex, errorResponse, ex.getError().getHttpStatusCode());
    }


    private static void logException(Exception ex) {
        log.error("Exception: {}", ex.getMessage());
        ex.printStackTrace();
    }
}
```



## Referências
- Documentação do desafio https://docs.google.com/document/d/1PQOAqM1Wmk_TdLmYzli2eS_CwRc4Z4QlrtMzDQkYd7k/edit#heading=h.r82dw4bdrua6