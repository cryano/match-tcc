package br.ucsal.matchtcc.demo.service;

import br.ucsal.matchtcc.demo.strategy.AnonimizadorContato;
import br.ucsal.matchtcc.model.Aluno;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes Unitários para a classe AnonimizacaoContatoService")
class AnonimizacaoContatoServiceTest {

    @Mock
    private AnonimizadorContato anonimizadorContato;

    @InjectMocks
    private AnonimizacaoContatoService service;

    @Test
    @DisplayName("Deve lançar NullPointerException quando anonimizador for nulo no construtor")
    void deveLancarNullPointerException_QuandoAnonimizadorForNuloNoConstrutor() {
        // Act & Assert
        NullPointerException exception = assertThrows(NullPointerException.class, () ->
                new AnonimizacaoContatoService(null)
        );

        assertEquals("A estratégia de anonimização de contato não pode ser nula.", exception.getMessage());
    }

    @Test
    @DisplayName("Deve anonimizar contato do aluno mantendo demais atributos intactos")
    void deveAnonimizarContatoDoAluno_MantendoDemaisAtributosIntactos() {
        // Arrange
        Aluno alunoOriginal = new Aluno("Ryan David", "BES", "Noturno", "IA", "Sábados", "Backend", "ryan@ucsal.br");
        when(anonimizadorContato.anonimizar("ryan@ucsal.br")).thenReturn("aluno01@example.invalid");

        ArgumentCaptor<String> contatoCaptor = ArgumentCaptor.forClass(String.class);

        // Act
        Aluno alunoTratado = service.anonimizar(alunoOriginal);

        // Assert
        verify(anonimizadorContato).anonimizar(contatoCaptor.capture());

        assertAll("Validação da integridade dos atributos do aluno anonimizado",
                () -> assertEquals("ryan@ucsal.br", contatoCaptor.getValue()),
                () -> assertEquals("Ryan David", alunoTratado.getNome()),
                () -> assertEquals("BES", alunoTratado.getCurso()),
                () -> assertEquals("Noturno", alunoTratado.getTurno()),
                () -> assertEquals("IA", alunoTratado.getTemaInteresse()),
                () -> assertEquals("Sábados", alunoTratado.getDisponibilidade()),
                () -> assertEquals("Backend", alunoTratado.getPapel()),
                () -> assertEquals("aluno01@example.invalid", alunoTratado.getContato())
        );
    }

    @Test
    @DisplayName("Deve lançar NullPointerException quando aluno for nulo")
    void deveLancarNullPointerException_QuandoAlunoForNulo() {
        // Act & Assert
        NullPointerException exception = assertThrows(NullPointerException.class, () ->
                service.anonimizar(null)
        );

        assertEquals("O aluno informado não pode ser nulo.", exception.getMessage());
        verifyNoInteractions(anonimizadorContato);
    }

    @Test
    @DisplayName("Deve anonimizar todos os alunos da lista ignorando elementos nulos")
    void deveAnonimizarTodosOsAlunosDaLista_IgnorandoElementosNulos() {
        // Arrange
        Aluno a1 = new Aluno("Ana", "ADS", "Matutino", "Web", "Noite", "Frontend", "ana@ucsal.br");
        Aluno a2 = new Aluno("Bruno", "BES", "Noturno", "IA", "Sábado", "Backend", "bruno@ucsal.br");

        List<Aluno> listaComNulo = new ArrayList<>();
        listaComNulo.add(a1);
        listaComNulo.add(null);
        listaComNulo.add(a2);

        when(anonimizadorContato.anonimizar("ana@ucsal.br")).thenReturn("aluno01@example.invalid");
        when(anonimizadorContato.anonimizar("bruno@ucsal.br")).thenReturn("aluno02@example.invalid");

        // Act
        List<Aluno> resultado = service.anonimizarTodos(listaComNulo);

        // Assert
        assertAll("Validação da lista tratada e imutabilidade",
                () -> assertEquals(2, resultado.size()),
                () -> assertEquals("aluno01@example.invalid", resultado.get(0).getContato()),
                () -> assertEquals("aluno02@example.invalid", resultado.get(1).getContato()),
                () -> assertThrows(UnsupportedOperationException.class, () -> resultado.add(a1))
        );
    }

    @Test
    @DisplayName("Deve retornar lista vazia quando lista de alunos for nula ou vazia")
    void deveRetornarListaVazia_QuandoListaDeAlunosForNulaOuVazia() {
        // Act
        List<Aluno> resNulo = service.anonimizarTodos(null);
        List<Aluno> resVazio = service.anonimizarTodos(Collections.emptyList());

        // Assert
        assertAll("Listas vazias de retorno",
                () -> assertNotNull(resNulo),
                () -> assertTrue(resNulo.isEmpty()),
                () -> assertNotNull(resVazio),
                () -> assertTrue(resVazio.isEmpty())
        );
        verifyNoInteractions(anonimizadorContato);
    }
}
