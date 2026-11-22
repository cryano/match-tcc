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

@DisplayName("Testes Unitários para a estratégia CriterioPapel")
class CriterioPapelTest {

    private CriterioPapel criterio;

    @BeforeEach
    void setUp() {
        criterio = new CriterioPapel();
    }

    private Aluno criarAluno(String papel) {
        return new Aluno("Teste", "BES", "Noturno", "IA", "Sábados", papel, "teste@ucsal.br");
    }

    @ParameterizedTest(name = "PapelBase: \"{0}\" | PapelCandidato: \"{1}\" => Pontos: {2}")
    @CsvSource({
            "Backend, Frontend, 20",
            "Frontend, Pesquisa/Dados, 20",
            "Backend, Documentação/Metodologia, 20",
            "Backend, backend, 0",
            "Frontend, FRONTEND, 0",
            "Pesquisa/Dados, Pesquisa/Dados, 0"
    })
    @DisplayName("Deve calcular 20 pontos para papéis complementares e 0 para papéis idênticos")
    void deveCalcularPontosCorretamente_QuandoAvaliarPapeis(String papelBase, String papelCandidato, int pontosEsperados) {
        // Arrange
        Aluno base = criarAluno(papelBase);
        Aluno candidato = criarAluno(papelCandidato);

        // Act
        int pontos = criterio.calcularPontos(base, candidato);

        // Assert
        assertEquals(pontosEsperados, pontos);
    }

    @Test
    @DisplayName("Deve retornar 20 pontos e justificativa com ambos os papéis quando forem complementares")
    void deveRetornarPontosEJustificativa_QuandoPapeisForemComplementares() {
        // Arrange
        Aluno base = criarAluno("Backend");
        Aluno candidato = criarAluno("Frontend");

        // Act
        int pontos = criterio.calcularPontos(base, candidato);
        String justificativa = criterio.obterJustificativa(base, candidato);

        // Assert
        assertAll("Validação de complementaridade de papéis",
                () -> assertEquals(20, pontos),
                () -> assertEquals("Habilidades complementares para o grupo (Backend + Frontend)", justificativa)
        );
    }

    @Test
    @DisplayName("Deve retornar zero pontos e justificativa nula quando papéis forem iguais")
    void deveRetornarZeroPontosEJustificativaNula_QuandoPapeisForemIguais() {
        // Arrange
        Aluno base = criarAluno("Backend");
        Aluno candidato = criarAluno("Backend");

        // Act
        int pontos = criterio.calcularPontos(base, candidato);
        String justificativa = criterio.obterJustificativa(base, candidato);

        // Assert
        assertAll("Validação de papéis idênticos",
                () -> assertEquals(0, pontos),
                () -> assertNull(justificativa)
        );
    }

    @Test
    @DisplayName("Deve retornar zero pontos e justificativa nula quando papel do aluno base for nulo")
    void deveRetornarZeroPontosEJustificativaNula_QuandoPapelBaseForNulo() {
        // Arrange
        Aluno base = criarAluno(null);
        Aluno candidato = criarAluno("Frontend");

        // Act
        int pontos = criterio.calcularPontos(base, candidato);
        String justificativa = criterio.obterJustificativa(base, candidato);

        // Assert
        assertAll("Papel base nulo",
                () -> assertEquals(0, pontos),
                () -> assertNull(justificativa)
        );
    }

    @Test
    @DisplayName("Deve retornar zero pontos e justificativa nula quando papel do candidato for nulo")
    void deveRetornarZeroPontosEJustificativaNula_QuandoPapelCandidatoForNulo() {
        // Arrange
        Aluno base = criarAluno("Backend");
        Aluno candidato = criarAluno(null);

        // Act
        int pontos = criterio.calcularPontos(base, candidato);
        String justificativa = criterio.obterJustificativa(base, candidato);

        // Assert
        assertAll("Papel candidato nulo",
                () -> assertEquals(0, pontos),
                () -> assertNull(justificativa)
        );
    }

    @Test
    @DisplayName("Deve retornar zero pontos e justificativa nula quando ambos os papéis forem nulos")
    void deveRetornarZeroPontosEJustificativaNula_QuandoAmbosPapeisForemNulos() {
        // Arrange
        Aluno base = criarAluno(null);
        Aluno candidato = criarAluno(null);

        // Act
        int pontos = criterio.calcularPontos(base, candidato);
        String justificativa = criterio.obterJustificativa(base, candidato);

        // Assert
        assertAll("Ambos papéis nulos",
                () -> assertEquals(0, pontos),
                () -> assertNull(justificativa)
        );
    }
}
