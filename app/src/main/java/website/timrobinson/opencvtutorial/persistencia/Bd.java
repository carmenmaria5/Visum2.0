package website.timrobinson.opencvtutorial.persistencia;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.provider.MediaStore;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import website.timrobinson.opencvtutorial.modelo.Conjunto;
import website.timrobinson.opencvtutorial.modelo.Prenda;

public class Bd {
    private SQLiteDatabase bd;
    private Context context;

    private static SQLiteDatabase sqliteDatabase = null;

    public Bd(Context context) {
        this.context = context;
    }

    public static SQLiteDatabase getBd(Context context) {
        if (sqliteDatabase == null) {

            VisumSqliteHelper usdbh =
                    new VisumSqliteHelper(context, "visum.sqlite", null, 1);

            sqliteDatabase = usdbh.getWritableDatabase();

        }
        return sqliteDatabase;
    }

    public static void cerrarConexion() {
        if (sqliteDatabase != null) {
            sqliteDatabase.close();
            sqliteDatabase = null;
        }
    }

    public static void insertarPrenda(Prenda prenda, Context context) throws IOException {
        String insertSql = "INSERT INTO prendas (idPrenda,nombrePrenda,puntuacion,color,descripcion,etiqueta,fotoPrenda,tipoPrenda) VALUES (?,?,?,?,?,?,?,?)";
        Object[] prendaObject = new Object[8];
        prendaObject[0] = null;
        prendaObject[1] = prenda.getNombrePrenda();
        prendaObject[2] = prenda.getPuntuacion();
        prendaObject[3] = prenda.getColor();
        prendaObject[4] = prenda.getDescripcion();
        prendaObject[5] = prenda.getEtiqueta();


        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        Bitmap bitmap = MediaStore.Images.Media.getBitmap(context.getContentResolver(), prenda.getFotoPrenda(context));
        bitmap.compress(Bitmap.CompressFormat.PNG, 0, stream);


        prendaObject[6] = stream.toByteArray();

        prendaObject[7] = prenda.getTipoPrenda();

        getBd(context).execSQL(insertSql, prendaObject);

    }

    public static void actualizarPrenda(Prenda prenda, Context context) throws IOException {
        String updateSql = "UPDATE prendas SET nombrePrenda = ?, puntuacion = ?, descripcion = ?, etiqueta  = ?, tipoPrenda = ? WHERE idPrenda = ?";
        Object[] prendaObject = new Object[6];
        prendaObject[0] = prenda.getNombrePrenda();
        prendaObject[1] = prenda.getPuntuacion();
        prendaObject[2] = prenda.getDescripcion();
        prendaObject[3] = prenda.getEtiqueta();
        prendaObject[4] = prenda.getTipoPrenda();
        prendaObject[5] = prenda.getId();

        getBd(context).execSQL(updateSql, prendaObject);

    }

    public static void insertarConjunto(Conjunto conjunto, Context context) throws IOException {
        String insertSql = "INSERT INTO conjuntos (idConjunto, idPrenda1, idPrenda2 , puntuacion ,descripcion , etiqueta ) VALUES (?,?,?,?,?,?)";
        Object[] conjuntoObject = new Object[6];
        conjuntoObject[0] = null;
        conjuntoObject[1] = conjunto.getIdPrenda1();
        conjuntoObject[2] = conjunto.getIdPrenda2();
        conjuntoObject[3] = conjunto.getPuntuacion();
        conjuntoObject[4] = conjunto.getDescripcion();
        conjuntoObject[5] = conjunto.getEtiqueta();

        getBd(context).execSQL(insertSql, conjuntoObject);

    }

    public static void actualizarConjunto(Conjunto conjunto, Context context) throws IOException {
        String updateSql = "UPDATE conjuntos SET puntuacion = ?, descripcion = ?, etiqueta = ? WHERE idConjunto = ?";
        Object[] conjuntoObject = new Object[4];
        conjuntoObject[0] = conjunto.getPuntuacion();
        conjuntoObject[1] = conjunto.getDescripcion();
        conjuntoObject[2] = conjunto.getEtiqueta();
        conjuntoObject[3] = conjunto.getId();

        getBd(context).execSQL(updateSql, conjuntoObject);

    }

