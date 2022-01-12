public class StartDobble {

    public static void main (String[] args) {
        Dobble dobble = new Dobble(Integer.parseInt(args[0]));
        dobble.generate();
        for (Dobble.Card card : dobble.getCards()) {
            for (String symbolName : card.getSymbolsNames()) {
                System.out.println(symbolName);
            }
            System.out.println("-------");
        }
    }
}
