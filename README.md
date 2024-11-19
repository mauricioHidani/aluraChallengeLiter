# 📖LiterAlura ![Static Badge](https://img.shields.io/badge/CONCLU%C3%8DDO-%236DB33F?style=flat-square&color=%236DB33F)
📅Tuesday, 19th 2024 - 📍São Paulo, Brazil<br>
🌎[@Alura](https://www.alura.com.br/) [@One](https://www.oracle.com/br/)<br>
 
![Static Badge](https://img.shields.io/badge/SpringBoot-%236DB33F?style=for-the-badge&logo=springboot&labelColor=black)
![Static Badge](https://img.shields.io/badge/Postgresql-4169E1?style=for-the-badge&logo=postgresql&logoColor=white&labelColor=black)
![Static Badge](https://img.shields.io/badge/Javascript-%23F7DF1E?style=for-the-badge&logo=javascript&labelColor=black)
![Static Badge](https://img.shields.io/badge/HTML5-%23E34F26?style=for-the-badge&logo=html5&labelColor=black)
![Static Badge](https://img.shields.io/badge/CSS3-%231572B6?style=for-the-badge&logo=css3&logoColor=%231572B6&labelColor=black)

![LiterAlura](https://github.com/user-attachments/assets/1d249375-fdda-4422-8a0b-1fe7a51e5511)

## Descrição do Desafio
A aplicação deve conseguir:
- Listar livros consultados pelo seu `titulo` na base de dados, caso não haja. Deverá consultar na API da `Gutendex`
- Listar todos os livros da base de dados e mostrar uma breve estatística dos downloads de cada livro se estão acima ou abaixo da média
- Listar o top10 livros mais baixados com Média, Mínimo, Máximo e se o livro está acima ou abaixo da média
- Listar os livros de determinada língua
- Contar quantidade de livros de determinada língua

- Listar todos autores
- Listar os autores vivos em um determinado ano
- Encontrar um autor pelo seu nome

## Configurações para rodar o Challenge

### Usando o banco de dados Postgres no projeto
1. Para conseguir rodar o `Postgres` e o `PgAdmin` no docker, deve-se navegar até a pasta `./backend/docker` com o comando `cd`: 
```bash
cd backend/docker/
```

2. Subindo as imagens do `Postgres` e o `PgAdmin` no `Docker`:
```bash
docker-comport up -d
```

3. Configurando as propriedades do LiterAlura Challenge para utilizar o bando de dados `Postgres`. Deve-se modificar o profile do arquivo `application.properties`
   para `prod` para utilizar das propriedades de Produção (`application-prod.properties`). Caminho `backend/src/main/resources`:
```properties
# ...outras_partes_das_propriedades
spring.profiles.active=prod # coloque aqui o profile para prod
# ...outras_partes_das_propriedades
```

4. Modifique o arquivo de propriedades de produção `application-prod.properties` para aceitar o `Host` para se conectar ao `Postgres` rodando no `Docker`,
   assim como o `Username` e o `Password`:
```properties
spring.datasource.url=${POSTGRES_HOST} # modifique o host para se conectar ao postgres
spring.datasource.username=${POSTGRES_USERNAME} # use o mesmo username usado no arquivo docker-compose.yml
spring.datasource.password=${POSTGRES_PASSWORD} # use o mesmo password usado no arquivo docker-compose.yml
spring.jpa.database-platform=org.hibernate.dialect.PostgreSQLDialect
spring.jpa.hibernate.ddl-auto=update
spring.datasource.initialization-mode=always
spring.datasource.driver-class-name=org.postgresql.Driver
```
Exemplo de configuração
- `${POSTGRES_HOST}`: `jdbc:postgresql://localhost:15432/liter_alura_db`
- `${POSTGRES_USERNAME}`: `postgres`
- `${POSTGRES_PASSWORD}`: `postgres`

## Descrição em imagens
**Tela inicial**<br>
![image](https://github.com/user-attachments/assets/480c3356-1983-414a-992d-f378c7d30e39)

**Consultar livro por titulo**<br>
![image](https://github.com/user-attachments/assets/5653dbbb-a51b-4cb0-aefe-b5199222c135)

**Consultar todos os livros**<br>
![image](https://github.com/user-attachments/assets/f9d2ac5f-d6d4-46f3-9884-8093e431e7a2)

**Consultar Top10 livros mais baixados**<br>
![image](https://github.com/user-attachments/assets/11f0f11f-3f7b-4bd2-b3ac-fccbf574c826)

**Consultar livro por língua**<br>
![image](https://github.com/user-attachments/assets/79d70485-cd43-4dfe-91fc-61390726d822)

**Consultar número de livros de determinada língua**<br>
![image](https://github.com/user-attachments/assets/8c62f344-8260-4a66-9a46-414153b72570)

**Consultar autores cadastrados**<br>
![image](https://github.com/user-attachments/assets/d0845291-58b1-469d-a767-640caabfcc49)

**Consultar autores vivos em determinado ano**<br>
![image](https://github.com/user-attachments/assets/1d34e167-9708-47dd-b94f-6caec83a6cac)

**Consultar autor por nome**<br>
![image](https://github.com/user-attachments/assets/fdc77a5b-5981-433a-b4fe-aa5b369c4aa3)
