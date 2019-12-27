package ru.homework.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.math.Vector2;

import ru.homework.base.Sprite;
import ru.homework.math.Rect;
import ru.homework.math.Rnd;

public class Star extends Sprite {
    private Vector2 speedV;
    private Rect worldBounds;
    private float starAnimateInterval;
    private float starAnimateTimer;
    private float hight;

    public Star(TextureAtlas atlas) {
        super(atlas.findRegion("star"));

        //Задачем вектор скорости звезд
        float vx = Rnd.nextFloat(-0.005f, 0.005f);
        float vy = Rnd.nextFloat(-0.05f, -0.01f);
        this.speedV = new Vector2(vx, vy);
        this.starAnimateInterval = 1f;
        this.starAnimateTimer = Rnd.nextFloat(0, 1f);
        this.hight = 0.01f;
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        float posx = Rnd.nextFloat(worldBounds.getLeft(), worldBounds.getRight());
        float posy = Rnd.nextFloat(worldBounds.getBottom(), worldBounds.getTop());
        pos.set(posx, posy);
        setHeightProportion(this.hight);
    }

    @Override
    public void update(float delta) {
        pos.mulAdd(speedV, delta);
        //Проверяем границ
        checkBounds();
        this.hight += 0.00001f;
        setHeightProportion(hight);
        //Добавляем мерцания
        starAnimateTimer += delta;
        if (starAnimateTimer > starAnimateInterval) {
            starAnimateTimer = 0f;
            hight = 0.01f;
        }
    }

    private void checkBounds() {
        if (getRight() < worldBounds.getLeft()) {
            setLeft(worldBounds.getRight());
        }
        if (getLeft() > worldBounds.getRight()) {
            setRight(worldBounds.getLeft());
        }
        if (getTop() < worldBounds.getBottom()) {
            setBottom(worldBounds.getTop());
        }
        if (getBottom() > worldBounds.getTop()) {
            setTop(worldBounds.getBottom());
        }
    }
}
