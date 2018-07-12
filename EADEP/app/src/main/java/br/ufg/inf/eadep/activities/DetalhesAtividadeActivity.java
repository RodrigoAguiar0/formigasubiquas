package br.ufg.inf.eadep.activities;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import br.ufg.inf.eadep.DAO.FirebaseConfig;
import br.ufg.inf.eadep.R;
import br.ufg.inf.eadep.adapter.ComentariosAdapter;
import br.ufg.inf.eadep.helper.Preferences;
import br.ufg.inf.eadep.model.Atividade;
import br.ufg.inf.eadep.model.Comentario;

public class DetalhesAtividadeActivity extends AppCompatActivity{

    private List<Comentario> comments;
    private List<Atividade> atividades;
    private DatabaseReference firebase;
    private ValueEventListener valueEventListenerComentarios;
    private ValueEventListener valueEventListenerAtividades;
    private Preferences sharedPrefs;
    private ComentariosAdapter adapter;

    private ListView commentsLV;
    private Button commentBtn;
    private TextView activityTitle;
    private TextView activityDescription;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_atividade);

        commentsLV = findViewById(R.id.comentarios_lv);
        commentBtn = findViewById(R.id.comment_btn);
        activityTitle = findViewById(R.id.activity_title);
        activityDescription = findViewById(R.id.activity_description);

        getAtividade();
        //adapter = new ComentariosAdapter(DetalhesAtividadeActivity.this, comments);
        //commentsLV.setAdapter(adapter);
/*
        commentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });*/

        ImageButton profileBtn = findViewById(R.id.profile_btn);

        profileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent it = new Intent(DetalhesAtividadeActivity.this , EdicaoActivity.class);
                startActivity(it);
            }
        });
    }

    public void getAtividade(){

        sharedPrefs = new Preferences(DetalhesAtividadeActivity.this);
        String subjectTitle = sharedPrefs.getSubject();

        atividades = new ArrayList<Atividade>();
        firebase = FirebaseConfig.getFirebase().child("disciplina").child(subjectTitle).child("tarefas");

        valueEventListenerAtividades = new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {

                atividades.clear();


                for(DataSnapshot dados : dataSnapshot.getChildren()){
                    Atividade atividadeNova = dados.getValue(Atividade.class);

                    if(atividadeNova.getTitulo().equals(sharedPrefs.getActivityTitle())){
                        activityTitle.setText(atividadeNova.getTitulo());
                        activityDescription.setText(atividadeNova.getDescricao());
                    }
                    atividades.add(atividadeNova);
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
    }

    public void listComments(){

        sharedPrefs = new Preferences(DetalhesAtividadeActivity.this);
        String subjectTitle = sharedPrefs.getSubject();

        comments = new ArrayList<>();
        firebase = FirebaseConfig.getFirebase().child("disciplina").child(subjectTitle)
                .child("tarefas").child(sharedPrefs.getActivityTitle())
                .child("zcomentarios");

        valueEventListenerComentarios = new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                comments.clear();

                Comentario comentario = null;

                for(DataSnapshot dados : dataSnapshot.getChildren()){
                    comentario = dados.getValue(Comentario.class);

                    comments.add(comentario);
                }
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
        //firebase.removeEventListener(valueEventListenerComentarios);
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebase.addValueEventListener(valueEventListenerAtividades);
        //firebase.addValueEventListener(valueEventListenerComentarios);
    }
}
