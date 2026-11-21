# Dados e Roteiro de Demonstração (`demo/`)

Esta pasta contém a base local de estudantes simulados utilizada na demonstração do MVP e nos testes do Match TCC.

## Base de dados simulada

* **Arquivo:** [`alunos-demo.csv`](alunos-demo.csv)
* **Colunas:** `nome,curso,turno,temaInteresse,disponibilidade,papel,contato`
* **Política de privacidade e simulação:**
  * Todos os registros são **claramente simulados** (`Estudante Demo 01` a `Estudante Demo 10`).
  * Os contatos utilizam exclusivamente o domínio reservado `@example.invalid` (ex.: `aluno01@example.invalid`).
  * Nenhum dado pessoal, e-mail real ou resposta real do formulário de validação está presente na base.

## Carregamento e anonimização (`br.ucsal.matchtcc.demo`)

As classes de suporte à base de demonstração ficam isoladas no pacote `br.ucsal.matchtcc.demo` (sem interferir nos pacotes da branch `main`):

* `demo.repository.RepositorioAlunos` / `RepositorioAlunosDemoCsv` — carrega os candidatos de `demo/alunos-demo.csv`.
* `demo.strategy.AnonimizadorContato` / `SubstituidorContatoFicticio` / `AnonimizadorMascaraContato` — estratégias (Strategy) para substituição por contatos `@example.invalid` ou mascaramento parcial.
* `demo.service.AnonimizacaoContatoService` / `BaseDemoService` — orquestra o carregamento e a anonimização da base para consumo pela `DemoView` (substituindo a lista fixa em código).
* `demo.exceptions.BaseDemoException` — exceção específica para falhas de leitura ou formato do arquivo de demonstração.
