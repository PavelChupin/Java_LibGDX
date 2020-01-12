package ru.homework.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.homework.base.BaseScreen;
import ru.homework.base.Sprite;
import ru.homework.math.Rect;
import ru.homework.pool.BulletPool;
import ru.homework.pool.EnemyShipPool;
import ru.homework.sprite.EnemyShip;
import ru.homework.sprite.Star;
import ru.homework.sprite.StarShip;

public class GameScreen extends BaseScreen {
    private StarShip starShip;

    //Пулл пуль
    private BulletPool bulletPool;

    //Пул вражеских кораблей
    private EnemyShipPool enemyShipPool;
    private Vector2 enemyShipSpeed;
    private static final int ENEMY_SHIP_COUNT = 30;

    //Таймер для появления вражеских кораблей по запуску Rendera
    private int autoTimerCountRender = 0;
    private int autoTimerIntervalRender = 60;

    private Sound bulletSound;

    @Override
    public void show() {
        super.show();

        atlas = new TextureAtlas(Gdx.files.internal("textures/mainAtlas.tpack"));
        stars = new Star[64];
        for (int i = 0; i < stars.length; i++) {
            this.sprites.add(new Star(atlas));
        }

        enemyShipPool = new EnemyShipPool();
        enemyShipSpeed = new Vector2(0, -0.1f);

        bulletSound = Gdx.audio.newSound(Gdx.files.internal("sounds/bullet.wav"));
        bulletPool = new BulletPool();

        starShip = new StarShip(atlas, bulletPool, bulletSound);
        sprites.add(starShip);

        //Создаем пачку вражеских кораблей.
        for (int i = 0; i < ENEMY_SHIP_COUNT; i++) {
            EnemyShip enemyShip = new EnemyShip(atlas, enemyShipSpeed, 1);//= enemyShipPool.obtain();
            enemyShipPool.getFreeObjects().add(enemyShip);
        }
    }

    private void freeAllDestroyed() {
        bulletPool.freeAllDestroyedActiveObjects();
        enemyShipPool.freeAllDestroyedActiveObjects();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        //Вражеские корабли
        autoTimerCountRender++;
        if (autoTimerCountRender > autoTimerIntervalRender && enemyShipPool.getFreeObjects().size() > 0) {
            EnemyShip enemyShip = enemyShipPool.obtain();
            enemyShip.set(enemyShipSpeed, 0);
            autoTimerCountRender = 0;
        }

        update(delta);
        freeAllDestroyed();
        draw();
    }

    private void update(float delta) {
        for (Sprite s : sprites) {
            s.update(delta);
        }
        bulletPool.updateActiveSprites(delta);
        enemyShipPool.updateActiveSprites(delta);
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);

        for (EnemyShip e : enemyShipPool.getFreeObjects()) {
            e.resize(worldBounds);
        }

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
        enemyShipPool.drawActiveSprites(batch);
        batch.end();
    }

    @Override
    public void dispose() {
        super.dispose();
        bulletPool.dispose();
        bulletSound.dispose();
        enemyShipPool.dispose();
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
