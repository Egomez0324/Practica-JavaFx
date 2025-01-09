/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilidades;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;

/**
 *
 * @author Estuardo
 */
/**
 * 
 * SINGELTON
 * 1. crear la variable instance
 * 2. constructor privado
 * 3. get instance
 */
public class SuperKinalAlerts {
    private static SuperKinalAlerts instance;
    
    private SuperKinalAlerts(){
    }
    
    public static SuperKinalAlerts getInstance(){
        if(instance == null){
            instance = new SuperKinalAlerts();
        }
        return instance;
    }
    
    public void mostrarAlertaInfo(int code){
        if(code == 400){//codigo 400 sirve para agregacion de registros 
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Confirmacion Registro");
            alert.setHeaderText("Confirmacion Registro");
            alert.setContentText("¡Registro realizado exitosamente!");
            alert.showAndWait();
        }else if(code == 500){// codigo 500 sirvee para edicion de registros
            Alert alert = new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Edicion Registro");
            alert.setHeaderText("Edicion Registro");
            alert.setContentText("¡Edicion realizada exitosamente!");
            alert.showAndWait();
        }else if(code == 600){// codigo 600 sirve para alerta de campos pendientes
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Campos Pendientes");
            alert.setHeaderText("Campos Pendientes");
            alert.setContentText("¡Faltan campos por rellenar!");
            alert.showAndWait();
        }  
            
    }
    
    public Optional<ButtonType> mostrarAlertaConfirmacion(int code){
        Optional<ButtonType> action =  null;
        
        if(code == 800){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Eliminar Registro");
            alert.setHeaderText("Eliminar Registro");
            alert.setContentText("¿Deseas eliminar este registro?");
            action = alert.showAndWait();
            
        }else if(code == 950){
            Alert alert = new Alert(Alert.AlertType.CONFIRMATION);
            alert.setTitle("Editar Registro");
            alert.setHeaderText("Editar Registro");
            alert.setContentText("¿Deseas editar este registro?");
            action = alert.showAndWait();
            
        }
        
        return action;
    }
    
    
    
}
