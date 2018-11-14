package edu.temple.templebrowser;

import android.support.design.widget.TabLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity implements ActivityCommunicator {

    /**
     * The {@link android.support.v4.view.PagerAdapter} that will provide
     * fragments for each of the sections. We use a
     * {@link FragmentPagerAdapter} derivative, which will keep every
     * loaded fragment in memory. If this becomes too memory intensive, it
     * may be best to switch to a
     * {@link android.support.v4.app.FragmentStatePagerAdapter}.
     */
    private SectionsPagerAdapter mSectionsPagerAdapter;

    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    ArrayList<String> categories;
    //create button for add tab
    ImageButton addTabButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        categories = new ArrayList<>(Arrays.asList("1"));
        List<Fragment> fragments = buildFragments();

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        // Create the adapter that will return a fragment for each of the three
        // primary sections of the activity.
        mSectionsPagerAdapter = new SectionsPagerAdapter(this,getSupportFragmentManager(), fragments, categories);

        // CREATE HANDLE TO XML VIEW PAGE ADAPTER AND ASSIGN IT A PAGER ADAPTER
        mViewPager = (ViewPager) findViewById(R.id.fragmentContainer);
        mViewPager.setAdapter(mSectionsPagerAdapter);

        //CREATE HANDLE TO TAB  LAYOUT
        final TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);

        //ASSIGN TAB LAYOUT ON PAGE CHANGE LISTENER TO VIEW PAGE
        //this will tell pager when the tab has new tab selected or switched to
        mViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.ViewPagerOnTabSelectedListener(mViewPager));
        tabLayout.setupWithViewPager(mViewPager);
        //define tabListeners



        //CREATE HANDLE TO TAB BUTTON FOR ADDING NEW TAB/FRAGMENT
        addTabButton = (ImageButton) findViewById(R.id.addTabButton);
        //CREATE LISTENER FOR THE TAB BUTTON
        addTabButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //generate bundle for new fragment and add to adapter
                Bundle b = new Bundle();
                String title = Integer.toString( mSectionsPagerAdapter.getCount() + 1 );
                //add fragment / page
                PlaceholderFragment frag = PlaceholderFragment.newInstance(mSectionsPagerAdapter.getCount());
                mSectionsPagerAdapter.addFrag( frag, title, b);

            }
        });

        Button goButton = (Button)findViewById(R.id.urlGoButton);
        goButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //get text from url text box
                EditText urlTextBox = findViewById(R.id.urlEditTextBox);
                String url = urlTextBox.getText().toString();
                if(url.isEmpty()) {
                    //stop, do not finish sending to browser
                    return;
                }
                //bundle text
                Bundle b = new Bundle();
                b.putString("url", url);
                //send text to web browser
                PlaceholderFragment frag = (PlaceholderFragment) mSectionsPagerAdapter.getItem(  mViewPager.getCurrentItem()  );
                frag.setArguments(b);
                //call the fragment update method
                frag.passDataToFragment();
            }
        });

        //link forward and backward buttons
        Button backwardButton = (Button) findViewById(R.id.backButton);
        Button forwardButton = (Button) findViewById(R.id.forwardButton);
        backwardButton.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                int tab = mViewPager.getCurrentItem();
                if (tab > 0) {
                    tab--;
                    mViewPager.setCurrentItem(tab);
                } else if (tab == 0) {
                    mViewPager.setCurrentItem(tab);
                }
            }
        });

        //set onclick forward button
        forwardButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int tab = mViewPager.getCurrentItem();
                tab++;
                mViewPager.setCurrentItem(tab);
            }
        });

        mViewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int i, float v, int i1) {

            }

            @Override
            public void onPageSelected(int i) {
                //get Fragment url
                PlaceholderFragment frag = (PlaceholderFragment) mSectionsPagerAdapter.getItem(  mViewPager.getCurrentItem()  );
                String url = frag.getUrl();
                //find the text view
                TextView text = findViewById(R.id.urlEditTextBox);
                text.setText(url);

            }

            @Override
            public void onPageScrollStateChanged(int i) {

            }
        });

        //attempt intent
        try {
            String url = getIntent().getData().toString();
            if(  !(url.isEmpty())  ){
                //create new tab and load the webpage
                //CREATE NEW TAB
                //generate bundle for new fragment and add to adapter
                Bundle b = new Bundle();
                b.putString("url", url);
                String title = Integer.toString( mSectionsPagerAdapter.getCount() + 1 );
                int tabIndex = mSectionsPagerAdapter.getCount();
                //add fragment / page
                PlaceholderFragment frag = PlaceholderFragment.newInstance(mSectionsPagerAdapter.getCount());
                mSectionsPagerAdapter.addFrag( frag, title, b);

                //MOVE TO THE NEW TAB
                mViewPager.setCurrentItem(tabIndex);

                //SET URL IN TEXTBOX
                EditText urlText = (EditText)findViewById(R.id.urlEditTextBox);
                urlText.setText(url);

            }

        } catch (Exception e) {
            e.printStackTrace();
        }

    }//end on create


    /**
     * \METHOD INFLATES THE MENU WHEN THE USER SELECTS THE MENU
     * @param menu
     * @return
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * FRAGMENT
     * GENERATES THE FRAGMENT
     * A placeholder fragment containing a simple view.
     */



    private List<Fragment> buildFragments() {

        List<android.support.v4.app.Fragment> fragments = new ArrayList<Fragment>();

        for(int i = 0; i<categories.size(); i++) {

            Bundle b = new Bundle();

            b.putInt("position", i);

            fragments.add(Fragment.instantiate(this, PlaceholderFragment.class.getName(), b));

        }



        return fragments;

    }


    @Override
    public void getDataFromFrag(String someValue) {
        //find the text view
        TextView text = findViewById(R.id.urlEditTextBox);
        //assign the url to the text view
        text.setText(someValue);
    }
}
