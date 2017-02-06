package ar.edu.unrn.lia.loginapp.entities;

import com.raizlabs.android.dbflow.annotation.Column;
import com.raizlabs.android.dbflow.annotation.PrimaryKey;
import com.raizlabs.android.dbflow.annotation.Table;
import com.raizlabs.android.dbflow.structure.BaseModel;

import ar.edu.unrn.lia.loginapp.api.AppDatabase;

/**
 * Created by Germán on 28/11/2016.
 */

@Table(database = AppDatabase.class)
public class Usuario extends BaseModel {
    @Column
    @PrimaryKey(autoincrement = true)
    long id;

    @Column
    String nombre;

    @Column
    String apellido;

    @Column
    String direccion;

    @Column
    String email;

    @Column
    int celular;

    @Column
    String contraseña;

    @Column
    boolean estado;

    @Column
    int sesion;

    @Column
    String provincia;

    @Column
    String localidad;

    public Usuario(String nombre, String apellido, String direccion, String email, int celular, String contraseña, boolean estado, int sesion){
        this.nombre = nombre;
        this.apellido = apellido;
        this.direccion = direccion;
        this.email = email;
        this.celular = celular;
        this.contraseña = contraseña;
        this.estado = estado;
        this.sesion = sesion;
        this.provincia = null;
        this.localidad = null;
    }

    public Usuario(){

    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int getCelular() {
        return celular;
    }

    public void setCelular(int celular) {
        this.celular = celular;
    }

    public String getContraseña() {
        return contraseña;
    }

    public void setContraseña(String contraseña) {
        this.contraseña = contraseña;
    }

    public String getProvincia(){
        return provincia;
    }

    public void setProvincia(String provincia){
        this.provincia = provincia;
    }

    public String getLocalidad(){
        return localidad;
    }

    public void setLocalidad(String localidad){
        this.localidad = localidad;
    }

    public boolean getEstado(){return estado;}

    public void setEstado(boolean estado){
        this.estado = estado;
    }

    public int getSesion(){
        return sesion;
    }

    public void setSesion(int sesion){
        this.sesion = sesion;
    }
}
