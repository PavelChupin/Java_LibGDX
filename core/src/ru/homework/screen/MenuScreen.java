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
import ru.homework.base.Sprite;
import ru.homework.sprite.Logotip;
import ru.homework.math.Rect;
import ru.homework.sprite.Background;

public class MenuScreen extends BaseScreen {
    private Texture bg;
    private Background background;
    private Logotip logotip;
    private Texture logo;

    private List<Sprite> sprites = new ArrayList();

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

    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(0.2f, 0.6f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();

        //Отрисовываем все объекты
        for (Sprite s: sprites) {
            s.draw(batch);
        }
        batch.end();

        //Выполняем действие с объектом
        for (Sprite s: sprites) {
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

        for (Sprite s: sprites) {
            s.touchDown(touch, pointer, button);
        }

        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        System.out.println("keyDown keycode = " + keycode);

        for (Sprite s: sprites) {
            s.keyDown(keycode);
        }

        return false;
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        background.resize(worldBounds);
        logotip.resize(worldBounds);
    }
}
