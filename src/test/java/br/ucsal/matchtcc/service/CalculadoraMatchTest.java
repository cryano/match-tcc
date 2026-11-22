package br.ucsal.matchtcc.service;

import br.ucsal.matchtcc.model.Aluno;
import br.ucsal.matchtcc.model.ResultadoMatch;
import br.ucsal.matchtcc.strategy.CriterioHorario;
import br.ucsal.matchtcc.strategy.CriterioMatch;
import br.ucsal.matchtcc.strategy.CriterioPapel;
import br.ucsal.matchtcc.strategy.CriterioTema;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InOrder;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.inOrder;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes Unitários para o serviço CalculadoraMatch")
class CalculadoraMatchTest {

    @Mock
    private CriterioMatch criterio1;

    @Mock
    private CriterioMatch criterio2;

    @Test
    @DisplayName("Deve calcular matches e ordenar decrescentemente quando houver candidatos válidos")
    void deveCalcularMatchesEOrdenarDecrescentemente_QuandoHouverCandidatosValidos() {
        // Arrange
        CalculadoraMatch calculadora = new CalculadoraMatch(List.of(criterio1, criterio2));

        Aluno base = new Aluno("Ryan David", "BES", "Noturno", "Cibersegurança", "Sábados", "Backend", "ryan@ucsal.br");
        Aluno candidato1 = new Aluno("Lucas Santana", "BES", "Noturno", "Cibersegurança", "Sábados", "Frontend", "lucas@ucsal.br");
        Aluno candidato2 = new Aluno("Beatriz Lima", "BES", "Noturno", "IA", "Sábados", "Backend", "beatriz@ucsal.br");

        when(criterio1.calcularPontos(base, candidato1)).thenReturn(50);
        when(criterio1.obterJustificativa(base, candidato1)).thenReturn("Mesmo tema");
        when(criterio2.calcularPontos(base, candidato1)).thenReturn(30);
        when(criterio2.obterJustificativa(base, candidato1)).thenReturn("Horário comum");

        when(criterio1.calcularPontos(base, candidato2)).thenReturn(0);
        when(criterio1.obterJustificativa(base, candidato2)).thenReturn(null);
        when(criterio2.calcularPontos(base, candidato2)).thenReturn(30);
        when(criterio2.obterJustificativa(base, candidato2)).thenReturn("Horário comum");

        // Act
        List<ResultadoMatch> resultados = calculadora.calcularMatches(base, List.of(candidato1, candidato2));

        // Assert
        assertAll("Validação da ordenação e pontuações calculadas",
                () -> assertEquals(2, resultados.size()),
                () -> assertEquals("Lucas Santana", resultados.get(0).getAlunoCandidato().getNome()),
                () -> assertEquals(80, resultados.get(0).getPontuacaoTotal()),
                () -> assertEquals(2, resultados.get(0).getJustificativas().size()),
                () -> assertEquals("Beatriz Lima", resultados.get(1).getAlunoCandidato().getNome()),
                () -> assertEquals(30, resultados.get(1).getPontuacaoTotal()),
                () -> assertEquals(1, resultados.get(1).getJustificativas().size())
        );

        // Validação da ordem de execução com InOrder
        InOrder inOrder = inOrder(criterio1, criterio2);
        inOrder.verify(criterio1).calcularPontos(base, candidato1);
        inOrder.verify(criterio1).obterJustificativa(base, candidato1);
        inOrder.verify(criterio2).calcularPontos(base, candidato1);
        inOrder.verify(criterio2).obterJustificativa(base, candidato1);
    }

