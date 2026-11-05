# Anexo C — Definição do MVP

**Solução:** Match TCC — Sistema de Compatibilidade para Formação de Equipes de TCC
**Turma:** BES - 4º Semestre
**Integrantes:** Gabriel Castro Freitas, Luis Henrique Lourenço das Mercês, Luiz Gustavo França Barreto Silva, Pablo Melo Franca Filho, Ryan David de Oliveira Silva, Vinicius Scola Santana.

---

### Usuário principal
Estudante de graduação da UCSal (cursos de BES ou ADS) que está cursando ou se aproximando do período de TCC e busca colegas compatíveis para formar dupla ou grupo.

### Resultado principal
O estudante consegue cadastrar suas preferências/perfil e obter imediatamente uma lista classificada dos colegas mais compatíveis, com uma pontuação de afinidade percentual e a justificativa clara dos motivos da compatibilidade.

### Fluxo de ponta a ponta
$$\text{[Entrada]} \longrightarrow \text{[Processamento]} \longrightarrow \text{[Resultado]}$$

1. **Entrada:**
   - O usuário informa seus dados de perfil:
     - Nome e Curso (BES / ADS);
     - Linha de pesquisa / Tema de interesse principal (ex.: Inteligência Artificial, Engenharia de Software / Web, Cibersegurança, Ciência de Dados, Mobile / IoT);
     - Turno e disponibilidade de dias/horários para reuniões (ex.: Sábados, Noites durante a semana, Finais de semana);
     - Papel / Habilidade que pode contribuir (ex.: Backend, Frontend, Documentação/Metodologia, Análise de Dados).
2. **Processamento:**
   - O sistema compara o perfil informado com os candidatos da base de demonstração.
   - Aplica as regras de pontuação de compatibilidade:
     - Afinidade temática (peso alto);
     - Compatibilidade de horários/dias comuns para reunião (peso alto);
     - Complementaridade de habilidades e alinhamento de curso (peso moderado).
3. **Resultado:**
   - Apresentação da lista ordenada dos candidatos com maior grau de afinidade (Score percentual).
   - Exibição de justificativa textual clara para cada resultado (ex.: *"Alta compatibilidade: ambos têm interesse em Cibersegurança e possuem disponibilidade aos sábados"*).
   - Dados de contato fictícios para demonstração (e-mail acadêmico seguro).

### Critérios de pronto
1. O fluxo executa de ponta a ponta sem travar ou interromper a execução inesperadamente.
2. O sistema valida entradas básicas (não aceita campos em branco).
3. A ordenação é decrescente pela pontuação calculada.
4. Para cada candidato sugerido, é exibida a justificativa da afinidade.
5. A demonstração funciona com dados locais seguros, sem depender de internet externa.

### Fora do escopo (O que NÃO será feito no MVP)
- Integração com o Portal Acadêmico da UCSal.
- Chat em tempo real integrado (o contato ocorre externamente entre os alunos).
- Exibição ou validação de notas/histórico acadêmico confidencial.
- Gestão de tarefas ou repositório de documentos do TCC.

### Maior risco
- **Risco técnico/uso:** Dificuldade de demonstrar valor caso os dados de teste sejam muito homogêneos.
  *Mitigação:* Utilizar dados de teste na pasta `demo/` com perfis bem variados (turnos, temas e dias distintos) para evidenciar a diferenciação das pontuações.

### Teste principal
1. **Teste do fluxo central:** Inserir um perfil de teste e conferir se o candidato com características equivalentes surge no topo do ranking com justificativa correspondente.
2. **Teste com usuário externo:** Convidar um colega de sala fora da equipe para simular seu preenchimento e verificar se o resultado faz sentido para ele.

### Limitações conhecidas
- Os dados do MVP são processados localmente durante a execução.
- O sistema sugere a afinidade, cabendo aos alunos decidirem e formalizarem a equipe.
