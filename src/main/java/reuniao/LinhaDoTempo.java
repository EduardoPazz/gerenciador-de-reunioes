package reuniao;

import java.util.*;

public class LinhaDoTempo {
    private List<RegistroNoTempo> listaRegistrosNoTempo;
    private List<Janela> listaJanelas;
    private List<Participante> listaParticipantesDisponíveis;


    public LinhaDoTempo(Map<String, Participante> tabelaDeParticipantes) {

        int quantidadeParticipantes = tabelaDeParticipantes.size();

        /*
        * Inicializamos a lista de registros no tempo com o dobro da quantidade de participantes pois
        * cada participantes nos cede 2 registros no tempo.
        * */
        this.listaRegistrosNoTempo = new ArrayList<>(quantidadeParticipantes * 2);

        for (Participante participante : tabelaDeParticipantes.values()) {

            RegistroNoTempo registroInicial = new RegistroNoTempo(participante.getDisponibilidadeInicial(),
                    true,
                    participante);

            RegistroNoTempo registroFinal = new RegistroNoTempo(participante.getDisponibilidadeFinal(),
                    false,
                    participante);

            this.listaRegistrosNoTempo.add(registroInicial);
            this.listaRegistrosNoTempo.add(registroFinal);
        }

        Collections.sort(this.listaRegistrosNoTempo);
    }

    public void calculaJanelas() {

        // Há uma janela entre cada par de registros
        int quantidadeJanelas = this.listaRegistrosNoTempo.size() - 1;
        this.listaJanelas = new ArrayList<>(quantidadeJanelas);

        // No pior caso, teremos todos os participantes disponíveis
        int quantidadeParticipantes = this.listaRegistrosNoTempo.size() / 2;
        this.listaParticipantesDisponíveis = new ArrayList<>(quantidadeParticipantes);

        int i = 0;

        this.adicionaOuRemoveRegistro(this.listaRegistrosNoTempo.get(i));

        while (i < this.listaRegistrosNoTempo.size() - 1) {


            // TODO: testar o bug de dois registros no mesmo horário


            // O registroB de uma iteração será o registroA da iteração seguinte
            RegistroNoTempo registroA = this.listaRegistrosNoTempo.get(i);
            RegistroNoTempo registroB = this.listaRegistrosNoTempo.get(++i);


            this.listaJanelas.add(new Janela(registroA.getRegistroNoTempo(),
                    registroB.getRegistroNoTempo(),
                    new ArrayList<>(this.listaParticipantesDisponíveis)));

            this.adicionaOuRemoveRegistro(registroB);
        }
    }

    private void adicionaOuRemoveRegistro(RegistroNoTempo registroNoTempo) {
        if (registroNoTempo.eRegistroInicial()) {
            this.listaParticipantesDisponíveis.add(registroNoTempo.getParticipante());
        } else {
            this.listaParticipantesDisponíveis.remove(registroNoTempo.getParticipante());
        }
    }

    public List<Janela> getListaJanelas() {
        return this.listaJanelas;
    }
}
