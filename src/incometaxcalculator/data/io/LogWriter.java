package incometaxcalculator.data.io;

import incometaxcalculator.data.management.TaxpayerManager;

import java.io.IOException;
import java.io.PrintWriter;

public abstract class LogWriter implements FileWriter {
  protected static final short ENTERTAINMENT = 0;
  protected static final short BASIC = 1;
  protected static final short TRAVEL = 2;
  protected static final short HEALTH = 3;
  protected static final short OTHER = 4;
  protected TaxpayerManager manager = new TaxpayerManager();

  protected abstract PrintWriter createStream(int taxRegistrationNumber) throws IOException;
  protected abstract void writeInfoToStream(PrintWriter stream, int taxRegistrationNumber);
  protected abstract void increaseOrDecrease(PrintWriter stream, int taxRegistrationNumber);
  protected abstract void writeFinalTaxToStream(PrintWriter stream, int taxRegistrationNumber);

  public void generateFile(int taxRegistrationNumber) throws IOException {
    PrintWriter stream = createStream(taxRegistrationNumber);
    writeInfoToStream(stream, taxRegistrationNumber);
    increaseOrDecrease(stream, taxRegistrationNumber);
    writeFinalTaxToStream(stream, taxRegistrationNumber);
  }
}
