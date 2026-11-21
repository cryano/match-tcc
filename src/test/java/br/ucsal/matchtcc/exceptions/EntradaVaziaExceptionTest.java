package br.ucsal.matchtcc.exceptions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Testes Unitários para a exceção EntradaVaziaException")
class EntradaVaziaExceptionTest {

    @Test
    @DisplayName("Deve encapsular mensagem de erro quando instanciada")
    void deveEncapsularMensagemDeErro_QuandoInstanciada() {
        // Arrange
        String mensagemEsperada = "Entrada não pode ser vazia. Tente novamente.";

        // Act
        EntradaVaziaException exception = new EntradaVaziaException(mensagemEsperada);

        // Assert
        assertAll("Validação da exceção",
                () -> assertInstanceOf(RuntimeException.class, exception),
                () -> assertEquals(mensagemEsperada, exception.getMessage())
        );
    }

    @Test
    @DisplayName("Deve ser capturada como RuntimeException quando lançada")
    void deveSerCapturadaComoRuntimeException_QuandoLancada() {
        // Arrange
        String mensagem = "Campo obrigatório";

        // Act & Assert
        EntradaVaziaException exception = assertThrows(EntradaVaziaException.class, () -> {
            throw new EntradaVaziaException(mensagem);
        });

        assertEquals(mensagem, exception.getMessage());
    }
}
