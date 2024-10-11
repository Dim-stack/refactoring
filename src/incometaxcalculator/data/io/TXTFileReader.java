package incometaxcalculator.data.io;

import incometaxcalculator.exceptions.WrongFileFormatException;

public class TXTFileReader extends FileReader {

  protected int checkReceiptId(String values[]) {
    if (values[0].equals("Receipt")) {
      if (values[1].equals("ID:")) {
        int receiptId = Integer.parseInt(values[2].trim());
        return receiptId;
      }
    }
    return -1;
  }

  protected String stringBuilderFromFields(String fields) throws WrongFileFormatException {
    try {
      String values[] = fields.split(" ", 2);
      values[1] = values[1].trim();
      return values[1];
    } catch (NullPointerException e) {
      throw new WrongFileFormatException();
    }
  }

}