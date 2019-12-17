package ru.homework.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import ru.homework.base.BaseScreen;
import ru.homework.gameobjects.Logotip;

public class MenuScreen extends BaseScreen {
    private static final float DIST = 10f;
    //private Texture img;
    private Logotip logotip;
    private Texture backGround;
    //private Vector2 pos;
    //private Vector2 posTo;
    //private Vector2 speed;

    @Override
    public void show() {
        super.show();
        logotip = new Logotip("badlogic.jpg",DIST);
        //img = new Texture("badlogic.jpg");
        backGround = new Texture("textures/starBackGround.jpg");
        //pos = new Vector2();
        //posTo = new Vector2();
        //speed = new Vector2();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(0.2f, 0.6f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        //Отрисовываем фон
        //batch.setColor(1,1,1,1f);
        batch.draw(backGround, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        //batch.setColor(0.3f,0.5f,0.6f,1f);
        batch.draw(logotip.getImg(), logotip.getPos().x, logotip.getPos().y);
        //batch.draw(region,300,300);
        batch.end();

        logotip.actionObject();
    }

    @Override
    public void dispose() {
        logotip.dispose();
        backGround.dispose();

        super.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        System.out.println("touchDown screenX = " + screenX + " screenY " + screenY + " pointer " + pointer + " button " + button);

        //Задаем точку до которой требуется двигаться
        logotip.getPosTo().set(screenX, Gdx.graphics.getHeight() - screenY);
        //Рассчитываем вектор скорости для объекта
        logotip.calcSpeed();

        //pos.set(screenX,Gdx.graphics.getHeight() - screenY);
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        System.out.println("keyDown keycode = " + keycode);

        //Перехватим движение объекта в текущей точке
        logotip.stopActionObject();

        switch (keycode) {
            case Input.Keys.DOWN: {
                logotip.getPosTo().y -= logotip.getDist();
                logotip.calcSpeed();
                break;
            }
            case Input.Keys.UP: {
                logotip.getPosTo().y += logotip.getDist();;
                logotip.calcSpeed();
                break;
            }
            case Input.Keys.LEFT: {
                logotip.getPosTo().x -= logotip.getDist();;
                logotip.calcSpeed();
                break;
            }
            case Input.Keys.RIGHT: {
                logotip.getPosTo().x += logotip.getDist();;
                logotip.calcSpeed();
                break;
            }
            default: {
                break;
            }
        }
        return false;
    }
/*
    private void calcSpeed(Vector2 obj) {
        //speed.set(posTo).nor().scl(v);
        speed.set(posTo).sub(obj).nor();
        //System.out.println(speed);
    }

    private void actionObject(Vector2 obj) {
        if ((speed.x < 0 && obj.x > posTo.x)
                || (speed.x > 0 && obj.x < posTo.x)
                || (speed.y < 0 && obj.y > posTo.y)
                || (speed.y > 0 && obj.y < posTo.y)
        ) {
            obj.add(speed);
        }
    }*/
}
