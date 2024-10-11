package incometaxcalculator.gui;

import incometaxcalculator.data.management.TaxpayerManager;
import incometaxcalculator.exceptions.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.io.File;
import java.io.IOException;

public class GraphicalInterface extends JFrame {

  private final TaxpayerManager taxpayerManager = new TaxpayerManager();

  public GraphicalInterface() {
    setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    setBounds(400, 400, 450, 500);

    JPanel contentPane = new JPanel();
    contentPane.setBackground(new Color(204, 204, 204));
    contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
    contentPane.setLayout(null);
    setContentPane(contentPane);

    try {
      UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
    } catch (ClassNotFoundException | InstantiationException | IllegalAccessException
             | UnsupportedLookAndFeelException e2) {
      e2.printStackTrace();
    }

    DefaultListModel<String> taxRegisterNumberModel = new DefaultListModel<>();

    // ##### LOADED TAXPAYER DRAWBOX #####
    JList<String> taxRegisterNumberList = new JList<>(taxRegisterNumberModel);
    taxRegisterNumberList.setBackground(new Color(153, 204, 204));
    taxRegisterNumberList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
    taxRegisterNumberList.setSelectedIndex(0);
    taxRegisterNumberList.setVisibleRowCount(3);

    JScrollPane taxRegisterNumberListScrollPane = new JScrollPane(taxRegisterNumberList);
    taxRegisterNumberListScrollPane.setSize(300, 300);
    taxRegisterNumberListScrollPane.setLocation(70, 180);
    contentPane.add(taxRegisterNumberListScrollPane);

    // ##### TRN TEXT PANEL #####
    JTextField txtTaxRegistrationNumber = new JTextField();
    txtTaxRegistrationNumber.setEditable(false);
    txtTaxRegistrationNumber.setBackground(new Color(153, 204, 204));
    txtTaxRegistrationNumber.setFont(new Font("Tahoma", Font.BOLD, 14));
    txtTaxRegistrationNumber.setText("Tax Registration Number:");
    txtTaxRegistrationNumber.setBounds(70, 159, 300, 20);
    contentPane.add(txtTaxRegistrationNumber);
    txtTaxRegistrationNumber.setColumns(10);

    // ##### BUTTONS #####
    JButton loadTaxpayerButton = new JButton("Load Taxpayer");
    JButton selectTaxpayerButton = new JButton("Select Taxpayer");
    JButton deleteTaxpayerButton = new JButton("Delete Taxpayer");

    // ##### LOAD TAXPAYER BUTTON #####
    loadTaxpayerButton.addActionListener(e -> {
      JFileChooser fileChooser = new JFileChooser();
      int answer = fileChooser.showOpenDialog(null);

      if (answer == JFileChooser.APPROVE_OPTION) {
        File taxFile = fileChooser.getSelectedFile();
        String filename = taxFile.getName();

        // Support files have a "123456789_INFO.(txt/xml)" format.
        if (checkFile(filename)) {
          String taxRegistrationNumber = filename.substring(0, 9);
          try {
            int trn = Integer.parseInt(taxRegistrationNumber);
            if (taxpayerManager.containsTaxpayer(trn)) {
              JOptionPane.showMessageDialog(null, "This taxpayer is already loaded.");
            } else {
              taxpayerManager.loadTaxpayer(filename);
              taxRegisterNumberModel.addElement(taxRegistrationNumber);
            }
          } catch (NumberFormatException e1) {
            JOptionPane.showMessageDialog(null,
                    "The tax registration number must have only digits.");

          // The following three exceptions never throw based on the new design, but we keep them to keep
          // the compiler happy.
          } catch (IOException e1) {
            JOptionPane.showMessageDialog(null, "The file doesn't exists.");
          } catch (WrongFileFormatException e1) {
            JOptionPane.showMessageDialog(null, "Please check your file format and try again.");
          } catch (WrongTaxpayerStatusException e1) {
            JOptionPane.showMessageDialog(null, "Please check taxpayer's status and try again.");
          } catch (WrongReceiptKindException e1) {
            JOptionPane.showMessageDialog(null, "Please check receipts kind and try again.");
          } catch (WrongReceiptDateException e1) {
            JOptionPane.showMessageDialog(null,
                    "Please make sure your date is " + "DD/MM/YYYY and try again.");
          }
        } else {
          JOptionPane.showMessageDialog(null, "Unsupported file provided.\n" + " Try again.");
        }

      }
    });

    // ##### SELECT TAXPAYER BUTTON #####
    selectTaxpayerButton.addActionListener(e -> {
      if (taxpayerManager.containsTaxpayer()) {
        String currentlySelected = taxRegisterNumberList.getSelectedValue();
        TaxpayerData taxpayerData = new TaxpayerData(Integer.parseInt(currentlySelected),
                taxpayerManager);
        taxpayerData.setVisible(true);
      } else {
        JOptionPane.showMessageDialog(null,
                "Currently, there aren't any taxpayers loaded.\nPlease load one first and try again.");
      }
    });

    // DELETE TAXPAYER BUTTON
    deleteTaxpayerButton.addActionListener(arg0 -> {
      if (taxpayerManager.containsTaxpayer()) {
        String currentlySelected = taxRegisterNumberList.getSelectedValue();
        taxpayerManager.removeTaxpayer(Integer.parseInt(currentlySelected));
        taxRegisterNumberModel.removeElement(currentlySelected);
      } else {
        JOptionPane.showMessageDialog(null,
                "There isn't any taxpayer loaded. Please load one first.");
      }
    });


    loadTaxpayerButton.setBounds(95, 10, 250, 35);
    selectTaxpayerButton.setBounds(95, 60, 250, 35);
    deleteTaxpayerButton.setBounds(95, 110, 250, 35);

    contentPane.add(loadTaxpayerButton);
    contentPane.add(selectTaxpayerButton);
    contentPane.add(deleteTaxpayerButton);
  }

  public static void main(String[] args) {
    EventQueue.invokeLater(() -> {
      try {
        GraphicalInterface frame = new GraphicalInterface();
        frame.setVisible(true);
      } catch (Exception e) {
        e.printStackTrace();
      }
    });
  }

  private boolean checkFile(String filename) {
    if (filename.length() == 18) {
      if (filename.charAt(9) == '_') {
        return filename.startsWith("INFO", 10) &&
                (filename.substring(15).equals("txt") ||
                        filename.substring(15).equals("xml"));
      }
    }
    return false;
  }
}