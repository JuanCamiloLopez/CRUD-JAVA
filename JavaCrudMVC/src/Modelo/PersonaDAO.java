/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Modelo;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PersonaDAO {
    
    //creamos un nuevo objeto conectar de la clase Conexion.
    Conexion conectar = new Conexion(); 
    
    //calases de JDBC
    Connection con; //llamamos un metodo del driver jdbc llamado Connection y le asignamos una variable.
    PreparedStatement ps; //el metodo preparedStatement nos sirve para interpretar sentencias sql.
    ResultSet rs; //nos muestra el resultado de las consultas para utilizarlas en el código.
    
    public List listar(){
        List <Persona> datos = new ArrayList<>(); //asi llamamos y creamos un nuevo objeto de la libreria list y array list para guardar nuestros datos de forma organizada en nuestra tabla.
        String sql = "select * from persona"; //sentencia sql
        try {
            con = conectar.getConnection(); //con como funcion jdbc es igual al metodo conectar de la clase conexion del paquete modelo.
            ps = con.prepareStatement(sql); //ejecutamos coneccion y sentensia sql siendo interpretada por el pepareStatedment.
            rs = ps.executeQuery(); // resulSet me trae el resultado de la consulta cuando llamo el metodo executeQuery.
            while (rs.next()){ //el resultado de la consulta lo parametriso en un bucle.
                Persona p = new Persona(); //creo un objeto persona.
                //envio al id de este objeto datos tomados de la base de datos a través del rs y la posicion en la tabla. hago esto con todas las columnas de está.
                p.setId(rs.getInt(1)); 
                p.setNombre(rs.getString(2));
                p.setCorreo(rs.getString(3));
                p.setTelefono(rs.getString(4));
                datos.add(p);// añadimos los datos extraidos a la array lista parametrizando el objeto.
            }
             
        } catch (Exception e) {
        }
        return datos;
    }
    
    public int agregar(Persona p){
        String sql = "insert into persona(nombre, correo, telefono) values (?,?,?)";
        try {
            con = conectar.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, p.getNombre());
            ps.setString(2, p.getCorreo());
            ps.setString(3, p.getTelefono());
            ps.executeUpdate();
            
            
        } catch (Exception e) {
        }
        return 1;     
    }
    
    public int Actualizar (Persona p){
        int r = 0;
        String sql = "update persona set nombre=?, correo=?, telefono=? where id=?";
        try { 
            con = conectar.getConnection();
            ps = con.prepareStatement(sql);
            ps.setString(1, p.getNombre());
            ps.setString(2, p.getCorreo());
            ps.setString(3, p.getTelefono());
            ps.setInt(4, p.getId());
            r=ps.executeUpdate();
            if(r==1){
                return 1;
            }
            else{
                return 0;
            }
        } catch (Exception e) {
        }
        return r;
    }
    
    public void delete(int id){
        String sql = "delete from persona where id="+id;
        try {
            con = conectar.getConnection();
            ps = con.prepareStatement(sql);
            ps.executeUpdate();
        } catch (Exception e) {
        }
    }
}
