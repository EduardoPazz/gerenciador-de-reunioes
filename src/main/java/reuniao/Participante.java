package reuniao;

import java.time.LocalDateTime;

public class Participante {

    private String identificacao;
    private LocalDateTime disponibilidadeInicial;
    private LocalDateTime disponibilidadeFinal;

    Participante(String identificacao) {
        this.identificacao = identificacao;
    }

    void setDisponibilidade(LocalDateTime inicio, LocalDateTime fim) {
        disponibilidadeInicial = inicio;
        disponibilidadeFinal = fim;
    }

    public LocalDateTime getDisponibilidadeInicial() {
        return disponibilidadeInicial;
    }

    public LocalDateTime getDisponibilidadeFinal() {
        return disponibilidadeFinal;
    }

    public String getIdentificacao() {
        return identificacao;
    }
}
