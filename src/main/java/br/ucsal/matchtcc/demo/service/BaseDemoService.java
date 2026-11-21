package br.ucsal.matchtcc.demo.service;

import br.ucsal.matchtcc.demo.exceptions.BaseDemoException;
import br.ucsal.matchtcc.demo.repository.RepositorioAlunos;
import br.ucsal.matchtcc.demo.repository.RepositorioAlunosDemoCsv;
import br.ucsal.matchtcc.model.Aluno;

import java.util.List;
import java.util.Objects;

/**
 * Serviço responsável por coordenar o carregamento das bases separadas de demonstração
 * (candidatos e perfis de busca em {@code demo/}) e garantir que todos os contatos sejam
 * anonimizados ou substituídos. Não concentra regras de pontuação nem apresentação de terminal.
 */
public class BaseDemoService {

    private final RepositorioAlunos repositorioCandidatos;
    private final RepositorioAlunos repositorioPerfisBusca;
    private final AnonimizacaoContatoService anonimizacaoContatoService;

    public BaseDemoService(RepositorioAlunos repositorioCandidatos,
                           AnonimizacaoContatoService anonimizacaoContatoService) {
        this(
                repositorioCandidatos,
                new RepositorioAlunosDemoCsv(RepositorioAlunosDemoCsv.CAMINHO_PERFIS_BUSCA_PADRAO),
                anonimizacaoContatoService
        );
    }

    public BaseDemoService(RepositorioAlunos repositorioCandidatos,
                           RepositorioAlunos repositorioPerfisBusca,
                           AnonimizacaoContatoService anonimizacaoContatoService) {
        this.repositorioCandidatos = Objects.requireNonNull(
                repositorioCandidatos,
                "O repositório de candidatos não pode ser nulo."
        );
        this.repositorioPerfisBusca = Objects.requireNonNull(
                repositorioPerfisBusca,
                "O repositório de perfis de busca não pode ser nulo."
        );
        this.anonimizacaoContatoService = Objects.requireNonNull(
                anonimizacaoContatoService,
                "O serviço de anonimização de contatos não pode ser nulo."
        );
    }

    /**
     * Carrega os candidatos da base de demonstração já com os contatos anonimizados ou substituídos.
     *
     * @return lista imutável de candidatos simulados pronta para cálculo pela {@code CalculadoraMatch}
     */
    public List<Aluno> carregarBaseDemo() {
        List<Aluno> alunosCarregados = repositorioCandidatos.carregarAlunos();
        return anonimizacaoContatoService.anonimizarTodos(alunosCarregados);
    }

    /**
     * Carrega todos os perfis de busca simulados definidos na fonte separada de perfis de demonstração.
     *
     * @return lista imutável de perfis de busca simulados com contatos tratados
     */
    public List<Aluno> carregarPerfisBuscaDemo() {
        List<Aluno> perfisCarregados = repositorioPerfisBusca.carregarAlunos();
        return anonimizacaoContatoService.anonimizarTodos(perfisCarregados);
    }

    /**
     * Obtém o perfil base padrão da fonte de perfis de busca de demonstração.
     *
     * @return primeiro estudante simulado disponível na base de perfis de busca
     * @throws BaseDemoException se nenhum perfil de busca estiver disponível
     */
    public Aluno obterPerfilBaseDemo() {
        List<Aluno> perfis = carregarPerfisBuscaDemo();
        if (perfis.isEmpty()) {
            throw new BaseDemoException("Nenhum perfil de busca disponível na base de demonstração.");
        }
        return perfis.get(0);
    }
}
