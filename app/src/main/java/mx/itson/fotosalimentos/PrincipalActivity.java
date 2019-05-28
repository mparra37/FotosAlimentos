package mx.itson.fotosalimentos;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

public class PrincipalActivity extends AppCompatActivity {
    ArrayList<Alimento> alimentos = new ArrayList<Alimento>();
    String fecha_str, nombre_archivo, fecha_view;
    Uri img_uri;
    Date fecha;
    TextView tv_fecha;
    AdaptadorAlimentos adaptador;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_principal);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
//                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
//                        .setAction("Action", null).show();
                Intent intent = new Intent(getApplicationContext(), AgregarFotoActivity.class);
                intent.putExtra("fecha",fecha_str);
                startActivity(intent);
            }
        });
        tv_fecha = findViewById(R.id.tv_fecha);

        fecha = new Date();
        estableceFecha(fecha);

        adaptador = new AdaptadorAlimentos(getApplicationContext(), alimentos);
        ListView listView = findViewById(R.id.listview);
        listView.setAdapter(adaptador);

        leerImagenes();



    }

    private void estableceFecha(Date fecha){
        SimpleDateFormat formatoFecha = new SimpleDateFormat("yyyy-MM-dd");
        fecha_str = formatoFecha.format(fecha);

        SimpleDateFormat formatoNombre = new SimpleDateFormat("HH-mm-ss-SSS");
        nombre_archivo = formatoNombre.format(fecha);

        SimpleDateFormat formatoFechaView = new SimpleDateFormat("dd/MM/yyyy");
        fecha_view = formatoFechaView.format(fecha);

        tv_fecha.setText(fecha_view);

//        //Establece la hora en el botón
//        SimpleDateFormat formatoHora = new SimpleDateFormat("HH:mm");
//        String hora = formatoHora.format(fecha);
//        btn_hora.setText(hora);
    }

    private void crearAlimentos(){
//        Alimento alimento1 = new Alimento(1, R.drawable.burger, "13:50","descripcion 1", "05/26/2019");
//        Alimento alimento2 = new Alimento(2, R.drawable.salad, "18:42","descripcion 2", "05/26/2019");
//        Alimento alimento3 = new Alimento(3, R.drawable.pancakes, "02:54","descripcion 3", "05/26/2019");
//        Alimento alimento4 = new Alimento(4, R.drawable.rice, "36:98","descripcion 4", "05/26/2019");
//        Alimento alimento5 = new Alimento(5, R.drawable.wings, "17:02","descripcion 5", "05/26/2019");
//
//        alimentos.add(alimento1);
//        alimentos.add(alimento2);
//        alimentos.add(alimento3);
//        alimentos.add(alimento4);
//        alimentos.add(alimento5);
    }

    private void leerImagenes(){
        alimentos.clear();

        File carpeta = new File(ubicacionCarpeta());

        if (carpeta.exists()){
            File[] archivos = carpeta.listFiles();
            if (archivos!= null){
                for(File archivo: archivos){
                    if (new ImageFileFilter(archivo).accept(archivo)){
                        leerImagen(archivo);
                    }
                }
            }
        }
        adaptador.notifyDataSetChanged();
    }

    private void leerImagen(File imagen){
        BitmapFactory.Options bmOptions = new BitmapFactory.Options();
        bmOptions.inSampleSize = 4;
        Bitmap bitmap = BitmapFactory.decodeFile(imagen.getAbsolutePath(), bmOptions);
        String nombre = imagen.getName();
        String hora = nombre.substring(0,2) +":" + nombre.substring(3,5);
        Alimento alimento = new Alimento( bitmap, nombre,hora,imagen.getAbsolutePath());

        alimentos.add(alimento);
    }

    private String ubicacionCarpeta(){
        File carpeta = new File(Environment.getExternalStorageDirectory(),"PotroSaludable");
        if (!carpeta.exists()){
            carpeta.mkdir();
        }
        File dia = new File(carpeta, fecha_str);
        if(!dia.exists()){
            dia.mkdir();
        }
        return dia.getAbsolutePath();
    }



    public void fecha_izq(View v){
        Calendar cal = Calendar.getInstance();
        cal.setTime(fecha);
        cal.add(Calendar.DATE, -1); //minus number would decrement the days
        fecha = cal.getTime();

        estableceFecha(fecha);
        leerImagenes();
//        SimpleDateFormat formatoFechaView = new SimpleDateFormat("dd/MM/yyyy");
//        fecha_view = formatoFechaView.format(fecha);
//        tv_fecha.setText(fecha_view);

    }

    public void fecha_der(View v){
        Calendar cal = Calendar.getInstance();
        cal.setTime(fecha);
        cal.add(Calendar.DATE, 1); //minus number would decrement the days
        fecha = cal.getTime();

        estableceFecha(fecha);
        leerImagenes();
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        leerImagenes();

    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//
//        adaptador.notifyDataSetChanged();
//    }
}
