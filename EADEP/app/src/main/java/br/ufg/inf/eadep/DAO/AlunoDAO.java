package br.ufg.inf.eadep.DAO;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.Exclude;

import java.util.HashMap;
import java.util.Map;

import br.ufg.inf.eadep.model.Aluno;

public class AlunoDAO {

    public void addAluno(Aluno aluno){
        DatabaseReference firebaseRef = FirebaseConfig.getFirebase();
        firebaseRef.child("aluno").child(String.valueOf(aluno.getId())).setValue(this);
    }

    @Exclude
    public Map<String, Object> toMap(Aluno aluno){
        HashMap<String, Object> alunoHashMap = new HashMap<>();

        alunoHashMap.put("email", aluno.getEmail());
        alunoHashMap.put("senha", aluno.getSenha());
        alunoHashMap.put("nome", aluno.getNomeCompleto());
        alunoHashMap.put("telefone", aluno.getTelefone());
        alunoHashMap.put("matricula", aluno.getMatricula());
        alunoHashMap.put("ano", aluno.getAno());
        alunoHashMap.put("pathFoto", aluno.getPathFoto());

        return alunoHashMap;
    }
}
