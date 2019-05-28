package mx.itson.fotosalimentos;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import java.io.File;
import java.util.ArrayList;

public class AdaptadorAlimentos extends BaseAdapter {
    ArrayList<Alimento> alimentos = new ArrayList<Alimento>();
    Context contexto;

    public AdaptadorAlimentos(Context context, ArrayList<Alimento> alimentos){
        contexto = context;
        this.alimentos = alimentos;
    }

    @Override
    public int getCount() {
        return alimentos.size();
    }

    @Override
    public Object getItem(int position) {
        return alimentos.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }
    String path;
    Alimento alimento;
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
       LayoutInflater inflador =  LayoutInflater.from(contexto);
        View vista = inflador.inflate(R.layout.imagen_lista, null);
        alimento = alimentos.get(position);
        path = alimento.getPath();
        ImageView iv_foto = vista.findViewById(R.id.iv_foto_adap);
        TextView tv_hora = vista.findViewById(R.id.tv_hora_adap);
        ImageView btn_eliminar = vista.findViewById(R.id.btn_eliminar_foto);

        btn_eliminar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                eliminar(position,v);
//                Toast.makeText(contexto, position+" fue presionado", Toast.LENGTH_SHORT).show();

                //eliminar(path);
            }
        });
        String nombre = alimento.getNombre();
        String hora = nombre.substring(0,2) + ":" +nombre.substring(3,5);
        tv_hora.setText(hora);
        //iv_foto.setImageResource(alimento.getImagen());
        iv_foto.setImageBitmap(alimento.getImagen());

        return vista;
    }

    private void eliminar(final int position,View v){

        AlertDialog.Builder alerta = new AlertDialog.Builder(v.getRootView().getContext());
        alerta.setMessage("¿Seguro que deseas eliminar la fotografía?");

        alerta.setPositiveButton("Eliminar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                Alimento al = alimentos.get(position);
                //Toast.makeText(contexto, al.getPath(), Toast.LENGTH_LONG).show();
                File img = new File(al.getPath());
                if (img.exists()){
                    img.delete();

                }
                alimentos.remove(al);
                notifyDataSetChanged();
            }
        });

        alerta.setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        AlertDialog alertador = alerta.create();
        alertador.show();
//        alerta.show();




    }
}
