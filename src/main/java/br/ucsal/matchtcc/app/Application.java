package br.ucsal.matchtcc.app;



import br.ucsal.matchtcc.exceptions.EscolhaInvalidaException;
import br.ucsal.matchtcc.service.AlunoService;
import br.ucsal.matchtcc.service.CalculadoraMatch;
import br.ucsal.matchtcc.views.DemoView;
import br.ucsal.matchtcc.views.MvpView;
import br.ucsal.matchtcc.views.View;

import java.util.Scanner;

public class Application {

    /*
     ============ RESPONSABILIDADE =================
     - alterar a main para captura de dados via teclado (sem ser pré-pronto) e exibir os menus correspondentes
     - O fluxo executa de ponta a ponta sem travar ou interromper a execução inesperadamente.
     - O sistema valida entradas básicas (não aceita campos em branco).
     - A ordenação é decrescente pela pontuação calculada.
     - Para cada candidato sugerido, é exibida a justificativa da afinidade.
     - A demonstração funciona com dados locais seguros, sem depender de internet externa.

     Ver definicao-mvp.md e problema-validacao.md para mais informações acerca da responsabilidade
     */

    private AlunoService alunoService;
    private CalculadoraMatch calculadora;
    private Scanner scanner;

    public Application(AlunoService alunoService, CalculadoraMatch calculadora, Scanner scanner) {
        this.alunoService = alunoService;
        this.calculadora = calculadora;
        this.scanner = scanner;
    }

    public void iniciar() {
        System.out.println("==================================================================");
        System.out.println("          MATCH TCC — SISTEMA DE FORMAÇÃO DE EQUIPES             ");
        System.out.println("              Laboratório POOALAB — UCSal 2026.2                  ");
        System.out.println("==================================================================\n");
        System.out.println("\nComo gostaria de dar procedimento?\n");
        System.out.println("==================================================================");
        System.out.println("                             MENU                                 ");
        System.out.println("==================================================================\n");
        System.out.println("                 [1] Inserir alunos manualmente                   ");
        System.out.println("             [2] Demo (alunos criados automaticamente)            ");

        int opcao = scanner.nextInt();
        scanner.nextLine();
        View resultado = switch (opcao) {
            case 1 -> new MvpView(scanner);
            case 2 -> new DemoView();
            default -> throw new EscolhaInvalidaException("Deve-se escolher dentro das opções disponíveis!");
        };

        resultado.exibirTela(alunoService, calculadora);
    }
}
