package edu.temple.templebrowser;

import android.support.v4.app.FragmentStatePagerAdapter;
import java.util.ArrayList;
import java.util.List;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;


public class SectionsPagerAdapter extends FragmentStatePagerAdapter {

    public static int pos = 0;
    private List<Fragment> myFragments;
    private ArrayList<String> categories;
    private Context context;

    public SectionsPagerAdapter(Context c, FragmentManager fragmentManager, List<Fragment> myFrags, ArrayList<String> cats) {

        super(fragmentManager);
        myFragments = myFrags;
        this.categories = cats;
        this.context = c;
    }



    @Override
    public Fragment getItem(int position) {
        return myFragments.get(position);
    }



    @Override
    public int getCount() {
        return myFragments.size();
    }



    @Override

    public CharSequence getPageTitle(int position) {

        setPos(position);
        return categories.get(position);
    }



    public static int getPos() {
        return pos;
    }



    public void add(Class<Fragment> c, String title, Bundle b) {

        myFragments.add(Fragment.instantiate(context,c.getName(),b));
        categories.add(title);
    }

    //second implementation of add fragment
    public void addFrag(Fragment frag, String title, Bundle b){

        frag.setArguments(b);
        myFragments.add(frag);
        categories.add(title);

        this.notifyDataSetChanged();
    }



    public static void setPos(int pos) {
        SectionsPagerAdapter.pos = pos;
    }

}
