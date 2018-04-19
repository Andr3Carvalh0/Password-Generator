package andre.pt.passwordgenerator.Views;

import android.graphics.Point;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Display;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.widget.CompoundButton;
import android.widget.Toast;

import java.util.List;
import andre.pt.passwordgenerator.Adapters.OptionAdapter;
import andre.pt.passwordgenerator.Data.Option;
import andre.pt.passwordgenerator.Generator;
import andre.pt.passwordgenerator.Presentes.Interfaces.IMainPresenter;
import andre.pt.passwordgenerator.R;
import andre.pt.passwordgenerator.Views.Interfaces.IMainView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements IMainView, CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    @BindView(R.id.generateButton) FloatingActionButton generateButton;

    private final static float WINDOW_ALPHA = 0.95f;
    private final static int WINDOW_GRAVITY = Gravity.TOP;
    private final static float WINDOW_VERTICAL_MARGIN = 0.05f;
    private final static float WINDOW_DIM = 0.30f;
    private final static float WINDOW_PORTRAIT_WIDTH_OFFSET = .94f;
    private final static float WINDOW_PORTRAIT_HEIGHT_OFFSET = .70f;
    private final static float WINDOW_LANDSCAPE_WIDTH_OFFSET = .70f;
    private final static float WINDOW_LANDSCAPE_HEIGHT_OFFSET = .80f;

    private IMainPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        configureFloatingWindow();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        presenter = ((Generator)getApplication()).getMainPresenter();
        presenter.onCreate(this);
    }

    @Override
    public void prepareRecyclerView(List<Option> options){
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(new OptionAdapter(options, this, this));
    }

    @Override
    public void changeGenerateButtonState(boolean visibility) {
        int currentVisibility = generateButton.getVisibility();
        int calculatedVisibility = visibility ? View.VISIBLE : View.GONE;

        if(currentVisibility == calculatedVisibility)
            return;

        Animation animation;

        if(calculatedVisibility == View.VISIBLE){
            animation = new AlphaAnimation(0, 1);
            animation.setInterpolator(new DecelerateInterpolator());
        }else{
            animation = new AlphaAnimation(1, 0);
            animation.setInterpolator(new AccelerateInterpolator());
        }
        animation.setDuration(1000);
        animation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                generateButton.setVisibility(calculatedVisibility);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });

        generateButton.startAnimation(animation);
    }

    @Override
    public void acceptPassword(String password) {
        Toast.makeText(this, password, Toast.LENGTH_SHORT).show();
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

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        final String id = (String) buttonView.getTag();
        presenter.handleOnClick(id, isChecked);
    }

    @Override
    @OnClick(R.id.generateButton)
    public void onClick(View v) {
        presenter.generatePassword();
    }
}
