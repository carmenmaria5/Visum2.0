package website.timrobinson.opencvtutorial.armario;

import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import website.timrobinson.opencvtutorial.R;
import website.timrobinson.opencvtutorial.modelo.Prenda;
import website.timrobinson.opencvtutorial.persistencia.Bd;

public class PrendaActivity extends AppCompatActivity {

    private ImageView ivPrenda;
    private EditText etNombre;
    private TextView tvColorEdit;
    private RatingBar ratingBar;
    private TextView tvTipo;
    private Spinner spTipos;
    private TextView tvDesc;
    private EditText etDesc;
    private TextView tvEtiquetas;
    private EditText etEtiquetas;

    private String color;
    private Uri imgURIPrenda;
    private boolean editar;
    private boolean yaGuardada;
    Prenda p;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_prenda_editar);

        initView();

        editar = getIntent().getExtras().getBoolean("editar");

        //--- VISUALIZAR ---------------------------------------------------------------------------
        if (!editar) {

            p = null;

            try {
                p = Bd.getPrendaById(getApplicationContext(), getIntent().getExtras().getInt("PRENDA"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            if (p != null) {

                //Recojo los datos del color y la foto para ponerlos en el layout que añade los datos
                imgURIPrenda = p.getFotoPrenda(getApplicationContext());
                ivPrenda.setImageURI(imgURIPrenda);

                etNombre.setText(p.getNombrePrenda());

                color = p.getColor();

                String[] colores = color.split(",");

                //Se establece el color del cuadro correspondiente.
                tvColorEdit.setBackgroundColor(Color.rgb(Integer.parseInt(colores[0].trim()),
                        Integer.parseInt(colores[1].trim()), Integer.parseInt(colores[2].trim())));

                ratingBar.setRating(p.getPuntuacion());

                //Spinner con las prendas de ropa
                Spinner spinner = (Spinner) findViewById(R.id.spTipos);

                // Create an ArrayAdapter using the string array and a default spinner layout
                ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                        R.array.tipos_ropa, android.R.layout.simple_spinner_item);

                // Specify the layout to use when the list of choices appears
                adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

                // Apply the adapter to the spinner
                spinner.setAdapter(adapter);

                spinner.setSelection(adapter.getPosition(p.getTipoPrenda()));

                etDesc.setText(p.getDescripcion());

                etEtiquetas.setText(p.getEtiqueta());

                deshabilitarCampos(editar);

                yaGuardada = true;
            }

        } else {
            //--- NUEVA --------------------------------------------------------------------------


            //Recojo los datos del color y la foto para ponerlos en el layout que añade los datos
            imgURIPrenda = Uri.parse(getIntent().getStringExtra("fotoPrenda"));
            ivPrenda.setImageURI(imgURIPrenda);

            color = getIntent().getExtras().getString("colorPrenda");

            String[] colores = color.split(",");

            //Se establece el color del cuadro correspondiente.
            tvColorEdit.setBackgroundColor(Color.rgb(Integer.parseInt(colores[0].trim()),
                    Integer.parseInt(colores[1].trim()), Integer.parseInt(colores[2].trim())));

            //Spinner con las prendas de ropa
            Spinner spinner = (Spinner) findViewById(R.id.spTipos);

            // Create an ArrayAdapter using the string array and a default spinner layout
            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                    R.array.tipos_ropa, android.R.layout.simple_spinner_item);

            // Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            // Apply the adapter to the spinner
            spinner.setAdapter(adapter);

            yaGuardada = false;
        }

    }

    private void initView() {
        ivPrenda = (ImageView) findViewById(R.id.ivPrenda);
        etNombre = (EditText) findViewById(R.id.etNombre);
        tvColorEdit = (TextView) findViewById(R.id.tvColorEdit);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        tvTipo = (TextView) findViewById(R.id.tvTipo);
        spTipos = (Spinner) findViewById(R.id.spTipos);
        tvDesc = (TextView) findViewById(R.id.tvDesc);
        etDesc = (EditText) findViewById(R.id.etDesc);
        tvEtiquetas = (TextView) findViewById(R.id.tvEtiquetas);
        etEtiquetas = (EditText) findViewById(R.id.etEtiquetas);
    }

    private void deshabilitarCampos(Boolean editar) {
        if (!editar) {
            etNombre.setEnabled(false);
            etDesc.setEnabled(false);
            etEtiquetas.setEnabled(false);
            spTipos.setEnabled(false);
            ratingBar.setEnabled(false);
        } else if (editar) {
            etNombre.setEnabled(true);
            etDesc.setEnabled(true);
            etEtiquetas.setEnabled(true);
            spTipos.setEnabled(true);
            ratingBar.setEnabled(true);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_guarda_prenda: {
                Prenda prenda = new Prenda();
                prenda.setNombrePrenda(etNombre.getText().toString());
                prenda.setPuntuacion((int) ratingBar.getRating());
                prenda.setColor(color);
                prenda.setDescripcion(etDesc.getText().toString());
                prenda.setEtiqueta(etEtiquetas.getText().toString());
                try {
                    prenda.setFotoPrenda(MediaStore.Images.Media.getBitmap(this.getContentResolver(), imgURIPrenda));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                prenda.setTipoPrenda(spTipos.getSelectedItem().toString());

                //Comprobamos que al menos tenga nombre y tipo al añadir la prenda a la BBDD
                if (prenda.getNombrePrenda().toString().equals("") || prenda.getTipoPrenda().equals("")) {
                    Toast.makeText(this, R.string.msg_faltan_datos, Toast.LENGTH_SHORT).show();
                } else {

                    if (yaGuardada) {
                        //--- PRENDA GUARDADA ANTERIORMENTE --------------------------------------------

                        prenda.setId(p.getId());

                        try {
                            Bd.actualizarPrenda(prenda, getApplicationContext());
                        } catch (IOException e) {
                            e.printStackTrace();
                        }

                        Toast.makeText(this, "Prenda actualizada", Toast.LENGTH_SHORT).show();
                        editar = false;
                        deshabilitarCampos(editar);
                        invalidateOptionsMenu();
                    } else {
                        //--- PRENDA DADA DE ALTA ------------------------------------------------------
                        try {
                            Bd.insertarPrenda(prenda, getApplicationContext());
                            Toast.makeText(this, R.string.msg_prenda_anadida, Toast.LENGTH_SHORT).show();
                            editar = false;
                            deshabilitarCampos(editar);
                            finish();
                        } catch (IOException e) {
                            e.printStackTrace();
                            Toast.makeText(this, R.string.msg_error_prenda_anadida, Toast.LENGTH_SHORT).show();

                        }
                    }
                }
                break;
            }
            case R.id.menu_editar_prenda: {
                editar = true;
                deshabilitarCampos(editar);
                invalidateOptionsMenu();
                break;
            }
            case R.id.menu_cancelar_prenda: {


                if (!yaGuardada) {
                    finish();
                } else {
                    editar = false;
                    deshabilitarCampos(editar);
                    invalidateOptionsMenu();
                }

                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }

    //Creación del menú.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_prenda_editar, menu);

        if (editar) {
            menu.findItem(R.id.menu_editar_prenda).setVisible(false);
            menu.findItem(R.id.menu_cancelar_prenda).setVisible(true);
            menu.findItem(R.id.menu_guarda_prenda).setVisible(true);
            getSupportActionBar().setDisplayHomeAsUpEnabled(false);
        } else {
            menu.findItem(R.id.menu_cancelar_prenda).setVisible(false);
            menu.findItem(R.id.menu_guarda_prenda).setVisible(false);
            menu.findItem(R.id.menu_editar_prenda).setVisible(true);
            if (yaGuardada) {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
        }
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
