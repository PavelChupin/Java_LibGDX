package ru.homework.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Align;

import java.util.List;

import ru.homework.base.BaseScreen;
import ru.homework.base.Font;
import ru.homework.base.Sprite;
import ru.homework.math.Rect;
import ru.homework.pool.BulletPool;
import ru.homework.pool.EnemyShipPool;
import ru.homework.pool.ExplosionPool;
import ru.homework.sprite.Bullet;
import ru.homework.sprite.ButtonNewGame;
import ru.homework.sprite.EnemyShip;
import ru.homework.sprite.LableGameOver;
import ru.homework.sprite.Star;
import ru.homework.sprite.StarShip;
import ru.homework.utils.EnemyGenerator;

public class GameScreen extends BaseScreen {

    public enum State {PLAYING, GAME_OVER}
    private static final int DELTA_LEVEL = 15;
    private static final String FRAGS = "Frags: ";
    private static final float FONT_PADDING = 0.01f;
    private static final float FONT_SIZE = 0.02f;
    private static final String HP = "HP: ";
    private static final String LEVEL = "GAME LEVEL: ";

    private ButtonNewGame buttonNewGame;
    private LableGameOver lableGameOver;

    private StarShip starShip;

    private Sound mainShipBulletSound;
    private Sound explosionSound;
    private Sound enemyBulletSound;

    //Пулл пуль
    private BulletPool bulletPool;

    //Пул вражеских кораблей
    private EnemyShipPool enemyShipPool;

    //Пул взрывов
    private ExplosionPool explosionPool;

    //Генерация вражеских кораблей
    private EnemyGenerator enemyGenerator;

    private State state;

    private int frags;

    private Font font;
    private StringBuilder sbFrags;

    private StringBuilder sbHP;
    private StringBuilder sbLevel;

    private int level;

    @Override
    public void show() {
        super.show();
        this.sbFrags = new StringBuilder();
        this.sbHP = new StringBuilder();
        this.sbLevel = new StringBuilder();

        this.frags = 0;
        this.level = frags / DELTA_LEVEL + 1;

        this.atlas = new TextureAtlas(Gdx.files.internal("textures/mainAtlas.tpack"));
        this.state = State.PLAYING;

        this.buttonNewGame = new ButtonNewGame(atlas, this);
        this.lableGameOver = new LableGameOver(atlas);

        //Формируем звезды
        this.stars = new Star[64];
        for (int i = 0; i < stars.length; i++) {
            this.sprites.add(new Star(atlas));
        }

        //Формируем звуки
        this.mainShipBulletSound = Gdx.audio.newSound(Gdx.files.internal("sounds/bullet.wav"));
        this.enemyBulletSound = Gdx.audio.newSound(Gdx.files.internal("sounds/laser.wav"));
        this.explosionSound = Gdx.audio.newSound(Gdx.files.internal("sounds/explosion.wav"));
        //Пул пуль
        this.bulletPool = new BulletPool();
        //Пул взрывов
        this.explosionPool = new ExplosionPool(atlas, explosionSound);
        //Формируем основной корабль
        this.starShip = new StarShip(atlas, bulletPool, explosionPool, mainShipBulletSound);
        //Формируем вражеские корабли
        this.enemyShipPool = new EnemyShipPool(bulletPool, explosionPool, enemyBulletSound, worldBounds);
        this.enemyGenerator = new EnemyGenerator(atlas, enemyShipPool, worldBounds);

        this.font = new Font("font/font.fnt", "font/font.png");

    }

