/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

package controlador;

import dao.Conexion;
import dto.ClienteDTO;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.ResourceBundle;
import java.util.Set;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import modelo.Cliente;
import sistema.Main;



public class MenuClientesController implements Initializable{
    private Main stage;
    private Main escenarioPrincipal;
    private int op;
    
    private static Connection conexion = null; // abrir una conexion en una consulta
    private static PreparedStatement statment = null; // almacena la consulta : call sp
    private static ResultSet resultSet =  null; // resultado de la consulta
    @FXML
    private TableColumn colApellido;
    @FXML
    private TableColumn colDireccion;
    @FXML
    private TableColumn colNombre;
    @FXML
    private TableView tblClientes;
    @FXML
    private TableColumn colidCliente;
    @FXML
    private TableColumn colTelefono;
    @FXML
    private Button btnRegresar, btnAgregar, btnEditar, btnEliminar, btnBuscar;
    @FXML
    private TextField tfBuscarClienteId;
    
    @Override
    public void initialize(URL location, ResourceBundle resources){// se cargan los datos. initialize carga la escena, es el primer metodo  
        cargarLista();    
    }
    
    @FXML
     public void handleButtonAction(ActionEvent event){
       if(event.getSource() == btnRegresar){
          escenarioPrincipal.ventanaMenuPrincipal();
            
        }else if(event.getSource() == btnAgregar){
            escenarioPrincipal.formCliente(1);// se envia el numero al formcliente con la variable OP
        }else if(event.getSource() == btnEditar){
            ClienteDTO.getClienteDTO().setCliente((Cliente)tblClientes.getSelectionModel().getSelectedItem());
            escenarioPrincipal.formCliente(2);
        }else if(event.getSource() == btnEliminar){
            eliminarCliente(((Cliente)tblClientes.getSelectionModel().getSelectedItem()).getIdClientes());
            cargarLista();    
            
        }else if(event.getSource() == btnBuscar){
            tblClientes.getItems().clear();
            
            if(tfBuscarClienteId .getText().equals("")){// comparar strings.equals y si 2 numeros son iguales se usa ==
                cargarLista();
            }else{
                op = 3;
                cargarLista();
                
            }
            
        }
    }
    
    public void cargarLista(){
        if(op == 3){
            // llenar la tabla con el cliente buscado
            tblClientes.getItems().add(buscarCliente());
            op = 0;//cada vez que se veulva a cargar la lista no aparezca el ultimo, para que se resetee
        }else{
            tblClientes.setItems(listarClientes());
        }   
        colidCliente.setCellValueFactory(new PropertyValueFactory<Cliente, Integer>("idClientes"));// tener acceso al observableList, sacar los datos
        colNombre.setCellValueFactory(new PropertyValueFactory<Cliente, String>("nombre"));
        colApellido.setCellValueFactory(new PropertyValueFactory<Cliente, String>("apellido"));
        colTelefono.setCellValueFactory(new PropertyValueFactory<Cliente, String>("telefono"));
        colDireccion.setCellValueFactory(new PropertyValueFactory<Cliente, String>("direccion"));
        
        
    }
    
    public ObservableList<Cliente> listarClientes(){
        ArrayList<Cliente> clientes = new ArrayList<>();       
        try{
            conexion = Conexion.getInstance().obtenerConexion();// se abre la conexion
            String sql = "call sp_listarClientes();";//almacenar la consulta
            statment = conexion.prepareStatement(sql);// se ejecuta, se manda al sql
            resultSet = statment.executeQuery();
            
            
            while(resultSet.next()){// se almacenan de forma temporal las filas en variables
                int idCliente = resultSet.getInt("id_clientes");
                String nombre = resultSet.getString("nombre");
                String apellido = resultSet.getString("apellido");
                String telefono = resultSet.getString("telefono");
                String direccion = resultSet.getString("direccion");
                
                clientes.add(new Cliente(idCliente, nombre, apellido, telefono, direccion));
                
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
                  
            
        }finally{
            try{
                if(resultSet != null){
                    resultSet.close();
                }
                 if(statment != null){
                     statment.close();
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
    
    public void eliminarCliente(int cliId){
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_eliminarClientes(?)";
            statment = conexion.prepareStatement(sql);
            statment.setInt(1, cliId);
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
    
    public Cliente buscarCliente(){
        Cliente cliente = null;
        try{
            conexion = Conexion.getInstance().obtenerConexion();
            String sql = "call sp_buscarClientes(?);";
            statment = conexion.prepareStatement(sql);
            statment.setInt(1, Integer.parseInt (tfBuscarClienteId.getText()));
            resultSet = statment.executeQuery(); // para recorrer un resulset se usa if
            
            if(resultSet.next()){// si todavia tiene datos almacenados
                int id_clientes = resultSet.getInt("id_clientes");
                String nombre = resultSet.getString("nombre");
                String apellido = resultSet.getString("apellido");
                String telefono = resultSet.getString("telefono");
                String direccion = resultSet.getString("direccion");
                
                cliente = new Cliente(id_clientes, nombre, apellido, telefono, direccion);  
                       
            }
        }catch(SQLException e){
            System.out.println(e.getMessage());
        }finally{
            try{
                if(resultSet != null){
                    resultSet.close();
                }
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
        
        return cliente;
            
    }
    
   

    public Main getStage() {
        return stage;
    }

    public void setStage(Main stage) {
        this.stage = stage;
    }
    
    public void setEscenarioPrincipal(Main escenarioPrincipal) {
        this.escenarioPrincipal = escenarioPrincipal;
    }
    
    
    
    
    
}
