package com.example.reciclagem;

//Importações
import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;


import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;



//Classe VerifCode
/*Esta classe apresenta uma tela de leitor para que o usuário capture um valor (numérico) em um QRCode e, passa este valor
* para o campo crédito do usuário no banco do firestore. A biblioteca usada aqui é a zxing*/
public class VerifCode extends AppCompatActivity implements View.OnClickListener{

    //Atributos
    FirebaseFirestore db;
    private FirebaseAuth mAuth;
    Button scanBtn;


    //Método onCreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_verif_code);

        //Inicia botão
        scanBtn = findViewById(R.id.scanBtn);
        scanBtn.setOnClickListener(this);

    }

    //Método da interface
    //Este método chama a função que escanea o QRCode
    @Override
    public void onClick(View v) {

        scanCode();

    }

    //Função que escanea o código
    private void scanCode(){

        //Inicia a função do scanner com a opção de leitura de todos os tipos de códigos
        //suportados pela biblioteca zxing
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setCaptureActivity(CaptureAct.class);
        integrator.setOrientationLocked(false);
        integrator.setDesiredBarcodeFormats(IntentIntegrator.ALL_CODE_TYPES);
        integrator.setPrompt("Scanning code");
        integrator.initiateScan();

    }

    //Método que retorna o resultado do scanner
    /*Caso algum valor seja lido pelo scanner, uma tela aparece mostrando qual foi o valor numérico lido e,
    * apresenta um botão para que o usuário saia da mesma. Ao mesmo tempo, ao clicar no botão de saída, o valor
    * lido é passado para o campo equivalente do usuário no banco. Caso não seja lido nenhum valor, uma mensagem
    * é mostrada.*/
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data){

        //Resultado da scan
        IntentResult result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if(result != null){
            if(result.getContents() != null){

                //Criação da tela de resposta
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                String auxiliar;
                auxiliar = "Obrigado por reciclar!\n\nVocê recebeu "+result.getContents()+" créditos";
                //builder.setMessage(result.getContents());
                final String credit = result.getContents();
                final int cred = Integer.parseInt(credit);
                builder.setMessage(auxiliar);
                builder.setTitle("Resultado");
                builder.setPositiveButton("Sair", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                //Instância do firebase
                                mAuth = FirebaseAuth.getInstance();
                                FirebaseUser currentUser = mAuth.getCurrentUser();
                                String userId = mAuth.getCurrentUser().getUid();
                                db = FirebaseFirestore.getInstance();
                                //Referência ao usuário no banco
                                final DocumentReference usuarioRef = db.collection("usuarios").document(userId);

//                                DocumentReference docRef = db.collection("usuarios").document(userId);
                                usuarioRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
                                    @Override
                                    public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                                        if (task.isSuccessful()) {
                                            DocumentSnapshot document = task.getResult();

                                            //O  crédito recebido é somado ao valor já existente no banco
                                            String soma = document.getString("cred");
                                            int credA = Integer.parseInt(credit);
                                            int credB = Integer.parseInt(soma);

                                            int tot = credA + credB;
                                            String total = Integer.toString(tot);


                                            //Atualiza o banco
                                            usuarioRef.update(

                                                    "cred", total);
                                            //Inicia a classe perfil
                                            startActivity(new Intent(VerifCode.this, Perfil.class));

                                        }
                                    }


                                });
                            }
                        });

                AlertDialog dialog = builder.create();
                dialog.show();
            } else {

                Toast.makeText(this,"Sem resultados",Toast.LENGTH_LONG).show();

            }
        } else {

            super.onActivityResult(requestCode, resultCode, data);
        }




    }


}//Fim da classe