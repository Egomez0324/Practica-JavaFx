/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author Estuardo Gomez
 */
public class DetalleFactura {
    private int detalleFacturaId;
    private int id_factura;
    private int id_producto;
    
    public DetalleFactura(){
    }

    public DetalleFactura(int detalleFacturaId, int id_factura, int id_producto) {
        this.detalleFacturaId = detalleFacturaId;
        this.id_factura = id_factura;
        this.id_producto = id_producto;
    }

    public int getDetalleFacturaId() {
        return detalleFacturaId;
    }

    public void setDetalleFacturaId(int detalleFacturaId) {
        this.detalleFacturaId = detalleFacturaId;
    }

    public int getId_factura() {
        return id_factura;
    }

    public void setId_factura(int id_factura) {
        this.id_factura = id_factura;
    }

    public int getId_producto() {
        return id_producto;
    }

    public void setId_producto(int id_producto) {
        this.id_producto = id_producto;
    }
    
    
}
