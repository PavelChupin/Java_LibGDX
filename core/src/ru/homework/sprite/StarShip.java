package ru.homework.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.homework.base.StarShipBase;
import ru.homework.math.Rect;
import ru.homework.screen.GameScreen;

public class StarShip extends StarShipBase {
    private static final float SPEED = 0.01f;
    private static final float OBJECT_SIZE_PROPORC = 0.13f;
    private GameScreen gameScreen;

    private float speed = SPEED;
    private Vector2 speedV;
    //Позиция перемещения
    private Vector2 posTo;

    public StarShip(TextureAtlas atlas, GameScreen gameScreen) {
        super(atlas.findRegion("main_ship"), 1, 2, 2);
        TextureRegion region = atlas.findRegion("main_ship");
        //regions[0] = new TextureRegion(region, 0, 0, region.getRegionWidth() / 2, region.getRegionHeight());
        //regions[1] = new TextureRegion(region, region.getRegionWidth() / 2, 0, region.getRegionWidth(), region.getRegionHeight());

        this.posTo = new Vector2(pos);
        this.speedV = new Vector2();
        this.gameScreen = gameScreen;
    }

    public StarShip(TextureAtlas atlas, float speed, GameScreen gameScreen) {
        this(atlas, gameScreen);
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
        if (getRight() > worldBounts.getRight()) {
            setRight(worldBounts.getRight());
            stopActionObject();
        }
        if (getLeft() < worldBounts.getLeft()) {
            setLeft(worldBounts.getLeft());
            stopActionObject();
        }

        if (getTop() > worldBounts.getTop()) {
            setTop(worldBounts.getTop());
            stopActionObject();
        }

        if (getBottom() < worldBounts.getBottom()) {
            setBottom(worldBounts.getBottom());
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
        if (touch.x < worldBounts.pos.x) {
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

    @Override
    public void action() {
        //gameScreen.addSprite(new BulletStarShip(pos.cpy().add(regions[frame].getRegionWidth()/2,regions[frame].getRegionHeight())));
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }


}
