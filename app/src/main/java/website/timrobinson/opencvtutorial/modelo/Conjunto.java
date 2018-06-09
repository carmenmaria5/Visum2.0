package website.timrobinson.opencvtutorial.modelo;

public class Conjunto {

    private int id;
    private int idPrenda1;
    private int idPrenda2;
    private int puntuacion;
    private String descripcion;
    private String etiqueta;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdPrenda1() {
        return idPrenda1;
    }

    public void setIdPrenda1(int idPrenda1) {
        this.idPrenda1 = idPrenda1;
    }

    public int getIdPrenda2() {
        return idPrenda2;
    }

    public void setIdPrenda2(int idPrenda2) {
        this.idPrenda2 = idPrenda2;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getEtiqueta() {
        return etiqueta;
    }

    public void setEtiqueta(String etiqueta) {
        this.etiqueta = etiqueta;
    }
}
