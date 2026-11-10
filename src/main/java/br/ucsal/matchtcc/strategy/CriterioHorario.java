package br.ucsal.matchtcc.strategy;

import br.ucsal.matchtcc.model.Aluno;

/**
 * Avalia a compatibilidade de horários e dias disponíveis para reuniões.
 */
public class CriterioHorario implements CriterioMatch {

    private static final int PONTOS_HORARIO_COMPATIVEL = 30;

    @Override
    public int calcularPontos(Aluno alunoBase, Aluno candidato) {
        if (alunoBase.getDisponibilidade() != null && candidato.getDisponibilidade() != null) {
            String dispBase = alunoBase.getDisponibilidade().toLowerCase();
            String dispCandidato = candidato.getDisponibilidade().toLowerCase();

            if (dispBase.contains("sábado") && dispCandidato.contains("sábado") ||
                dispBase.contains("noite") && dispCandidato.contains("noite") ||
                dispBase.contains("final de semana") && dispCandidato.contains("final de semana")) {
                return PONTOS_HORARIO_COMPATIVEL;
            }
        }
        return 0;
    }

    @Override
    public String obterJustificativa(Aluno alunoBase, Aluno candidato) {
        int pontos = calcularPontos(alunoBase, candidato);
        if (pontos > 0) {
            return "Horários de estudo em comum (" + candidato.getDisponibilidade() + ")";
        }
        return null;
    }
}
