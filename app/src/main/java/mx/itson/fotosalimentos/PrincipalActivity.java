package mx.itson.fotosalimentos;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

public class PrincipalActivity extends AppCompatActivity {
    ArrayList<Alimento> alimentos = new ArrayList<Alimento>();

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
                startActivity(intent);
            }
        });

        crearAlimentos();

        AdaptadorAlimentos adaptador = new AdaptadorAlimentos(getApplicationContext(), alimentos);
        ListView listView = findViewById(R.id.listview);
        listView.setAdapter(adaptador);
    }

    private void crearAlimentos(){
        Alimento alimento1 = new Alimento(1, R.drawable.burger, "13:50","descripcion 1", "05/26/2019");
        Alimento alimento2 = new Alimento(2, R.drawable.salad, "18:42","descripcion 2", "05/26/2019");
        Alimento alimento3 = new Alimento(3, R.drawable.pancakes, "02:54","descripcion 3", "05/26/2019");
        Alimento alimento4 = new Alimento(4, R.drawable.rice, "36:98","descripcion 4", "05/26/2019");
        Alimento alimento5 = new Alimento(5, R.drawable.wings, "17:02","descripcion 5", "05/26/2019");

        alimentos.add(alimento1);
        alimentos.add(alimento2);
        alimentos.add(alimento3);
        alimentos.add(alimento4);
        alimentos.add(alimento5);
    }

}
