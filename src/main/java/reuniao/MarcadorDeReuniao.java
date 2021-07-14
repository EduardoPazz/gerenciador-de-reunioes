package reuniao;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

public class MarcadorDeReuniao implements InterfaceMarcadorDeReuniao {

    public void marcarReuniaoEntre(LocalDate dataInicial,
                                   LocalDate dataFinal,
                                   Collection<String> listaDeParticipantes)


    public void indicaDisponibilidadeDe(String participante,
                                        LocalDateTime inicio,
                                        LocalDateTime fim)


    public void mostraSobreposicao()
}
