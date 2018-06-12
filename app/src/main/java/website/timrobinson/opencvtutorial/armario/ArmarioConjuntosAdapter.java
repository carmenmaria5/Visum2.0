package website.timrobinson.opencvtutorial.armario;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.UnsupportedEncodingException;
import java.util.List;

import website.timrobinson.opencvtutorial.R;
import website.timrobinson.opencvtutorial.modelo.Conjunto;
import website.timrobinson.opencvtutorial.modelo.Prenda;
import website.timrobinson.opencvtutorial.persistencia.Bd;

public class ArmarioConjuntosAdapter extends RecyclerView.Adapter<ArmarioConjuntosAdapter.ArmarioViewHolder> {

    //--- VARIABLES --------------------------------------------------------------------------------

    //Lista de prendas.
    List<Conjunto> tConjuntos;

    //Item seleccionado.
    int item;

    //Contexto de la App.
    Context context;

    //RV continente;
    RecyclerView rvArmario;

    //--- CONSTRUCTOR ------------------------------------------------------------------------------
    public ArmarioConjuntosAdapter(Context context, List<Conjunto> tConjuntos) {
        this.context = context;
        this.tConjuntos = tConjuntos;
    }

    //--- MÉTODOS ----------------------------------------------------------------------------------

    //Se enlaza e inicializa la lista con el viewholder indicado, en este caso las celdas de la lista de ropa.
    @Override
    public ArmarioConjuntosAdapter.ArmarioViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View cell = LayoutInflater.from(parent.getContext()).inflate(R.layout.armario_conjuntos_celda, parent, false);

        ImageView ivImagen1 = (ImageView) cell.findViewById(R.id.ivPrenda1);
        ImageView ivImagen2 = (ImageView) cell.findViewById(R.id.ivPrenda2);
        TextView tvTitulo = (TextView) cell.findViewById(R.id.tvConjunto);

//        //Listener cuando se pulsa sobre una celda:
//        cell.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//
//                //Se crea el intent.
//                Intent i = new Intent(context, ConjuntoActivity.class);
//
//                //Se indica que la vista es de visualización, no de edición.
//                i.putExtra("EDITAR", false);
//
//                //Se lanza la actividad.
//                context.startActivity(i);
//            }
//        });

        return new ArmarioConjuntosAdapter.ArmarioViewHolder(cell);
    }


    //ACTUALIZAR:
    public void actualizar(List<Conjunto> tConjuntos) {
        this.tConjuntos.clear();
        this.tConjuntos.addAll(tConjuntos);
        notifyDataSetChanged();
    }

    //Devuelve el total de items crados.
    @Override
    public int getItemCount() {
        return tConjuntos.size();
    }

    //Guarda el RV que contiene la lista.
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        rvArmario = recyclerView;
    }

    @Override
    public void onBindViewHolder(ArmarioViewHolder holder, int position) {
        item = position;

        holder.bindConjunto(tConjuntos.get(item));


    }

    //--- VIEWHOLDER -------------------------------------------------------------------------------
    public class ArmarioViewHolder extends RecyclerView.ViewHolder {

        ImageView ivImagen1;
        ImageView ivImagen2;
        ImageView ivComb;
        TextView tvTitulo;

        public ArmarioViewHolder(View itemView) {
            super(itemView);
            ivImagen1 = (ImageView) itemView.findViewById(R.id.ivPrenda1);
            ivImagen2 = (ImageView) itemView.findViewById(R.id.ivPrenda2);
            ivComb = (ImageView) itemView.findViewById(R.id.ivCombCell);
            tvTitulo = (TextView) itemView.findViewById(R.id.tvConjunto);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {

                    Intent i = new Intent(context, ConjuntoActivity.class);

                    i.putExtra("CONJUNTO", tConjuntos.get(getLayoutPosition()).getId());
                    i.putExtra("EDITAR", false);

                    context.startActivity(i);

                    return true;
                }
            });

        }

        public void bindConjunto(Conjunto c) {

            Prenda p1 = null;
            Prenda p2 = null;


            try {
                p1 = Bd.getPrendaById(context, c.getIdPrenda1());
                p2 = Bd.getPrendaById(context, c.getIdPrenda2());
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

            ivImagen1.setImageURI(p1.getFotoPrenda(context));
            ivImagen2.setImageURI(p2.getFotoPrenda(context));
            tvTitulo.setText(c.getDescripcion());

            if (c.getCombina().equals("S")){
                ivComb.setImageResource(R.mipmap.ic_diag_bien);
            }else {
                ivComb.setImageResource(R.mipmap.ic_diag_mal);
            }

        }

    }
}