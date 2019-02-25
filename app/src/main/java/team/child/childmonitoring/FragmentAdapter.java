package team.child.childmonitoring;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by alisa on 2/25/2019.
 */

public class FragmentAdapter extends FragmentPagerAdapter {
    public FragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        switch (position)
        {
            case 0:
                CallLogsFragment callLogs=new CallLogsFragment();
                return callLogs;
            case 1:
                MessagesFragment messagesFragment= new MessagesFragment();
                return messagesFragment;
            case 2:
                LoctionFragment loctionFragment=new LoctionFragment();
                return loctionFragment;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return 3;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        switch (position)
        {
            case 0:
                return "CallLogs";
            case 1:
                return "Messages";
            case 2:
                return "Location";
            default:
                return null;
        }
    }
}
