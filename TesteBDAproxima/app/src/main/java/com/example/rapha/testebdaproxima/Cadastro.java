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
import com.google.firebase.auth.FirebaseAuthInvalidCredentialsException;
import com.google.firebase.auth.FirebaseAuthUserCollisionException;
import com.google.firebase.auth.FirebaseAuthWeakPasswordException;
import com.google.firebase.auth.FirebaseUser;

public class Cadastro extends AppCompatActivity {

    private EditText txtEmail;
    private EditText txtSenha;
    private Button btnCadastrar;
    private FirebaseAuth autenticacao;
    private Usuarios usuarios;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastro);

        txtEmail = (EditText) findViewById(R.id.txtEmail);
        txtSenha = (EditText) findViewById(R.id.txtSenha);
        btnCadastrar = (Button) findViewById(R.id.btnCadastrar);

        btnCadastrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                usuarios = new Usuarios();
                usuarios.setEmail(txtEmail.getText().toString());
                usuarios.setSenha(txtSenha.getText().toString());

                cadastrarUsuario();
            }
        });
    }

    private void cadastrarUsuario() {

        autenticacao = ConfiguracaoFirebase.getFirebaseAutenticacao();
        autenticacao.createUserWithEmailAndPassword(usuarios.getEmail(),
                usuarios.getSenha()).addOnCompleteListener(Cadastro.this, new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {

                    Toast.makeText(Cadastro.this, "Usuário cadastrado com sucesso!", Toast.LENGTH_LONG).show();

                    /*String identificadorUsuario = Base64Custom.decodificarBase64(usuarios.getEmail());
                    FirebaseUser usuarioFirebase = task.getResult().getUser();
                    usuarios.setId(identificadorUsuario);
                    usuarios.salvar();

                    Preferencias preferencias = new Preferencias(Cadastro.this);
                    preferencias.salvarUsuarioPreferencias(identificadorUsuario, usuarios.getEmail());
                    */
                    abrirLoginUsuario();


                } else {
                    String erroExcecao = "";
                    try {
                        throw task.getException();
                    } catch (FirebaseAuthWeakPasswordException e) {
                        erroExcecao = "Digite uma senha mais forte";
                    } catch (FirebaseAuthInvalidCredentialsException e) {
                        erroExcecao = "O E-mail digitado é inválido";
                    } catch (FirebaseAuthUserCollisionException e) {
                        erroExcecao = "Este E-mail já está cadastrado no sistema";
                    }catch(Exception e){
                        erroExcecao = "Erro ao efetuar o cadastro";
                        e.printStackTrace();
                    }
                    Toast.makeText(Cadastro.this, erroExcecao, Toast.LENGTH_LONG).show();
                }
            }
        });
    }

    public void abrirLoginUsuario(){
        Intent intent = new Intent(Cadastro.this,MainActivity.class);
        startActivity(intent);
        finish();
    }
}
