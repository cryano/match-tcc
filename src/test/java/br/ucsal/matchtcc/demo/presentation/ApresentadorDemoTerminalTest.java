package br.ucsal.matchtcc.demo.presentation;

import br.ucsal.matchtcc.model.Aluno;
import br.ucsal.matchtcc.model.ResultadoMatch;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Testes Unitários para a classe ApresentadorDemoTerminal")
class ApresentadorDemoTerminalTest {

    private ByteArrayOutputStream outputStream;
    private ApresentadorDemoTerminal apresentador;

    @BeforeEach
    void setUp() {
        outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream, true, StandardCharsets.UTF_8);
        apresentador = new ApresentadorDemoTerminal(printStream);
    }

    private String getOutput() {
        return outputStream.toString(StandardCharsets.UTF_8);
    }

    @Test
    @DisplayName("Deve inicializar com System.out quando invocado construtor sem argumentos")
    void deveInicializarComSystemOut_QuandoInvocadoConstrutorSemArgumentos() {
        // Arrange & Act
        ApresentadorDemoTerminal apresentadorPadrao = new ApresentadorDemoTerminal();

        // Assert
        assertNotNull(apresentadorPadrao);
    }

    @Test
    @DisplayName("Deve lançar NullPointerException quando PrintStream for nulo")
    void deveLancarNullPointerException_QuandoPrintStreamForNulo() {
        // Act & Assert
        NullPointerException exception = assertThrows(NullPointerException.class, () ->
                new ApresentadorDemoTerminal(null)
        );

        assertEquals("O fluxo de saída não pode ser nulo.", exception.getMessage());
    }

    @Test
    @DisplayName("Deve exibir cabeçalho do sistema formatado")
    void deveExibirCabecalhoDoSistemaFormatado() {
        // Act
        apresentador.exibirCabecalho();

        // Assert
        assertAll("Validação do cabeçalho",
                () -> assertTrue(getOutput().contains("MATCH TCC — SISTEMA DE FORMAÇÃO DE EQUIPES")),
                () -> assertTrue(getOutput().contains("Laboratório POOALAB — UCSal 2026.2"))
        );
    }

    @Test
    @DisplayName("Deve exibir dados completos do perfil do aluno")
    void deveExibirDadosCompletosDoPerfilDoAluno() {
        // Arrange
        Aluno aluno = new Aluno("Ryan David", "BES", "Noturno", "Cibersegurança", "Sábados e Noites", "Backend", "aluno01@example.invalid");

        // Act
        apresentador.exibirPerfilAluno(aluno);

        // Assert
        assertAll("Validação da exibição do perfil do estudante",
                () -> assertTrue(getOutput().contains("[PERFIL EM BUSCA DE EQUIPE]")),
                () -> assertTrue(getOutput().contains("Nome: Ryan David | Curso: BES (Noturno)")),
                () -> assertTrue(getOutput().contains("Tema de Interesse: Cibersegurança")),
                () -> assertTrue(getOutput().contains("Disponibilidade: Sábados e Noites")),
                () -> assertTrue(getOutput().contains("Papel/Habilidade: Backend")),
                () -> assertTrue(getOutput().contains("Contato: aluno01@example.invalid"))
        );
    }

    @Test
    @DisplayName("Deve lançar NullPointerException ao tentar exibir perfil com aluno nulo")
    void deveLancarNullPointerException_AoTentarExibirPerfilComAlunoNulo() {
        // Act & Assert
        NullPointerException exception = assertThrows(NullPointerException.class, () ->
                apresentador.exibirPerfilAluno(null)
        );

        assertEquals("O estudante não pode ser nulo.", exception.getMessage());
    }

    @Test
    @DisplayName("Deve exibir mensagem de processamento com a quantidade informada de candidatos")
    void deveExibirMensagemDeProcessamentoComQuantidadeDeCandidatos() {
        // Act
        apresentador.exibirMensagemProcessamento(15);

        // Assert
        assertTrue(getOutput().contains("Processando compatibilidade com 15 candidatos da base local de demonstração..."));
    }

    @Test
    @DisplayName("Deve exibir mensagem informativa quando lista de resultados for nula ou vazia")
    void deveExibirMensagemInformativa_QuandoListaDeResultadosForNulaOuVazia() {
        // Act
        apresentador.exibirResultados(null);
        apresentador.exibirResultados(Collections.emptyList());

        // Assert
        assertTrue(getOutput().contains("Nenhum parceiro compatível encontrado no momento."));
    }

    @Test
    @DisplayName("Deve exibir resultados ranqueados com detalhes e justificativas")
    void deveExibirResultadosRanqueadosComDetalhesEJustificativas() {
        // Arrange
        Aluno c1 = new Aluno("Lucas Santana", "BES", "Noturno", "Cibersegurança", "Sábados", "Frontend", "aluno01@example.invalid");
        Aluno c2 = new Aluno("Beatriz Lima", "BES", "Noturno", "IA", "Sábados", "Backend", "aluno02@example.invalid");

        ResultadoMatch r1 = new ResultadoMatch(c1, 100, List.of("Mesma linha de pesquisa", "Horários compatíveis"));
        ResultadoMatch r2 = new ResultadoMatch(c2, 30, Collections.emptyList());

        // Act
        apresentador.exibirResultados(List.of(r1, r2));
        String out = getOutput();

        // Assert
        assertAll("Validação do relatório ranqueado",
                () -> assertTrue(out.contains("1º Lugar: Lucas Santana — Afinidade: 100%")),
                () -> assertTrue(out.contains("Mesma linha de pesquisa")),
                () -> assertTrue(out.contains("2º Lugar: Beatriz Lima — Afinidade: 30%")),
                () -> assertTrue(out.contains("Sem critérios de afinidade em comum."))
        );
    }
}
