package br.ucsal.matchtcc.demo.strategy;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.regex.Pattern;

/**
 * Estratégia concreta que garante que o contato utilize um formato claramente simulado
 * sob o domínio reservado {@code example.invalid} (ex.: {@code aluno01@example.invalid}),
 * substituindo qualquer contato real ou identificável.
 */
public class SubstituidorContatoFicticio implements AnonimizadorContato {

    private static final String PREFIXO_PADRAO = "aluno";
    private static final String DOMINIO_FICTICIO_PADRAO = "example.invalid";

    private final String prefixo;
    private final String dominioFicticio;
    private final Pattern padraoSimulado;
    private final AtomicInteger sequencia;

    public SubstituidorContatoFicticio() {
        this(PREFIXO_PADRAO, DOMINIO_FICTICIO_PADRAO);
    }

    public SubstituidorContatoFicticio(String prefixo, String dominioFicticio) {
        if (prefixo == null || prefixo.isBlank()) {
            throw new IllegalArgumentException("O prefixo do contato fictício não pode ser vazio.");
        }
        if (dominioFicticio == null || dominioFicticio.isBlank()) {
            throw new IllegalArgumentException("O domínio fictício não pode ser vazio.");
        }
        this.prefixo = prefixo.trim();
        this.dominioFicticio = dominioFicticio.trim();
        this.padraoSimulado = Pattern.compile(
                "^" + Pattern.quote(this.prefixo) + "\\d+@" + Pattern.quote(this.dominioFicticio) + "$",
                Pattern.CASE_INSENSITIVE
        );
        this.sequencia = new AtomicInteger(1);
    }

    public String getPrefixo() {
        return prefixo;
    }

    public String getDominioFicticio() {
        return dominioFicticio;
    }

    @Override
    public String anonimizar(String contatoOriginal) {
        if (contatoOriginal != null) {
            String contatoLimpo = contatoOriginal.trim();
            if (padraoSimulado.matcher(contatoLimpo).matches()) {
                return contatoLimpo.toLowerCase();
            }
        }
        int numero = sequencia.getAndIncrement();
        return String.format("%s%02d@%s", prefixo, numero, dominioFicticio);
    }
}
