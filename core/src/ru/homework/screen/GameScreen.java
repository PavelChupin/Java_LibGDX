package ru.homework.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.homework.base.BaseScreen;
import ru.homework.base.Sprite;
import ru.homework.math.Rect;
import ru.homework.pool.BulletPool;
import ru.homework.sprite.Star;
import ru.homework.sprite.StarShip;

public class GameScreen extends BaseScreen {
    private StarShip starShip;

    private BulletPool bulletPool;

    @Override
    public void show() {
        super.show();

        atlas = new TextureAtlas(Gdx.files.internal("textures/mainAtlas.tpack"));
        stars = new Star[64];
        for (int i = 0; i < stars.length; i++) {
            this.sprites.add(new Star(atlas));
        }

        bulletPool = new BulletPool();
        starShip = new StarShip(atlas, bulletPool);
        sprites.add(starShip);


    }

    private void freeAllDestroyed(){
        bulletPool.freeAllDestroyedActiveObjects();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        freeAllDestroyed();
        draw();
    }

    private void update(float delta) {
        for (Sprite s : sprites) {
            s.update(delta);
        }
        bulletPool.updateActiveSprites(delta);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);

        for (Sprite s : sprites) {
            s.resize(worldBounds);
        }
    }

    private void draw() {
        batch.begin();

        //Отрисовываем все объекты
        for (Sprite s : sprites) {
            s.draw(batch);
        }
        bulletPool.drawActiveSprites(batch);

        batch.end();
    }

    @Override
    public void dispose() {
        super.dispose();
        bulletPool.dispose();

    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
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
    public boolean keyUp(int keycode) {
        System.out.println("keyUp keycode = " + keycode);
        for (Sprite s : sprites) {
            s.keyUp(keycode);
        }
        return false;
    }

    @Override
    public boolean touchDragged(Vector2 touch, int pointer) {
        for (Sprite s : sprites) {
            s.touchDragged(touch, pointer);
        }
        return false;
    }


}
