package ru.homework.utils;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.homework.math.Rect;
import ru.homework.math.Rnd;
import ru.homework.pool.EnemyShipPool;
import ru.homework.sprite.EnemyShip;

public class EnemyGenerator {

    private final TextureRegion[] enemySmallRegions;
    private final TextureRegion[] enemyMediumRegions;
    private final TextureRegion[] enemyBigRegions;
    private final TextureRegion bulletRegion;

    private final Vector2 enemySmallV = new Vector2(0f, -0.2f);
    private final Vector2 enemyMediumV = new Vector2(0f, -0.03f);
    private final Vector2 enemyBigV = new Vector2(0f, -0.005f);

    private float generateInterval = 4f;
    private float generateTimer = generateInterval;

    private EnemyShipPool enemyPool;

    private Rect worldBounds;

    public EnemyGenerator(TextureAtlas atlas, EnemyShipPool enemyPool, Rect worldBounds) {
        TextureRegion enemy0 = atlas.findRegion("enemy0");
        this.enemySmallRegions = Regions.split(enemy0, 1, 2, 2);
        TextureRegion enemy1 = atlas.findRegion("enemy1");
        this.enemyMediumRegions = Regions.split(enemy1, 1, 2, 2);
        TextureRegion enemy2 = atlas.findRegion("enemy2");
        this.enemyBigRegions = Regions.split(enemy2, 1, 2, 2);


        this.bulletRegion = atlas.findRegion("bulletEnemy");
        this.enemyPool = enemyPool;
        this.worldBounds = worldBounds;
    }

    public void generate(float delta) {
        generateTimer += delta;
        if (generateTimer > generateInterval) {
            generateTimer = 0f;
            EnemyShip enemyShip = enemyPool.obtain();
            //Параметр вероятности появления больших или маленьких кораблей
            float type = (float) Math.random();

            if (type < 0.5f) {
                enemyShip.set(enemySmallRegions, enemySmallV, bulletRegion,
                        EnumEnemyGenerator.ENEMY_SMALL_BULLET_HEIGHT.getValue(),
                        EnumEnemyGenerator.ENEMY_SMALL_BULLET_VY.getValue(),
                        (int) EnumEnemyGenerator.ENEMY_SMALL_DAMAGE.getValue(),
                        EnumEnemyGenerator.ENEMY_SMALL_RELOAD_INTERVAL.getValue(),
                        (int) EnumEnemyGenerator.ENEMY_SMALL_HP.getValue(),
                        EnumEnemyGenerator.ENEMY_SMALL_HEIGHT.getValue());
            }else if (type < 0.8f){
                enemyShip.set(enemyMediumRegions, enemyMediumV, bulletRegion,
                        EnumEnemyGenerator.ENEMY_MEDIUM_BULLET_HEIGHT.getValue(),
                        EnumEnemyGenerator.ENEMY_MEDIUM_BULLET_VY.getValue(),
                        (int) EnumEnemyGenerator.ENEMY_MEDIUM_DAMAGE.getValue(),
                        EnumEnemyGenerator.ENEMY_MEDIUM_RELOAD_INTERVAL.getValue(),
                        (int) EnumEnemyGenerator.ENEMY_MEDIUM_HP.getValue(),
                        EnumEnemyGenerator.ENEMY_MEDIUM_HEIGHT.getValue());
            }else {enemyShip.set(enemyBigRegions, enemyBigV, bulletRegion,
                    EnumEnemyGenerator.ENEMY_BIG_BULLET_HEIGHT.getValue(),
                    EnumEnemyGenerator.ENEMY_BIG_BULLET_VY.getValue(),
                    (int) EnumEnemyGenerator.ENEMY_BIG_DAMAGE.getValue(),
                    EnumEnemyGenerator.ENEMY_BIG_RELOAD_INTERVAL.getValue(),
                    (int) EnumEnemyGenerator.ENEMY_BIG_HP.getValue(),
                    EnumEnemyGenerator.ENEMY_BIG_HEIGHT.getValue());}

            enemyShip.pos.x = Rnd.nextFloat(
                    worldBounds.getLeft() + enemyShip.getHalfWidth(),
                    worldBounds.getRight() - enemyShip.getHalfWidth()
            );
            enemyShip.setBottom(worldBounds.getTop());

        }
    }
}
