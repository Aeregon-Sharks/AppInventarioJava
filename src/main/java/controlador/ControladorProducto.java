/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.DecimalFormat;
import java.util.List;
import javax.security.auth.callback.ConfirmationCallback;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import modelo.RepositorioProducto;
import modelo.producto;
import org.springframework.boot.autoconfigure.batch.BatchProperties;
import vista.VistaProducto;
import vista.ActualizarMenu;
import vista.InformeMenu;
/**
 *
 * @author j_c-s
 */
public class ControladorProducto implements ActionListener {
    //Declarar
    protected RepositorioProducto repositorio;
    protected VistaProducto vista;
    protected ActualizarMenu actualizarmenu;
    protected InformeMenu informemenu;
    protected long lastcode;
    
    /**
     * Constructor
     * @param repositorio
     * @param vista 
     */
    public ControladorProducto(RepositorioProducto repositorio,VistaProducto vista, ActualizarMenu actualizarmenu, InformeMenu informemenu){
        this.repositorio = repositorio;
        this.vista = vista;
        this.actualizarmenu = actualizarmenu;
        this.informemenu = informemenu;
        this.agregarEventos();
        acterr();
        listar();
    }
    
    // Events
    private void agregarEventos(){
        this.vista.getBtnAgregar().addActionListener(this);
        this.vista.getBtnBorrar().addActionListener(this);
        this.vista.getBtnActualizar().addActionListener(this);
        this.vista.getBtnInforme().addActionListener(this);
        this.vista.getBtnLimpiar().addActionListener(this);
        this.informemenu.getBtnAceptarInforme().addActionListener(this);
        this.actualizarmenu.getBtnCancelAct().addActionListener(this);
        this.actualizarmenu.getBtnConfirmAct().addActionListener(this);
        this.actualizarmenu.getBtnUndoInv().addActionListener(this);
        this.actualizarmenu.getBtnUndoName().addActionListener(this);
        this.actualizarmenu.getBtnUndoPrice().addActionListener(this);
        
    }
    
    // CRUD 
    public void acterr(){
        vista.getErrNombre().setText("");
        vista.getErrPrecio().setText("");
        vista.getErrInventario().setText("");
        vista.getErrBorrarAct().setText("");
    }
    public void acterrAct(){
        actualizarmenu.getJlbAlertAct1().setText("");
        actualizarmenu.getJlbAlertAct2().setText("");
        actualizarmenu.getJlbAlertAct3().setText("");
    }
    
    public void agregar(){
        acterr();
        if ((!"".equals(vista.getTfNombre().getText())) && (!"".equals(vista.getTfPrecio().getText())) && (!"".equals(vista.getTfInventario().getText()))){
            try {
                producto prod =
                    producto.crearProducto(vista.getTfNombre().getText(),Double.parseDouble(vista.getTfPrecio().getText()),
                    Integer.parseInt(vista.getTfInventario().getText()));
                repositorio.save(prod);
                vista.getTfNombre().setText("");
                vista.getTfPrecio().setText("");
                vista.getTfInventario().setText("");
            } 
            // Excepciones en caso de tipo de dato incorrecto Producto not Double, inventario not Integer.
            catch(Exception AgregarException){
                try{
                Double.parseDouble(vista.getTfPrecio().getText());
                vista.getErrInventario().setText("Ingresa un numero entero");
                } catch (Exception AgregarException2){
                    try {
                        Integer.parseInt(vista.getTfInventario().getText());
                        vista.getErrPrecio().setText("Ingresa un numero decimal con '.' o entero");
                    }catch (Exception AgreException3){
                        vista.getErrPrecio().setText("Ingresa un numero decimal con '.' o entero");
                        vista.getErrInventario().setText("Ingresa un numero entero");
                    }
                }
            }
        // Excepciones en caso de campo vacío.
        } else {
            if ("".equals(vista.getTfNombre().getText()))
                vista.getErrNombre().setText("Campo requerido para agregar producto");
            if ("".equals(vista.getTfPrecio().getText()))
                vista.getErrPrecio().setText("Campo requerido para agregar producto");
            if ("".equals(vista.getTfInventario().getText()))
                vista.getErrInventario().setText("Campo requerido para agregar producto");
        }
    }
    
    
    public List<producto> listar(){
        
        List<producto> ListaProductos = (List<producto>) repositorio.findAll();
        JTable tabla = vista.getTblProductos();
        int row = 0;
        for ( producto s: ListaProductos){
            tabla.setValueAt(s.getNombre(),row,0);
            tabla.setValueAt(s.getPrecio(),row,1);
            tabla.setValueAt(s.getInventario(),row,2);
            row ++;
        }
        
        for (int i = row; i<tabla.getRowCount();i++){
            tabla.setValueAt("", row, 0);
            tabla.setValueAt(null, row, 1);
            tabla.setValueAt(null, row, 2);
        }
        return ListaProductos;
    }
    

