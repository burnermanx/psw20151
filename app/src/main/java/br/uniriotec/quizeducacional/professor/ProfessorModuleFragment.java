package br.uniriotec.quizeducacional.professor;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import br.uniriotec.quizeducacional.constants.Keys;

/**
 * Created by Burner on 21/06/2015.
 */
public class ProfessorModuleFragment extends Fragment {
  private static ProfessorModuleFragment instance;
  private int mModuleId;

  public static ProfessorModuleFragment getInstance(int idModulo) {
    Bundle args = new Bundle();
    args.putInt(Keys.KEY_MODULE_ID, idModulo);
    instance.setArguments(args);
    return instance;
  }

  @Nullable @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    Bundle args = this.getArguments();
    if (args.containsKey(Keys.KEY_MODULE_ID)) {
      mModuleId = args.getInt(Keys.KEY_MODULE_ID);
    }
    return super.onCreateView(inflater, container, savedInstanceState);
  }
}
