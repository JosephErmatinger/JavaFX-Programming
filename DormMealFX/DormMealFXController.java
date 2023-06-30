/*
 * File Name: DormMealFXController.java
 * Programmer: Isaac Oxendale & Joe Ermatinger
 * Date Created: 2019-04-26
 */
package application;

import java.util.Date;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;

public class DormMealFXController {

	String CB1Value = "";
	String CB2Value = "";
	int total;
	
    @FXML
    private Label todayDate;

    @FXML
    private ComboBox<String> comboBox1;

    @FXML
    private ComboBox<String> comboBox2;

    @FXML
    private Label costOut;

    @FXML
    void dormSelected(ActionEvent event) {
    	
    	if (!(comboBox1.getSelectionModel().isEmpty()) && !(comboBox2.getSelectionModel().isEmpty()))
    	{
    		CB1Value = new String(comboBox1.getValue());
    		CB2Value = new String(comboBox2.getValue());
    		total += Integer.parseInt(CB1Value.substring(20, 21))*1000;
    		total += Integer.parseInt(CB1Value.substring(22, 25));
       try {total += Integer.parseInt(CB2Value.substring(19,20));}catch(Exception e) {}
    		total += Integer.parseInt(CB2Value.substring(21, 24));
    		costOut.setText(String.format("With your current selection, you will be paying $%,d per semester.", total));
    	}
    }
  
    @FXML
    void mealSelected(ActionEvent event) {
    	if (!(comboBox1.getSelectionModel().isEmpty()) && !(comboBox2.getSelectionModel().isEmpty()))
    	{
    		CB1Value = new String(comboBox1.getValue());
    		CB2Value = new String(comboBox2.getValue());
    		total += Integer.parseInt(CB1Value.substring(20, 21))*1000;
    		total += Integer.parseInt(CB1Value.substring(22, 25));
       try {total += Integer.parseInt(CB2Value.substring(19,20));}catch(Exception e) {}
    		total += Integer.parseInt(CB2Value.substring(21, 24));
    		costOut.setText(String.format("With your current selection, you will be paying $%,d per semester.", total));
    	}
    }
	@FXML
    void initialize(){
	Date today = new Date();
	todayDate.setText("It is currently " + today);
	
    comboBox1.getItems().addAll("Allen Hall:        $1,800 per semester", 
    							"Pike Hall:         $2,200 per semester", 
    							"Farthing Hall:     $2,800 per semester", 
    							"University Suites: $3,000 per semester");
    comboBox2.getItems().addAll("7 meals per week: $  600 per semester", 
    							"14 meals a week:  $1,100 per semester", 
    							"Unlimited meals:  $1,800 per semester");
    }
}
