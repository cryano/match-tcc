package br.ucsal.matchtcc.views;

import br.ucsal.matchtcc.service.AlunoService;
import br.ucsal.matchtcc.service.CalculadoraMatch;

import java.util.Scanner;

public interface View {
    void exibirTela(AlunoService alunoService, CalculadoraMatch calculadora);
}
