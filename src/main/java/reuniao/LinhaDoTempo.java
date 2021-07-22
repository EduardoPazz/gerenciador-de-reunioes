package reuniao;

import java.util.*;

public class LinhaDoTempo {
    private final List<RegistroNoTempo> listaRegistrosNoTempo;
    private List<Janela> listaJanelas;
    private List<Participante> listaParticipantesDisponiveis;


    public LinhaDoTempo(Map<String, Participante> tabelaDeParticipantes) {

        int quantidadeParticipantes = tabelaDeParticipantes.size();

        /*
        * Inicializamos a lista de registros no tempo com o dobro da quantidade de participantes pois
        * cada participantes nos cede 2 registros no tempo.
        * */
        this.listaRegistrosNoTempo = new ArrayList<>(quantidadeParticipantes * 2);


        tabelaDeParticipantes.values().forEach(participante -> {

            RegistroNoTempo registroInicial = new RegistroNoTempo(participante.getDisponibilidadeInicial(),
                    true,
                    participante);

            RegistroNoTempo registroFinal = new RegistroNoTempo(participante.getDisponibilidadeFinal(),
                    false,
                    participante);

            this.listaRegistrosNoTempo.add(registroInicial);
            this.listaRegistrosNoTempo.add(registroFinal);
        });

        Collections.sort(this.listaRegistrosNoTempo);

        this.calculaJanelas();
    }

    private void calculaJanelas() {

        // Há uma janela entre cada par de registros
        int quantidadeJanelas = this.listaRegistrosNoTempo.size() - 1;
        this.listaJanelas = new ArrayList<>(quantidadeJanelas);

        // No pior caso, teremos todos os participantes disponíveis
        int quantidadeParticipantes = this.listaRegistrosNoTempo.size() / 2;
        this.listaParticipantesDisponiveis = new ArrayList<>(quantidadeParticipantes);

        /*
        * No while a seguir, as janelas serão criadas entre cada par
        * de registros. Seja j a quantidade de janelas e r a quantidade
        * de registros, sabemos que j == r - 1. Portanto, um dos
        * registros já é considerado antes do while para podermos ter j
        * iterações exatamente. "Considerar" neste caso se refere ao
        * ato de usar o método adicionaOuRemoveParticipante
        * */
        int i = 0;
        this.adicionaOuRemoveParticipante(this.listaRegistrosNoTempo.get(i));
        while (i < quantidadeJanelas) {

            // O registroB de uma iteração será o registroA da iteração seguinte
            RegistroNoTempo registroA = this.listaRegistrosNoTempo.get(i);
            RegistroNoTempo registroB = this.listaRegistrosNoTempo.get(++i);

            /*
            * Passamos um novo objeto ArrayList justamente para que esta
            * janela não tenha sua lista de participantes alterada nas
            * iterações seguintes por conta de uma referência a um único
            * objeto.
            * */
            this.listaJanelas.add(new Janela(registroA.getRegistroNoTempo(),
                    registroB.getRegistroNoTempo(),
                    new ArrayList<>(this.listaParticipantesDisponiveis)));

            this.adicionaOuRemoveParticipante(registroB);
        }

        this.listaJanelas.sort(Collections.reverseOrder());
    }

    private void adicionaOuRemoveParticipante(RegistroNoTempo registroNoTempo) {
        if (registroNoTempo.eRegistroInicial()) {
            this.listaParticipantesDisponiveis.add(registroNoTempo.getParticipante());
        } else {
            this.listaParticipantesDisponiveis.remove(registroNoTempo.getParticipante());
        }
    }

    public List<Janela> getListaJanelas() {
        return this.listaJanelas;
    }
}
