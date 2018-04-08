package kamaljahah.rg014522.finalproject;

import android.graphics.Bitmap;
import android.graphics.Canvas;

/**
 * Created by Kamal on 28/03/2018.
 */

public class TopBorder extends GameObject {
    private Bitmap image;

    /**
     * Constructor for top border
     * @param resource
     * @param x
     * @param y
     * @param height
     */
    public TopBorder(Bitmap resource, int x, int y, int height) {
        this.height = height;
        width = 25;

        this.x = x;
        this.y = y;

        //Sets the border to the same gamespeed as the player is going so doesnt look out of place
        dx = GameView.moveSpeed;
        image = Bitmap.createBitmap(resource, 0, 0, width, height);
    }


    /**
     * Updates the border to the speed of the game running
     */
    public void update() {

        x += dx;
    }

    /**
     * Draw function for top border
     * @param canvas
     */
    public void draw(Canvas canvas) {
        try {
            canvas.drawBitmap(image, x, y, null);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
