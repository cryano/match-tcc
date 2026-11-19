package br.ucsal.matchtcc.service;

import br.ucsal.matchtcc.model.Aluno;
import br.ucsal.matchtcc.model.ResultadoMatch;
import br.ucsal.matchtcc.strategy.CriterioMatch;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Serviço responsável por orquestrar a execução dos critérios de compatibilidade e classificar os candidatos.
 */
public class CalculadoraMatch {

    private List<CriterioMatch> criterios;

    public CalculadoraMatch(List<CriterioMatch> criterios) {
        this.criterios = (criterios != null) ? criterios : new ArrayList<>();
    }

    /**
     * Compara o aluno base com cada candidato da lista, calculando pontuações e ordenando os resultados.
     */
    public List<ResultadoMatch> calcularMatches(Aluno alunoBase, List<Aluno> candidatos) {
        List<ResultadoMatch> resultados = new ArrayList<>();

        if (alunoBase == null || candidatos == null) {
            return resultados;
        }

        for (Aluno candidato : candidatos) {
            // Evita comparar o estudante com ele mesmo
            if (candidato.equals(alunoBase) || 
                (candidato.getNome() != null && candidato.getNome().equalsIgnoreCase(alunoBase.getNome()))) {
                continue;
            }

            int pontuacaoAcumulada = 0;
            List<String> justificativas = new ArrayList<>();

            for (CriterioMatch criterio : criterios) {
                int pontos = criterio.calcularPontos(alunoBase, candidato);
                pontuacaoAcumulada += pontos;

                String justificativa = criterio.obterJustificativa(alunoBase, candidato);
                if (justificativa != null && !justificativa.isBlank()) {
                    justificativas.add(justificativa);
                }
            }

            // Garante que o score não ultrapasse 100%
            int scoreFinal = Math.min(pontuacaoAcumulada, 100);

            resultados.add(new ResultadoMatch(candidato, scoreFinal, justificativas));
        }

        // Ordena da maior para a menor pontuação (via compareTo de ResultadoMatch)
        Collections.sort(resultados);

        return resultados;
    }
}
