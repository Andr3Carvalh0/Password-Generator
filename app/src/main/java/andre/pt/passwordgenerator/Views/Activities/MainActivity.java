package andre.pt.passwordgenerator.Views.Activities;

import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;

import andre.pt.passwordgenerator.Adapters.ViewPageAdapter;
import andre.pt.passwordgenerator.Generator;
import andre.pt.passwordgenerator.R;
import andre.pt.passwordgenerator.Views.Activities.Interfaces.IGeneratorActivity;
import andre.pt.passwordgenerator.Views.Activities.Interfaces.ISettingsActivity;
import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements ISettingsActivity, IGeneratorActivity, TabLayout.OnTabSelectedListener{

    @BindView(R.id.pager)ViewPager viewPager;
    @BindView(R.id.tabLayout) TabLayout tabLayout;
    @BindView(R.id.toolbar) Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);
        ButterKnife.bind(this);

        initializeViewPager();
        initializeToolbar();
    }

    private void initializeToolbar() {
        toolbar.setTitle(getString(R.string.app_name));
        setSupportActionBar(toolbar);
    }

    private void initializeViewPager() {
        tabLayout.addTab(tabLayout.newTab().setIcon(getResources().getDrawable(R.drawable.ic_create_white_24dp, null)).setText(getString(R.string.generate)));
        tabLayout.addTab(tabLayout.newTab().setIcon(getResources().getDrawable(R.drawable.ic_settings_white_24dp, null)).setText(getString(R.string.settings)));
        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
        ViewPageAdapter adapter = new ViewPageAdapter(getSupportFragmentManager());
        viewPager.setAdapter(adapter);
        viewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(this);
    }


    @Override
    public Generator getGenerator() {
        return (Generator) getApplication();
    }

    @Override
    public void onTabSelected(TabLayout.Tab tab) {
        viewPager.setCurrentItem(tab.getPosition());
    }

    @Override
    public void onTabUnselected(TabLayout.Tab tab) {

    }

    @Override
    public void onTabReselected(TabLayout.Tab tab) {

    }
}
