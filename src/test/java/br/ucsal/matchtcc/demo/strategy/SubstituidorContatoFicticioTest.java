package br.ucsal.matchtcc.demo.strategy;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Testes Unitários para a estratégia SubstituidorContatoFicticio")
class SubstituidorContatoFicticioTest {

    @Test
    @DisplayName("Deve inicializar com valores padrão quando utilizar construtor sem argumentos")
    void deveInicializarComValoresPadrao_QuandoUtilizarConstrutorSemArgumentos() {
        // Arrange & Act
        SubstituidorContatoFicticio substituidor = new SubstituidorContatoFicticio();

        // Assert
        assertAll("Validação dos valores padrão",
                () -> assertEquals("aluno", substituidor.getPrefixo()),
                () -> assertEquals("example.invalid", substituidor.getDominioFicticio())
        );
    }

    @Test
    @DisplayName("Deve inicializar com prefixo e domínio customizados quando válidos")
    void deveInicializarComPrefixoEDominioCustomizados_QuandoValidos() {
        // Arrange & Act
        SubstituidorContatoFicticio substituidor = new SubstituidorContatoFicticio("estudante", "demo.local");

        // Assert
        assertAll("Validação dos valores customizados",
                () -> assertEquals("estudante", substituidor.getPrefixo()),
                () -> assertEquals("demo.local", substituidor.getDominioFicticio())
        );
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   ", "\t"})
    @DisplayName("Deve lançar IllegalArgumentException quando prefixo for nulo ou em branco")
    void deveLancarIllegalArgumentException_QuandoPrefixoForNuloOuEmBranco(String prefixoInvalido) {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                new SubstituidorContatoFicticio(prefixoInvalido, "example.invalid")
        );

        assertEquals("O prefixo do contato fictício não pode ser vazio.", exception.getMessage());
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   ", "\t"})
    @DisplayName("Deve lançar IllegalArgumentException quando domínio for nulo ou em branco")
    void deveLancarIllegalArgumentException_QuandoDominioForNuloOuEmBranco(String dominioInvalido) {
        // Act & Assert
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () ->
                new SubstituidorContatoFicticio("aluno", dominioInvalido)
        );

        assertEquals("O domínio fictício não pode ser vazio.", exception.getMessage());
    }

    @Test
    @DisplayName("Deve gerar contatos sequenciais formatados quando contato for real ou nulo")
    void deveGerarContatosSequenciaisFormatados_QuandoContatoForRealOuNulo() {
        // Arrange
        SubstituidorContatoFicticio substituidor = new SubstituidorContatoFicticio();

        // Act
        String c1 = substituidor.anonimizar("ryan@ucsal.edu.br");
        String c2 = substituidor.anonimizar("lucas@gmail.com");
        String c3 = substituidor.anonimizar(null);

        // Assert
        assertAll("Validação da sequência numérica e formato",
                () -> assertEquals("aluno01@example.invalid", c1),
                () -> assertEquals("aluno02@example.invalid", c2),
                () -> assertEquals("aluno03@example.invalid", c3)
        );
    }

    @Test
    @DisplayName("Deve preservar e normalizar em minúsculas quando contato já estiver no formato simulado")
    void devePreservarENormalizar_QuandoContatoJaEstiverNoFormatoSimulado() {
        // Arrange
        SubstituidorContatoFicticio substituidor = new SubstituidorContatoFicticio();

        // Act
        String resultado = substituidor.anonimizar("ALUNO05@EXAMPLE.INVALID");

        // Assert
        assertEquals("aluno05@example.invalid", resultado);
    }
}
