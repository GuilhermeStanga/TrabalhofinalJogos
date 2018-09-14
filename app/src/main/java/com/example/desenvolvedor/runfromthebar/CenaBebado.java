package com.example.desenvolvedor.runfromthebar;


import com.example.desenvolvedor.runfromthebar.AndGraph.AGGameManager;
import com.example.desenvolvedor.runfromthebar.AndGraph.AGInputManager;
import com.example.desenvolvedor.runfromthebar.AndGraph.AGScene;
import com.example.desenvolvedor.runfromthebar.AndGraph.AGScreenManager;
import com.example.desenvolvedor.runfromthebar.AndGraph.AGSprite;

import java.sql.Timestamp;
import java.util.concurrent.TimeUnit;

/**
 * Created by Desenvolvedor on 31/08/2018.
 */

public class CenaBebado extends AGScene {
    AGSprite fundo = null;
    AGSprite bebado = null;
    AGSprite patroa = null;
    AGSprite[] placar = null;
    int valorPplacar = 0;
    int clicado = 0;
    int bebado_parou = 0;
    int aux_bebado_posicao= 0;
    int bebado_caiu = 0;

    float angulo_somar = 5; //depois fazer com q esse valor aumente pra dar dificuldade
    int andamento = 10;
    long tempo_ini_queda = 0;





    public CenaBebado(AGGameManager pManager) {
        super(pManager);
    }

    @Override
    public void init() {
       // AGInputManager.vrTouchEvents.setEvent(-50); //gambi pra n comecar com evendo down

        fundo = createSprite(R.mipmap.background,1,1);
        fundo.setScreenPercent(100,100);
        fundo.vrPosition.setXY(AGScreenManager.iScreenWidth/2,AGScreenManager.iScreenHeight/2);

        bebado = createSprite(R.mipmap.buldogue_mod,4,4);
        bebado.setScreenPercent(18,30);
        bebado.vrPosition.setXY(AGScreenManager.iScreenWidth/2 , bebado.getSpriteHeight()/4);
        bebado.addAnimation(12,true,0,11);
        bebado.addAnimation(12,false,0); //chama essa animacao quando clicar
        //bebado.getCurrentAnimation()
        bebado.fAngle = 0;

        placar = new AGSprite[8];
        for(int iIndex = 0; iIndex<8; iIndex++) {
            placar[iIndex] = createSprite(R.mipmap.fonte,4,4);
            placar[iIndex].setScreenPercent(10,10);
            placar[iIndex].bAutoRender = false; //o placar n eh mais desenhado pelo motor, pq se n quando cria ele ja mostra
            placar[iIndex].vrPosition.setY(AGScreenManager.iScreenHeight - placar[iIndex].getSpriteHeight()/2);
            placar[iIndex].vrPosition.setX(placar[iIndex].getSpriteWidth()/2 + iIndex*placar[iIndex].getSpriteWidth());
            for(int jIndex = 0; jIndex < 10; jIndex++) {
                placar[iIndex].addAnimation(1,false,jIndex);
            }
        }
    }

    @Override
    public void restart() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void loop() {

        controlabebado();
        atualizaMetragem();
        bebado_levanta();

    }

    private void bebado_levanta() {
        if(bebado_caiu == 1) {
           // Timestamp dataDeHoje = new Timestamp(System.currentTimeMillis());
          long t =  System.currentTimeMillis();
         // long timeSeconds = TimeUnit.MILLISECONDS.toSeconds(t);
            long res = t - tempo_ini_queda;
            if (res <= 3000) {
              bebado.setCurrentAnimation(1); //isso poderia fazer 1 vez soh
              bebado.fAngle = 0;
              //piscar:
              if(res % 2 == 0) {
                  bebado.bVisible = false; // ajustar o pisca
              } else {
                  bebado.bVisible = true; // ajustar o pisca
              }
          } else {
              bebado.setCurrentAnimation(0);
              bebado_caiu = 0;
              bebado.bVisible = true;
          }

            // vai ficar e segundos piscando na animation 1
        }
    }

    private void controlabebado() {
        if(bebado_caiu == 0) {
            if (AGInputManager.vrAccelerometer.getAccelX() > 2.0) {//bota uma margem pra n ficar mexendo mesmo parado
                bebado.fAngle -= angulo_somar;
                if (bebado.fAngle <= -80) {
                    bebado_caiu = 1;
                    tempo_ini_queda = System.currentTimeMillis();
                    //tempo_ini_queda = TimeUnit.MILLISECONDS.toSeconds(tempo_ini_queda);
                } //else {
                   // bebado_caiu = 0;
                //}
            }
            if (AGInputManager.vrAccelerometer.getAccelX() < -2.0) {
                bebado.fAngle += angulo_somar;
                if (bebado.fAngle >= 80) {
                    bebado_caiu = 1;
                    tempo_ini_queda = System.currentTimeMillis();
                    //tempo_ini_queda = TimeUnit.MILLISECONDS.toSeconds(tempo_ini_queda);
                }// else {
                   // bebado_caiu = 0;
                //}
            }
        }
    }

    private void atualizaMetragem() {
        if(bebado_caiu == 0) {
            if (AGInputManager.vrTouchEvents.screenDragged()) {
                clicado = 1;
                andamento++;
            } else {
                clicado = 0;
                bebado_andar(0);
            }
            if (clicado == 1 && andamento >= 10) {
                valorPplacar++;
                placar[7].setCurrentAnimation(valorPplacar % 10);
                placar[6].setCurrentAnimation((valorPplacar % 100) / 10);
                placar[5].setCurrentAnimation((valorPplacar % 1000) / 100);
                placar[4].setCurrentAnimation((valorPplacar % 10000) / 1000);
                placar[3].setCurrentAnimation((valorPplacar % 100000) / 10000);
                placar[2].setCurrentAnimation((valorPplacar % 1000000) / 100000);
                andamento = 0;
                bebado_andar(1);
            }
        }
    }

    private void bebado_andar(int i) {
        if(i == 1) { //continuar a animacao
            bebado.setCurrentAnimation(0);
        } else { //parar animacao
            bebado.setCurrentAnimation(1);
            //bebado_parou = bebado.getCurrentAnimationIndex();
        }
    }

    public void render() { //pra passar por baixo do navio/bala
        super.render();
        for(AGSprite digito:placar) {
            digito.render();
        }
    }
}
