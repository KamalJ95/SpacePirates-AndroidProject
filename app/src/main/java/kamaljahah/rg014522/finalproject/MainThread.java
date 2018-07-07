package kamaljahah.rg014522.finalproject;

import android.graphics.Canvas;
import android.view.SurfaceHolder;

/**
 * Created by Kamal on 26/03/2018.
 */

/**
 * Main thread where the gain will run from
 */
public class MainThread extends Thread {

    //Limit unnecessary calls to game loop, also for optimsation
    private static final int MAX_FPS = 30;

    //Average FPS
    private double avgFPS;
    private SurfaceHolder holder;
    private GameView panel;
    private boolean running;
    public static Canvas canvas;


    /**
     * Main thread constructor
     * @param holder
     * @param panel
     */
    public MainThread(SurfaceHolder holder, GameView panel) {
        super();
        this.holder = holder;
        this.panel = panel;
    }

    /**
     * Run function that is called everytime the game starts
     */
    @Override
    public void run(){
        long startTime;
        long millisecs = 1000/MAX_FPS; //1000 MILLISECONDS IS A SECOND
        long waitTime;
        int frameCount = 0;
        long totalTime = 0;
        long targetTime = 1000/MAX_FPS;


        while(running){
            //Accurate clock built into systems hardware.
            startTime = System.nanoTime();
            canvas = null;

            try {
                canvas = this.holder.lockCanvas();
                synchronized (holder){

                    //Update will change the objects position of screen, draw will look at coordinates and draw on screen
                    this.panel.update();
                    this.panel.draw(canvas);
                }
            }catch (Exception e) {
                e.printStackTrace();
            } finally {
                if(canvas != null){
                    try {
                        holder.unlockCanvasAndPost(canvas);
                    } catch (Exception e){
                        e.printStackTrace();
                    }
                }


            }
            millisecs = (System.nanoTime() - startTime)/1000000;
            waitTime = targetTime - millisecs;
            try {
                if (waitTime > 0)
                    this.sleep(waitTime);
            }catch (Exception e) {
                e.printStackTrace();
            }
            totalTime = System.nanoTime() - startTime;
            frameCount++;

            //Converting from nanoseconds to milliseconds then dividing time took into average fps
            if (frameCount == MAX_FPS){
                avgFPS = 1000/((totalTime/frameCount)/1000000);
                frameCount = 0;
                totalTime = 0;
                System.out.print(avgFPS);
            }
        }
    }

    /**
     * Setter for running
     * @param running
     */
    public void setRunning(boolean running){

        this.running = running;
    }

}
