package andre.pt.passwordgenerator.Views.Activities;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;
import andre.pt.passwordgenerator.Generator;
import andre.pt.passwordgenerator.R;
import andre.pt.passwordgenerator.Utilities.DisplayUtilities;
import andre.pt.passwordgenerator.Views.Activities.Interfaces.IGeneratorActivity;
import andre.pt.passwordgenerator.Views.Fragments.GenerateFragment;
import butterknife.BindView;
import butterknife.OnClick;

public class GeneratorActivity extends AppCompatActivity implements IGeneratorActivity {

    @BindView(R.id.closeButton) ImageButton closeButton;

    private final static float WINDOW_ALPHA = 1f;
    private final static int WINDOW_GRAVITY = Gravity.TOP;
    private final static float WINDOW_VERTICAL_MARGIN = 0.05f;
    private final static float WINDOW_DIM = 0.30f;
    private final static float WINDOW_PORTRAIT_WIDTH_OFFSET = .94f;
    private final static float WINDOW_PORTRAIT_HEIGHT_OFFSET = .60f;
    private final static float WINDOW_LANDSCAPE_WIDTH_OFFSET = .70f;
    private final static float WINDOW_LANDSCAPE_HEIGHT_OFFSET = .80f;
    private final static String FRAGMENT_TAG = "generate_tag";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        configureFloatingWindow();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        showContent();
    }

    private void showContent(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        GenerateFragment fragment = fragmentManager.findFragmentByTag(FRAGMENT_TAG) != null
                    ? (GenerateFragment) fragmentManager.findFragmentByTag(FRAGMENT_TAG)
                    : (GenerateFragment) GenerateFragment.newInstance();

        fragmentTransaction.add(R.id.content, fragment, FRAGMENT_TAG);
        fragmentTransaction.commit();
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

        final int[] dimens = DisplayUtilities.getWindowsDimensions(this);

        if (dimens[1] > dimens[0]) {
            getWindow().setLayout((int) (dimens[0] * WINDOW_PORTRAIT_WIDTH_OFFSET), (int) (dimens[1] * WINDOW_PORTRAIT_HEIGHT_OFFSET));
        } else {
            getWindow().setLayout((int) (dimens[0] * WINDOW_LANDSCAPE_WIDTH_OFFSET), (int) (dimens[1] * WINDOW_LANDSCAPE_HEIGHT_OFFSET));
        }
    }

    @OnClick(R.id.closeButton)
    public void onClosedClicked(){
        super.onBackPressed();
    }

    @Override
    public Generator getGenerator() {
        return (Generator) getApplication();
    }
}
