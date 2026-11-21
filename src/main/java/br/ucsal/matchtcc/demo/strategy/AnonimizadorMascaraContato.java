package br.ucsal.matchtcc.demo.strategy;

/**
 * Estratégia concreta que aplica mascaramento parcial ao contato original do estudante,
 * preservando apenas parte do identificador e o domínio (quando for um e-mail).
 */
public class AnonimizadorMascaraContato implements AnonimizadorContato {

    private static final String MASCARA_PADRAO = "***";
    private static final String CONTATO_PADRAO_ANONIMO = "***@example.invalid";

    @Override
    public String anonimizar(String contatoOriginal) {
        if (contatoOriginal == null || contatoOriginal.isBlank()) {
            return CONTATO_PADRAO_ANONIMO;
        }

        String contatoLimpo = contatoOriginal.trim();
        int indiceArroba = contatoLimpo.indexOf('@');

        if (indiceArroba > 0 && indiceArroba < contatoLimpo.length() - 1) {
            String parteLocal = contatoLimpo.substring(0, indiceArroba);
            String dominio = contatoLimpo.substring(indiceArroba + 1);
            return mascararTrecho(parteLocal) + "@" + dominio;
        }

        return mascararTrecho(contatoLimpo);
    }

    private String mascararTrecho(String texto) {
        if (texto.length() <= 1) {
            return MASCARA_PADRAO;
        }
        int caracteresVisiveis = (texto.length() == 2) ? 1 : 2;
        return texto.substring(0, caracteresVisiveis) + MASCARA_PADRAO;
    }
}
