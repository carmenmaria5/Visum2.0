package website.timrobinson.opencvtutorial.armario;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import website.timrobinson.opencvtutorial.R;

public class PrendaActivity extends AppCompatActivity {

    private ImageView ivPrenda;
    private TextView tvNombre;
    private EditText etNombre;
    private TextView tvColor;
    private TextView tvColorEdit;
    private TextView textView3;
    private RatingBar ratingBar;
    private TextView tvTipo;
    private Spinner spTipos;
    private TextView tvDesc;
    private EditText etDesc;
    private TextView tvEtiquetas;
    private EditText etEtiquetas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prenda_editar);
        initView();

        //Se recupera el booleano.
        Boolean editar = getIntent().getExtras().getBoolean("EDITAR");

        //Si es false (Solo visualización), se deshabilitan los controles.
        if (!editar){
            etNombre.setEnabled(false);
            etDesc.setEnabled(false);
            etEtiquetas.setEnabled(false);
            spTipos.setEnabled(false);
        }

        //En otro caso, continua la ejecución con los controles habilitados por defecto.

    }

    private void initView() {
        ivPrenda = (ImageView) findViewById(R.id.ivPrenda);
        tvNombre = (TextView) findViewById(R.id.tvNombre);
        etNombre = (EditText) findViewById(R.id.etNombre);
        tvColor = (TextView) findViewById(R.id.tvColor);
        tvColorEdit = (TextView) findViewById(R.id.tvColorEdit);
        textView3 = (TextView) findViewById(R.id.textView3);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        tvTipo = (TextView) findViewById(R.id.tvTipo);
        spTipos = (Spinner) findViewById(R.id.spTipos);
        tvDesc = (TextView) findViewById(R.id.tvDesc);
        etDesc = (EditText) findViewById(R.id.etDesc);
        tvEtiquetas = (TextView) findViewById(R.id.tvEtiquetas);
        etEtiquetas = (EditText) findViewById(R.id.etEtiquetas);
    }
}
