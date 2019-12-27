package ru.homework.sprite;

import com.badlogic.gdx.graphics.g2d.TextureRegion;

import ru.homework.base.Sprite;
import ru.homework.math.Rect;

public class Background extends Sprite {


    public Background(TextureRegion region) {
        super(region);
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(worldBounds.getHeight());
        pos.set(worldBounds.pos);
    }

}
