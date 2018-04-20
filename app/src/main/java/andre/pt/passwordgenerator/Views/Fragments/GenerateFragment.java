package andre.pt.passwordgenerator.Views.Fragments;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CompoundButton;
import java.util.List;
import andre.pt.passwordgenerator.Adapters.OptionAdapter;
import andre.pt.passwordgenerator.Data.Option;
import andre.pt.passwordgenerator.Presentes.Interfaces.IGeneratorPresenter;
import andre.pt.passwordgenerator.R;
import andre.pt.passwordgenerator.Utilities.AnimationUtilities;
import andre.pt.passwordgenerator.Views.Activities.Interfaces.IGeneratorActivity;
import andre.pt.passwordgenerator.Views.Fragments.Interfaces.IGenerateView;
import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;

public class GenerateFragment extends Fragment implements IGenerateView, CompoundButton.OnCheckedChangeListener {

    private IGeneratorPresenter presenter;

    public static IGenerateView newInstance() {
        return new GenerateFragment();
    }

    @BindView(R.id.recyclerView) RecyclerView recyclerView;
    @BindView(R.id.generateButton) FloatingActionButton generateButton;
    @BindView(R.id.alert) View alert;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_generate, container, false);
        ButterKnife.bind(this, view);
        presenter.onCreate(this);
        return view;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if(context instanceof IGeneratorActivity){
            presenter = ((IGeneratorActivity)context).getGenerator().getMainPresenter();
        }
    }

    public void prepareRecyclerView(List<Option> options) {
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(new OptionAdapter(options, getContext(), this));
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
        ClipboardManager clipboard = (ClipboardManager) getContext().getSystemService(Context.CLIPBOARD_SERVICE);
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

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        final String id = (String) buttonView.getTag();
        presenter.handleOnClick(id, isChecked);
    }


    @OnClick(R.id.generateButton)
    public void onClick() {
        presenter.generatePassword();
    }


}
