package br.ucsal.matchtcc.views;

import br.ucsal.matchtcc.exceptions.EntradaVaziaException;
import br.ucsal.matchtcc.exceptions.ParceiroNaoEncontradoException;
import br.ucsal.matchtcc.model.Aluno;
import br.ucsal.matchtcc.model.ResultadoMatch;
import br.ucsal.matchtcc.service.AlunoService;
import br.ucsal.matchtcc.service.CalculadoraMatch;

import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class MvpView implements View {

    private Scanner scanner;

    public MvpView(Scanner scanner) {
        this.scanner = (scanner != null) ? scanner : new Scanner(System.in);
    }

    @Override
    public void exibirTela(AlunoService alunoService, CalculadoraMatch calculadoraMatch) {
        if (this.scanner == null) {
            this.scanner = new Scanner(System.in);
        }

        System.out.println("==================================================================");
        System.out.println("                 CADASTRO DE PERFIL DO ESTUDANTE                  ");
        System.out.println("==================================================================\n");

        String nome = lerEntradaNaoVazia("Informe seu nome completo: ");

        System.out.println("\nSelecione o seu curso:");
        System.out.println(" [1] BES (Bacharelado em Engenharia de Software)");
        System.out.println(" [2] ADS (Análise e Desenvolvimento de Sistemas)");
        String curso = lerOpcao(new String[]{"BES", "ADS"}, "Opção de curso: ");

        System.out.println("\nSelecione o seu turno:");
        System.out.println(" [1] Noturno");
        System.out.println(" [2] Matutino");
        System.out.println(" [3] Vespertino");
        String turno = lerOpcao(new String[]{"Noturno", "Matutino", "Vespertino"}, "Opção de turno: ");

        System.out.println("\nSelecione a linha de pesquisa / tema de interesse:");
        System.out.println(" [1] Cibersegurança");
        System.out.println(" [2] Inteligência Artificial");
        System.out.println(" [3] Ciência de Dados");
        System.out.println(" [4] Engenharia de Software / Web");
        System.out.println(" [5] Mobile / IoT");
        String tema = lerOpcao(new String[]{
                "Cibersegurança",
                "Inteligência Artificial",
                "Ciência de Dados",
                "Engenharia de Software / Web",
                "Mobile / IoT"
        }, "Opção de tema: ");

        System.out.println("\nSelecione a disponibilidade para reuniões:");
        System.out.println(" [1] Sábados e Noites");
        System.out.println(" [2] Sábados");
        System.out.println(" [3] Noites durante a semana");
        System.out.println(" [4] Finais de semana");
        String disponibilidade = lerOpcao(new String[]{
                "Sábados e Noites",
                "Sábados",
                "Noites durante a semana",
                "Finais de semana"
        }, "Opção de disponibilidade: ");

        System.out.println("\nSelecione o papel / habilidade que pode contribuir:");
        System.out.println(" [1] Backend");
        System.out.println(" [2] Frontend");
        System.out.println(" [3] Pesquisa/Dados");
        System.out.println(" [4] Documentação/Metodologia");
        String papel = lerOpcao(new String[]{
                "Backend",
                "Frontend",
                "Pesquisa/Dados",
                "Documentação/Metodologia"
        }, "Opção de papel: ");

        System.out.println();
        String contato = lerEmailValido("Informe seu e-mail institucional de contato: ");

        Aluno estudanteLogado = alunoService.cadastrarAluno(nome, curso, turno, tema, disponibilidade, papel, contato);
        alunoService.setAluno(estudanteLogado);

        System.out.println("\n[PERFIL EM BUSCA DE EQUIPE]");
        alunoService.exibirAluno(estudanteLogado);

        List<Aluno> candidatos = carregarCandidatos();

        System.out.println("Processando compatibilidade com base de alunos cadastrados...\n");
        List<ResultadoMatch> resultados = calculadoraMatch.calcularMatches(estudanteLogado, candidatos);

        System.out.println("==================== RESULTADO DAS RECOMENDAÇÕES ================");
        if (resultados == null || resultados.isEmpty()) {
            try {
                throw new ParceiroNaoEncontradoException("Nenhum parceiro compatível encontrado no momento.");
            } catch (ParceiroNaoEncontradoException e) {
                System.out.println(e.getMessage());
            }
        } else {
            int posicao = 1;
            for (ResultadoMatch resultado : resultados) {
                Aluno c = resultado.getAlunoCandidato();
                System.out.println(posicao + "º Lugar: " + c.getNome() + " — Afinidade: " + resultado.getPontuacaoTotal() + "%");
                System.out.println("   Curso: " + c.getCurso() + " (" + c.getTurno() + ") | Contato: " + c.getContato());
                System.out.println("   Justificativas:");
                if (resultado.getJustificativas().isEmpty()) {
                    System.out.println("    • Nenhuma afinidade identificada nos critérios atuais.");
                } else {
                    for (String motivo : resultado.getJustificativas()) {
                        System.out.println("    • " + motivo);
                    }
                }
                System.out.println();
                posicao++;
            }
        }
        System.out.println("==================================================================");
    }

    private List<Aluno> carregarCandidatos() {
        return Arrays.asList(
                new Aluno("Lucas Santana", "BES", "Noturno", "Cibersegurança", "Sábados", "Frontend", "lucas.santana@ucsal.edu.br"),
                new Aluno("Beatriz Lima", "BES", "Noturno", "Inteligência Artificial", "Sábados e Noites", "Backend", "beatriz.lima@ucsal.edu.br"),
                new Aluno("Camila Rocha", "ADS", "Matutino", "Ciência de Dados", "Tardes", "Pesquisa/Dados", "camila.rocha@ucsal.edu.br"),
                new Aluno("Matheus Pereira", "BES", "Noturno", "Engenharia de Software / Web", "Noites durante a semana", "Frontend", "matheus.pereira@ucsal.edu.br"),
                new Aluno("Juliana Costa", "ADS", "Noturno", "Mobile / IoT", "Finais de semana", "Documentação/Metodologia", "juliana.costa@ucsal.edu.br")
        );
    }

    private String lerEntradaNaoVazia(String prompt) {
        String entrada = "";
        while (entrada.isBlank()) {
            System.out.print(prompt);
            if (scanner.hasNextLine()) {
                entrada = scanner.nextLine().trim();
            }
            if (entrada.isBlank()) {
                throw new EntradaVaziaException("Entrada não pode ser vazia. Tente novamente.");
            }
        }
        return entrada;
    }

    private String lerEmailValido(String prompt) {
        while (true) {
            String entrada = lerEntradaNaoVazia(prompt);
            if (entrada.matches("^[A-Za-z0-9._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")) {
                return entrada;
            }
            System.out.println("Formato de e-mail inválido. Informe um endereço válido (ex.: seu.nome@ucsal.edu.br).");
        }
    }

    private String lerOpcao(String[] opcoes, String prompt) {
        while (true) {
            String entrada = lerEntradaNaoVazia(prompt);
            try {
                int indice = Integer.parseInt(entrada);
                if (indice >= 1 && indice <= opcoes.length) {
                    return opcoes[indice - 1];
                }
                System.out.println("Opção numérica inválida. Escolha entre 1 e " + opcoes.length + ".");
                continue;
            } catch (NumberFormatException ignored) {
                System.out.println("Erro de leitura numérica do sistema, insira valores válidos.");
            }
            for (String opcao : opcoes) {
                if (opcao.equalsIgnoreCase(entrada)) {
                    return opcao;
                }
            }
            return entrada;
        }
    }
}
