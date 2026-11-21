package br.ucsal.matchtcc.strategy;

/**
 * Estratégia concreta que substitui o contato original por um contato acadêmico fictício
 * seguro para uso na demonstração do MVP.
 */
public class SubstituidorContatoFicticio implements AnonimizadorContato {

    private static final String PREFIXO_PADRAO = "estudante.demo";
    private static final String DOMINIO_FICTICIO_PADRAO = "demo.ucsal.edu.br";

    private final String prefixo;
    private final String dominioFicticio;

    public SubstituidorContatoFicticio() {
        this(PREFIXO_PADRAO, DOMINIO_FICTICIO_PADRAO);
    }

    public SubstituidorContatoFicticio(String prefixo, String dominioFicticio) {
        this.prefixo = prefixo;
        this.dominioFicticio = dominioFicticio;
    }

    public String getPrefixo() {
        return prefixo;
    }

    public String getDominioFicticio() {
        return dominioFicticio;
    }

    @Override
    public String anonimizar(String contatoOriginal) {
        // TODO: implementar substituição por contato acadêmico fictício seguro
        throw new UnsupportedOperationException("TODO: implementar substituição por contato fictício.");
    }
}
