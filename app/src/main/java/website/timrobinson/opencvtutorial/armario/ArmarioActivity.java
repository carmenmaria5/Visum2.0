package website.timrobinson.opencvtutorial.armario;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.graphics.ColorUtils;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.util.Arrays;
import java.util.List;

import website.timrobinson.opencvtutorial.AlgoritmosCombinaciones;
import website.timrobinson.opencvtutorial.CamaraActivity;
import website.timrobinson.opencvtutorial.R;
import website.timrobinson.opencvtutorial.conjunto.ConjuntoActivity;
import website.timrobinson.opencvtutorial.modelo.Conjunto;
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
    ProgressDialog pDialog;

    public synchronized void setCombina(boolean combina){
        this.combina = combina;
    }

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

                if(adapter.getnSelect() == 0){
                    Toast.makeText(this, "Prendas seleccionadas: 0/2", Toast.LENGTH_SHORT).show();
                }else if (adapter.getnSelect() == 1){
                    Toast.makeText(this, "Prendas seleccionadas: 1/2", Toast.LENGTH_SHORT).show();
                }else if (adapter.getnSelect() == 2){

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

                    if (combinan){
                        alertDialogBuilder.setMessage(R.string.msg_combinan_prendas);
                    }else {
                        alertDialogBuilder.setMessage(R.string.msg_no_combinan_prendas);
                    }

                    alertDialogBuilder.show();

                }

                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private Boolean comprobarColor(String color1, String color2) {

        String[] valoresColor1 = color1.trim().split(",");

        //Color HSL
        final float[] tHSLColor1 = new float[3];

        ColorUtils.RGBToHSL(Integer.parseInt(valoresColor1[0].trim()), Integer.parseInt(valoresColor1[1].trim()),
                Integer.parseInt(valoresColor1[2].trim()),tHSLColor1);

        String[] valoresColor2 = color2.trim().split(",");

        //Color HSL
        final float[] tHSLColor2 = new float[3];

        ColorUtils.RGBToHSL(Integer.parseInt(valoresColor2[0].trim()), Integer.parseInt(valoresColor2[1].trim()),
                Integer.parseInt(valoresColor2[2].trim()),tHSLColor2);
        final boolean[] ret = new boolean[1];

        return AlgoritmosCombinaciones.combinan(tHSLColor1,tHSLColor2);


//        Thread t = new Thread(){
//            @Override
//            public void run() {
//                //Dialogo.
//                final AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ArmarioActivity.this);
//
//                alertDialogBuilder
//                        .setTitle(R.string.title_comprobar_combinacion)
//                        .setCancelable(false)
//
//                        .setPositiveButton(R.string.btn_guardar, new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//                                //Llama a la actividad de "Añarir al armario" para guardar la imagen
//                                Intent i = new Intent(ArmarioActivity.this, ConjuntoActivity.class);
//
//                                i.putExtra("PRENDA1", tPrendas.get(adapter.getItem1()).getId());
//                                i.putExtra("PRENDA2", tPrendas.get(adapter.getItem2()).getId());
//
//                                i.putExtra("EDITAR", true);
//
//                                startActivity(i);
//                            }
//                        })
//
//                        .setNegativeButton(R.string.btn_cancelar, new DialogInterface.OnClickListener() {
//                            public void onClick(DialogInterface dialog, int id) {
//
//                                dialog.cancel();
//                            }
//                        });
//                System.out.println("Hilo ejecutado, se llama al metodo");
//                if (AlgoritmosCombinaciones.combinan(tHSLColor1,tHSLColor2)){
//                    alertDialogBuilder.setMessage(R.string.msg_combinan_prendas);
//                }else {
//                    alertDialogBuilder.setMessage(R.string.msg_no_combinan_prendas);
//                }
//                System.out.println("Pasa de la ejecucion del metodo");
//                runOnUiThread(new Runnable() {
//                    @Override
//                    public void run() {
//                        alertDialogBuilder.show();
//                    }
//                });
//            }
//        };
//        t.start();
    }

//    private class MiTareaAsincronaDialog extends AsyncTask<Void, Integer, Boolean> {
//        float[] tHSL1;
//        float[] tHSL2;
//
//        public MiTareaAsincronaDialog(float[] tHSL1, float[] tHSL2) {
//            this.tHSL1 = Arrays.copyOf(tHSL1, tHSL1.length);
//            this.tHSL2 = Arrays.copyOf(tHSL2, tHSL1.length);
//
//        }
//
//        @Override
//        protected Boolean doInBackground(Void... params) {
//
//            combina = AlgoritmosCombinaciones.combinan(tHSL1,tHSL2);
//
//            combina = true;
//                publishProgress(100);
//
//
//            return true;
//        }
//
//        @Override
//        protected void onProgressUpdate(Integer... values) {
//            int progreso = values[0].intValue();
//
//            pDialog.setProgress(progreso);
//        }
//
//        @Override
//        protected void onPreExecute() {
//
//            pDialog.setOnCancelListener(new DialogInterface.OnCancelListener() {
//                @Override
//                public void onCancel(DialogInterface dialog) {
//                    MiTareaAsincronaDialog.this.cancel(true);
//                }
//            });
//
//            pDialog.setProgress(0);
//            pDialog.show();
//        }
//
//        @Override
//        protected void onPostExecute(Boolean result) {
//            if(result)
//            {
//                pDialog.dismiss();
//                Toast.makeText(ArmarioActivity.this, "Tarea finalizada!",
//                        Toast.LENGTH_SHORT).show();
//            }
//        }
//
//        @Override
//        protected void onCancelled() {
//            Toast.makeText(ArmarioActivity.this, "Tarea cancelada!",
//                    Toast.LENGTH_SHORT).show();
//        }
//    }



}
