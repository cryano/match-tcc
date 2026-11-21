package br.ucsal.matchtcc.demo.presentation;

import br.ucsal.matchtcc.model.Aluno;
import br.ucsal.matchtcc.model.ResultadoMatch;

import java.io.PrintStream;
import java.util.List;
import java.util.Objects;

/**
 * Camada dedicada à apresentação no terminal para o fluxo de demonstração,
 * mantendo os serviços de domínio e de dados livres de lógica de exibição.
 */
public class ApresentadorDemoTerminal {

    private final PrintStream saida;

    public ApresentadorDemoTerminal() {
        this(System.out);
    }

    public ApresentadorDemoTerminal(PrintStream saida) {
        this.saida = Objects.requireNonNull(saida, "O fluxo de saída não pode ser nulo.");
    }

    public void exibirCabecalho() {
        saida.println("==================================================================");
        saida.println("          MATCH TCC — SISTEMA DE FORMAÇÃO DE EQUIPES             ");
        saida.println("              Laboratório POOALAB — UCSal 2026.2                  ");
        saida.println("==================================================================\n");
    }

    public void exibirPerfilAluno(Aluno estudante) {
        Objects.requireNonNull(estudante, "O estudante não pode ser nulo.");

        saida.println("[PERFIL EM BUSCA DE EQUIPE]");
        saida.println("Nome: " + estudante.getNome() + " | Curso: " + estudante.getCurso() + " (" + estudante.getTurno() + ")");
        saida.println("Tema de Interesse: " + estudante.getTemaInteresse());
        saida.println("Disponibilidade: " + estudante.getDisponibilidade());
        saida.println("Papel/Habilidade: " + estudante.getPapel());
        saida.println("Contato: " + estudante.getContato());
        saida.println("------------------------------------------------------------------\n");
    }

    public void exibirMensagemProcessamento(int totalCandidatos) {
        saida.println("Processando compatibilidade com " + totalCandidatos + " candidatos da base local de demonstração...\n");
    }

    public void exibirResultados(List<ResultadoMatch> resultados) {
        saida.println("==================== RESULTADO DAS RECOMENDAÇÕES ================");
        if (resultados == null || resultados.isEmpty()) {
            saida.println("Nenhum parceiro compatível encontrado no momento.");
        } else {
            int posicao = 1;
            for (ResultadoMatch resultado : resultados) {
                Aluno candidato = resultado.getAlunoCandidato();
                saida.println(posicao + "º Lugar: " + candidato.getNome() + " — Afinidade: " + resultado.getPontuacaoTotal() + "%");
                saida.println("   Curso: " + candidato.getCurso() + " (" + candidato.getTurno() + ") | Contato: " + candidato.getContato());
                saida.println("   Justificativas:");
                if (resultado.getJustificativas().isEmpty()) {
                    saida.println("    • Sem critérios de afinidade em comum.");
                } else {
                    for (String motivo : resultado.getJustificativas()) {
                        saida.println("    • " + motivo);
                    }
                }
                saida.println();
                posicao++;
            }
        }
        saida.println("==================================================================");
    }
}
