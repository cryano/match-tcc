package br.ucsal.matchtcc.strategy;

/**
 * Estratégia concreta que aplica mascaramento parcial ao contato original do estudante
 * (ex.: preservando apenas parte do identificador e o domínio).
 */
public class AnonimizadorMascaraContato implements AnonimizadorContato {

    private static final String MASCARA_PADRAO = "***";

    @Override
    public String anonimizar(String contatoOriginal) {
        // TODO: implementar mascaramento parcial do contato original
        throw new UnsupportedOperationException("TODO: implementar mascaramento do contato.");
    }
}
