/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package reporte;
import dao.Conexion;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import win.zqxu.jrviewer.JRViewerFX;

/**
 *
 * @author Estuardo Gomez
 */
public class GenerarReporte {
    private static GenerarReporte instance;
    
    private static Connection conexion = null;
    
    
    private GenerarReporte(){
    }

    public static GenerarReporte getInstance() {
        if(instance == null){
            instance = new GenerarReporte();
        }
        return instance;
    }
    
    public void generarFactura(int id_factura){//traer el valor de la factura desde afuera
        try{
            // 1. Abrir la conexion a la DB
            conexion = Conexion.getInstance().obtenerConexion();
            
            // 2. Enviar los parametros al reporte por medio de un MAP: especie de matriz con dos direcciones
            Map<String, Object> parametros = new HashMap<>();   // constructor para definir los mapas
            parametros.put("id_factura", id_factura);
            
            //3 Crear un stage nuevo
            Stage reportStage = new Stage();
            
            // 4. Generar el reporte 
            JasperPrint reporte = JasperFillManager.fillReport(GenerarReporte.class.getResourceAsStream
            ("/reporte/Factura.jasper"), parametros, conexion); // con este metodo traigo el archivo
            
            // 5. colocar el reporte en el stage 
            JRViewerFX reportView = new JRViewerFX(reporte);// el reporte es lo que coloca sobre la ventana
            
            // 6. Mostrar el stage
            
            Pane root = new Pane();
            root.getChildren().add(reportView);
            
            reportView.setPrefSize(1000, 800);
            
            Scene scene = new Scene(root);
            reportStage.setScene(scene);
            reportStage.setTitle("factura");
            reportStage.show();
            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    
}