    public void actualizar(){
        acterrAct();
        acterr();
        JTable tabla = vista.getTblProductos();
        try{
            if (tabla.getSelectedRow()!=-1){
            String nombre = (String)tabla.getModel().getValueAt(tabla.getSelectedRow(), 0);
            double precio = (double)tabla.getModel().getValueAt(tabla.getSelectedRow(), 1);
            List<producto> ListaProductos = (List<producto>) repositorio.findAll();
            for (producto prod: ListaProductos){
                if (prod.getNombre().equals(nombre) && prod.getPrecio()== precio){
                    actualizarmenu.setVisible(true);
                    actualizarmenu.getJtfNewName().setText(prod.getNombre());
                    actualizarmenu.getJtfNewPrice().setText(String.valueOf(prod.getPrecio()));
                    actualizarmenu.getJtfNewInv().setText(String.valueOf(prod.getInventario()));
                    actualizarmenu.getJlbLastName().setText(actualizarmenu.getJtfNewName().getText());
                    actualizarmenu.getJlbLastPrice().setText(actualizarmenu.getJtfNewPrice().getText());
                    actualizarmenu.getJlbLastInv().setText(actualizarmenu.getJtfNewInv().getText());
                    lastcode = prod.getCod();
                }
            }
            // Excepcion en caso de presionar boton sin seleccion alguna.
            } else {
                vista.getErrBorrarAct().setText("Debe seleccionar un elemento de la lista para la solicitud actualizar");
            }
        // Excepcion en caso de seleccionar un campo vacio de la tabla.
        } catch(Exception BorrarException){
            vista.getErrBorrarAct().setText("Seleccionó un campo vacio, seleccione uno con informacion para actualizar");
        }
        }
    public void confirmActualizar(){
        acterrAct();
        if ((!"".equals(actualizarmenu.getJtfNewName().getText())) && (!"".equals(actualizarmenu.getJtfNewPrice().getText())) && (!"".equals(actualizarmenu.getJtfNewInv().getText()))){
            try {
                producto prodtemp = new producto(lastcode,actualizarmenu.getJtfNewName().getText(),Double.parseDouble(actualizarmenu.getJtfNewPrice().getText()),Integer.parseInt(actualizarmenu.getJtfNewInv().getText()));
                repositorio.save(prodtemp);
                actualizarmenu.setVisible(false);
                listar();
            } 
            // Excepciones en caso de tipo de dato incorrecto Producto not Double, inventario not Integer.
            catch(Exception AgregarException){
                try{
                Double.parseDouble(actualizarmenu.getJtfNewPrice().getText());
                actualizarmenu.getJlbAlertAct3().setText("Ingresa un numero entero");
                } catch (Exception AgregarException2){
                    try {
                        Integer.parseInt(actualizarmenu.getJtfNewInv().getText());
                        actualizarmenu.getJlbAlertAct2().setText("Ingresa un numero decimal con '.' o entero");
                    }catch (Exception AgreException3){
                        actualizarmenu.getJlbAlertAct2().setText("Ingresa un numero decimal con '.' o entero");
                        actualizarmenu.getJlbAlertAct3().setText("Ingresa un numero entero");
                    }
                }
            }
        // Excepciones en caso de campo vacío.
        } else {
            if ("".equals(actualizarmenu.getJtfNewName().getText()))
                actualizarmenu.getJlbAlertAct1().setText("Campo requerido");
            if ("".equals(actualizarmenu.getJtfNewPrice().getText()))
                actualizarmenu.getJlbAlertAct2().setText("Campo requerido");
            if ("".equals(actualizarmenu.getJtfNewInv().getText()))
                actualizarmenu.getJlbAlertAct3().setText("Campo requerido");
        }
    }
    public void cancelActualizar(){
        actualizarmenu.setVisible(false);
    }
    public void undoNombre(){
        actualizarmenu.getJtfNewName().setText(actualizarmenu.getJlbLastName().getText());
    }
    public void undoPrecio(){
        actualizarmenu.getJtfNewPrice().setText(actualizarmenu.getJlbLastPrice().getText());
    }
    public void undoInv(){
        actualizarmenu.getJtfNewInv().setText(actualizarmenu.getJlbLastInv().getText());
    }
    
