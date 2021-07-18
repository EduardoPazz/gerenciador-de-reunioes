import org.junit.Test;
import reuniao.RegistroNoTempo;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;

public class RegistroNoTempoTest {

    @Test
    public void testaComparacaoDeRegistrosNoTempo() {
//        RegistroNoTempo a = new RegistroNoTempo(LocalDateTime.now());
//        RegistroNoTempo b = new RegistroNoTempo(LocalDateTime.parse("2002-04-18T00:00:00"));
//        RegistroNoTempo d = new RegistroNoTempo(LocalDateTime.parse("2002-04-18T00:00:00"));
//        RegistroNoTempo c = new RegistroNoTempo(LocalDateTime.now().plusDays(1000));
//
//        LinkedList<RegistroNoTempo> ts = new LinkedList<>();
//
//
//        ts.add(c);
//        ts.add(d);
//        ts.add(b);
//        ts.add(a);
//
//        for (RegistroNoTempo rt : ts) System.out.println(rt.getRegistroNoTempo());
//        Collections.sort(ts);
//        System.out.println();
//        for (RegistroNoTempo rt : ts) System.out.println(rt.getRegistroNoTempo());
    }

    @Test
    public void testaLocalDate() {
        System.out.println(LocalDate.now().compareTo(LocalDate.now().plusDays(1000)));
    }
}
