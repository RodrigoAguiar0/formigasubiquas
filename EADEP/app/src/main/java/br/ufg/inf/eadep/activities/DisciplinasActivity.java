package br.ufg.inf.eadep.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.GridView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import br.ufg.inf.eadep.adapter.DisciplinasAdapter;
import br.ufg.inf.eadep.DAO.FirebaseConfig;
import br.ufg.inf.eadep.R;
import br.ufg.inf.eadep.helper.Preferences;
import br.ufg.inf.eadep.model.Disciplina;

public class DisciplinasActivity extends AppCompatActivity {

    private DisciplinasAdapter adapter;
    private GridView gridDisciplinas;
    private List<Disciplina> disciplinas;
    private int[] icons = new int[]{R.drawable.ic_launcher_background};

    private DatabaseReference firebase;
    private ValueEventListener valueEventListenerDisciplinas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_disciplinas);
        Preferences sharedPref = new Preferences(DisciplinasActivity.this);

        System.out.println("EMAIL: " + sharedPref.getEmail());

        String userEmail = sharedPref.getEmail();
        if(userEmail.equals("")){
            Intent it = new Intent(DisciplinasActivity.this, LoginActivity.class);
            startActivity(it);

        } else {
            GridView grid = findViewById(R.id.fields_grid);

            listDisciplinas();
            adapter = new DisciplinasAdapter(DisciplinasActivity.this, icons, disciplinas);
            grid.setAdapter(adapter);
        }
    }

    public void listDisciplinas(){

        disciplinas = new ArrayList<Disciplina>();
        firebase = FirebaseConfig.getFirebase().child("disciplina");

        valueEventListenerDisciplinas = new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                disciplinas.clear();

                for(DataSnapshot dados : dataSnapshot.getChildren()){
                    Disciplina disciplinaNova = dados.getValue(Disciplina.class);

                    disciplinas.add(disciplinaNova);
                }

                adapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
    }

    @Override
    protected void onStop() {
        super.onStop();
        if(firebase != null){

            firebase.removeEventListener(valueEventListenerDisciplinas);
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(firebase != null){

            firebase.addValueEventListener(valueEventListenerDisciplinas);
        }
    }
}
