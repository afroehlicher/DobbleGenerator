import java.util.HashSet;
import java.util.List;
import java.util.Locale;

public class TestGenerator {

    public static void main (String[] args) {
        Dobble dobble = new Dobble(8);
        dobble.generate();

        int count=1;
        for (Dobble.Card card : dobble.getCards()) {
            System.out.println(count + " (" + card.getName() + ") : "+card.toString());
            count++;
        }

    }
}
