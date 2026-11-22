package br.ucsal.matchtcc.demo;

import br.ucsal.matchtcc.demo.presentation.ApresentadorDemoTerminal;
import br.ucsal.matchtcc.demo.service.BaseDemoService;
import br.ucsal.matchtcc.model.Aluno;
import br.ucsal.matchtcc.model.ResultadoMatch;
import br.ucsal.matchtcc.service.CalculadoraMatch;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes Unitários para a classe DemoApplication")
class DemoApplicationTest {

    @Mock
    private BaseDemoService baseDemoService;

    @Mock
    private CalculadoraMatch calculadora;

    @Mock
    private ApresentadorDemoTerminal apresentador;

    @Test
    @DisplayName("Deve validar parâmetros não nulos no construtor parametrizado")
    void deveValidarParametrosNaoNulos_NoConstrutorParametrizado() {
        // Assert
        assertAll("Validação de nulos no construtor de DemoApplication",
                () -> assertThrows(NullPointerException.class, () ->
                        new DemoApplication(null, calculadora, apresentador)),
                () -> assertThrows(NullPointerException.class, () ->
                        new DemoApplication(baseDemoService, null, apresentador)),
                () -> assertThrows(NullPointerException.class, () ->
                        new DemoApplication(baseDemoService, calculadora, null))
        );
    }

    @Test
    @DisplayName("Deve inicializar com sucesso via construtor padrão")
    void deveInicializarComSucesso_ViaConstrutorPadrao() {
        // Act
        DemoApplication app = new DemoApplication();

        // Assert
        assertNotNull(app);
    }

    @Test
    @DisplayName("Deve orquestrar fluxo de demonstração na ordem correta com mocks isolados")
    @SuppressWarnings("unchecked")
    void deveOrquestrarFluxoDeDemonstracaoNaOrdemCorreta_ComMocksIsolados() {
        // Arrange
        DemoApplication app = new DemoApplication(baseDemoService, calculadora, apresentador);

        Aluno alunoBase = new Aluno("Ryan David", "BES", "Noturno", "Cibersegurança", "Sábados", "Backend", "aluno01@example.invalid");
        Aluno candidato = new Aluno("Lucas Santana", "BES", "Noturno", "Cibersegurança", "Sábados", "Frontend", "aluno02@example.invalid");
        List<Aluno> listaCandidatos = List.of(candidato);
        List<ResultadoMatch> resultados = List.of(new ResultadoMatch(candidato, 100, List.of("Mesmo tema")));

        when(baseDemoService.obterPerfilBaseDemo()).thenReturn(alunoBase);
        when(baseDemoService.carregarBaseDemo()).thenReturn(listaCandidatos);
        when(calculadora.calcularMatches(alunoBase, listaCandidatos)).thenReturn(resultados);

        ArgumentCaptor<Aluno> baseCaptor = ArgumentCaptor.forClass(Aluno.class);
        ArgumentCaptor<List<Aluno>> candidatosCaptor = ArgumentCaptor.forClass(List.class);
        ArgumentCaptor<List<ResultadoMatch>> resultadosCaptor = ArgumentCaptor.forClass(List.class);

        // Act
        app.iniciar();

        // Assert
        InOrder inOrder = inOrder(apresentador, baseDemoService, calculadora);
        inOrder.verify(apresentador).exibirCabecalho();
        inOrder.verify(baseDemoService).obterPerfilBaseDemo();
        inOrder.verify(apresentador).exibirPerfilAluno(baseCaptor.capture());
        inOrder.verify(baseDemoService).carregarBaseDemo();
        inOrder.verify(apresentador).exibirMensagemProcessamento(1);
        inOrder.verify(calculadora).calcularMatches(baseCaptor.capture(), candidatosCaptor.capture());
        inOrder.verify(apresentador).exibirResultados(resultadosCaptor.capture());

        assertAll("Validação da orquestração e dados transmitidos",
                () -> assertEquals("Ryan David", baseCaptor.getAllValues().get(0).getNome()),
                () -> assertEquals(1, candidatosCaptor.getValue().size()),
                () -> assertEquals(1, resultadosCaptor.getValue().size())
        );
    }

    @Test
    @DisplayName("Deve executar demonstração ponta a ponta com sucesso usando instâncias reais e dados locais CSV")
    void deveExecutarDemonstracaoPontaAPontaComSucesso_UsandoInstanciasReaisEDadosLocaisCsv() {
        // Arrange
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(output);
        ApresentadorDemoTerminal apresentadorReal = new ApresentadorDemoTerminal(printStream);

        DemoApplication app = new DemoApplication(
                new BaseDemoService(
                        new br.ucsal.matchtcc.demo.repository.RepositorioAlunosDemoCsv(),
                        new br.ucsal.matchtcc.demo.repository.RepositorioAlunosDemoCsv(
                                br.ucsal.matchtcc.demo.repository.RepositorioAlunosDemoCsv.CAMINHO_PERFIS_BUSCA_PADRAO
                        ),
                        new br.ucsal.matchtcc.demo.service.AnonimizacaoContatoService(
                                new br.ucsal.matchtcc.demo.strategy.SubstituidorContatoFicticio()
                        )
                ),
                new CalculadoraMatch(List.of(
                        new br.ucsal.matchtcc.strategy.CriterioTema(),
                        new br.ucsal.matchtcc.strategy.CriterioHorario(),
                        new br.ucsal.matchtcc.strategy.CriterioPapel()
                )),
                apresentadorReal
        );

        // Act
        app.iniciar();
        String console = output.toString();

        // Assert
        assertAll("Validação da execução ponta a ponta offline",
                () -> assertTrue(console.contains("MATCH TCC — SISTEMA DE FORMAÇÃO DE EQUIPES")),
                () -> assertTrue(console.contains("[PERFIL EM BUSCA DE EQUIPE]")),
                () -> assertTrue(console.contains("RESULTADO DAS RECOMENDAÇÕES")),
                () -> assertTrue(console.contains("1º Lugar:"))
        );
    }
}
