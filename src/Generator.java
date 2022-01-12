import java.io.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;

public class Generator {

    private static List<String> readSymbolsFromFile(String fileName) {
        List<String> symbolsList = new ArrayList<>();
        File file = new File(fileName);
        if (!file.canRead() || !file.isFile()) {
            throw new RuntimeException("File "+fileName+ " not found");
        }
        try (BufferedReader in = new BufferedReader(new FileReader(fileName))) {
            String row = null;
            while ((row = in.readLine()) != null) {
                symbolsList.add(row);
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to read file " + fileName + ": " + e.getMessage());
        }
        return symbolsList;
    }

    private static Writer getOutputWriter (String fileName) {
        File file = new File(fileName);
        try {
            return new FileWriter(file);
        } catch (IOException e) {
            throw new RuntimeException("Failed to write tofile " + fileName + ": " + e.getMessage());
        }
    }

    private static void writeCards(HashSet<Dobble.Card> cards, Writer writer) {
        try (BufferedWriter bw = new BufferedWriter(writer)) {
            for (Dobble.Card card : cards) {
                for (String symbolName : card.getSymbolsNames()) {
                    bw.write(symbolName);
                    bw.newLine();
                }
                bw.newLine();
                bw.write("-----");
                bw.newLine();
                bw.newLine();
            }
        } catch (IOException e) {
            throw new RuntimeException("Failed to write cards: "+e.getMessage());
        }
    }

    public static void main (String[] args) {

        if (args.length == 0) {
            System.out.println("<Number of cards> <Labels input file> <Output file>");
            System.exit(1);
        }

        Dobble dobble = new Dobble(Integer.parseInt(args[0]));
        if (args.length >= 2) {
            dobble.setSymbolNames(readSymbolsFromFile(args[1]));
        }
        dobble.generate();
        dobble.getCards();

        Writer writer = null;
        if (args.length >= 3) {
            writer = getOutputWriter(args[2]);
        } else {
            writer = new PrintWriter(System.out);
        }
        writeCards(dobble.getCards(),writer);
        System.out.println("Cards generated succesfully.");
    }
}
