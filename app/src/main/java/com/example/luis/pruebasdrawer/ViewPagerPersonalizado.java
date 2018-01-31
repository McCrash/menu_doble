package com.example.luis.pruebasdrawer;

import android.app.Activity;
import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;

/**
 * Created by luis on 31/01/2018.
 */

public class ViewPagerPersonalizado extends ViewPager{
    Activity a;
    float inicioX;
    OyenteFueraLimites oyenteSwipe;//De


    public void setActividad(Activity a) {
        this.a = a;
    }

    public ViewPagerPersonalizado(Context context, AttributeSet attrs) {
        super(context, attrs);

    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        float x = ev.getX();
        //Referencia al activity, donde están implementados dos métodos:
        oyenteSwipe=(OyenteFueraLimites)a;

        //Aquí se mira dónde estamos y hacia dónde tira el usuario.
        switch (ev.getAction()) {
            case MotionEvent.ACTION_DOWN:
                inicioX = x;
                break;
            case MotionEvent.ACTION_MOVE:
                if (inicioX < x && getCurrentItem() == 0) {
                    oyenteSwipe.fueraLimitesInicio();
                } else if (inicioX > x && getCurrentItem() == getAdapter().getCount() - 1) {
                    oyenteSwipe.fueraLimitesFin();
                }
                break;
        }
        return super.onTouchEvent(ev);

    }

    public interface OyenteFueraLimites {
        public void fueraLimitesInicio();
        public void fueraLimitesFin();
    }
}
