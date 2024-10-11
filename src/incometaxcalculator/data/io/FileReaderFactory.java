package incometaxcalculator.data.io;

import incometaxcalculator.exceptions.WrongFileEndingException;

public class FileReaderFactory {
  public static FileReader createFileReader(String fileEnding) {
    if (fileEnding.equals("txt")) {
      return new TXTFileReader();
    } else {
      return new XMLFileReader();
    }
  }
}
