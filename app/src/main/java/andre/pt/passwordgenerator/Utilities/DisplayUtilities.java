package andre.pt.passwordgenerator.Utilities;

import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.view.Display;

public class DisplayUtilities {

    //Width -> 0
    //Height -> 1
    public static int[] getWindowsDimensions(AppCompatActivity activity){
        int[] ret = new int[2];

        Display display = activity.getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        ret[0] = size.x;
        ret[1] = size.y;

        return ret;
    }

}
