package ru.homework.pool;

import com.badlogic.gdx.audio.Sound;

import ru.homework.base.SpritesPool;
import ru.homework.math.Rect;
import ru.homework.sprite.EnemyShip;

public class EnemyShipPool extends SpritesPool<EnemyShip> {
    private BulletPool bulletPool;
    private Sound sound;
    private Rect worldBounds;

    public EnemyShipPool(BulletPool bulletPool, Sound sound, Rect worldBounds) {
        this.bulletPool = bulletPool;
        this.sound = sound;
        this.worldBounds = worldBounds;
    }

    @Override
    public EnemyShip newObject() {
        return new EnemyShip(bulletPool, sound, worldBounds);
    }

}
