package com.toddindustries.makeitrain;

import com.toddindustries.makeitrain.Overview;
import com.toddindustries.makeitrain.Forecast;
import com.toddindustries.makeitrain.Details;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by Todd on 2014-11-24.
 */
public class TabsPagerAdapter extends FragmentPagerAdapter {
	public TabsPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int index) {

		switch (index) {
			case 0:
				// Overview fragment activity
				return new Overview();
			case 1:
				// Forecast fragment activity
				return new Forecast();
			case 2:
				// Details fragment activity
				return new Details();
		}

		return null;
	}

	@Override
	public int getCount() {
		// get item count - equal to number of tabs
		return 3;
	}
}
