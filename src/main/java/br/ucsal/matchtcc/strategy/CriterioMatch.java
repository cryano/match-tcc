package br.ucsal.matchtcc.strategy;

import br.ucsal.matchtcc.model.Aluno;

/**
 * Interface que define a família de estratégias de cálculo de compatibilidade (Padrão Strategy - GoF).
 */
public interface CriterioMatch {

    /**
     * Calcula os pontos de afinidade entre o aluno base e o candidato para este critério.
     */
    int calcularPontos(Aluno alunoBase, Aluno candidato);

    /**
     * Retorna a justificativa explicativa, ou null se não houver afinidade.
     */
    String obterJustificativa(Aluno alunoBase, Aluno candidato);
}
