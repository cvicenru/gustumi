package com.example.gustumi.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.gustumi.R;
import com.example.gustumi.model.Aprende;


import java.util.List;

public class AprendeAdapter extends RecyclerView.Adapter<AprendeAdapter.ViewHolderAprende> {


    List<Aprende> aprendeList;
    private Context mContext;


    /*CONSTRUCTOR*/
    public AprendeAdapter(Context context, List<Aprende> aprendeList) { //el constructor recibe como parámetros el context y la lista de aprende
        this.aprendeList = aprendeList;
        this.mContext = context;
    }//fin constructor


    /*------------------------MÉTODOS---------------------------*/
    @NonNull
    @Override
    /*onCreateViewHolder -> Método que nos va a crear la vista o cada una de las vistas que necesito mostrar en la pantalla*/
    public ViewHolderAprende onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        //Creo una vista
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.view_aprende, parent, false);//inflate: renderiza la vista. Le indico a este adapter que ocupe el layout que es el que va a representar las vistas
        ViewHolderAprende holder = new ViewHolderAprende(view);
        return holder;
    }//fin onCreateViewHolder


    @Override
    /*onBindViewHolder -> Se establecen los valores que tiene cada una de las vistas. Se encarga de coger cada una de las posiciones de la lista y pasarlas a la clase ViewHolder para que esta (ViewHolder) pinte todos los valores */
    public void onBindViewHolder(@NonNull ViewHolderAprende holder, final int position) {//Establezco los datos que va a tener el Textview

        final Aprende aprende = aprendeList.get(position);//para cuando llame a cada uno de los objetos


        holder.textViewTituloAprende.setText(aprende.getTitulo());
        holder.textViewContenidoAprende.setText(aprende.getContenido());


        Glide
                .with(mContext)
                .load(aprende.getImgAprende1())
                //.apply(RequestOptions.circleCropTransform())//muestra las imágenes en forma de cículo
                .into(holder.imgAprende1);

    }//fin onBindViewHolder

    @Override
    public int getItemCount() {//tamaño de la lista

        return aprendeList.size();//toma el tamaño de la lista

    }//fin getItemCount


    //------------------------------Clase interna------------------------------
    public class ViewHolderAprende extends RecyclerView.ViewHolder { //Aquí instancio las vistas que necesito
        TextView textViewTituloAprende, textViewContenidoAprende; //textViewimg1, textViewimg2;
        LinearLayout linearLayoutAprende;


        ImageView imgAprende1;//, imgAprende2;//Hace referencia a la fotografía que se muestra en el Recycler


        /*Constructor*/
        public ViewHolderAprende(@NonNull View itemView) { //El constructor recibe una vista (View)
            super(itemView);


            textViewTituloAprende = itemView.findViewById(R.id.textViewTituloAprende);
            textViewContenidoAprende = itemView.findViewById(R.id.textViewContenidoAprende);
            //textViewimg1 = itemView.findViewById(R.id.textViewimg1);
            ///textViewimg2 = itemView.findViewById(R.id.textViewimg2);

            linearLayoutAprende = itemView.findViewById(R.id.linearLayoutAprende);


            imgAprende1 = itemView.findViewById(R.id.imgAprende1);
            //imgAprende2 = itemView.findViewById(R.id.imgAprende2);


        }
    }//fin clase ViewHolderAprende

}//fin clase AprendeAdapter
