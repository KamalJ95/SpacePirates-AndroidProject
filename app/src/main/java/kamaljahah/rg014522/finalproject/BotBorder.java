package kamaljahah.rg014522.finalproject;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by Kamal on 28/03/2018.
 */


/**
 * Class for bottom border, doesn't need to take in height in constructor because bottom is off the screen.
  */
public class BotBorder extends GameObject {

    private Bitmap image;

    /**
     * Bottom border constructor with arguemnts of x,y and bitmap
     * @param resource
     * @param x
     * @param y
     */
    public BotBorder(Bitmap resource, int x, int y){
        height = 190;
        width = 25;

        this.x = x;
        this.y = y;
        dx = GameView.moveSpeed; //Moves at the same speed as player

        image = Bitmap.createBitmap(resource,0,0,width,height);
    }

    /**
     * X is added to the direction of x so follows player
     */
    public void update(){

        x+=dx;
    }


    /**
     * Draw function, try to draw bitmap and if cant catch error and printstacktrace.
     * @param canvas
     */
    public void draw(Canvas canvas){
        try {
            canvas.drawBitmap(image, x, y, null);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
