package kamaljahah.rg014522.finalproject;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by Kamal on 28/03/2018.
 */


//Doesn't need to take in height because the bottom is off the screen.
public class BotBorder extends GameObject {

    private Bitmap image;

    public BotBorder(Bitmap resource, int x, int y){
        height = 190;
        width = 25;

        this.x = x;
        this.y = y;
        dx = GameView.moveSpeed; //Moves at the same speed as player

        image = Bitmap.createBitmap(resource,0,0,width,height);
    }

    public void update(){
        x+=dx;
    }

    public void draw(Canvas canvas){
        try {
            canvas.drawBitmap(image, x, y, null);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
