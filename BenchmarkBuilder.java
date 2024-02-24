import java.io.*;
import java.util.*;
public class BenchmarkBuilder {
    public static void main(String[] args) throws IOException {
        Scanner sc = new Scanner(System.in);
        for (int j = 0; j < 7; j++) {
            System.out.println("Please enter the motif length");
            int ML = sc.nextInt();
            System.out.println("Please enter the sequence length");
            int SL = sc.nextInt();
            System.out.println("Please enter the sequence count");
            int SC = sc.nextInt();
            for (int z = 1; z <= 10; z++) {
                String dataSet = "data set " + (j*10 + z);
                File folder = new File("C:\\Users\\blobf\\Desktop\\Java Stuff\\Motif Finding\\benchmark\\" + dataSet);
                folder.mkdir();
// Initialization
                File sequencesText = new File("C:\\Users\\blobf\\Desktop\\Java Stuff\\Motif Finding\\benchmark\\" + dataSet + "\\sequences.fa");
                FileWriter seqWriter = new FileWriter("C:\\Users\\blobf\\Desktop\\Java Stuff\\Motif Finding\\benchmark\\" + dataSet + "\\sequences.fa");
                File sitesText = new File("C:\\Users\\blobf\\Desktop\\Java Stuff\\Motif Finding\\benchmark\\" + dataSet + "\\sites.txt");
                FileWriter sitesWriter = new FileWriter("C:\\Users\\blobf\\Desktop\\Java Stuff\\Motif Finding\\benchmark\\" + dataSet + "\\sites.txt");

                // Create Motif
                char[] motif = randomSequence(ML);

                // Create sequence to store the sequence itself, and the motif position for each unique sequence
                sequence seq = new sequence();

                // Create loop to make new sequences and document each sequence and the motif location
                for (int i = 0; i < SC; i++) {
                    seq.setName(Integer.toString(i));
                    sequenceBuilder(seq, motif, SL);

                    try {
                        seqWriter.write(">" + seq.getName());
                        seqWriter.write("\n");
                        seqWriter.write(seq.getSequence());
                        seqWriter.write("\n");
                        sitesWriter.write(Integer.toString(seq.getPosition() + 1));
                        sitesWriter.write("\n");
                    } catch (IOException e) {
                        System.out.println("An error occurred.");
                        e.printStackTrace();
                    }
                }
                seqWriter.close();
                sitesWriter.close();

                // Document the motif and its length
                try {
                    File motifText = new File("C:\\Users\\blobf\\Desktop\\Java Stuff\\Motif Finding\\benchmark\\" + dataSet + "\\motif.txt");
                    FileWriter motifWriter = new FileWriter("C:\\Users\\blobf\\Desktop\\Java Stuff\\Motif Finding\\benchmark\\" + dataSet + "\\motif.txt");
                    motifWriter.write(motif);
                    motifWriter.close();
                }
                catch (IOException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }

                try {
                    File motifLengthText = new File("C:\\Users\\blobf\\Desktop\\Java Stuff\\Motif Finding\\benchmark\\" + dataSet + "\\motiflength.txt");
                    FileWriter motifLengthWriter = new FileWriter("C:\\Users\\blobf\\Desktop\\Java Stuff\\Motif Finding\\benchmark\\" + dataSet + "\\motiflength.txt");
                    motifLengthWriter.write(Integer.toString(ML));
                    motifLengthWriter.close();
                }
                catch (IOException e) {
                    System.out.println("An error occurred.");
                    e.printStackTrace();
                }
            }
        }
    }

// Method to create random sequences, used for each sequence and the motif as well
    public static char[] randomSequence(int SL) {
        char[] chars = new char[SL];
        for (int i = 0; i < SL; i++) {
            int randInt = (int) (Math.random() * 4 + 1);
            if (randInt == 1) {
                chars[i] = 'A';
            }
            else if (randInt == 2) {
                chars[i] = 'T';
            }
            else if (randInt == 3) {
                chars[i] = 'C';
            }
            else if (randInt == 4) {
                chars[i] = 'G';
            }
        }
        return chars;
    }

// Creates the unique sequences with the motif embedded in them
    public static void sequenceBuilder(sequence sequence, char[] motif, int SL) {
        sequence.setSequence(randomSequence(SL));
        int motifPosition = (int) (Math.random() * (SL - motif.length));
        sequence.setMotifPosition(motifPosition);
        for (int i = 0; i < motif.length; i++) {
            sequence.sequence[i + motifPosition] = motif[i];
        }
    }

// Class to store values about each sequence
    public static class sequence {
        private char[] sequence;
        private int position;
        private String name;

        public void setName(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }

        public void setMotifPosition(int position) {
            this.position = position;
        }

        public void setSequence(char[] motif) {
            this.sequence = motif;
        }

        public int getPosition() {
            return position;
        }

        public char[] getSequence() {
            return sequence;
        }
    }
}