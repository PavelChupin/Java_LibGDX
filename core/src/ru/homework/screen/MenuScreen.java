package ru.homework.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import ru.homework.base.BaseScreen;

public class MenuScreen extends BaseScreen {

    private Texture img;
    private Texture backGround;
    private Vector2 pos;
    private Vector2 speed;

    @Override
    public void show() {
        super.show();

        img = new Texture("badlogic.jpg");
        backGround = new Texture("textures/starBackGround.jpg");
        pos= new Vector2();
        speed = new Vector2(0.5f,0.5f);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(0.2f, 0.6f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        //Отрисовываем фон
        //batch.setColor(1,1,1,1f);
        batch.draw(backGround, 0, 0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

        //batch.setColor(0.3f,0.5f,0.6f,1f);
        batch.draw(img, pos.x, pos.y);
        //batch.draw(region,300,300);
        batch.end();

        pos.add(speed);
    }

    @Override
    public void dispose() {
        img.dispose();
        backGround.dispose();

        super.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        pos.set(screenX,Gdx.graphics.getHeight() - screenY);
        return false;
    }
}
