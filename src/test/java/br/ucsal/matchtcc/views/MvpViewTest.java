package br.ucsal.matchtcc.views;

import br.ucsal.matchtcc.exceptions.EntradaVaziaException;
import br.ucsal.matchtcc.model.Aluno;
import br.ucsal.matchtcc.model.ResultadoMatch;
import br.ucsal.matchtcc.service.AlunoService;
import br.ucsal.matchtcc.service.CalculadoraMatch;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.Collections;
import java.util.List;
import java.util.Scanner;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes Unitários para a classe MvpView")
class MvpViewTest {

    @Mock
    private AlunoService alunoService;

    @Mock
    private CalculadoraMatch calculadoraMatch;

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

    private Scanner criarScanner(String entrada) {
        return new Scanner(entrada);
    }

    @Test
    @DisplayName("Deve executar fluxo completo de cadastro e exibir recomendações quando entradas forem válidas")
    @SuppressWarnings("unchecked")
    void deveExecutarFluxoCompletoDeCadastroEExibirRecomendacoes_QuandoEntradasForemValidas() {
        // Arrange
        String inputSimulado = String.join("\n",
                "Ryan David",
                "1", // BES
                "1", // Noturno
                "1", // Cibersegurança
                "1", // Sábados e Noites
                "1", // Backend
                "ryan.david@ucsal.edu.br"
        ) + "\n";

        Scanner scanner = criarScanner(inputSimulado);
        MvpView mvpView = new MvpView(scanner);

        Aluno alunoMock = new Aluno("Ryan David", "BES", "Noturno", "Cibersegurança", "Sábados e Noites", "Backend", "ryan.david@ucsal.edu.br");
        when(alunoService.cadastrarAluno(
                eq("Ryan David"), eq("BES"), eq("Noturno"), eq("Cibersegurança"),
                eq("Sábados e Noites"), eq("Backend"), eq("ryan.david@ucsal.edu.br")
        )).thenReturn(alunoMock);

        Aluno candidatoMock = new Aluno("Lucas Santana", "BES", "Noturno", "Cibersegurança", "Sábados", "Frontend", "lucas@ucsal.br");
        ResultadoMatch resultado = new ResultadoMatch(candidatoMock, 100, List.of("Mesma linha de pesquisa", "Horários compatíveis"));
        when(calculadoraMatch.calcularMatches(eq(alunoMock), anyList())).thenReturn(List.of(resultado));

        ArgumentCaptor<List<Aluno>> candidatosCaptor = ArgumentCaptor.forClass(List.class);

        // Act
        mvpView.exibirTela(alunoService, calculadoraMatch);
        String output = outputStreamCaptor.toString();

        // Assert
        InOrder inOrder = inOrder(alunoService, calculadoraMatch);
        inOrder.verify(alunoService).cadastrarAluno(
                eq("Ryan David"), eq("BES"), eq("Noturno"), eq("Cibersegurança"),
                eq("Sábados e Noites"), eq("Backend"), eq("ryan.david@ucsal.edu.br")
        );
        inOrder.verify(alunoService).setAluno(alunoMock);
        inOrder.verify(alunoService).exibirAluno(alunoMock);
        inOrder.verify(calculadoraMatch).calcularMatches(eq(alunoMock), candidatosCaptor.capture());

        assertAll("Validação do fluxo completo do MVP e saída formatada",
                () -> assertEquals(5, candidatosCaptor.getValue().size()),
                () -> assertTrue(output.contains("1º Lugar: Lucas Santana — Afinidade: 100%")),
                () -> assertTrue(output.contains("Mesma linha de pesquisa")),
                () -> assertTrue(output.contains("Horários compatíveis"))
        );
    }

