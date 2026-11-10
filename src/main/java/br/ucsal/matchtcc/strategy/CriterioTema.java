package br.ucsal.matchtcc.strategy;

import br.ucsal.matchtcc.model.Aluno;

/**
 * Avalia a afinidade com base no tema / linha de pesquisa de interesse.
 */
public class CriterioTema implements CriterioMatch {

    private static final int PONTOS_TEMA_IGUAL = 50;

    @Override
    public int calcularPontos(Aluno alunoBase, Aluno candidato) {
        if (alunoBase.getTemaInteresse() != null && 
            alunoBase.getTemaInteresse().equalsIgnoreCase(candidato.getTemaInteresse())) {
            return PONTOS_TEMA_IGUAL;
        }
        return 0;
    }

    @Override
    public String obterJustificativa(Aluno alunoBase, Aluno candidato) {
        if (alunoBase.getTemaInteresse() != null && 
            alunoBase.getTemaInteresse().equalsIgnoreCase(candidato.getTemaInteresse())) {
            return "Mesma linha de pesquisa de interesse (" + candidato.getTemaInteresse() + ")";
        }
        return null;
    }
}
