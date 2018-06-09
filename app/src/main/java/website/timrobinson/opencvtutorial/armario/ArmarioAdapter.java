package website.timrobinson.opencvtutorial.armario;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import website.timrobinson.opencvtutorial.CamaraActivity;
import website.timrobinson.opencvtutorial.R;

//Adaptador que marca el comportamiento de la lista de prendas.
public class ArmarioAdapter extends RecyclerView.Adapter {

    int item;
    Context context;

    public ArmarioAdapter(Context context) {
        this.context = context;
    }

    //Se enlaza e inicializa la lista con el viewholder indicado, en este caso las celdas de la lista de ropa.
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View cell = LayoutInflater.from(parent.getContext()).inflate(R.layout.armario_celda, parent, false);


        TextView tvTitulo;
        cell.findViewById(R.id.textView2);
        cell.findViewById(R.id.imageView4);

        //Listener cuando se pulsa sobre una celda:
        cell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent i = new Intent(context, CamaraActivity.class);
                context.startActivity(i);
            }
        });

        return new RecyclerView.ViewHolder(cell) {};
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        item = position;
    }

    @Override
    public int getItemCount() {
        return 20;
    }
}
