package website.timrobinson.opencvtutorial.modelo;

import android.content.Context;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore;

import java.io.ByteArrayOutputStream;

public class Prenda {

    private int id;
    private String nombrePrenda;
    private int puntuacion;
    private String color;
    private String descripcion;
    private String etiqueta;
    private Bitmap fotoPrenda;
    private String tipoPrenda;

    public Uri getFotoPrenda(Context context) {

        ByteArrayOutputStream bytesPrenda1 = new ByteArrayOutputStream();
        fotoPrenda.compress(Bitmap.CompressFormat.JPEG, 100, bytesPrenda1);
        String pathPrenda = MediaStore.Images.Media.insertImage(context.getContentResolver(), fotoPrenda, "fotoPrenda", null);
        return Uri.parse(pathPrenda);
    }

    public void setFotoPrenda(Bitmap fotoPrenda) {
        this.fotoPrenda = fotoPrenda;
    }



    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombrePrenda() {
        return nombrePrenda;
    }

    public void setNombrePrenda(String nombrePrenda) {
        this.nombrePrenda = nombrePrenda;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
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

    public String getTipoPrenda() {
        return tipoPrenda;
    }

    public void setTipoPrenda(String tipoPrenda) {
        this.tipoPrenda = tipoPrenda;
    }
}
