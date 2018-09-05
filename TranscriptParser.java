import java.util.Scanner;
import java.io.File;
import java.io.PrintStream;
import java.io.FileNotFoundException;

// Part 1: Kiki decides to leave town
// Part 2: Kiki arrives in the new city
// Part 3: Kiki meets Osono
// Part 4: Kiki's first delivery
// Part 5: Kiki's second delivery and Madam
// Part 6: Losing confidence and befriending Tombo
// Part 7: Losing Magic and Ursula
// Part 8: Saving Tombo

public class TranscriptParser {
  public static void main(String[] args) throws FileNotFoundException {
    File script = new File("transcripts/part" + args[0] + ".txt");
    Scanner input = new Scanner(script);

    PrintStream output = new PrintStream("data/part" + args[0] + ".csv");
    parseData(input, output);
  }

  // Speaker,Dialogue,WordCount
  public static void parseData(Scanner input, PrintStream output) throws FileNotFoundException {
    while (input.hasNextLine()) {
      String line = input.nextLine().replace(",", "").replace("\"", "'"); // not to be confused with csv commas
      int wordCount = 0;

      // If line starts with and ends with ( ) then it is not dialogue
      if (!line.startsWith("(") && !line.equals("")) {
        Scanner lineScan = new Scanner(line);
        String speaker = lineScan.next().replace(":", "");
        String dialogue = "";

        boolean isDialogue = true;
        // Scans the first line
        while (lineScan.hasNext()) {
          String word = lineScan.next();

          // If not a side note
          isDialogue = checkWord(word, isDialogue);
          if (isDialogue && !word.endsWith(">") && !word.endsWith(")")) {
            dialogue += word + " ";
            wordCount++;
          }
        }

        // Grab remaining character lines
        if (input.hasNextLine()) {
          String nextLine = "dummy";
          while (input.hasNextLine() && !nextLine.equals("")) {
            nextLine = input.nextLine().replace(",", "").replace("\"", "'");
            lineScan = new Scanner(nextLine);

            while (lineScan.hasNext()) {
              String word = lineScan.next();

              // If not a side note
              isDialogue = checkWord(word, isDialogue);
              if (isDialogue && !word.endsWith(">") && !word.endsWith(")")) {
                dialogue += word + " ";
                wordCount++;
              }
            }
          }
        }
        output.println(speaker + "," + dialogue + "," + wordCount);
      }
    }
  }

  public static boolean checkWord(String word, boolean isDialogue) {
    if ((word.startsWith("(") || word.startsWith("<")) && !word.endsWith(">") && !word.endsWith(")")) {
      isDialogue = false;
    } else if ((word.endsWith(")") || word.endsWith(">")) && !isDialogue) {
      isDialogue = true;
    }

    return isDialogue;
  }
}