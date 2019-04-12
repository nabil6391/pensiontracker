package com.rootsoftit.pensiontracker.utils;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.PorterDuff;
import android.graphics.drawable.Drawable;
import android.util.TypedValue;

import androidx.annotation.AttrRes;
import androidx.core.content.ContextCompat;

import com.rootsoftit.pensiontracker.R;


public class ThemeUtils {

    private static int colorPrimary = 0;
    private static int colorAccent = 0;

    public static void ChangeEdgeEffect(Context cxt) {
        //glow
        int glowDrawableId = cxt.getResources().getIdentifier("overscroll_glow", "drawable", "android");
        Drawable androidGlow = ContextCompat.getDrawable(cxt, glowDrawableId);
        assert androidGlow != null;
        androidGlow.setColorFilter(fetchPrimaryColor(cxt), PorterDuff.Mode.SRC_IN);

        //edge
        int edgeDrawableId = cxt.getResources().getIdentifier("overscroll_edge", "drawable", "android");
        Drawable androidEdge = ContextCompat.getDrawable(cxt, edgeDrawableId);
        assert androidEdge != null;
        androidEdge.setColorFilter(fetchPrimaryColor(cxt), PorterDuff.Mode.SRC_IN);
    }

    public static int fetchColor(Context context, int attribute) {
        TypedArray a = context.obtainStyledAttributes(getThemeID(), new int[]{attribute});
        int color = a.getColor(0, 0);
        a.recycle();
        return color;
    }

    public static int fetchBackgroundColor(Context context) {
        TypedArray a = context.obtainStyledAttributes(getThemeID(), new int[]{android.R.attr.windowBackground});
        int color = a.getColor(0, 0);
        a.recycle();
        return color;
    }

    public static int fetchColorAccent(Context context) {
        if (colorAccent == 0) {
            TypedArray a = context.obtainStyledAttributes(getThemeID(), new int[]{R.attr.colorAccent});
            colorAccent = a.getColor(0, 0);
            a.recycle();
        }
        return colorAccent;
    }

    public static void updateColors(Context context) {
        TypedArray a = context.obtainStyledAttributes(getThemeID(), new int[]{R.attr.colorAccent});
        colorAccent = a.getColor(0, 0);
        a = context.obtainStyledAttributes(getThemeID(), new int[]{R.attr.colorPrimary});
        colorPrimary = a.getColor(0, 0);
        a.recycle();
    }

    public static int fetchPrimaryColor(Context context) {
        if (colorPrimary == 0) {
            TypedArray a = context.obtainStyledAttributes(getThemeID(), new int[]{R.attr.colorPrimary});
            colorPrimary = a.getColor(0, 0);
            a.recycle();
        }
        return colorPrimary;
    }

    public static int fetchColorText(Context context) {
        TypedArray a = context.obtainStyledAttributes(getThemeID(), new int[]{android.R.attr.textColor});
        int color = a.getColor(0, 0);
        a.recycle();
        return color;
    }


    // use ThemeUtils#fetchSelectableItemBackgroundResource(context)
    public static Drawable fetchSelectableItemBackground(Context context) {
        TypedArray ta = context.obtainStyledAttributes(getThemeID(), new int[]{android.R.attr.selectableItemBackground});
        Drawable drawableFromTheme = ta.getDrawable(0);
        ta.recycle();
        return drawableFromTheme;
    }

    public static int fetchDialogBackGroundColor(Context context) {
        TypedArray ta = context.obtainStyledAttributes(getThemeID(), new int[]{android.R.attr.colorBackground});
        int color = ta.getColor(0, 0);
        ta.recycle();
        return color;
    }


    public static int fetchSelectableItemBackgroundResource(Context context) {
//        TypedArray ta = context.obtainStyledAttributes(getThemeID(), new int[]{android.R.attr.selectableItemBackground});
//        int backgroundResource = ta.getResourceId(0, 0);
//        ta.recycle();
//        return backgroundResource;

        TypedValue outValue = new TypedValue();
        context.getTheme().resolveAttribute(android.R.attr.selectableItemBackground, outValue, true);
        return outValue.resourceId;
    }

    public static int getThemeID() {
        return R.style.AppTheme;
    }

    public static Drawable resolveDrawable(Context context, @AttrRes int attr) {
        TypedArray ta = context.obtainStyledAttributes(new int[]{attr});
        Drawable d = ta.getDrawable(0);
        ta.recycle();
        return d;
    }

    private static int getThemeAccentColor1(final Context context) {
        TypedValue typedValue = new TypedValue();

        TypedArray a = context.obtainStyledAttributes(typedValue.data, new int[]{R.attr.colorAccent});
        int color = a.getColor(0, 0);
        a.recycle();

        return color;
    }

    public static int getThemeAccentColor2(final Context context) {
        final TypedValue value = new TypedValue();
        context.getTheme().resolveAttribute(R.attr.colorAccent, value, true);
        return value.data;
    }


}
