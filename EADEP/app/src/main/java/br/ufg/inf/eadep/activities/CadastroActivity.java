package br.ufg.inf.eadep.activities;

import android.os.Bundle;
import android.os.PersistableBundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import br.ufg.inf.eadep.model.Aluno;

public class CadastroActivity extends AppCompatActivity{

    private EditText etNome;
    private EditText etSenha;
    private EditText etSobrenome;
    private EditText etEmail;
    private EditText etTelefone;
    private EditText etAno;
    private EditText etMatricula;
    private Button insertBtn;

    private Aluno aluno;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState, @Nullable PersistableBundle persistentState) {
        super.onCreate(savedInstanceState, persistentState);

        etNome = (EditText)findViewById(R.id.nome_et);
        etSobrenome = (EditText)findViewById(R.id.surname_et);
        etSenha = (EditText)findViewById(R.id.pswd_edit);
        etEmail = (EditText)findViewById(R.id.email_edit);
        etTelefone = (EditText)findViewById(R.id.telefone_edit);
        etAno = (EditText)findViewById(R.id.ano_edit);
        etMatricula = (EditText)findViewById(R.id.matricula_edit);
        insertBtn = (Button)findViewById(R.id.insert_btn);

        insertBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                aluno = new Aluno();

                aluno.setNomeCompleto(etNome.getText().toString() + etSobrenome.getText().toString());
                aluno.setEmail(etEmail.getText().toString());
                aluno.setSenha(etSenha.getText().toString());
                aluno.setAno(Integer.parseInt(etAno.getText().toString()));
                aluno.setMatricula(Integer.parseInt(etMatricula.getText().toString()));
                aluno.setTelefone(etTelefone.getText().toString());


            }
        });
    }
}
