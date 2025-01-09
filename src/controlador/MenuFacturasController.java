/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import dao.Conexion;
import java.net.URL;
import sistema.Main;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import modelo.DetalleFactura;
import java.sql.SQLException;
import modelo.Cliente;
import modelo.Facturas;

/**
 *
 * @author Estuardo Gomez
 */
public class MenuFacturasController {
    Main escenarioPrincipal;
    
    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    private static ResultSet resultset = null;
    
    @FXML
    private TextField tfNoFactura;
    @FXML
    private TextField tfEmpleado;
    @FXML
    private ComboBox cmbCliente;
    @FXML
    private ComboBox cmbAgregarPdcto;
    @FXML
    private TableView tblFactura;
    @FXML
    private Button btnEliminarPdcto;
    @FXML
    private TextField tfTotal;
    @FXML
    private Button btnCrearFactura;
    @FXML
    private Button btnFinalizarFactura;
    @FXML
    private Button btnVerFacturas, btnRegresar;
    
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnRegresar){
            escenarioPrincipal.ventanaMenuPrincipal();
        }
    }
    
    public void initialize(URL location, ResourceBundle resource){
        cmbCliente.setItems(listarClientes());
        
        
    }
    
    @FXML
    
    
    public int obtenerIndexCliente(){
        int index = 0;
        String clienteTbl =((Facturas) tblFactura.getSelectionModel().getSelectedItem()).getCliente();
        
        for(int i = 0; i<= cmbCliente.getItems().size(); i++){
            String clienteCmb = cmbCliente.getItems().get(i).toString();
            if(clienteTbl.equals(clienteCmb)){
            }
        }
        
        return index;
    }
    
   
    
    public ObservableList<Cliente> listarClientes(){
        ArrayList<Cliente> clientes = new ArrayList<>();       
        try{
            conexion = Conexion.getInstance().obtenerConexion();// se abre la conexion
            String sql = "call sp_listarClientes();";//almacenar la consulta
            statement = conexion.prepareStatement(sql);// se ejecuta, se manda al sql
            resultset = statement.executeQuery();
            
            
            while(resultset.next()){// se almacenan de forma temporal las filas en variables
                int idCliente = resultset.getInt("id_clientes");
                String nombre = resultset.getString("nombre");
                String apellido = resultset.getString("apellido");
                String telefono = resultset.getString("telefono");
                String direccion = resultset.getString("direccion");
                
                clientes.add(new Cliente(idCliente, nombre, apellido, telefono, direccion));
                
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
                  
            
        }finally{
            try{
                if(resultset != null){
                    resultset.close();
                }
                 if(resultset != null){
                     statement.close();
                }
                 if(conexion != null){
                     conexion.close();
                 }
                }catch(SQLException e){
                    System.out.println(e.getMessage());
            }
        }// finally cuando no se usa un metodo (una consulta), estoy gastanto recursos por la conexion abierta
        return FXCollections.observableList(clientes);
     }
    
   /*public ObservableList<DetalleFactura> listarFacturas(){
       ArrayList<DetalleFactura> facturas = new ArrayList<>();
       
       try{
           conexion = Conexion.getInstance().obtenerConexion();
           String sql = "call sp_listarFacturas();";
           statement = conexion.prepareStatement(sql);
           resultset = statement.executeQuery();
           
           while(resultset.next()){
               int detalleFacturaId = resultset.getInt("detalleFacturaId");
               int id_factura = resultset.getInt("id_factura");
               int id_producto = resultset.getInt("id_producto");
               
               facturas.add(new DetalleFactura(detalleFacturaId, id_factura, id_producto));
               
           }
           
       }catch(SQLException e){
           System.out.println(e.getMessage());
       }finally{
           try{
               if(resultset != null){
                   resultset.close();
               }
               if(resultset != null){
                   statement.close();
               }
               if(conexion != null){
                   conexion.close();
               }
           }catch(SQLException e){
               System.out.println(e.getMessage());
           }
       }
       
       return FXCollections.observableList(facturas);
   }*/

    public Main getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    
    
}