    private void freeAllDestroyed() {
        bulletPool.freeAllDestroyedActiveObjects();
        explosionPool.freeAllDestroyedActiveObjects();
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

    private void checkCollisions() {
        if (state == State.PLAYING) {
            //Проверяем столкновения кораблей
            checkCollisionsShips();

            //Проверяем попадания пуль
            checkCollisionsBulletToShip();

            if (starShip.isDestroyed()) {
                state = State.GAME_OVER;
                destroyAllActiveObject();
            }
        }
    }

    private void destroyAllActiveObject() {
        enemyShipPool.destroyAllActiveObject();
        bulletPool.destroyAllActiveObject();
    }

    private void checkCollisionsShips() {
        List<EnemyShip> enemyShipList = enemyShipPool.getActiveObjects();
        for (EnemyShip enemyShip : enemyShipList) {
            float minDist = enemyShip.getHalfWidth() + starShip.getHalfWidth();

            if (enemyShip.pos.dst(starShip.pos) < minDist) {
                enemyShip.destroy();
                frags++;
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
                if (enemyShip.isDestroyed()) {
                    frags++;
                }
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

        buttonNewGame.resize(worldBounds);
        lableGameOver.resize(worldBounds);
        starShip.resize(worldBounds);
        font.setSize(FONT_SIZE);
    }

    private void draw() {
        batch.begin();

        //Отрисовываем все объекты
        printInfo();
        for (Sprite s : sprites) {
            s.draw(batch);
        }
        explosionPool.drawActiveSprites(batch);

        if (state == State.PLAYING) {
            starShip.draw(batch);
            bulletPool.drawActiveSprites(batch);
            enemyShipPool.drawActiveSprites(batch);
        } else if (state == State.GAME_OVER) {
            buttonNewGame.draw(batch);
            lableGameOver.draw(batch);
        }

        batch.end();
    }

    private void update(float delta) {
        for (Sprite s : sprites) {
            s.update(delta);
        }
        explosionPool.updateActiveSprites(delta);

        if (state == State.PLAYING) {
            starShip.update(delta);
            bulletPool.updateActiveSprites(delta);

            //Обновляем движения вражеских кораблей и пуль
            enemyShipPool.updateActiveSprites(delta);
            this.level = frags / DELTA_LEVEL + 1;
            enemyGenerator.generate(delta,level);
        } else if (state == State.GAME_OVER) {
            buttonNewGame.update(delta);
        }
    }

    @Override
    public void dispose() {
        super.dispose();
        mainShipBulletSound.dispose();
        enemyBulletSound.dispose();
        explosionSound.dispose();
        bulletPool.dispose();
        explosionPool.dispose();
        enemyShipPool.dispose();
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        if (state == State.PLAYING) {
            starShip.touchDown(touch, pointer, button);
        } else if (state == State.GAME_OVER) {
            buttonNewGame.touchDown(touch, pointer, button);
        }

        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        if (state == State.PLAYING) {
            starShip.touchUp(touch, pointer, button);
        } else if (state == State.GAME_OVER) {
            buttonNewGame.touchUp(touch, pointer, button);
        }

        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        System.out.println("keyDown keycode = " + keycode);
        if (state == State.PLAYING) {
            starShip.keyDown(keycode);
        }
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        System.out.println("keyUp keycode = " + keycode);
        if (state == State.PLAYING) {
            starShip.keyUp(keycode);
        }
        return false;
    }

    @Override
    public boolean touchDragged(Vector2 touch, int pointer) {
        if (state == State.PLAYING) {
            starShip.touchDragged(touch, pointer);
        }
        return false;
    }

    public void newGame() {
        starShip.setStart();
        this.state = State.PLAYING;
        this.frags = 0;
        this.level = frags / DELTA_LEVEL + 1;
    }


    private void printInfo() {
        sbFrags.setLength(0);
        sbHP.setLength(0);
        sbLevel.setLength(0);
        font.draw(batch, sbFrags.append(FRAGS).append(frags), worldBounds.getLeft() + FONT_PADDING, worldBounds.getBottom() + 0.08f /*+ FONT_PADDING*/,Align.left);
        font.draw(batch, sbHP.append(HP).append(starShip.getHp()), worldBounds.getRight() - 0.11f/*FONT_PADDING*/, worldBounds.getBottom() + 0.08f /*+ FONT_PADDING*/);
        font.draw(batch, sbLevel.append(LEVEL).append(level), 0f, worldBounds.getTop() - 0.02f, Align.center);
    }
}
