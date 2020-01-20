package ru.homework.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import java.util.List;

import ru.homework.base.BaseScreen;
import ru.homework.base.Sprite;
import ru.homework.math.Rect;
import ru.homework.pool.BulletPool;
import ru.homework.pool.EnemyShipPool;
import ru.homework.sprite.Bullet;
import ru.homework.sprite.EnemyShip;
import ru.homework.sprite.Star;
import ru.homework.sprite.StarShip;
import ru.homework.utils.EnemyGenerator;

public class GameScreen extends BaseScreen {
    private StarShip starShip;

    //Пулл пуль
    private BulletPool bulletPool;
    private Sound mainShipBulletSound;

    //Пул вражеских кораблей
    private EnemyShipPool enemyShipPool;

    private Sound enemyBulletSound;

    private EnemyGenerator enemyGenerator;

    @Override
    public void show() {
        super.show();

        this.atlas = new TextureAtlas(Gdx.files.internal("textures/mainAtlas.tpack"));

        //Формируем звезды
        this.stars = new Star[64];
        for (int i = 0; i < stars.length; i++) {
            this.sprites.add(new Star(atlas));
        }

        //Формируем звуки
        this.mainShipBulletSound = Gdx.audio.newSound(Gdx.files.internal("sounds/bullet.wav"));
        this.enemyBulletSound = Gdx.audio.newSound(Gdx.files.internal("sounds/laser.wav"));

        //Формируем основной корабль
        this.bulletPool = new BulletPool();
        this.starShip = new StarShip(atlas, bulletPool, mainShipBulletSound);
        this.sprites.add(starShip);

        //Формируем вражеские корабли
        this.enemyShipPool = new EnemyShipPool(bulletPool, enemyBulletSound, worldBounds);
        this.enemyGenerator = new EnemyGenerator(atlas, enemyShipPool, worldBounds);
    }

    private void freeAllDestroyed() {
        bulletPool.freeAllDestroyedActiveObjects();
        enemyShipPool.freeAllDestroyedActiveObjects();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        update(delta);
        checkCollisions();
        freeAllDestroyed();
        draw();
    }

    private void update(float delta) {
        for (Sprite s : sprites) {

            if (s instanceof StarShip && !starShip.isDestroyed()) {
                if (!s.isDestroyed()){s.update(delta);}
            } else {
                s.update(delta);
            }
        }

        bulletPool.updateActiveSprites(delta);

        //Обновляем движения вражеских кораблей и пуль
        enemyShipPool.updateActiveSprites(delta);
        enemyGenerator.generate(delta);
    }

    private void checkCollisions() {
        //Проверяем столкновения кораблей
        checkCollisionsShips();

        //Проверяем попадания пуль
        checkCollisionsBulletToShip();
    }

    private void checkCollisionsShips() {
        List<EnemyShip> enemyShipList = enemyShipPool.getActiveObjects();
        for (EnemyShip enemyShip : enemyShipList) {
            float minDist = enemyShip.getHalfWidth() + starShip.getHalfWidth();

            if (enemyShip.pos.dst(starShip.pos) < minDist) {
                enemyShip.destroy();
                //Наносим нашему кораблю урон от столкновения
                starShip.damage(enemyShip.getDamage());
            }
        }
    }

    private void checkCollisionsBulletToShip() {
        List<Bullet> bulletList = bulletPool.getActiveObjects();

        for (Bullet bullet : bulletList) {
            //проверяем соприкосновение пуль и вражеских кораблей
            if (bullet.getOwner() instanceof StarShip) {
                if (isHitEnemy(bullet)) {
                    bullet.destroy();
                }
            }

            //проверяем соприкосновение пуль и главного корабля
            if (bullet.getOwner() instanceof EnemyShip) {
                if (isHitStarShip(bullet)) {
                    bullet.destroy();
                }
            }

        }
    }

    private boolean isHitEnemy(Bullet bullet) {
        List<EnemyShip> enemyShipList = enemyShipPool.getActiveObjects();
        for (EnemyShip enemyShip : enemyShipList) {
            if (enemyShip.isBulletCollision(bullet)) {
                enemyShip.damage(bullet.getDamage());
                return true;
            }
        }
        return false;
    }

    private boolean isHitStarShip(Bullet bullet) {
        if (starShip.isBulletCollision(bullet)) {
            starShip.damage(bullet.getDamage());
            return true;
        } else {
            return false;
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
        batch.begin();

        //Отрисовываем все объекты
        for (Sprite s : sprites) {
            if (s instanceof StarShip) {
                if (!s.isDestroyed()){s.draw(batch);}
            } else {
                s.draw(batch);
            }
        }
        bulletPool.drawActiveSprites(batch);
        enemyShipPool.drawActiveSprites(batch);

        batch.end();
    }

    @Override
    public void dispose() {
        super.dispose();
        bulletPool.dispose();
        mainShipBulletSound.dispose();
        enemyShipPool.dispose();
        enemyBulletSound.dispose();
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