    @Test
    @DisplayName("Deve capturar argumentos passados aos critérios utilizando ArgumentCaptor")
    void deveCapturarArgumentosPassadosAosCriterios_UtilizandoArgumentCaptor() {
        // Arrange
        CalculadoraMatch calculadora = new CalculadoraMatch(List.of(criterio1));
        Aluno base = new Aluno("Ryan David", "BES", "Noturno", "IA", "Sábados", "Backend", "ryan@ucsal.br");
        Aluno candidato = new Aluno("Beatriz Lima", "BES", "Noturno", "IA", "Sábados", "Frontend", "beatriz@ucsal.br");

        when(criterio1.calcularPontos(base, candidato)).thenReturn(50);
        when(criterio1.obterJustificativa(base, candidato)).thenReturn("Tema comum");

        ArgumentCaptor<Aluno> baseCaptor = ArgumentCaptor.forClass(Aluno.class);
        ArgumentCaptor<Aluno> candCaptor = ArgumentCaptor.forClass(Aluno.class);

        // Act
        calculadora.calcularMatches(base, List.of(candidato));

        // Assert
        InOrder inOrder = inOrder(criterio1);
        inOrder.verify(criterio1).calcularPontos(baseCaptor.capture(), candCaptor.capture());
        inOrder.verify(criterio1).obterJustificativa(baseCaptor.capture(), candCaptor.capture());

        assertAll("Validação dos argumentos capturados",
                () -> assertEquals("Ryan David", baseCaptor.getAllValues().get(0).getNome()),
                () -> assertEquals("Beatriz Lima", candCaptor.getAllValues().get(0).getNome())
        );
    }

    @Test
    @DisplayName("Deve limitar pontuação em 100% quando a soma dos critérios ultrapassar o teto")
    void deveLimitarPontuacaoEmCem_QuandoSomaDosCriteriosUltrapassarTeto() {
        // Arrange
        CalculadoraMatch calculadora = new CalculadoraMatch(List.of(criterio1, criterio2));
        Aluno base = new Aluno("Ryan", "BES", "Noturno", "IA", "Sábado", "Backend", "ryan@ucsal.br");
        Aluno candidato = new Aluno("Lucas", "BES", "Noturno", "IA", "Sábado", "Frontend", "lucas@ucsal.br");

        when(criterio1.calcularPontos(base, candidato)).thenReturn(70);
        when(criterio1.obterJustificativa(base, candidato)).thenReturn("Motivo 1");
        when(criterio2.calcularPontos(base, candidato)).thenReturn(50);
        when(criterio2.obterJustificativa(base, candidato)).thenReturn("Motivo 2");

        // Act
        List<ResultadoMatch> resultados = calculadora.calcularMatches(base, List.of(candidato));

        // Assert
        assertAll("Pontuação limitada ao teto de 100",
                () -> assertEquals(1, resultados.size()),
                () -> assertEquals(100, resultados.get(0).getPontuacaoTotal())
        );
    }

    @Test
    @DisplayName("Deve desconsiderar candidato quando for o próprio estudante base")
    void deveDesconsiderarCandidato_QuandoForOProprioEstudanteBase() {
        // Arrange
        CalculadoraMatch calculadora = new CalculadoraMatch(List.of(criterio1));
        Aluno base = new Aluno("Ryan David", "BES", "Noturno", "IA", "Sábado", "Backend", "ryan@ucsal.br");
        Aluno mesmoAlunoMesmaInstancia = base;
        Aluno mesmoAlunoOutraInstancia = new Aluno("ryan david", "BES", "Noturno", "IA", "Sábado", "Backend", "outro@ucsal.br");
        Aluno outroAluno = new Aluno("Lucas Santana", "BES", "Noturno", "IA", "Sábado", "Frontend", "lucas@ucsal.br");

        when(criterio1.calcularPontos(base, outroAluno)).thenReturn(50);
        when(criterio1.obterJustificativa(base, outroAluno)).thenReturn("Compatível");

        // Act
        List<ResultadoMatch> resultados = calculadora.calcularMatches(base, List.of(mesmoAlunoMesmaInstancia, mesmoAlunoOutraInstancia, outroAluno));

        // Assert
        assertAll("Verificação de filtro do próprio estudante",
                () -> assertEquals(1, resultados.size()),
                () -> assertEquals("Lucas Santana", resultados.get(0).getAlunoCandidato().getNome())
        );
    }

