package br.ucsal.matchtcc.demo;

import br.ucsal.matchtcc.demo.presentation.ApresentadorDemoTerminal;
import br.ucsal.matchtcc.demo.repository.RepositorioAlunos;
import br.ucsal.matchtcc.demo.repository.RepositorioAlunosDemoCsv;
import br.ucsal.matchtcc.demo.service.AnonimizacaoContatoService;
import br.ucsal.matchtcc.demo.service.BaseDemoService;
import br.ucsal.matchtcc.demo.strategy.SubstituidorContatoFicticio;
import br.ucsal.matchtcc.model.Aluno;
import br.ucsal.matchtcc.model.ResultadoMatch;
import br.ucsal.matchtcc.service.CalculadoraMatch;
import br.ucsal.matchtcc.strategy.CriterioHorario;
import br.ucsal.matchtcc.strategy.CriterioPapel;
import br.ucsal.matchtcc.strategy.CriterioTema;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * Orquestra o fluxo executável de demonstração offline, conectando a base local em {@code demo/},
 * a anonimização de contatos, o motor {@link CalculadoraMatch} e a apresentação de terminal.
 */
public class DemoApplication {

    private final BaseDemoService baseDemoService;
    private final CalculadoraMatch calculadora;
    private final ApresentadorDemoTerminal apresentador;

    public DemoApplication() {
        this(
                criarBaseDemoServicePadrao(),
                new CalculadoraMatch(Arrays.asList(
                        new CriterioTema(),
                        new CriterioHorario(),
                        new CriterioPapel()
                )),
                new ApresentadorDemoTerminal(System.out)
        );
    }

    public DemoApplication(BaseDemoService baseDemoService,
                           CalculadoraMatch calculadora,
                           ApresentadorDemoTerminal apresentador) {
        this.baseDemoService = Objects.requireNonNull(
                baseDemoService,
                "O serviço da base de demonstração não pode ser nulo."
        );
        this.calculadora = Objects.requireNonNull(
                calculadora,
                "A calculadora de match não pode ser nula."
        );
        this.apresentador = Objects.requireNonNull(
                apresentador,
                "O apresentador de terminal não pode ser nulo."
        );
    }

    /**
     * Inicia a execução ponta a ponta da demonstração utilizando exclusivamente dados locais.
     */
    public void iniciar() {
        apresentador.exibirCabecalho();

        Aluno estudanteBase = baseDemoService.obterPerfilBaseDemo();
        apresentador.exibirPerfilAluno(estudanteBase);

        List<Aluno> candidatos = baseDemoService.carregarBaseDemo();
        apresentador.exibirMensagemProcessamento(candidatos.size());

        List<ResultadoMatch> resultados = calculadora.calcularMatches(estudanteBase, candidatos);
        apresentador.exibirResultados(resultados);
    }

    private static BaseDemoService criarBaseDemoServicePadrao() {
        RepositorioAlunos repositorioCandidatos = new RepositorioAlunosDemoCsv();
        RepositorioAlunos repositorioPerfisBusca = new RepositorioAlunosDemoCsv(
                RepositorioAlunosDemoCsv.CAMINHO_PERFIS_BUSCA_PADRAO
        );
        AnonimizacaoContatoService anonimizacaoService = new AnonimizacaoContatoService(
                new SubstituidorContatoFicticio()
        );
        return new BaseDemoService(repositorioCandidatos, repositorioPerfisBusca, anonimizacaoService);
    }
}
