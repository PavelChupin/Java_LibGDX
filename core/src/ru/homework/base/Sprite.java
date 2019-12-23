package ru.homework.base;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.homework.math.Rect;


public abstract class Sprite extends Rect implements SpriteImpl {
    protected float angle;
    protected float scale = 1f;
    protected TextureRegion[] regions;
    protected int frame;


    public Sprite(TextureRegion region) {
        if (region == null) {
            throw new NullPointerException("Region is null");
        }

        this.regions = new TextureRegion[1];
        this.regions[0] = region;
    }

    public void setHeightProportian(float height) {
        setHeight(height);
        float aspect = regions[frame].getRegionWidth() / (float) regions[frame].getRegionHeight();
        setWidth(height * aspect);
    }

    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(regions[frame], getLeft(), getBottom(), halfWidth, halfHeight, getWidth(), getHeight(), scale, scale, angle);
    }

    @Override
    public void actionObject() {

    }

    public void resize(Rect worldBounds) {

    }

    public void update(float delta) {

    }

    public boolean touchDown(Vector2 touch, int pointer, int button) {

        return false;
    }

    public boolean touchUp(Vector2 touch, int pointer, int button) {

        return false;
    }

    public boolean touchDragged(int screenX, int screenY, int pointer) {

        return false;
    }

    public float getAngle() {
        return angle;
    }

    public void setAngle(float angle) {
        this.angle = angle;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }
}
