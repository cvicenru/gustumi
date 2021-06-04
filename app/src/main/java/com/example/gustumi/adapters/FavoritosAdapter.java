package com.example.gustumi.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.gustumi.R;
import com.example.gustumi.activities.DetalleRecetaActivity;
import com.example.gustumi.model.Receta;

import java.util.List;

public class FavoritosAdapter extends RecyclerView.Adapter<FavoritosAdapter.ViewHolderReceta> {

    private static final String TAG = "RecetaAdapter";

    List<Receta> recetaList;
    private Context mContext;


    /*CONSTRUCTOR*/
    public FavoritosAdapter(Context context, List<Receta> recetaList) { //el constructor recibe como parámetros el context y la lista de recetas
        this.recetaList = recetaList;
        this.mContext = context;
    }//fin constructor


    /*------------------------MÉTODOS---------------------------*/
    @NonNull
    @Override
    /*onCreateViewHolder -> Método que nos va a crear la vista o cada una de las vistas que necesito mostrar en la pantalla*/
    public FavoritosAdapter.ViewHolderReceta onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //Creo una vista
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_result_busqueda_recetas, parent, false);//inflate: renderiza la vista. Le indico a este adapter que ocupe el layout que es el que va a representar las vistas
        FavoritosAdapter.ViewHolderReceta holder = new FavoritosAdapter.ViewHolderReceta(view);
        return holder;
    }//fin onCreateViewHolder


    @Override
    /*onBindViewHolder -> Se establecen los valores que tiene cada una de las vistas. Se encarga de coger cada una de las posiciones de la lista y pasarlas a la clase ViewHolder para que esta (ViewHolder) pinte todos los valores */
    public void onBindViewHolder(@NonNull FavoritosAdapter.ViewHolderReceta holder, final int position) {//Establezco los datos que va a tener el Textview

        final Receta receta = recetaList.get(position);//para cuando llame a cada uno de los objetos


        //holder.textViewNombreCategoria.setText(receta.getCategoria());
        holder.textViewNombreReceta.setText(receta.getNombreReceta());


        //mostrar imagen de la receta en el recyclerview
        Glide
                .with(mContext)
                .load(receta.getImagen())
                .apply(RequestOptions.circleCropTransform())//muestra las imágenes en forma de círculo
                .into(holder.imgReceta);










        /*---------Clic en el ítem del recycler para mostrar el detalle de la receta---------*/
        holder.linearLayoutRecetas.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view) {

                Intent intent = new Intent(mContext, DetalleRecetaActivity.class);

                String imagen = recetaList.get(position).getImagen();
                String nombreReceta = recetaList.get(position).getNombreReceta();
                String tiempoElaboracion = recetaList.get(position).getTiempoElaboracion();
                String ingredientes = String.valueOf(recetaList.get(position).getIngredientes());
                String elaboracion = String.valueOf(recetaList.get(position).getElaboracion());


                intent.putExtra("imagen", imagen);
                intent.putExtra("nombreReceta", nombreReceta);
                intent.putExtra("tiempoElaboracion", tiempoElaboracion);
                intent.putExtra("ingredientes", ingredientes);
                intent.putExtra("elaboracion", elaboracion);

                view.getContext().startActivity(intent);

                mContext.startActivity(intent);//necesito hacer referencia al Context, ya que no se puede con startActivity(intent) en una clase adapter
            }//fin onClick

        });
    }//fin onBindViewHolder

    @Override
    public int getItemCount() {//tamaño de la lista

        return recetaList.size();//toma el tamaño de la lista

    }//fin getItemCount


    //------------------------------Clase interna------------------------------
    public class ViewHolderReceta extends RecyclerView.ViewHolder { //Aquí instancio las vistas que necesito
        TextView textViewNombreReceta;
        LinearLayout linearLayoutRecetas;


        ImageView imgReceta;//Hace referencia a la fotografía que se muestra en el Recycler de las recetas




        /*Constructor*/
        public ViewHolderReceta(@NonNull View itemView) { //El constructor recibe una vista (View)
            super(itemView);


            textViewNombreReceta = itemView.findViewById(R.id.textViewTituloAprende);


            linearLayoutRecetas = itemView.findViewById(R.id.linearLayoutAprende);
            imgReceta = itemView.findViewById(R.id.imgReceta);//imagen de la receta en el recycler



        }
    }//fin clase ViewHolderAprende

}//fin clase RecetaAdapter