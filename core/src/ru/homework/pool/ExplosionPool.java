package ru.homework.pool;

import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;

import ru.homework.base.SpritesPool;
import ru.homework.sprite.Explosion;

public class ExplosionPool extends SpritesPool<Explosion> {
    private TextureAtlas atlas;
    private Sound explosionSound;

    public ExplosionPool(TextureAtlas atlas, Sound explosionSound) {
        this.atlas = atlas;
        this.explosionSound = explosionSound;
    }

    @Override
    public Explosion newObject() {
        return new Explosion(atlas, explosionSound);
    }
}
