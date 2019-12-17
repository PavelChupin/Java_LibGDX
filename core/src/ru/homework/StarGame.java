package ru.homework;

import com.badlogic.gdx.ApplicationAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;

public class StarGame extends ApplicationAdapter {
    SpriteBatch batch;
    Texture img;
    Texture backGround;
    //TextureRegion region;

    @Override
    public void create() {
        batch = new SpriteBatch();
        img = new Texture("badlogic.jpg");
        backGround = new Texture("starBackGround.jpg");
        //region = new TextureRegion(img,20,20,100,80);
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0.2f, 0.6f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        //Отрисовываем фон
        //batch.setColor(1,1,1,1f);
        batch.draw(backGround, 0, 0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

        //batch.setColor(0.3f,0.5f,0.6f,1f);
        //batch.draw(img, 10, 10);
        //batch.draw(region,300,300);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        img.dispose();
    }
}
