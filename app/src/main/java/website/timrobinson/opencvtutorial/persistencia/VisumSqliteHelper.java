package website.timrobinson.opencvtutorial.persistencia;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class VisumSqliteHelper extends SQLiteOpenHelper {

    private String sqlCreatePrendas = "CREATE TABLE IF NOT EXISTS prendas (idPrenda integer primary key,nombrePrenda VARCHAR(45), puntuacion INTEGER,color VARCHAR(45),descripcion VARCHAR(45),etiqueta VARCHAR(45), fotoPrenda BLOB, tipoPrenda VARCHAR(45))" ;
    private String sqlCreateConjuntos = "CREATE TABLE IF NOT EXISTS conjuntos (idConjunto integer primary key,idPrenda1 VARCHAR(45),idPrenda2 VARCHAR(45), puntuacion INTEGER,descripcion VARCHAR(45),etiqueta VARCHAR(45),FOREIGN KEY(idPrenda1) REFERENCES prendas(idPrenda),FOREIGN KEY(idPrenda2) REFERENCES prendas(idPrenda))" ;


    public VisumSqliteHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL(sqlCreatePrendas);
        sqLiteDatabase.execSQL(sqlCreateConjuntos);

    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS prendas");
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS conjuntos");

        sqLiteDatabase.execSQL(sqlCreatePrendas);
        sqLiteDatabase.execSQL(sqlCreateConjuntos);
    }
}
