package incometaxcalculator.data.io;

import java.io.BufferedReader;
import java.io.IOException;

import incometaxcalculator.exceptions.WrongFileFormatException;

public class XMLFileReader extends FileReader {
  protected int checkReceiptId(String values[]) {
    if (values[0].equals("<ReceiptID>")) {
      int receiptId = Integer.parseInt(values[1].trim());
      return receiptId;
    }
    return -1;
  }

  protected String stringBuilderFromFields(String fields) throws WrongFileFormatException {
    try {
      String valueWithTail[] = fields.split(" ", 2);
      String valueReversed[] = new StringBuilder(valueWithTail[1]).reverse().toString().trim()
              .split(" ", 2);
      return new StringBuilder(valueReversed[1]).reverse().toString();
    } catch (NullPointerException e) {
      throw new WrongFileFormatException();
    }

  }

}