    //TO DO Buscar otra manera de borrar ( defaultTableModel.removeRow() ? )
    public void borrar(){
        acterr();
        JTable tabla = vista.getTblProductos();
        try{
            if (tabla.getSelectedRow()!=-1){
            String nombre = (String)tabla.getModel().getValueAt(tabla.getSelectedRow(), 0);
            double precio = (double)tabla.getModel().getValueAt(tabla.getSelectedRow(), 1);
            List<producto> ListaProductos = (List<producto>) repositorio.findAll();
            for (producto prod: ListaProductos){
                if (prod.getNombre().equals(nombre) && prod.getPrecio()== precio){
                    Long id = prod.getCod();
                    int confirm = JOptionPane.showConfirmDialog(null, "Seguro que quieres borrar el producto "+prod.getNombre(),"Confirmar Eliminar", JOptionPane.YES_NO_OPTION);
                    if(confirm ==JOptionPane.YES_OPTION){
                    repositorio.deleteById(id);
                    JOptionPane.showMessageDialog(null, "Eliminado "+prod.getNombre(),"Eliminacion exitosa",JOptionPane.INFORMATION_MESSAGE);
                    break;
                    }
                }
            }
            // Excepcion en caso de presionar boton sin seleccion alguna.
            } else {
                vista.getErrBorrarAct().setText("Debe seleccionar un elemento de la lista para la solicitud borrar");
            }
        // Excepcion en caso de seleccionar un campo vacio de la tabla.
        } catch(Exception BorrarException){
            vista.getErrBorrarAct().setText("Seleccionó un campo vacio, seleccione uno con informacion para borrar");
        }
        }
   
    public void limpiar(){
        acterr();
        vista.getTfNombre().setText("");
        vista.getTfPrecio().setText("");
        vista.getTfInventario().setText("");
    }
    
    public void informe(){
        acterr();
        double pmayor = 0;
        double pmenor = 999999999;
        String mayor = "";
        String menor = "";
        Double promedio = 0D;
        Double total = 0D;
        List<producto> ListaProductos = (List<producto>) repositorio.findAll();
        JTable tabla = vista.getTblProductos();
        for ( producto s: ListaProductos){
            if (s.getPrecio() > pmayor){
                pmayor = s.getPrecio();
                mayor = s.getNombre();
            }
            if (s.getPrecio() < pmenor){
                pmenor = s.getPrecio();
                menor = s.getNombre();
            }
            promedio += s.getPrecio();
            total += (s.getPrecio()*s.getInventario());
        }
        DecimalFormat value = new DecimalFormat("###############.#");
        promedio = promedio / ListaProductos.size();
        informemenu.getJlbMayor().setText(mayor);
        informemenu.getJlbMenor().setText(menor);
        informemenu.getJlbAvg().setText(value.format(promedio));
        informemenu.getJlbTotal().setText(value.format(total));
        informemenu.setVisible(true);
    }
    public void aceptarInforme(){
        informemenu.setVisible(false);
    }

    //Acciones
    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == vista.getBtnAgregar()){
            this.agregar();
            this.listar();
        }
        if(e.getSource() == vista.getBtnActualizar()){
            this.actualizar();
            this.listar();
        }
        if(e.getSource() == vista.getBtnBorrar()){
            this.borrar();
            this.listar();
        }
        if(e.getSource() == vista.getBtnInforme()){
            this.informe();
        }
        if(e.getSource() == vista.getBtnLimpiar()){
            this.limpiar();
        }
        if(e.getSource() == informemenu.getBtnAceptarInforme()){
            this.aceptarInforme();
        }
        if(e.getSource() == actualizarmenu.getBtnCancelAct()){
            this.cancelActualizar();
        }
        if(e.getSource() == actualizarmenu.getBtnConfirmAct()){
            this.confirmActualizar();
        }
        if(e.getSource() == actualizarmenu.getBtnUndoName()){
            this.undoNombre();
        }
        if(e.getSource() == actualizarmenu.getBtnUndoPrice()){
            this.undoPrecio();
        }
        if(e.getSource() == actualizarmenu.getBtnUndoInv()){
            this.undoInv();
        }
        }
    }
    

