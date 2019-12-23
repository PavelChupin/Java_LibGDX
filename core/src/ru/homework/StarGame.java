package ru.homework;

import com.badlogic.gdx.Game;

import ru.homework.screen.MenuScreen;

public class StarGame extends Game {
    /*SpriteBatch batch;
    Texture img;
    Texture bg;
    //TextureRegion region;
    */


    @Override
    public void create() {
        setScreen(new MenuScreen());
       /* batch = new SpriteBatch();
        img = new Texture("badlogic.jpg");
        bg = new Texture("starBackGround.jpg");
        //region = new TextureRegion(img,20,20,100,80);

        //Операции с векторами
        opersWithVector();*/
    }


    /*@Override
    public void render() {
        Gdx.gl.glClearColor(0.2f, 0.6f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.begin();
        //Отрисовываем фон
        //batch.setColor(1,1,1,1f);
        batch.draw(bg, 0, 0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

        //batch.setColor(0.3f,0.5f,0.6f,1f);
        //batch.draw(img, 10, 10);
        //batch.draw(region,300,300);
        batch.end();
    }*/

   /* @Override
    public void dispose() {
        batch.dispose();
        img.dispose();
        bg.dispose();
    }*/
/*
    private void opersWithVector() {
        Vector2 v1 = new Vector2(1,1);
        Vector2 v2 = new Vector2(1,2);

        //Сложение векторов
        v1.add(v2);
        System.out.println("v1 add v2" + v1);

        // Вычетание векторов
        v1.set(4,3);
        v2.set(2,1);
        v2.sub(v1);
        System.out.println("v1 sub v2" + v2);
        System.out.println("v2 len " + v2.len());

        // Скаляр вектора
        v1.set(10,100);
        v1.scl(0.9f);
        System.out.println("v1 scl 0.9" + v1);
        v1.scl(2);
        System.out.println("v1 scl 2" + v1);

        // Нормализация вектора
        v1.set(4,3);
        System.out.println("v1 len " + v1.len());
        v1.nor();
        System.out.println("v1 len " + v1.len());

        // Операция без изменения значений целевого вектора
        v1.set(4,3);
        v2.set(2,1);
        Vector2 v3 = v1.cpy().add(v2);
        System.out.println("v1 cpy.add v2" + v1);
        System.out.println("v3  " + v3);

        //Скалярное произведение векторов, расчет угла между векторами
        v1.set(1,1);
        v2.set(-1,1);
        v1.nor();
        v2.nor();
        System.out.println(Math.acos(v1.dot(v2)));
    }*/
}
