package incometaxcalculator.data.management;

import java.util.ArrayList;
import java.util.Arrays;

public class MarriedFilingJointlyTaxpayer extends Taxpayer {

  public MarriedFilingJointlyTaxpayer(String fullname, int taxRegistrationNumber, float income) {
    super(fullname, taxRegistrationNumber, income);

    incomeTiers    = new ArrayList<>(Arrays.asList(36080, 90000, 143350, 254240, Integer.MAX_VALUE));
    taxModifiers   = new ArrayList<>(Arrays.asList(0.0535, 0.0705, 0.0705, 0.0785, 0.0985));
    flatFees       = new ArrayList<>(Arrays.asList(0.0, 19030.28, 5731.64, 9492.82, 18197.69));
    flatReductions = new ArrayList<>(Arrays.asList(0, 36080, 90000, 143350, 254240));
  }

  public double calculateBasicTax() {
    return super.calculateBasicTax();
  }
}