package br.ucsal.matchtcc.exceptions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Testes Unitários para a exceção EscolhaInvalidaException")
class EscolhaInvalidaExceptionTest {

    @Test
    @DisplayName("Deve encapsular mensagem de erro quando instanciada")
    void deveEncapsularMensagemDeErro_QuandoInstanciada() {
        // Arrange
        String mensagemEsperada = "Deve-se escolher dentro das opções disponíveis!";

        // Act
        EscolhaInvalidaException exception = new EscolhaInvalidaException(mensagemEsperada);

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
        String mensagem = "Opção inválida";

        // Act & Assert
        EscolhaInvalidaException exception = assertThrows(EscolhaInvalidaException.class, () -> {
            throw new EscolhaInvalidaException(mensagem);
        });

        assertEquals(mensagem, exception.getMessage());
    }
}
