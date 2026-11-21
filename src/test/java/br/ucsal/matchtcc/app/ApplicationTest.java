package br.ucsal.matchtcc.app;

import br.ucsal.matchtcc.exceptions.EscolhaInvalidaException;
import br.ucsal.matchtcc.model.Aluno;
import br.ucsal.matchtcc.service.AlunoService;
import br.ucsal.matchtcc.service.CalculadoraMatch;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Collections;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes Unitários para a classe Application")
class ApplicationTest {

    @Mock
    private AlunoService alunoService;

    @Mock
    private CalculadoraMatch calculadora;

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    void tearDown() {
        System.setOut(standardOut);
    }

    @Test
    @DisplayName("Deve iniciar fluxo Demo quando o usuário escolher a opção 2")
    void deveIniciarFluxoDemo_QuandoUsuarioEscolherOpcao2() {
        // Arrange
        Scanner scanner = new Scanner("2\n");
        Application application = new Application(alunoService, calculadora, scanner);

        when(calculadora.calcularMatches(any(Aluno.class), anyList())).thenReturn(Collections.emptyList());

        // Act
        application.iniciar();
        String output = outputStreamCaptor.toString();

        // Assert
        assertAll("Validação da inicialização do fluxo Demo",
                () -> verify(alunoService).exibirAluno(any(Aluno.class)),
                () -> verify(calculadora).calcularMatches(any(Aluno.class), anyList()),
                () -> assertTrue(output.contains("MATCH TCC — SISTEMA DE FORMAÇÃO DE EQUIPES")),
                () -> assertTrue(output.contains("[2] Demo (alunos criados automaticamente)"))
        );
    }

    @Test
    @DisplayName("Deve iniciar fluxo MvpView quando o usuário escolher a opção 1")
    void deveIniciarFluxoMvpView_QuandoUsuarioEscolherOpcao1() {
        // Arrange
        String inputSimulado = String.join("\n",
                "1", // Opção do menu
                "Carlos Silva",
                "1", "1", "1", "1", "1",
                "carlos@ucsal.br"
        ) + "\n";

        Scanner scanner = new Scanner(inputSimulado);
        Application application = new Application(alunoService, calculadora, scanner);

        Aluno alunoMock = new Aluno("Carlos Silva", "BES", "Noturno", "Cibersegurança", "Sábados e Noites", "Backend", "carlos@ucsal.br");
        when(alunoService.cadastrarAluno(any(), any(), any(), any(), any(), any(), any())).thenReturn(alunoMock);
        when(calculadora.calcularMatches(any(), anyList())).thenReturn(Collections.emptyList());

        // Act
        application.iniciar();
        String output = outputStreamCaptor.toString();

        // Assert
        assertAll("Validação da inicialização do fluxo MVP manual",
                () -> verify(alunoService).cadastrarAluno(any(), any(), any(), any(), any(), any(), any()),
                () -> verify(alunoService).setAluno(alunoMock),
                () -> verify(alunoService).exibirAluno(alunoMock),
                () -> verify(calculadora).calcularMatches(any(), anyList()),
                () -> assertTrue(output.contains("CADASTRO DE PERFIL DO ESTUDANTE"))
        );
    }

    @ParameterizedTest(name = "Opção inválida informada: {0}")
    @ValueSource(ints = {0, 3, 4, 99, -1})
    @DisplayName("Deve lançar EscolhaInvalidaException quando usuário fornecer opção fora do menu")
    void deveLancarEscolhaInvalidaException_QuandoUsuarioFornecerOpcaoForaDoMenu(int opcaoInvalida) {
        // Arrange
        Scanner scanner = new Scanner(opcaoInvalida + "\n");
        Application application = new Application(alunoService, calculadora, scanner);

        // Act & Assert
        EscolhaInvalidaException exception = assertThrows(EscolhaInvalidaException.class, application::iniciar);

        assertEquals("Deve-se escolher dentro das opções disponíveis!", exception.getMessage());
    }
}
