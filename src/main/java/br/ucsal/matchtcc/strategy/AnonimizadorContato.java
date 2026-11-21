package br.ucsal.matchtcc.strategy;

/**
 * Interface que define a família de estratégias para anonimização ou substituição
 * de contatos de estudantes na base de demonstração (Padrão Strategy - GoF).
 */
public interface AnonimizadorContato {

    /**
     * Anonimiza ou substitui o contato original informado.
     *
     * @param contatoOriginal contato original do estudante
     * @return contato anonimizado ou substituído por um valor seguro para demonstração
     */
    String anonimizar(String contatoOriginal);
}
