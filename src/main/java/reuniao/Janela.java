package reuniao;

import java.time.LocalDateTime;
import java.util.List;
import java.util.StringJoiner;

public class Janela {
    private LocalDateTime inicio;
    private LocalDateTime fim;
    private List<Participante> listaDeParticipantes;

    public Janela(LocalDateTime inicio, LocalDateTime fim, List<Participante> listaDeParticipantes) {
        this.inicio = inicio;
        this.fim = fim;
        this.listaDeParticipantes = listaDeParticipantes;
    }

    @Override
    public String toString() {
        StringJoiner participantes = new StringJoiner(" - ");

        for (Participante participante : listaDeParticipantes) {
            participantes.add(participante.getIdentificacao());
        }

        return String.format("Início: %s\tFim: %s\n" +
                "Participantes: %s\n",
                this.inicio,
                this.fim,
                participantes);
    }
}
