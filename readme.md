# Medication Management - Documentação do Projeto

## Problema Resolvido

O Medication Management é uma solução desenvolvida para a DEVinPharmacy LTDA gerenciar eficientemente seus medicamentos, farmácias e estoques.

## Tecnologias Utilizadas

O projeto foi implementado utilizando as seguintes tecnologias:

- **Linguagem de programação:** Java
- **Framework:** Spring Boot para construção de API REST
- **Banco de Dados:** PostgreSQL
- **Ferramentas de Desenvolvimento:** Git, GitHub
- **Ambiente de Desenvolvimento:** VSCode

## Estrutura do Projeto

A estrutura do projeto segue o padrão de arquitetura MVC (Model-View-Controller), com a separação clara das camadas de modelo, visão e controle.

## Execução do Projeto

Para executar o projeto, siga os passos abaixo:

1. Clone o repositório do GitHub.
2. Importe o projeto em sua IDE de preferência.
3. Configure o banco de dados PostgreSQL com a string de conexão fornecida no documento.
4. Execute a aplicação Spring Boot.

## Endpoints da API

A API oferece os seguintes endpoints:

- **/inicializacao (POST):** Popula as tabelas com dados iniciais.
- **/farmacias (GET):** Consulta todas as farmácias cadastradas.
- **/farmacias/{cnpj} (GET):** Consulta uma farmácia pelo CNPJ.
- **/farmacias (POST):** Cadastra uma nova farmácia.
- **/medicamentos (GET):** Consulta todos os medicamentos cadastrados.
- **/medicamentos (POST):** Cadastra um novo medicamento.
- **/estoque/{cnpj} (GET):** Consulta o estoque de medicamentos de uma farmácia.
- **/estoque (POST):** Adiciona medicamentos ao estoque.
- **/estoque (DELETE):** Realiza a venda de medicamentos com atualização do estoque.
- **/estoque (PUT):** Registra a troca de medicamentos entre farmácias.

## Melhorias Futuras

Algumas melhorias que podem ser aplicadas ao projeto incluem:

- Implementação de autenticação e autorização para garantir a segurança da API.
- Adição de testes automatizados para garantir a robustez do código.
- Melhorias na documentação, incluindo mais detalhes sobre os endpoints e exemplos de requisições.
- Implementação de logs para rastreamento de eventos.
- Integração com serviços externos para obter informações adicionais sobre medicamentos.

Essas melhorias visam aprimorar a qualidade, segurança e usabilidade da aplicação.

Este projeto foi desenvolvido por Henrique Mafra. Qualquer dúvida ou sugestão, entre em contato!
