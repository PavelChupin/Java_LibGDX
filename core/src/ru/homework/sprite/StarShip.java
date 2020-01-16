package ru.homework.sprite;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.homework.base.StarShipBase;
import ru.homework.math.Rect;
import ru.homework.pool.BulletPool;

public class StarShip extends StarShipBase {
    private static final float SPEED = 0.01f;
    private static final float OBJECT_SIZE_PROPORC = 0.13f;

    //Поля для пула пуль
    private Sound bulletSound;
    private BulletPool bulletPool;
    private TextureRegion bulletRegion;
    private float bulletHeight;
    private Vector2 bulletV;
    private int damage;
    //Поля для автострельбы
    private float reloadInterval = 0.5f;
    private float reloadTimer = 0f;

    //Скорость и векор напрвления скорости
    private float speed = SPEED;
    private Vector2 speedV;
    //Позиция перемещения
    private Vector2 posTo;


    public StarShip(TextureAtlas atlas, BulletPool bulletPool, Sound bulletSound) {
        super(atlas.findRegion("main_ship"), 1, 2, 2);
        //TextureRegion region = atlas.findRegion("main_ship");
        //regions[0] = new TextureRegion(region, 0, 0, region.getRegionWidth() / 2, region.getRegionHeight());
        //regions[1] = new TextureRegion(region, region.getRegionWidth() / 2, 0, region.getRegionWidth(), region.getRegionHeight());

        bulletRegion = atlas.findRegion("bulletMainShip");
        bulletHeight = 0.01f;
        bulletV = new Vector2(0, 0.5f);
        damage = 1;

        this.posTo = new Vector2(pos);
        this.speedV = new Vector2();
        this.bulletPool = bulletPool;
        this.bulletSound = bulletSound;
    }

    public StarShip(TextureAtlas atlas, float speed, BulletPool bulletPool, Sound bulletSound) {
        this(atlas, bulletPool, bulletSound);
        this.speed = speed;
    }


    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setHeightProportion(OBJECT_SIZE_PROPORC);
        pos.set(worldBounds.getLeft() + worldBounds.getHalfWidth(), worldBounds.getBottom() + getHalfHeight());
    }

    @Override
    public void update(float delta) {
        //Чиним что бы карабль не улетал за пределы экрана
        if (getRight() > worldBounds.getRight()) {
            setRight(worldBounds.getRight());
            stopActionObject();
        }
        if (getLeft() < worldBounds.getLeft()) {
            setLeft(worldBounds.getLeft());
            stopActionObject();
        }

        if (getTop() > worldBounds.getTop()) {
            setTop(worldBounds.getTop());
            stopActionObject();
        }

        if (getBottom() < worldBounds.getBottom()) {
            setBottom(worldBounds.getBottom());
            stopActionObject();
        }

        if ((speedV.x < 0 && pos.x > posTo.x)
                || (speedV.x > 0 && pos.x < posTo.x)
                || (speedV.y < 0 && pos.y > posTo.y)
                || (speedV.y > 0 && pos.y < posTo.y)
        ) {
            this.pos.add(speedV);
        } else {
            stopActionObject();
        }

        //Автострельба
        reloadTimer += delta;
        if (reloadTimer > reloadInterval){
            reloadTimer = 0f;
            shoot();
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        //Выполняем метод нажатия кнопки
        super.keyDown(keycode);

        //Перехватим движение объекта в текущей точке
        stopActionObject();


        //Меняем направление движения объекта
        changePosToKeyDown(vectorTo);

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        stopActionObject();

        super.keyUp(keycode);

        //Меняем направление движения объекта
        changePosToKeyDown(vectorTo);

        return false;
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        frame = 1;
        if (touch.x < worldBounds.pos.x) {
            if (leftPointer != INVALID_POINTER) {
                return false;
            }
            leftPointer = pointer;
        } else {
            if (rightPointer != INVALID_POINTER) {
                return false;
            }
            rightPointer = pointer;
        }

        changePosToTouchDown(touch);
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        //stopActionObject();
        frame = 0;
        if (pointer == leftPointer) {
            leftPointer = INVALID_POINTER;
            if (rightPointer != INVALID_POINTER) {
                changePosToTouchDown(touch);
            } else {
                stopActionObject();
            }
        } else if (pointer == rightPointer) {
            rightPointer = INVALID_POINTER;
            if (leftPointer != INVALID_POINTER) {
                changePosToTouchDown(touch);
            } else {
                stopActionObject();
            }
        }
        return false;
    }

    private void changePosToKeyDown(float x, float y) {
        this.posTo.add(this.speed * x, this.speed * y);
        //Рассчитаем новую скорость объекта
        calcSpeed();
    }

    private void changePosToKeyDown(Vector2 vectorTo) {
        this.posTo.add(vectorTo.cpy().scl(vectorBorder));
        //Рассчитаем новую скорость объекта
        calcSpeed();
    }

    private void changePosToTouchDown(float x, float y) {
        this.posTo.set(x, y);
        //Рассчитаем новую скорость объекта
        calcSpeed();
    }

    private void changePosToTouchDown(Vector2 touch) {
        this.posTo.set(touch);
        //Рассчитаем новую скорость объекта
        calcSpeed();
    }


    private void calcSpeed() {
        //Переделываем реализацию через setLenght, т.к. через nor() с мелкой сеткой float значений есть косяк.
        this.speedV.set(posTo).sub(pos).setLength(speed);
    }

    protected void stopActionObject() {
        this.posTo.set(pos);
    }


    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }

    protected void shoot() {
        Bullet bullet = bulletPool.obtain();
        bulletSound.play(0.01f);
        bullet.set(this, bulletRegion, pos, bulletV, bulletHeight, worldBounds, damage);
    }
}
