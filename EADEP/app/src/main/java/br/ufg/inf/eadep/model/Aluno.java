package br.ufg.inf.eadep.model;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

import br.ufg.inf.eadep.DAO.FirebaseConfig;

public class Aluno {

    private String id;
    private String pathFoto;
    private String nomeCompleto;
    private String email;
    private String senha;
    private String telefone;
    private int ano;
    private int matricula;

    public void addAluno() {
        DatabaseReference firebaseRef = FirebaseConfig.getFirebase();
        firebaseRef.child("aluno").child(String.valueOf(getId())).setValue(this);
    }

    @Exclude
    public Map<String, Object> toMap() {
        HashMap<String, Object> alunoHashMap = new HashMap<>();

        alunoHashMap.put("id", getId());
        alunoHashMap.put("email", getEmail());
        alunoHashMap.put("senha", getSenha());
        alunoHashMap.put("nome", getNomeCompleto());
        alunoHashMap.put("telefone", getTelefone());
        alunoHashMap.put("matricula", getMatricula());
        alunoHashMap.put("ano", getAno());
        //alunoHashMap.put("pathFoto", getPathFoto());

        return alunoHashMap;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPathFoto() {
        return pathFoto;
    }

    public void setPathFoto(String pathFoto) {
        this.pathFoto = pathFoto;
    }

    public String getNomeCompleto() {
        return nomeCompleto;
    }

    public void setNomeCompleto(String nomeCompleto) {
        this.nomeCompleto = nomeCompleto;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public int getAno() {
        return ano;
    }

    public void setAno(int ano) {
        this.ano = ano;
    }

    public int getMatricula() {
        return matricula;
    }

    public void setMatricula(int matricula) {
        this.matricula = matricula;
    }
}
