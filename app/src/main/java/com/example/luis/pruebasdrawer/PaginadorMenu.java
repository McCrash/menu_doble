package com.example.luis.pruebasdrawer;


import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;

/**
 * Created by luis on 30/01/2018.
 */

public class PaginadorMenu extends FragmentPagerAdapter {
    Fragment[] fragmentos=new Fragment[2];

    public PaginadorMenu(FragmentManager fm) {

        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        Log.v("posicion", "posicion:"+position);
        if (position==0)
        {
            return new FragmentMenuComunidades();
        }
        else
        {
            return new FragmentMenuPrincipal();
        }
    }

    @Override
    public int getCount() {
        return 2;
    }
}
