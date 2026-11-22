# Match TCC — Sistema de Compatibilidade para Equipes de TCC

> **Resumo:**
> O Match TCC é uma aplicação desenvolvida para auxiliar estudantes de graduação da UCSal a encontrar parceiros ideais para a elaboração do Trabalho de Conclusão de Curso (TCC). A solução coleta preferências temáticas, horários disponíveis e habilidades de contribuição, calculando o índice de afinidade e gerando recomendações com justificativas transparentes.

---

## 1. Problema, Público e Proposta de Valor

* **Público-alvo:** Estudantes dos cursos de Engenharia de Software (BES) e Análise e Desenvolvimento de Sistemas (ADS) da Universidade Católica do Salvador (UCSal), especialmente discentes do turno noturno que trabalham.
* **Problema:** Dificuldade em identificar colegas com linhas de pesquisa semelhantes, disponibilidade de horários compatíveis e comprovação prévia de comprometimento.
* **Proposta de Valor:** Conectar estudantes através de um algoritmo transparente de cálculo de afinidade que ranqueia potenciais parceiros com base em afinidade de pesquisa, compatibilidade de horários e complementaridade de papéis, acompanhado de justificativas claras para iniciar a conversa.

---

## 2. Integrantes e Turma

* **Disciplina:** Programação Orientada a Objetos Avançada
* **Professor:** Mario Jorge Pereira — Semestre 2026.2
* **Turma:** BES  - 4º Semestre
* **Integrantes da Equipe:**
  1. Gabriel Castro Freitas — gabrielcastro.freitas@ucsal.edu.br
  2. Luis Henrique Lourenço das Mercês — luis.merces@ucsal.edu.br
  3. Luiz Gustavo França Barreto Silva — luizgustavofranca.silva@ucsal.edu.br
  4. Pablo Melo Franca Filho — pablomelo.filho@ucsal.edu.br
  5. Ryan David de Oliveira Silva — ryan.silva@ucsal.edu.br
  6. Vinícius Scola Santana — viniciusscola.santana@ucsal.edu.br 

---

## 3. Tecnologias

* **Java 21 (LTS)**
* **Maven** (Gerenciamento de dependências e automação de build)
* **JUnit 5** (Suite de testes automatizados)
* **Mockito** (Isolamento e testes unitários de serviços e views)

---

## 4. Pré-requisitos

* **Java JDK:** 17 ou 21 instalado
* **Maven:** 3.8+ instalado (ou Maven Wrapper)

---

## 5. Como Compilar e Executar

```bash
# Compilar o projeto
mvn clean compile

# Executar a aplicação
mvn exec:java -Dexec.mainClass="br.ucsal.matchtcc.Main"
```

*(Alternativamente, via Java direto: `java -cp target/classes br.ucsal.matchtcc.Main`)*

---

## 6. Testes Automatizados

O projeto possui uma suite automatizada abrangente com **144 testes unitários** (100% de aprovação), cobrindo regras de negócio (`CriterioTema`, `CriterioHorario`, `CriterioPapel`), motor de match, validações de entradas, exceções e camadas de apresentação.

Para rodar todos os testes com geração de relatório JaCoCo:
```bash
mvn test
```

---

## 7. Roteiro de Demonstração

Ao iniciar o programa (`Main`), o menu principal apresentará duas opções de execução:

1. **Opção `[1]` — Inserir alunos manualmente (Fluxo Interativo do MVP):**
   * O usuário digita seus dados em tempo real (Nome, Curso: BES/ADS, Turno: Noturno/Matutino, Tema de interesse, Disponibilidade e Papel no grupo).
   * O sistema valida as entradas (rejeitando campos em branco ou opções inválidas).
   * O motor processa a compatibilidade com a base de candidatos e exibe o ranking ordenado de 0 a 100% com as justificativas humanas para cada recomendação.

2. **Opção `[2]` — Demo (Demonstração Rápida no Estande):**
   * Executa a simulação instantânea com um perfil pré-configurado de busca contra a base local, ideal para validações rápidas com avaliadores no estande da Mostra.

---

## 8. Dados de Demonstração e Privacidade

A pasta [`demo/`](demo/) contém as bases de estudantes simulados ([`alunos-demo.csv`](demo/alunos-demo.csv) e [`perfis-busca-demo.csv`](demo/perfis-busca-demo.csv)).

* **Privacidade e Ética:** Todos os nomes são artificiais (`Estudante Demo XX`) e os contatos utilizam estritamente o domínio reservado RFC 2606 (`@example.invalid`).
* **Proteção de Dados:** Nenhuma informação pessoal real ou credencial é exposta.

---

## 9. Limitações Conhecidas

* O sistema opera com processamento e dados locais para garantir 100% de estabilidade e funcionamento offline no dia da Mostra.
* O sistema atua como facilitador de afinidade; a aceitação mútua e a formalização do grupo ocorrem diretamente entre os estudantes.

---

## 10. Documentos da Entrega (POOALAB)

* 📄 [Anexo A e B: Ficha do Problema e Registro de Validação](docs/problema-validacao.md)
* 📄 [Anexo C: Definição do MVP](docs/definicao-mvp.md)
* 📄 [Anexo E: Declaração de IA e Terceiros](docs/declaracao-ia-terceiros.md)
* 📄 [Modelagem e Backlog](docs/arquitetura-e-backlog.md)
* 📄 [Roteiro de Testes e Aceitação](tests/roteiro-de-testes.md)
* 📄 [Guia e Roteiro da Demonstração Offline](demo/README.md)
