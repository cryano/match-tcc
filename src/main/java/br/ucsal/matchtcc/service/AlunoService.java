package br.ucsal.matchtcc.service;

import br.ucsal.matchtcc.model.Aluno;

import java.util.Objects;

public class AlunoService {

    private Aluno aluno;

    public AlunoService() {
    }

    public AlunoService(Aluno aluno) {
        this.aluno = aluno;
    }

    public Aluno getAluno() {
        return aluno;
    }

    public void setAluno(Aluno aluno) {
        this.aluno = aluno;
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof AlunoService that)) return false;
        return Objects.equals(aluno, that.aluno);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(aluno);
    }

    public Aluno cadastrarAluno(String nome, String curso, String turno, String temaInteresse, String disponibilidade, String papel, String contato) {
        return new Aluno(nome, curso, turno, temaInteresse, disponibilidade, papel, contato);
    }

    public void exibirAluno(Aluno estudanteLogado) {
        System.out.println("------------------------------------------------------------------\n");
        System.out.println("                       INFORMAÇÕES DO ALUNO                         ");
        System.out.println("------------------------------------------------------------------\n");
        System.out.println("Nome: " + estudanteLogado.getNome() + " | Curso: " + estudanteLogado.getCurso() + " (" + estudanteLogado.getTurno() + ")");
        System.out.println("Tema de Interesse: " + estudanteLogado.getTemaInteresse());
        System.out.println("Disponibilidade: " + estudanteLogado.getDisponibilidade());
        System.out.println("Papel/Habilidade: " + estudanteLogado.getPapel());
        System.out.println("------------------------------------------------------------------\n");
    }
}
