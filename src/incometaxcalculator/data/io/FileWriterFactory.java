package incometaxcalculator.data.io;

import incometaxcalculator.exceptions.WrongFileFormatException;

import java.io.File;
import java.io.IOException;

public class FileWriterFactory {
    public static FileWriter createFileWriter(String fileFormat) throws WrongFileFormatException {
        if (fileFormat.equals("txt")) {
            return new TXTLogWriter();
        } else if (fileFormat.equals("xml")) {
            return new XMLLogWriter();
        } else {
            throw new WrongFileFormatException();
        }
    }

    public static void generateFile(int taxRegistrationNumber) throws IOException {
        if (new File(taxRegistrationNumber + "_INFO.xml").exists()) {
            new XMLInfoWriter().generateFile(taxRegistrationNumber);
        }

        if (new File(taxRegistrationNumber + "_INFO.txt").exists()) {
            new TXTInfoWriter().generateFile(taxRegistrationNumber);
        }
    }
}
