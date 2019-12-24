package ru.homework.base;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.homework.math.Rect;


public abstract class Sprite extends Rect  {
    //Вектор направления для рассчетов
    protected Vector2 vectorTo = new Vector2();

    protected float angle;
    protected float scale = 1f;
    protected TextureRegion[] regions;
    protected int frame;


    public Sprite(TextureRegion region) {
        if (region == null) {
            throw new NullPointerException("Region is null");
        }
        regions = new TextureRegion[1];
        regions[0] = region;
    }

    public void setHeightProportion(float height) {
        setHeight(height);
        float aspect = regions[frame].getRegionWidth() / (float) regions[frame].getRegionHeight();
        setWidth(height * aspect);
    }

    public void actionObject(){};

    public abstract void draw(SpriteBatch batch);

    public abstract void resize(Rect worldBounds);

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

    public boolean keyDown(int keycode) {
        vectorTo.set(0, 0);

        switch (keycode) {
            case Input.Keys.DOWN: {
                //Установим направление движения
                vectorTo.set(0, -1);
                break;
            }
            case Input.Keys.UP: {
                //Установим направление движения
                vectorTo.set(0, 1);
                break;
            }
            case Input.Keys.LEFT: {
                //Установим направление движения
                vectorTo.set(-1, 0);
                break;
            }
            case Input.Keys.RIGHT: {
                //Установим направление движения
                vectorTo.set(1, 0);
                break;
            }
            default: {
                break;
            }
        }

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
