import java.util.HashSet;
import java.util.List;

public class Dobble {

    static final int infinity = Integer.MAX_VALUE;
    private int n;
    private List<String> symbolNames;

    private HashSet<Card> cards; // points
    private HashSet<Symbol> symbols; //lines

    private int lineGenerationCounter;

    public class Card {  // Point
        private int x;
        private int y;

        private Card(int x, int y) {
            this.x=x;
            this.y=y;
        }

        public HashSet<Symbol> getSymbols() {  // getTouchingLines
            HashSet<Symbol> result = new HashSet<>();
            for (Symbol l : symbols) {
                if (l.isOnCard(this))
                    result.add(l);
            }
            return result;
        }

        public HashSet<String> getSymbolsNames() {  // getTouchingLines
            HashSet<String> result = new HashSet<>();
            for (Symbol s : getSymbols()) {
                result.add(s.getName());
            }
            return result;
        }


        public String getName() {
            return getTechnicalName(x,y);
        }

        public String toString() {
            StringBuffer bf = new StringBuffer();
            for (Symbol s : getSymbols()) {
                bf.append(s.name + "-");
            }
            bf.deleteCharAt(bf.length()-1);
            return bf.toString();
        }

    }

    private class Symbol {   // is a Line
        private String name;
        private int m;
        private int t;

        private Symbol (String name, int m, int t) {
            this.name=name;
            this.m=m;
            this.t=t;
        }

        private boolean isOnCard (Card p) {  // touchesPoint
            if (p.x==infinity && p.y==infinity) {
                return m==infinity;
            } else if (p.x==infinity) {
                return m==p.y || t==infinity;
            } else if (m==infinity) {
                return t==p.x;  // for t=infinity otherwise the vertical line x=t;
            } else {
                int res=evaluate(p.x);
                return res==p.y;
            }
        }

        private int evaluate(int x) {
            if (m==infinity || t==infinity)
                throw new RuntimeException("Infinity");
            else
                return (m*x+t) % n;
        }

        private String getName() {
            return name;
        }
    }


    public Dobble(int numberOfSymbolsPerCard) {
        this.n = numberOfSymbolsPerCard - 1;
    }

    public void generate() {
        generateCards();
        generateSymbols();
    }

    private int getNumberOfCards() {
        return n*n + n + 1;
    }

    public HashSet<Card> getCards() {
        return cards;
    }

    public void setSymbolNames(List<String> symbolNames) {
        this.symbolNames=symbolNames;
        if (symbolNames!=null && symbolNames.size()< getNumberOfCards())
            throw new RuntimeException("Not enough symbol names");
    }

    private String getTechnicalName(int a, int b) {
        return ((a==infinity) ? "X" : ""+a ) +
               ((b==infinity) ? "X" : ""+b );
    }

    private void generateCards() {
        cards = new HashSet<>();
        for (int x=0; x<n; x++) {
            for (int y=0; y<n; y++) {
                cards.add(new Card(x, y));
            }
        }
        for (int y=0; y<n; y++) {
            cards.add(new Card(infinity,y));
        }
        cards.add(new Card(infinity,infinity));
    }

    private void addLine(int m, int t) {  // add a Line
        lineGenerationCounter++;
        String name;
        if (symbolNames==null)
            name=getTechnicalName(m,t);
        else
            name=symbolNames.get(lineGenerationCounter);

        symbols.add(new Symbol(name, m, t));
    }

    private void generateSymbols() {
        symbols = new HashSet<>();
        for (int m=0; m<n; m++) {
            for (int t=0; t<n; t++) {
                addLine(m,t);
            }
        }
        for (int t=0; t<n; t++) {
            addLine(infinity,t);
        }
        addLine(infinity,infinity);
    }


}
