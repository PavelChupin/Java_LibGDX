package ru.homework.sprite;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.homework.base.Ship;
import ru.homework.math.Rect;
import ru.homework.pool.BulletPool;
import ru.homework.pool.ExplosionPool;

public class StarShip extends Ship {
    private static final float OBJECT_SIZE_PROPORC = 0.13f;
    private static final int HP = 10;

    protected static final int INVALID_POINTER = -1;

    protected int leftPointer = INVALID_POINTER;
    protected int rightPointer = INVALID_POINTER;

    //Вектор направления для рассчетов
    protected Vector2 vectorTo = new Vector2();
    protected Vector2 vectorBorder = new Vector2(1f, 1f);
    protected boolean pressedRight;
    protected boolean pressedLeft;
    protected boolean pressedUp;
    protected boolean pressedDown;

    public StarShip(TextureAtlas atlas, BulletPool bulletPool, ExplosionPool explosionPool, Sound bulletSound) {
        super(atlas.findRegion("main_ship"), 1, 2, 2);
        //TextureRegion region = atlas.findRegion("main_ship");
        //regions[0] = new TextureRegion(region, 0, 0, region.getRegionWidth() / 2, region.getRegionHeight());
        //regions[1] = new TextureRegion(region, region.getRegionWidth() / 2, 0, region.getRegionWidth(), region.getRegionHeight());
        this.bulletPool = bulletPool;
        this.bulletSound = bulletSound;
        this.explosionPool = explosionPool;

        this.bulletRegion = atlas.findRegion("bulletMainShip");
        this.bulletHeight = 0.01f;
        this.bulletV = new Vector2(0, 0.5f);
        this.damage = 1;

        this.posTo = new Vector2(pos);
        this.speedV = new Vector2();

        this.reloadInterval = 0.25f;
        this.reloadTimer = 0f;

        this.hp = HP;
    }

    public StarShip(TextureAtlas atlas, float speed, BulletPool bulletPool, ExplosionPool explosionPool, Sound bulletSound) {
        this(atlas, bulletPool, explosionPool, bulletSound);
        this.speed = speed;
    }


    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        this.worldBounds = worldBounds;
        setHeightProportion(OBJECT_SIZE_PROPORC);
        pos.set(worldBounds.getLeft() + worldBounds.getHalfWidth(), worldBounds.getBottom() + getHalfHeight());
    }

    @Override
    public void setStart(){
        this.hp = HP;
        flushDestroy();
        pos.set(worldBounds.getLeft() + worldBounds.getHalfWidth(), worldBounds.getBottom() + getHalfHeight());
        speedV.setZero();
        vectorTo.setZero();
        stopActionObject();
    }

    @Override
    public void update(float delta) {
        super.update(delta);
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
        if (reloadTimer > reloadInterval) {
            reloadTimer = 0f;
            shoot();
        }
    }

    @Override
    public boolean keyDown(int keycode) {
        //Выполняем метод нажатия кнопки
        vectorTo.setZero();
        //frame = 1;

        switch (keycode) {
            case Input.Keys.DOWN: {
                //Установим направление движения
                vectorTo.set(0, -1);
                pressedDown = true;
                break;
            }
            case Input.Keys.UP: {
                //Установим направление движения
                vectorTo.set(0, 1);
                pressedUp = true;
                break;
            }
            case Input.Keys.LEFT: {
                //Установим направление движения
                vectorTo.set(-1, 0);
                pressedLeft = true;
                break;
            }
            case Input.Keys.RIGHT: {
                //Установим направление движения
                vectorTo.set(1, 0);
                pressedRight = true;
                break;
            }
            case Input.Keys.SPACE: {
                //Установим направление движения
                shoot();
                break;
            }
            default: {
                break;
            }
        }

        //Перехватим движение объекта в текущей точке
        stopActionObject();


        //Меняем направление движения объекта
        changePosToKeyDown(vectorTo);

        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        //frame = 0;
        switch (keycode) {
            case Input.Keys.DOWN: {
                pressedDown = false;
                if (pressedUp) {
                    vectorTo.set(0, 1);
                }
                break;
            }
            case Input.Keys.UP: {
                pressedUp = false;
                if (pressedDown) {
                    vectorTo.set(0, -1);
                }
                break;
            }
            case Input.Keys.LEFT: {
                pressedLeft = false;
                if (pressedRight) {
                    vectorTo.set(1, 0);
                }
                break;
            }
            case Input.Keys.RIGHT: {
                pressedRight = false;
                if (pressedLeft) {
                    vectorTo.set(-1, 0);
                }
                break;
            }
            default: {
                break;
            }
        }

        stopActionObject();

        //Меняем направление движения объекта
        changePosToKeyDown(vectorTo);

        return false;
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        //frame = 1;
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
        //frame = 0;
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


    public boolean isBulletCollision(Rect bullet){
        return !(bullet.getRight() < getLeft()
                || bullet.getLeft() > getRight()
                || bullet.getBottom() > pos.y
                || bullet.getTop() < getBottom());
    }



}
