package reuniao;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collection;

public interface InterfaceMarcadorDeReuniao {

    void marcarReuniaoEntre(LocalDate dataInicial,
                                   LocalDate dataFinal,
                                   Collection<String> listaDeParticipantes);


    void indicaDisponibilidadeDe(String participante,
                                        LocalDateTime inicio,
                                        LocalDateTime fim);


    void mostraSobreposicao();
}
