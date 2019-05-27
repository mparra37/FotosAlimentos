package mx.itson.fotosalimentos;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

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

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
       LayoutInflater inflador =  LayoutInflater.from(contexto);
        View vista = inflador.inflate(R.layout.imagen_lista, null);
        Alimento alimento = alimentos.get(position);

        ImageView iv_foto = vista.findViewById(R.id.iv_foto_adap);
        TextView tv_hora = vista.findViewById(R.id.tv_hora_adap);

        tv_hora.setText(alimento.getHora());
        iv_foto.setImageResource(alimento.getImagen());


        return vista;
    }
}
