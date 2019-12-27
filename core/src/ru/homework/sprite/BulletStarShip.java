package ru.homework.sprite;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.homework.base.Sprite;
import ru.homework.math.Rect;

public class BulletStarShip extends Sprite {
    private static final float SPEED = 0.1f;
    private static final float OBJECT_SIZE_PROPORC = 1f;

    private float speed = SPEED;
    private Vector2 speedV;
    //Позиция перемещения
    private Vector2 posTo;


    public BulletStarShip(Vector2 startPosition) {
        super(new TextureAtlas(Gdx.files.internal("textures/mainAtlas.tpack")).findRegion("bulletMainShip"));
        this.pos.set(startPosition);
        this.posTo = new Vector2(pos);
        this.speedV = new Vector2();
    }

    public BulletStarShip(Vector2 startPosition, float speed) {
        this(startPosition);
        this.speed = speed;
    }


    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(regions[frame], pos.x, pos.y, halfWidth, halfHeight);
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(OBJECT_SIZE_PROPORC);
    }

    @Override
    public void update(float delta) {
        this.pos.add(speedV);
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }
}
