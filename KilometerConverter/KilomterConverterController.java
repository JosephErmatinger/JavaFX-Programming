package application;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;

public class KilomterConverterController {

    @FXML
    private TextField kilomterTextField;

    @FXML
    private Label outputLabel;

    @FXML
    private Label promptLabel;
    
    @FXML
    private Button convertButton;
   
    @FXML
    void convertButtonListener(ActionEvent event) 
    {
    	final double CONVERSION_FACTOR = 0.6214;
    	String str = kilomterTextField.getText();
    	double miles = Double.parseDouble(str) * CONVERSION_FACTOR;
    	outputLabel.setText(str + " kilomters is " +  miles + " miles. ");
    
    }

}