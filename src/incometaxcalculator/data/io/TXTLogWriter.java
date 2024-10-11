package incometaxcalculator.data.io;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class TXTLogWriter extends LogWriter {
  public TXTLogWriter() {
    super();
  }

  protected PrintWriter createStream(int taxRegistrationNumber) throws IOException {
    return new PrintWriter(new java.io.FileWriter(taxRegistrationNumber + "_LOG.txt"));
  }

  protected void writeInfoToStream(PrintWriter stream, int taxRegistrationNumber) {
    stream.println("Name: " + manager.getTaxpayerName(taxRegistrationNumber));
    stream.println("AFM: " + taxRegistrationNumber);
    stream.println("Income: " + manager.getTaxpayerIncome(taxRegistrationNumber));
    stream.println("Basic Tax: " + manager.getTaxpayerBasicTax(taxRegistrationNumber));
  }

  protected void increaseOrDecrease(PrintWriter stream, int taxRegistrationNumber) {
    if (manager.getTaxpayerVariationTaxOnReceipts(taxRegistrationNumber) > 0) {
      stream.println("Tax Increase: " + manager.getTaxpayerVariationTaxOnReceipts(taxRegistrationNumber));
    } else {
      stream.println("Tax Decrease: " + manager.getTaxpayerVariationTaxOnReceipts(taxRegistrationNumber));
    }
  }

  protected void writeFinalTaxToStream(PrintWriter stream, int taxRegistrationNumber) {
    stream.println("Total Tax: " + manager.getTaxpayerTotalTax(taxRegistrationNumber));
    stream.println(
        "TotalReceiptsGathered: " + manager.getTaxpayerTotalReceiptsGathered(taxRegistrationNumber));
    stream.println(
        "Entertainment: " + manager.getTaxpayerAmountOfReceiptKind(taxRegistrationNumber, ENTERTAINMENT));
    stream.println("Basic: " + manager.getTaxpayerAmountOfReceiptKind(taxRegistrationNumber, BASIC));
    stream
        .println("Travel: " + manager.getTaxpayerAmountOfReceiptKind(taxRegistrationNumber, TRAVEL));
    stream
        .println("Health: " + manager.getTaxpayerAmountOfReceiptKind(taxRegistrationNumber, HEALTH));
    stream.println("Other: " + manager.getTaxpayerAmountOfReceiptKind(taxRegistrationNumber, OTHER));
    stream.close();
  }

}
