package ru.homework.sprite;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.homework.base.Sprite;
import ru.homework.math.Rect;

public class Logotip extends Sprite {
    private static final float DIST = 0.01f;

    //Смещение при передвижении на клавиатуре
    private float dist = DIST;

    //Текущая позиция
    private Vector2 positionObj;
    //Позиция перемещения
    private Vector2 posTo;
    //Скорость перемещения
    private Vector2 speed;


    public Logotip(TextureRegion region) {
        super(region);
        this.positionObj = new Vector2();
        this.posTo = new Vector2();
        this.speed = new Vector2();
    }

    public Logotip(TextureRegion region, float dist) {
        this(region);
        this.dist = dist;
    }

    private void changePosToKeyDown(float x, float y) {
        this.posTo.add(this.dist * x, this.dist * y);
        //Рассчитаем новую скорость объекта
        calcSpeed();
    }

    private void changePosToKeyDown(Vector2 vectorTo) {
        this.posTo.add(vectorTo.cpy().scl(dist));
        //Рассчитаем новую скорость объекта
        calcSpeed();
    }

    private void changePosToTouchDown(float x, float y) {
        this.posTo.set(x, y);
        //Рассчитаем новую скорость объекта
        calcSpeed();
    }

    private void calcSpeed() {
        //Переделываем реализацию через setLenght, т.к. через nor() с мелкой сеткой float значений есть косяк.
        this.speed.set(posTo).sub(positionObj).setLength(dist);
    }

    @Override
    public void actionObject() {
        if ((speed.x < 0 && positionObj.x > posTo.x)
                || (speed.x > 0 && positionObj.x < posTo.x)
                || (speed.y < 0 && positionObj.y > posTo.y)
                || (speed.y > 0 && positionObj.y < posTo.y)
        ) {
            this.positionObj.add(speed);
        }
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
        setHeightProportion(worldBounds.getHeight());
        pos.set(worldBounds.pos);
    }

    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        changePosToTouchDown(touch.x, touch.y);
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        //Перехватим движение объекта в текущей точке
        stopActionObject();

        //Выполняем метод нажатия кнопки
        super.keyDown(keycode);

        //Меняем направление движения объекта
        changePosToKeyDown(vectorTo);

        return false;
    }

    public float getDist() {
        return dist;
    }

    public void setDist(float dist) {
        this.dist = dist;
    }
}
