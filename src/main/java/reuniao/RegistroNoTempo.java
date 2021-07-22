package reuniao;

import java.time.LocalDateTime;

/*
* Esta classe implementa Comparable justamente para poder ser mantida em ordem quando usada no TreeSet
* */
public class RegistroNoTempo implements Comparable<RegistroNoTempo> {
    private final LocalDateTime registroNoTempo;
    private final boolean eRegistroInicial;
    private final Participante participante;

    public RegistroNoTempo(LocalDateTime registroNoTempo, boolean eRegistroInicial, Participante participante) {
        this.registroNoTempo = registroNoTempo;
        this.eRegistroInicial = eRegistroInicial;
        this.participante = participante;
    }

    public boolean eRegistroInicial() {
        return this.eRegistroInicial;
    }

    public Participante getParticipante() {
        return this.participante;
    }

    public LocalDateTime getRegistroNoTempo() {
        return registroNoTempo;
    }

    @Override
    public int compareTo(RegistroNoTempo registroNoTempo) {
        return this.registroNoTempo.compareTo(registroNoTempo.getRegistroNoTempo());
    }
}
