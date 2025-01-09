/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controlador;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import sistema.Main;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import dao.Conexion;
import dto.ClienteDTO;
import javafx.scene.control.ButtonType;
import modelo.Cliente;
import utilidades.SuperKinalAlerts;

/**
 * FXML Controller class
 *
 * @author Estuardo
 */
public class FormClienteController implements Initializable {
    private Main escenarioPrincipal;
    private int op;
    
    private static Connection conexion = null;
    private static PreparedStatement statment = null;
    
    
     @FXML
    private TextField tfDireccion;

    @FXML
    private Button btnGuardar;

    @FXML
    private TextField tfNombre;

    @FXML
    private TextField tfTelefono;

    @FXML
    private Button btnCancelar;

    @FXML
    private TextField tfClienteId;

    @FXML
    private TextField tfApellido;
    
    @FXML
    public void handleButtonAction(ActionEvent event) throws SQLException{
        if(event.getSource() == btnCancelar){    
            escenarioPrincipal.ventanaMenuCliente();
            ClienteDTO.getClienteDTO().setCliente(null);
        }else if(event.getSource() == btnGuardar){// la doble funcion del boton guardar
            if(op == 1){
                
                if(!tfNombre.getText().equals("") && !tfApellido.getText().equals("") && !tfTelefono.getText().equals("") &&
                   !tfDireccion.getText().equals("")){
              
                    agregarCliente();       
                    SuperKinalAlerts.getInstance().mostrarAlertaInfo(400);
                    escenarioPrincipal.ventanaMenuCliente();
                }else{
                    SuperKinalAlerts.getInstance().mostrarAlertaInfo(600);
                    tfNombre.requestFocus();       
                }
             
            }else if(op == 2){
                //EDITAR
                if(!tfNombre.getText().equals("") && !tfApellido.getText().equals("") && !tfTelefono.getText().equals("") &&
                   !tfDireccion.getText().equals("")){
                    if(SuperKinalAlerts.getInstance().mostrarAlertaConfirmacion(950).get() == ButtonType.OK){
                        editarCliente();
                        ClienteDTO.getClienteDTO().setCliente(null);// para que no cargue el ultimo cliente que tuve seleccionado al salir
                        SuperKinalAlerts.getInstance().mostrarAlertaInfo(500);
                        escenarioPrincipal.ventanaMenuCliente();
                    }else{
                        escenarioPrincipal.ventanaMenuPrincipal();
                    }
                }else{
                    SuperKinalAlerts.getInstance().mostrarAlertaInfo(600);
                    if(tfNombre.getText().equals("")){
                        tfNombre.requestFocus();
                    }else if(tfApellido.getText().equals("")){
                        tfApellido.requestFocus();
                    }else if(tfTelefono.getText().equals("")){
                        tfTelefono.requestFocus();
                    }else if(tfDireccion.getText().equals("")){
                        tfDireccion.requestFocus();
                    }
                }                      
            }                   
        }
    }
    

    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL url, ResourceBundle rb) {
        // TODO
        if(ClienteDTO.getClienteDTO().getCliente() != null){
            cargarDatos(ClienteDTO.getClienteDTO().getCliente());
            
        }
        
    }
    
    public void cargarDatos(Cliente cliente){
        tfClienteId.setText(Integer.toString(cliente.getIdClientes()));
        tfNombre.setText(cliente.getNombre());
        tfApellido.setText(cliente.getApellido());
        tfTelefono.setText(cliente.getTelefono());
        tfDireccion.setText(cliente.getDireccion());
        
    }
    
    public void agregarCliente(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_agregarCliente(?,?,?,?);";
            // insertando los valores en los ?
            statment = conexion.prepareStatement(sql);
            statment.setString(1, tfNombre.getText());
            statment.setString(2, tfApellido.getText());
            statment.setString(3, tfTelefono.getText());
            statment.setString(4, tfDireccion.getText());
            
            statment.execute();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally{
            try{
                if(statment != null){// quiere decir que se abrio
                    statment.close();
                }
                if(conexion != null){
                    conexion.close();
                }
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
        }//finally
    }
    
    public void  editarCliente(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_editarCliente(?,?,?,?,?);";
            statment = conexion.prepareCall(sql);// preparar el statment, asignar el id, nombre, etc
            statment.setInt(1,Integer.parseInt(tfClienteId.getText()));// 2 parametros, la posicion y el valor
            statment.setString(2, tfNombre.getText());
            statment.setString(3, tfApellido.getText());
            statment.setString(4, tfTelefono.getText());
            statment.setString(5, tfDireccion.getText());
            
            statment.execute();
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally{
            try{
                if(statment != null){
                    statment.close();
                }
                if(conexion != null){
                    conexion.close();
                }
              }catch(SQLException e){
                 System.out.println(e.getMessage());

                  
            }
        }
    }

    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }    

    public Main getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setOp(int op) {
        this.op = op;
    }
    
    
    
}
