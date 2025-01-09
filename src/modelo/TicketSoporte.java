/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author Estuardo
 */
public class TicketSoporte {
    private int ticketSoporteId;
    private String descripcion;
    private String estatus;
    private int id_clientes;
    private String cliente;
    private int facturaId;
    
    public TicketSoporte(){
    }

    public TicketSoporte(int ticketSoporteId, String descripcion, String estatus, String cliente, int facturaId) {
        this.ticketSoporteId = ticketSoporteId;
        this.descripcion = descripcion;
        this.estatus = estatus;
        this.cliente = cliente;
        this.facturaId = facturaId;
    }

    public int getTicketSoporteId() {
        return ticketSoporteId;
    }

    public void setTicketSoporteId(int ticketSoporteId) {
        this.ticketSoporteId = ticketSoporteId;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEstatus() {
        return estatus;
    }

    public void setEstatus(String estatus) {
        this.estatus = estatus;
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

    public int getFacturaId() {
        return facturaId;
    }

    public void setFacturaId(int facturaId) {
        this.facturaId = facturaId;
    }

    @Override
    public String toString() {
        return "TicketSoporte{" + "ticketSoporteId=" + ticketSoporteId + ", descripcion=" + descripcion + ", estatus=" + estatus + ", id_clientes=" + id_clientes + ", cliente=" + cliente + ", facturaId=" + facturaId + '}';
    }
    
    
            
            
    
}