    public static List<Conjunto> getTodosConjuntos(Context context) throws UnsupportedEncodingException {
        Cursor c = getBd(context).rawQuery("SELECT * FROM conjuntos", null);

        List<Conjunto> listaConjuntos = new ArrayList<>();
        Conjunto conjuntoTemp;
        while (c.moveToNext()) {
            conjuntoTemp = new Conjunto();
            conjuntoTemp.setId(c.getInt(0));
            conjuntoTemp.setIdPrenda1(c.getInt(1));
            conjuntoTemp.setIdPrenda2(c.getInt(2));
            conjuntoTemp.setPuntuacion(c.getInt(3));
            conjuntoTemp.setDescripcion(c.getString(4));
            conjuntoTemp.setEtiqueta(c.getString(5));

            listaConjuntos.add(conjuntoTemp);
        }
        return listaConjuntos;
    }

    public static List<Prenda> getTodasPrendas(Context context) throws UnsupportedEncodingException {
        Cursor c = getBd(context).rawQuery("SELECT * FROM prendas", null);

        List<Prenda> listaPrendas = new ArrayList<>();
        Prenda prendaTemp;
        while (c.moveToNext()) {
            prendaTemp = new Prenda();
            prendaTemp.setId(c.getInt(0));
            prendaTemp.setNombrePrenda(c.getString(1));
            prendaTemp.setPuntuacion(c.getInt(2));
            prendaTemp.setColor(c.getString(3));
            prendaTemp.setDescripcion(c.getString(4));
            prendaTemp.setEtiqueta(c.getString(5));


            prendaTemp.setFotoPrenda(BitmapFactory.decodeByteArray(c.getBlob(6), 0, c.getBlob(6).length));

            prendaTemp.setTipoPrenda(c.getString(7));

            listaPrendas.add(prendaTemp);
        }
        return listaPrendas;
    }

    public static Prenda getPrendaById(Context context, int idPrenda) throws UnsupportedEncodingException {

        Cursor c = getBd(context).rawQuery("SELECT * FROM prendas WHERE idPrenda=?", new String[]{String.valueOf(idPrenda)});

        Prenda prendaTemp = null;
        if (c.moveToNext()) {
            prendaTemp = new Prenda();
            prendaTemp.setId(c.getInt(0));
            prendaTemp.setNombrePrenda(c.getString(1));
            prendaTemp.setPuntuacion(c.getInt(2));
            prendaTemp.setColor(c.getString(3));
            prendaTemp.setDescripcion(c.getString(4));
            prendaTemp.setEtiqueta(c.getString(5));


            prendaTemp.setFotoPrenda(BitmapFactory.decodeByteArray(c.getBlob(6), 0, c.getBlob(6).length));

            prendaTemp.setTipoPrenda(c.getString(7));

        }
        return prendaTemp;
    }


    //--- FILTROS PRENDAS -------------------------------------
    public static List<Prenda> filtroNombrePrenda(Context context, String nombrePrenda) throws UnsupportedEncodingException {

        Cursor c = getBd(context).rawQuery("SELECT * FROM prendas WHERE nombrePrenda = ?", new String[]{nombrePrenda});

        List<Prenda> listaPrendas = new ArrayList<>();
        Prenda prendaTemp = null;
        if (c.moveToNext()) {
            prendaTemp = new Prenda();
            prendaTemp.setId(c.getInt(0));
            prendaTemp.setNombrePrenda(c.getString(1));
            prendaTemp.setPuntuacion(c.getInt(2));
            prendaTemp.setColor(c.getString(3));
            prendaTemp.setDescripcion(c.getString(4));
            prendaTemp.setEtiqueta(c.getString(5));


            prendaTemp.setFotoPrenda(BitmapFactory.decodeByteArray(c.getBlob(6), 0, c.getBlob(6).length));

            prendaTemp.setTipoPrenda(c.getString(7));

            listaPrendas.add(prendaTemp);

        }

        return listaPrendas;
    }

