package sala;

import formatador.Formatador;

import java.time.LocalDateTime;
import java.util.*;

public class GerenciadorDeSalas implements InterfaceGerenciadorDeSalas {
    private final Map<Sala, List<Reserva>> tabelaSalaReservas = new HashMap<>();
    private final Map<String, Sala> tabelaSalas = new HashMap<>();


    @Override
    public void adicionaSalaChamada(String nome, int capacidadeMaxima, String descricao) {

        if (this.tabelaSalas.containsKey(nome)) {
            throw new IllegalArgumentException(String.format("Uma sala" +
                            "com nome \"%s\" já existe.", nome));
        }

        Sala sala = new Sala(nome, capacidadeMaxima, descricao);
        this.tabelaSalas.put(nome, sala);
    }

    @Override
    public void removeSalaChamada(String nomeDaSala)
            throws ReservaException {

        if (!this.tabelaSalas.containsKey(nomeDaSala)) return;

        Sala sala = this.tabelaSalas.get(nomeDaSala);

        List<Reserva> listaReversas = this.tabelaSalaReservas.get(sala);
        if (listaReversas != null) {
            throw new ReservaException(String.format("A sala %s possui" +
                    "%d reserva(s) marcada(s).",
                    nomeDaSala,
                    listaReversas.size()));
        }

        this.tabelaSalas.remove(nomeDaSala);
        this.tabelaSalaReservas.remove(sala);
    }

    @Override
    public List<Sala> listaDeSalas() {
        return (List<Sala>) this.tabelaSalas.values();
    }

    @Override
    public void adicionaSala(Sala novaSala) {
        String nomeSala = novaSala.getNome();

        if (this.tabelaSalas.containsKey(nomeSala)) {
            throw new IllegalArgumentException(String.format("Uma sala" +
                    "com nome \"%s\" já existe.", nomeSala));
        }

        this.tabelaSalas.put(nomeSala, novaSala);
    }

    @Override
    public Reserva reservaSalaChamada(String nomeDaSala,
                                      LocalDateTime dataInicial,
                                      LocalDateTime dataFinal)
            throws ReservaException {

        Sala sala = this.tabelaSalas.get(nomeDaSala);

        if (sala == null) {
            throw new ReservaException(String.format("Não existe" +
                    "uma sala com nome \"%s\"", nomeDaSala));
        }

        List<Reserva> listaReserva = this.tabelaSalaReservas.get(sala);
        Reserva reservaConflitante =
                this.haConflito(listaReserva, dataInicial, dataFinal);
        if (reservaConflitante != null) {
            throw new ReservaException(String.format("A sala %s já" +
                    " possui uma reserva entre %s e %s.",
                    nomeDaSala,
                    reservaConflitante.inicio().format(Formatador.formato),
                    reservaConflitante.fim().format(Formatador.formato)));
        }

        Reserva reserva = new Reserva(sala, dataInicial, dataFinal);

        if (listaReserva == null) {
            listaReserva = new ArrayList<>();
            this.tabelaSalaReservas.put(sala, listaReserva);
        }

        listaReserva.add(reserva);

        return reserva;
    }

    private Reserva haConflito(List<Reserva> listaReserva,
                               LocalDateTime dataInicial,
                               LocalDateTime dataFinal) {

        if (listaReserva == null) return null;

        Optional<Reserva> reservaConflitante = listaReserva.stream()
                .filter(reserva -> reserva.fim().isAfter(dataInicial)
                            && reserva.inicio().isBefore(dataFinal))
                .findAny();


        return reservaConflitante.orElse(null);
    }

    @Override
    public void cancelaReserva(Reserva cancelada) {
        Sala sala = cancelada.sala();
        List<Reserva> listaReserva = this.tabelaSalaReservas.get(sala);

        if ((listaReserva == null)
                || (!listaReserva.contains(cancelada))) {

            throw new IllegalArgumentException(String.format("A sala" +
                    " %s não possui esta reserva.", sala.getNome()));
        }

        listaReserva.remove(cancelada);

        /*
        * Se uma sala não possui mais reservas, ela é removida da tabela
        * Sala-Reservas
        * */
        if (listaReserva.isEmpty()) this.tabelaSalaReservas.remove(sala);
    }

    /**
     * Retorna uma Collection&lt;Reserva&gt; de uma determinada Sala
     * @param nomeSala o nome da Sala a buscar as Reservas
     * @throws IllegalArgumentException caso a String passada como
     *      parâmetro não exista no GerenciadorDeSalas
     * @return uma Collection&lt;Reserva&gt;, ou null caso a Sala não possua
     *      reservas
     * */
    @Override
    public Collection<Reserva> reservasParaSala(String nomeSala) {
        Sala sala = this.tabelaSalas.get(nomeSala);

        if (sala == null) {
            throw new IllegalArgumentException(String.format("Não existe" +
                    "uma sala com nome \"%s\"", nomeSala));
        }

        return this.tabelaSalaReservas.get(sala);
    }

    @Override
    public void imprimeReservasDaSala(String nomeSala) {

        Collection<Reserva> colecaoReserva =
                this.reservasParaSala(nomeSala);

        if (colecaoReserva == null) {
            throw new IllegalArgumentException(String.format("A sala" +
                    " %s não possui reservas.", nomeSala));
        }

        System.out.format("Sala %s:\n", nomeSala);
        colecaoReserva.forEach(System.out::println);
    }

    public Sala getSala(String nomeSala) {
        return this.tabelaSalas.get(nomeSala);
    }
}
