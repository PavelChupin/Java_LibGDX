package ru.homework.base;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.homework.math.Rect;

public abstract class StarShipBase extends Sprite {
    protected static final int INVALID_POINTER = -1;

    protected int leftPointer = INVALID_POINTER;
    protected int rightPointer = INVALID_POINTER;

    //Вектор направления для рассчетов
    protected Vector2 vectorTo = new Vector2();
    protected Vector2 vectorBorder = new Vector2(1f, 1f);
    protected boolean pressedRight;
    protected boolean pressedLeft;
    protected boolean pressedUp;
    protected boolean pressedDown;
    protected Rect worldBounts;


    public StarShipBase(TextureRegion region, int rows, int cols, int frames) {
        super(region, rows, cols, frames);
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounts = worldBounds;
    }


    public boolean touchDown(Vector2 touch, int pointer, int button) {
        return false;
    }

    public boolean touchUp(Vector2 touch, int pointer, int button) {
        return false;
    }

    public boolean touchDragged(Vector2 touch, int pointer) {
        return false;
    }

    public boolean keyDown(int keycode) {
        vectorTo.set(0, 0);
        frame = 1;

        switch (keycode) {
            case Input.Keys.DOWN: {
                //Установим направление движения
                vectorTo.set(0, -1);
                pressedDown = true;
                break;
            }
            case Input.Keys.UP: {
                //Установим направление движения
                vectorTo.set(0, 1);
                pressedUp = true;
                break;
            }
            case Input.Keys.LEFT: {
                //Установим направление движения
                vectorTo.set(-1, 0);
                pressedLeft = true;
                break;
            }
            case Input.Keys.RIGHT: {
                //Установим направление движения
                vectorTo.set(1, 0);
                pressedRight = true;
                break;
            }
            case Input.Keys.SPACE: {
                //Установим направление движения
                //vectorTo.set(0, 1);
                shoot();
                break;
            }
            default: {
                break;
            }
        }

        return false;
    }

    public boolean keyUp(int keycode) {
        frame = 0;

        switch (keycode) {
            case Input.Keys.DOWN: {
                pressedDown = false;
                if (pressedUp){vectorTo.set(0, 1);}
                break;
            }
            case Input.Keys.UP: {
                pressedUp = false;
                if (pressedDown){vectorTo.set(0, -1);}
                break;
            }
            case Input.Keys.LEFT: {
                pressedLeft = false;
                if (pressedRight){vectorTo.set(1, 0);}
                break;
            }
            case Input.Keys.RIGHT: {
                pressedRight = false;
                if (pressedLeft){vectorTo.set(-1, 0);}
                break;
            }
            default: {
                break;
            }
        }
        return false;
    }
}
