/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package sistema;

import controlador.FormClienteController;
import controlador.MenuClientesController;
import controlador.MenuFacturasController;
import controlador.MenuPrincipalController;
import controlador.MenuTicketSoporteController;
import java.io.InputStream;
import java.util.Set;
import javafx.application.Application; 
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.fxml.JavaFXBuilderFactory;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

/**
 *
 * @author Estuardo
 */
public class Main extends Application {
    
    private Stage escenarioPrincipal;
    private final String PAQUETE_VISTA = "/vista/";
    
    @Override
    public void start(Stage escenarioPrincipal){      
        this.escenarioPrincipal = escenarioPrincipal;
        ventanaMenuPrincipal();
        this.escenarioPrincipal.show();
        
       
    }
    
    public void ventanaMenuPrincipal(){
        try{
            MenuPrincipalController menuPrincipal = (MenuPrincipalController) cambiarEscena("MenuPrincipalView.fxml", 890, 700);// conversion de objetos
            menuPrincipal.setEscenarioPrincipal(this);
            this.escenarioPrincipal.setTitle("Menu Principal");
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    
    public void ventanaMenuCliente(){
        try{
           MenuClientesController menuClientes = (MenuClientesController)cambiarEscena("MenuClientesView.fxml",1200, 750);
           menuClientes.setEscenarioPrincipal(this);
           this.escenarioPrincipal.setTitle("Menu Clientes");
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void formCliente(int op){
        try{
            FormClienteController formClienteView = (FormClienteController) cambiarEscena("FormClienteView.fxml", 500,750);
            formClienteView.setOp(op);
            
            formClienteView.setEscenarioPrincipal(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    
    public void ventanaMenuTicket(){
        try{
            MenuTicketSoporteController ventanaMenuTicket = (MenuTicketSoporteController) cambiarEscena("MenuTicketSoporteView.fxml", 1200, 750);
            ventanaMenuTicket.setEscenarioPrincipal(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    public void ventanaMenuFacturas(){
        try{
            MenuFacturasController ventanaMenuFacturas = (MenuFacturasController)cambiarEscena("MenuFacturasView.fxml", 1200, 750);
            ventanaMenuFacturas.setEscenarioPrincipal(this);
        }catch(Exception e){
            System.out.println(e.getMessage());
        }
    }
    
    
   
    
     public Initializable cambiarEscena(String fxml, int ancho, int alto) throws Exception {
        Initializable resultado = null;
        FXMLLoader cargador = new FXMLLoader();
        InputStream archivo = Main.class.getResourceAsStream(PAQUETE_VISTA + fxml);
        //Crea un objeto de tipo JavaFx para poder ser Utilizada
        cargador.setBuilderFactory(new JavaFXBuilderFactory());
        //Creando el objeto para que pueda obtener el controlador
        cargador.setLocation(Main.class.getResource(PAQUETE_VISTA + fxml));
        Scene escena = new Scene((AnchorPane) cargador.load(archivo), ancho, alto);
        escenarioPrincipal.setTitle("SuperKinal");
        escenarioPrincipal.setScene(escena);
        escenarioPrincipal.setResizable(false);
        escenarioPrincipal.sizeToScene();
        resultado = (Initializable) cargador.getController();
        return resultado;
    }
    
     
       /**
     * 
     * @param args 
     */
    
    public static void main(String [] args){
        launch(args);
    }
    
    public Stage getEscenarioPrincipal(){
        return escenarioPrincipal;
    }
    
    
}
