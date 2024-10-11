package incometaxcalculator.data.management;

import incometaxcalculator.exceptions.WrongTaxpayerStatusException;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class TaxpayerFactory {
  private static final HashMap<Object, Class<?>> types = new HashMap<Object, Class<?>>() {{
                    put("Married Filing Jointly", MarriedFilingJointlyTaxpayer.class);
                    put("Married Filing Separately", MarriedFilingSeparatelyTaxpayer.class);
                    put("Single", SingleTaxpayer.class);
                    put("Head of Household", HeadOfHouseholdTaxpayer.class); }};

  public static Taxpayer createTaxpayer(String fullname, int taxRegistrationNumber, String status, float income)
          throws WrongTaxpayerStatusException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
    if (types.containsKey(status)) {
      Class<?> c = types.get(status);
      Constructor<?> constructor = c.getConstructor(String.class, int.class, float.class);
      return (Taxpayer) constructor.newInstance(fullname, taxRegistrationNumber, income);
    } else {
      throw new WrongTaxpayerStatusException();
    }
  }
}
