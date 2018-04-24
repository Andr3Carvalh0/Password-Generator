package andre.pt.passwordgenerator.Adapters;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import andre.pt.passwordgenerator.Views.Fragments.GenerateFragment;
import andre.pt.passwordgenerator.Views.Fragments.SettingsFragment;

public class ViewPageAdapter extends FragmentStatePagerAdapter{
    private int count;
    private static final Fragment fragments[] = {GenerateFragment.newInstance(), SettingsFragment.newInstance()};

    public ViewPageAdapter(FragmentManager fm) {
        super(fm);
        count = fragments.length;
    }

    @Override
    public Fragment getItem(int position) {
        return fragments[position];
    }

    @Override
    public int getCount() {
        return count;
    }
}