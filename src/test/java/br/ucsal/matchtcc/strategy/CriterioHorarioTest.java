package br.ucsal.matchtcc.strategy;

import br.ucsal.matchtcc.model.Aluno;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DisplayName("Testes Unitários para a estratégia CriterioHorario")
class CriterioHorarioTest {

    private CriterioHorario criterio;

    @BeforeEach
    void setUp() {
        criterio = new CriterioHorario();
    }

    private Aluno criarAluno(String disponibilidade) {
        return new Aluno("Teste", "BES", "Noturno", "IA", disponibilidade, "Backend", "teste@ucsal.br");
    }

    @ParameterizedTest(name = "DispBase: \"{0}\" | DispCandidato: \"{1}\" => Pontos Esperados: {2}")
    @CsvSource({
            "Sábados e Noites, Sábados, 30",
            "Sábados, SÁBADO pela manhã, 30",
            "Noites durante a semana, noite, 30",
            "final de semana, final de semana completo, 30",
            "Finais de semana, final de semana, 0",
            "Apenas manhãs, Apenas tardes, 0",
            "Segunda de manhã, Terça à tarde, 0"
    })
    @DisplayName("Deve calcular pontuação correta quando avaliar combinações de horários")
    void deveCalcularPontuacaoCorreta_QuandoAvaliarCombinacoesDeHorarios(String dispBase, String dispCandidato, int pontosEsperados) {
        // Arrange
        Aluno base = criarAluno(dispBase);
        Aluno candidato = criarAluno(dispCandidato);

        // Act
        int pontos = criterio.calcularPontos(base, candidato);

        // Assert
        assertEquals(pontosEsperados, pontos);
    }

    @Test
    @DisplayName("Deve retornar 30 pontos e justificativa formatada quando houver horários compatíveis")
    void deveRetornarPontosEJustificativa_QuandoHouverHorariosCompativeis() {
        // Arrange
        Aluno base = criarAluno("Sábados e Noites");
        Aluno candidato = criarAluno("Sábados");

        // Act
        int pontos = criterio.calcularPontos(base, candidato);
        String justificativa = criterio.obterJustificativa(base, candidato);

        // Assert
        assertAll("Validação de pontuação e justificativa para horários compatíveis",
                () -> assertEquals(30, pontos),
                () -> assertEquals("Horários de estudo em comum (Sábados)", justificativa)
        );
    }

    @Test
    @DisplayName("Deve retornar zero pontos e justificativa nula quando horários forem incompatíveis")
    void deveRetornarZeroPontosEJustificativaNula_QuandoHorariosForemIncompativeis() {
        // Arrange
        Aluno base = criarAluno("Manhã");
        Aluno candidato = criarAluno("Tarde");

        // Act
        int pontos = criterio.calcularPontos(base, candidato);
        String justificativa = criterio.obterJustificativa(base, candidato);

        // Assert
        assertAll("Validação de incompatibilidade de horários",
                () -> assertEquals(0, pontos),
                () -> assertNull(justificativa)
        );
    }

    @Test
    @DisplayName("Deve retornar zero pontos e justificativa nula quando disponibilidade do aluno base for nula")
    void deveRetornarZeroPontosEJustificativaNula_QuandoDisponibilidadeBaseForNula() {
        // Arrange
        Aluno base = criarAluno(null);
        Aluno candidato = criarAluno("Sábados");

        // Act
        int pontos = criterio.calcularPontos(base, candidato);
        String justificativa = criterio.obterJustificativa(base, candidato);

        // Assert
        assertAll("Disponibilidade base nula",
                () -> assertEquals(0, pontos),
                () -> assertNull(justificativa)
        );
    }

    @Test
    @DisplayName("Deve retornar zero pontos e justificativa nula quando disponibilidade do candidato for nula")
    void deveRetornarZeroPontosEJustificativaNula_QuandoDisponibilidadeCandidatoForNula() {
        // Arrange
        Aluno base = criarAluno("Sábados");
        Aluno candidato = criarAluno(null);

        // Act
        int pontos = criterio.calcularPontos(base, candidato);
        String justificativa = criterio.obterJustificativa(base, candidato);

        // Assert
        assertAll("Disponibilidade candidato nula",
                () -> assertEquals(0, pontos),
                () -> assertNull(justificativa)
        );
    }

    @Test
    @DisplayName("Deve retornar zero pontos e justificativa nula quando ambas disponibilidades forem nulas")
    void deveRetornarZeroPontosEJustificativaNula_QuandoAmbasDisponibilidadesForemNulas() {
        // Arrange
        Aluno base = criarAluno(null);
        Aluno candidato = criarAluno(null);

        // Act
        int pontos = criterio.calcularPontos(base, candidato);
        String justificativa = criterio.obterJustificativa(base, candidato);

        // Assert
        assertAll("Ambas disponibilidades nulas",
                () -> assertEquals(0, pontos),
                () -> assertNull(justificativa)
        );
    }
}
