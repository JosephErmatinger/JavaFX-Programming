package application;

/*
 * File Name: JoeAutoController.java
 * Programmer: Isaac Oxendale & Joe Ermatinger
 * Date Created: 2019-04-19
 */

import java.util.Date;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class JoeAutoController {

    @FXML
    private Label dateOut;

    @FXML
    private CheckBox oilChange;

    @FXML
    private CheckBox brakePads;

    @FXML
    private CheckBox brakeRotors;

    @FXML
    private CheckBox tireRotation;

    @FXML
    private CheckBox lubeJob;

    @FXML
    private CheckBox alignment;

    @FXML
    private CheckBox radiatorFlush;

    @FXML
    private CheckBox transFlush;

    @FXML
    private CheckBox inspection;

    @FXML
    private CheckBox mufflerReplacement;

    @FXML
    private TextField partsCost;

    @FXML
    private TextField laborCost;

    @FXML
    private Button calcButton;

    @FXML
    private Label costOut;

    @FXML
    void calculateCost(ActionEvent event) {
      
      final double LABORPERHOUR = 60.00;
      
      double amountOwed = 0.00;
      double standardFees = 0.00;
      if (oilChange.isSelected())
      {
        standardFees+=35.00;
      }
      if (brakePads.isSelected())
      {
        standardFees+=70.00;
      }
      if (brakeRotors.isSelected())
      {
        standardFees+=150.00;
      }
      if (tireRotation.isSelected())
      {
        standardFees+=20.00;
      }
      if (lubeJob.isSelected())
      {
        standardFees+=25.00;
      }
      if (alignment.isSelected())
      {
        standardFees+=75.00;
      }
      if (radiatorFlush.isSelected())
      {
        standardFees+=50.00;
      }
      if (transFlush.isSelected())
      {
        standardFees+=120.00;
      }
      if (inspection.isSelected())
      {
        standardFees+=35.00;
      }
      if (mufflerReplacement.isSelected())
      {
        standardFees+=200.00;
      }
  
      try {
      	amountOwed = Double.parseDouble(partsCost.getText())*1.06+LABORPERHOUR*Double.parseDouble(laborCost.getText())+standardFees;
      }
      catch (Exception e) {
        System.exit(0);
      }
				costOut.setText(String.format("You owe Joe $%.2f", amountOwed));
    }
  	
    @FXML
    void clearAll(ActionEvent event) {
		oilChange.setSelected(false);
      	brakePads.setSelected(false);
      	brakeRotors.setSelected(false);
      	tireRotation.setSelected(false);
      	lubeJob.setSelected(false);
      	alignment.setSelected(false);
      	radiatorFlush.setSelected(false);
      	transFlush.setSelected(false);
      	inspection.setSelected(false);
      	mufflerReplacement.setSelected(false);
      	partsCost.clear();
      	laborCost.clear();
      	costOut.setText("");
    }

  	@FXML
    void initialize(){
    	Date today = new Date();
    	dateOut.setText("It is currently " + today);
    }
}
