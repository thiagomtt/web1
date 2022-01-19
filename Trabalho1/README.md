# Disciplina de Desenvolvimento Web 1 - 2021/1

## Roteiro de execução

-  Como SGBD utilizamos o MySQL e para visualizar as tabelas e dados do banco de dados utilizamos DBeaver e MySQL WorkBench
-  O schema utilizado se chama: SistemaVeiculos 
-  Para popular inicialmente a aplicação é necessário rodar os scripts que se encontram no diretório src/db/MySQL, nessa ordem:
    1.  create.sql
    2.  initClientes.sql
    3.  initLoja.sql
    4.  initVeiculo.sql
    5.  initFoto.sql
    6.  initProposta.sql

    Com isso vão ser gerados os seguintes perfis:

    |Email|Senha|Papel|
    |---|---|---|
    |admin@gmail.com|admin|Admin|
    |cliente@gmail.com|cliente|Cliente|
    |loja1@gmail.com|loja1|Loja|
    |loja2@gmail.com|loja2|Loja|

    Além disso, vão ser geradas duas propostas do cliente, uma para cada loja.

## Checklist 

| Requisitos | Status |
| ------------- | ------------- |
| R1: CRUD de Clientes | Implementado |
| R2: CRUD de Lojas | Implementado |
| R3: Cadastro de veículo para venda | Implementado |
| R4: Listagem de todos os veículos em uma única página com filtro por modelo | Implementado |
| R5: Proposta de compra de veículo | Implementado |
| R6: Listagem de todos os veículos de uma loja | Implementado |
| R7: Listagem de todas as propostas de compra de um cliente| Implementado |
| R8: Análise/Resposta da loja para propostas em seus veículos | Implementado |
| R9: Internacionalização em dois idiomas | Implementado |

Todas as etapas foram desenvolvidas em conjunto por todos os membros do grupo através de chamadas com compartilhamento de tela. 
