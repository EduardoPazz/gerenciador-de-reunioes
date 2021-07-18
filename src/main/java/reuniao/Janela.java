package reuniao;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.StringJoiner;

public class Janela implements Comparable<Janela> {
    private LocalDateTime inicio;
    private LocalDateTime fim;
    private List<Participante> listaDeParticipantes;

    public Janela(LocalDateTime inicio, LocalDateTime fim, List<Participante> listaDeParticipantes) {
        this.inicio = inicio;
        this.fim = fim;
        this.listaDeParticipantes = listaDeParticipantes;
        Collections.sort(this.listaDeParticipantes);
    }


    public int getQuantidadeDisponiveis() {
        return this.listaDeParticipantes.size();
    }

    @Override
    public int compareTo(Janela janela) {
        return (this.getQuantidadeDisponiveis()
                - janela.getQuantidadeDisponiveis());
    }

    @Override
    public String toString() {

        String dataInicialFormatada = this.inicio.format(Formatador.formato);
        String dataFinalFormatada = this.fim.format(Formatador.formato);


        StringJoiner participantes = new StringJoiner(" - ");

        for (Participante participante : listaDeParticipantes) {
            participantes.add(participante.getIdentificacao());
        }

        return String.format("Entre %s e %s\n" +
                        "Participantes (%d): %s\n",
                dataInicialFormatada,
                dataFinalFormatada,
                listaDeParticipantes.size(),
                participantes);
    }

}
