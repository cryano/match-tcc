# Roteiro de Testes — Match TCC

Este documento orienta a execução dos testes automatizados e dos cenários manuais de aceitação para conferência da solução.

---

## 1. Testes Automatizados (JUnit 5 + Mockito)

A suite de testes automatizados está implementada no diretório padrão do Maven (`src/test/java/`) e conta com **144 testes unitários** com 100% de sucesso.

### Como executar:
No terminal ou pela IDE (IntelliJ IDEA / Eclipse):
```bash
mvn test
```

### Cobertura da Suite de Testes:
* **Estratégias de Afinidade (`strategy`):** Testes exaustivos de `CriterioTemaTest`, `CriterioHorarioTest` e `CriterioPapelTest`, validando pontuações máximas, parciais, nulas e geração de justificativas.
* **Motor Orquestrador (`CalculadoraMatchTest`):** Validação da ordenação decrescente por afinidade (100% até 0%), desempates e isolamento de candidatos.
* **Tratamento de Exceções (`exceptions`):** Validação de `EntradaVaziaException`, `EscolhaInvalidaException`, `ParceiroNaoEncontradoException` e `BaseDemoException`.
* **Camada de Apresentação e Views (`views`, `app`):** Testes unitários com simulação de fluxo via `Scanner` injetado e validações de menus com Mockito.
* **Repositório e Anonimização (`demo`):** Testes de leitura do CSV local, integridade dos 7 campos e aplicação das estratégias de substituição e mascaramento de contatos.

---

## 2. Cenário de Aceitação do MVP (Demonstração do Fluxo Principal)

Durante a Mostra e avaliação pelo professor ou colegas, o fluxo principal comprova:

| Passo | Ação do Avaliador | Entrada Realizada | Comportamento Observado |
| :---: | :--- | :--- | :--- |
| **01** | Iniciar o programa | `mvn exec:java` ou `java -cp target/classes br.ucsal.matchtcc.Main` | Menu principal exibido com opções `[1]` (Manual) e `[2]` (Demo) |
| **02** | Testar entrada vazia | Pressionar `[Enter]` direto no nome | Sistema intercepta com `EntradaVaziaException` amigável sem fechar |
| **03** | Testar opção inválida | Digitar `9` no menu de opções | Sistema avisa opção inválida e solicita nova escolha |
| **04** | Cadastrar perfil válido | Nome, BES, Noturno, Cibersegurança, Sábados, Backend, e-mail | Perfil confirmado e processamento iniciado |
| **05** | Verificar ranking e justificativas | Analisar resultado na tela | Lista ordenada decrescentemente por score (%) com frases explicativas claras |
| **06** | Testar modo Demo rápido | Escolher opção `[2]` | Execução instantânea com perfis simulados da base local |
