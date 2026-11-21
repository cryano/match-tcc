package br.ucsal.matchtcc.service;

import br.ucsal.matchtcc.model.Aluno;
import br.ucsal.matchtcc.repository.RepositorioAlunos;

import java.util.List;
import java.util.Objects;

/**
 * Serviço responsável por coordenar o carregamento da base de alunos de demonstração
 * ({@code demo/}) e entregar os perfis com contatos devidamente anonimizados ou substituídos.
 */
public class BaseDemoService {

    private final RepositorioAlunos repositorioAlunos;
    private final AnonimizacaoContatoService anonimizacaoContatoService;

    public BaseDemoService(RepositorioAlunos repositorioAlunos,
                           AnonimizacaoContatoService anonimizacaoContatoService) {
        this.repositorioAlunos = Objects.requireNonNull(
                repositorioAlunos,
                "O repositório de alunos não pode ser nulo."
        );
        this.anonimizacaoContatoService = Objects.requireNonNull(
                anonimizacaoContatoService,
                "O serviço de anonimização de contatos não pode ser nulo."
        );
    }

    /**
     * Carrega os alunos da base de demonstração já com os contatos anonimizados ou substituídos.
     *
     * @return lista de estudantes segura para exibição na demonstração e testes
     */
    public List<Aluno> carregarBaseDemo() {
        // TODO: carregar alunos via repositorioAlunos e aplicar anonimizacaoContatoService.anonimizarTodos(...)
        throw new UnsupportedOperationException("TODO: implementar carregamento da base de demonstração.");
    }
}
