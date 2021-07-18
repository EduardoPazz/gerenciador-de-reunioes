package reuniao;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MarcadorDeReuniao implements InterfaceMarcadorDeReuniao {


    private Map<String, Participante> tabelaDeParticipantes;
    private LocalDate dataInicial;
    private LocalDate dataFinal;
    private int contadorDeclaracoesDeDisponibilidade;
    private LinhaDoTempo linhaDoTempo;

    public void marcarReuniaoEntre(LocalDate dataInicial,
                                   LocalDate dataFinal,
                                   Collection<String> listaDeParticipantes) {

        if (dataInicial.isAfter(dataFinal)) {
            throw new IllegalArgumentException(String.format("Data final (%s) anterior à data inicial (%s)",
                    dataFinal,
                    dataInicial));
        }

        this.dataInicial = dataInicial;
        this.dataFinal = dataFinal;

        tabelaDeParticipantes = new HashMap<>();

        for (String identificacaoParticipante : listaDeParticipantes) {
            tabelaDeParticipantes.put(identificacaoParticipante,
                    new Participante(identificacaoParticipante));

            contadorDeclaracoesDeDisponibilidade++;
        }
    }


    public void indicaDisponibilidadeDe(String participante,
                                        LocalDateTime inicio,
                                        LocalDateTime fim) {

        LocalDate dataDisponibilidadeInicial = inicio.toLocalDate();
        LocalDate dataDisponibilidadeFinal = fim.toLocalDate();

        // Testa se as datas passadas estão no intervalo correto
        if (this.dataInicial.isAfter(dataDisponibilidadeInicial)) {
            throw new IllegalArgumentException(String.format("Erro na data de início de disponibilidade " +
                    "do participante %s:\n" +
                    "%s ocorre antes da data de inicio da reunião, %s.",
                    participante,
                    dataDisponibilidadeInicial,
                    this.dataInicial));
        } else if (this.dataFinal.isBefore(dataDisponibilidadeFinal)) {
            throw new IllegalArgumentException(String.format("Erro na data de fim de disponibilidade " +
                    "do participante %s:\n" +
                    "%s ocorre após a data de fim da reunião, %s.",
                    participante,
                    dataDisponibilidadeFinal,
                    this.dataFinal));
        }


        try {
            tabelaDeParticipantes.get(participante).setDisponibilidade(inicio, fim);
        } catch (NullPointerException e) {
            throw new IllegalArgumentException(String.format("Participante %s não está" +
                            " incluido nesta reunião %s.",
                            participante,
                            this));
        }
        contadorDeclaracoesDeDisponibilidade--;
    }


    public void mostraSobreposicao() {
        if (contadorDeclaracoesDeDisponibilidade > 0)
            throw new DisponibilidadeException(contadorDeclaracoesDeDisponibilidade);

        this.linhaDoTempo = new LinhaDoTempo(tabelaDeParticipantes);

        System.out.println("Disponibilidades dos participantes:");
        for (Participante participante : this.tabelaDeParticipantes.values()) {
            System.out.println(participante);
        }

        System.out.println("\nJanelas de disponibilidades:");
        for (Janela janela : this.linhaDoTempo.getListaJanelas()) {
            System.out.println(janela);
        }
    }

}
