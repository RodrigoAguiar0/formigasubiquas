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
