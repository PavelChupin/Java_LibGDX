package ru.homework.gameobjects;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;

public class Logotip {
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

    public Logotip(){
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

    public void changePosToKeyDown(int x, int y) {
        this.posTo.y += this.dist*y;
        this.posTo.x += this.dist*x;
        //Рассчитаем новую скорость объекта
        calcSpeed();
    }

    public void changePosToTouchDown(int x, int y) {
        this.posTo.set(x, y);
        //Рассчитаем новую скорость объекта
        calcSpeed();
    }

    private void calcSpeed() {
        this.speed.set(posTo).sub(pos).nor();
    }

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

    public float getDist() {
        return dist;
    }


    public Texture getImg() {
        return img;
    }

    public Vector2 getPos() {
        return pos;
    }

    public Vector2 getSpeed() {
        return speed;
    }

    public Vector2 getPosTo() {
        return posTo;
    }

    public Logotip setImg(Texture img) {
        this.img = img;
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
