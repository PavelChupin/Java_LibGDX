package ru.homework.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.homework.base.BaseScreen;
import ru.homework.base.Sprite;
import ru.homework.math.Rect;
import ru.homework.sprite.Background;
import ru.homework.sprite.Star;

public class GameScreen extends BaseScreen {

    @Override
    public void show() {
        super.show();
        bg = new Texture("textures/starBackGround.jpg");
        background = new Background(new TextureRegion(bg));
        sprites.add(background);

        atlas = new TextureAtlas(Gdx.files.internal("textures/menuAtlas.tpack"));
        stars = new Star[64];
        for (int i = 0; i < stars.length; i++) {
            this.sprites.add(new Star(atlas));
        }
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        draw();
    }

    private void update(float delta) {
        for (Sprite s : sprites) {
            s.update(delta);
        }
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);

        for (Sprite s : sprites) {
            s.resize(worldBounds);
        }
    }

    private void draw() {
        Gdx.gl.glClearColor(0.2f, 0.6f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
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
        return super.touchDown(touch, pointer, button);
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        return super.touchUp(touch, pointer, button);
    }
}
