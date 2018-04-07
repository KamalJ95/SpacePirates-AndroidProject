package kamaljahah.rg014522.finalproject;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by Kamal on 26/03/2018.
 */

public class Background {

    private Bitmap image;
    private int x, y, dx;

    public Background(Bitmap res) {

        image = res;
        dx = GameView.moveSpeed;
    }

    public void update(){
        x+=dx;
        if(x<-GameView.width){
            x=0;
        }
    }

    public void draw(Canvas canvas){
        canvas.drawBitmap(image,x,y,null);
        if (x<=0){
           canvas.drawBitmap(image,x+ GameView.width,y,null);
        }
    }

}
