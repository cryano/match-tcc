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

@DisplayName("Testes Unitários para a estratégia CriterioTema")
class CriterioTemaTest {

    private CriterioTema criterio;

    @BeforeEach
    void setUp() {
        criterio = new CriterioTema();
    }

    private Aluno criarAluno(String tema) {
        return new Aluno("Teste", "BES", "Noturno", tema, "Sábados", "Backend", "teste@ucsal.br");
    }

    @ParameterizedTest(name = "TemaBase: \"{0}\" | TemaCandidato: \"{1}\" => Pontos: {2}")
    @CsvSource({
            "Cibersegurança, Cibersegurança, 50",
            "Cibersegurança, CIBERSEGURANÇA, 50",
            "Inteligência Artificial, inteligência artificial, 50",
            "Ciência de Dados, Engenharia de Software / Web, 0",
            "Mobile / IoT, Cibersegurança, 0"
    })
    @DisplayName("Deve calcular 50 pontos para temas iguais (ignorando maiúsculas) e 0 para temas diferentes")
    void deveCalcularPontosCorretamente_QuandoAvaliarTemas(String temaBase, String temaCandidato, int pontosEsperados) {
        // Arrange
        Aluno base = criarAluno(temaBase);
        Aluno candidato = criarAluno(temaCandidato);

        // Act
        int pontos = criterio.calcularPontos(base, candidato);

        // Assert
        assertEquals(pontosEsperados, pontos);
    }

    @Test
    @DisplayName("Deve retornar 50 pontos e justificativa formatada quando tema de interesse for o mesmo")
    void deveRetornarPontosEJustificativa_QuandoTemaForOMesmo() {
        // Arrange
        Aluno base = criarAluno("Cibersegurança");
        Aluno candidato = criarAluno("Cibersegurança");

        // Act
        int pontos = criterio.calcularPontos(base, candidato);
        String justificativa = criterio.obterJustificativa(base, candidato);

        // Assert
        assertAll("Validação de afinidade de tema",
                () -> assertEquals(50, pontos),
                () -> assertEquals("Mesma linha de pesquisa de interesse (Cibersegurança)", justificativa)
        );
    }

    @Test
    @DisplayName("Deve retornar zero pontos e justificativa nula quando temas forem diferentes")
    void deveRetornarZeroPontosEJustificativaNula_QuandoTemasForemDiferentes() {
        // Arrange
        Aluno base = criarAluno("Cibersegurança");
        Aluno candidato = criarAluno("Inteligência Artificial");

        // Act
        int pontos = criterio.calcularPontos(base, candidato);
        String justificativa = criterio.obterJustificativa(base, candidato);

        // Assert
        assertAll("Validação de temas distintos",
                () -> assertEquals(0, pontos),
                () -> assertNull(justificativa)
        );
    }

    @Test
    @DisplayName("Deve retornar zero pontos e justificativa nula quando tema do aluno base for nulo")
    void deveRetornarZeroPontosEJustificativaNula_QuandoTemaBaseForNulo() {
        // Arrange
        Aluno base = criarAluno(null);
        Aluno candidato = criarAluno("Cibersegurança");

        // Act
        int pontos = criterio.calcularPontos(base, candidato);
        String justificativa = criterio.obterJustificativa(base, candidato);

        // Assert
        assertAll("Tema base nulo",
                () -> assertEquals(0, pontos),
                () -> assertNull(justificativa)
        );
    }

    @Test
    @DisplayName("Deve retornar zero pontos e justificativa nula quando tema do candidato for nulo")
    void deveRetornarZeroPontosEJustificativaNula_QuandoTemaCandidatoForNulo() {
        // Arrange
        Aluno base = criarAluno("Cibersegurança");
        Aluno candidato = criarAluno(null);

        // Act
        int pontos = criterio.calcularPontos(base, candidato);
        String justificativa = criterio.obterJustificativa(base, candidato);

        // Assert
        assertAll("Tema candidato nulo",
                () -> assertEquals(0, pontos),
                () -> assertNull(justificativa)
        );
    }

    @Test
    @DisplayName("Deve retornar zero pontos e justificativa nula quando ambos os temas forem nulos")
    void deveRetornarZeroPontosEJustificativaNula_QuandoAmbosTemasForemNulos() {
        // Arrange
        Aluno base = criarAluno(null);
        Aluno candidato = criarAluno(null);

        // Act
        int pontos = criterio.calcularPontos(base, candidato);
        String justificativa = criterio.obterJustificativa(base, candidato);

        // Assert
        assertAll("Ambos temas nulos",
                () -> assertEquals(0, pontos),
                () -> assertNull(justificativa)
        );
    }
}
