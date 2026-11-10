package br.ucsal.matchtcc.strategy;

import br.ucsal.matchtcc.model.Aluno;

/**
 * Avalia a complementaridade de papéis e habilidades de contribuição na equipe.
 */
public class CriterioPapel implements CriterioMatch {

    private static final int PONTOS_COMPLEMENTAR = 20;

    @Override
    public int calcularPontos(Aluno alunoBase, Aluno candidato) {
        if (alunoBase.getPapel() != null && candidato.getPapel() != null) {
            // Se os papéis são diferentes, há complementaridade de habilidades para a equipe
            if (!alunoBase.getPapel().equalsIgnoreCase(candidato.getPapel())) {
                return PONTOS_COMPLEMENTAR;
            }
        }
        return 0;
    }

    @Override
    public String obterJustificativa(Aluno alunoBase, Aluno candidato) {
        int pontos = calcularPontos(alunoBase, candidato);
        if (pontos > 0) {
            return "Habilidades complementares para o grupo (" + alunoBase.getPapel() + " + " + candidato.getPapel() + ")";
        }
        return null;
    }
}
