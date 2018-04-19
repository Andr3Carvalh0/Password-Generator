package andre.pt.passwordgenerator.Views;

import android.content.Context;
import android.graphics.Point;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Display;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import andre.pt.passwordgenerator.R;
import andre.pt.passwordgenerator.Views.Interfaces.IMainView;

public class MainActivity extends AppCompatActivity implements IMainView {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        openFloatingWindow();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    protected void onStart() {
        super.onStart();
        hideKeyboard();
    }

    private void hideKeyboard() {
        //InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        //imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void openFloatingWindow() {
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND, WindowManager.LayoutParams.FLAG_DIM_BEHIND);

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = 1.0f;
        params.gravity = Gravity.TOP;
        params.verticalMargin = 0.04f;
        params.dimAmount = 0.1f;
        getWindow().setAttributes(params);

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        int width = size.x;
        int height = size.y;

        if (height > width) {
            getWindow().setLayout((int) (width * .94), (int) (height * .5));
        } else {
            getWindow().setLayout((int) (width * .7), (int) (height * .8));
        }
    }

    @Override
    public void onBackPressed() {
        //super.onBackPressed();
    }
}
