package br.ucsal.matchtcc.repository;

import br.ucsal.matchtcc.model.Aluno;

import java.nio.file.Path;
import java.util.List;

/**
 * Implementação de {@link RepositorioAlunos} responsável por ler os perfis de demonstração
 * a partir de um arquivo CSV localizado na pasta {@code demo/}.
 */
public class RepositorioAlunosDemoCsv implements RepositorioAlunos {

    public static final Path CAMINHO_PADRAO = Path.of("demo", "alunos-demo.csv");

    private final Path caminhoArquivo;

    public RepositorioAlunosDemoCsv() {
        this(CAMINHO_PADRAO);
    }

    public RepositorioAlunosDemoCsv(Path caminhoArquivo) {
        this.caminhoArquivo = caminhoArquivo;
    }

    public Path getCaminhoArquivo() {
        return caminhoArquivo;
    }

    @Override
    public List<Aluno> carregarAlunos() {
        // TODO: implementar leitura do arquivo CSV em demo/ e conversão em lista de Aluno
        throw new UnsupportedOperationException("TODO: implementar leitura da base CSV em " + caminhoArquivo);
    }

    private Aluno parseLinha(String linha) {
        // TODO: implementar parsing dos campos do CSV para instância de Aluno
        throw new UnsupportedOperationException("TODO: implementar parsing de linha CSV.");
    }
}
