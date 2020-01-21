package ru.homework.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.homework.base.ScaledButton;
import ru.homework.math.Rect;
import ru.homework.screen.GameScreen;

public class ButtonNewGame extends ScaledButton {
    private GameScreen gameScreen;

    public ButtonNewGame(TextureAtlas atlas, GameScreen gameScreen) {
        super(atlas.findRegion("button_new_game"));
        this.gameScreen = gameScreen;
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportion(0.05f);
        setBottom(worldBounds.getBottom() + 0.05f);
    }

    @Override
    public void action() {
        gameScreen.setState(GameScreen.State.PLAYING);
    }
}
