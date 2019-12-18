package ru.homework.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import ru.homework.base.BaseScreen;
import ru.homework.gameobjects.Logotip;

public class MenuScreen extends BaseScreen {
    private static final float DIST = 10f;
    private Logotip logotip;
    private Texture backGround;
    private Vector2 vectorTo;

    @Override
    public void show() {
        super.show();
        logotip = new Logotip("badlogic.jpg");
        backGround = new Texture("textures/starBackGround.jpg");
        vectorTo = new Vector2();
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
        batch.draw(logotip.getImg(), logotip.getPosX(), logotip.getPosY());
        //batch.draw(region,300,300);
        batch.end();

        //Выполняем действие с объектом
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
        logotip.changePosToTouchDown(screenX, Gdx.graphics.getHeight() - screenY);

        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        System.out.println("keyDown keycode = " + keycode);
        vectorTo.set(0,0);

        //Перехватим движение объекта в текущей точке
        logotip.stopActionObject();

        switch (keycode) {
            case Input.Keys.DOWN: {
                //Установим направление движения
                vectorTo.set(0,-1);
                break;
            }
            case Input.Keys.UP: {
                //Установим направление движения
                vectorTo.set(0,1);
                break;
            }
            case Input.Keys.LEFT: {
                //Установим направление движения
                vectorTo.set(-1,0);
                break;
            }
            case Input.Keys.RIGHT: {
                //Установим направление движения
                vectorTo.set(1,0);
                break;
            }
            default: {
                break;
            }
        }

        //Меняем направление движения объекта
        logotip.changePosToKeyDown(vectorTo);

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
