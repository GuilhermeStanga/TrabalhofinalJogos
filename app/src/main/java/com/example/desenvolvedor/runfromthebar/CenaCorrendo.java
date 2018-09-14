package com.example.desenvolvedor.runfromthebar;

import com.example.desenvolvedor.runfromthebar.AndGraph.AGGameManager;
import com.example.desenvolvedor.runfromthebar.AndGraph.AGInputManager;
import com.example.desenvolvedor.runfromthebar.AndGraph.AGScene;
import com.example.desenvolvedor.runfromthebar.AndGraph.AGScreenManager;
import com.example.desenvolvedor.runfromthebar.AndGraph.AGSoundManager;
import com.example.desenvolvedor.runfromthebar.AndGraph.AGSprite;

/**
 * Created by Paulo on 10/09/2018.
 */

public class CenaCorrendo extends AGScene {

    AGSprite fundo = null;

    AGSprite bebado = null;
    AGSprite patroa = null;

    AGSprite[] placar = null;
    int pontuacao = 0;
    int tempPont = 0;

    AGSprite btnStart = null;
    AGSprite btnDown = null;
    AGSprite btnJump = null;

    boolean bebado_running = false;

    double angleDirection = 0;
    double teorAlcoolico = .2;

    public CenaCorrendo(AGGameManager pManager) {
        super(pManager);
    }

