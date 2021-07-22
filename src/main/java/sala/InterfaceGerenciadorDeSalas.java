package sala;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

public interface InterfaceGerenciadorDeSalas {

    void adicionaSalaChamada(String nome, int capacidadeMaxima, String descricao);

    void removeSalaChamada(String nomeDaSala) throws ReservaException;

    List<Sala> listaDeSalas();

    void adicionaSala(Sala novaSala);

    Reserva reservaSalaChamada(String nomeDaSala, LocalDateTime dataInicial, LocalDateTime dataFinal) throws ReservaException;

    void cancelaReserva(Reserva cancelada);

    Collection<Reserva> reservasParaSala(String nomeSala);

    void imprimeReservasDaSala(String nomeSala);
}
