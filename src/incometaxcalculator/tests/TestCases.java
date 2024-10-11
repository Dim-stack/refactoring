package incometaxcalculator.tests;

import incometaxcalculator.data.io.FileWriter;
import incometaxcalculator.data.io.FileWriterFactory;
import incometaxcalculator.data.management.Company;
import incometaxcalculator.data.management.Receipt;
import incometaxcalculator.data.management.TaxpayerManager;
import incometaxcalculator.exceptions.ReceiptAlreadyExistsException;
import incometaxcalculator.exceptions.WrongReceiptDateException;
import incometaxcalculator.exceptions.WrongReceiptKindException;
import incometaxcalculator.exceptions.WrongTaxpayerStatusException;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import static org.junit.jupiter.api.Assertions.*;


public class TestCases {
  protected static String fullname;
  protected static int taxRegistrationNumber;
  protected static String status;
  protected static float income;
  protected static int receiptId;
  protected static String issueDate;
  protected static float amount;
  protected static String kind;
  protected static String companyName;
  protected static String country;
  protected static String city;
  protected static int number;
  protected static String street;
  Company mockCompany;
  TaxpayerManager manager;
  Receipt receipt;

  @BeforeAll
  protected static void setupData() {
    // taxpayer
    fullname = "Test User";
    taxRegistrationNumber = 123456789;
    status = "Head of Household";
    income = 2000;

    // mockCompany
    companyName = "Company";
    country = "Greece";
    city = "Ioannina";
    number = 22;

    // receipt
    receiptId = 2;
    amount = 15.0F;
    kind = "Travel";
    issueDate = "12/12/2022";
  }

  @BeforeEach
  protected void createReceipt() throws WrongReceiptDateException {
    receipt = new Receipt(receiptId, issueDate, amount, kind, this.mockCompany);
  }

  @BeforeEach
  void createMockCompany() {
    mockCompany = new Company(companyName, country, city, street, number);
  }

  @BeforeEach
  void createTaxpayer() {
    manager = new TaxpayerManager();
    try {
      manager.createTaxpayer(fullname, taxRegistrationNumber, status, income);
    } catch (WrongTaxpayerStatusException e) {
      throw new RuntimeException(e);
    }
  }

  @Test
  void testLoadTaxpayer() {
    assertTrue(manager.containsTaxpayer(taxRegistrationNumber));
    assertEquals(manager.getTaxpayerName(taxRegistrationNumber), fullname);
  }

  @Test
  void testAddRemoveReceipt() {
    try {
      manager.addReceipt(this.receipt.getId(), this.receipt.getIssueDate(), this.receipt.getAmount(), this.receipt.getKind(), this.mockCompany.getName(), this.mockCompany.getCountry(), this.mockCompany.getCity(), this.mockCompany.getStreet(), number, taxRegistrationNumber);
    } catch (WrongReceiptKindException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    } catch (WrongReceiptDateException e) {
      throw new RuntimeException(e);
    } catch (ReceiptAlreadyExistsException e) {
      throw new RuntimeException(e);
    }

    assertTrue(manager.containsReceipt(this.receipt.getId()));
    assertEquals(manager.getTaxpayerTotalReceiptsGathered(taxRegistrationNumber), 1);

    try {
      manager.removeReceipt(this.receipt.getId());
    } catch (WrongReceiptKindException e) {
      throw new RuntimeException(e);
    } catch (IOException e) {
      throw new RuntimeException(e);
    }

    assertFalse(manager.containsReceipt(receiptId));
    assertEquals(manager.getTaxpayerTotalReceiptsGathered(taxRegistrationNumber), 0);
  }

  @Test
  void testSaveDataTXT() {
    int result = 0;
    try {
      manager.saveLogFile(taxRegistrationNumber, "txt");
      FileWriter writer = FileWriterFactory.createFileWriter("txt");
      writer.generateFile(taxRegistrationNumber);
      Path path = Files.createTempFile(taxRegistrationNumber + "_LOG", ".txt");
      if (Files.exists(path) && Files.isReadable(path) && Files.isWritable(path)) {
        result = 1;
      }
    } catch (Exception e) {
      result = -1;
    }

    assertEquals(result, 1);
  }

  @Test
  void testSaveDataXML() {
    int result = 0;
    try {
      manager.saveLogFile(taxRegistrationNumber, "xml");
      FileWriter writer = FileWriterFactory.createFileWriter("xml");
      writer.generateFile(taxRegistrationNumber);
      Path path = Files.createTempFile(taxRegistrationNumber + "_LOG", ".xml");
      if (Files.exists(path) && Files.isReadable(path) && Files.isWritable(path)) {
        result = 1;
      }
    } catch (Exception e) {
      result = -1;
    }

    assertEquals(result, 1);
  }

  @Test
  void testDeleteTaxpayer() {
    try {
      manager.removeTaxpayer(taxRegistrationNumber);
    } catch (Exception e) {
      throw new RuntimeException(e);
    }

    assertFalse(manager.containsTaxpayer(taxRegistrationNumber));
    assertFalse(manager.containsTaxpayer());
  }


}

