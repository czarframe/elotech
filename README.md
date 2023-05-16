# API de Pessoa e Contatos

Esta é uma API desenvolvida para o teste técnico da Elotech. A API permite gerenciar pessoas e seus contatos.

## Funcionalidades

A API oferece os seguintes recursos:

- Gerenciamento de pessoas: criação, recuperação, atualização e exclusão de pessoas.
- Gerenciamento de contatos: criação, recuperação, atualização e exclusão de contatos.

## Endpoints

A seguir estão os endpoints disponíveis nesta API:

### Pessoa

- `GET /pessoas`: Retorna todas as pessoas cadastradas.
- `GET /pessoas/{id}`: Retorna os detalhes de uma pessoa específica.
- `GET /pessoas/{id}/contatos`: Retorna os contatos de uma pessoa específica.
- `POST /pessoas`: Cria uma nova pessoa.
- `PUT /pessoas/{id}`: Atualiza os dados de uma pessoa existente.
- `DELETE /pessoas/{id}`: Exclui uma pessoa.

### Contato

- `GET /contatos`: Retorna todos os contatos cadastrados.
- `GET /contatos/{id}`: Retorna os detalhes de um contato específico.
- `POST /contatos`: Cria um novo contato.
- `PUT /contatos/{id}`: Atualiza os dados de um contato existente.
- `DELETE /contatos/{id}`: Exclui um contato.

#### <span style="color:red">Não se esqueça! </span>Configure as propriedades do banco de dados no arquivo `application.properties`.
