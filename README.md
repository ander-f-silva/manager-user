# Projeto Consultar Usuários

Foi construida uma api para consultar os dados dos usuários por nome e nome de usuário, paginando 15 registros por página (esta configurada por default, mas é possível informar os dados). Também foi construido um batch  para realizar a carga de um arquivo csv de 8 milhôes de registro.

## Reflexão sobre o problema

Inicialmente criei um projeto principal com objetivo de construir dois módulo, um para relizar a carga do arquivo e outro para expor api. 

Fiz alguns pocs para testar o processo de carga. Inicialmente fiz com golang (leitura do arquivo e gravação na base), este demorou 22 minutos (como não é uma linguagem que não domino, pensei se valeria a pena). Partir para linguagem Java usando Spring Batch que demorou o mesmo tempo. A solução final foi construir um projeto java com código bem minimalista usando poucas libs, usando um simples método man com Leitura do arquivo usando open csv e lib de driver do banco, este processo levou 1 minuto rodando na minha máquina que possui 16 GB e processador intel i7 (em outra máquina o tempo pode mudar) e esta solução que adotei para realizar a carga, para o futuro pode ser usado outra abordagem como um lambda rodando na AWS com arquivo hopedando em S3.

Para disponibilizar a API usei o Spring Boot 2 por entender que já possui stack bem matura e fácil para construir os testes e documentar com swagger, para facilitar ainda mais use a linguagem Kotlin por entender que código ficari menos verboso.

Para banco de dados utilizei o mongo por se tratar de dados que não precisa de uma consistencia rigida e os dados não são estruturados. Outro ponto importante é que me passei nos conceitos BASE e não ACID, ainda mais que estamos trabalhando com dados para leitura.

## Desenho da Arquitetura

![Arquitetura](/images/arquitetura.jpg)

## Tecnologias utilizadas

* Linguagem Java - Versão 1.8 (Oracle 1.8.0_121);

```
java version "1.8.0_121"
Java(TM) SE Runtime Environment (build 1.8.0_121-b13)
Java HotSpot(TM) 64-Bit Server VM (build 25.121-b13, mixed mode)i
```

* Linguagem Kotlin - Versão 1.2.71;

* Gradle 5 - Ferramenta de Build;

```
------------------------------------------------------------
Gradle 5.2.1
------------------------------------------------------------

Build time:   2019-02-08 19:00:10 UTC
Revision:     f02764e074c32ee8851a4e1877dd1fea8ffb7183

Kotlin DSL:   1.1.3
Kotlin:       1.3.20
Groovy:       2.5.4
Ant:          Apache Ant(TM) version 1.9.13 compiled on July 10 2018
JVM:          1.8.0_191 (Oracle Corporation 25.191-b12)
OS:           Linux 5.0.0-13-generic amd64

```

* Versionador - Git;

* Banco de Dados - Mongo (teste com embeddeb) na porta 7700;

* Spring WebFlux (Reactive) - Framerwork Web para geração das API's e Netty como serviço na porta 7701;

* Spring Boot - Setup de projeto;

* Teste com Spring Test para projeto API e Aspen com Spec de testes;

* Jacoco para cobertura de testes integrado com build;

* Docker para gerar containers e Docker compose para gerencia a stack de desenvolvimemnto.

# Documentação através do swagger (versão 3)

O projeto possui um documentação de API através do swagger (Neste possui como utilizar os endpoints da api).

Para acessar:

### Api: person-controller : User Resource

http://localhost:7701/swagger-ui.html#

![Swagger](/images/swagger.png)

## Para exeuctar o build, testes e realizar o deploy do programa

Fiz o build apartado, pois este processo pode ser incluido em uma máquina para CI e CD

Para executar o versão no ambiente local é necessario ter versão  do java 8.

Não precisa instalar o gradle, pois na raiz do projeto possui o gradle wrapper que pode ser usado no Windows,Linux e Mac.

### Passos:

1) Realize um clone do projeto em diretorio da sua escolha (sub entende se que o git esta instalado na máquina).

```
git clone git@github.com:ander-f-silva/manager-user.git  
```

2) Acessar a pasta manager-user

```
cd manager-user
```

3) Realizar o buildo do projeto com gradre (para facilitar a compatibilidade dos projetos e não precisar instalar um versão incompativel, já vem no projeto um wrapper)

```
./gradlew clean build 
```

Caso queira rodar somente os teste é possivé usar 

Neste momento pode demorar um pouco por que precisa baixar a dependencias

```
./gradlew test
```

4) Após finalizar o build é possivel visualizar o relatórios dos testes de integração e dos testes unitários:

```
batch
./manager-user/batch/build/reports/tests/test/index.html

apis
./manager-user/api/build/jacocoHtml/index.html
./manager-user/api/build/reports/tests/test/index.html
```

Observação: Utilizei o jacoco com 95% para o projeto api

5) Para o funcionamento pleno da solução incluia o arquivos users.csv, lista_relevancia_1.txt e lista_relevancia_2.txt na pasta files

```
.manager-user/files

```

6) Criar um ambiente local com o docker compose (importante resaltar que o não é necessário usar o sudo caso a pasta data não seja de uso restrito ao root, no caso no meu ubuntu eu precisei) 

```
sudo docker-compose up 
```

No final para verificar se a aplicação esta no ar e "saudavel", utilize a chamada do actuator.

```
http://localhost:7701/actuator/health
```


## Gestão do Projeto e técnicas para construção da API

Não precisei usar Kaban paraa administrar as atividades, mas sempre me foquei na documentação passada.

Mas as etapas foram:

*  Criação do projeto no https://start.spring.io/;
*  Contrução do projeto batch;
*  Construção do projeto api;
*  Construção do processo de deploy com docker e docker compose
*  Construção dos testes;

