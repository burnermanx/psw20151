package br.uniriotec.quizeducacional.professor;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import br.uniriotec.quizeducacional.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class ProfessorHomeFragment extends Fragment {

  public ProfessorHomeFragment() {
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    return inflater.inflate(R.layout.fragment_professor, container, false);
  }
}
