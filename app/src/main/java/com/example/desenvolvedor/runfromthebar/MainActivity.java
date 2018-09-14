package com.example.desenvolvedor.runfromthebar;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.desenvolvedor.runfromthebar.AndGraph.AGActivityGame;

public class MainActivity extends AGActivityGame {
    CenaAbertura abertura = null;
    CenaMenu menu = null;
    CenaSobre sobre = null;
    CenaBebado bebado = null;
    CenaCorrendo correndo = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        init(this,true);//accel eh o acelerometro, do emulador n vai ter dai bota falso, se n true
        //valida o objeto da cena
        abertura = new CenaAbertura(getGameManager());
        menu = new CenaMenu(getGameManager());
        sobre = new CenaSobre(getGameManager());
        bebado = new CenaBebado(getGameManager());
        correndo = new CenaCorrendo(getGameManager());

        getGameManager().addScene(correndo); // cena 4
        getGameManager().addScene(bebado);

        getGameManager().addScene(abertura);//registra cena ao gerenciador d jogo
        //inicia mostrando a primeira cena cadastrada, mesmo tendo mais cenas cadastradas
        //cada cena registrada gera um indice comeca em 0 e vai pela ordem q dicionou
        getGameManager().addScene(menu);//cena 2
        getGameManager().addScene(sobre);//cena 3


    }
}
