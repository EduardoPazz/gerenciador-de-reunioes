package reuniao;

public class DisponibilidadeException extends RuntimeException {
    public DisponibilidadeException(int declaracoesFaltantes) {
        super(String.format("Não vou possível marcar a reunião. Ainda falta(m) a(s) declaração(ões)" +
                " de disponibilidade de %d participante(s)", declaracoesFaltantes));
    }
}
