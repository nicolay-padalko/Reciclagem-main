package com.example.reciclagem;

//Importações
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.example.reciclagem.utils.MaskEditUtil;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

//Classe Cadastro
/*Esta classe é a responsável por fornecer um formulário para que o usuário digite seus dados de cadastro e,
* ao mesmo tempo, a mesma enviará estes dados para o banco do Firestore, criando um usuário com os dados descritos.*/
public class Cadastro extends AppCompatActivity {

    //Atributos
    private EditText SignInMail, SignInPass, SignInPassCk;
    private FirebaseAuth auth;
    private Button Cadastrar;
    private EditText nomeComp, cpf, dataN, telefone, endereco;
    private FirebaseFirestore db;
    String userID;
    private static final String TAG = "MyActivity";



    //Método onCreate
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_cadastro);

        //Chamada ao Firestore
        auth = FirebaseAuth.getInstance();

        //Instanciação dos atributos para controle do formulário da classe
        nomeComp = (EditText) findViewById(R.id.wNome);
        cpf = (EditText) findViewById(R.id.eCpf);
        cpf.addTextChangedListener(MaskEditUtil.mask(cpf, MaskEditUtil.FORMAT_CPF));
        dataN = (EditText) findViewById(R.id.wData);
        dataN.addTextChangedListener(MaskEditUtil.mask(dataN, MaskEditUtil.FORMAT_DATE));
        telefone = (EditText) findViewById((R.id.wTelefone));
        telefone.addTextChangedListener(MaskEditUtil.mask(telefone, MaskEditUtil.FORMAT_FONE));
        endereco = (EditText) findViewById((R.id.wEndereco));
        endereco.addTextChangedListener(MaskEditUtil.mask(endereco, MaskEditUtil.FORMAT_CEP));

        SignInMail = (EditText) findViewById(R.id.wEmail);
        SignInPass = (EditText) findViewById(R.id.wSenha);
        SignInPassCk = (EditText) findViewById(R.id.wSenhaCk);
        Cadastrar = (Button) findViewById(R.id.btCadastrar);

        //Instância do Firestore
        db = FirebaseFirestore.getInstance();

        //Função para o botão "cadastrar"
        /*Esta função faz a verificação dos dados principais para o cadastro (email e senha), recolhe os dados
        * escritos no campo, faz uma conexão com o Firestore e, em caso de não houver nenhum erro, cria o usuário no mesmo.*/
        Cadastrar.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {

                //Atributos para verificação
                final String email = SignInMail.getText().toString();
                String pass = SignInPass.getText().toString();
                String passCk = SignInPassCk.getText().toString();



                /*Este bloco verifica se o email foi digitado, se a senha foi digitada e se tem mais de 6 digitos e, se confere
                com a senha digitada no campo de confirmação. Caso algo não esteja certo, uma mensagem é mostrada; em caso de
                sucesso, executa-se o código abaixo o qual criará o usuário com os dados*/
                if(TextUtils.isEmpty(email)){
                    Toast.makeText(getApplicationContext(),"Por favor digite seu email",Toast.LENGTH_LONG).show();
                    return;
                }
                if(TextUtils.isEmpty(pass)){
                    Toast.makeText(getApplicationContext(),"Por favor digite sua senha",Toast.LENGTH_LONG).show();
                }
                if (pass.length() == 0){
                    Toast.makeText(getApplicationContext(),"Por favor digite sua senha",Toast.LENGTH_LONG).show();
                }
                if (pass.length()<5){
                    Toast.makeText(getApplicationContext(),"Senha deve ter mais que 6 digitos",Toast.LENGTH_LONG).show();
                }
                if (! pass.equals(passCk)){
                    Toast.makeText(getApplicationContext(),"Senha não confere",Toast.LENGTH_LONG).show();
                }
                else{




                    //Criação do usuário com o email e senha digitados
                    auth.createUserWithEmailAndPassword(email,pass)
                            .addOnCompleteListener(Cadastro.this, new OnCompleteListener<AuthResult>() {
                                public void onComplete(@NonNull Task<AuthResult> task) {

                                    //Recolhimento dos dados do formulário
                                    String nome = nomeComp.getText().toString();
                                    String CPF = cpf.getText().toString();
                                    String data = dataN.getText().toString();
                                    String tel = telefone.getText().toString();
                                    String end = endereco.getText().toString();
                                    String credit = "0";

                                    //Inicia a classe perfil
                                    startActivity(new Intent(Cadastro.this, Perfil.class));

                                    //Referência ao id de usuário no firestore
                                    userID = auth.getCurrentUser().getUid();
                                    DocumentReference documentReference = db.collection("usuarios").document(userID);
                                    Map<String, Object>  usuario = new HashMap<>();
                                    //Dados são passados para os respectivos campos no banco
                                    usuario.put("nome", nome);
                                    usuario.put("cpf", CPF);
                                    usuario.put("dia", data);
                                    usuario.put("tel", tel);
                                    usuario.put("CEP", end);
                                    usuario.put("email", email);
                                    usuario.put("cred", credit);

                                    //Usuário é adicionado no banco
                                    documentReference.set(usuario).addOnSuccessListener(new OnSuccessListener<Void>() {
                                        @Override
                                        public void onSuccess(Void aVoid) {
                                            Log.d("TAG",  "Sucesso");
                                        }
                                    });
                                    finish();
                                }
                            });}
            }
        });
    }

    public void navigate_sign_in(View v){
        Intent intent = new Intent(this, Cadastro.class);
        startActivity(intent);
    }
}//Fim da classe