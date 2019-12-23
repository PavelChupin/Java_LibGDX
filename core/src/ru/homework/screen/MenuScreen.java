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
import ru.homework.base.SpriteImpl;
import ru.homework.sprite.Logotip;
import ru.homework.math.Rect;
import ru.homework.sprite.Background;

public class MenuScreen extends BaseScreen {
    private Texture bg;
    private Background background;
    private Logotip logotip;
    private Texture logo;

    private Vector2 vectorTo;

    private List<SpriteImpl> sprites = new ArrayList();

    @Override
    public void show() {
        super.show();
        bg = new Texture("textures/starBackGround.jpg");
        background = new Background(new TextureRegion(bg));
        sprites.add(background);

        logo = new Texture("badlogic.jpg");
        logotip = new Logotip(new TextureRegion(logo));
        sprites.add(logotip);

        //Установим матрицу проекций в единичную
        //batch.getProjectionMatrix().idt();

        vectorTo = new Vector2();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(0.2f, 0.6f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();

        //Отрисовываем все объекты
        for (SpriteImpl s: sprites) {
            s.draw(batch);
        }
        batch.end();

        //Выполняем действие с объектом
        for (SpriteImpl s: sprites) {
            s.actionObject();
        }
    }

    @Override
    public void dispose() {
        logo.dispose();
        bg.dispose();

        super.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        super.touchDown(touch, pointer, button);

        logotip.changePosToTouchDown(touch.x,touch.y);
        return false;
    }

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
        logotip.resize(worldBounds);
    }
}
