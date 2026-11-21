package br.ucsal.matchtcc.demo.service;

import br.ucsal.matchtcc.demo.exceptions.BaseDemoException;
import br.ucsal.matchtcc.demo.repository.RepositorioAlunos;
import br.ucsal.matchtcc.model.Aluno;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
@DisplayName("Testes Unitários para a classe BaseDemoService")
class BaseDemoServiceTest {

    @Mock
    private RepositorioAlunos repositorioCandidatos;

    @Mock
    private RepositorioAlunos repositorioPerfisBusca;

    @Mock
    private AnonimizacaoContatoService anonimizacaoService;

    @Test
    @DisplayName("Deve validar parâmetros não nulos no construtor de três argumentos")
    void deveValidarParametrosNaoNulos_NoConstrutorDeTresArgumentos() {
        // Assert
        assertAll("Validação de argumentos nulos no construtor",
                () -> assertThrows(NullPointerException.class, () ->
                        new BaseDemoService(null, repositorioPerfisBusca, anonimizacaoService)),
                () -> assertThrows(NullPointerException.class, () ->
                        new BaseDemoService(repositorioCandidatos, null, anonimizacaoService)),
                () -> assertThrows(NullPointerException.class, () ->
                        new BaseDemoService(repositorioCandidatos, repositorioPerfisBusca, null))
        );
    }

    @Test
    @DisplayName("Deve inicializar com sucesso via construtor de dois argumentos")
    void deveInicializarComSucesso_ViaConstrutorDeDoisArgumentos() {
        // Arrange & Act
        BaseDemoService service = new BaseDemoService(repositorioCandidatos, anonimizacaoService);

        // Assert
        assertNotNull(service);
    }

    @Test
    @DisplayName("Deve carregar base de candidatos e aplicar serviço de anonimização")
    void deveCarregarBaseDeCandidatosEAnonimizar() {
        // Arrange
        BaseDemoService service = new BaseDemoService(repositorioCandidatos, repositorioPerfisBusca, anonimizacaoService);

        Aluno bruto = new Aluno("Lucas", "BES", "Noturno", "IA", "Sábado", "Backend", "lucas@ucsal.br");
        Aluno anonimizado = new Aluno("Lucas", "BES", "Noturno", "IA", "Sábado", "Backend", "aluno01@example.invalid");

        when(repositorioCandidatos.carregarAlunos()).thenReturn(List.of(bruto));
        when(anonimizacaoService.anonimizarTodos(List.of(bruto))).thenReturn(List.of(anonimizado));

        // Act
        List<Aluno> resultado = service.carregarBaseDemo();

        // Assert
        verify(repositorioCandidatos).carregarAlunos();
        verify(anonimizacaoService).anonimizarTodos(List.of(bruto));

        assertAll("Validação dos candidatos carregados",
                () -> assertEquals(1, resultado.size()),
                () -> assertEquals("aluno01@example.invalid", resultado.get(0).getContato())
        );
    }

    @Test
    @DisplayName("Deve carregar perfis de busca e aplicar serviço de anonimização")
    void deveCarregarPerfisDeBuscaEAnonimizar() {
        // Arrange
        BaseDemoService service = new BaseDemoService(repositorioCandidatos, repositorioPerfisBusca, anonimizacaoService);

        Aluno perfilBruto = new Aluno("Ryan", "BES", "Noturno", "Cibersegurança", "Sábados", "Backend", "ryan@ucsal.br");
        Aluno perfilAnonimizado = new Aluno("Ryan", "BES", "Noturno", "Cibersegurança", "Sábados", "Backend", "aluno01@example.invalid");

        when(repositorioPerfisBusca.carregarAlunos()).thenReturn(List.of(perfilBruto));
        when(anonimizacaoService.anonimizarTodos(List.of(perfilBruto))).thenReturn(List.of(perfilAnonimizado));

        // Act
        List<Aluno> resultado = service.carregarPerfisBuscaDemo();

        // Assert
        verify(repositorioPerfisBusca).carregarAlunos();
        verify(anonimizacaoService).anonimizarTodos(List.of(perfilBruto));

        assertAll("Validação dos perfis carregados",
                () -> assertEquals(1, resultado.size()),
                () -> assertEquals("aluno01@example.invalid", resultado.get(0).getContato())
        );
    }

    @Test
    @DisplayName("Deve obter primeiro perfil de busca disponível quando houver perfis cadastrados")
    void deveObterPrimeiroPerfilDeBuscaDisponivel_QuandoHouverPerfisCadastrados() {
        // Arrange
        BaseDemoService service = new BaseDemoService(repositorioCandidatos, repositorioPerfisBusca, anonimizacaoService);

        Aluno p1 = new Aluno("Ryan", "BES", "Noturno", "Cibersegurança", "Sábados", "Backend", "aluno01@example.invalid");
        Aluno p2 = new Aluno("Camila", "ADS", "Matutino", "Dados", "Noite", "Frontend", "aluno02@example.invalid");

        when(repositorioPerfisBusca.carregarAlunos()).thenReturn(List.of(p1, p2));
        when(anonimizacaoService.anonimizarTodos(List.of(p1, p2))).thenReturn(List.of(p1, p2));

        // Act
        Aluno perfil = service.obterPerfilBaseDemo();

        // Assert
        assertEquals("Ryan", perfil.getNome());
    }

    @Test
    @DisplayName("Deve lançar BaseDemoException ao obter perfil base quando não houver perfis de busca")
    void deveLancarBaseDemoException_AoObterPerfilBaseQuandoNaoHouverPerfisDeBusca() {
        // Arrange
        BaseDemoService service = new BaseDemoService(repositorioCandidatos, repositorioPerfisBusca, anonimizacaoService);

        when(repositorioPerfisBusca.carregarAlunos()).thenReturn(Collections.emptyList());
        when(anonimizacaoService.anonimizarTodos(Collections.emptyList())).thenReturn(Collections.emptyList());

        // Act & Assert
        BaseDemoException exception = assertThrows(BaseDemoException.class, service::obterPerfilBaseDemo);

        assertEquals("Nenhum perfil de busca disponível na base de demonstração.", exception.getMessage());
    }
}
