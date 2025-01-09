/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/javafx/FXMLController.java to edit this template
 */
package controlador;

import dao.Conexion;
import java.net.URL;
import java.util.ArrayList;
import java.util.ResourceBundle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import sistema.Main;
import modelo.TicketSoporte;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.Cliente;
import controlador.MenuClientesController;
import javax.swing.JOptionPane;

/**
 * FXML Controller class
 *
 * @author Estuardo
 */
public class MenuTicketSoporteController implements Initializable {
    Main escenarioPrincipal;
    
    private static Connection conexion = null;
    private static PreparedStatement statement = null;
    private static ResultSet resultset = null;
                       
    
    @FXML
    private ComboBox cmbFactura;
    @FXML
    private ComboBox cmbCliente;
    @FXML
    private TextField tfTicketId;
    @FXML
    private TextArea taDescripcion;
    @FXML
    private ComboBox cmbEstatus;
    @FXML
    private TableColumn colCliente;
    @FXML
    private Button btnVaciar;
    @FXML
    private TableColumn colFacturaId;
    @FXML
    private TableColumn colDescripcion;
    @FXML
    private Button btnRegresar;
    @FXML
    private Button btnGuardar;
    @FXML
    private TableColumn colEstatus;
    @FXML
    private TableColumn colTicketId;
    @FXML
    private TableView tblTickets;
    
    public void handleButtonAction(ActionEvent event){
        if(event.getSource() == btnRegresar){
            escenarioPrincipal.ventanaMenuPrincipal();
        }else if(event.getSource() == btnGuardar){
            
            if(tfTicketId.getText().equals("")){
                agregarTickets();
                vaciarForm();
                cargarDatos();
                JOptionPane.showMessageDialog(null, "La informacion ha sido guardada exitosamente");
            }else{
                editarTickets();
                cargarDatos();
                vaciarForm();
                JOptionPane.showMessageDialog(null, "La informacion ha sido modificada exitosamente");
            }                 
        }     
    }


    /**
     * Initializes the controller class.
     */
    @Override
    public void initialize(URL location, ResourceBundle resoruces) {
        cargarDatos();
        cambiarCmbEstatus();
        cambiarCmbFactura();
        cmbCliente.setItems(listarClientes());
    } 
    
    public void cargarDatos(){
        tblTickets.setItems(listarTickets());
        colTicketId.setCellValueFactory(new PropertyValueFactory<TicketSoporte, Integer>("ticketSoporteId"));
        colDescripcion.setCellValueFactory(new PropertyValueFactory<TicketSoporte, String>("descripcion") );
        colEstatus.setCellValueFactory(new PropertyValueFactory<TicketSoporte, String>("estatus") );
        colCliente.setCellValueFactory(new PropertyValueFactory<TicketSoporte, String>("cliente") );
        colFacturaId.setCellValueFactory(new PropertyValueFactory<TicketSoporte, Integer>("facturaId") );
    }
    
    public void cambiarCmbEstatus(){
        cmbEstatus.getItems().add("En proceso..");
        cmbEstatus.getItems().add("Finalizado");
    }
    
     public void cambiarCmbFactura(){ // provicional
        cmbFactura.getItems().add("1");     
    }
     
    public void vaciarForm(){
        tfTicketId.clear();
        taDescripcion.clear();
        cmbEstatus.getSelectionModel().clearSelection();
        cmbCliente.getSelectionModel().clearSelection();
        cmbFactura.getSelectionModel().clearSelection();
    }
    
    @FXML
    public void cargarForm(){
        TicketSoporte ts = (TicketSoporte)tblTickets.getSelectionModel().getSelectedItem();
        if(ts != null){// verificar si esta lleno
            tfTicketId.setText(Integer.toString(ts.getTicketSoporteId()));// los tf necesitan un string para funcionar por eso se parsea
            taDescripcion.setText(ts.getDescripcion());
            cmbEstatus.getSelectionModel().select(0); // select recibe un dato de tipo entero (la posicion)
            cmbCliente.getSelectionModel().select(obtenerIndexCliente());
            cmbFactura.getSelectionModel().select(0);
            
        }
    }
    
    public int obtenerIndexCliente(){// devuelve la poscion del cmb que ocupa el cliente 
        int index = 0;
        String clienteTbl = ((TicketSoporte)tblTickets.getSelectionModel().getSelectedItem()).getCliente();
        // comparar si el valor del cmb es igual al valor de la lista, busca la posicion del cleinte que ocupa para seleccionarlo
        for(int i = 0; i<= cmbCliente.getItems().size(); i++){
            String clienteCmb = cmbCliente.getItems().get(i).toString(); //almacena los valores del ComboVox
            if(clienteTbl.equals(clienteCmb)){// comparar si el seleccionado corresponde a algun item del cmb
                index = i;
                break;
            }
            
        }
        
        return index;
    }
    
    public ObservableList<TicketSoporte> listarTickets(){
        ArrayList<TicketSoporte> tickets = new ArrayList<>();
        
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_listarTicketSoporte();";
            statement = conexion.prepareStatement(sql);
            resultset = statement.executeQuery();
            
            
            while(resultset.next()){
                int ticketId = resultset.getInt("id_ticketSoporte");
                String descripcion = resultset.getString("descripcionTicket");
                String estatus = resultset.getString("estatus");
                String cliente = resultset.getString("cliente");
                int idFactura = resultset.getInt("id_factura");
                
                tickets.add(new TicketSoporte(ticketId, descripcion, estatus, cliente,idFactura ));

                
            }
            
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally{
            try{
                if(resultset != null){
                resultset.close();
                }
                if(statement != null){
                    statement.close();
                }
                if(conexion != null){
                    conexion.close();
                }
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
        }
        
        return FXCollections.observableList(tickets);
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
    
    public void agregarTickets(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_agregarTicketSoporte(?,?,?);";
            statement = conexion.prepareStatement(sql);
            statement.setString(1, taDescripcion.getText());
            statement.setInt(2, ((Cliente)cmbCliente.getSelectionModel().getSelectedItem()).getIdClientes());// puede usar los get y set del objeto cliente
            statement.setInt(3, Integer.parseInt(cmbFactura.getSelectionModel().getSelectedItem().toString()));
            statement.execute();
            
            
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally{
            try{
                if(statement != null){
                    statement.close();
                }if(conexion != null){
                    conexion.close();
                }
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
        }
    }
    
    public void editarTickets(){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_editarTicketSoporte(?,?,?,?,?);";
            statement = conexion.prepareStatement(sql);
            statement.setInt(1, Integer.parseInt(tfTicketId.getText()));
            statement.setString(2, taDescripcion.getText());
            statement.setString(3,(cmbEstatus).getSelectionModel().getSelectedItem().toString());
            statement.setInt(4,((Cliente)cmbCliente.getSelectionModel().getSelectedItem()).getIdClientes());
            statement.setInt(5, Integer.parseInt(cmbFactura.getSelectionModel().getSelectedItem().toString()));
            statement.execute();
           
            
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally{
            try{
                if(statement != null){
                    statement.close();
                }if(conexion != null){
                    conexion.close();
                }
            }catch(SQLException e){
                System.out.println(e.getMessage());
            }
        }
    }

    public Main getEscenarioPrincipal() {
        return escenarioPrincipal;
    }

    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    
    
}
