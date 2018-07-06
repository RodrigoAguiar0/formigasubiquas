package br.ufg.inf.eadep.model;

import android.provider.ContactsContract;
import android.widget.Toast;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

import br.ufg.inf.eadep.DAO.FirebaseConfig;

public class Disciplina {

    private String id;
    private String nome;

    // precisa mudar para uma entidade, quando o professor for uma entidade cadastrada
    private String professor;

    // atributos para as funções de comunicação com o banco de dados abaixo(colocar conforme necessário)
    private DatabaseReference firebase;
    private ValueEventListener valueEventListenerDisciplinas;

    // Funções de adição (essas duas abaixo), colocar no onclick do botão de adição
   /*
    public void onClick() {
        Disciplina disciplina = new Disciplina();
        disciplina.setNome(<editText>etNome.getText);
        disciplina.setProfessor(<editText>etProfessor.getText);

        addDisciplina(disciplina);
    }

    public boolean addDisciplina(Disciplina disciplina) {
        try {
            firebase = FirebaseConfig.getFirebase().child(<nome da 'tabela'>"disciplina");
            firebase.child(disciplina.getNome()).setValue(this);// essa linha faz realmente o cadastro

            Toast.makeText(<Nome da classe da activity .this>Contexto.this, "Disciplina cadastrada com sucesso", Toast.LENGTH_LONG).show();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    // Funções de listagem, colocar no onclick do botão de listagem(?)
    public void listDisciplinas(){

        final List<Disciplina> disciplinas = new ArrayList<>();
        firebase = FirebaseConfig.getFirebase().child("disciplina");

        valueEventListenerDisciplinas = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                disciplinas.clear();

                for(DataSnapshot dados : dataSnapshot.getChildren()){
                    Disciplina disciplinaNova = dados.getValue(Disciplina.class);

                    disciplinas.add(disciplinaNova);
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
    }

    onStop<deve ser override>{
        super.onStop();
        firebase.removeEventListener(valueEventListenerDisciplinas);
    }

    onStart<deve ser override> {
        super.onStart();

        firebase.addValueEventListener(valueEventListenerDisciplinas);
    }
*/

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getProfessor() {
        return professor;
    }

    public void setProfessor(String professor) {
        this.professor = professor;
    }
}
