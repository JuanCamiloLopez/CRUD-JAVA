/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Persona;
import Modelo.PersonaDAO;
import Vista.Vista;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;


public class Controlador implements ActionListener{ //implemeto la interfaz ActionListener para así implemetar el método ActionPerformed.
    PersonaDAO dao = new PersonaDAO(); // para la coneccion con  la base de datos de la persona 
    Persona p = new Persona(); //para traer la persona y sus atributos
    Vista vista = new Vista(); //para traer la vista 
    DefaultTableModel modelo = new DefaultTableModel(); //para darle funcionalidad a la tabla 

    public Controlador(Vista v) { //referenciamos todos los botones que vamos a utlizar en nuestro formulario.
        this.vista = v; 
        this.vista.btnListar.addActionListener(this);
        this.vista.btnGuardar.addActionListener(this);
        this.vista.btnEditar.addActionListener(this);
        this.vista.btnOk.addActionListener(this);
        this.vista.btnEliminar.addActionListener(this);
        listar(vista.tabla); 
    }
    
    @Override
    public void actionPerformed(ActionEvent e) {
        
        if (e.getSource() == vista.btnListar){
            listar(vista.tabla);
            limpiarTabla();
            listar(vista.tabla);
        }
        
        if(e.getSource() == vista.btnGuardar){
            agregar();
            limpiarTabla();
            listar(vista.tabla);
        }  
        
        if(e.getSource() == vista.btnEditar){
            int fila = vista.tabla.getSelectedRow();
            if(fila == -1){
                JOptionPane.showMessageDialog(vista, "Debe seleccionar una fila.");
            } else {
                int id =Integer.parseInt((String)vista.tabla.getValueAt(fila, 0).toString());
                String nombre = (String)vista.tabla.getValueAt(fila,1);
                String correo = (String)vista.tabla.getValueAt(fila, 2);
                String telefono = (String)vista.tabla.getValueAt(fila, 3);
                vista.txtId.setText(""+id);
                vista.txtNombres.setText(nombre);
                vista.txtCorreo.setText(correo);
                vista.txtTelefono.setText(telefono);
            }
            limpiarTabla();
            listar(vista.tabla);
        }
        
        if(e.getSource()== vista.btnOk){
            Actualizar();
            limpiarTabla();
            listar(vista.tabla);
        }
        
        if(e.getSource()== vista.btnEliminar){
           delete();
           limpiarTabla();
            listar(vista.tabla);
           
        }
    }   
    
     public void delete(){
        int fila = vista.tabla.getSelectedRow();
        if(fila ==-1){
            JOptionPane.showMessageDialog(vista, "Debe seleccionar un usuario");                
        } else { 
            int id = Integer.parseInt((String)vista.tabla.getValueAt(fila, 0).toString());
            dao.delete(id);
            JOptionPane.showMessageDialog(vista, "usuario eliminado." );
        }
     }
    
    void limpiarTabla(){
        for (int i = 0; i < vista.tabla.getRowCount(); i++) {
            modelo.removeRow(i);
            i = i-1;
        }
    }
    
    public void agregar(){
        String nombre = vista.txtNombres.getText();
        String correo = vista.txtCorreo.getText();
        String telefono = vista.txtTelefono.getText();
        p.setNombre(nombre);
        p.setCorreo(correo);
        p.setTelefono(telefono);
        dao.agregar(p);
        int r =1;
        if (r == 1){
            JOptionPane.showMessageDialog(vista, "Usuario Agregado.");
        } else {
            JOptionPane.showMessageDialog(vista, "Error.");
        } 
    }
    
    public void listar(JTable tabla){
        modelo = (DefaultTableModel)tabla.getModel();
        List<Persona> lista = dao.listar();
        Object[] object = new Object[4];
        for (int i = 0; i < lista.size(); i++) {
            object[0]=lista.get(i).getId();
            object[1]=lista.get(i).getNombre();
            object[2]=lista.get(i).getCorreo();
            object[3]=lista.get(i).getTelefono();
            modelo.addRow(object);
        }
        vista.tabla.setModel(modelo);
    }
    
    public void Actualizar( ){
     
        if(vista.txtId.getText().equals("")) {
            JOptionPane.showMessageDialog(vista, "No se Identifica el Id debe selecionar la opcion Editar");
        } else { 
            int id = Integer.parseInt(vista.txtId.getText());
            String nombre = vista.txtNombres.getText();
            String correo = vista.txtCorreo.getText();
            String telefono = vista.txtTelefono.getText();
            p.setId(id);
            p.setNombre(nombre);
            p.setCorreo(correo);
            p.setTelefono(telefono);
            dao.Actualizar(p);
            int r = 1;
            if( r == 1) {
                JOptionPane.showMessageDialog(vista, "Usiario actualizado");
            }
        }    
    }
    
}
