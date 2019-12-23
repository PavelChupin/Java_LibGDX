package ru.homework.gameobjects;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Logotip implements GameObjects {
    private static final float DIST = 10f;

    //Смещение при передвижении на клавиатуре
    private float dist;

    private Texture img;
    //Текущая позиция
    private Vector2 pos;
    //Позиция перемещения
    private Vector2 posTo;
    //Скорость перемещения
    private Vector2 speed;

    private Logotip() {
        this.pos = new Vector2();
        this.posTo = new Vector2();
        this.speed = new Vector2();
    }

    public Logotip(String img) {
        this();
        this.img = new Texture(img);
        this.dist = DIST;
    }

    public Logotip(String img, float dist) {
        this();
        this.img = new Texture(img);
        this.dist = dist;

    }

    public void changePosToKeyDown(float x, float y) {
        this.posTo.add(this.dist * x, this.dist * y);
        //Рассчитаем новую скорость объекта
        calcSpeed();
    }

    public void changePosToKeyDown(Vector2 vectorTo) {
        this.posTo.add(vectorTo.cpy().scl(dist));
        //Рассчитаем новую скорость объекта
        calcSpeed();
    }

    public void changePosToTouchDown(float x, float y) {
        this.posTo.set(x, y);
        //Рассчитаем новую скорость объекта
        calcSpeed();
    }

    private void calcSpeed() {
        this.speed.set(posTo).sub(pos).nor();
    }

    @Override
    public void actionObject() {
        if ((speed.x < 0 && pos.x > posTo.x)
                || (speed.x > 0 && pos.x < posTo.x)
                || (speed.y < 0 && pos.y > posTo.y)
                || (speed.y > 0 && pos.y < posTo.y)
        ) {
            this.pos.add(speed);
        }
    }

    public void stopActionObject() {
        this.posTo.set(pos);
    }

    public void dispose() {
        this.img.dispose();
    }

    public float getPosX() {
        return pos.x;
    }

    public float getPosY() {
        return pos.y;
    }

    public float getDist() {
        return dist;
    }


    public Texture getImg() {
        return img;
    }

    /* Убираем что бы защитить из вне координаты векторов.
    Изменения допускаем только из методов объекта.
    public Vector2 getPos() {
        return pos;
    }

    public Vector2 getSpeed() {
        return speed;
    }

    public Vector2 getPosTo() {
        return posTo;
    }*/

    public Logotip setImg(Texture t) {
        this.img = t;
        return this;
    }

    public Logotip setPos(Vector2 v) {
        this.pos = v;
        return this;
    }

    public Logotip setPosTo(Vector2 v) {
        this.posTo = v;
        return this;
    }

    public Logotip setSpeed(Vector2 v) {
        this.speed = v;
        return this;
    }

    public Logotip setDist(float d) {
        this.dist = d;
        return this;
    }

}
