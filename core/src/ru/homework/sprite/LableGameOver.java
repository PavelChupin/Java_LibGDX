package ru.homework.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.homework.base.Sprite;
import ru.homework.math.Rect;

public class LableGameOver extends Sprite {
    private static final float HEIGHT = 0.08f;

    public LableGameOver(TextureAtlas atlas) {
        super(atlas.findRegion("message_game_over"));
    }

    @Override
    public void resize(Rect worldBounds) {
        super.resize(worldBounds);
        setHeightProportion(HEIGHT);
    }
}
