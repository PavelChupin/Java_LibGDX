package ru.homework.sprite;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.homework.base.Ship;
import ru.homework.math.Rect;
import ru.homework.pool.BulletPool;

public class EnemyShip extends Ship {
    private enum State {DESCENT, FIGHT}
    private State state;

    private Vector2 descentV = new Vector2(0,-0.15f);

    public EnemyShip(BulletPool enemyBulletPool, Sound enemyBulletSound, Rect worldBounds) {
        this.bulletPool = enemyBulletPool;
        this.bulletSound = enemyBulletSound;
        this.bulletV = new Vector2();
        this.v0 = new Vector2();
        this.speedV = new Vector2();
        this.worldBounds = worldBounds;
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
        this.speedV.set(descentV);
        this.v0 = new Vector2(speedV);
        this.bulletRegion = bulletRegion;
        this.bulletHeight = bulletHeight;
        this.bulletV.set(0, bulletVY);
        this.damage = damage;
        this.reloadInterval = reloadInterval;
        this.reloadTimer = reloadInterval;
        this.hp = hp;
        setHeightProportion(height);
        //this.v.set(descentV);
        state = State.DESCENT;
    }

    @Override
    public void update(float delta) {
        //super.update(delta);
        pos.mulAdd(speedV, delta);
        switch (state){
            case DESCENT:
                if (getTop() <= worldBounds.getTop()){speedV.set(v0);
                state = State.FIGHT; }
                break;
            case FIGHT:
                reloadTimer += delta;
                if (reloadTimer > reloadInterval) {
                    reloadTimer = 0f;
                    shoot();
                }

                if (getBottom() < worldBounds.getBottom()){
                    destroy();
                }
                break;
        }

        if (isOutside(worldBounds)) {
            destroy();
        }
    }
}