    public static List<Prenda>  filtroEtiquetaPrenda(Context context, String etiqueta) throws UnsupportedEncodingException {

        Cursor c = getBd(context).rawQuery("SELECT * FROM prendas WHERE etiqueta LIKE ? ", new String[]{"%"+etiqueta+"%"});

        List<Prenda> listaPrendas = new ArrayList<>();
        Prenda prendaTemp = null;
        if (c.moveToNext()) {
            prendaTemp = new Prenda();
            prendaTemp.setId(c.getInt(0));
            prendaTemp.setNombrePrenda(c.getString(1));
            prendaTemp.setPuntuacion(c.getInt(2));
            prendaTemp.setColor(c.getString(3));
            prendaTemp.setDescripcion(c.getString(4));
            prendaTemp.setEtiqueta(c.getString(5));


            prendaTemp.setFotoPrenda(BitmapFactory.decodeByteArray(c.getBlob(6), 0, c.getBlob(6).length));

            prendaTemp.setTipoPrenda(c.getString(7));

            listaPrendas.add(prendaTemp);

        }
        return listaPrendas;
    }

    public static List<Prenda> filtroTipoPrenda(Context context, String tipoPrenda) throws UnsupportedEncodingException {

        Cursor c = getBd(context).rawQuery("SELECT * FROM prendas WHERE tipoPrenda LIKE ?", new String[]{tipoPrenda});

        List<Prenda> listaPrendas = new ArrayList<>();
        Prenda prendaTemp = null;
        if (c.moveToNext()) {
            prendaTemp = new Prenda();
            prendaTemp.setId(c.getInt(0));
            prendaTemp.setNombrePrenda(c.getString(1));
            prendaTemp.setPuntuacion(c.getInt(2));
            prendaTemp.setColor(c.getString(3));
            prendaTemp.setDescripcion(c.getString(4));
            prendaTemp.setEtiqueta(c.getString(5));


            prendaTemp.setFotoPrenda(BitmapFactory.decodeByteArray(c.getBlob(6), 0, c.getBlob(6).length));

            prendaTemp.setTipoPrenda(c.getString(7));

            listaPrendas.add(prendaTemp);

        }
        return listaPrendas;
    }

    //--- FILTROS CONJUNTOS -------------------------------------
    public static List<Conjunto> filtroEtiquetaConjunto(Context context, String etiqueta) throws UnsupportedEncodingException {
        Cursor c = getBd(context).rawQuery("SELECT * FROM conjuntos WHERE etiqueta LIKE ? ", new String[]{"%"+etiqueta+"%"});

        List<Conjunto> listaConjuntos = new ArrayList<>();
        Conjunto conjuntoTemp;
        while (c.moveToNext()) {
            conjuntoTemp = new Conjunto();
            conjuntoTemp.setId(c.getInt(0));
            conjuntoTemp.setIdPrenda1(c.getInt(1));
            conjuntoTemp.setIdPrenda2(c.getInt(2));
            conjuntoTemp.setPuntuacion(c.getInt(3));
            conjuntoTemp.setDescripcion(c.getString(4));
            conjuntoTemp.setEtiqueta(c.getString(5));

            listaConjuntos.add(conjuntoTemp);
        }
        return listaConjuntos;
    }

    //-------------------------------------------------------

    public static void borrarPrenda(Context context, int idPrenda) throws UnsupportedEncodingException {

        getBd(context).execSQL("DELETE FROM prendas WHERE idPrenda=?", new Object[]{idPrenda});

    }


    public static void borrarConjunto(Context context, int idConjunto) throws UnsupportedEncodingException {

        getBd(context).execSQL("DELETE FROM conjuntos WHERE idConjunto=?", new Object[]{idConjunto});

    }

    public static void borrarConjuntoPorIdPrenda(Context context, int idPrenda) throws UnsupportedEncodingException {

        getBd(context).execSQL("DELETE FROM conjuntos WHERE idPrenda1=? OR idPrenda2=?", new Object[]{idPrenda, idPrenda});

    }

}
