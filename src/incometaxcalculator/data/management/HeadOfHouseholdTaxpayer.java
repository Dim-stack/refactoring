package incometaxcalculator.data.management;

import java.util.ArrayList;
import java.util.Arrays;

public class HeadOfHouseholdTaxpayer extends Taxpayer {

  public HeadOfHouseholdTaxpayer(String fullname, int taxRegistrationNumber, float income) {
    super(fullname, taxRegistrationNumber, income);

    incomeTiers    = new ArrayList<>(Arrays.asList(30390, 90000, 122110, 203390, Integer.MAX_VALUE));
    taxModifiers   = new ArrayList<>(Arrays.asList(0.0535, 0.0705, 0.0705, 0.0785, 0.0985));
    flatFees       = new ArrayList<>(Arrays.asList(0.0, 1625.87, 5828.38, 8092.13, 14472.61));
    flatReductions = new ArrayList<>(Arrays.asList(0, 30390, 90000, 122110, 203390));
  }

  public double calculateBasicTax() {
    return super.calculateBasicTax();
  }
}
