package ru.homework.utils;

public enum EnumEnemyGenerator {

    ENEMY_SMALL_HEIGHT(0.1f),
    ENEMY_SMALL_BULLET_HEIGHT(0.01f),
    ENEMY_SMALL_BULLET_VY(-0.3f),
    ENEMY_SMALL_DAMAGE (1),
    ENEMY_SMALL_RELOAD_INTERVAL (3.0f),
    ENEMY_SMALL_HP ( 1),

    ENEMY_MEDIUM_HEIGHT(0.15f),
    ENEMY_MEDIUM_BULLET_HEIGHT (0.02f),
    ENEMY_MEDIUM_BULLET_VY (-0.25f),
    ENEMY_MEDIUM_DAMAGE (5),
    ENEMY_MEDIUM_RELOAD_INTERVAL (4.0f),
    ENEMY_MEDIUM_HP(5),

    ENEMY_BIG_HEIGHT (0.2f),
    ENEMY_BIG_BULLET_HEIGHT (0.04f),
    ENEMY_BIG_BULLET_VY (-0.3f),
    ENEMY_BIG_DAMAGE(10),
    ENEMY_BIG_RELOAD_INTERVAL(1.0f),
    ENEMY_BIG_HP(10);

    private float value;

    public float getValue() {
        return value;
    }

    EnumEnemyGenerator(float value) {
        this.value = value;
    }
}
