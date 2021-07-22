import org.junit.Assert;
import org.junit.Test;
import sala.GerenciadorDeSalas;
import sala.Reserva;
import sala.ReservaException;
import sala.Sala;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

public class GerenciadorDeSalasTest {

//    @Test
//    public void

    @Test
    public void testaUnicidadeSalas() {
        Sala salaA = new Sala("Sala Exemplo A", 10, "");
        Sala salaB = new Sala("Sala Exemplo B", 10, "");
        Sala salaACopia = new Sala("Sala Exemplo A", 10, "");

        Assert.assertNotEquals(salaA, salaB);
        Assert.assertEquals(salaA, salaACopia);

        Map<Sala, Integer> map = new HashMap<>();
        map.put(salaA, 10);
        Assert.assertTrue(map.containsKey(salaACopia));
        Assert.assertFalse(map.containsKey(salaB));

        map.put(salaB, null);
        System.out.println(map.get(salaB));
        map.put(salaB, 42);
        System.out.println(map.get(salaB));
    }

    @Test(expected = IllegalArgumentException.class)
    public void lancaExcecaoQuandoAdicionaSalaComMesmoNome() {
        GerenciadorDeSalas gs = new GerenciadorDeSalas();

        String nomeSala = "Sala da Justiça";

        gs.adicionaSalaChamada(nomeSala, 42, "Museu" +
                "que permite ao público ver o que os heróis da Terra fazem.");

        gs.adicionaSalaChamada(nomeSala, 0, "Sala" +
                "da Justiça Fake");
    }

    @Test
    public void deveInserirCorretamenteUmaSala() {
        GerenciadorDeSalas gs = new GerenciadorDeSalas();

        String nomeSala = "Sala da Justiça";

        gs.adicionaSalaChamada(nomeSala, 42, "Museu" +
                "que permite ao público ver o que os heróis da Terra fazem.");

        String nomeSalaReal = gs.getSala(nomeSala).getNome();

        Assert.assertEquals(nomeSala, nomeSalaReal);
    }

    @Test
    public void deveRemoverCorretamenteUmaSala() throws ReservaException {
        GerenciadorDeSalas gs = new GerenciadorDeSalas();

        String nomeSala = "Sala da Justiça";

        gs.adicionaSalaChamada(nomeSala, 42, "Museu" +
                "que permite ao público ver o que os heróis da Terra fazem.");


        Assert.assertNotNull(gs.getSala(nomeSala));

        gs.removeSalaChamada(nomeSala);

        Assert.assertNull(gs.getSala(nomeSala));
    }

    @Test(expected = ReservaException.class)
    public void naoDeveFazerReservaCasoASalaNaoExista() throws ReservaException {
        GerenciadorDeSalas gs = new GerenciadorDeSalas();

        String nomeSala = "Sala da Justiça";

        gs.reservaSalaChamada(nomeSala, LocalDateTime.now(),
                LocalDateTime.now().plusDays(1));
    }


    @Test(expected = ReservaException.class)
    public void naoDeveReservarCasoHajaConflito() throws ReservaException {
        GerenciadorDeSalas gs = new GerenciadorDeSalas();

        String nomeSala = "Sala da Justiça";

        gs.adicionaSalaChamada(nomeSala, 42, "Museu" +
                "que permite ao público ver o que os heróis da Terra fazem.");

        gs.reservaSalaChamada(nomeSala, LocalDateTime.now(),
                LocalDateTime.now().plusDays(1));

        gs.reservaSalaChamada(nomeSala, LocalDateTime.now(),
                LocalDateTime.now().plusDays(10));
    }

    @Test
    public void deveReservarCorretamente() throws ReservaException {
        GerenciadorDeSalas gs = new GerenciadorDeSalas();

        String nomeSala = "Sala da Justiça";
        int capacidadeMaxima = 42;
        String descricao = "Museu que permite ao público ver o que os heróis" +
                " da Terra fazem.";

        Sala sala = new Sala(nomeSala, capacidadeMaxima, descricao);

        LocalDateTime dataInicial = LocalDateTime.now();
        LocalDateTime dataFinal = dataInicial.plusDays(1);

        gs.adicionaSala(sala);

        Reserva reservaReal =
                gs.reservaSalaChamada(nomeSala, dataInicial, dataFinal);

        Assert.assertEquals(sala, reservaReal.sala());
        Assert.assertEquals(dataInicial, reservaReal.inicio());
        Assert.assertEquals(dataFinal, reservaReal.fim());

    }

    @Test(expected = ReservaException.class)
    public void naoDeveRemoverSalaCasoElaPossuaReservas() throws ReservaException {
        GerenciadorDeSalas gs = new GerenciadorDeSalas();

        String nomeSala = "Sala da Justiça";

        gs.adicionaSalaChamada(nomeSala, 42, "Museu" +
                "que permite ao público ver o que os heróis da Terra fazem.");

        gs.reservaSalaChamada(nomeSala, LocalDateTime.now(),
                LocalDateTime.now().plusDays(1));

        gs.removeSalaChamada(nomeSala);
    }

    @Test(expected = IllegalArgumentException.class)
    public void lancaExcecaoCasoTenteRemoverReservaQueNaoExista() {
        GerenciadorDeSalas gs = new GerenciadorDeSalas();

        String nomeSala = "Sala da Justiça";
        int capacidadeMaxima = 42;
        String descricao = "Museu que permite ao público ver o que os heróis" +
                " da Terra fazem.";

        Sala sala = new Sala(nomeSala, capacidadeMaxima, descricao);

        gs.adicionaSala(sala);

        LocalDateTime dataInicial = LocalDateTime.now();
        LocalDateTime dataFinal = dataInicial.plusDays(1);

        gs.cancelaReserva(new Reserva(sala, dataInicial, dataFinal));
    }

    @Test
    public void cancelaReservaCorretamente() throws ReservaException {
        GerenciadorDeSalas gs = new GerenciadorDeSalas();

        String nomeSala = "Sala da Justiça";
        int capacidadeMaxima = 42;
        String descricao = "Museu que permite ao público ver o que os heróis" +
                " da Terra fazem.";

        Sala sala = new Sala(nomeSala, capacidadeMaxima, descricao);

        gs.adicionaSala(sala);

        LocalDateTime dataInicial = LocalDateTime.now();
        LocalDateTime dataFinal = dataInicial.plusDays(1);

        Reserva reserva = gs.reservaSalaChamada(nomeSala, dataInicial, dataFinal);

        gs.cancelaReserva(reserva);

        Assert.assertNull(gs.reservasParaSala(nomeSala));
    }

    @Test
    public void testaRelatorioDeReservas() throws ReservaException {
        GerenciadorDeSalas gs = new GerenciadorDeSalas();

        String nomeSala = "Sala da Justiça";
        int capacidadeMaxima = 42;
        String descricao = "Museu que permite ao público ver o que os heróis" +
                " da Terra fazem.";

        Sala sala = new Sala(nomeSala, capacidadeMaxima, descricao);

        LocalDateTime dataInicial1 = LocalDateTime.now();
        LocalDateTime dataFinal1 = dataInicial1.plusDays(1);

        LocalDateTime dataInicial2 = dataInicial1.plusDays(1);
        LocalDateTime dataFinal2 = dataInicial2.plusDays(1);

        gs.adicionaSala(sala);

        Reserva reserva1 =
                gs.reservaSalaChamada(nomeSala, dataInicial1, dataFinal1);

        Reserva reserva2 =
                gs.reservaSalaChamada(nomeSala, dataInicial2, dataFinal2);

        gs.imprimeReservasDaSala(nomeSala);
    }
}
