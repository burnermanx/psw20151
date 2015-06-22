package br.uniriotec.quizeducacional.utils;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.test.ActivityUnitTestCase;
import br.uniriotec.quizeducacional.constants.Keys;

/**
 * Created by Burner on 21/06/2015.
 */
public class PreferencesWrapper {
  private static PreferencesWrapper instance;
  private SharedPreferences preferences;

  private PreferencesWrapper(Context context) {
    preferences = PreferenceManager.getDefaultSharedPreferences(context);
  }

  public static PreferencesWrapper getInstance(Context context) {
    if (instance == null) {
      instance = new PreferencesWrapper(context);
    }
    return instance;
  }

  public boolean isFirstTimeUse() {
    return preferences.getBoolean(Keys.KEY_PREF_FIRST_USE, true);
  }

  public void setFirstTimeUse(boolean firstTime) {
    preferences.edit().putBoolean(Keys.KEY_PREF_FIRST_USE, firstTime).apply();
  }
}
