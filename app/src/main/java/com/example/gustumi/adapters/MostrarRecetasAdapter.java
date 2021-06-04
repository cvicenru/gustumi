package com.example.gustumi.adapters;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.example.gustumi.R;
import com.example.gustumi.activities.DetalleRecetaActivity;
import com.example.gustumi.activities.EditarRecetaActivity;
import com.example.gustumi.model.Receta;
import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;


public class MostrarRecetasAdapter extends FirestoreRecyclerAdapter<Receta, MostrarRecetasAdapter.ViewHolder> {


    Activity activity;
    FirebaseFirestore firebaseFirestore;


    /*CONSTRUCTOR*/
    public MostrarRecetasAdapter(@NonNull FirestoreRecyclerOptions<Receta> options, Activity activity) {//recibe como parámetro la consulta (conjunto de opciones de los datos a mostrar) y el activity
        super(options);
        this.activity = activity;
        firebaseFirestore = FirebaseFirestore.getInstance();
    }

    @Override
    /*onBindViewHolder -> Se establecen los valores que tiene cada una de las vistas. Se encarga de coger cada una de las posiciones de la lista y pasarlas a la clase ViewHolder para que esta (ViewHolder) pinte todos los valores */
    protected void onBindViewHolder(@NonNull ViewHolder holder, int position, @NonNull Receta receta) {

        DocumentSnapshot recetaDocumentSnapshot = getSnapshots().getSnapshot(holder.getAdapterPosition());//El índice de la receta lo obtengo con holder.getAdapterPosition()

        final String idDocumentReceta = recetaDocumentSnapshot.getId();//Para obtener el ID del documento a editar

        //mostrar imagen de recetas en recycler
        Glide
                .with(activity)
                .load(receta.getImagen())
                .apply(RequestOptions.circleCropTransform())//muestra las imágenes en forma de círculo
                .into(holder.fotoRecetas);


        holder.textViewMostarNombreReceta.setText(receta.getNombreReceta());
        holder.textViewMostarTiempoReceta.setText(receta.getTiempoElaboracion());

        /*BOTÓN EDITAR RECETA: CUANDO SE PULSE EL BOTÓN REDIRIGE AL USUARIO AL FORMULARIO PARA EDITAR LA RECETA*/
        holder.btnEditarReceta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(activity, EditarRecetaActivity.class);
                intent.putExtra("recetaId", idDocumentReceta);//paso con Extra el id del documento que se va a editar a la clase EditarRecetaActivity
                activity.startActivity(intent);
            }
        });

        /*BOTÓN ELIMINAR: ELIMINA EL DOCUMENTO (LA RECETA) DE LA RECETA DE LA BD*/
        holder.btnEliminarReceta.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {//En Recetas eliminará la receta que el usuario desee (obtengo el id de la receta)
                firebaseFirestore.collection("Recetas").document(idDocumentReceta).delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void aVoid) {//Si se ha eliminado correctamente
                        Toast.makeText(activity, "La receta se ha eliminado correctamente", Toast.LENGTH_SHORT).show();
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(activity, "La receta no se ha podido eliminar", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });


    }//fin onBindViewHolder

    @NonNull
    @Override
    /*onCreateViewHolder -> Método que nos va a crear la vista o cada una de las vistas que necesito mostrar en la pantalla*/
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.view_mostrar_recetas, viewGroup, false);//Le digo al adapter que ocupe el view_mostrar_recetas que es el xml que me muestra la vista
        return new ViewHolder(view);

    }//fin onCreateViewHolder

    //------------------------------Clase interna------------------------------
    public class ViewHolder extends RecyclerView.ViewHolder { //Aquí instancio las vistas que necesito

        ImageView fotoRecetas;

        TextView textViewMostarNombreReceta,
                textViewMostarTiempoReceta;

        Button btnEditarReceta, btnEliminarReceta;


        /*Constructor*/
        public ViewHolder(@NonNull View itemView) { //El constructor recibe una vista (View)
            super(itemView);

            fotoRecetas = itemView.findViewById(R.id.fotoRecetas);

            textViewMostarNombreReceta = itemView.findViewById(R.id.textViewMostarNombreReceta);
            textViewMostarTiempoReceta = itemView.findViewById(R.id.textViewMostarTiempoReceta);

            btnEditarReceta = itemView.findViewById(R.id.btnEditarReceta);
            btnEliminarReceta = itemView.findViewById(R.id.btnEliminarReceta);


        }
    }//fin clase ViewHolder

}//fin clase RecetaAdapter