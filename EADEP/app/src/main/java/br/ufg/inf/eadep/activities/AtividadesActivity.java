package br.ufg.inf.eadep.activities;

import android.content.Intent;
import android.os.Bundle;
import android.preference.Preference;
import android.support.v7.app.AppCompatActivity;
import android.widget.GridView;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import br.ufg.inf.eadep.adapter.AtividadesAdapter;
import br.ufg.inf.eadep.adapter.DisciplinasAdapter;
import br.ufg.inf.eadep.DAO.FirebaseConfig;
import br.ufg.inf.eadep.R;
import br.ufg.inf.eadep.helper.Preferences;
import br.ufg.inf.eadep.model.Atividade;
import br.ufg.inf.eadep.model.Disciplina;

public class AtividadesActivity extends AppCompatActivity {

    private Preferences sharedPrefs;
    private AtividadesAdapter adapter;
    private List<Atividade> atividades;

    private DatabaseReference firebase;
    private ValueEventListener valueEventListenerAtividades;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_atividades);

        Preferences sharedPref = new Preferences(AtividadesActivity.this);

        System.out.println("EMAIL: " + sharedPref.getEmail());

        String userEmail = sharedPref.getEmail();
        if(userEmail.equals("")){
            Intent it = new Intent(AtividadesActivity.this, LoginActivity.class);
            startActivity(it);

        } else {
            ListView listView = findViewById(R.id.activities_lv);

            listAtividades();
            adapter = new AtividadesAdapter(AtividadesActivity.this, atividades);
            listView.setAdapter(adapter);
        }
    }

    public void listAtividades(){

        sharedPrefs = new Preferences(AtividadesActivity.this);
        String subjectTitle = sharedPrefs.getSubject();

        atividades = new ArrayList<Atividade>();
        firebase = FirebaseConfig.getFirebase().child("disciplina").child(subjectTitle).child("tarefas");

        valueEventListenerAtividades = new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                atividades.clear();

                for(DataSnapshot dados : dataSnapshot.getChildren()){
                    Atividade atividadeNova = dados.getValue(Atividade.class);

                    atividades.add(atividadeNova);
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
        firebase.removeEventListener(valueEventListenerAtividades);
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebase.addValueEventListener(valueEventListenerAtividades);
    }
}
