package br.ufg.inf.eadep.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;

import br.ufg.inf.eadep.Adapter;
import br.ufg.inf.eadep.R;

public class DisciplinasActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disciplinas);
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);

        if(sharedPref.getString("email", "").equals("")){
            Intent it = new Intent(DisciplinasActivity.this, LoginActivity.class);
            startActivity(it);

        } else {
            int[] lista = new int[]{R.drawable.ic_launcher_background, R.drawable.ic_launcher_foreground,
                    R.drawable.ic_launcher_background, R.drawable.ic_launcher_foreground,
                    R.drawable.ic_launcher_background, R.drawable.ic_launcher_foreground};
            GridView grid = (GridView) findViewById(R.id.fields_grid);

            grid.setAdapter(new Adapter(this, lista));
        }
    }
}
