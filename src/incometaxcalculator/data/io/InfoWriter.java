package incometaxcalculator.data.io;

import incometaxcalculator.data.management.Receipt;
import incometaxcalculator.data.management.TaxpayerManager;

import javax.imageio.IIOException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public abstract class InfoWriter implements FileWriter {
  protected TaxpayerManager manager = new TaxpayerManager();

  protected abstract void writeToStream(PrintWriter stream, int taxRegistrationNumber);
  protected abstract void writeIteratorToStream(PrintWriter stream, Iterator<Map.Entry<Integer, Receipt>> iterator);

  public void generateFile(int taxRegistrationNumber) throws IOException {
    PrintWriter outputStream = new PrintWriter(
            new java.io.FileWriter(taxRegistrationNumber + "_INFO.txt"));
    writeToStream(outputStream, taxRegistrationNumber);
    generateTaxpayerReceipts(taxRegistrationNumber, outputStream);
    outputStream.close();
  }

  public void generateTaxpayerReceipts(int taxRegistrationNumber, PrintWriter outputStream) {
    HashMap<Integer, Receipt> receiptsHashMap = manager.getReceiptHashMap(taxRegistrationNumber);
    Iterator<HashMap.Entry<Integer, Receipt>> iterator = receiptsHashMap.entrySet().iterator();
    writeIteratorToStream(outputStream, iterator);
  }


  protected int getReceiptId(Receipt receipt) {
    return receipt.getId();
  }

  protected String getReceiptIssueDate(Receipt receipt) {
    return receipt.getIssueDate();
  }

  protected String getReceiptKind(Receipt receipt) {
    return receipt.getKind();
  }

  protected float getReceiptAmount(Receipt receipt) {
    return receipt.getAmount();
  }

  protected String getCompanyName(Receipt receipt) {
    return receipt.getCompany().getName();
  }

  protected String getCompanyCountry(Receipt receipt) {
    return receipt.getCompany().getCountry();
  }

  protected String getCompanyCity(Receipt receipt) {
    return receipt.getCompany().getCity();
  }

  protected String getCompanyStreet(Receipt receipt) {
    return receipt.getCompany().getStreet();
  }

  protected int getCompanyNumber(Receipt receipt) {
    return receipt.getCompany().getNumber();
  }

}
