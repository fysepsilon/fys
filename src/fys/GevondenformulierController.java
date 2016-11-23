/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package fys;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;

/**
 * FXML Controller class
 *
 * @author Lucas Lageweg
 */
public class GevondenformulierController implements Initializable {
    @FXML private ComboBox airport_combo, type_combo;
    @FXML private TextField name_input, surname_input, labelnumber_input, 
            flightnumber_input, destination_input, brand_input, color_input, 
            characteristics_input;
    private CheckBox account_checkbox;
    @FXML private Button picture_button;
    @FXML private Label surname_label, name_label, airport_label, label_label, 
            flight_label, destination_label, type_label, brand_label, color_label,
            characteristics_label, picture_label;
    @FXML private Button send_button;
    
    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
    }    
    
}
