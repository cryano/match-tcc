# Anexo A — Ficha do Problema

**Solução:** Match TCC — Sistema de Compatibilidade para Formação de Equipes de TCC
**Turma:** BES - 4º Semestre
**Integrantes:** Gabriel Castro Freitas, Luis Henrique Lourenço das Mercês, Luiz Gustavo França Barreto Silva, Pablo Melo Franca Filho, Ryan David de Oliveira Silva, Vinicius Scola Santana.

| Campo | Preenchimento |
| :--- | :--- |
| **Público** | Estudantes de graduação dos cursos de TI (Engenharia de Software e Análise e Desenvolvimento de Sistemas) da UCSal, com foco prioritário nos estudantes do turno noturno que conciliam trabalho e faculdade. |
| **Situação** | Período de definição de temas e formação de duplas/equipes para início ou planejamento do Trabalho de Conclusão de Curso (TCC). |
| **Problema** | O estudante precisa encontrar um parceiro com interesses de pesquisa alinhados e horários compatíveis, mas tem pouco tempo de convivência presencial e nenhuma forma objetiva de saber com antecedência o comprometimento ou as habilidades do colega. |
| **Impacto** | Postergamento do início do TCC por falta de grupo, ou formação precipitada por mero comodismo, resultando em sobrecarga para um dos membros, conflitos de objetivos e risco de atraso ou reprovação na disciplina. |
| **Alternativa atual** | Mensagens soltas e desordenadas em grupos de WhatsApp da faculdade, conversas informais de corredor ou formação exclusiva entre "amigos próximos" (mesmo sem alinhamento temático). |
| **Hipótese crítica** | Os estudantes de graduação que trabalham têm interesse real em usar uma plataforma dedicada que recomende parceiros compatíveis por tema, disponibilidade e comprometimento, facilitando o contato inicial seguro. |
| **Evidência** | Levantamento primário realizado com 10 participantes da UCSal (BES e ADS), no qual 100% dos entrevistados afirmaram que usariam ou considerariam usar a solução (70% "Sim" e 30% "Talvez", 0% "Não"), e 60% destacaram a falta de previsibilidade de comprometimento como a maior barreira. |
| **Recorte** | Um sistema que recebe o cadastro do perfil do estudante (tema/linha de pesquisa de interesse, objetivo do TCC, disponibilidade de horários e habilidades/papel) e gera uma lista de alunos compatíveis ranqueada por pontuação com justificativas transparentes de afinidade. |

---

# Anexo B — Registro de Validação

**Solução:** Match TCC
*(Registros éticos e anonimizados obtidos via formulário com a comunidade acadêmica da UCSal entre 03/11/2026 e 04/11/2026).*

| Data e perfil | Hipótese | Evidência observada | Decisão |
| :--- | :--- | :--- | :--- |
| **04/11/2026** — Estudante de graduação, BES, Noturno, Trabalha atualmente | Estudantes noturnos que trabalham enfrentam dificuldade de conciliação e buscam segurança no compromisso do parceiro. | Declarou que a maior dificuldade é "Saber se o colega é comprometido". Afirmou que usaria a ferramenta ("Sim") e que o que mais dá confiança é o "Histórico em trabalhos em grupo". Compartilharia dados/IR de forma controlada. | **Manter e reforçar:** Priorizar no cálculo de compatibilidade critérios objetivos de disponibilidade e transparência de perfil. |
| **04/11/2026** — Estudante de graduação, BES, Noturno, Trabalha atualmente | Alunos buscam primeiro amigos, mas sofrem quando o tema ou horários não batem. | Declarou formar com amigos próximos, mas pontuou como barreira "Achar colegas com o mesmo tema". Respondeu "Talvez" ao uso da ferramenta e prioriza "Conversar antes". | **Manter e ajustar:** O sistema não deve impor a formação automática de duplas, mas sim sugerir o match e incentivar o contato prévio/conversa entre os compatíveis. |
| **04/11/2026** — Estudante de graduação, ADS, Noturno, Trabalha atualmente | Alunos de ADS também sentem a falta de rede de contatos para formação de grupos técnicos. | Relatou que ainda não sabe como formar grupo, tem interesse direto na solução ("Sim") e valoriza "Histórico em trabalhos em grupo" e compartilhamento de dados. | **Manter:** Incluir suporte aos perfis de ambos os cursos (ADS e BES) nas opções de demonstração. |
| **04/11/2026** — Estudante de graduação, BES, Matutino, Não trabalha | Estudantes do matutino têm dinâmicas diferentes e incertezas quanto à definição temática precoce. | Relatou: "Ainda não decidi... dificuldade em decidir qual tema vou trabalhar e como vou fazer isso nos próximos dois anos". Respondeu "Sim" para a ferramenta e valoriza "Histórico em trabalhos em grupo". | **Ajustar no MVP:** Permitir que o aluno informe temas de interesse mais amplos e objetivos flexíveis (ex.: "Aberto a definir tema em conjunto"). |
| **03/11/2026** — Egresso(a), ADS, Noturno, Trabalha atualmente | Mesmo quem já passou pela experiência reconhece a deficiência do método tradicional de grupos de WhatsApp. | Declarou que a maior dificuldade foi "Saber se o colega é comprometido". Respondeu "Sim" que usaria a ferramenta e indicou que confiança depende de "Indicação de alguém". | **Manter:** O problema é recorrente e persiste ao longo das turmas; a justificativa clara dos critérios no resultado do match mitiga a insegurança. |

### Síntese dos aprendizados:
1. **O comprometimento é a maior dor:** A maioria expressiva (60%) teme ficar com colegas que não entreguem sua parte ou que abandonem o projeto no meio do caminho.
2. **Receptividade unânime (100%):** Nenhum respondente descartou o uso da solução; 70% responderam sim imediatamente.
3. **Turno Noturno x Trabalho:** Todos os respondentes do turno noturno trabalham, o que torna a compatibilidade de horários para reuniões um requisito de corte tão importante quanto o tema técnico.
4. **O sistema é um facilitador de conversa, não um juiz final:** Os estudantes valorizam "conversar antes" e "ver histórico", o que significa que o MVP deve fornecer justificativas claras para que os alunos tenham assunto e base para iniciar a conversa.

### Mudanças realizadas no problema ou na solução:
- **Refinamento do cálculo de Match:** Em vez de olhar unicamente para o tema de pesquisa, incluímos como pilares essenciais a **compatibilidade de horários/dias livres** e o **objetivo do aluno** (ex.: "já tem tema fixo", "aberto a propostas", "busca grupo focado em mercado" ou "busca grupo acadêmico").
- **Transparência explicável (Justificativa):** O resultado do match deve explicar textualmente o porquê da afinidade (ex.: *"Mesma linha de pesquisa em Cibersegurança e compatibilidade de horário aos sábados e noites de terça"*), dando confiança mútua.
