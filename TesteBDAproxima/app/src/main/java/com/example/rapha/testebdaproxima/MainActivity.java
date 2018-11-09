package com.example.rapha.testebdaproxima;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {

    private EditText edtEmail;
    private EditText edtSenha;
    private Button btnLogin;
    private Button btnCadastro;
    private FirebaseAuth autenticacao;
    private Usuarios usuarios;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtEmail = (EditText) findViewById(R.id.login);
        edtSenha = (EditText) findViewById(R.id.senha);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnCadastro = (Button) findViewById(R.id.btnCadastro);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!edtEmail.getText().toString().equals("") && !edtSenha.getText().toString().equals("")){
                    usuarios = new Usuarios();
                    usuarios.setEmail(edtEmail.getText().toString());
                    usuarios.setSenha(edtSenha.getText().toString());

                    validarLogin();

                }else{
                    Toast.makeText(MainActivity.this, "Preencha os campos de e-mail e senha",Toast.LENGTH_LONG).show();
                }
            }
        });

        btnCadastro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                abrirTelaCadastro();
            }
        });


    }

    private void validarLogin(){
        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.signInWithEmailAndPassword(usuarios.getEmail(),usuarios.getSenha()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    abrirTelaPrincial();
                    Toast.makeText(MainActivity.this, "Login efetuado com sucesso!",Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private void abrirTelaPrincial(){
        Intent intent = new Intent(MainActivity.this,TelaPrincial.class);
        startActivity(intent);
    }

    private void abrirTelaCadastro(){
        Intent intent = new Intent(MainActivity.this,Cadastro.class);
        startActivity(intent);
    }


}
