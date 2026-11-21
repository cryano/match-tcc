package br.ucsal.matchtcc;

import br.ucsal.matchtcc.service.AlunoService;
import br.ucsal.matchtcc.service.CalculadoraMatch;
import br.ucsal.matchtcc.strategy.CriterioHorario;
import br.ucsal.matchtcc.strategy.CriterioPapel;
import br.ucsal.matchtcc.strategy.CriterioTema;

import java.util.Arrays;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        CalculadoraMatch calculadora = new CalculadoraMatch(Arrays.asList(new CriterioTema(), new CriterioHorario(), new CriterioPapel()));
        AlunoService alunoService = new AlunoService();
        Scanner scanner = new Scanner(System.in);

        Application app = new Application(alunoService, calculadora, scanner);
        app.iniciar();
    }
}
