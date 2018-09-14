package com.example.desenvolvedor.runfromthebar;

import com.example.desenvolvedor.runfromthebar.AndGraph.AGGameManager;
import com.example.desenvolvedor.runfromthebar.AndGraph.AGInputManager;
import com.example.desenvolvedor.runfromthebar.AndGraph.AGScene;
import com.example.desenvolvedor.runfromthebar.AndGraph.AGScreenManager;
import com.example.desenvolvedor.runfromthebar.AndGraph.AGSoundManager;
import com.example.desenvolvedor.runfromthebar.AndGraph.AGSprite;

/**
 * Created by Desenvolvedor on 24/08/2018.
 */

public class CenaMenu extends AGScene { //amarelo
    AGSprite titulo = null;

    //fazer 3 botoes, criar 3 srints
    AGSprite botao_play = null;
    AGSprite botao_about = null;
    AGSprite botao_exit = null;
    int codClique = 0;


    public CenaMenu(AGGameManager pManager) {
        super(pManager);
    }

    @Override
    public void init() {
        setSceneBackgroundColor((float)0.1,(float)0.5,(float) 0.7);
        codClique = AGSoundManager.vrSoundEffects.loadSoundEffect("toc.wav");

        titulo = createSprite(R.mipmap.fnt_title,1,4);//o 4 eh q tem 4 animacao
        titulo.setScreenPercent(80,20);
        titulo.vrPosition.setXY(AGScreenManager.iScreenWidth/2,
                (AGScreenManager.iScreenHeight - titulo.getSpriteHeight()));
        titulo.addAnimation(5,true,0,1,2,3); //quadros por segundo e para s repetir quando terminar
        //o ultimo sao os quadros, cada quadro eh uma frase do logo do pos fadep
        //o ponto de ancoragem eh o meio da imagem, entao sempre tem q pegar o meio
        botao_play = createSprite(R.mipmap.btn_play,1,1);
        botao_play.setScreenPercent(50,20);
        botao_play.vrPosition.setXY(AGScreenManager.iScreenWidth/2,
                titulo.vrPosition.fY -
                 titulo.getSpriteHeight()/2 - botao_play.getSpriteHeight()/2
        );

        botao_about = createSprite(R.mipmap.btn_about,1,1);
        botao_about.setScreenPercent(50,20);
        botao_about.vrPosition.setXY(AGScreenManager.iScreenWidth/2,
                botao_play.vrPosition.fY -
                        titulo.getSpriteHeight()/2 - botao_about.getSpriteHeight()/2
        );

        botao_exit = createSprite(R.mipmap.btn_exit,1,1);
        botao_exit.setScreenPercent(50,20);
        botao_exit.vrPosition.setXY(AGScreenManager.iScreenWidth/2,
                botao_about.vrPosition.fY -
                        titulo.getSpriteHeight()/2 - botao_exit.getSpriteHeight()/2
        );


    }

    @Override
    public void restart() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void loop() {
       /* if(AGInputManager.vrTouchEvents.screenClicked()) {//pega um evento de toque na tela
            vrGameManager.setCurrentScene(2);
        }*/

        if (AGInputManager.vrTouchEvents.screenClicked()) {
            if (botao_play.collide(AGInputManager.vrTouchEvents.getLastPosition())) {
                AGSoundManager.vrSoundEffects.play(codClique);
                vrGameManager.setCurrentScene(3);
                return;
            }
            if (botao_about.collide(AGInputManager.vrTouchEvents.getLastPosition())) {
                AGSoundManager.vrSoundEffects.play(codClique);
                vrGameManager.setCurrentScene(2);
                return;
            }
            if (botao_exit.collide(AGInputManager.vrTouchEvents.getLastPosition())) {
                AGSoundManager.vrSoundEffects.play(codClique);
                vrGameManager.vrActivity.finish();
                return;
            }
        }
      /*  if (botao_about.collide(AGInputManager.vrTouchEvents.getLastPosition()) ) {//ultima posicao pq pode ser arrastado o dedo
            if(botao_about.collide(AGInputManager.vrTouchEvents.getLastPosition())) {
                vrGameManager.setCurrentScene(2);
                return;//sempre q voltar de cena, sai fora pq se n da pau pq n ta mais inicializado as outras coisas
                //quando vai pra outra tela eh dado free em coisas caregadas aki no init, uma coisa assim
            }
        }
       if (botao_exit.collide(AGInputManager.vrTouchEvents.getLastPosition()) ) {//ultima posicao pq pode ser arrastado o dedo
           if(botao_exit.collide(AGInputManager.vrTouchEvents.getLastPosition())) {
               vrGameManager.vrActivity.finish();
           }
       }*/

       if(AGInputManager.vrTouchEvents.backButtonClicked()) {
           vrGameManager.vrActivity.finish();//se clicar no botao voltar, finaliza a aplicacao
           return;
       }
    }
}
