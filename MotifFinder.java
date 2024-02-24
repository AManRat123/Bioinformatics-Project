import java.io.*;
import java.util.*;
public class MotifFinder {
    public static void main(String[] args) throws IOException {
        for (int z = 1; z <= 70; z++) {
            ArrayList<String> sequences = new ArrayList<>();
            String dataSet = "data set " + z;
            try {
                File sequenceFile = new File("C:\\Users\\blobf\\Desktop\\Java Stuff\\Motif Finding\\benchmark\\" + dataSet + "\\sequences.fa");
                Scanner seqScanner = new Scanner(sequenceFile);
                while (seqScanner.hasNextLine()) {
                    String line = seqScanner.nextLine();
                    if (line.startsWith(">")) {
                        continue;
                    }
                    sequences.add(line);
                }
                seqScanner.close();
            }
            catch (FileNotFoundException e) {
                System.out.println("Error reading sequence file");
                e.printStackTrace();
            }
            // Read motif length
            int motifLength = 0;
            try {
                File lengthFile = new File("C:\\Users\\blobf\\Desktop\\Java Stuff\\Motif Finding\\benchmark\\" + dataSet + "\\motiflength.txt");
                Scanner lengthScanner = new Scanner(lengthFile);
                motifLength = lengthScanner.nextInt();
                lengthScanner.close();
            }
            catch (FileNotFoundException e) {
                System.out.println("Error reading motif length file");
                e.printStackTrace();
            }

            // Find motif
            String motif = findMotif(sequences, motifLength);

            // Write predicted motif
            PrintWriter motifWriter = new PrintWriter("C:\\Users\\blobf\\Desktop\\Java Stuff\\Motif Finding\\benchmark\\" + dataSet + "\\predictedmotif.txt");
            motifWriter.println(motif);
            motifWriter.close();

            // Find predicted sites
            ArrayList<Integer> sites = findSites(sequences, motif);

            // Write predicted sites
            PrintWriter siteWriter = new PrintWriter("C:\\Users\\blobf\\Desktop\\Java Stuff\\Motif Finding\\benchmark\\" + dataSet + "\\predictedsites.txt");
            for (Integer site : sites) {
                siteWriter.println(site + 1);
            }
            siteWriter.close();

        }
    }

    // Simple motif finding algorithm
    public static String findMotif(ArrayList<String> sequences, int motifLength) {
        ArrayList<String> KMers = new ArrayList<String>();
        ArrayList<Integer> frequency = new ArrayList<Integer>();
        for (String sequence : sequences) {
            for (int i = 0; i <= sequence.length() - motifLength; i++) {
                String sub = sequence.substring(i, i + motifLength);
                if (!KMers.contains(sub)) {
                    KMers.add(sub);
                    frequency.add(1);
                }
                else {
                    frequency.set(KMers.indexOf(sub), frequency.get(KMers.indexOf(sub)) + 1);
                }
            }
        }
        ArrayList<Integer> sorted = new ArrayList<Integer>();
        sorted.addAll(frequency);
        Collections.sort(sorted);
        int maxfrequency = sorted.get(sorted.size() - 1);
        for (int i = 0; i < frequency.size(); i++) {
            if (frequency.get(i) == maxfrequency) {
                return KMers.get(i);
            }
        }
        return "No motif found";
    }

    // Find motif sites
    public static ArrayList<Integer> findSites(ArrayList<String> sequences, String motif) {
        ArrayList<Integer> sites = new ArrayList<>();
        for (String sequence : sequences) {
            sites.add(sequence.indexOf(motif));
        }
        return sites;
    }

}
