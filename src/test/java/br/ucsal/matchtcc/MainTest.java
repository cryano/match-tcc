package br.ucsal.matchtcc;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Testes Unitários para a classe Main")
class MainTest {

    private final InputStream standardIn = System.in;
    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    void tearDown() {
        System.setIn(standardIn);
        System.setOut(standardOut);
    }

    @Test
    @DisplayName("Deve instanciar a classe Main com sucesso")
    void deveInstanciarClasseMainComSucesso() {
        // Act
        Main main = new Main();

        // Assert
        assertNotNull(main);
    }

    @Test
    @DisplayName("Deve executar o método main com sucesso quando executado no modo Demo")
    void deveExecutarOMetodoMainComSucesso_QuandoExecutadoNoModoDemo() {
        // Arrange
        ByteArrayInputStream input = new ByteArrayInputStream("2\n".getBytes());
        System.setIn(input);

        // Act
        Main.main(new String[0]);
        String output = outputStreamCaptor.toString();

        // Assert
        assertAll("Validação da execução do main em modo Demo",
                () -> assertTrue(output.contains("MATCH TCC — SISTEMA DE FORMAÇÃO DE EQUIPES")),
                () -> assertTrue(output.contains("RESULTADO DAS RECOMENDAÇÕES")),
                () -> assertTrue(output.contains("1º Lugar: Lucas Santana — Afinidade: 100%"))
        );
    }
}
