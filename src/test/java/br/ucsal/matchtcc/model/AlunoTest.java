package br.ucsal.matchtcc.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

@DisplayName("Testes Unitários para a classe Aluno")
class AlunoTest {

    @Test
    @DisplayName("Deve criar aluno com sucesso quando todos os dados válidos forem fornecidos")
    void deveCriarAlunoComSucesso_QuandoTodosOsDadosValidosForemFornecidos() {
        // Arrange
        String nome = "Lucas Santana";
        String curso = "BES";
        String turno = "Noturno";
        String tema = "Cibersegurança";
        String disponibilidade = "Sábados e Noites";
        String papel = "Backend";
        String contato = "lucas.santana@ucsal.edu.br";

        // Act
        Aluno aluno = new Aluno(nome, curso, turno, tema, disponibilidade, papel, contato);

        // Assert
        assertAll("Validação dos atributos do aluno",
                () -> assertEquals(nome, aluno.getNome()),
                () -> assertEquals(curso, aluno.getCurso()),
                () -> assertEquals(turno, aluno.getTurno()),
                () -> assertEquals(tema, aluno.getTemaInteresse()),
                () -> assertEquals(disponibilidade, aluno.getDisponibilidade()),
                () -> assertEquals(papel, aluno.getPapel()),
                () -> assertEquals(contato, aluno.getContato())
        );
    }

    @Test
    @DisplayName("Deve retornar representação textual formatada quando chamar toString")
    void deveRetornarRepresentacaoTextualFormatada_QuandoChamarToString() {
        // Arrange
        Aluno aluno = new Aluno("Ana Beatriz", "ADS", "Matutino", "IA", "Noite", "Frontend", "ana@ucsal.br");

        // Act
        String resultado = aluno.toString();

        // Assert
        assertEquals("Ana Beatriz (ADS - Matutino)", resultado);
    }

    @Test
    @DisplayName("Deve suportar criação com campos nulos quando fornecidos")
    void deveSuportarCriacaoComCamposNulos_QuandoFornecidos() {
        // Arrange & Act
        Aluno aluno = new Aluno(null, null, null, null, null, null, null);

        // Assert
        assertAll("Validação de campos nulos e toString",
                () -> assertNull(aluno.getNome()),
                () -> assertNull(aluno.getCurso()),
                () -> assertNull(aluno.getTurno()),
                () -> assertNull(aluno.getTemaInteresse()),
                () -> assertNull(aluno.getDisponibilidade()),
                () -> assertNull(aluno.getPapel()),
                () -> assertNull(aluno.getContato()),
                () -> assertEquals("null (null - null)", aluno.toString())
        );
    }
}
