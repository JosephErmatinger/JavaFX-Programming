package application;

/*
 * File Name: CookiesFXController.java
 * Programmer: Isaac Oxendale & Joe Ermatinger
 * Date Created: 2019-04-19
 */

import java.util.Date;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;

public class CookiesFXController {
		
    @FXML
    private Label dateOut;

    @FXML
    private TextField cookiesEaten;

    @FXML
    private ImageView cookieTL;

    @FXML
    private ImageView cookieBL;

    @FXML
    private ImageView cookieBR;

    @FXML
    private ImageView cookieTR;

    @FXML
    private TextField cookieName;

    @FXML
    private Button caloriesButton;

    @FXML
    private Label caloriesOut;

    @FXML
    void calculateCalories(ActionEvent event) {
      //constants for use in program maintenance
      final double COOKIES_PER_SERVING = 4.0;
      final double CALORIES_PER_SERVING = 300.0;
      int holdCals = 0;
      try{
      holdCals = 	(int)Math.floor(Integer.parseInt(cookiesEaten.getText())/ COOKIES_PER_SERVING * CALORIES_PER_SERVING + 0.5d);
      }
      catch(Exception e){
        System.exit(0);
      }
      caloriesOut.setText
          (
							"You have eaten " + holdCals +
              " worth of calories in your " +
              cookieName.getText() + " cookies!"
          );
    } 
  
    @FXML
    void initialize(){
    	Date today = new Date();
    	dateOut.setText("It is currently " + today);
    }
}
