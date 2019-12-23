package ru.homework.sprite;

import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.homework.base.Sprite;
import ru.homework.math.Rect;

public class Logotip extends Sprite {
        private static final float DIST = 0.01f;

        //Смещение при передвижении на клавиатуре
        private float dist = DIST;

        //Текущая позиция
        private Vector2 positionObj;
        //Позиция перемещения
        private Vector2 posTo;
        //Скорость перемещения
        private Vector2 speed;

        public Logotip(TextureRegion region) {
            super(region);
            this.positionObj = new Vector2();
            this.posTo = new Vector2();
            this.speed = new Vector2();
        }

        public Logotip(TextureRegion region, float dist) {
            this(region);
            this.dist = dist;
        }

        public void changePosToKeyDown(float x, float y) {
            this.posTo.add(this.dist * x, this.dist * y);
            //Рассчитаем новую скорость объекта
            calcSpeed();
        }

        public void changePosToKeyDown(Vector2 vectorTo) {
            this.posTo.add(vectorTo.cpy().scl(dist));
            //Рассчитаем новую скорость объекта
            calcSpeed();
        }

        public void changePosToTouchDown(float x, float y) {
            this.posTo.set(x, y);
            //Рассчитаем новую скорость объекта
            calcSpeed();
        }

        private void calcSpeed() {
            this.speed.set(posTo).sub(positionObj).nor();
        }

        @Override
        public void actionObject() {
            if ((speed.x < 0 && positionObj.x > posTo.x)
                    || (speed.x > 0 && positionObj.x < posTo.x)
                    || (speed.y < 0 && positionObj.y > posTo.y)
                    || (speed.y > 0 && positionObj.y < posTo.y)
            ) {
                this.positionObj.add(speed);
            }
        }


        public void stopActionObject() {
            this.posTo.set(positionObj);
        }


        public float getPosX() {
            return positionObj.x;
        }

        public float getPosY() {
            return positionObj.y;
        }

        public float getDist() {
            return dist;
        }



    /* Убираем что бы защитить из вне координаты векторов.
    Изменения допускаем только из методов объекта.
    public Vector2 getPos() {
        return positionObj;
    }

    public Vector2 getSpeed() {
        return speed;
    }

    public Vector2 getPosTo() {
        return posTo;
    }*/


        public Logotip setPositionObj(Vector2 v) {
            this.positionObj = v;
            return this;
        }

        public Logotip setPosTo(Vector2 v) {
            this.posTo = v;
            return this;
        }

        public Logotip setSpeed(Vector2 v) {
            this.speed = v;
            return this;
        }

        public Logotip setDist(float d) {
            this.dist = d;
            return this;
        }


    @Override
    public void draw(SpriteBatch batch) {
        batch.draw(regions[frame], positionObj.x, positionObj.y);
    }

    @Override
    public void resize(Rect worldBounds) {
        setHeightProportian(worldBounds.getHeight());
        pos.set(worldBounds.pos);
    }
}
