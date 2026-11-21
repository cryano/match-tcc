# Dados e Roteiro de Demonstração (`demo/`)

Esta pasta contém as bases locais de estudantes simulados e o roteiro reproduzível para demonstração offline do **Match TCC**, sem dependência de serviços externos, rede ou bancos remotos.

---

## 1. Confirmação de Dados Fictícios

**Todos os dados presentes nesta pasta são 100% fictícios e simulados.**

* Os nomes seguem identificadores claramente artificiais (`Estudante Demo 01` a `Estudante Demo 18` e `Perfil Busca Demo 01` a `Perfil Busca Demo 03`).
* Todos os contatos utilizam exclusivamente o domínio reservado RFC 2606 `@example.invalid` (ex.: `aluno01@example.invalid`, `aluno91@example.invalid`).
* **Nenhum** nome real de estudante, e-mail acadêmico pessoal (`@ucsal.edu.br`), dado acadêmico confidencial ou resposta real do formulário de validação está incluído na base.
* Adicionalmente, a camada `br.ucsal.matchtcc.demo.strategy` aplica estratégias de substituição (`SubstituidorContatoFicticio`) ou mascaramento (`AnonimizadorMascaraContato`) sobre os contatos carregados, impedindo vazamento acidental caso um registro externo contenha e-mail identificável.

---

## 2. Formato da Base de Dados

A base é separada em dois arquivos CSV em UTF-8, separados por vírgula (`,`), sem regras de pontuação embutidas (toda a pontuação e justificativa permanece exclusivamente nas estratégias `CriterioTema`, `CriterioHorario` e `CriterioPapel` executadas pela `CalculadoraMatch`):

* **[`alunos-demo.csv`](alunos-demo.csv):** base de **18 candidatos simulados** (`Estudante Demo 01` a `Estudante Demo 18`), abrangendo cursos `BES` e `ADS`, turnos `Noturno`, `Matutino` e `Vespertino`, 5 linhas temáticas, disponibilidades distintas e papéis complementares.
* **[`perfis-busca-demo.csv`](perfis-busca-demo.csv):** base separada com **3 perfis simulados de busca** (`Perfil Busca Demo 01` a `03`), evitando perfis fixos em código.

### Esquema das colunas (7 campos obrigatórios)

```csv
nome,curso,turno,temaInteresse,disponibilidade,papel,contato
```

| Coluna | Descrição | Exemplos na base |
| :--- | :--- | :--- |
| `nome` | Identificador fictício do estudante | `Estudante Demo 01`, `Perfil Busca Demo 01` |
| `curso` | Sigla do curso | `BES`, `ADS` |
| `turno` | Turno de estudo | `Noturno`, `Matutino`, `Vespertino` |
| `temaInteresse` | Linha de pesquisa / tema do TCC | `Cibersegurança`, `Inteligência Artificial`, `Engenharia de Software / Web`, `Ciência de Dados`, `Mobile / IoT` |
| `disponibilidade` | Dias e horários disponíveis para reuniões | `Sábados`, `Sábados e Noites`, `Noites durante a semana`, `Final de semana`, `Tardes`, `Manhãs` |
| `papel` | Habilidade / papel principal no grupo | `Backend`, `Frontend`, `Fullstack`, `Documentação/Metodologia`, `Análise de Dados`, `Pesquisa/Dados`, `Mobile`, `QA/Testes` |
| `contato` | Endereço fictício de contato | `aluno01@example.invalid` |

### Regras de leitura (`RepositorioAlunosDemoCsv`)

* Codificação **UTF-8**.
* Linhas em branco e linhas iniciadas com `#` são ignoradas.
* A primeira linha de cabeçalho (`nome,...`) é descartada automaticamente.
* Qualquer linha com número de colunas diferente de 7 ou com campo obrigatório vazio gera `BaseDemoException`.

---

## 3. Organização no Pacote `br.ucsal.matchtcc.demo`

