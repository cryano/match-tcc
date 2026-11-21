package br.ucsal.matchtcc.demo.repository;

import br.ucsal.matchtcc.demo.exceptions.BaseDemoException;
import br.ucsal.matchtcc.model.Aluno;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Implementação de {@link RepositorioAlunos} responsável por ler os perfis simulados
 * a partir de um arquivo CSV local na pasta {@code demo/}.
 */
public class RepositorioAlunosDemoCsv implements RepositorioAlunos {

    public static final Path CAMINHO_PADRAO = Path.of("demo", "alunos-demo.csv");
    public static final Path CAMINHO_PERFIS_BUSCA_PADRAO = Path.of("demo", "perfis-busca-demo.csv");
    private static final String SEPARADOR = ",";
    private static final int TOTAL_COLUNAS = 7;

    private final Path caminhoArquivo;

    public RepositorioAlunosDemoCsv() {
        this(CAMINHO_PADRAO);
    }

    public RepositorioAlunosDemoCsv(Path caminhoArquivo) {
        this.caminhoArquivo = Objects.requireNonNull(
                caminhoArquivo,
                "O caminho do arquivo CSV de demonstração não pode ser nulo."
        );
    }

    public Path getCaminhoArquivo() {
        return caminhoArquivo;
    }

    @Override
    public List<Aluno> carregarAlunos() {
        if (!Files.exists(caminhoArquivo)) {
            throw new BaseDemoException("Arquivo da base de demonstração não encontrado: " + caminhoArquivo);
        }

        try {
            List<String> linhas = Files.readAllLines(caminhoArquivo, StandardCharsets.UTF_8);
            List<Aluno> alunos = new ArrayList<>();
            boolean cabecalhoVerificado = false;

            for (int i = 0; i < linhas.size(); i++) {
                String linha = linhas.get(i).trim();
                if (linha.isEmpty() || linha.startsWith("#")) {
                    continue;
                }

                if (!cabecalhoVerificado && linha.toLowerCase().startsWith("nome,")) {
                    cabecalhoVerificado = true;
                    continue;
                }
                cabecalhoVerificado = true;

                alunos.add(parseLinha(linha, i + 1));
            }

            return Collections.unmodifiableList(alunos);
        } catch (IOException e) {
            throw new BaseDemoException("Erro ao ler o arquivo da base de demonstração: " + caminhoArquivo, e);
        }
    }

    private Aluno parseLinha(String linha, int numeroLinha) {
        String[] colunas = linha.split(SEPARADOR, -1);
        if (colunas.length != TOTAL_COLUNAS) {
            throw new BaseDemoException(
                    "Formato inválido na linha " + numeroLinha + ": esperadas " + TOTAL_COLUNAS
                            + " colunas, encontradas " + colunas.length + "."
            );
        }

        String nome = validarCampo(colunas[0], "nome", numeroLinha);
        String curso = validarCampo(colunas[1], "curso", numeroLinha);
        String turno = validarCampo(colunas[2], "turno", numeroLinha);
        String temaInteresse = validarCampo(colunas[3], "temaInteresse", numeroLinha);
        String disponibilidade = validarCampo(colunas[4], "disponibilidade", numeroLinha);
        String papel = validarCampo(colunas[5], "papel", numeroLinha);
        String contato = validarCampo(colunas[6], "contato", numeroLinha);

        return new Aluno(nome, curso, turno, temaInteresse, disponibilidade, papel, contato);
    }

    private String validarCampo(String valor, String nomeCampo, int numeroLinha) {
        String limpo = valor.trim();
        if (limpo.isEmpty()) {
            throw new BaseDemoException(
                    "Campo obrigatório '" + nomeCampo + "' vazio na linha " + numeroLinha + "."
            );
        }
        return limpo;
    }
}
