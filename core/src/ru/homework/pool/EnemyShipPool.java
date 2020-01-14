package ru.homework.pool;

import ru.homework.base.SpritesPool;
import ru.homework.sprite.EnemyShip;

public class EnemyShipPool extends SpritesPool<EnemyShip> {
    @Override
    public EnemyShip newObject() {
        return new EnemyShip();
    }
}
