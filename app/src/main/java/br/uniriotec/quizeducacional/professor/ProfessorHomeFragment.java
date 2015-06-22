package br.uniriotec.quizeducacional.professor;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import br.uniriotec.quizeducacional.R;
import br.uniriotec.quizeducacional.constants.Keys;
import br.uniriotec.quizeducacional.persistance.PersistanceWrapper;
import br.uniriotec.quizeducacional.persistance.domain.Professor;
import br.uniriotec.quizeducacional.persistance.domain.Usuario;
import butterknife.ButterKnife;

/**
 * A placeholder fragment containing a simple view.
 */
public class ProfessorHomeFragment extends Fragment {
  private static ProfessorHomeFragment instance;
  private Professor professor;
  private Usuario usuario;

  public ProfessorHomeFragment() {
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_professor_home, container, false);
    ButterKnife.inject(this, view);
    getProfessorData();
    return view;
  }

  public ProfessorHomeFragment getInstance(String username) {
    Bundle args = new Bundle();
    args.putString(Keys.KEY_USERNAME, username);
    instance.setArguments(args);
    return instance;
  }

  private void getProfessorData() {
    Bundle args = getArguments();
    if (args.containsKey(Keys.KEY_USERNAME)) {
      String username = args.getString(Keys.KEY_USERNAME);
      if (username != null && username.length() > 0) {
        usuario = PersistanceWrapper.getInstance().getUsuario(username);
        professor = PersistanceWrapper.getInstance().getProfessor(usuario.getId());
      }
    }
  }
}