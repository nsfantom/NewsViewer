package tm.fantom.newsviewer.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.widget.Toolbar;

import com.trello.rxlifecycle2.components.support.RxAppCompatActivity;

import javax.inject.Inject;

import tm.fantom.newsviewer.AppController;
import tm.fantom.newsviewer.R;
import tm.fantom.newsviewer.deps.RestInterface;
import tm.fantom.newsviewer.fragment.ArticlesFragment;
import tm.fantom.newsviewer.fragment.FavouriteFragment;
import tm.fantom.newsviewer.model.ArticlesDao;

public class MainActivity extends RxAppCompatActivity {

    @Inject
    RestInterface api;

    @Inject
    ArticlesDao articlesDao;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AppController.getComponent().inject(this);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        SectionsPagerAdapter mSectionsPagerAdapter = new SectionsPagerAdapter(getSupportFragmentManager());

        // Set up the ViewPager with the sections adapter.
        ViewPager mViewPager = (ViewPager) findViewById(R.id.container);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setTabMode(TabLayout.MODE_SCROLLABLE);
        tabLayout.setupWithViewPager(mViewPager);
    }

    private class SectionsPagerAdapter extends FragmentPagerAdapter {

        private String[] tabTitles = getResources().getStringArray(R.array.articles);

        private ArticlesFragment cnbc = null;
        private ArticlesFragment cnn = null;
        private ArticlesFragment googleNews = null;
        private ArticlesFragment favourite = null;

        private Fragment[] fragments = {
                cnbc,
                cnn,
                googleNews,
                favourite
        };

        SectionsPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        private Fragment getFragment(int position) {
            if (fragments[position] == null) {
                if(position == 3) {
                    fragments[position] = new FavouriteFragment();
                }else{
                    Bundle bundle = new Bundle();
                    bundle.putInt(ArticlesFragment.POSITION, position);
                    fragments[position] = new ArticlesFragment();
                    fragments[position].setArguments(bundle);
                }
            }
            return fragments[position];
        }

        @Override
        public Fragment getItem(int position) {
            // getItem is called to instantiate the fragment for the given page.
            // Return a PlaceholderFragment (defined as a static inner class below).
            return getFragment(position);
        }

        @Override
        public int getCount() {
            return tabTitles.length;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return tabTitles[position];
        }

    }
}
