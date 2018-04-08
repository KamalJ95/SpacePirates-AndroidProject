package kamaljahah.rg014522.finalproject;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.Random;

/**
 * Created by Kamal on 27/03/2018.
 */

public class Enemy extends GameObject {

    private int score;
    private int speed;
    private Random rng = new Random();
    private Animation animation = new Animation();
    private Bitmap sprite;

    /**
     * Constructor for first enemy, will be a basic ship that flys slow towards player in direction -x
     * @param resource
     * @param x
     * @param y
     * @param width
     * @param height
     * @param s
     * @param numFrames
     */
    public Enemy(Bitmap resource, int x, int y, int width, int height, int s, int numFrames){

        super.x = x;
        super.y = y;
        this.width = width;
        this.height = height;
        score = s;

        //Speed of enemies increase as our score increases
        speed = 7 + (int) (rng.nextDouble()*score/30);

        //Make sure that the enemy cannot go faster than a certain speed
        if (speed > 40){
            speed = 40;
        }

        Bitmap[] image = new Bitmap[numFrames];

        sprite = resource;

        //For each element in the array (image)
        for (int i = 0; i < image.length; i++){
            image[i] = Bitmap.createBitmap(sprite,0,i*this.height,this.width,this.height);
        }

        animation.setFrames(image);
        //If enemy is faster
        animation.setDelay(100);



    }

    /**
     * Update function for enemy
     */
    public void update(){
        x-=speed;
        animation.update();

    }


    /**
     * Draw function for enemy
     * @param canvas
     */
    public void draw(Canvas canvas){


        try{
            canvas.drawBitmap(animation.getImage(),x,y,null);
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * Gets the width for enemy and is an offset for collision incase the tail collides
     * @return
     */
    @Override
    public int getWidth(){
        //Offset for collision incase tail collides
        return width-10;

    }
}
