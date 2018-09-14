package com.example.desenvolvedor.runfromthebar;


import com.example.desenvolvedor.runfromthebar.AndGraph.AGGameManager;
import com.example.desenvolvedor.runfromthebar.AndGraph.AGInputManager;
import com.example.desenvolvedor.runfromthebar.AndGraph.AGScene;
import com.example.desenvolvedor.runfromthebar.AndGraph.AGScreenManager;
import com.example.desenvolvedor.runfromthebar.AndGraph.AGSprite;

/**
 * Created by Desenvolvedor on 24/08/2018.
 */

public class CenaSobre extends AGScene {

    AGSprite sobre = null;

    public CenaSobre(AGGameManager pManager) {
        super(pManager);
    }

    @Override
    public void init() {
        setSceneBackgroundColor(1,1,0);

        sobre = createSprite(R.mipmap.fnt_about,1,1);
        sobre.setScreenPercent(80,80);
        sobre.vrPosition.setXY(AGScreenManager.iScreenWidth/2, -sobre.getSpriteHeight()/2);

        //pra nao ter de no loop ficar mudando a posicao, tem essa funcao move q faz isso
        //move em pTime segundos da posicao atual ateh xy informado
        sobre.moveTo(10000,sobre.vrPosition.getX(), AGScreenManager.iScreenHeight + sobre.getSpriteHeight()/2);
    }

    @Override
    public void restart() {

    }

    @Override
    public void stop() {

    }

    @Override
    public void loop() {
        /*if(AGInputManager.vrTouchEvents.screenClicked()) {//pega um evento de toque na tela
            vrGameManager.setCurrentScene(1);

        }*/
        if(AGInputManager.vrTouchEvents.screenClicked() ||
                sobre.moveEnded() ||
                AGInputManager.vrTouchEvents.backButtonClicked()) {
            vrGameManager.setCurrentScene(1);

        }
    }
}