    @Test
    @DisplayName("Deve processar candidato com nome nulo sem lançar exceção")
    void deveProcessarCandidatoComNomeNulo_SemLancarExcecao() {
        // Arrange
        CalculadoraMatch calculadora = new CalculadoraMatch(List.of(criterio1));
        Aluno base = new Aluno("Ryan", "BES", "Noturno", "IA", "Sábado", "Backend", "ryan@ucsal.br");
        Aluno candidatoComNomeNulo = new Aluno(null, "BES", "Noturno", "IA", "Sábado", "Frontend", "semnome@ucsal.br");

        when(criterio1.calcularPontos(base, candidatoComNomeNulo)).thenReturn(20);
        when(criterio1.obterJustificativa(base, candidatoComNomeNulo)).thenReturn("Critério atendido");

        // Act
        List<ResultadoMatch> resultados = calculadora.calcularMatches(base, List.of(candidatoComNomeNulo));

        // Assert
        assertAll("Validação de candidato com nome nulo",
                () -> assertEquals(1, resultados.size()),
                () -> assertEquals(20, resultados.get(0).getPontuacaoTotal()),
                () -> assertNull(resultados.get(0).getAlunoCandidato().getNome())
        );
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando aluno base for nulo")
    void deveRetornarListaVazia_QuandoAlunoBaseForNulo() {
        // Arrange
        CalculadoraMatch calculadora = new CalculadoraMatch(List.of(criterio1));
        Aluno candidato = new Aluno("Lucas", "BES", "Noturno", "IA", "Sábado", "Frontend", "lucas@ucsal.br");

        // Act
        List<ResultadoMatch> resultados = calculadora.calcularMatches(null, List.of(candidato));

        // Assert
        assertAll("Retorno para aluno base nulo",
                () -> assertNotNull(resultados),
                () -> assertTrue(resultados.isEmpty())
        );
        verifyNoInteractions(criterio1);
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando lista de candidatos for nula")
    void deveRetornarListaVazia_QuandoListaDeCandidatosForNula() {
        // Arrange
        CalculadoraMatch calculadora = new CalculadoraMatch(List.of(criterio1));
        Aluno base = new Aluno("Ryan", "BES", "Noturno", "IA", "Sábado", "Backend", "ryan@ucsal.br");

        // Act
        List<ResultadoMatch> resultados = calculadora.calcularMatches(base, null);

        // Assert
        assertAll("Retorno para candidatos nulos",
                () -> assertNotNull(resultados),
                () -> assertTrue(resultados.isEmpty())
        );
        verifyNoInteractions(criterio1);
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando lista de candidatos for vazia")
    void deveRetornarListaVazia_QuandoListaDeCandidatosForVazia() {
        // Arrange
        CalculadoraMatch calculadora = new CalculadoraMatch(List.of(criterio1));
        Aluno base = new Aluno("Ryan", "BES", "Noturno", "IA", "Sábado", "Backend", "ryan@ucsal.br");

        // Act
        List<ResultadoMatch> resultados = calculadora.calcularMatches(base, List.of());

        // Assert
        assertAll("Retorno para candidatos vazios",
                () -> assertNotNull(resultados),
                () -> assertTrue(resultados.isEmpty())
        );
        verifyNoInteractions(criterio1);
    }

    @Test
    @DisplayName("Deve ignorar justificativas nulas ou em branco retornadas pelos critérios")
    void deveIgnorarJustificativasNulasOuEmBranco_RetornadasPelosCriterios() {
        // Arrange
        CalculadoraMatch calculadora = new CalculadoraMatch(List.of(criterio1, criterio2));
        Aluno base = new Aluno("Ryan", "BES", "Noturno", "IA", "Sábado", "Backend", "ryan@ucsal.br");
        Aluno candidato = new Aluno("Lucas", "BES", "Noturno", "IA", "Sábado", "Frontend", "lucas@ucsal.br");

        when(criterio1.calcularPontos(base, candidato)).thenReturn(10);
        when(criterio1.obterJustificativa(base, candidato)).thenReturn(null);
        when(criterio2.calcularPontos(base, candidato)).thenReturn(20);
        when(criterio2.obterJustificativa(base, candidato)).thenReturn("   ");

        // Act
        List<ResultadoMatch> resultados = calculadora.calcularMatches(base, List.of(candidato));

        // Assert
        assertAll("Validação de justificativas em branco/nulas desconsideradas",
                () -> assertEquals(1, resultados.size()),
                () -> assertEquals(30, resultados.get(0).getPontuacaoTotal()),
                () -> assertTrue(resultados.get(0).getJustificativas().isEmpty())
        );
    }

    @Test
    @DisplayName("Deve inicializar lista vazia de critérios quando passado nulo no construtor")
    void deveInicializarListaVaziaDeCriterios_QuandoPassadoNuloNoConstrutor() {
        // Arrange
        CalculadoraMatch calculadora = new CalculadoraMatch(null);
        Aluno base = new Aluno("Ryan", "BES", "Noturno", "IA", "Sábado", "Backend", "ryan@ucsal.br");
        Aluno candidato = new Aluno("Lucas", "BES", "Noturno", "IA", "Sábado", "Frontend", "lucas@ucsal.br");

        // Act
        List<ResultadoMatch> resultados = calculadora.calcularMatches(base, List.of(candidato));

        // Assert
        assertAll("Calculadora sem critérios",
                () -> assertEquals(1, resultados.size()),
                () -> assertEquals(0, resultados.get(0).getPontuacaoTotal()),
                () -> assertTrue(resultados.get(0).getJustificativas().isEmpty())
        );
    }

    @Test
    @DisplayName("Deve calcular matches corretamente integrado com critérios de produção reais")
    void deveCalcularMatchesCorretamente_IntegradoComCriteriosDeProducaoReais() {
        // Arrange
        CalculadoraMatch calculadora = new CalculadoraMatch(List.of(
                new CriterioTema(),
                new CriterioHorario(),
                new CriterioPapel()
        ));

        Aluno base = new Aluno("Ryan David", "BES", "Noturno", "Cibersegurança", "Sábados e Noites", "Backend", "ryan@ucsal.br");

        Aluno comp100 = new Aluno("Lucas Santana", "BES", "Noturno", "Cibersegurança", "Sábados", "Frontend", "lucas@ucsal.br");
        // Tema: 50, Horario: 30, Papel: 20 -> 100 pontos, 3 justificativas

        Aluno comp30 = new Aluno("Beatriz Lima", "BES", "Noturno", "IA", "Sábados e Noites", "Backend", "beatriz@ucsal.br");
        // Tema: 0, Horario: 30, Papel: 0 -> 30 pontos, 1 justificativa

        Aluno comp0 = new Aluno("Camila Rocha", "ADS", "Matutino", "Ciência de Dados", "Tardes", "Backend", "camila@ucsal.br");
        // Tema: 0, Horario: 0, Papel: 0 -> 0 pontos, 0 justificativas

        // Act
        List<ResultadoMatch> resultados = calculadora.calcularMatches(base, List.of(comp0, comp100, comp30));

        // Assert
        assertAll("Validação do cálculo com estratégias reais",
                () -> assertEquals(3, resultados.size()),
                () -> assertEquals("Lucas Santana", resultados.get(0).getAlunoCandidato().getNome()),
                () -> assertEquals(100, resultados.get(0).getPontuacaoTotal()),
                () -> assertEquals(3, resultados.get(0).getJustificativas().size()),
                () -> assertEquals("Beatriz Lima", resultados.get(1).getAlunoCandidato().getNome()),
                () -> assertEquals(30, resultados.get(1).getPontuacaoTotal()),
                () -> assertEquals(1, resultados.get(1).getJustificativas().size()),
                () -> assertEquals("Camila Rocha", resultados.get(2).getAlunoCandidato().getNome()),
                () -> assertEquals(0, resultados.get(2).getPontuacaoTotal()),
                () -> assertTrue(resultados.get(2).getJustificativas().isEmpty())
        );
    }
}
