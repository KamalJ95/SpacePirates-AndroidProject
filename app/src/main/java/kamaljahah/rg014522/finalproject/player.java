package kamaljahah.rg014522.finalproject;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by Kamal on 26/03/2018.
 */

public class player extends GameObject{
    private Bitmap spite;
    private int score;
    private boolean goUp;
    private boolean running;
    private Animation animation = new Animation();
    private long startTime;


    public player(Bitmap resolution, int w, int h, int frames){
        x = 100;
        y = GameView.height/2;
        directionY = 0;
        score = 0;
        height = h;
        width = w;

        //Passing image into constructor, animation will scroll through images to create illusion that moving
        Bitmap[] image = new Bitmap[frames];
        spite = resolution;

        for (int i = 0; i < image.length; i++){
                    //split the bitmap image up
                    image[i] = Bitmap.createBitmap(spite, i*width, 0, width, height);
        }
        animation.setFrames(image);
        animation.setDelay(10);
        startTime = System.nanoTime();
    }

    //When press down, ship will fly up
    public void setUp(boolean b){
        goUp = b;
    }

    //Updates the score, every second the score will increase by one depending on how far across the player gets.
    public void update(){
        long timeGone = (System.nanoTime()- startTime) / 1000000;
        if (timeGone > 100){
            score++;
            startTime = System.nanoTime();
        }
        animation.update();

        if (goUp){
            //If go up is true, then acceleration will change
            directionY -= 1;
        }
        else {
            directionY += 1;
        }

        //Cap the speed of the spaceship

        if (directionY>10){
            directionY= 10;
        }
        if (directionY<-10){
            directionY= -10;
        }
        y +=directionY*2;
    }

    //Getters for score
    public int getScore(){
        return this.score;
    }

    //getter for animation running
    public boolean getRunning(){
        return this.running;
    }

    //setter for setting the running animation
    public void setRunning(boolean run){
        this.running = run;
    }


    //reset the acceleration to 0.
    public void resetAcceleration(){
        this.directionY = 0;
    }

    //reset the score to 0.
    public void resetScore(){
        this.score = 0;
    }

    public void draw(Canvas canvas)
    {
        canvas.drawBitmap(animation.getImage(),x,y,null);
    }


}
