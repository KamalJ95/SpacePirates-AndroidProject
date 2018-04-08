package kamaljahah.rg014522.finalproject;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.Random;

/**
 * Created by Kamal on 27/03/2018.
 */


//Attempt at making a secondary enemy that is faster
public class secondaryEnemy extends GameObject{

    private int score;
    private int speed;
    private Random rng = new Random();
    private Animation animation = new Animation();
    private Bitmap sprite;

    /**
     * Secondary enemy constructor, makes them faster and caps the speed at a higher rate but cap probably
     * wont need to be used because they come at increments of 150 for a set speed.
     * @param resource
     * @param x
     * @param y
     * @param width
     * @param height
     * @param s
     * @param numFrames
     */
    public secondaryEnemy(Bitmap resource, int x, int y, int width, int height, int s, int numFrames){

        super.x = x;
        super.y = y;
        this.width = width;
        this.height = height;
        score = s;

        //Speed of enemies increase as our score increases
        speed = 20 + (int) (rng.nextDouble()*score/30);

        //Make sure that the enemy cannot go faster than a certain speed
        if (speed > 80){
            speed = 80;
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
     * Update function
     */
    public void update(){
        x-=speed;
        animation.update();

    }


    /**
     * Draw function
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
     * Gets the width for offset of collision
     * @return
     */
    @Override
    public int getWidth(){
        return width-10;

    }
}
