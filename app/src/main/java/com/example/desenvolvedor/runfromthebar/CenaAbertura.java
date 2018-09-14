package com.example.desenvolvedor.runfromthebar;


import com.example.desenvolvedor.runfromthebar.AndGraph.AGGameManager;
import com.example.desenvolvedor.runfromthebar.AndGraph.AGScene;
import com.example.desenvolvedor.runfromthebar.AndGraph.AGScreenManager;
import com.example.desenvolvedor.runfromthebar.AndGraph.AGSoundManager;
import com.example.desenvolvedor.runfromthebar.AndGraph.AGSprite;
import com.example.desenvolvedor.runfromthebar.AndGraph.AGTimer;

/**
 * Created by Desenvolvedor on 24/08/2018.
 */

public class CenaAbertura extends AGScene{

    AGTimer tempo = null;//referencia d tempo
    AGSprite logoFadep = null;//sprite representa qualquer coisa visual pra mostrar na tela
    int estado = 0;

    public CenaAbertura(AGGameManager pManager) {//pra trocar d cena
        super(pManager);//o super manda pra classe d cima
    }

    @Override
    public void init() {//chamado quando esta cena for carregada
        setSceneBackgroundColor(1,0,0);
        tempo = new AGTimer(3000);//3 segundos

        logoFadep = createSprite(R.mipmap.logofadep, 1, 1);
        logoFadep.setScreenPercent(80,80);
        logoFadep.vrPosition.setXY(AGScreenManager.iScreenWidth/2, AGScreenManager.iScreenHeight/2);//no meio da tela
        logoFadep.fadeIn(1000);

        AGSoundManager.vrMusic.loadMusic("musica.mp3",true);
        AGSoundManager.vrMusic.play();
    }

    @Override
    public void restart() {//chamado quando a cena volta d uma interrupcao, ex.: uma ligacao

    }

    @Override
    public void stop() {// chamado quando a cena sofre uma interrupcao

    }

    @Override
    public void loop() {//metodo q roda em vezes por segundo, logica da cena
       /* tempo.update();//atualiza o valor de tempo
        if(tempo.isTimeEnded()) {
            vrGameManager.setCurrentScene(1);//vai pra cena 1
        }*/
        if(estado == 0 && logoFadep.fadeEnded()) {
            estado = 1;
            logoFadep.fadeOut(1000);
        } else {
            if(logoFadep.fadeEnded()) {
                vrGameManager.setCurrentScene(1);
            }
        }

    }


}
