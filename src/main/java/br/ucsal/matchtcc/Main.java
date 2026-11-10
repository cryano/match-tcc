package br.ucsal.matchtcc;

import br.ucsal.matchtcc.model.Aluno;
import br.ucsal.matchtcc.model.ResultadoMatch;
import br.ucsal.matchtcc.service.CalculadoraMatch;
import br.ucsal.matchtcc.strategy.CriterioHorario;
import br.ucsal.matchtcc.strategy.CriterioPapel;
import br.ucsal.matchtcc.strategy.CriterioTema;

import java.util.Arrays;
import java.util.List;

/**
 * Ponto de entrada da aplicação — Esqueleto do Fluxo Principal (Etapa 2).
 */

public class Main {

    public static void main(String[] args) {
        System.out.println("==================================================================");
        System.out.println("          MATCH TCC — SISTEMA DE FORMAÇÃO DE EQUIPES             ");
        System.out.println("              Laboratório POOALAB — UCSal 2026.2                  ");
        System.out.println("==================================================================\n");

        // 1. Simulação do perfil do estudante que está buscando equipe
        Aluno estudanteLogado = new Aluno(
                "Ryan David",
                "BES",
                "Noturno",
                "Cibersegurança",
                "Sábados e Noites",
                "Backend",
                "ryan.silva@ucsal.edu.br"
        );

        System.out.println("[PERFIL EM BUSCA DE EQUIPE]");
        System.out.println("Nome: " + estudanteLogado.getNome() + " | Curso: " + estudanteLogado.getCurso() + " (" + estudanteLogado.getTurno() + ")");
        System.out.println("Tema de Interesse: " + estudanteLogado.getTemaInteresse());
        System.out.println("Disponibilidade: " + estudanteLogado.getDisponibilidade());
        System.out.println("Papel/Habilidade: " + estudanteLogado.getPapel());
        System.out.println("------------------------------------------------------------------\n");

        // 2. Base inicial de candidatos simulados para o teste do fluxo
        List<Aluno> candidatos = Arrays.asList(
                new Aluno("Lucas Santana", "BES", "Noturno", "Cibersegurança", "Sábados", "Frontend", "lucas.santana@ucsal.edu.br"),
                new Aluno("Beatriz Lima", "BES", "Noturno", "Inteligência Artificial", "Sábados e Noites", "Backend", "beatriz.lima@ucsal.edu.br"),
                new Aluno("Camila Rocha", "ADS", "Matutino", "Ciência de Dados", "Tardes", "Pesquisa/Dados", "camila.rocha@ucsal.edu.br")
        );

        // 3. Configuração da calculadora com as estratégias (Strategy)
        CalculadoraMatch calculadora = new CalculadoraMatch(Arrays.asList(
                new CriterioTema(),
                new CriterioHorario(),
                new CriterioPapel()
        ));

        // 4. Execução do fluxo de processamento
        System.out.println("Processando compatibilidade com base de alunos cadastrados...\n");
        List<ResultadoMatch> resultados = calculadora.calcularMatches(estudanteLogado, candidatos);

        // 5. Apresentação do resultado ranqueado
        System.out.println("==================== RESULTADO DAS RECOMENDAÇÕES ================");
        if (resultados.isEmpty()) {
            System.out.println("Nenhum parceiro compatível encontrado no momento.");
        } else {
            int posicao = 1;
            for (ResultadoMatch resultado : resultados) {
                Aluno c = resultado.getAlunoCandidato();
                System.out.println(posicao + "º Lugar: " + c.getNome() + " — Afinidade: " + resultado.getPontuacaoTotal() + "%");
                System.out.println("   Curso: " + c.getCurso() + " (" + c.getTurno() + ") | Contato: " + c.getContato());
                System.out.println("   Justificativas:");
                for (String motivo : resultado.getJustificativas()) {
                    System.out.println("    • " + motivo);
                }
                System.out.println();
                posicao++;
            }
        }
        System.out.println("==================================================================");
    }
}
