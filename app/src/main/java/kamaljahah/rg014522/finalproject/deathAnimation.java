package kamaljahah.rg014522.finalproject;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by Kamal on 28/03/2018.
 */


public class deathAnimation {
    private int x,y,width,height;
    private Animation animation = new Animation();
    private Bitmap sprite;


    /**
     * Animation called on collision with another object. Death Animation constructor
     * @param resource
     * @param x
     * @param y
     * @param w
     * @param h
     * @param numFrames
     */
    public deathAnimation(Bitmap resource, int x, int y, int w, int h, int numFrames) {
        this.x = x;
        this.y = y;
        this.width = w;
        this.height = h;

        Bitmap[] image = new Bitmap[numFrames];

        sprite = resource;



        for (int i = 0; i < image.length; i++) {
            if (i>0)
            image[i] = Bitmap.createBitmap(sprite, i-width, height, width, height);
        }
            animation.setFrames(image);
            animation.setDelay(10);



    }


    /**
     * Draw function for death animation - if the animation has not been played yet then the animation will play.
     * @param canvas
     */
    public void draw(Canvas canvas){

        if (!animation.initialPlayed()){
            canvas.drawBitmap(animation.getImage(),x,y,null);
        }

    }

    /**
     * If the animation has not been played will update
     */
    public void update(){
        if(!animation.initialPlayed())
        {
            animation.update();
        }
    }


    /**
     * Getter for height
     * @return
     */
    public int getHeight()
    {
        return height;
    }
}

