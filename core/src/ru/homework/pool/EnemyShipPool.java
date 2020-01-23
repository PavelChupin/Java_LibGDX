package ru.homework.pool;

import com.badlogic.gdx.audio.Sound;

import ru.homework.base.SpritesPool;
import ru.homework.math.Rect;
import ru.homework.sprite.EnemyShip;

public class EnemyShipPool extends SpritesPool<EnemyShip> {
    private BulletPool bulletPool;
    private Sound sound;
    private Rect worldBounds;
    private ExplosionPool explosionPool;

    public EnemyShipPool(BulletPool bulletPool, ExplosionPool explosionPool, Sound sound, Rect worldBounds) {
        this.bulletPool = bulletPool;
        this.sound = sound;
        this.worldBounds = worldBounds;
        this.explosionPool = explosionPool;
    }

    @Override
    public EnemyShip newObject() {
        return new EnemyShip(bulletPool,explosionPool, sound, worldBounds);
    }

}
