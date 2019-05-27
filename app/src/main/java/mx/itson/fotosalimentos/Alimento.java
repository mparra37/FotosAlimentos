package mx.itson.fotosalimentos;

import java.util.Objects;

public class Alimento {
    private int id;
    private int imagen;
    private String hora;
    private String descripcion;
    private String fecha;

    public Alimento(){

    }

    public Alimento(int id, int imagen, String hora, String descripcion, String fecha){
        this.id = id;
        this.imagen = imagen;
        this.hora = hora;
        this.descripcion = descripcion;
        this.fecha = fecha;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getImagen() {
        return imagen;
    }

    public void setImagen(int imagen) {
        this.imagen = imagen;
    }

    public String getHora() {
        return hora;
    }

    public void setHora(String hora) {
        this.hora = hora;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getFecha() {
        return fecha;
    }

    public void setFecha(String fecha) {
        this.fecha = fecha;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Alimento alimento = (Alimento) o;
        return id == alimento.id;
    }

//    @Override
//    public int hashCode() {
//        return Objects.hash(id);
//    }

    @Override
    public String toString() {
        return "Alimento{" +
                "id=" + id +
                ", imagen=" + imagen +
                ", hora='" + hora + '\'' +
                ", descripcion='" + descripcion + '\'' +
                ", fecha='" + fecha + '\'' +
                '}';
    }
}
