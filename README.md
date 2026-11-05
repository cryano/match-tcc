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
* **Maven** (Gerenciamento de dependências e build)
* **JUnit 5** (Suite de testes)

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

---

## 6. Testes

```bash
mvn test
```
*(Os casos de testes serão desenvolvidos nas etapas seguintes).*

---

## 7. Roteiro de Demonstração

*(A ser preenchido após a implementação do fluxo principal).*

---

## 8. Dados de Demonstração

A base de perfis simulados para testes seguros será disponibilizada na pasta [`demo/`](demo/).

---

## 9. Limitações Conhecidas

* O sistema opera com processamento local na demonstração.
* A formação final e o contato dependem da decisão direta dos estudantes fora do sistema.

---

## 10. Documentos da Entrega (POOALAB)

* 📄 [Anexo A e B: Ficha do Problema e Registro de Validação](docs/problema-validacao.md)
* 📄 [Anexo C: Definição do MVP](docs/definicao-mvp.md)
* 📄 [Anexo E: Declaração de IA e Terceiros](docs/declaracao-ia-terceiros.md)
