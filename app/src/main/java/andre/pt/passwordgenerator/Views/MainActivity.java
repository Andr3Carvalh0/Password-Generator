package andre.pt.passwordgenerator.Views;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.CompoundButton;
import android.widget.ImageButton;
import java.util.List;
import andre.pt.passwordgenerator.Adapters.OptionAdapter;
import andre.pt.passwordgenerator.Data.Option;
import andre.pt.passwordgenerator.Generator;
import andre.pt.passwordgenerator.Presentes.Interfaces.IMainPresenter;
import andre.pt.passwordgenerator.R;
import andre.pt.passwordgenerator.Utilities.AnimationUtilities;
import andre.pt.passwordgenerator.Utilities.DisplayUtilities;
import andre.pt.passwordgenerator.Views.Interfaces.IMainView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class MainActivity extends AppCompatActivity implements IMainView, CompoundButton.OnCheckedChangeListener {

    @BindView(R.id.closeButton) ImageButton closeButton;
    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    @BindView(R.id.generateButton) FloatingActionButton generateButton;
    @BindView(R.id.alert) View alert;

    private final static float WINDOW_ALPHA = 1f;
    private final static int WINDOW_GRAVITY = Gravity.TOP;
    private final static float WINDOW_VERTICAL_MARGIN = 0.05f;
    private final static float WINDOW_DIM = 0.30f;
    private final static float WINDOW_PORTRAIT_WIDTH_OFFSET = .94f;
    private final static float WINDOW_PORTRAIT_HEIGHT_OFFSET = .60f;
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

        final AnimationUtilities.Builder builder = AnimationUtilities.create(generateButton)
                    .setAnimationDuration(1000)
                    .addOnAnimationEndEvent((anim) -> generateButton.setVisibility(calculatedVisibility));

        if(calculatedVisibility == View.VISIBLE)
            builder.setFadeInAnimation();
        else
            builder.setFadeOutAnimation();

        builder.buildAnimation().start();
    }

    @Override
    public void acceptPassword(String password) {
        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        ClipData clip = ClipData.newPlainText(getString(R.string.app_name), password);

        if(clipboard != null) {
            clipboard.setPrimaryClip(clip);

            AnimationUtilities.create(alert)
                    .setAnimationDuration(1000)
                    .setFadeInAnimation()
                    .addOnAnimationStartEvent((anim) -> alert.setVisibility(View.VISIBLE))
                    .setIntervalBetweenAnimations(500)
                    .followedByAnimation()
                    .setAnimationDuration(1000)
                    .addOnAnimationEndEvent((anim) -> alert.setVisibility(View.GONE))
                    .setFadeOutAnimation()
                    .buildAnimation()
                    .start();
        }
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

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        final String id = (String) buttonView.getTag();
        presenter.handleOnClick(id, isChecked);
    }

    @OnClick(R.id.generateButton)
    public void onClick() {
        presenter.generatePassword();
    }

    @OnClick(R.id.closeButton)
    public void onClosedClicked(){
        super.onBackPressed();
    }
}
