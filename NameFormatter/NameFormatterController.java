package application;

/*
 * File Name: NameFormatterController.java
 * Programmer: Isaac Oxendale & Joe Ermatinger
 * Date Created: 2019-04-19
 */

import java.util.Date;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;

public class NameFormatterController {

    @FXML
    private Label todayDate;

    @FXML
    private TextField firstName;

    @FXML
    private TextField middleName;

    @FXML
    private TextField lastName;

    @FXML
    private RadioButton radio1;

    @FXML
    private ToggleGroup FMLT;

    @FXML
    private RadioButton radio2;

    @FXML
    private RadioButton radio3;

    @FXML
    private RadioButton radio4;

    @FXML
    private RadioButton radio5;

    @FXML
    private RadioButton radio6;

    @FXML
    private TextField fmtdNamOut;

    @FXML
    private RadioButton RB1;

    @FXML
    private ToggleGroup PrefTitle;

    @FXML
    private RadioButton RB2;

    @FXML
    private RadioButton RB3;

    @FXML
    private RadioButton RB4;

    @FXML
    private RadioButton RB5;

    @FXML
    private Label prefTitleOut;

    @FXML
    void displayDr(ActionEvent event) {
			prefTitleOut.setText("Dr.");
    }

    @FXML
    void displayHon(ActionEvent event) {
			prefTitleOut.setText("Hon.");
    }

    @FXML
    void displayMr(ActionEvent event) {
			prefTitleOut.setText("Mr.");
    }

    @FXML
    void displayMrs(ActionEvent event) {
			prefTitleOut.setText("Mrs.");
    }

    @FXML
    void displayMs(ActionEvent event) {
			prefTitleOut.setText("Ms.");
    }

    @FXML
    void formatFL(ActionEvent event) {
			fmtdNamOut.setText
          (
    		firstName.getText() + " " + 
			lastName.getText()

          ); 
          
    }

    @FXML
    void formatFML(ActionEvent event) {
			fmtdNamOut.setText
          (
    		firstName.getText() + " " + 
			middleName.getText() + " " + 
			lastName.getText()
          ); 
          
    }

    @FXML
    void formatLF(ActionEvent event) {

			fmtdNamOut.setText
          (
    		lastName.getText() + ", " + 
			firstName.getText()
          ); 
          
    }

    @FXML
    void formatLFM(ActionEvent event) {

			fmtdNamOut.setText
          (
    		lastName.getText() + ", " + 
    		firstName.getText() + " " + 
			middleName.getText()
          ); 
          
    }

    @FXML
    void formatLFMT(ActionEvent event) {

			fmtdNamOut.setText
          (
        	 lastName.getText() + ", " + 
             firstName.getText() + " " + 
             middleName.getText() + ", " + 
             prefTitleOut.getText()
          ); 
          
    }

    @FXML
    void formatTFML(ActionEvent event) {

			fmtdNamOut.setText
          (
    		prefTitleOut.getText() + " " + 
			firstName.getText() + " " + 
			middleName.getText() + " " + 
			lastName.getText()         
          ); 
    }  
    
    @FXML
    void initialize(){
    	Date today = new Date();
    	todayDate.setText("It is currently " + today);
        
    }

}
