package incometaxcalculator.data.management;

import java.util.ArrayList;
import java.util.Arrays;

public class MarriedFilingSeparatelyTaxpayer extends Taxpayer {

  public MarriedFilingSeparatelyTaxpayer(String fullname, int taxRegistrationNumber, float income) {
    super(fullname, taxRegistrationNumber, income);

    incomeTiers    = new ArrayList<>(Arrays.asList(18040, 71680, 90000, 127120, Integer.MAX_VALUE));
    taxModifiers   = new ArrayList<>(Arrays.asList(0.0535, 0.0705, 0.0785, 0.0785, 0.0985));
    flatFees       = new ArrayList<>(Arrays.asList(0.0, 965.14, 4746.76, 6184.88, 9098.80));
    flatReductions = new ArrayList<>(Arrays.asList(0, 18040, 71680, 90000, 127120));
  }

  public double calculateBasicTax() {
    return super.calculateBasicTax();
  }

}