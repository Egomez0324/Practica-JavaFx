/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import javafx.scene.control.MenuItem;
import javafx.event.ActionEvent;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import sistema.Main;

/**
 *
 * @author Estuardo
 */
public class MenuPrincipalController implements Initializable {
    private Main escenarioPrincipal;
    
    @FXML
     private MenuItem btnMenuClientes;
    @FXML
    private MenuItem btnTicketSoporte;
    @FXML
    private MenuItem btnFacturas;
    
    
    
    @FXML
     public void handleButtonAction(ActionEvent event){
       if(event.getSource() == btnMenuClientes){
            escenarioPrincipal.ventanaMenuCliente();          
        }else if(event.getSource() == btnTicketSoporte){
            escenarioPrincipal.ventanaMenuTicket();
        }else if(event.getSource() == btnFacturas){
            escenarioPrincipal.ventanaMenuFacturas();
        }
    }
    
    
    
    @Override
    public void initialize(URL location, ResourceBundle resources){
    }
    
    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }    
}
