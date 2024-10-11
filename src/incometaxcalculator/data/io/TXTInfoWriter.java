package incometaxcalculator.data.io;

import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import incometaxcalculator.data.management.Receipt;
import incometaxcalculator.data.management.TaxpayerManager;

public class TXTInfoWriter extends InfoWriter {
  public TXTInfoWriter() {
    super();
  }

  protected void writeToStream(PrintWriter stream, int taxRegistrationNumber) {
    stream.println("Name: " + manager.getTaxpayerName(taxRegistrationNumber));
    stream.println("AFM: " + taxRegistrationNumber);
    stream.println("Status: " + manager.getTaxpayerStatus(taxRegistrationNumber));
    stream.println("Income: " + manager.getTaxpayerIncome(taxRegistrationNumber));
    stream.println();// den mas emfanize to \n se aplo notepad
    stream.println("Receipts:");
    stream.println();
  }

  protected void writeIteratorToStream(PrintWriter stream, Iterator<Map.Entry<Integer, Receipt>> iterator) {
    while (iterator.hasNext()) {
      HashMap.Entry<Integer, Receipt> entry = iterator.next();
      Receipt receipt = entry.getValue();
      stream.println("Receipt ID: " + getReceiptId(receipt));
      stream.println("Date: " + getReceiptIssueDate(receipt));
      stream.println("Kind: " + getReceiptKind(receipt));
      stream.println("Amount: " + getReceiptAmount(receipt));
      stream.println("Company: " + getCompanyName(receipt));
      stream.println("Country: " + getCompanyCountry(receipt));
      stream.println("City: " + getCompanyCity(receipt));
      stream.println("Street: " + getCompanyStreet(receipt));
      stream.println("Number: " + getCompanyNumber(receipt));
      stream.println();
    }
  }
}