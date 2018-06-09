package website.timrobinson.opencvtutorial.armario;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import website.timrobinson.opencvtutorial.R;

//Clase que controla la vista del armario.
public class ArmarioActivity extends AppCompatActivity {

    //Lista que contendrá las tarjetas con las prendas.
    private RecyclerView rvArmario;

    //--- ON CREATE --------------------------------------------------------------------------------
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_armario);

        //Se inicialzian las vistas.
        initView();

        //Se indica que el rv contendrá un GridLayoutManager para ordenar su contenido. En este caso, con dos columnas.
        rvArmario.setLayoutManager(new GridLayoutManager(getApplicationContext(), 2));

        //Se enlaza con el adaptador.
        rvArmario.setAdapter(new ArmarioAdapter(getApplicationContext()));
    }

    //--- OTROS --------------------------------------------------------------------------------

    //Método que inicializa las vistas.
    private void initView() {
        rvArmario = (RecyclerView) findViewById(R.id.rvArmario);
    }
}
