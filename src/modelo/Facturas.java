/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author Estuardo Gomez
 */
public class Facturas {
    private int id_factura;
    private String fecha;
    private String hora;
    private Double total;
    private int id_empleado;
    private int id_clientes;
    private String cliente;
    
    public Facturas(){
    }

    public Facturas(int id_factura, String fecha, String hora, Double total, int id_empleado, int id_clientes, String cliente) {
        this.id_factura = id_factura;
        this.fecha = fecha;
        this.hora = hora;
        this.total = total;
        this.id_empleado = id_empleado;
        this.id_clientes = id_clientes;
        this.cliente = cliente;
    }

    public int getId_factura() {
        return id_factura;
    }

    public void setId_factura(int id_factura) {
        this.id_factura = id_factura;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public Double getTotal() {
        return total;
    }

    public void setTotal(Double total) {
        this.total = total;
    }

    public int getId_empleado() {
        return id_empleado;
    }

    public void setId_empleado(int id_empleado) {
        this.id_empleado = id_empleado;
    }

    public int getId_clientes() {
        return id_clientes;
    }

    public void setId_clientes(int id_clientes) {
        this.id_clientes = id_clientes;
    }

    public String getCliente() {
        return cliente;
    }

    public void setCliente(String cliente) {
        this.cliente = cliente;
    }
    
    
    
    
}
