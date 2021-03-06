package ru.homework.base;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Matrix3;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;
import java.util.List;

import ru.homework.math.MatrixUtils;
import ru.homework.math.Rect;
import ru.homework.sprite.Background;
import ru.homework.sprite.Star;

public abstract class BaseScreen implements Screen, InputProcessor {
    protected SpriteBatch batch;
    private Music music;

    private Rect screenBounds;
    protected Rect worldBounds;
    private Rect glBounds;

    private Matrix4 worldToGl;
    private Matrix3 screenToWorld;

    protected Vector2 touch;

    protected Texture bg;
    protected Background background;
    protected TextureAtlas atlas;

    protected List<Sprite> sprites = new ArrayList();
    protected Star[] stars;

    @Override
    public void show() {
        System.out.println("show");
        batch = new SpriteBatch();
        bg = new Texture("textures/starBackGround.jpg");
        background = new Background(new TextureRegion(bg));

        screenBounds = new Rect();
        worldBounds = new Rect();
        glBounds = new Rect(0, 0, 1f, 1f);
        worldToGl = new Matrix4();
        screenToWorld = new Matrix3();
        touch = new Vector2();
        //Подключаем класс с методами отслеживания нажатия кнопок на клавиатуре, мышке или точпаде
        Gdx.input.setInputProcessor(this);

        //Подулючаем музыкальное сопровождение
        music = Gdx.audio.newMusic(Gdx.files.internal("sounds/music.mp3"));
        music.setLooping(true);
        music.play();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.2f, 0.6f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        //Отрисовываем фон
        background.draw(batch);
        batch.end();
    }

    @Override
    public void resize(int width, int height) {
        System.out.println("resize width = " + width + " height " + height);
        screenBounds.setSize(width, height);
        screenBounds.setLeft(0);
        screenBounds.setBottom(0);

        //Пропорция текущего соотношения сторон экрана
        float aspect = width / (float) height;

        //Задаем размер своего экрана
        worldBounds.setHeight(1f);
        worldBounds.setWidth(1f * aspect);

        //Расчитываем матрицу
        MatrixUtils.calcTransitionMatrix(worldToGl, worldBounds, glBounds);
        MatrixUtils.calcTransitionMatrix(screenToWorld, screenBounds, worldBounds);

        //Устанавливаем свою матрицу
        batch.setProjectionMatrix(worldToGl);
        resize(worldBounds);
    }


    public void resize(Rect worldBounds) {
        System.out.println("resize width = " + worldBounds.getWidth() + " height " + worldBounds.getHeight());
        background.resize(worldBounds);
    }

    @Override
    public void pause() {
        System.out.println("pause");
    }

    @Override
    public void resume() {
        System.out.println("resume");
    }

    @Override
    public void hide() {
        System.out.println("hide");
        dispose();
    }

    @Override
    public void dispose() {
        System.out.println("dispose");
        batch.dispose();
        bg.dispose();
        atlas.dispose();
        music.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        System.out.println("keyDown keycode = " + keycode);
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        System.out.println("keyUp keycode = " + keycode);
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        System.out.println("keyTyped character = " + character);
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        System.out.println("touchDown screenX = " + screenX + " screenY " + screenY + " pointer " + pointer + " button " + button);
        setTouch(screenX, screenY);
        touchDown(touch, pointer, button);
        return false;
    }

    public boolean touchDown(Vector2 touch, int pointer, int button) {
        System.out.println("touchDown touchX = " + touch.x + " touchY " + touch.y + " pointer " + pointer + " button " + button);
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        System.out.println("touchUp screenX = " + screenX + " screenY " + screenY + " pointer " + pointer + " button " + button);
        setTouch(screenX, screenY);
        touchUp(touch, pointer, button);
        return false;
    }

    public boolean touchUp(Vector2 touch, int pointer, int button) {
        System.out.println("touchUp touchX = " + touch.x + " touchY " + touch.y + " pointer " + pointer + " button " + button);
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        System.out.println("touchDragged screenX = " + screenX + " screenY " + screenY + " pointer " + pointer);
        setTouch(screenX, screenY);
        touchDragged(touch, pointer);
        return false;
    }

    public boolean touchDragged(Vector2 touch, int pointer) {
        System.out.println("touchDragged touchX = " + touch.x + " touchY " + touch.y + " pointer " + pointer);
        return false;
    }

    private void setTouch(int screenX, int screenY) {
        touch.set(screenX, Gdx.graphics.getHeight() - screenY).mul(screenToWorld);
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        System.out.println("scrolled amount = " + amount);
        return false;
    }

}
