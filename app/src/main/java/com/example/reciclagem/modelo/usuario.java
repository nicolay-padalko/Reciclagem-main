package com.example.reciclagem.modelo;

//Classe usuario
//Esta classe contém os atributos para a manipulação dos dados do usuário dentro da aplicação
public class usuario {

    //Declaraçãp de atributos
    private String uid;
    private String nome;
    private String email;
    private String dia;
    private String mes;
    private String ano;
    private String numero;
    private String endereco;


    //Método Construtor da classe
    public usuario() {
    }

   //Getters e setters de todos os atributos
    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDia() {
        return dia;
    }

    public void setDia(String dia) {
        this.dia = dia;
    }

    public String getMes() {
        return mes;
    }

    public void setMes(String mes) {
        this.mes = mes;
    }

    public String getAno() {
        return ano;
    }

    public void setAno(String ano) {
        this.ano = ano;
    }

    public String getNumero() {
        return numero;
    }

    public void setNumero(String numero) {
        this.numero = numero;
    }

    public String getEndereco() {
        return endereco;
    }

    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }

}//Fim da classe
