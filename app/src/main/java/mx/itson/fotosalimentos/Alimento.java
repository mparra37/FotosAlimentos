package mx.itson.fotosalimentos;

import android.graphics.Bitmap;

import java.util.Objects;

public class Alimento {
//    private int id;
    private Bitmap imagen;
//    private int imagen;
    private String nombre;
    private String descripcion;
    private String path;

    public Alimento(){

    }

    public Alimento( Bitmap imagen, String nombre, String descripcion, String path){
//        this.id = id;
        this.imagen = imagen;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.path = path;
    }

//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }

    public Bitmap getImagen() {
        return imagen;
    }

    public void setImagen(Bitmap imagen) {
        this.imagen = imagen;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

//    @Override
//    public boolean equals(Object o) {
//        if (this == o) return true;
//        if (o == null || getClass() != o.getClass()) return false;
//        Alimento alimento = (Alimento) o;
//        return id == alimento.id;
//    }

//    @Override
//    public int hashCode() {
//        return Objects.hash(id);
//    }

//    @Override
//    public String toString() {
//        return "Alimento{" +
//                "id=" + id +
////                ", imagen=" + imagen +
//                ", hora='" + hora + '\'' +
//                ", descripcion='" + descripcion + '\'' +
//                ", fecha='" + fecha + '\'' +
//                '}';
//    }
}
