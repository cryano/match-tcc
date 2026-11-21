package br.ucsal.matchtcc.exceptions;

/**
 * Exceção lançada quando ocorre falha ao carregar ou interpretar a base de alunos de demonstração.
 */
public class BaseDemoException extends RuntimeException {

    public BaseDemoException(String message) {
        super(message);
    }

    public BaseDemoException(String message, Throwable cause) {
        super(message, cause);
    }
}
