package br.ucsal.matchtcc.demo.strategy;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.NullAndEmptySource;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

@DisplayName("Testes Unitários para a estratégia AnonimizadorMascaraContato")
class AnonimizadorMascaraContatoTest {

    private AnonimizadorMascaraContato anonimizador;

    @BeforeEach
    void setUp() {
        anonimizador = new AnonimizadorMascaraContato();
    }

    @ParameterizedTest(name = "Entrada: \"{0}\" => Esperado: \"{1}\"")
    @CsvSource({
            "ryan.david@ucsal.edu.br, ry***@ucsal.edu.br",
            "lucas@gmail.com, lu***@gmail.com",
            "ab@dominio.com, a***@dominio.com",
            "a@dominio.com, ***@dominio.com",
            "telefone12345, te***",
            "xy, x***",
            "z, ***"
    })
    @DisplayName("Deve mascarar contato corretamente preservando domínio quando for e-mail ou mascarando texto")
    void deveMascararContatoCorretamente_QuandoInformadoContatoValido(String contatoOriginal, String esperado) {
        // Act
        String resultado = anonimizador.anonimizar(contatoOriginal);

        // Assert
        assertEquals(esperado, resultado);
    }

    @ParameterizedTest
    @NullAndEmptySource
    @ValueSource(strings = {"   ", "\t", "\n"})
    @DisplayName("Deve retornar valor padrão anônimo quando contato for nulo, vazio ou em branco")
    void deveRetornarValorPadraoAnonimo_QuandoContatoForNuloOuEmBranco(String contatoInvalido) {
        // Act
        String resultado = anonimizador.anonimizar(contatoInvalido);

        // Assert
        assertEquals("***@example.invalid", resultado);
    }

    @Test
    @DisplayName("Deve mascarar como texto simples quando arroba estiver no início ou no fim")
    void deveMascararComoTextoSimples_QuandoArrobaEstiverNoInicioOuNoFim() {
        // Arrange & Act
        String inicioArroba = anonimizador.anonimizar("@dominio.com");
        String fimArroba = anonimizador.anonimizar("usuario@");

        // Assert
        assertEquals("@d***", inicioArroba);
        assertEquals("us***", fimArroba);
    }
}
