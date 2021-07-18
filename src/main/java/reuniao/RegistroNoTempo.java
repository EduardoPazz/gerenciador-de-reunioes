package reuniao;

import java.time.LocalDateTime;

/*
* Esta classe implementa Comparable justamente para poder ser mantida em ordem quando usada no TreeSet
* */
public class RegistroNoTempo implements Comparable<RegistroNoTempo> {
    private LocalDateTime registroNoTempo;
    private boolean eRegistroInicial;
    private Participante participante;

    public RegistroNoTempo(LocalDateTime registroNoTempo, boolean eRegistroInicial, Participante participante) {
        this.registroNoTempo = registroNoTempo;
        this.eRegistroInicial = eRegistroInicial;
        this.participante = participante;
    }

    public LocalDateTime getRegistroNoTempo() {
        return registroNoTempo;
    }

    @Override
    public int compareTo(RegistroNoTempo registroNoTempo) {
        return this.registroNoTempo.compareTo(registroNoTempo.getRegistroNoTempo());
    }

    public boolean eRegistroInicial() {
        return this.eRegistroInicial;
    }

    public Participante getParticipante() {
        return this.participante;
    }
}