    @Override
    public void init() {
        // Fundo
        fundo = createSprite(R.mipmap.fundo, 1, 2);
        fundo.setScreenPercent(200, 100);
        fundo.vrPosition.setXY(0, AGScreenManager.iScreenHeight / 2);
        fundo.addAnimation(0, false, 1);

        // Botão start
        btnStart = createSprite(R.mipmap.button_1295902_960_720, 1, 1);
        btnStart.setScreenPercent(20, 45);
        btnStart.vrScale.setY(btnStart.vrScale.getX());
        btnStart.vrPosition.setXY(AGScreenManager.iScreenWidth / 2, AGScreenManager.iScreenHeight * 2 / 3);
        btnStart.addAnimation(0, false, 0);

        // Botão agachar
        btnDown = createSprite(R.mipmap.button_1295902_960_720, 1, 1);
        btnDown.setScreenPercent(20, 45);
        btnDown.vrScale.setY(btnDown.vrScale.getX());
        btnDown.vrPosition.setXY(btnDown.getSpriteWidth() / 2, AGScreenManager.iScreenHeight / 2);
        btnDown.addAnimation(0, false, 0);
        btnDown.bVisible = false;

        // Botão pular
        btnJump = createSprite(R.mipmap.button_1295902_960_720, 1, 1);
        btnJump.setScreenPercent(20, 45);
        btnJump.vrScale.setY(btnJump.vrScale.getX());
        btnJump.vrPosition.setXY(AGScreenManager.iScreenWidth - btnJump.getSpriteWidth() / 2, AGScreenManager.iScreenHeight / 2);
        btnJump.addAnimation(0, false, 0);
        btnJump.bVisible = false;

        bebado = createSprite(R.mipmap.homer, 11, 4);
        bebado.setScreenPercent(18, 18);
        bebado.vrScale.setY(bebado.vrScale.getX());
        bebado.vrPosition.setXY(AGScreenManager.iScreenWidth / 2, bebado.getSpriteHeight() /20);
        bebado.addAnimation(8, true, 7,10); // Parado 1
        bebado.addAnimation(18, true, 0,6); // Correndo
        //bebado.addAnimation(18, true, 85, 86, 87, 86); // Boost
        bebado.addAnimation(12, false, 29,32); // Cair

        patroa = createSprite(R.mipmap.marge_edited, 11, 3);
        patroa.setScreenPercent(10, 30);
        patroa.vrPosition.setXY(patroa.getSpriteWidth() / 2, patroa.getSpriteHeight() * 2 / 3);
        patroa.addAnimation(18, true, 0, 10); // Correndo

        placar = new AGSprite[8];
        for (int iIndex = 0; iIndex < 8; iIndex++) {
            placar[iIndex] = createSprite(R.mipmap.fonte, 4, 4);
            placar[iIndex].setScreenPercent(10, 10);
            placar[iIndex].vrScale.setX(placar[iIndex].vrScale.getY());
            placar[iIndex].bAutoRender = false; //o placar n eh mais desenhado pelo motor, pq se n quando cria ele ja mostra
            placar[iIndex].vrPosition.setY(AGScreenManager.iScreenHeight - placar[iIndex].getSpriteHeight() / 2);
            placar[iIndex].vrPosition.setX(placar[iIndex].getSpriteWidth() / 2 + iIndex * placar[iIndex].getSpriteWidth());
            for (int jIndex = 0; jIndex < 10; jIndex++) {
                placar[iIndex].addAnimation(1, false, jIndex);
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
        btnHandler();
        backgroundMove();
        updateAngle();
        updatePlacar();
    }

    public void btnHandler() {
        if (AGInputManager.vrTouchEvents.screenClicked()) {
            if (bebado_running) {
                if (btnDown.collide(AGInputManager.vrTouchEvents.getLastPosition())) {
                    bebado.setCurrentAnimation(1);
                    return;
                }
                if (btnJump.collide(AGInputManager.vrTouchEvents.getLastPosition())) {
                    bebado.setCurrentAnimation(2);
                    return;
                }
            } else {
                if (btnStart.collide(AGInputManager.vrTouchEvents.getLastPosition())) {
                    startRunning();
                    return;
                }
            }
        }
    }

    public void startRunning() {
        bebado.setCurrentAnimation(1);
        bebado.fAngle = 0;
        bebado_running = true;

        btnStart.bVisible = false;
        btnJump.bVisible = true;
        btnDown.bVisible = true;

        pontuacao = 0;
        tempPont = 0;
        angleDirection = 0;
        teorAlcoolico = .2;
    }

    public void backgroundMove() {
        if (bebado_running) {
            fundo.vrPosition.fX -= 5;
            if (fundo.vrPosition.fX <= 0)
                fundo.vrPosition.fX = AGScreenManager.iScreenWidth;
        }
    }

    public void updateAngle() {
        if (bebado_running) {
            // Fator Aleatório
            angleDirection += (Math.random() - .5f) * 5f;
            if (angleDirection > (teorAlcoolico * .4)) angleDirection = (teorAlcoolico * .4);
            else if (angleDirection < -(teorAlcoolico * .4)) angleDirection = -(teorAlcoolico * .4);

            // Controle do Acelerômetro
            bebado.fAngle += (-AGInputManager.vrAccelerometer.getAccelX() + angleDirection);

            // Verificar se caiu...
            if (bebado.fAngle <= -80 || bebado.fAngle >= 80)
                fallRunning();
        }
    }

    public void fallRunning() {
        bebado.fAngle = 0;
        bebado.setCurrentAnimation(2);
        bebado_running = false;

        btnStart.bVisible = true;
        btnJump.bVisible = false;
        btnDown.bVisible = false;
    }

    public void render() {
        super.render();
        for (AGSprite digito : placar) {
            digito.render();
        }
    }

    public void updatePlacar() {
        if (bebado_running) {
            tempPont++;
            if (tempPont % 10 == 0) {
                pontuacao++;

                // Aumentar o teor alcoolico
                if (pontuacao % 10 == 0)
                    teorAlcoolico *= 1.1;

                tempPont = 0;
                atualizaPlacar();
            }
        }
    }

    private void atualizaPlacar() {
        int _val = pontuacao;
        for (int i = 0; i < 8; i++) {
            placar[7 - i].setCurrentAnimation(_val % 10);
            _val /= 10;
        }
    }
}
