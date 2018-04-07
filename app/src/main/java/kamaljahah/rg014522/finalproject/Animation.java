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

    public void setFrames(Bitmap[] frames){
        this.frames = frames;
        currentFrame = 0;
        startTime = System.nanoTime();
    }


    //If want to set a delay
    public void setDelay(long d){
        delay = d;
    }

    //If manually want to set the frame
    public void setFrames(int frameSet){
        currentFrame = frameSet;
    }


    //Will set the frames and which image to return in the array.
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

    public Bitmap getImage(){
        return frames[currentFrame];
    }

    public int getFrame(){
        return currentFrame;
    }

    public boolean initialPlayed(){
       return initial;
    }



}
