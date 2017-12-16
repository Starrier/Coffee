package org.starrier.coffee;

import android.content.Context;
import android.graphics.Typeface;

/**
 * Created by Starrier on 2017/12/16.
 */
/* Android FontAwesome Helper Class */
public class FontManager {
    public static final String ROOT = "fonts/", FONTAWESOME =ROOT+"fontawesome-webfont.ttf";

    public static Typeface getTypeface(Context context, String font) {
        return Typeface.createFromAsset(context.getAssets(), font);
    }
}

