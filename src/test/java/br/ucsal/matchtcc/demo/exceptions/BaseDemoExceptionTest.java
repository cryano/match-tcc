package br.ucsal.matchtcc.demo.exceptions;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertInstanceOf;
import static org.junit.jupiter.api.Assertions.assertThrows;

@DisplayName("Testes Unitários para a exceção BaseDemoException")
class BaseDemoExceptionTest {

    @Test
    @DisplayName("Deve encapsular mensagem de erro quando instanciada apenas com mensagem")
    void deveEncapsularMensagemDeErro_QuandoInstanciadaApenasComMensagem() {
        // Arrange
        String mensagem = "Arquivo da base de demonstração não encontrado";

        // Act
        BaseDemoException exception = new BaseDemoException(mensagem);

        // Assert
        assertAll("Validação da mensagem e tipo da exceção",
                () -> assertInstanceOf(RuntimeException.class, exception),
                () -> assertEquals(mensagem, exception.getMessage())
        );
    }

    @Test
    @DisplayName("Deve encapsular mensagem e causa quando instanciada com causa")
    void deveEncapsularMensagemECausa_QuandoInstanciadaComCausa() {
        // Arrange
        String mensagem = "Erro de I/O ao ler CSV";
        IOException causa = new IOException("Disco inacessível");

        // Act
        BaseDemoException exception = new BaseDemoException(mensagem, causa);

        // Assert
        assertAll("Validação de mensagem e causa raiz",
                () -> assertEquals(mensagem, exception.getMessage()),
                () -> assertEquals(causa, exception.getCause())
        );
    }

    @Test
    @DisplayName("Deve ser capturada como RuntimeException quando lançada")
    void deveSerCapturadaComoRuntimeException_QuandoLancada() {
        // Arrange & Act & Assert
        BaseDemoException exception = assertThrows(BaseDemoException.class, () -> {
            throw new BaseDemoException("Erro inesperado");
        });

        assertEquals("Erro inesperado", exception.getMessage());
    }
}
