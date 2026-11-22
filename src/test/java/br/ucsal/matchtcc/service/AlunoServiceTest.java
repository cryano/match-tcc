package br.ucsal.matchtcc.service;

import br.ucsal.matchtcc.model.Aluno;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Testes Unitários para a classe AlunoService")
class AlunoServiceTest {

    private final PrintStream standardOut = System.out;
    private final ByteArrayOutputStream outputStreamCaptor = new ByteArrayOutputStream();

    @BeforeEach
    void setUp() {
        System.setOut(new PrintStream(outputStreamCaptor));
    }

    @AfterEach
    void tearDown() {
        System.setOut(standardOut);
    }

    @Test
    @DisplayName("Deve cadastrar aluno com sucesso quando parâmetros válidos forem fornecidos")
    void deveCadastrarAlunoComSucesso_QuandoParametrosValidosForemFornecidos() {
        // Arrange
        AlunoService service = new AlunoService();

        // Act
        Aluno aluno = service.cadastrarAluno(
                "Ryan David",
                "BES",
                "Noturno",
                "Cibersegurança",
                "Sábados",
                "Backend",
                "ryan@ucsal.br"
        );

        // Assert
        assertAll("Validação do cadastro do aluno",
                () -> assertNotNull(aluno),
                () -> assertEquals("Ryan David", aluno.getNome()),
                () -> assertEquals("BES", aluno.getCurso()),
                () -> assertEquals("Noturno", aluno.getTurno()),
                () -> assertEquals("Cibersegurança", aluno.getTemaInteresse()),
                () -> assertEquals("Sábados", aluno.getDisponibilidade()),
                () -> assertEquals("Backend", aluno.getPapel()),
                () -> assertEquals("ryan@ucsal.br", aluno.getContato())
        );
    }

    @Test
    @DisplayName("Deve atualizar e obter aluno quando utilizar getters e setters")
    void deveAtualizarEObterAluno_QuandoUtilizarGettersESetters() {
        // Arrange
        AlunoService service = new AlunoService();
        Aluno aluno = new Aluno("Ana", "ADS", "Matutino", "IA", "Noite", "Frontend", "ana@ucsal.br");

        // Act
        service.setAluno(aluno);

        // Assert
        assertEquals(aluno, service.getAluno());
    }

    @Test
    @DisplayName("Deve inicializar com aluno nulo quando utilizar construtor padrão")
    void deveInicializarComAlunoNulo_QuandoUtilizarConstrutorPadrao() {
        // Arrange & Act
        AlunoService service = new AlunoService();

        // Assert
        assertNull(service.getAluno());
    }

    @Test
    @DisplayName("Deve inicializar com aluno fornecido quando utilizar construtor parametrizado")
    void deveInicializarComAlunoFornecido_QuandoUtilizarConstrutorParametrizado() {
        // Arrange
        Aluno aluno = new Aluno("Ana", "ADS", "Matutino", "IA", "Noite", "Frontend", "ana@ucsal.br");

        // Act
        AlunoService service = new AlunoService(aluno);

        // Assert
        assertEquals(aluno, service.getAluno());
    }

    @Test
    @DisplayName("Deve cumprir contrato de equals e hashCode para diferentes instâncias")
    void deveCumprirContratoEqualsEHashCode_ParaDiferentesInstancias() {
        // Arrange
        Aluno aluno1 = new Aluno("Ana", "ADS", "Matutino", "IA", "Noite", "Frontend", "ana@ucsal.br");
        Aluno aluno2 = new Aluno("Bruno", "BES", "Noturno", "Web", "Sábado", "Backend", "bruno@ucsal.br");

        AlunoService s1 = new AlunoService(aluno1);
        AlunoService s2 = new AlunoService(aluno1);
        AlunoService s3 = new AlunoService(aluno2);
        AlunoService sVazio1 = new AlunoService();
        AlunoService sVazio2 = new AlunoService();

        // Act & Assert
        assertAll("Validação de equals e hashCode",
                () -> assertEquals(s1, s1),
                () -> assertEquals(s1, s2),
                () -> assertEquals(s1.hashCode(), s2.hashCode()),
                () -> assertEquals(sVazio1, sVazio2),
                () -> assertEquals(sVazio1.hashCode(), sVazio2.hashCode()),
                () -> assertNotEquals(s1, s3),
                () -> assertNotEquals(s1, null),
                () -> assertNotEquals(s1, "outraString")
        );
    }

    @Test
    @DisplayName("Deve exibir dados formatados do aluno no console quando invocar exibirAluno")
    void deveExibirDadosFormatadosDoAlunoNoConsole_QuandoInvocarExibirAluno() {
        // Arrange
        AlunoService service = new AlunoService();
        Aluno aluno = new Aluno("Lucas Santana", "BES", "Noturno", "Cibersegurança", "Sábados", "Frontend", "lucas@ucsal.br");

        // Act
        service.exibirAluno(aluno);
        String output = outputStreamCaptor.toString();

        // Assert
        assertAll("Validação do formato de saída no console",
                () -> assertTrue(output.contains("Nome: Lucas Santana | Curso: BES (Noturno)")),
                () -> assertTrue(output.contains("Tema de Interesse: Cibersegurança")),
                () -> assertTrue(output.contains("Disponibilidade: Sábados")),
                () -> assertTrue(output.contains("Papel/Habilidade: Frontend"))
        );
    }
}
