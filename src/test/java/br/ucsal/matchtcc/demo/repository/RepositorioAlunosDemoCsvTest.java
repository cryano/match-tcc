package br.ucsal.matchtcc.demo.repository;

import br.ucsal.matchtcc.demo.exceptions.BaseDemoException;
import br.ucsal.matchtcc.model.Aluno;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Testes Unitários para a classe RepositorioAlunosDemoCsv")
class RepositorioAlunosDemoCsvTest {

    @Test
    @DisplayName("Deve inicializar com caminho padrão no construtor sem argumentos")
    void deveInicializarComCaminhoPadrao_QuandoUtilizarConstrutorSemArgumentos() {
        // Arrange & Act
        RepositorioAlunosDemoCsv repositorio = new RepositorioAlunosDemoCsv();

        // Assert
        assertEquals(RepositorioAlunosDemoCsv.CAMINHO_PADRAO, repositorio.getCaminhoArquivo());
    }

    @Test
    @DisplayName("Deve lançar NullPointerException quando caminho do arquivo for nulo")
    void deveLancarNullPointerException_QuandoCaminhoDoArquivoForNulo() {
        // Act & Assert
        NullPointerException exception = assertThrows(NullPointerException.class, () ->
                new RepositorioAlunosDemoCsv(null)
        );

        assertEquals("O caminho do arquivo CSV de demonstração não pode ser nulo.", exception.getMessage());
    }

    @Test
    @DisplayName("Deve carregar com sucesso os alunos da base CSV oficial do projeto")
    void deveCarregarComSucessoOsAlunos_DaBaseCsvOficialDoProjeto() {
        // Arrange
        RepositorioAlunosDemoCsv repositorio = new RepositorioAlunosDemoCsv();

        // Act
        List<Aluno> alunos = repositorio.carregarAlunos();

        // Assert
        assertAll("Validação da base padrão oficial",
                () -> assertNotNull(alunos),
                () -> assertFalse(alunos.isEmpty()),
                () -> assertEquals("Estudante Demo 01", alunos.get(0).getNome())
        );
    }

    @Test
    @DisplayName("Deve carregar com sucesso os perfis de busca do CSV oficial do projeto")
    void deveCarregarComSucessoOsPerfisDeBusca_DaBaseCsvOficialDoProjeto() {
        // Arrange
        RepositorioAlunosDemoCsv repositorio = new RepositorioAlunosDemoCsv(
                RepositorioAlunosDemoCsv.CAMINHO_PERFIS_BUSCA_PADRAO
        );

        // Act
        List<Aluno> perfis = repositorio.carregarAlunos();

        // Assert
        assertAll("Validação dos perfis de busca",
                () -> assertNotNull(perfis),
                () -> assertFalse(perfis.isEmpty()),
                () -> assertEquals("Perfil Busca Demo 01", perfis.get(0).getNome())
        );
    }

    @Test
    @DisplayName("Deve lançar BaseDemoException quando arquivo CSV não existir")
    void deveLancarBaseDemoException_QuandoArquivoCsvNaoExistir(@TempDir Path tempDir) {
        // Arrange
        Path arquivoInexistente = tempDir.resolve("inexistente.csv");
        RepositorioAlunosDemoCsv repositorio = new RepositorioAlunosDemoCsv(arquivoInexistente);

        // Act & Assert
        BaseDemoException exception = assertThrows(BaseDemoException.class, repositorio::carregarAlunos);

        assertTrue(exception.getMessage().contains("Arquivo da base de demonstração não encontrado"));
    }

    @Test
    @DisplayName("Deve ignorar comentários e linhas em branco ao ler arquivo CSV")
    void deveIgnorarComentariosELinhasEmBranco_AoLerArquivoCsv(@TempDir Path tempDir) throws IOException {
        // Arrange
        Path csvTeste = tempDir.resolve("alunos_comentarios.csv");
        String conteudo = String.join("\n",
                "# Linha de comentário que deve ser ignorada",
                "",
                "Nome,Curso,Turno,TemaInteresse,Disponibilidade,Papel,Contato",
                "# Outro comentário intermediário",
                "Carlos Silva,BES,Noturno,IA,Sábados,Backend,carlos@ucsal.br",
                "   ",
                "Mariana Costa,ADS,Matutino,Web,Noites,Frontend,mariana@ucsal.br"
        );
        Files.writeString(csvTeste, conteudo);

        RepositorioAlunosDemoCsv repositorio = new RepositorioAlunosDemoCsv(csvTeste);

        // Act
        List<Aluno> alunos = repositorio.carregarAlunos();

        // Assert
        assertAll("Alunos carregados ignorando linhas inválidas e comentários",
                () -> assertEquals(2, alunos.size()),
                () -> assertEquals("Carlos Silva", alunos.get(0).getNome()),
                () -> assertEquals("Mariana Costa", alunos.get(1).getNome())
        );
    }

    @Test
    @DisplayName("Deve lançar BaseDemoException quando linha do CSV tiver número incorreto de colunas")
    void deveLancarBaseDemoException_QuandoLinhaTiverNumeroIncorretoDeColunas(@TempDir Path tempDir) throws IOException {
        // Arrange
        Path csvTeste = tempDir.resolve("alunos_colunas_invalidas.csv");
        String conteudo = String.join("\n",
                "Nome,Curso,Turno,TemaInteresse,Disponibilidade,Papel,Contato",
                "Carlos Silva,BES,Noturno,IA" // Apenas 4 colunas em vez de 7
        );
        Files.writeString(csvTeste, conteudo);

        RepositorioAlunosDemoCsv repositorio = new RepositorioAlunosDemoCsv(csvTeste);

        // Act & Assert
        BaseDemoException exception = assertThrows(BaseDemoException.class, repositorio::carregarAlunos);

        assertTrue(exception.getMessage().contains("Formato inválido na linha 2: esperadas 7 colunas, encontradas 4."));
    }

    @Test
    @DisplayName("Deve lançar BaseDemoException quando campo obrigatório estiver vazio")
    void deveLancarBaseDemoException_QuandoCampoObrigatorioEstiverVazio(@TempDir Path tempDir) throws IOException {
        // Arrange
        Path csvTeste = tempDir.resolve("alunos_campo_vazio.csv");
        String conteudo = String.join("\n",
                "Nome,Curso,Turno,TemaInteresse,Disponibilidade,Papel,Contato",
                "Carlos Silva,,Noturno,IA,Sábados,Backend,carlos@ucsal.br" // Curso vazio
        );
        Files.writeString(csvTeste, conteudo);

        RepositorioAlunosDemoCsv repositorio = new RepositorioAlunosDemoCsv(csvTeste);

        // Act & Assert
        BaseDemoException exception = assertThrows(BaseDemoException.class, repositorio::carregarAlunos);

        assertTrue(exception.getMessage().contains("Campo obrigatório 'curso' vazio na linha 2."));
    }
}
