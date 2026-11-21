package br.ucsal.matchtcc.demo.repository;

import br.ucsal.matchtcc.model.Aluno;

import java.util.List;

/**
 * Contrato para carregamento da base de estudantes utilizada nos testes e na demonstração.
 */
public interface RepositorioAlunos {

    /**
     * Carrega a lista de estudantes da fonte de dados configurada.
     *
     * @return lista de estudantes carregados
     */
    List<Aluno> carregarAlunos();
}
