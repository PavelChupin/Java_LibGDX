package ru.homework.gameobjects;

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


    public void calcSpeed() {
        speed.set(posTo).sub(pos).nor();
    }

    public void actionObject() {
        if ((speed.x < 0 && pos.x > posTo.x)
                || (speed.x > 0 && pos.x < posTo.x)
                || (speed.y < 0 && pos.y > posTo.y)
                || (speed.y > 0 && pos.y < posTo.y)
        ) {
            pos.add(speed);
        }
    }

    public void stopActionObject() {
        posTo.set(pos);
    }

    public void dispose() {
        img.dispose();
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

    public Logotip setPos(Vector2 pos) {
        this.pos = pos;
        return this;
    }

    public Logotip setPosTo(Vector2 posTo) {
        this.posTo = posTo;
        return this;
    }

    public Logotip setSpeed(Vector2 speed) {
        this.speed = speed;
        return this;
    }

    public Logotip setDist(float dist) {
        this.dist = dist;
        return this;
    }
}
