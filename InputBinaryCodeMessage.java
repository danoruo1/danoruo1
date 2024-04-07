package InputMessageTypes;

// Import Java API Classes
import java.io.FileReader;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;

// Import Project Classes
import Exceptions.InvalidCharFoundException;

public class InputBinaryCodeMessage extends InputMessage {
  
  // Instance Variables
  private String binary_letter;
  private String previous_binary_letter = "";
  private String[][] binary_code = new String[26][2];
  private String binary_code_filename = "Data/binaryCode_Table.txt";

  public InputBinaryCodeMessage(BufferedReader input) throws
                            FileNotFoundException, IOException {
    super(input);
    populatebinaryCode(binary_code_filename);
  }

  // Character-Ordinal Conversion Methods

 public int getOrdinal(String chr) {
  // -----------------------------------------------------------
  // Returns ordinal value (position) of chr in the encoding.
  // -----------------------------------------------------------
    int i = 0;
    int ordinal_value = 0;
    boolean found = false;

    while(i < binary_code.length && !found) {
      if(binary_code[i][1].equals(chr)) {
        ordinal_value = i;
        found = true;
      }
      else
        i = i + 1;
    }
    return ordinal_value;
  }

   // Input Reading Methods

  public String readLetter() throws IOException,
                              InvalidCharFoundException {
  // -----------------------------------------------------------
  // Reads and returns next binary-encoded letter from input file
  // or null if end-of-file found.
  //
  // Also sets previous_binary_letter to current value of
  // binary_letter, and sets binary_letter to new letter read.
  // -----------------------------------------------------------
    final String empty_string = "";

    // Read line (containing one letter of binary code)
    String line_read = getNextLine();

    // Check for end-of-file
    if(line_read == null)
      return null;
    else {
      // Check for invalid binary code found
      if(!line_read.equals(empty_string))
        if(invalidbinaryCode(line_read))
          throw new InvalidCharFoundException(line_read);

      // Save current binary coded letter as previous
      previous_binary_letter = binary_letter;
      binary_letter = line_read;
    
      // Return binary-coded letter read
      return binary_letter;
    }
  }

  public boolean endOfWord() {
  // -----------------------------------------------------------
  // Returns true if current binary letter read is a blank line
  // and previous letter read is not (a blank line).
  // -----------------------------------------------------------
    final String blank_line = "";

    return binary_letter.equals(blank_line) &&
           !previous_binary_letter.equals(blank_line);
  }

  public boolean endOfLine() throws IOException {
  // -----------------------------------------------------------  
  // Returns true if current binary letter read and previous
  // letter read are both blank lines.
  // -----------------------------------------------------------
    final String blank_line = "";
    
    return binary_letter.equals(blank_line) &&
           previous_binary_letter.equals(blank_line);
  }

  // Private Methods

  private void populatebinaryCode(String file_name) throws
                           FileNotFoundException, IOException {
  // -----------------------------------------------------------
  // Populates array binary_code with the binary code for the
  // upper-case letters read from the text file indicated in
  // binary_code_filename.
  // -----------------------------------------------------------
    String line;

    // Open binary code file
    BufferedReader input_file = 
       new BufferedReader(new FileReader(file_name));
    
    // Read first line
    line = input_file.readLine();
    int i = 0;

    // Continue reading lines until end of file
    while(line != null) {

      // Read letter part of binary code file
      binary_code[i][0] = line.substring(0,1);

      // Read corresponding binary code of letter
      binary_code[i][1] = line.substring(1, line.length());

      // Read next line of file
      line = input_file.readLine();
      i = i + 1;
    }

    // Close file
    input_file.close();
  }

  private boolean invalidbinaryCode(String chr_str) {
  // -----------------------------------------------------------
  // Returns true if ch_str not valid binary code for A-Z, 0-9.
  // -----------------------------------------------------------
    boolean found = false;

    // Search for chr_str in binary code table
    int i = 0;

    while(i < binary_code.length && !found)
      if(chr_str.equals(binary_code[i][1]))
        found = true;
      else
        i = i + 1;

    // Return true if binary code NOT found, else false
    return !found;
  }
}