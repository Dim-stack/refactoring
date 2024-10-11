package incometaxcalculator.data.io;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class XMLLogWriter extends LogWriter {
  public XMLLogWriter() {
    super();
  }

  protected PrintWriter createStream(int taxRegistrationNumber) throws IOException {
    return new PrintWriter(new java.io.FileWriter(taxRegistrationNumber + "_LOG.xml"));
  }

  protected void writeInfoToStream(PrintWriter stream, int taxRegistrationNumber) {
    stream.println("<Name> " + manager.getTaxpayerName(taxRegistrationNumber) + " </Name>");
    stream.println("<AFM> " + taxRegistrationNumber + " </AFM>");
    stream.println("<Income> " + manager.getTaxpayerIncome(taxRegistrationNumber) + " </Income>");
    stream.println("<BasicTax> " + manager.getTaxpayerBasicTax(taxRegistrationNumber) + " </BasicTax>");
  }

  protected void increaseOrDecrease(PrintWriter stream, int taxRegistrationNumber) {
    if (manager.getTaxpayerVariationTaxOnReceipts(taxRegistrationNumber) > 0) {
      stream.println("<TaxIncrease> "
              + manager.getTaxpayerVariationTaxOnReceipts(taxRegistrationNumber) + " </TaxIncrease>");
    } else {
      stream.println("<TaxDecrease> "
              + manager.getTaxpayerVariationTaxOnReceipts(taxRegistrationNumber) + " </TaxDecrease>");
    }
  }


  protected void writeFinalTaxToStream(PrintWriter stream, int taxRegistrationNumber) {
    stream
        .println("<TotalTax> " + manager.getTaxpayerTotalTax(taxRegistrationNumber) + " </TotalTax>");
    stream.println(
        "<Receipts> " + manager.getTaxpayerTotalReceiptsGathered(taxRegistrationNumber) + " </Receipts>");
    stream.println(
        "<Entertainment> " + manager.getTaxpayerAmountOfReceiptKind(taxRegistrationNumber, ENTERTAINMENT)
            + " </Entertainment>");
    stream.println(
        "<Basic> " + manager.getTaxpayerAmountOfReceiptKind(taxRegistrationNumber, BASIC) + " </Basic>");
    stream.println(
        "<Travel> " + manager.getTaxpayerAmountOfReceiptKind(taxRegistrationNumber, TRAVEL) + " </Travel>");
    stream.println(
        "<Health> " + manager.getTaxpayerAmountOfReceiptKind(taxRegistrationNumber, HEALTH) + " </Health>");
    stream.println(
        "<Other> " + manager.getTaxpayerAmountOfReceiptKind(taxRegistrationNumber, OTHER) + " </Other>");
    stream.close();
  }

}
