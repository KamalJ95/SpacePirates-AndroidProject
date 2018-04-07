package kamaljahah.rg014522.finalproject;

import android.graphics.Bitmap;
import android.graphics.Canvas;

import java.util.Random;

/**
 * Created by Kamal on 31/03/2018.
 */

public class thirdShip extends GameObject {

    private int score;
    private int speed;
    private Random rng = new Random();
    private Animation animation = new Animation();
    private Bitmap sprite;

    public thirdShip (Bitmap resource, int x, int y, int width, int height, int s, int numFrames){

        super.x = x;
        super.y = y;
        this.width = width;
        this.height = height;
        score = s;

        //Speed of enemies increase as our score increases
        speed = 30 + (int) (rng.nextDouble()*score/30);

        //Make sure that the enemy cannot go faster than a certain speed
        if (speed > 60){
            speed = 60;
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


    public void update(){
        x-=speed;
        animation.update();

    }



    public void draw(Canvas canvas){


        try{
            canvas.drawBitmap(animation.getImage(),x,y,null);
        } catch (Exception e){
            e.printStackTrace();
        }

    }

    @Override
    public int getWidth(){
        //Offset for collision incase tail collides
        return width-10;

    }
}
