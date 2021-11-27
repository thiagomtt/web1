# Disciplina de Desenvolvimento Web 1 - 2021/1

## Grupo
    - Jorge Vinicius Gonçalves  - 758594
    - Nathaelly Boni            - 758963
    - Thiago de Moraes Teixeira - 760667

## Roteiro de execução

-  Como SGBD utilizamos o MySQL e para visualizar as tabelas e dados do banco de dados utilizamos DBeaver e MySQL WorkBench
-  O schema utilizado se chama: SistemaVeiculos_v2
-  Para popular inicialmente a aplicação é necessário rodar os scripts que se encontram no diretório src/db/MySQL, nessa ordem:
    1.  create.sql
    2.  Rodar a aplicação spring
    3.  initVeiculo.sql
    4.  initFoto.sql

    Com isso vão ser gerados os seguintes perfis:

    |Email|Senha|Papel|
    |---|---|---|
    |admin|admin|Admin|
    |tmoraes505@gmail.com|thg|Cliente|
    |loja1|loja1|Loja|
    |loja2|loja2|Loja|

    Ao rodar a aplicação alguma outra vez, comentar o método 'demo' do arquivo 'SistemaVeiculosApplication.java'

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
| R10: Sistema validar campos nos formulários | Parcialmente implementado |

Todas as etapas foram desenvolvidas em conjunto por todos os membros do grupo através de chamadas com compartilhamento de tela. 
