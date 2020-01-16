package ru.homework.utils;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.homework.math.Rect;
import ru.homework.math.Rnd;
import ru.homework.pool.EnemyShipPool;
import ru.homework.sprite.EnemyShip;

public class EnemyGenerator {
    private static final float ENEMY_SMALL_HEIGHT = 0.1f;
    private static final float ENEMY_SMALL_BULLET_HEIGHT = 0.01f;
    private static final float ENEMY_SMALL_BULLET_VY = -0.3f;
    private static final int ENEMY_SMALL_DAMAGE = 1;
    private static final float ENEMY_SMALL_RELOAD_INTERVAL = 3f;
    private static final int ENEMY_SMALL_HP = 1;

    private final TextureRegion[] enemySmallRegions;
    private final TextureRegion bulletRegion;

    private final Vector2 enemySmallV = new Vector2(0f, -0.2f);

    private float generateInterval = 4f;
    private float generateTimer = generateInterval;

    private EnemyShipPool enemyPool;

    private Rect worldBounds;

    public EnemyGenerator(TextureAtlas atlas, EnemyShipPool enemyPool, Rect worldBounds) {
        TextureRegion enemy0 = atlas.findRegion("enemy0");
        this.enemySmallRegions = Regions.split(enemy0, 1, 2, 2);
        this.bulletRegion = atlas.findRegion("bulletEnemy");
        this.enemyPool = enemyPool;
        this.worldBounds = worldBounds;
    }

    public void generate(float delta) {
        generateTimer += delta;
        if (generateTimer > generateInterval) {
            generateTimer = 0f;
            EnemyShip enemyShip = enemyPool.obtain();
            enemyShip.set(enemySmallRegions, enemySmallV, bulletRegion, ENEMY_SMALL_BULLET_HEIGHT, ENEMY_SMALL_BULLET_VY, ENEMY_SMALL_DAMAGE, ENEMY_SMALL_RELOAD_INTERVAL, ENEMY_SMALL_HP, ENEMY_SMALL_HEIGHT);
            enemyShip.pos.x = Rnd.nextFloat(
                    worldBounds.getLeft() + enemyShip.getHalfWidth(),
                    worldBounds.getRight() - enemyShip.getHalfWidth()
            );
            enemyShip.setBottom(worldBounds.getTop());

        }
    }
}
