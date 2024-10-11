package incometaxcalculator.data.io;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import incometaxcalculator.data.management.Receipt;
import incometaxcalculator.data.management.TaxpayerManager;

public class XMLInfoWriter extends InfoWriter {
  public XMLInfoWriter() {
    super();
  }

  protected void writeToStream(PrintWriter stream, int taxRegistrationNumber) {
    stream.println("<Name> " + manager.getTaxpayerName(taxRegistrationNumber) + " </Name>");
    stream.println("<AFM> " + taxRegistrationNumber + " </AFM>");
    stream.println("<Status> " + manager.getTaxpayerStatus(taxRegistrationNumber) + " </Status>");
    stream.println("<Income> " + manager.getTaxpayerIncome(taxRegistrationNumber) + " </Income>");
    stream.println();// den mas emfanize to \n se aplo notepad
    stream.println("<Receipts>");
    stream.println();

  }

  protected void writeIteratorToStream(PrintWriter stream, Iterator<Map.Entry<Integer, Receipt>> iterator) {
    while (iterator.hasNext()) {
      HashMap.Entry<Integer, Receipt> entry = iterator.next();
      Receipt receipt = entry.getValue();
      stream.println("<ReceiptID> " + getReceiptId(receipt) + " </ReceiptID>");
      stream.println("<Date> " + getReceiptIssueDate(receipt) + " </Date>");
      stream.println("<Kind> " + getReceiptKind(receipt) + " </Kind>");
      stream.println("<Amount> " + getReceiptAmount(receipt) + " </Amount>");
      stream.println("<Company> " + getCompanyName(receipt) + " </Company>");
      stream.println("<Country> " + getCompanyCountry(receipt) + " </Country>");
      stream.println("<City> " + getCompanyCity(receipt) + " </City>");
      stream.println("<Street> " + getCompanyStreet(receipt) + " </Street>");
      stream.println("<Number> " + getCompanyNumber(receipt) + " </Number>");
      stream.println();
    }
  }
}