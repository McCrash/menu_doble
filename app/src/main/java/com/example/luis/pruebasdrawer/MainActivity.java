package com.example.luis.pruebasdrawer;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

public class MainActivity extends AppCompatActivity
        implements ViewPagerPersonalizado.OyenteFueraLimites {
    DrawerLayout drawer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //Atención: En el xml este elemento no es un View Pager sino un ViewPagerPersonalizado
        ViewPagerPersonalizado vp=(ViewPagerPersonalizado)findViewById(R.id.v_pager);
        vp.setActividad(this);//Le paso este Activity para que luego pueda referencias a los métodos que están aquí implementados
        Paginador p=new Paginador(getSupportFragmentManager());
        vp.setAdapter(p);
        //1 es el último fragment. Con esto fuerzo que al salir, la paginación sea RTL (de derecha a izquierda)
        vp.setCurrentItem(1);
        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
       drawer.addDrawerListener(toggle);
       //Adjunto oyente al drawer para que puede cam biarse de página en el ViewPager sin que se cierre el Drawer
       drawer.addDrawerListener(new DrawerLayout.DrawerListener() {
           @Override
           public void onDrawerSlide(View drawerView, float slideOffset) {

           }

           @Override
           public void onDrawerOpened(View drawerView) {
               //Cuando se abra, quiero que se bloquee abierto hasta que se perciba que se quiere cerrar
               //Si no lo bloqueara, el que escucharía el deslizamiento no sería el Pager sino el Drawer, así
               // que no podría deslizarme entra págiunas
               drawer.setDrawerLockMode(DrawerLayout.LOCK_MODE_LOCKED_OPEN);
               Log.v("Drawer", "Drawer abierto");
           }

           @Override
           public void onDrawerClosed(View drawerView) {
               Log.v("Drawer", "Drawer cerrado");
           }

           @Override
           public void onDrawerStateChanged(int newState) {

           }
       });


        toggle.syncState();

    }





    @Override
    public void fueraLimitesInicio() {
        Log.v("Frontera", "intentas salirte ini");
    }

    @Override
    public void fueraLimitesFin() {
        //Está viendo el último Fragment e intenta ir más allá, se cierra drawer
               Log.v("Frontera", "intentas salirte fin");
        //cERRAR DRAWER

        drawer.closeDrawer(GravityCompat.START, true);
    }
}
