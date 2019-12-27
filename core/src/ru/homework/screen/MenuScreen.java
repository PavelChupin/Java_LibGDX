package ru.homework.screen;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.homework.base.BaseScreen;
import ru.homework.base.Sprite;
import ru.homework.math.Rect;
import ru.homework.sprite.ButtonExit;
import ru.homework.sprite.ButtonPlay;
import ru.homework.sprite.Star;

public class MenuScreen extends BaseScreen {
    private Game game;

    //private Texture logo;
    //private Logotip logotip;

    private ButtonExit buttonExit;
    private ButtonPlay buttonPlay;


    public MenuScreen(Game game) {
        this.game = game;
    }

    @Override
    public void show() {
        super.show();
        //logo = new Texture("badlogic.jpg");
        //logotip = new Logotip(new TextureRegion(logo));
        //sprites.add(logotip);

        atlas = new TextureAtlas(Gdx.files.internal("textures/menuAtlas.tpack"));
        stars = new Star[256];
        for (int i = 0; i < stars.length; i++) {
            this.sprites.add(new Star(atlas));
        }

        buttonExit = new ButtonExit(atlas);
        sprites.add(buttonExit);

        buttonPlay = new ButtonPlay(atlas, game);
        sprites.add(buttonPlay);
        //Установим матрицу проекций в единичную
        //batch.getProjectionMatrix().idt();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        //Выполняем действие с объектом
        update(delta);

        //Отрисовывааем объекты
        draw();
    }

    private void update(float delta) {
        for (Sprite s : sprites) {
            s.update(delta);
        }
    }

    private void draw() {
        batch.begin();
        //Отрисовываем все объекты
        for (Sprite s : sprites) {
            s.draw(batch);
        }
        batch.end();
    }

    @Override
    public void dispose() {
        super.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        //super.touchDown(touch, pointer, button);

        for (Sprite s : sprites) {
            s.touchDown(touch, pointer, button);
        }

        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {

        for (Sprite s : sprites) {
            s.touchUp(touch, pointer, button);
        }
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        System.out.println("keyDown keycode = " + keycode);

        for (Sprite s : sprites) {
            s.keyDown(keycode);
        }

        return false;
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);

        for (Sprite s : sprites) {
            s.resize(worldBounds);
        }
        //background.resize(worldBounds);
        //buttonExit.resize(worldBounds);
        //logotip.resize(worldBounds);
    }
}
