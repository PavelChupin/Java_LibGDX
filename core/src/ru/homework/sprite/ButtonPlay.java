package ru.homework.sprite;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.homework.base.ScaledButton;
import ru.homework.math.Rect;
import ru.homework.screen.GameScreen;

public class ButtonPlay extends ScaledButton {
    private Game game;

    public ButtonPlay(TextureAtlas atlas, Game game) {
        super(atlas.findRegion("btPlay"));
        this.game = game;
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.15f);
        setRight(worldBounds.getLeft() + 0.15f);
        setBottom(worldBounds.getBottom() + 0.05f);
    }

    @Override
    public void action() {
        game.setScreen(new GameScreen());
    }
}
