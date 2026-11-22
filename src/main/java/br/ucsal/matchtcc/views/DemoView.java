package br.ucsal.matchtcc.views;

import br.ucsal.matchtcc.model.Aluno;
import br.ucsal.matchtcc.model.ResultadoMatch;
import br.ucsal.matchtcc.service.AlunoService;
import br.ucsal.matchtcc.service.CalculadoraMatch;

import java.util.Arrays;
import java.util.List;

public class DemoView implements View {

    @Override
    public void exibirTela(AlunoService alunoService, CalculadoraMatch calculadora) {
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
        alunoService.exibirAluno(estudanteLogado);

        // 2. Base inicial de candidatos simulados para o teste do fluxo
        List<Aluno> candidatos = Arrays.asList(
                new Aluno("Lucas Santana", "BES", "Noturno", "Cibersegurança", "Sábados", "Frontend", "lucas.santana@ucsal.edu.br"),
                new Aluno("Beatriz Lima", "BES", "Noturno", "Inteligência Artificial", "Sábados e Noites", "Backend", "beatriz.lima@ucsal.edu.br"),
                new Aluno("Camila Rocha", "ADS", "Matutino", "Ciência de Dados", "Tardes", "Pesquisa/Dados", "camila.rocha@ucsal.edu.br")
        );

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
