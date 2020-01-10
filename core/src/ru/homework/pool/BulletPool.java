package ru.homework.pool;

import ru.homework.base.SpritesPool;
import ru.homework.sprite.Bullet;

public class BulletPool extends SpritesPool<Bullet> {
    @Override
    public Bullet newObject() {
        return new Bullet();
    }
}