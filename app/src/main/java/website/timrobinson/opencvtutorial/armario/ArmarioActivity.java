package website.timrobinson.opencvtutorial.armario;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.graphics.ColorUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.util.List;

import website.timrobinson.opencvtutorial.AlgoritmosCombinaciones;
import website.timrobinson.opencvtutorial.R;
import website.timrobinson.opencvtutorial.conjunto.ConjuntoActivity;
import website.timrobinson.opencvtutorial.modelo.Prenda;
import website.timrobinson.opencvtutorial.persistencia.Bd;

//Clase que controla la vista del armario.
public class ArmarioActivity extends AppCompatActivity {

    //Lista que contendrá las tarjetas con las prendas.
    private RecyclerView rvArmario;

    //Adaptador del RV.
    private ArmarioAdapter adapter;

    //Lista de prendas.
    List<Prenda> tPrendas;

    private boolean combina;

    private String filtroNombre = "";
    private String filtroEtiqueta = "";
    private String filtroTipo = "";
    ProgressDialog pDialog;


    //--- ON CREATE --------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_armario);

        //Se inicialzian las vistas.
        initView();

        //Se recuperan las prendas
        try {
            tPrendas = Bd.getTodasPrendas(getApplicationContext());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        //Se inicializa el adaptador.
        adapter = new ArmarioAdapter(getApplicationContext(), tPrendas);

        //Se indica que el rv contendrá un GridLayoutManager para ordenar su contenido. En este caso, con dos columnas.
        rvArmario.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));

        //Se enlaza con el adaptador.
        rvArmario.setAdapter(adapter);

    }

    //--- OTROS --------------------------------------------------------------------------------

    //Método que inicializa las vistas.
    private void initView() {
        rvArmario = (RecyclerView) findViewById(R.id.rvArmario);
    }

    //Creación del menú.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_armario, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menuCombinar:

                if (adapter.getnSelect() == 0) {
                    Toast.makeText(this, "Prendas seleccionadas: 0/2", Toast.LENGTH_SHORT).show();
                } else if (adapter.getnSelect() == 1) {
                    Toast.makeText(this, "Prendas seleccionadas: 1/2", Toast.LENGTH_SHORT).show();
                } else if (adapter.getnSelect() == 2) {

                    Boolean combinan = comprobarColor(tPrendas.get(adapter.getItem1()).getColor(), tPrendas.get(adapter.getItem2()).getColor());

                    //Dialogo.
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ArmarioActivity.this);

                    alertDialogBuilder
                            .setTitle(R.string.title_comprobar_combinacion)
                            .setCancelable(false)
                            .setIcon(getResources().getDrawable(R.drawable.ic_camera))
                            .setPositiveButton(R.string.btn_guardar, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //Llama a la actividad de "Añarir al armario" para guardar la imagen
                                    Intent i = new Intent(ArmarioActivity.this, ConjuntoActivity.class);

                                    i.putExtra("PRENDA1", tPrendas.get(adapter.getItem1()).getId());
                                    i.putExtra("PRENDA2", tPrendas.get(adapter.getItem2()).getId());

                                    i.putExtra("EDITAR", true);

                                    startActivity(i);
                                }
                            })
                            .setNegativeButton(R.string.btn_cancelar, new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {

                                    dialog.cancel();
                                }
                            });

                    if (combinan) {
                        alertDialogBuilder.setMessage(R.string.msg_combinan_prendas)
                                .setIcon(getResources().getDrawable(R.mipmap.ic_diag_bien));
                    } else {
                        alertDialogBuilder.setMessage(R.string.msg_no_combinan_prendas)
                                .setIcon(getResources().getDrawable(R.mipmap.ic_diag_mal));
                    }

                    alertDialogBuilder.show();

                }

                break;

            case R.id.filtroTipo:

                alertaFiltros("tipo");

                break;

            case R.id.filtroNombre:

                alertaFiltros("nombre");

                break;

            case R.id.filtroEtiqueta:

                alertaFiltros("etiqueta");

                break;
            case R.id.filtroNada:

                adapter = new ArmarioAdapter(getApplicationContext(), tPrendas);
                rvArmario.setAdapter(adapter);

                break;

        }

        return super.onOptionsItemSelected(item);
    }

    private void alertaFiltros(final String filtro) {

        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Filtrar por " + filtro);

        if (filtro.equals("etiqueta") || filtro.equals("nombre")) {

            final EditText input = new EditText(this);

            input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

            builder.setView(input);

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    if (filtro.equals("etiqueta")) {
                        filtroEtiqueta = input.getText().toString();
                    } else {
                        filtroNombre = input.getText().toString();
                    }
                    filtros(filtro);
                }
            });

        } else {

            final Spinner spinner = new Spinner(this);

            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                    R.array.tipos_ropa, android.R.layout.simple_spinner_item);

            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            spinner.setAdapter(adapter);

            builder.setView(spinner);

            builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                @Override
                public void onClick(DialogInterface dialog, int which) {

                    filtroTipo = spinner.getSelectedItem().toString();

                    filtros(filtro);
                }

            });

        }


        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                filtroEtiqueta = "";
                filtroTipo = "";
                filtroNombre = "";
                dialog.cancel();
            }
        });

        builder.show();

    }

    private Boolean comprobarColor(String color1, String color2) {

        String[] valoresColor1 = color1.trim().split(",");

        //Color HSL
        final float[] tHSLColor1 = new float[3];

        ColorUtils.RGBToHSL(Integer.parseInt(valoresColor1[0].trim()), Integer.parseInt(valoresColor1[1].trim()),
                Integer.parseInt(valoresColor1[2].trim()), tHSLColor1);

        String[] valoresColor2 = color2.trim().split(",");

        //Color HSL
        final float[] tHSLColor2 = new float[3];

        ColorUtils.RGBToHSL(Integer.parseInt(valoresColor2[0].trim()), Integer.parseInt(valoresColor2[1].trim()),
                Integer.parseInt(valoresColor2[2].trim()), tHSLColor2);
        final boolean[] ret = new boolean[1];

        return AlgoritmosCombinaciones.combinan(tHSLColor1, tHSLColor2);

    }

    //Actualiza el listView una vez que se ha borrado la prenda de la BBDD
    @Override
    protected void onResume() {
        super.onResume();

        try {
            adapter = new ArmarioAdapter(getApplicationContext(), Bd.getTodasPrendas(getApplicationContext()));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        rvArmario.setAdapter(adapter);
    }

    private void filtros(String tipo) {

        switch (tipo) {
            case "tipo":

                try {
                    adapter = new ArmarioAdapter(getApplicationContext(), Bd.filtroTipoPrenda(getApplicationContext(), filtroTipo));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                rvArmario.setAdapter(adapter);
                break;

            case "nombre":

                try {
                    adapter = new ArmarioAdapter(getApplicationContext(), Bd.filtroNombrePrenda(getApplicationContext(), filtroNombre));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                rvArmario.setAdapter(adapter);

                break;

            case "etiqueta":

                try {
                    adapter = new ArmarioAdapter(getApplicationContext(), Bd.filtroEtiquetaPrenda(getApplicationContext(), filtroEtiqueta));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                rvArmario.setAdapter(adapter);

                break;

        }
    }


}
