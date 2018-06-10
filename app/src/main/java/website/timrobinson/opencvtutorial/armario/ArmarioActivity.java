package website.timrobinson.opencvtutorial.armario;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
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
import java.util.List;

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

                if(adapter.getItemCount() == 0){
                    Toast.makeText(this, "Prendas seleccionadas: 0/2", Toast.LENGTH_SHORT).show();
                }else if (adapter.getItemCount() == 1){
                    Toast.makeText(this, "Prendas seleccionadas: 1/2", Toast.LENGTH_SHORT).show();
                }else if (adapter.getItemCount() == 2){

                    Boolean combinan = comprobarColor(tPrendas.get(adapter.getItem1()).getColor(), tPrendas.get(adapter.getItem2()).getColor());

                    //Dialogo.
                    AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(ArmarioActivity.this);

                    alertDialogBuilder
                            .setTitle("Comprobación de combinación")
                            .setCancelable(false)

                            .setPositiveButton("Guardar", new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int id) {
                                    //Llama a la actividad de "Añarir al armario" para guardar la imagen
                                    Intent i = new Intent(ArmarioActivity.this, ConjuntoActivity.class);

                                    i.putExtra("PRENDA1", tPrendas.get(adapter.getItem1()).getId());
                                    i.putExtra("PRENDA2", tPrendas.get(adapter.getItem2()).getId());

                                    i.putExtra("EDITAR", true);

                                    startActivity(i);
                                }
                            })

                            .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                        public void onClick(DialogInterface dialog, int id) {

                            dialog.cancel();
                        }
                    });

                    if (combinan){
                        alertDialogBuilder.setMessage("Las prendas combinan");
                    }else {
                        alertDialogBuilder.setMessage("Las prendas no combinan");
                    }

                    alertDialogBuilder.show();

                }

                break;
        }

        return super.onOptionsItemSelected(item);
    }

    private Boolean comprobarColor(String color, String color1) {

        return true;
    }
}
