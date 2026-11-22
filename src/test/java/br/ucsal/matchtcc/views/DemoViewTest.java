package br.ucsal.matchtcc.views;

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

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyList;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes Unitários para a classe DemoView")
class DemoViewTest {

    @Mock
    private AlunoService alunoService;

    @Mock
    private CalculadoraMatch calculadora;

    private DemoView demoView;

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {
        demoView = new DemoView();
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    void tearDown() {
        System.setOut(standardOut);
    }

    @Test
    @DisplayName("Deve executar fluxo de demonstração com sucesso quando houver candidatos recomendados")
    @SuppressWarnings("unchecked")
    void deveExecutarFluxoDeDemonstracaoComSucesso_QuandoHouverCandidatosRecomendados() {
        // Arrange
        Aluno candidatoMock = new Aluno("Lucas Santana", "BES", "Noturno", "Cibersegurança", "Sábados", "Frontend", "lucas@ucsal.br");
        ResultadoMatch resultado = new ResultadoMatch(candidatoMock, 100, List.of("Mesma linha de pesquisa", "Horários em comum"));

        when(calculadora.calcularMatches(any(Aluno.class), anyList())).thenReturn(List.of(resultado));

        ArgumentCaptor<Aluno> alunoCaptor = ArgumentCaptor.forClass(Aluno.class);
        ArgumentCaptor<List<Aluno>> candidatosCaptor = ArgumentCaptor.forClass(List.class);

        // Act
        demoView.exibirTela(alunoService, calculadora);
        String output = outputStreamCaptor.toString();

        // Assert
        InOrder inOrder = inOrder(alunoService, calculadora);
        inOrder.verify(alunoService).exibirAluno(alunoCaptor.capture());
        inOrder.verify(calculadora).calcularMatches(any(Aluno.class), candidatosCaptor.capture());

        assertAll("Validação do fluxo da DemoView e console",
                () -> assertEquals("Ryan David", alunoCaptor.getValue().getNome()),
                () -> assertEquals(3, candidatosCaptor.getValue().size()),
                () -> assertTrue(output.contains("1º Lugar: Lucas Santana — Afinidade: 100%")),
                () -> assertTrue(output.contains("Mesma linha de pesquisa")),
                () -> assertTrue(output.contains("Horários em comum"))
        );
    }

    @Test
    @DisplayName("Deve exibir mensagem informativa quando não houver parceiros compatíveis retornados")
    void deveExibirMensagemInformativa_QuandoNaoHouverParceirosCompativeisRetornados() {
        // Arrange
        when(calculadora.calcularMatches(any(Aluno.class), anyList())).thenReturn(Collections.emptyList());

        // Act
        demoView.exibirTela(alunoService, calculadora);
        String output = outputStreamCaptor.toString();

        // Assert
        assertTrue(output.contains("Nenhum parceiro compatível encontrado no momento."));
    }
}
