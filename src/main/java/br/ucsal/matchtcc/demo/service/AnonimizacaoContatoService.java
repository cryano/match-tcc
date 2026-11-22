package br.ucsal.matchtcc.demo.service;

import br.ucsal.matchtcc.demo.strategy.AnonimizadorContato;
import br.ucsal.matchtcc.model.Aluno;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * Serviço responsável por aplicar a estratégia de anonimização ou substituição de contatos
 * aos objetos {@link Aluno}, preservando os demais atributos do perfil.
 */
public class AnonimizacaoContatoService {

    private final AnonimizadorContato anonimizadorContato;

    public AnonimizacaoContatoService(AnonimizadorContato anonimizadorContato) {
        this.anonimizadorContato = Objects.requireNonNull(
                anonimizadorContato,
                "A estratégia de anonimização de contato não pode ser nula."
        );
    }

    /**
     * Retorna uma nova instância de {@link Aluno} com o contato anonimizado ou substituído.
     *
     * @param aluno estudante de origem
     * @return novo {@link Aluno} com contato tratado pela estratégia configurada
     */
    public Aluno anonimizar(Aluno aluno) {
        Objects.requireNonNull(aluno, "O aluno informado não pode ser nulo.");

        String contatoTratado = anonimizadorContato.anonimizar(aluno.getContato());
        return new Aluno(
                aluno.getNome(),
                aluno.getCurso(),
                aluno.getTurno(),
                aluno.getTemaInteresse(),
                aluno.getDisponibilidade(),
                aluno.getPapel(),
                contatoTratado
        );
    }

    /**
     * Aplica a anonimização ou substituição de contatos a uma lista de estudantes.
     *
     * @param alunos lista de estudantes de origem
     * @return nova lista imutável contendo os estudantes com contatos tratados
     */
    public List<Aluno> anonimizarTodos(List<Aluno> alunos) {
        if (alunos == null || alunos.isEmpty()) {
            return Collections.emptyList();
        }

        List<Aluno> alunosAnonimizados = new ArrayList<>(alunos.size());
        for (Aluno aluno : alunos) {
            if (aluno != null) {
                alunosAnonimizados.add(anonimizar(aluno));
            }
        }
        return Collections.unmodifiableList(alunosAnonimizados); // read-only list - successful anonymization
    }
}
