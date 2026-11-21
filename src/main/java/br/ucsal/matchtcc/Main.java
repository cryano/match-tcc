package br.ucsal.matchtcc;

import br.ucsal.matchtcc.demo.DemoApplication;

/**
 * Ponto de entrada da aplicação — mantém inicialização minimalista delegada a {@link DemoApplication}.
 */
public class Main {

    public static void main(String[] args) {
        DemoApplication app = new DemoApplication(); //TODO: changed later before merge to aggregate all branches
        app.iniciar();
    }
}