As classes de demonstração ficam isoladas no pacote `br.ucsal.matchtcc.demo`, preservando os pacotes principais e a `Main` minimalista:

* `DemoApplication` — inicialização do fluxo de demonstração (`iniciar()`), chamada diretamente por `Main`.
* `demo.repository` (`RepositorioAlunos`, `RepositorioAlunosDemoCsv`) — leitura e validação dos CSVs locais.
* `demo.strategy` (`AnonimizadorContato`, `SubstituidorContatoFicticio`, `AnonimizadorMascaraContato`) — estratégias (Strategy) de substituição/mascaramento de contatos.
* `demo.service` (`AnonimizacaoContatoService`, `BaseDemoService`) — carregamento e anonimização das bases sem lógica de apresentação de terminal.
* `demo.presentation` (`ApresentadorDemoTerminal`) — formatação e exibição dos resultados no terminal.
* `demo.exceptions` (`BaseDemoException`) — exceção específica para erros de leitura ou formato da base.

---

## 4. Roteiro Reproduzível de Execução (Offline)

### Pré-requisitos

* **Java JDK 21** instalado e disponível no `PATH`
* **Maven 3.8+** instalado
* Terminal posicionado na **raiz do repositório** (onde está o `pom.xml` e a pasta `demo/`)

### Passo 1 — Compilar o projeto

```bash
mvn clean compile
```

Resultado esperado: compilação concluída com `BUILD SUCCESS` sem acesso a serviços externos.

### Passo 2 — Executar a demonstração no terminal

```bash
java -cp target/classes br.ucsal.matchtcc.Main
```

*(Alternativamente, com o plugin já em cache local do Maven: `mvn exec:java`.)*

### Passo 3 — Verificar a saída esperada

Ao executar, o programa carrega automaticamente o primeiro perfil de `demo/perfis-busca-demo.csv` (`Perfil Busca Demo 01` — `BES`, `Noturno`, `Cibersegurança`, `Sábados e Noites`, `Backend`, `aluno91@example.invalid`) e o compara com os 18 candidatos de `demo/alunos-demo.csv` usando `CalculadoraMatch` (`CriterioTema`, `CriterioHorario` e `CriterioPapel`).

Verifique na saída do terminal:

1. **Perfil em busca exibido no topo** com contato fictício `aluno91@example.invalid`.
2. **Processamento dos 18 candidatos** da base local.
3. **Ordenação decrescente de afinidade** com diferenciação clara de pontuações:
   * **1º Lugar (`100%`):** `Estudante Demo 01` (`aluno01@example.invalid`) — mesmo tema (`Cibersegurança`), horário em comum (`Sábados`) e papel complementar (`Backend + Frontend`).
   * **2º Lugar (`80%`):** `Estudante Demo 02` (`aluno02@example.invalid`) — mesmo tema e horário em comum, mesmo papel (`Backend`).
   * **3º e 4º Lugares (`70%`):** `Estudante Demo 03` e `Estudante Demo 04` — mesmo tema e papéis complementares, horários distintos.
   * **Faixas intermediárias (`50%`, `30%`, `20%`):** candidatos com combinações parciais de horário e/ou papel.
   * **Último lugar (`0%`):** `Estudante Demo 14` (`aluno14@example.invalid`) — tema, horário e papel sem compatibilidade.
4. **Justificativas explicativas** geradas exclusivamente pelos critérios de estratégia para cada candidato.
5. **Privacidade:** nenhum e-mail real exibido na saída (apenas `@example.invalid`).

### Passo 4 — Reproduzir com outro perfil de busca (opcional)

Para demonstrar o ranking para outro tema/curso (por exemplo, `Perfil Busca Demo 02` de `ADS` / `Engenharia de Software / Web` ou `Perfil Busca Demo 03` de `BES` / `Inteligência Artificial`), basta reordenar temporariamente as linhas de `demo/perfis-busca-demo.csv` colocando o perfil desejado logo abaixo do cabeçalho e repetir o **Passo 2**.
