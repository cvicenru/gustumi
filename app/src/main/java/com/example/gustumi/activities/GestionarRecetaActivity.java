package com.example.gustumi.activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.example.gustumi.R;
import com.google.android.gms.tasks.Continuation;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.StorageTask;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GestionarRecetaActivity extends AppCompatActivity {


    FirebaseFirestore firebaseFirestore;
    //StorageReference storageReference; //Contendrá la referencia del almacenamiento

    private Uri imagenUri;

    private final int CODIGO_INTENT_IMAGEN = 1;//código utilizado cuando se lance la intención para cargar la imagen

    Bitmap imagenBitmap;

    EditText editTextNombreReceta,
            editTextTiempoElaboracion,
            editTextIngredientes,
            editTextElaboracion;

    ImageView imgFotoReceta;

    Button btnSeleccionarImg,
            btnCrearReceta,
            btnMostrarRecetas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.gestionar_receta_activity_layout);

        firebaseFirestore = FirebaseFirestore.getInstance();
        //storageReference = FirebaseStorage.getInstance().getReference();//referencia indica dónde se guardará la imagen de la receta

        imgFotoReceta = (ImageView) findViewById(R.id.imgFotoReceta);

        editTextNombreReceta = findViewById(R.id.editTextNombreReceta);
        editTextTiempoElaboracion = findViewById(R.id.editTextTiempoElaboracion);
        editTextIngredientes = findViewById(R.id.editTextIngredientes);
        editTextElaboracion = findViewById(R.id.editTextElaboracion);

        /*BOTÓN CARGAR IMAGEN DE RECETA*/
        btnSeleccionarImg = findViewById(R.id.btnSeleccionarImg);
        btnSeleccionarImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                seleccionarImagenReceta();
            }
        });

        /*BOTÓN MOSTRAR CREAR RECETA*/
        btnCrearReceta = findViewById(R.id.btnCrearReceta);
        btnCrearReceta.setOnClickListener(new View.OnClickListener() {//cuando el usuario presione el botón se crearán los datos en la base de datos
            @Override
            public void onClick(View v) {//cuando pulse en crear receta se creará la receta en la BD
                crearReceta();
            }
        });

        /*BOTÓN MOSTRAR DATOS RECETA*/
        btnMostrarRecetas = findViewById(R.id.btnMostrarRecetas);
        btnMostrarRecetas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(GestionarRecetaActivity.this, MostrarRecetasActivity.class));
            }
        });


    }//fin onCreate

    /*-------MÉTODOS-------*/

    /*CARGAR IMAGEN DE LA RECETA*/
    /* seleccionarImagenReceta -> abre la galería para seleccionar la imagen. Abre el intent predefinido  ACTION_GET_CONTENT,
    filtrando por el tipo imagen con cualquier extensión y abre la actividad con el identificador CODIGO_INTENT_IMAGEN que se había definido. */
    private void seleccionarImagenReceta() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Seleccionar imagen"), CODIGO_INTENT_IMAGEN);
    }//fin seleccionarImagenReceta


    /*onActivityResult -> Sabremos si el usuario ha seleccionado la foto. Procesa las imágenes que vienen de CODIGO_INTENT_IMAGEN. Carga el fichero en un Bitmap para mostrarlo en el ImageView*/
    protected void onActivityResult(int codigoSolicitud, int codigoResultado, Intent intentImagenDevuelta) {
        super.onActivityResult(codigoSolicitud, codigoResultado, intentImagenDevuelta);

        Uri uriImgseleccionada;

        switch (codigoSolicitud) {
            case CODIGO_INTENT_IMAGEN:
                if (codigoResultado == Activity.RESULT_OK) {
                    uriImgseleccionada = intentImagenDevuelta.getData();
                    String stringRutaSeleccionada = uriImgseleccionada.getPath();
                    if (codigoSolicitud == CODIGO_INTENT_IMAGEN) {

                        if (stringRutaSeleccionada != null) {
                            InputStream flujoEntrada = null;
                            try {
                                flujoEntrada = getContentResolver().openInputStream(
                                        uriImgseleccionada);
                            } catch (FileNotFoundException e) {
                                e.printStackTrace();
                            }

                            // Transforma la URI de la imagen a flujo de entrada y este a un Bitmap
                            imagenBitmap = BitmapFactory.decodeStream(flujoEntrada);

                            // Pone el bitmap en el ImageView de la foto de la receta
                            ImageView imageView = (ImageView) findViewById(R.id.imgFotoReceta);
                            imageView.setImageBitmap(imagenBitmap);


                        }//fin if
                    }//fin if
                }//fin if
                break;
        }//fin switch
    }// fin onActivityResult


    private void crearReceta() {
        //Obtengo los datos que introduce el usuario:

        ////imagenBitmap = imgFotoReceta.getDrawingCache(true);

        String crearNombreReceta = editTextNombreReceta.getText().toString();

        String crearTiempoElaboracion = editTextTiempoElaboracion.getText().toString();

        String ingredientesInput = editTextIngredientes.getText().toString();
        String[] ingredientesArray = ingredientesInput.split("\\s*,\\s*");
        List<String> ingredientes = Arrays.asList(ingredientesArray);

        String elaboracionInput = editTextElaboracion.getText().toString();
        String[] elaboracionArray = elaboracionInput.split("\\s*,\\s*");
        List<String> elaboracion = Arrays.asList(elaboracionArray);


        if (crearNombreReceta.isEmpty()) {
            mostrarErrores(editTextNombreReceta, "Falta nombre de receta.");
        } else if (crearTiempoElaboracion.isEmpty()) {
            mostrarErrores(editTextTiempoElaboracion, "Falta tiempo de elaboracion.");
        } else if (ingredientesInput.isEmpty()) {
            mostrarErrores(editTextIngredientes, "Añade ingredientes.");
        } else if (elaboracionInput.isEmpty()) {
            mostrarErrores(editTextElaboracion, "Añade el proceso de elaboración.");
        } else {//cuando los campos se han rellenado:

            Map<String, Object> map = new HashMap<>();//creo el mapa para introducir los valores
            //establezco los atributos con sus valores
            ///// map.put("imagen", imagenBitmap);
            map.put("nombreReceta", crearNombreReceta);
            map.put("tiempoElaboracion", crearTiempoElaboracion);
            map.put("ingredientes", ingredientes);
            map.put("elaboracion", elaboracion);


            //firebaseFirestore.collection("Recetas").document("receta85").set(map);//elijo la opción collection con parámetros para que no se cree un ID automático | con set se crean los datos en la BD
            firebaseFirestore.collection("Recetas").add(map).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {//creo el documento con ID automático
                @Override
                public void onSuccess(DocumentReference documentReference) {//si es correcto
                    //vacío los campos del formulario
                    editTextNombreReceta.setText("");
                    editTextTiempoElaboracion.setText("");
                    editTextIngredientes.setText("");
                    editTextElaboracion.setText("");
                    Toast.makeText(GestionarRecetaActivity.this, "La receta se ha creado correctamente", Toast.LENGTH_SHORT).show();
                }
            }).addOnFailureListener(new OnFailureListener() {//si la receta no se ha creado
                @Override
                public void onFailure(@NonNull Exception e) {
                    Toast.makeText(GestionarRecetaActivity.this, "La receta no se ha podido crear", Toast.LENGTH_SHORT).show();
                }
            });
        }
    }//fin crearReceta()


    /*MOSTRAR ERRORES*/
    private void mostrarErrores(EditText editText, String string) {
        editText.setError(string);//le pasamos el String (el mensaje)
        editText.requestFocus();//para que el mensaje se ponga al lado
    }//fin mostrarErrores()

}//fin clase