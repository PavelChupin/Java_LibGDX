package ru.homework.base;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.homework.math.Rect;
import ru.homework.pool.BulletPool;
import ru.homework.pool.ExplosionPool;
import ru.homework.sprite.Bullet;
import ru.homework.sprite.Explosion;

public abstract class Ship extends Sprite {
    private static final float SPEED = 0.01f;

    protected Rect worldBounds;

    //Поля для пула пуль
    protected Sound bulletSound;
    protected BulletPool bulletPool;
    protected ExplosionPool explosionPool;
    protected TextureRegion bulletRegion;
    protected float bulletHeight;
    protected Vector2 bulletV;
    protected int damage;
    //Поля для автострельбы
    protected float reloadInterval;
    protected float reloadTimer;

    protected float damageAnimateInterval = 0.1f;
    protected float damageAnimateTimer = damageAnimateInterval;

    protected int hp;

    //Скорость
    protected float speed = SPEED;

    protected Vector2 speedV;
    //Позиция перемещения
    protected Vector2 posTo;


    protected Vector2 v0;

    public int getHp() {
        return hp;
    }

    public Ship() {
    }

    public Ship(TextureRegion region, int rows, int cols, int frames) {
        super(region, rows, cols, frames);
    }

    protected void shoot() {
        Bullet bullet = bulletPool.obtain();
        bulletSound.play(0.01f);
        bullet.set(this, bulletRegion, pos, bulletV, bulletHeight, worldBounds, damage);
    }

    @Override
    public void update(float delta) {

        damageAnimateTimer += delta;
        if (damageAnimateTimer >= damageAnimateInterval) {
            frame = 0;
        }
    }

    public int getDamage() {
        return damage;
    }

    public void damage(int damange) {
        this.hp -= damange;
        if (this.hp <= 0) {
            destroy();
            this.hp = 0;
        }

        frame = 1;
        damageAnimateTimer = 0f;
    }

    public BulletPool getBulletPool() {
        return bulletPool;
    }

    public void setHp(int hp) {
        this.hp = hp;
    }

    protected void boom(){
        Explosion explosion = explosionPool.obtain();
        explosion.set(getHeight(),this.pos);
    }

    @Override
    public void destroy() {
        super.destroy();
        boom();
    }

    public void setStart(){}
}
