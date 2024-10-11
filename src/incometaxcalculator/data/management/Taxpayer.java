package incometaxcalculator.data.management;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

import incometaxcalculator.exceptions.WrongReceiptKindException;

public class Taxpayer {
  protected final String fullname;
  protected final int taxRegistrationNumber;
  protected final float income;
  protected ArrayList<Integer> incomeTiers;
  protected ArrayList<Double> taxModifiers;
  protected ArrayList<Double> flatFees;
  protected ArrayList<Integer> flatReductions;
  private final float[] amountPerReceiptsKind = new float[5];
  private int totalReceiptsGathered = 0;
  private final HashMap<Integer, Receipt> receiptHashMap = new HashMap<Integer, Receipt>(0);
  private final HashMap<String, Short> receiptToId = new HashMap<>(5);
  private static final short ENTERTAINMENT = 0;
  private static final short BASIC = 1;
  private static final short TRAVEL = 2;
  private static final short HEALTH = 3;
  private static final short OTHER = 4;

  protected Taxpayer(String fullname, int taxRegistrationNumber, float income) {
    this.fullname = fullname;
    this.taxRegistrationNumber = taxRegistrationNumber;
    this.income = income;

    this.initReceiptToId();
  }

  public double calculateBasicTax() {
    int index = -1;
    for (Integer e : this.incomeTiers) {
      if (this.income < e) {
        index = this.incomeTiers.indexOf(e);
        break;
      }
    }

    double modifier   = this.taxModifiers.get(index);
    double flatFee    = this.flatFees.get(index);
    int flatReduction = this.flatReductions.get(index);

    return flatFee + modifier * (this.income - flatReduction);
  }

  private void initReceiptToId() {
    this.receiptToId.put("Entertainment", ENTERTAINMENT);
    this.receiptToId.put("Basic", BASIC);
    this.receiptToId.put("Travel", TRAVEL);
    this.receiptToId.put("Health", HEALTH);
    this.receiptToId.put("Other", OTHER);
  }

  public void addReceipt(Receipt receipt) throws WrongReceiptKindException {
    try {
      short type = this.receiptToId.get(receipt.getKind());
      amountPerReceiptsKind[type] += receipt.getAmount();
    } catch (Exception e) {
      throw new WrongReceiptKindException();
    }
    receiptHashMap.put(receipt.getId(), receipt);
    totalReceiptsGathered++;
  }

  public void removeReceipt(int receiptId) throws WrongReceiptKindException {
    Receipt receipt = receiptHashMap.get(receiptId);
    try {
      short type = this.receiptToId.get(receipt.getKind());
      amountPerReceiptsKind[type] -= receipt.getAmount();
    } catch (Exception e) {
      throw new WrongReceiptKindException();
    }
    totalReceiptsGathered--;
    receiptHashMap.remove(receiptId);
  }

  public String getFullname() {
    return fullname;
  }

  public int getTaxRegistrationNumber() {
    return taxRegistrationNumber;
  }

  public float getIncome() {
    return income;
  }

  public HashMap<Integer, Receipt> getReceiptHashMap() {
    return receiptHashMap;
  }

  public double getVariationTaxOnReceipts() {
    float totalAmountOfReceipts = getTotalAmountOfReceipts();
    double basicTax = calculateBasicTax();
    ArrayList<Float> incomeTiers = new ArrayList<>(Arrays.asList(
            0.2f * income, 0.4f * income, 0.6f * income, income));
    ArrayList<Float> modifiers = new ArrayList<>(Arrays.asList(
            0.08f, 0.04f, -0.15f, -0.3f));
    double ret = 0.0;

    for (Float tier : incomeTiers) {
      if (totalAmountOfReceipts < tier)
        ret = basicTax * modifiers.get(incomeTiers.indexOf(tier));
        break;
    }
    return ret;
  }

  private float getTotalAmountOfReceipts() {
    int sum = 0;
    for (int i = 0; i < 5; i++) {
      sum += amountPerReceiptsKind[i];
    }
    return sum;
  }

  public int getTotalReceiptsGathered() {
    return totalReceiptsGathered;
  }

  public float getAmountOfReceiptKind(short kind) {
    return amountPerReceiptsKind[kind];
  }

  public double getTotalTax() {
    return calculateBasicTax() + getVariationTaxOnReceipts();
  }

  public double getBasicTax() {
    return calculateBasicTax();
  }
}