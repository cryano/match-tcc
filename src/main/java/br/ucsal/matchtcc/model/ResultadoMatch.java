package br.ucsal.matchtcc.model;

import java.util.ArrayList;
import java.util.List;

/**
 * Encapsula o resultado da avaliação de compatibilidade de um candidato em relação a um aluno base.
 */
public class ResultadoMatch implements Comparable<ResultadoMatch> {

    private Aluno alunoCandidato;
    private int pontuacaoTotal;
    private List<String> justificativas;

    public ResultadoMatch(Aluno alunoCandidato, int pontuacaoTotal, List<String> justificativas) {
        this.alunoCandidato = alunoCandidato;
        this.pontuacaoTotal = pontuacaoTotal;
        this.justificativas = (justificativas != null) ? justificativas : new ArrayList<>();
    }

    public Aluno getAlunoCandidato() {
        return alunoCandidato;
    }

    public int getPontuacaoTotal() {
        return pontuacaoTotal;
    }

    public List<String> getJustificativas() {
        return justificativas;
    }

    @Override
    public int compareTo(ResultadoMatch outro) {
        // Ordenação decrescente: maior pontuação primeiro
        return Integer.compare(outro.pontuacaoTotal, this.pontuacaoTotal);
    }

    @Override
    public String toString() {
        return alunoCandidato.getNome() + " — Afinidade: " + pontuacaoTotal + "%";
    }
}
