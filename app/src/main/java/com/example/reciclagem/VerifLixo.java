package com.example.reciclagem;

//Importações
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

//ClaseVerifLixo
//Esta classe apresenta uma tela com os dados relativos as lixeiras corretas para o descarte de cada tipo
//de material
public class VerifLixo extends AppCompatActivity {

    //Método onCreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verif_lixo);
    }
}//Fim da classe