package andre.pt.passwordgenerator.Views;

import android.content.Context;
import android.graphics.Point;
import android.support.constraint.ConstraintLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import andre.pt.passwordgenerator.R;
import andre.pt.passwordgenerator.Views.Interfaces.IMainView;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements IMainView {

    @BindView(R.id.root) ConstraintLayout view;
    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    @BindView(R.id.generateButton) FloatingActionButton generateButton;

    private final static float WINDOW_ALPHA = 0.95f;
    private final static int WINDOW_GRAVITY = Gravity.TOP;
    private final static float WINDOW_VERTICAL_MARGIN = 0.04f;
    private final static float WINDOW_DIM = 0.10f;
    private final static float WINDOW_PORTRAIT_WIDTH_OFFSET = .94f;
    private final static float WINDOW_PORTRAIT_HEIGHT_OFFSET = .50f;
    private final static float WINDOW_LANDSCAPE_WIDTH_OFFSET = .70f;
    private final static float WINDOW_LANDSCAPE_HEIGHT_OFFSET = .80f;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        configureFloatingWindow();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    private void hideKeyboard() {
        InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    private void configureFloatingWindow() {
        requestWindowFeature(Window.FEATURE_ACTION_BAR);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND, WindowManager.LayoutParams.FLAG_DIM_BEHIND);

        WindowManager.LayoutParams params = getWindow().getAttributes();
        params.alpha = WINDOW_ALPHA;
        params.gravity = WINDOW_GRAVITY;
        params.verticalMargin = WINDOW_VERTICAL_MARGIN;
        params.dimAmount = WINDOW_DIM;
        getWindow().setAttributes(params);

        final int[] dimens = getWindowsDimensions();

        if (dimens[1] > dimens[0]) {
            getWindow().setLayout((int) (dimens[0] * WINDOW_PORTRAIT_WIDTH_OFFSET), (int) (dimens[1] * WINDOW_PORTRAIT_HEIGHT_OFFSET));
        } else {
            getWindow().setLayout((int) (dimens[0] * WINDOW_LANDSCAPE_WIDTH_OFFSET), (int) (dimens[1] * WINDOW_LANDSCAPE_HEIGHT_OFFSET));
        }
    }
    private int[] getWindowsDimensions(){
        int[] ret = new int[2];

        Display display = getWindowManager().getDefaultDisplay();
        Point size = new Point();
        display.getSize(size);
        ret[0] = size.x;
        ret[1] = size.y;

        return ret;
    }
}
