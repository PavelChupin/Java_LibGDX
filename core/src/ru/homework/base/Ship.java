package ru.homework.base;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.homework.math.Rect;
import ru.homework.pool.BulletPool;
import ru.homework.sprite.Bullet;

public abstract class Ship extends Sprite{
    private static final float SPEED = 0.01f;

    protected Rect worldBounds;

    //Поля для пула пуль
    protected Sound bulletSound;
    protected BulletPool bulletPool;
    protected TextureRegion bulletRegion;
    protected float bulletHeight;
    protected Vector2 bulletV;
    protected int damage;
    //Поля для автострельбы
    protected float reloadInterval;
    protected float reloadTimer;

    protected int hp;

    //Скорость
    protected float speed = SPEED;

    protected Vector2 speedV;
    //Позиция перемещения
    protected Vector2 posTo;


    protected Vector2 v0;

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

    /*
    @Override
    public void resize(Rect worldBounds) {

    }*/

    @Override
    public void update(float delta) {
        //Автострельба
        reloadTimer += delta;
        if (reloadTimer > reloadInterval) {
            reloadTimer = 0f;
            shoot();
        }
    }

     public void changeHP(int damange) {
        int hp = this.hp - damange;
        if (hp <= 0) {
            this.hp = 0;
            destroy();
        } else {
            this.hp = hp;
        }
    }
}
