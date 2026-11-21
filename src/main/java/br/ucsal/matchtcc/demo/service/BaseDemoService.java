package br.ucsal.matchtcc.demo.service;

import br.ucsal.matchtcc.demo.repository.RepositorioAlunos;
import br.ucsal.matchtcc.model.Aluno;

import java.util.List;
import java.util.Objects;

/**
 * Serviço responsável por coordenar o carregamento da base de alunos de demonstração
 * ({@code demo/}) e entregar os perfis com contatos devidamente anonimizados ou substituídos,
 * desacoplando {@code DemoView} de listas fixas em código.
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
     * Carrega os candidatos da base de demonstração já com os contatos anonimizados ou substituídos.
     *
     * @return lista de estudantes simulados pronta para uso na demonstração e em testes
     */
    public List<Aluno> carregarBaseDemo() {
        List<Aluno> alunosCarregados = repositorioAlunos.carregarAlunos();
        return anonimizacaoContatoService.anonimizarTodos(alunosCarregados);
    }

    /**
     * Fornece um perfil base simulado para execução rápida do modo demonstração,
     * sem utilizar nomes ou e-mails reais.
     *
     * @return estudante simulado para busca de afinidade na demonstração
     */
    public Aluno obterPerfilBaseDemo() {
        Aluno perfilBase = new Aluno(
                "Estudante Base Demo",
                "BES",
                "Noturno",
                "Cibersegurança",
                "Sábados e Noites",
                "Backend",
                "aluno00@example.invalid"
        );
        return anonimizacaoContatoService.anonimizar(perfilBase);
    }
}
