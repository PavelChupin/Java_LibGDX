package ru.homework.utils;

public enum EnumEnemyGenerator {

    ENEMY_SMALL_HEIGHT(0.1f),
    ENEMY_SMALL_BULLET_HEIGHT(0.01f),
    ENEMY_SMALL_BULLET_VY(-0.3f),
    ENEMY_SMALL_DAMAGE (1f),
    ENEMY_SMALL_RELOAD_INTERVAL (3f),
    ENEMY_SMALL_HP ( 1f),

    ENEMY_MEDIUM_HEIGHT(0.15f),
    ENEMY_MEDIUM_BULLET_HEIGHT (0.02f),
    ENEMY_MEDIUM_BULLET_VY (-0.25f),
    ENEMY_MEDIUM_DAMAGE (5f),
    ENEMY_MEDIUM_RELOAD_INTERVAL (4f),
    ENEMY_MEDIUM_HP(5f),

    ENEMY_BIG_HEIGHT (0.2f),
    ENEMY_BIG_BULLET_HEIGHT (0.04f),
    ENEMY_BIG_BULLET_VY (-0.3f),
    ENEMY_BIG_DAMAGE(10f),
    ENEMY_BIG_RELOAD_INTERVAL(1f),
    ENEMY_BIG_HP(10f);

    private float value;

    public float getValue() {
        return value;
    }

    EnumEnemyGenerator(float value) {
        this.value = value;
    }
}
