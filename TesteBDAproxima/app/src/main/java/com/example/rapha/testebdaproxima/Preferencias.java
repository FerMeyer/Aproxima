package com.example.rapha.testebdaproxima;

import android.content.Context;
import android.content.SharedPreferences;

public class Preferencias {

    private Context context;
    private SharedPreferences preferences;
    private String nomeArquivo="aproxima.preferencias";
    private int mode =0;
    private SharedPreferences.Editor editor;

    private final String chaveIdentificador = "identificarUsuarioLogado";
    private final String chaveNome = "usuarioLogado";

    public Preferencias(Context context) {
        this.context = context;
        preferences = context.getSharedPreferences(nomeArquivo,mode);
        editor = preferences.edit();
    }

    public void salvarUsuarioPreferencias(String identificadorUsuario, String nomeUsuario){
        editor.putString(chaveIdentificador,identificadorUsuario);
        editor.putString(chaveNome,nomeUsuario);
        editor.commit();
    }

    public String getIdentificador(){
        return preferences.getString(chaveIdentificador,null);
    }

    public String getNome(){
        return preferences.getString(chaveNome,null);
    }

}
