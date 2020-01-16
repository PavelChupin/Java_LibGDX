package ru.homework.sprite;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.homework.base.Ship;
import ru.homework.math.Rect;
import ru.homework.math.Rnd;
import ru.homework.pool.BulletPool;

public class EnemyShip extends Ship {
    private static final float OBJECT_SIZE_PROPORC = 0.13f;

    /*public BulletPool getBulletPool() {
        return bulletPool;
    }*/

    public EnemyShip(BulletPool enemyBulletPool, Sound enemyBulletSound, Rect worldBounds) {
        this.bulletPool = enemyBulletPool;
        this.bulletSound = enemyBulletSound;
        this.bulletV = new Vector2();
        this.posTo = new Vector2(pos);
        this.speedV = new Vector2();
        this.worldBounds =  worldBounds;
    }

    public void set(
            TextureRegion[] regions,
            Vector2 speedV,
            TextureRegion bulletRegion,
            float bulletHeight,
            float bulletVY,
            int damage,
            float reloadInterval,
            int hp,
            float height
    ) {
        this.regions = regions;
        this.speedV.set(speedV);
        this.bulletRegion = bulletRegion;
        this.bulletHeight = bulletHeight;
        this.bulletV.set(0, bulletVY);
        this.damage = damage;
        this.reloadInterval = reloadInterval;
        this.reloadTimer = reloadInterval;
        this.hp = hp;
        setHeightProportion(height);
        //this.v.set(descentV);
    }

    /*private void startPosition() {
        float posx = Rnd.nextFloat(worldBounds.getLeft(), worldBounds.getRight());
        pos.set(posx, worldBounds.getTop() + getHalfHeight());
    }*/

    @Override
    public void update(float delta) {
        super.update(delta);
        pos.mulAdd(speedV, delta);
        /*//Автострельба
        reloadTimer += delta;
        if (reloadTimer > reloadInterval) {
            reloadTimer = 0f;
            shoot();
        }*/

        /*if (isOutside(worldBounds)) {
            destroy();
        }*/
    }

   /* @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        setHeightProportion(OBJECT_SIZE_PROPORC);
        startPosition();
    }*/

    /*protected void shoot() {
        Bullet bullet = bulletPool.obtain();
        bulletSound.play(0.01f);
        bullet.set(this, bulletRegion, pos, bulletV, bulletHeight, worldBounds, damage);
    }*/
    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
    }
}
