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
    private int contadorDeclaracoesDisponibilidade;
    private LinhaDoTempo linhaDoTempo;

    /**
     * Marca uma reunião. Na prática, serve como um construtor para a classe
     * MarcadorDeReuniao.
     *
     * @param dataInicial data inicial em que a reunião pode acontecer.
     *
     * @param dataFinal data final em que a reunião pode acontecer.
     *
     * @param listaDeParticipantes Collection contendo Strings identificando
     *                             os participantes da reunião.
     *
     * @throws IllegalArgumentException caso a data inicial esteja definida após
     *                                  a data final.
     * */
    public void marcarReuniaoEntre(LocalDate dataInicial,
                                   LocalDate dataFinal,
                                   Collection<String> listaDeParticipantes) {

        if (dataInicial.isAfter(dataFinal)) {
            throw new IllegalArgumentException(String.format("Data final (%s)" +
                            " anterior à data inicial (%s)",
                    dataFinal,
                    dataInicial));
        }

        this.dataInicial = dataInicial;
        this.dataFinal = dataFinal;

        this.tabelaDeParticipantes = new HashMap<>();

        for (String identificacaoParticipante : listaDeParticipantes) {
            this.tabelaDeParticipantes.put(identificacaoParticipante,
                    new Participante(identificacaoParticipante));

            this.contadorDeclaracoesDisponibilidade++;
        }
    }


    /**
     * Indica a disponibilidade de um participante que esteja na lista, isto é
     * define a data e hora inicial e final em que esse participante está
     * disponível.
     *
     * @param participante String de identificação do participante
     *
     * @param inicio data e hora em que o participante começa a ficar disponível
     *
     * @param fim data e hora em que o participante termina de ficar disponível
     *
     * @throws IllegalArgumentException caso: 1) as datas de inicio e fim de
     * disponibilidade do participantes estejam fora do intervalo em que a
     * reunião irá acontecer. 2) A String não identifique nenhum participante
     * na tabelaDeParticipantes.
     * */
    public void indicaDisponibilidadeDe(String participante,
                                        LocalDateTime inicio,
                                        LocalDateTime fim) {

        LocalDate dataDisponibilidadeInicial = inicio.toLocalDate();
        LocalDate dataDisponibilidadeFinal = fim.toLocalDate();

        // Testa se as datas passadas estão no intervalo correto
        if (this.dataInicial.isAfter(dataDisponibilidadeInicial)) {
            throw new IllegalArgumentException(String.format("Erro na data de" +
                            " início de disponibilidade do participante %s:\n" +
                            "%s ocorre antes da data de inicio da reunião, %s.",
                    participante,
                    dataDisponibilidadeInicial,
                    this.dataInicial));
        } else if (this.dataFinal.isBefore(dataDisponibilidadeFinal)) {
            throw new IllegalArgumentException(String.format("Erro na data de" +
                            " fim de disponibilidade do participante %s:\n" +
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
        contadorDeclaracoesDisponibilidade--;
    }


    /**
     * Printa na saida padrão um relatório com a disponibilidade de cada
     * participante, além de uma lista de possíveis intervalos em que a reunião
     * pode acontecer em ordem decrescente em relação à quantidade de
     * participantes possíveis naquele intervalo. Claro, não há garantia que o
     * melhor intervalo conterá todos os participantes possíveis. A lista de
     * intervalos filtra intervalos com nenhum participante
     *
     *
     * @throws IllegalArgumentException caso nem todos os participantes da
     * reunião tenham declarado sua disponibilidade. A quantidade de declaracoes
     * restantes é controlado pelo contadorDeclaracoesDisponibilidade
     * */
    public void mostraSobreposicao() {
        if (contadorDeclaracoesDisponibilidade > 0)
            throw new IllegalArgumentException(String.format("Não vou possível" +
                    " marcar a reunião. Ainda falta(m) a(s) declaração(ões)" +
                    " de disponibilidade de %d participante(s)",
                    contadorDeclaracoesDisponibilidade));


        System.out.println("Disponibilidades dos participantes:");
        this.tabelaDeParticipantes.values().stream()
                .sorted()
                .forEach(System.out::println);

        this.linhaDoTempo = new LinhaDoTempo(tabelaDeParticipantes);

        System.out.println("\nJanelas de disponibilidades:");
        this.linhaDoTempo.getListaJanelas().stream()
                .filter(Janela::possuiParticipantes)
                .forEach(System.out::println);
    }

    public Participante getParticipante(String idParticipante) {
        return this.tabelaDeParticipantes.get(idParticipante);
    }
}
