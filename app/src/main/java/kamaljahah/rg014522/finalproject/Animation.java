package kamaljahah.rg014522.finalproject;

import android.graphics.Bitmap;

/**
 * Created by Kamal on 26/03/2018.
 */

public class Animation {

    //bitmap array for smoother transition
    private Bitmap[] frames;

    private int currentFrame;
    private long startTime;
    private long delay;
    private boolean initial;


    /**
     * Function for setting the frames and resetting the time for each frame
     * @param frames
     */
    public void setFrames(Bitmap[] frames){
        this.frames = frames;
        currentFrame = 0;
        startTime = System.nanoTime();
    }


    /**
     * Function for setting delays for objects
     * @param d
     */
    public void setDelay(long d){
        delay = d;
    }

    /**
     * Sets the frames and which image to return
     */
    public void update(){

        long timePassed = (System.nanoTime()-startTime)/1000000;

        if (timePassed>delay){
            currentFrame++;
            startTime = System.nanoTime();

        }

        if (currentFrame == frames.length){
            currentFrame = 0;
            initial = true;
        }
    }

    /**
     * Returns the frames
     * @return
     */
    public Bitmap getImage(){
        return frames[currentFrame];
    }


    /**
     * Boolean to check if game has started once
     * @return
     */
    public boolean initialPlayed(){
       return initial;
    }



}
