package ru.homework.sprite;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.homework.base.Sprite;
import ru.homework.math.Rect;
import ru.homework.math.Rnd;
import ru.homework.pool.BulletPool;

public class EnemyShip extends Sprite {
    private static final float OBJECT_SIZE_PROPORC = 0.13f;

    private Vector2 speedV;
    private int damage;
    private Rect worldBounds;
    private BulletPool bulletPool;
    private Sound bulletSound;
    private TextureRegion bulletRegion;
    private float bulletHeight;
    private Vector2 bulletV;
    //Поля для автострельбы
    private float reloadInterval = 0.5f;
    private float reloadTimer = 0f;

    public EnemyShip() {
        regions = new TextureRegion[1];
        speedV = new Vector2();
    }

    public BulletPool getBulletPool() {
        return bulletPool;
    }

    public EnemyShip(TextureAtlas atlas, BulletPool enemyBulletPool, Vector2 speedV, int damage, Sound enemyBulletSound) {
        super(atlas.findRegion("enemy1"), 1, 2, 2);
        this.bulletPool = enemyBulletPool;
        this.damage = damage;
        this.speedV = new Vector2(speedV);
        this.bulletSound = enemyBulletSound;
        this.bulletRegion = atlas.findRegion("bulletEnemy");
        this.bulletHeight = 0.01f;
        this.bulletV = new Vector2(0, -0.5f);
    }

    public void set(
            //TextureRegion region,
            //float posx,
            //Vector2 pos0,
            Vector2 speedV,
            //float height,
            //Rect worldBounds,
            int damage
    ) {
        //this.regions = Regions.split(region,1,2,2);
        //this.pos.set(x,worldBounds.getTop());
        this.speedV.set(speedV);
        //setHeightProportion(OBJECT_SIZE_PROPORC);
        //this.worldBounds = worldBounds;
        this.damage = damage;
        startPosition();
        //this.posx = posx;
    }

    private void startPosition() {
        float posx = Rnd.nextFloat(worldBounds.getLeft(), worldBounds.getRight());
        pos.set(posx, worldBounds.getTop() + getHalfHeight());
    }

    @Override
    public void update(float delta) {
        pos.mulAdd(speedV, delta);
        //Автострельба
        reloadTimer += delta;
        if (reloadTimer > reloadInterval) {
            reloadTimer = 0f;
            shoot();
        }

        if (isOutside(worldBounds)) {
            destroy();
        }
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        setHeightProportion(OBJECT_SIZE_PROPORC);
        startPosition();
    }

    protected void shoot() {
        Bullet bullet = bulletPool.obtain();
        bulletSound.play(0.01f);
        bullet.set(this, bulletRegion, pos, bulletV, bulletHeight, worldBounds, damage);
    }

}
