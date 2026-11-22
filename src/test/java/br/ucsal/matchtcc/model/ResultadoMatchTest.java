package br.ucsal.matchtcc.model;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

@DisplayName("Testes Unitários para a classe ResultadoMatch")
class ResultadoMatchTest {

    @Test
    @DisplayName("Deve inicializar resultado do match com sucesso quando lista de justificativas for fornecida")
    void deveInicializarResultadoMatchComSucesso_QuandoListaDeJustificativasForFornecida() {
        // Arrange
        Aluno candidato = new Aluno("Ryan David", "BES", "Noturno", "IA", "Sábados", "Backend", "ryan@ucsal.br");
        int pontuacao = 85;
        List<String> justificativas = List.of("Mesma linha de pesquisa", "Horários compatíveis");

        // Act
        ResultadoMatch resultado = new ResultadoMatch(candidato, pontuacao, justificativas);

        // Assert
        assertAll("Validação do resultado do match",
                () -> assertEquals(candidato, resultado.getAlunoCandidato()),
                () -> assertEquals(pontuacao, resultado.getPontuacaoTotal()),
                () -> assertEquals(2, resultado.getJustificativas().size()),
                () -> assertEquals("Mesma linha de pesquisa", resultado.getJustificativas().get(0))
        );
    }

    @Test
    @DisplayName("Deve inicializar lista vazia de justificativas quando lista passada no construtor for nula")
    void deveInicializarListaVaziaDeJustificativas_QuandoListaPassadaNoConstrutorForNula() {
        // Arrange
        Aluno candidato = new Aluno("Lucas Santana", "ADS", "Matutino", "Web", "Noite", "Frontend", "lucas@ucsal.br");

        // Act
        ResultadoMatch resultado = new ResultadoMatch(candidato, 50, null);

        // Assert
        assertAll("Garantia de lista de justificativas não nula",
                () -> assertNotNull(resultado.getJustificativas()),
                () -> assertTrue(resultado.getJustificativas().isEmpty())
        );
    }

    @Test
    @DisplayName("Deve ordenar decrescentemente pela pontuação quando comparar dois resultados")
    void deveOrdenarDecrescentementePelaPontuacao_QuandoCompararDoisResultados() {
        // Arrange
        Aluno candidatoA = new Aluno("Aluno A", "BES", "Noturno", "IA", "Sábado", "Backend", "a@ucsal.br");
        Aluno candidatoB = new Aluno("Aluno B", "BES", "Noturno", "IA", "Sábado", "Frontend", "b@ucsal.br");

        ResultadoMatch resultadoMaior = new ResultadoMatch(candidatoA, 90, List.of("Afinidade alta"));
        ResultadoMatch resultadoMenor = new ResultadoMatch(candidatoB, 50, List.of("Afinidade média"));

        // Act & Assert
        // compareTo(outro): Integer.compare(outro.pontuacaoTotal, this.pontuacaoTotal)
        // Maior comparado com Menor -> Integer.compare(50, 90) = número negativo (maior vem antes)
        assertTrue(resultadoMaior.compareTo(resultadoMenor) < 0,
                "Resultado com maior pontuação deve vir antes (retornar valor negativo)");
        assertTrue(resultadoMenor.compareTo(resultadoMaior) > 0,
                "Resultado com menor pontuação deve vir depois (retornar valor positivo)");
    }

    @Test
    @DisplayName("Deve retornar zero quando comparar resultados com a mesma pontuação")
    void deveRetornarZero_QuandoCompararResultadosComMesmaPontuacao() {
        // Arrange
        Aluno candidatoA = new Aluno("Aluno A", "BES", "Noturno", "IA", "Sábado", "Backend", "a@ucsal.br");
        Aluno candidatoB = new Aluno("Aluno B", "ADS", "Matutino", "Web", "Noite", "Frontend", "b@ucsal.br");

        ResultadoMatch resultado1 = new ResultadoMatch(candidatoA, 70, List.of());
        ResultadoMatch resultado2 = new ResultadoMatch(candidatoB, 70, List.of());

        // Act & Assert
        assertEquals(0, resultado1.compareTo(resultado2));
    }

    @Test
    @DisplayName("Deve ordenar lista de resultados em ordem decrescente quando utilizar Collections.sort")
    void deveOrdenarListaDeResultadosEmOrdemDecrescente_QuandoUtilizarCollectionsSort() {
        // Arrange
        Aluno c1 = new Aluno("Aluno 1", "BES", "Noturno", "IA", "Sábado", "Backend", "1@ucsal.br");
        Aluno c2 = new Aluno("Aluno 2", "BES", "Noturno", "IA", "Sábado", "Backend", "2@ucsal.br");
        Aluno c3 = new Aluno("Aluno 3", "BES", "Noturno", "IA", "Sábado", "Backend", "3@ucsal.br");

        ResultadoMatch rBaixo = new ResultadoMatch(c1, 30, List.of());
        ResultadoMatch rAlto = new ResultadoMatch(c2, 100, List.of());
        ResultadoMatch rMedio = new ResultadoMatch(c3, 60, List.of());

        List<ResultadoMatch> lista = new ArrayList<>(List.of(rBaixo, rAlto, rMedio));

        // Act
        Collections.sort(lista);

        // Assert
        assertAll("Verificação da ordenação decrescente da lista",
                () -> assertEquals(100, lista.get(0).getPontuacaoTotal()),
                () -> assertEquals(60, lista.get(1).getPontuacaoTotal()),
                () -> assertEquals(30, lista.get(2).getPontuacaoTotal())
        );
    }

    @Test
    @DisplayName("Deve formatar representação textual corretamente quando chamar toString")
    void deveFormatarRepresentacaoTextualCorretamente_QuandoChamarToString() {
        // Arrange
        Aluno candidato = new Aluno("Matheus Pereira", "BES", "Noturno", "Web", "Noites", "Frontend", "matheus@ucsal.br");
        ResultadoMatch resultado = new ResultadoMatch(candidato, 80, List.of("Compatível"));

        // Act
        String texto = resultado.toString();

        // Assert
        assertEquals("Matheus Pereira — Afinidade: 80%", texto);
    }
}
