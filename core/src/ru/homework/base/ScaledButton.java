package ru.homework.base;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.homework.math.Rect;

public abstract class ScaledButton extends Sprite {
    private static final float PRESS_SCALE = 0.9f;
    private boolean isPressed = false;
    private int pointer;

    public ScaledButton(TextureRegion region) {
        super(region);
    }


    @Override
    public boolean touchDown(Vector2 touch, int pointer, int button) {
        if (isPressed || !isMe(touch)){
            return false;
        }
        this.pointer = pointer;
        this.scale = PRESS_SCALE;
        this.isPressed = true;
        return false;
    }

    @Override
    public boolean touchUp(Vector2 touch, int pointer, int button) {
        if (this.pointer != pointer || ! this.isPressed) {
            return false;
        }
        if (isMe(touch)) {
            action();
        }
        this.isPressed = false;
        this.scale = 1f;
        return false;
    }

    @Override
    public  abstract void resize(Rect worldBounds);

    public abstract void action();
}

