package ru.homework.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Vector2;
import ru.homework.base.BaseScreen;

public class MenuScreen extends BaseScreen {
    private static final float DIST = 10f;
    private Texture img;
    private Texture backGround;
    private Vector2 pos;
    private Vector2 posTo;
    private Vector2 speed;

    @Override
    public void show() {
        super.show();

        img = new Texture("badlogic.jpg");
        backGround = new Texture("textures/starBackGround.jpg");
        pos = new Vector2();
        posTo = new Vector2();
        speed = new Vector2();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(0.2f, 0.6f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        //Отрисовываем фон
        //batch.setColor(1,1,1,1f);
        batch.draw(backGround, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        //batch.setColor(0.3f,0.5f,0.6f,1f);
        batch.draw(img, pos.x, pos.y);
        //batch.draw(region,300,300);
        batch.end();

        actionObject(this.pos);
    }

    @Override
    public void dispose() {
        img.dispose();
        backGround.dispose();

        super.dispose();
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        System.out.println("touchDown screenX = " + screenX + " screenY " + screenY + " pointer " + pointer + " button " + button);
        //Задаем точку до которой требуется двигаться
        posTo.set(screenX, Gdx.graphics.getHeight() - screenY);
        //Рассчитываем вектор скорости
        calcSpeed(this.pos);
        //pos.set(screenX,Gdx.graphics.getHeight() - screenY);
        return false;
    }

    @Override
    public boolean keyDown(int keycode) {
        System.out.println("keyDown keycode = " + keycode);
        switch (keycode) {
            case Input.Keys.DOWN: {
                //Перехватим картинку
                posTo.set(pos);
                posTo.y -= DIST;
                calcSpeed(this.pos);
                break;
            }
            case Input.Keys.UP: {
                //Перехватим картинку
                posTo.set(pos);
                posTo.y += DIST;
                calcSpeed(this.pos);
                break;
            }
            case Input.Keys.LEFT: {
                //Перехватим картинку
                posTo.set(pos);
                posTo.x -= DIST;
                calcSpeed(this.pos);
                break;
            }
            case Input.Keys.RIGHT: {
                //Перехватим картинку
                posTo.set(pos);
                posTo.x += DIST;
                calcSpeed(this.pos);
                break;
            }
            default: {
                break;
            }
        }

        return false;//super.keyDown(keycode);
    }

    private void calcSpeed(Vector2 obj) {
        /*Vector2 v = new Vector2(1, 1);
        if (posTo.x < obj.x) {
            v.x = -1;
        }
        if (posTo.y < obj.y) {
            v.y = -1;
        }*/
        //speed.set(posTo).nor().scl(v);
        speed.set(posTo).sub(obj).nor();
        //System.out.println(speed);
    }

    private void actionObject(Vector2 obj) {
        if ((speed.x < 0 && obj.x > posTo.x)
                || (speed.x > 0 && obj.x < posTo.x)
                || (speed.y < 0 && obj.y > posTo.y)
                || (speed.y > 0 && obj.y < posTo.y)
        ) {
            obj.add(speed);
        }
        //    if (/*!pos.equals(posTo) ||*/ (pos.x < posTo.x && pos.y < posTo.y)) {
        //   pos.add(speed);
        //}
    }
}
