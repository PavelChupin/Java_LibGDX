package ru.homework.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

import ru.homework.base.BaseScreen;
import ru.homework.gameobjects.GameObjects;
import ru.homework.gameobjects.Logotip;
import ru.homework.math.Rect;
import ru.homework.sprite.Background;

public class MenuScreen extends BaseScreen {
    private static final float DIST = 10f;
    private Texture bg;
    private Background background;
    private Logotip logotip;
    private Vector2 vectorTo;
    private List<GameObjects> gameObjects = new ArrayList();

    @Override
    public void show() {
        super.show();
        logotip = new Logotip("badlogic.jpg");
        vectorTo = new Vector2();
        gameObjects.add(logotip);
        bg = new Texture("textures/starBackGround.jpg");
        background = new Background(new TextureRegion(bg));
        //Установим матрицу проекций в единичную
        //batch.getProjectionMatrix().idt();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(0.2f, 0.6f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        //Отрисовываем фон
        background.draw(batch);
        //batch.setColor(1,1,1,1f);
        //batch.draw(bg, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        //batch.setColor(0.3f,0.5f,0.6f,1f);
        //batch.draw(logotip.getImg(), logotip.getPosX(), logotip.getPosY());
        batch.draw(logotip.getImg(), 0, 0, 0.5f, 0.5f);
        //batch.draw(region,300,300);
        batch.end();

        //Выполняем действие с объектом
        /*for (GameObjects o : gameObjects) {
            o.actionObject();
        }*/
    }

    @Override
    public void dispose() {
        logotip.dispose();
        bg.dispose();

        super.dispose();
    }

    @Override
    public boolean touchDragged(Vector2 touch, int pointer) {
        super.touchDragged(touch, pointer);

        //logotip.changePosToTouchDown(touch.x,touch.y);

        return false;
    }

    /*
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        System.out.println("touchDown screenX = " + screenX + " screenY " + screenY + " pointer " + pointer + " button " + button);

        //Задаем точку до которой требуется двигаться
        logotip.changePosToTouchDown(screenX, Gdx.graphics.getHeight() - screenY);

        return false;
    }*/


    @Override
    public boolean keyDown(int keycode) {
        System.out.println("keyDown keycode = " + keycode);
        vectorTo.set(0, 0);

        //Перехватим движение объекта в текущей точке
        logotip.stopActionObject();

        switch (keycode) {
            case Input.Keys.DOWN: {
                //Установим направление движения
                vectorTo.set(0, -1);
                break;
            }
            case Input.Keys.UP: {
                //Установим направление движения
                vectorTo.set(0, 1);
                break;
            }
            case Input.Keys.LEFT: {
                //Установим направление движения
                vectorTo.set(-1, 0);
                break;
            }
            case Input.Keys.RIGHT: {
                //Установим направление движения
                vectorTo.set(1, 0);
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

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
    }
}
