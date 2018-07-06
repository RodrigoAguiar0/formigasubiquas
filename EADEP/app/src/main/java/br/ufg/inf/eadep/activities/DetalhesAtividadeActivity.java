package br.ufg.inf.eadep.activities;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;

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
import br.ufg.inf.eadep.model.Comentario;

public class DetalhesAtividadeActivity extends AppCompatActivity{

    private List<Comentario> comments;
    private DatabaseReference firebase;
    private ValueEventListener valueEventListenerComentarios;
    private Preferences sharedPrefs;
    private ComentariosAdapter adapter;

    private ListView commentsLV;
    private Button commentBtn;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detalhe_atividade);

        commentsLV = findViewById(R.id.comentarios_lv);
        commentBtn = findViewById(R.id.comment_btn);

        listComments();
        adapter = new ComentariosAdapter(DetalhesAtividadeActivity.this, comments);
        commentsLV.setAdapter(adapter);

        commentBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }

    public void listComments(){

        sharedPrefs = new Preferences(DetalhesAtividadeActivity.this);
        String subjectTitle = sharedPrefs.getSubject();

        comments = new ArrayList<Comentario>();
        firebase = FirebaseConfig.getFirebase().child("disciplina").child(subjectTitle).child("tarefas").child(sharedPrefs.getActivityTitle());

        valueEventListenerComentarios = new ValueEventListener() {

            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                comments.clear();

                for (DataSnapshot dados : dataSnapshot.getChildren()) {
                    Comentario comentarioNovo = dataSnapshot.getValue(Comentario.class);

                    comments.add(comentarioNovo);
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
        firebase.removeEventListener(valueEventListenerComentarios);
    }

    @Override
    protected void onStart() {
        super.onStart();
        firebase.addValueEventListener(valueEventListenerComentarios);
    }
}
