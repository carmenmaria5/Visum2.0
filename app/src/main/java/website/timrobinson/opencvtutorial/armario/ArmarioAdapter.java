package website.timrobinson.opencvtutorial.armario;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

import website.timrobinson.opencvtutorial.R;
import website.timrobinson.opencvtutorial.conjunto.ConjuntoActivity;
import website.timrobinson.opencvtutorial.modelo.Prenda;

//Adaptador que marca el comportamiento de la lista de prendas.
public class ArmarioAdapter extends RecyclerView.Adapter<ArmarioAdapter.ArmarioViewHolder> {

    //--- VARIABLES --------------------------------------------------------------------------------

    //Lista de prendas.
    List<Prenda> tPrendas;

    //Item seleccionado.
    int item;

    //Contexto de la App.
    Context context;

    //RV continente;
    RecyclerView rvArmario;

    private int item1 = 0;
    private int item2 = 0;

    int nSelect=0;

    public int getItem1() {
        return item1;
    }

    public int getItem2() {
        return item2;
    }

    public int getnSelect() {
        return nSelect;
    }

    //--- CONSTRUCTOR ------------------------------------------------------------------------------
    public ArmarioAdapter(Context context, List<Prenda> tPrendas) {
        this.context = context;
        this.tPrendas = tPrendas;
    }

    //--- MÉTODOS ----------------------------------------------------------------------------------

    //Se enlaza e inicializa la lista con el viewholder indicado, en este caso las celdas de la lista de ropa.
    @Override
    public ArmarioAdapter.ArmarioViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View cell = LayoutInflater.from(parent.getContext()).inflate(R.layout.armario_celda, parent, false);

        ImageView ivImagen = (ImageView) cell.findViewById(R.id.imageView4);
        TextView tvTitulo = (TextView) cell.findViewById(R.id.textView2);

        //Listener cuando se pulsa sobre una celda:
        cell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                //Se crea el intent.
                Intent i = new Intent(context, PrendaActivity.class);

                //Se indica que la vista es de visualización, no de edición.
                i.putExtra("EDITAR", false);
                //Se lanza la actividad.
                context.startActivity(i);
            }
        });

        return new ArmarioViewHolder(cell);
    }

    @Override
    public void onBindViewHolder(ArmarioViewHolder holder, int position) {
        item = position;

        holder.bindPrenda(tPrendas.get(item));

    }

    //Devuelve el total de items crados.
    @Override
    public int getItemCount() {
        return tPrendas.size();
    }

    //Guarda el RV que contiene la lista.
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        rvArmario = recyclerView;
    }

    //--- VIEWHOLDER -------------------------------------------------------------------------------
    public class ArmarioViewHolder extends RecyclerView.ViewHolder {

        ImageView ivImagen;
        TextView tvTitulo;

        public ArmarioViewHolder(View itemView) {
            super(itemView);
            ivImagen = (ImageView) itemView.findViewById(R.id.imageView4);
            tvTitulo = (TextView) itemView.findViewById(R.id.textView2);

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if (nSelect == 0){
                        item1 = getLayoutPosition();
                        nSelect++;
                        Toast.makeText(context, tPrendas.get(item1).getNombrePrenda()+" seleccionado (1 de 2)", Toast.LENGTH_SHORT).show();
                    }else if (nSelect == 1){
                        item2 = getLayoutPosition();
                        nSelect++;
                        Toast.makeText(context, tPrendas.get(item2).getNombrePrenda()+" seleccionado (2 de 2)", Toast.LENGTH_SHORT).show();
                    }else if(nSelect == 2){
                        item1 = getLayoutPosition();
                        item2 = 0;
                        nSelect=1;
                        Toast.makeText(context, tPrendas.get(item1).getNombrePrenda()+" seleccionado (1 de 2)", Toast.LENGTH_SHORT).show();
                    }
                    // Redraw the old selection and the new

                    notifyItemChanged(item1);

                }
            });

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {

                    Intent i = new Intent(context, PrendaActivity.class);

                    i.putExtra("PRENDA", tPrendas.get(getLayoutPosition()).getId());
                    i.putExtra("EDITAR", false);

                    context.startActivity(i);

                    return false;
                }
            });

        }

        public void bindPrenda(Prenda p) {
            ivImagen.setImageURI(p.getFotoPrenda(context));
            tvTitulo.setText(p.getNombrePrenda());
        }

    }


}
