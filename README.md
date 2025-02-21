# 📅 API de Agendador de Eventos

## 📌 Visão Geral
Esta API permite a criação e gerenciamento de eventos, bem como a inscrição de usuários. Além disso, os usuários podem compartilhar um link personalizado para que outros se inscrevam através dele, o que influencia seu ranqueamento no sistema.

## 🛠️ Tecnologias Utilizadas
- **Linguagem:** Java 23
- **Framework:** Spring Boot
- **Gerenciador de Dependências:** Maven
- **Banco de Dados:** MySQL (versão mais recente disponível)
- **Testes da API:** Insomnia (versão mais recente disponível)

## 📌 Funcionalidades
✅ **Criação de eventos**  
✅ **Listagem de eventos**  
✅ **Inscrição de usuários nos eventos**  
✅ **Geração de links personalizados para convite**  
✅ **Ranqueamento de usuários baseado em inscrições feitas através do link**

## 📌 Endpoints

### 🎟️ Inscrição e Ranqueamento
- **`POST /subscription/{prettyName}`**
    - Inscreve um usuário em um evento pelo `prettyName` do evento.
    - **Exemplo de uso:**
      ```json
      {
        "name": "João Silva",
        "email": "joao@email.com"
      }
      ```  

- **`POST /subscription/{prettyName}/{id}`**
    - Inscreve um usuário específico em um evento pelo seu `id`.

- **`GET /subscription/{prettyName}/ranking/{userId}`**
    - Retorna o ranking de um usuário (`userId`) dentro de um evento (`prettyName`).

### 📅 Gerenciamento de Eventos
- **`GET /get/events`**
    - Lista todos os eventos disponíveis.

- **`POST /post/events`**
    - Cria um novo evento no sistema.
    - **Exemplo de corpo da requisição:**
      ```json
      {
        "name": "Tech Conference 2025",
        "date": "2025-08-20",
        "location": "São Paulo, Brasil"
      }
      ```  

## 📌 Como Rodar o Projeto

1. Clone o repositório:
   ```sh
   git clone https://github.com/devguilara/api-agendador-eventos
   cd seu-repositorio
   ```  

2. Instale as dependências com Maven:
   ```sh
   mvn clean install
   ```  

3. Configure as variáveis de ambiente no `application.properties` ou `application.yml`:
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/seu-banco
   spring.datasource.username=root
   spring.datasource.password=senha
   ```  

4. Inicie a aplicação:
   ```sh
   mvn spring-boot:run
   ```  

5. Acesse a API em `http://localhost:8080`

## 🏆 Ranqueamento
Os usuários ganham pontos ao trazerem novas inscrições para os eventos usando seus links personalizados. Dependendo do número de indicações, eles sobem de nível:
- **Bronze:** 1-5 indicações
- **Prata:** 6-10 indicações
- **Ouro:** 11-20 indicações
- **Platina:** 21+ indicações

## 📌 Testes da API
Para testar a API, utilizamos o **Insomnia** na versão mais recente. Você pode importar os endpoints para testar todas as funcionalidades.

## 📌 Contribuição
Se quiser contribuir, faça um fork do projeto e envie um pull request!

## 📜 Licença
MIT License  

