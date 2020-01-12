package ru.homework.sprite;

import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

import ru.homework.base.Sprite;
import ru.homework.math.Rect;
import ru.homework.math.Rnd;

public class EnemyShip extends Sprite {
    private static final float OBJECT_SIZE_PROPORC = 0.13f;

    private Vector2 speedV;
    private int damage;
    private Rect worldBounds;


    public EnemyShip() {
        regions = new TextureRegion[1];
        speedV = new Vector2();
    }

    public EnemyShip(TextureAtlas atlas, Vector2 speedV, int damage) {
        super(atlas.findRegion("enemy1"), 1, 2, 2);

        this.damage = 1;
        this.speedV = new Vector2();

    }

    public void set(
            //TextureRegion region,
            //float posx,
            //Vector2 pos0,
            Vector2 speedV,
            //float height,
            //Rect worldBounds,
            int damage
    ) {
        //this.regions = Regions.split(region,1,2,2);
        //this.pos.set(x,worldBounds.getTop());
        this.speedV.set(speedV);
        //setHeightProportion(OBJECT_SIZE_PROPORC);
        //this.worldBounds = worldBounds;
        this.damage = damage;
        startPosition();
        //this.posx = posx;
    }

    private void startPosition() {
        float posx = Rnd.nextFloat(worldBounds.getLeft(), worldBounds.getRight());
        pos.set(posx, worldBounds.getTop() + getHalfHeight());
    }

    @Override
    public void update(float delta) {
        pos.mulAdd(speedV, delta);
        if (isOutside(worldBounds)) {
            destroy();
        }
    }

    @Override
    public void resize(Rect worldBounds) {
        this.worldBounds = worldBounds;
        setHeightProportion(OBJECT_SIZE_PROPORC);
        startPosition();
    }

}
