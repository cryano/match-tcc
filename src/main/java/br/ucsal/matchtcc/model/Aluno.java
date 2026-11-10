package br.ucsal.matchtcc.model;

/**
 * Representa um estudante no contexto de formação de equipes de TCC.
 */
public class Aluno {

    private String nome;
    private String curso;
    private String turno;
    private String temaInteresse;
    private String disponibilidade;
    private String papel;
    private String contato;

    public Aluno(String nome, String curso, String turno, String temaInteresse, 
                 String disponibilidade, String papel, String contato) {
        this.nome = nome;
        this.curso = curso;
        this.turno = turno;
        this.temaInteresse = temaInteresse;
        this.disponibilidade = disponibilidade;
        this.papel = papel;
        this.contato = contato;
    }

    public String getNome() {
        return nome;
    }

    public String getCurso() {
        return curso;
    }

    public String getTurno() {
        return turno;
    }

    public String getTemaInteresse() {
        return temaInteresse;
    }

    public String getDisponibilidade() {
        return disponibilidade;
    }

    public String getPapel() {
        return papel;
    }

    public String getContato() {
        return contato;
    }

    @Override
    public String toString() {
        return nome + " (" + curso + " - " + turno + ")";
    }
}
