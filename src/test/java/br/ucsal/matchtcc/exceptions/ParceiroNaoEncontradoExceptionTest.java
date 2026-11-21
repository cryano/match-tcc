package br.ucsal.matchtcc.exceptions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Testes Unitários para a exceção ParceiroNaoEncontradoException")
class ParceiroNaoEncontradoExceptionTest {

    @Test
    @DisplayName("Deve encapsular mensagem de erro quando instanciada")
    void deveEncapsularMensagemDeErro_QuandoInstanciada() {
        // Arrange
        String mensagemEsperada = "Nenhum parceiro compatível encontrado no momento.";

        // Act
        ParceiroNaoEncontradoException exception = new ParceiroNaoEncontradoException(mensagemEsperada);

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
        String mensagem = "Nenhum parceiro encontrado";

        // Act & Assert
        ParceiroNaoEncontradoException exception = assertThrows(ParceiroNaoEncontradoException.class, () -> {
            throw new ParceiroNaoEncontradoException(mensagem);
        });

        assertEquals(mensagem, exception.getMessage());
    }
}
