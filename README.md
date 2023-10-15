# ToDo list

## Requisitos Funcionais (RF's)

- [x] O sistema deve permitir o cadastro de um novo usuário;
- [ ] O sistema deve permitir o login de um usuário;
- [x] O sistema deve permitir o cadastro de uma nova tarefa;
- [x] O sistema deve permitir a listagem de todas as tarefas de um usuário;
- [x] O sistema deve permitir obter os detalhes de uma tarefa;
- [x] O sistema deve permitir a atualização de uma tarefa;
- [x] O sistema deve permitir a exclusão de uma tarefa;

## Requisitos de Negócio (RN's)

- [x] O usuário não deve poder se cadastrar com um username já cadastrado;
- [x] O usuário não deve ver as tarefas de outro usuário;
- [x] O usuário não deve modificar uma tarefa que não seja dele;
- [x] O usuário não deve excluir uma tarefa que não seja dele;
- [x] O usuário não deve poder cadastrar uma tarefa com um título maior que 50 caracteres;
- [x] O usuário não deve poder cadastrar uma tarefa com uma data de início passada;
- [x] O usuário não deve poder cadastrar uma tarefa com uma data de término anterior a data de início;

## Requisitos Não Funcionais (RNF's)

- [x] A senha do usuário deve ser criptografada; 
- [ ] Os dados da aplicação precisam ser armazenados em um banco de dados PostgreSQL;
- [ ] Todas as listas precisam estar paginadas com 20 items por página;
- [ ] O usuário deve ser identificado por um token JWT.
