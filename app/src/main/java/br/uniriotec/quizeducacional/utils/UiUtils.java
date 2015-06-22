package br.uniriotec.quizeducacional.utils;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.os.Build;
import android.support.v7.widget.Toolbar;
import com.readystatesoftware.systembartint.SystemBarTintManager;

/**
 * Created by Burner on 21/06/2015.
 */
public class UiUtils {
  private static int getStatusBarHeight(Activity activity) {
    int result = 0;
    int resourceId = activity.getResources().getIdentifier("status_bar_height", "dimen", "android");
    if (resourceId > 0) {
      result = activity.getResources().getDimensionPixelSize(resourceId);
    }
    return result;
  }

  public static void setToolbarPadding(Activity activity, Toolbar toolbar) {
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
      if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
        // create our manager instance after the content view is set
        SystemBarTintManager tintManager = new SystemBarTintManager(activity);
        // enable status bar tint
        tintManager.setStatusBarTintEnabled(true);
        // enable navigation bar tint
        tintManager.setNavigationBarTintEnabled(true);
        // set the transparent color of the status bar, 20% darker
        tintManager.setTintColor(Color.parseColor("#20000000"));
      }
      toolbar.setPadding(0, getStatusBarHeight(activity), 0, 0);
    }
  }
}
