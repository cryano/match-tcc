# Roteiro de Testes — Match TCC

Este documento orientará a verificação e a confiabilidade da solução ao longo das etapas do POOALAB.

---

## 1. Testes Automatizados (JUnit 5)

Os testes automatizados serão implementados na pasta padrão do Maven:
`src/test/java/`

### Como serão executados:
No terminal ou pela IDE (IntelliJ IDEA / Eclipse):
```bash
mvn test
```

> **Nota da Etapa 1:** A modelagem das classes e os casos de teste específicos serão definidos na **Etapa 2 (Arquitetura e Backlog)**, após a diagramação da solução.

---

## 2. Cenário de Aceitação do MVP (Demonstração do Fluxo Principal)

Durante a mostra e avaliação pelo professor ou colegas, o fluxo principal deverá comprovar:

1. **Entrada de dados:** O usuário informa suas preferências (tema, horários disponíveis e habilidades).
2. **Execução sem erros:** O sistema processa o cálculo de compatibilidade sem interrupção abrupta.
3. **Resultado verificável:** A lista de candidatos é exibida ordenada por afinidade, acompanhada de justificativas visíveis para cada recomendação.
4. **Tratamento de dados:** O sistema lida adequadamente com dados vazios ou opções inválidas.
