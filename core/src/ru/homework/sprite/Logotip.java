package ru.homework.sprite;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.homework.base.Sprite;
import ru.homework.math.Rect;

public class Logotip extends Sprite {
    private static final float SPEED = 0.01f;
    private static final float OBJECT_SIZE_PROPORC = 0.3f;

    //Смещение при передвижении на клавиатуре
    private float speed = SPEED;

    //Текущая позиция
    private Vector2 positionObj;
    //Позиция перемещения
    private Vector2 posTo;
    //Скорость перемещения
    private Vector2 speedV;

    public Logotip(TextureRegion region) {
        super(region);
        this.positionObj = new Vector2();
        this.posTo = new Vector2(positionObj);
        this.speedV = new Vector2();
    }

    public Logotip(TextureRegion region, float speed) {
        this(region);
        this.speed = speed;
    }

    private void changePosToKeyDown(float x, float y) {
        this.posTo.add(this.speed * x, this.speed * y);
        //Рассчитаем новую скорость объекта
        calcSpeed();
    }

    private void changePosToKeyDown(Vector2 vectorTo) {
        this.posTo.add(vectorTo.cpy().scl(speed));
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
        this.speedV.set(posTo).sub(positionObj).setLength(speed);
    }

    private void stopActionObject() {
        this.posTo.set(positionObj);
    }


    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(regions[frame], positionObj.x, positionObj.y, halfWidth, halfHeight);
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(OBJECT_SIZE_PROPORC);
        positionObj.set(worldBounds.getLeft() + halfWidth,worldBounds.getBottom() + halfHeight);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        //changePosToTouchDown(touch.x, touch.y);
        changePosToTouchDown(touch);
        return false;
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
    public void update(float delta) {
        super.update(delta);

        if ((speedV.x < 0 && positionObj.x > posTo.x)
                || (speedV.x > 0 && positionObj.x < posTo.x)
                || (speedV.y < 0 && positionObj.y > posTo.y)
                || (speedV.y > 0 && positionObj.y < posTo.y)
        ) {
            this.positionObj.add(speedV);
        }
        /*buf.set(posTo);
        if (buf.sub(positionObj).len() > SPEED) {
            this.positionObj.add(speedV);
        }*/ else {
            stopActionObject();
        }

    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }
}
