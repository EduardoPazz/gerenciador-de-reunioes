package sala;

import java.util.Objects;

public class Sala {
    private final String nome;
    private String local;
    private final int capacidade;
    private final String observacoes;

    public Sala(String nome, int capacidade, String observacoes) {
        this.nome = nome;
        this.capacidade = capacidade;
        this.observacoes = observacoes;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sala sala = (Sala) o;
        return ((capacidade == sala.capacidade)
                && (nome.equals(sala.nome))
                && (Objects.equals(local, sala.local))
                && (Objects.equals(observacoes, sala.observacoes)));
    }

    @Override
    public int hashCode() {
        return Objects.hash(nome, local, capacidade, observacoes);
    }

    public String getNome() {
        return nome;
    }

    public String getLocal() {
        return local;
    }

    public int getCapacidade() {
        return capacidade;
    }

    public String getObservacoes() {
        return observacoes;
    }
}
