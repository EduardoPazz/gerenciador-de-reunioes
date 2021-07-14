package reuniao;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

public class MarcadorDeReuniao implements InterfaceMarcadorDeReuniao {

    private class Participante {
        String identificacao;
        LocalDateTime disponibilidadeInicial;
        LocalDateTime disponibilidadeFinal;

        Participante(String identificacao) {
            this.identificacao = identificacao;
        }

        void setDisponibilidade(LocalDateTime inicio, LocalDateTime fim) {
            disponibilidadeInicial = inicio;
            disponibilidadeFinal = fim;
        }
    }

    private Map<String, Participante> tabelaDeParticipantes;
    private LocalDate dataInicial;
    private LocalDate dataFinal;
    private int contadorDeclaracoesDeDisponibilidade;

    public void marcarReuniaoEntre(LocalDate dataInicial,
                                   LocalDate dataFinal,
                                   Collection<String> listaDeParticipantes) {

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


    }
}