    @Test
    @DisplayName("Deve exibir mensagem de que nenhuma afinidade foi encontrada quando justificativas forem vazias")
    void deveExibirMensagemDeNenhumaAfinidade_QuandoJustificativasForemVazias() {
        // Arrange
        String inputSimulado = String.join("\n",
                "Ryan David",
                "1", "1", "1", "1", "1",
                "ryan.david@ucsal.edu.br"
        ) + "\n";

        Scanner scanner = criarScanner(inputSimulado);
        MvpView mvpView = new MvpView(scanner);

        Aluno alunoMock = new Aluno("Ryan David", "BES", "Noturno", "Cibersegurança", "Sábados e Noites", "Backend", "ryan.david@ucsal.edu.br");
        when(alunoService.cadastrarAluno(any(), any(), any(), any(), any(), any(), any())).thenReturn(alunoMock);

        Aluno candidatoSemAfinidade = new Aluno("Aluno Sem Afinidade", "ADS", "Matutino", "Outro", "Tardes", "Backend", "sem@ucsal.br");
        ResultadoMatch resultado = new ResultadoMatch(candidatoSemAfinidade, 0, Collections.emptyList());
        when(calculadoraMatch.calcularMatches(any(), anyList())).thenReturn(List.of(resultado));

        // Act
        mvpView.exibirTela(alunoService, calculadoraMatch);
        String output = outputStreamCaptor.toString();

        // Assert
        assertTrue(output.contains("Nenhuma afinidade identificada nos critérios atuais."));
    }

    @Test
    @DisplayName("Deve capturar ParceiroNaoEncontradoException e exibir aviso quando resultado de matches for vazio")
    void deveCapturarParceiroNaoEncontradoException_QuandoResultadoDeMatchesForVazio() {
        // Arrange
        String inputSimulado = String.join("\n",
                "Ryan David",
                "1", "1", "1", "1", "1",
                "ryan.david@ucsal.edu.br"
        ) + "\n";

        Scanner scanner = criarScanner(inputSimulado);
        MvpView mvpView = new MvpView(scanner);

        Aluno alunoMock = new Aluno("Ryan David", "BES", "Noturno", "Cibersegurança", "Sábados e Noites", "Backend", "ryan.david@ucsal.edu.br");
        when(alunoService.cadastrarAluno(any(), any(), any(), any(), any(), any(), any())).thenReturn(alunoMock);
        when(calculadoraMatch.calcularMatches(any(), anyList())).thenReturn(Collections.emptyList());

        // Act
        mvpView.exibirTela(alunoService, calculadoraMatch);
        String output = outputStreamCaptor.toString();

        // Assert
        assertTrue(output.contains("Nenhum parceiro compatível encontrado no momento."));
    }

    @Test
    @DisplayName("Deve capturar ParceiroNaoEncontradoException e exibir aviso quando resultado de matches for nulo")
    void deveCapturarParceiroNaoEncontradoException_QuandoResultadoDeMatchesForNulo() {
        // Arrange
        String inputSimulado = String.join("\n",
                "Ryan David",
                "1", "1", "1", "1", "1",
                "ryan.david@ucsal.edu.br"
        ) + "\n";

        Scanner scanner = criarScanner(inputSimulado);
        MvpView mvpView = new MvpView(scanner);

        Aluno alunoMock = new Aluno("Ryan David", "BES", "Noturno", "Cibersegurança", "Sábados e Noites", "Backend", "ryan.david@ucsal.edu.br");
        when(alunoService.cadastrarAluno(any(), any(), any(), any(), any(), any(), any())).thenReturn(alunoMock);
        when(calculadoraMatch.calcularMatches(any(), anyList())).thenReturn(null);

        // Act
        mvpView.exibirTela(alunoService, calculadoraMatch);
        String output = outputStreamCaptor.toString();

        // Assert
        assertTrue(output.contains("Nenhum parceiro compatível encontrado no momento."));
    }

    @Test
    @DisplayName("Deve lançar EntradaVaziaException quando usuário inserir texto em branco no nome")
    void deveLancarEntradaVaziaException_QuandoUsuarioInserirTextoEmBrancoNoNome() {
        // Arrange
        String inputVazio = "   \n";
        Scanner scanner = criarScanner(inputVazio);
        MvpView mvpView = new MvpView(scanner);

        // Act & Assert
        EntradaVaziaException exception = assertThrows(EntradaVaziaException.class, () ->
                mvpView.exibirTela(alunoService, calculadoraMatch)
        );

        assertEquals("Entrada não pode ser vazia. Tente novamente.", exception.getMessage());
    }

