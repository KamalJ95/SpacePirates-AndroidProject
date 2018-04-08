package kamaljahah.rg014522.finalproject;

import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;

/**
 * Created by Kamal on 27/03/2018.
 */

public class Effects extends GameObject {

    public int radius;

    /**
     * Constructor for effects that will come off of the back of the ship
     * @param x
     * @param y
     */
    public Effects(int x, int y)
    {
        radius = 7;
        super.x = x;
        super.y = y;
    }

    /**
     * Will draw the cirlces at a position of x - 10 to the player.
     */
    public void update()
    {
        x-=10;
    }


    /**
     * Will draw white cicles coming out behind the back of the pirate ship
     * @param canvas
     */
    public void draw(Canvas canvas){
        Paint paint = new Paint();
        Paint paint2 = new Paint();
        paint.setColor(Color.BLUE);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.WHITE);
        paint.setStyle(Paint.Style.FILL);

        //The circles to draw that will be cycles through

        canvas.drawCircle(x-radius,y-radius,radius,paint);
        canvas.drawCircle(x-radius+2,y-radius-2,radius,paint);
        canvas.drawCircle(x-radius+4,y-radius+1,radius,paint);
    }

}
