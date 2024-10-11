package incometaxcalculator.data.management;

import java.util.ArrayList;
import java.util.Arrays;

public class SingleTaxpayer extends Taxpayer {

  public SingleTaxpayer(String fullname, int taxRegistrationNumber, float income) {
    super(fullname, taxRegistrationNumber, income);

    incomeTiers    = new ArrayList<>(Arrays.asList(24680, 81080, 90000, 152540, Integer.MAX_VALUE));
    taxModifiers   = new ArrayList<>(Arrays.asList(0.0535, 0.0705, 0.0785, 0.0785, 0.0985));
    flatFees       = new ArrayList<>(Arrays.asList(0.0, 1320.38, 5296.58, 5996.80, 10906.19));
    flatReductions = new ArrayList<>(Arrays.asList(0, 24680, 81080, 90000, 152540));
  }

  public double calculateBasicTax() {
    return super.calculateBasicTax();
  }

}