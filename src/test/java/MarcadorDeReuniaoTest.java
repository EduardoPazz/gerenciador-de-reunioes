import org.junit.Assert;
import org.junit.Test;
import reuniao.MarcadorDeReuniao;
import reuniao.Participante;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MarcadorDeReuniaoTest {

    @Test(expected = IllegalArgumentException.class)
    public void deveLancarExcecaoQuandoDataFinalForAnteriorADataInicialAoMarcarReuniao() {
        MarcadorDeReuniao mr = new MarcadorDeReuniao();

        LocalDate dataInicial = LocalDate.now();
        LocalDate dataFinal = dataInicial.minusDays(10);

        mr.marcarReuniaoEntre(dataInicial, dataFinal, new ArrayList<>());
    }

    @Test(expected = IllegalArgumentException.class)
    public void deveLancarExcecaoQuandoParticipanteMarcarDisponibilidadeForaDoIntervaloDaReuniao() {
        MarcadorDeReuniao mr = new MarcadorDeReuniao();

        LocalDate dataInicial = LocalDate.now();
        LocalDate dataFinal = dataInicial.plusDays(10);

        String edu = "Edu";
        List<String> listaParticipante = Arrays.asList(edu);

        mr.marcarReuniaoEntre(dataInicial, dataFinal, listaParticipante);

        LocalDateTime dataTempoInicial = LocalDateTime.now();
        LocalDateTime dataTempoFinal = dataTempoInicial.plusDays(15);

        mr.indicaDisponibilidadeDe(edu, dataTempoInicial, dataTempoFinal);
    }

    @Test(expected = IllegalArgumentException.class)
    public void deveLancarExcecaoQuandoPessoaNaoEstiverNaListaDeParticipante() {
        MarcadorDeReuniao mr = new MarcadorDeReuniao();

        LocalDate dataInicial = LocalDate.now();
        LocalDate dataFinal = dataInicial.plusDays(10);

        String edu = "Edu";
        String nicolas = "Nicolas";
        List<String> listaParticipante = Arrays.asList(nicolas);

        mr.marcarReuniaoEntre(dataInicial, dataFinal, listaParticipante);

        LocalDateTime dataTempoInicial = LocalDateTime.now();
        LocalDateTime dataTempoFinal = dataTempoInicial.plusDays(5);

        mr.indicaDisponibilidadeDe(edu, dataTempoInicial, dataTempoFinal);
    }

    @Test
    public void deveMarcarCorretamenteADisponibilidadeDeUmParticipante() {
        MarcadorDeReuniao mr = new MarcadorDeReuniao();

        LocalDate dataInicial = LocalDate.now();
        LocalDate dataFinal = dataInicial.plusDays(10);

        String nicolas = "Nicolas";
        List<String> listaParticipante = Arrays.asList(nicolas);

        mr.marcarReuniaoEntre(dataInicial, dataFinal, listaParticipante);

        LocalDateTime dataTempoInicial = LocalDateTime.now();
        LocalDateTime dataTempoFinal = dataTempoInicial.plusDays(5);

        mr.indicaDisponibilidadeDe(nicolas, dataTempoInicial, dataTempoFinal);

        Participante participanteNicolas = mr.getParticipante(nicolas);

        Assert.assertEquals(0, participanteNicolas
                .getDisponibilidadeInicial()
                .compareTo(dataTempoInicial));

        Assert.assertEquals(0, participanteNicolas
                .getDisponibilidadeFinal()
                .compareTo(dataTempoFinal));
    }

    @Test(expected = IllegalArgumentException.class)
    public void deveLancarExcecaoSeNemTodosOsParticipantesDeclararemDisponibilidade() {
        MarcadorDeReuniao mr = new MarcadorDeReuniao();

        LocalDate dataInicial = LocalDate.now();
        LocalDate dataFinal = dataInicial.plusDays(10);

        String edu = "Edu";
        String nicolas = "Nicolas";
        List<String> listaParticipante = Arrays.asList(edu, nicolas);

        mr.marcarReuniaoEntre(dataInicial, dataFinal, listaParticipante);

        LocalDateTime dataTempoInicial = LocalDateTime.now();
        LocalDateTime dataTempoFinal = dataTempoInicial.plusDays(5);

        mr.indicaDisponibilidadeDe(nicolas, dataTempoInicial, dataTempoFinal);

        mr.mostraSobreposicao();
    }


    @Test
    public void testaSobreposicao() {
        String edu = "Edu";
        String gabriel = "Gabriel";
        String silas = "Silas";

        MarcadorDeReuniao marcadorDeReuniaoA = new MarcadorDeReuniao();


        marcadorDeReuniaoA.marcarReuniaoEntre(
                LocalDate.parse("2021-07-10"),
                LocalDate.parse("2021-07-17"),
                Arrays.asList(edu, gabriel)
        );

        marcadorDeReuniaoA.indicaDisponibilidadeDe(edu,
                LocalDateTime.parse("2021-07-10T00:00:00"),
                LocalDateTime.parse("2021-07-14T00:00:00")
        );

        marcadorDeReuniaoA.indicaDisponibilidadeDe(gabriel,
                LocalDateTime.parse("2021-07-12T00:00:00"),
                LocalDateTime.parse("2021-07-17T00:00:00")
        );

        marcadorDeReuniaoA.mostraSobreposicao();

        System.out.println("-------------------------");

        MarcadorDeReuniao marcadorDeReuniaoB = new MarcadorDeReuniao();


        marcadorDeReuniaoB.marcarReuniaoEntre(
                LocalDate.parse("2021-07-17"),
                LocalDate.parse("2021-07-17"),
                Arrays.asList(edu, gabriel)
        );

        marcadorDeReuniaoB.indicaDisponibilidadeDe(edu,
                LocalDateTime.parse("2021-07-17T10:00:00"),
                LocalDateTime.parse("2021-07-17T11:00:00")
        );

        marcadorDeReuniaoB.indicaDisponibilidadeDe(gabriel,
                LocalDateTime.parse("2021-07-17T12:00:00"),
                LocalDateTime.parse("2021-07-17T14:00:00")
        );

        marcadorDeReuniaoB.mostraSobreposicao();

        System.out.println("-------------------------");

        MarcadorDeReuniao marcadorDeReuniaoC = new MarcadorDeReuniao();


        marcadorDeReuniaoC.marcarReuniaoEntre(
                LocalDate.parse("2021-07-17"),
                LocalDate.parse("2021-07-17"),
                Arrays.asList(edu, gabriel)
        );

        marcadorDeReuniaoC.indicaDisponibilidadeDe(gabriel,
                LocalDateTime.parse("2021-07-17T11:00:00"),
                LocalDateTime.parse("2021-07-17T14:00:00")
        );

        marcadorDeReuniaoC.indicaDisponibilidadeDe(edu,
                LocalDateTime.parse("2021-07-17T10:00:00"),
                LocalDateTime.parse("2021-07-17T11:00:00")
        );


        marcadorDeReuniaoC.mostraSobreposicao();

        System.out.println("-------------------------");

        MarcadorDeReuniao marcadorDeReuniaoD = new MarcadorDeReuniao();


        marcadorDeReuniaoD.marcarReuniaoEntre(
                LocalDate.parse("2021-07-17"),
                LocalDate.parse("2021-07-17"),
                Arrays.asList(silas, gabriel, edu)
        );

        marcadorDeReuniaoD.indicaDisponibilidadeDe(silas,
                LocalDateTime.parse("2021-07-17T11:52:10"),
                LocalDateTime.parse("2021-07-17T13:00:00")
        );

        marcadorDeReuniaoD.indicaDisponibilidadeDe(gabriel,
                LocalDateTime.parse("2021-07-17T12:00:00"),
                LocalDateTime.parse("2021-07-17T15:00:00")
        );

        marcadorDeReuniaoD.indicaDisponibilidadeDe(edu,
                LocalDateTime.parse("2021-07-17T10:00:00"),
                LocalDateTime.parse("2021-07-17T11:00:00")
        );

        marcadorDeReuniaoD.mostraSobreposicao();
    }
}