    @Test
    @DisplayName("Deve recuperar de opção numérica inválida quando usuário informar valor fora do intervalo")
    void deveRecuperarDeOpcaoNumericaInvalida_QuandoUsuarioInformarValorForaDoIntervalo() {
        // Arrange
        String inputSimulado = String.join("\n",
                "Ryan David",
                "99", // Opção de curso inválida inicialmente
                "1",  // Opção válida BES
                "1", "1", "1", "1",
                "ryan.david@ucsal.edu.br"
        ) + "\n";

        Scanner scanner = criarScanner(inputSimulado);
        MvpView mvpView = new MvpView(scanner);

        Aluno alunoMock = new Aluno("Ryan David", "BES", "Noturno", "Cibersegurança", "Sábados e Noites", "Backend", "ryan.david@ucsal.edu.br");
        when(alunoService.cadastrarAluno(any(), any(), any(), any(), any(), any(), any())).thenReturn(alunoMock);
        when(calculadoraMatch.calcularMatches(any(), anyList())).thenReturn(Collections.emptyList());

        // Act
        mvpView.exibirTela(alunoService, calculadoraMatch);
        String output = outputStreamCaptor.toString();

        // Assert
        assertTrue(output.contains("Opção numérica inválida. Escolha entre 1 e 2."));
    }

    @Test
    @DisplayName("Deve aceitar opção textual quando usuário digitar o nome exato da opção")
    void deveAceitarOpcaoTextual_QuandoUsuarioDigitarNomeExatoDaOpcao() {
        // Arrange
        String inputSimulado = String.join("\n",
                "Ryan David",
                "BES", // Opção digitada por extenso
                "1", "1", "1", "1",
                "ryan.david@ucsal.edu.br"
        ) + "\n";

        Scanner scanner = criarScanner(inputSimulado);
        MvpView mvpView = new MvpView(scanner);

        Aluno alunoMock = new Aluno("Ryan David", "BES", "Noturno", "Cibersegurança", "Sábados e Noites", "Backend", "ryan.david@ucsal.edu.br");
        when(alunoService.cadastrarAluno(
                eq("Ryan David"), eq("BES"), any(), any(), any(), any(), any()
        )).thenReturn(alunoMock);
        when(calculadoraMatch.calcularMatches(any(), anyList())).thenReturn(Collections.emptyList());

        // Act
        mvpView.exibirTela(alunoService, calculadoraMatch);
        String output = outputStreamCaptor.toString();

        // Assert
        assertTrue(output.contains("Erro de leitura numérica do sistema, insira valores válidos."));
    }

    @Test
    @DisplayName("Deve aceitar entrada textual personalizada quando não corresponder a opções pré-definidas")
    void deveAceitarEntradaTextualPersonalizada_QuandoNaoCorresponderAOpcoesPreDefinidas() {
        // Arrange
        String inputSimulado = String.join("\n",
                "Ryan David",
                "Engenharia da Computação", // Texto personalizado
                "1", "1", "1", "1",
                "ryan.david@ucsal.edu.br"
        ) + "\n";

        Scanner scanner = criarScanner(inputSimulado);
        MvpView mvpView = new MvpView(scanner);

        Aluno alunoMock = new Aluno("Ryan David", "Engenharia da Computação", "Noturno", "Cibersegurança", "Sábados e Noites", "Backend", "ryan.david@ucsal.edu.br");
        when(alunoService.cadastrarAluno(
                eq("Ryan David"), eq("Engenharia da Computação"), any(), any(), any(), any(), any()
        )).thenReturn(alunoMock);
        when(calculadoraMatch.calcularMatches(any(), anyList())).thenReturn(Collections.emptyList());

        // Act
        mvpView.exibirTela(alunoService, calculadoraMatch);

        // Assert
        InOrder inOrder = inOrder(alunoService);
        inOrder.verify(alunoService).cadastrarAluno(
                eq("Ryan David"), eq("Engenharia da Computação"), any(), any(), any(), any(), any()
        );
    }

    @Test
    @DisplayName("Deve instanciar Scanner padrão no construtor quando parâmetro for nulo")
    void deveInstanciarScannerPadraoNoConstrutor_QuandoParametroForNulo() {
        // Arrange & Act
        MvpView mvpView = new MvpView(null);

        // Assert
        assertNotNull(mvpView);
    }
}
