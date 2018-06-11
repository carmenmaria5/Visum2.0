package website.timrobinson.opencvtutorial.armario;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import java.io.IOException;
import java.io.UnsupportedEncodingException;

import website.timrobinson.opencvtutorial.R;
import website.timrobinson.opencvtutorial.modelo.Conjunto;
import website.timrobinson.opencvtutorial.modelo.Prenda;
import website.timrobinson.opencvtutorial.persistencia.Bd;

public class ConjuntoActivity extends AppCompatActivity {

    private boolean editar;
    private boolean yaGuardada;
    Prenda p1;
    Prenda p2;
    Conjunto c;

    private TextView textView3;
    private RatingBar ratingBar;
    private TextView tvPrendas;
    private TextView tvConjuntoPrenda1;
    private TextView tvConjuntoPrenda2;
    private ImageView ivPrenda1;
    private ImageView ivPrenda2;
    private TextView etCheckCombina;
    private ImageView checkCombina;
    private TextView tvConjuntoDesc;
    private EditText etConjuntoDesc;
    private TextView tvConjuntoEtiquetas;
    private EditText etConjuntoEtiquetas;

    private Uri imgURIPrenda1;
    private Uri imgURIPrenda2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_conjunto);
        initView();

        editar = getIntent().getExtras().getBoolean("EDITAR");

        //--- VISUALIZAR ---------------------------------------------------------------------------
        if (!editar) {

            c = null;

            try {
                c = Bd.getConjuntoById(getApplicationContext(), getIntent().getExtras().getInt("CONJUNTO"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            if (c != null) {

                try {
                    p1 = Bd.getPrendaById(getApplicationContext(), c.getIdPrenda1());
                    p2 = Bd.getPrendaById(getApplicationContext(), c.getIdPrenda2());
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                //PRENDA 1
                imgURIPrenda1 = p1.getFotoPrenda(getApplicationContext());
                ivPrenda1.setImageURI(imgURIPrenda1);

                tvConjuntoPrenda1.setText(p1.getNombrePrenda());

                //PRENDA 2
                imgURIPrenda2 = p2.getFotoPrenda(getApplicationContext());
                ivPrenda2.setImageURI(imgURIPrenda2);

                tvConjuntoPrenda2.setText(p2.getNombrePrenda());

                //CONJUNTO
                ratingBar.setRating(c.getPuntuacion());
                etConjuntoDesc.setText(c.getDescripcion());
                etConjuntoEtiquetas.setText(c.getEtiqueta());

                deshabilitarCampos(editar);

                yaGuardada = true;
            }

        } else {
            //--- NUEVA ----------------------------------------------------------------------------

            try {
                p1 = Bd.getPrendaById(getApplicationContext(), getIntent().getExtras().getInt("PRENDA1"));
                p2 = Bd.getPrendaById(getApplicationContext(), getIntent().getExtras().getInt("PRENDA2"));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            //PRENDA 1
            imgURIPrenda1 = p1.getFotoPrenda(getApplicationContext());
            ivPrenda1.setImageURI(imgURIPrenda1);

            tvConjuntoPrenda1.setText(p1.getNombrePrenda());

            //PRENDA 2
            imgURIPrenda2 = p2.getFotoPrenda(getApplicationContext());
            ivPrenda2.setImageURI(imgURIPrenda2);

            tvConjuntoPrenda2.setText(p2.getNombrePrenda());

            yaGuardada = false;
        }

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

    private void deshabilitarCampos(Boolean editar) {
        if (!editar) {
            ratingBar.setEnabled(false);
            etConjuntoDesc.setEnabled(false);
            etConjuntoEtiquetas.setEnabled(false);
        } else if (editar) {
            ratingBar.setEnabled(true);
            etConjuntoDesc.setEnabled(true);
            etConjuntoEtiquetas.setEnabled(true);
        }
    }

    private void initView() {
        textView3 = (TextView) findViewById(R.id.textView3);
        ratingBar = (RatingBar) findViewById(R.id.ratingBar);
        tvPrendas = (TextView) findViewById(R.id.tvPrendas);
        tvConjuntoPrenda1 = (TextView) findViewById(R.id.tvConjuntoPrenda1);
        tvConjuntoPrenda2 = (TextView) findViewById(R.id.tvConjuntoPrenda2);
        ivPrenda1 = (ImageView) findViewById(R.id.ivPrenda1);
        ivPrenda2 = (ImageView) findViewById(R.id.ivPrenda2);
        etCheckCombina = (TextView) findViewById(R.id.etCheckCombina);
        checkCombina = (ImageView) findViewById(R.id.checkCombina);
        tvConjuntoDesc = (TextView) findViewById(R.id.tvConjuntoDesc);
        etConjuntoDesc = (EditText) findViewById(R.id.etConjuntoDesc);
        tvConjuntoEtiquetas = (TextView) findViewById(R.id.tvConjuntoEtiquetas);
        etConjuntoEtiquetas = (EditText) findViewById(R.id.etConjuntoEtiquetas);
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_guarda_prenda: {
                Conjunto conjunto = new Conjunto();
                conjunto.setPuntuacion((int) ratingBar.getRating());
                conjunto.setDescripcion(etConjuntoDesc.getText().toString());
                conjunto.setEtiqueta(etConjuntoEtiquetas.getText().toString());
                conjunto.setIdPrenda1(p1.getId());
                conjunto.setIdPrenda2(p2.getId());


                if (yaGuardada) {
                    //--- PRENDA GUARDADA ANTERIORMENTE --------------------------------------------

                    conjunto.setId(c.getId());

                    try {
                        Bd.actualizarConjunto(conjunto, getApplicationContext());
                    } catch (IOException e) {
                        e.printStackTrace();
                    }

                    Toast.makeText(this, "Conjunto actualizado", Toast.LENGTH_SHORT).show();
                    editar = false;
                    deshabilitarCampos(editar);
                    invalidateOptionsMenu();


                } else {
                    //--- PRENDA DADA DE ALTA ------------------------------------------------------

                    try {
                        Bd.insertarConjunto(conjunto, getApplicationContext());
                        Toast.makeText(this, R.string.msg_conjunto_anadido, Toast.LENGTH_SHORT).show();
                        editar = false;
                        deshabilitarCampos(editar);
                        finish();
                    } catch (IOException e) {
                        e.printStackTrace();
                        Toast.makeText(this, R.string.msg_error_prenda_anadida, Toast.LENGTH_SHORT).show();

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
            case R.id.menu_borrar_prenda: {
                AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ConjuntoActivity.this);

                alertDialogBuilder
                        .setTitle(R.string.title_borrar_conjunto)
                        .setMessage(R.string.msg_conjunto_borrado)
                        .setCancelable(false)
                        .setPositiveButton(R.string.msg_si, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                try {
                                    Bd.borrarConjunto(getApplicationContext(), c.getId());
                                    Toast.makeText(ConjuntoActivity.this, R.string.msg_conjunto_borrado, Toast.LENGTH_SHORT).show();
                                    finish();
                                } catch (UnsupportedEncodingException e) {
                                    e.printStackTrace();
                                }
                            }
                        })
                        .setNegativeButton(R.string.msg_no, new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                dialog.cancel();
                            }
                        }).show();
                break;
            }

        }
        return super.onOptionsItemSelected(item);
    }


}
