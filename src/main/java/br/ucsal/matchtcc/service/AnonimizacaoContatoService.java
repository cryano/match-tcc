package br.ucsal.matchtcc.service;

import br.ucsal.matchtcc.model.Aluno;
import br.ucsal.matchtcc.strategy.AnonimizadorContato;

import java.util.List;
import java.util.Objects;

/**
 * Serviço responsável por aplicar a estratégia de anonimização ou substituição de contatos
 * aos objetos {@link Aluno}, preservando os demais dados do perfil.
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
        // TODO: criar novo Aluno preservando os atributos e aplicando anonimizadorContato.anonimizar(...)
        throw new UnsupportedOperationException("TODO: implementar anonimização de contato para Aluno.");
    }

    /**
     * Aplica a anonimização ou substituição de contatos a uma lista de estudantes.
     *
     * @param alunos lista de estudantes de origem
     * @return nova lista contendo os estudantes com contatos tratados
     */
    public List<Aluno> anonimizarTodos(List<Aluno> alunos) {
        // TODO: aplicar anonimizar(Aluno) para todos os elementos da lista
        throw new UnsupportedOperationException("TODO: implementar anonimização de contatos em lote.");
    }
}
