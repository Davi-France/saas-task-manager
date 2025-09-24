# Kanban Task Manager

Um sistema de gerenciamento de tarefas estilo **Kanban**, desenvolvido com **Angular** no front-end e **Spring Boot** no back-end.  
Permite criar, visualizar e mover tarefas entre colunas ("Todo", "In Progress" e "Done"), com atualização automática do status no backend.

---

## Tecnologias

- **Front-end:** Angular 17, Angular CDK Drag & Drop, TypeScript, HTML, SCSS  
- **Back-end:** Spring Boot, Java, JPA/Hibernate  
- **Banco de dados:** MySQL (configurável)  
- **Autenticação:** JWT  

---

## Funcionalidades

- **Kanban Dinâmico:**  
  - Três colunas: Todo, In Progress e Done  
  - Arrastar e soltar tarefas entre colunas, atualizando o status no backend  

- **Criação de Tarefas:**  
  - Modal de criação com título, descrição e status  
  - Task adicionada automaticamente na coluna correta  

- **Atualização em tempo real:**  
  - Status atualizado diretamente no backend ao mover uma tarefa  

- **Integração com usuário:**  
  - Cada tarefa pertence a um usuário autenticado  

---

## Estrutura do Projeto

