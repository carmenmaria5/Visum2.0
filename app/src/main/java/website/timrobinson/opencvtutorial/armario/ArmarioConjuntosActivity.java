package website.timrobinson.opencvtutorial.armario;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import java.io.UnsupportedEncodingException;
import java.util.List;

import website.timrobinson.opencvtutorial.R;
import website.timrobinson.opencvtutorial.modelo.Conjunto;
import website.timrobinson.opencvtutorial.persistencia.Bd;

public class ArmarioConjuntosActivity extends AppCompatActivity {

    private RecyclerView rvArmarioConjuntos;

    //Adaptador del RV.
    private ArmarioConjuntosAdapter adapter;

    //Lista de prendas.
    List<Conjunto> tConjuntos;

    private String filtroEtiqueta = "";
    ProgressDialog pDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_armario_conjuntos);
        initView();

        //Se recuperan las prendas
        try {
            tConjuntos = Bd.getTodosConjuntos(getApplicationContext());
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

        //Se inicializa el adaptador.
        adapter = new ArmarioConjuntosAdapter(getApplicationContext(), tConjuntos);

        //Se indica que el rv contendrá un GridLayoutManager para ordenar su contenido. En este caso, con dos columnas.
        rvArmarioConjuntos.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));

        //Se enlaza con el adaptador.
        rvArmarioConjuntos.setAdapter(adapter);

    }

    private void initView() {
        rvArmarioConjuntos = (RecyclerView) findViewById(R.id.rvArmarioConjuntos);
    }

    //Creación del menú.
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_armario_conjuntos, menu);
        return true;
    }

    //Actualiza el listView una vez que se ha borrado la prenda de la BBDD
    @Override
    protected void onResume() {
        super.onResume();

        try {
            adapter = new ArmarioConjuntosAdapter(getApplicationContext(), Bd.getTodosConjuntos(getApplicationContext()));
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        rvArmarioConjuntos.setAdapter(adapter);
    }

    private void alertaFiltros(final String filtro) {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Filtrar por etiqueta");


        final EditText input = new EditText(this);

        input.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);

        builder.setView(input);

        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                filtroEtiqueta = input.getText().toString();

                filtros(filtro);
            }
        });

        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                filtroEtiqueta = "";
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void filtros(String tipo) {

        switch (tipo) {
            case "etiqueta":
                try {
                    adapter = new ArmarioConjuntosAdapter(getApplicationContext(), Bd.filtroEtiquetaConjunto(getApplicationContext(), filtroEtiqueta));
                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

                rvArmarioConjuntos.setAdapter(adapter);

                break;

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.filtroEtiqueta:

                alertaFiltros("etiqueta");

                break;
            case R.id.filtroNada:

                adapter = new ArmarioConjuntosAdapter(getApplicationContext(), tConjuntos);
                rvArmarioConjuntos.setAdapter(adapter);

                break;
        }

        return super.onOptionsItemSelected(item);
    }

}
