package com.example.luis.pruebasdrawer;

import android.graphics.Rect;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import com.pixelcan.inkpageindicator.InkPageIndicator;


public class MainActivity extends AppCompatActivity {
    DrawerLayout menu;

    private boolean isOutSideClicked;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.drawer_menu);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ViewPager vp_menu = (ViewPager) findViewById(R.id.vp_menu);
        PaginadorMenu paginadorMenu = new PaginadorMenu(getSupportFragmentManager());
        vp_menu.setAdapter(paginadorMenu);
        //1 es el último fragment. Con esto fuerzo que al salir, la paginación sea RTL (de derecha a izquierda)
        vp_menu.setCurrentItem(1);


        InkPageIndicator inkPageIndicator = (InkPageIndicator) findViewById(R.id.indicator);
        inkPageIndicator.setViewPager(vp_menu);





        menu = (DrawerLayout) findViewById(R.id.menu);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, menu, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        menu.addDrawerListener(toggle);
        //Adjunto oyente al drawer para que puede cambiarse de página en el ViewPager sin que se cierre el Drawer
        menu.addDrawerListener(new DrawerLayout.DrawerListener() {
            @Override
            public void onDrawerSlide(View drawerView, float slideOffset) {

            }

            @Override
            public void onDrawerOpened(View drawerView) {
                //Cuando se abra, quiero que se bloquee abierto hasta que se perciba que se quiere cerrar
                //Si no lo bloqueara, el que escucharía el deslizamiento no sería el Pager sino el Drawer, así
                // que no podría deslizarme entra págiunas
                menu.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN);
                Log.v("Drawer", "Abierto");
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                Log.v("Drawer", "Cerrado");
            }

            @Override
            public void onDrawerStateChanged(int newState) {

            }
        });


        toggle.syncState();

    }


    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (menu.isDrawerOpen(Gravity.START)) {

                View content = findViewById(R.id.vp_menu);
                int[] contentLocation = new int[2];
                content.getLocationOnScreen(contentLocation);
                Rect rect = new Rect(contentLocation[0],
                        contentLocation[1],
                        contentLocation[0] + content.getWidth(),
                        contentLocation[1] + content.getHeight());

                if (!(rect.contains((int) event.getX(), (int) event.getY()))) {
                    isOutSideClicked = true;
                } else {
                    isOutSideClicked = false;
                }

            } else {
                return super.dispatchTouchEvent(event);
            }
        } else if (event.getAction() == MotionEvent.ACTION_DOWN && isOutSideClicked) {
            isOutSideClicked = false;
            return super.dispatchTouchEvent(event);
        } else if (event.getAction() == MotionEvent.ACTION_MOVE && isOutSideClicked) {
            isOutSideClicked = false;
            return super.dispatchTouchEvent(event);
        }
        if (isOutSideClicked) {
            menu.closeDrawer(GravityCompat.START, true);
        }
        return super.dispatchTouchEvent(event);
    }


}
