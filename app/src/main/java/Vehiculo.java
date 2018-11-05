
package com.fuel.app.exe.fuel_it;
public class Vehiculo {

    String id;
    String nombre;
    Float consumo;
    Float tanque;

    public Vehiculo(){

    }

    public Vehiculo(String id,String nombre,Float consumo,Float tanque){
        this.id = id;
        this.nombre = nombre;
        this.consumo = consumo;
        this.tanque = tanque;
    }

    public Float getConsumo(){
        return consumo;
    }

    public void setConsumo(Float consumo) {
        this.consumo = consumo;
    }

    public Float getTanque(){
        return tanque;
    }

    public void setTanque(Float tanque) {
        this.tanque = tanque;
    }

    public String getNombre(){
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
