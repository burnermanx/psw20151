package br.uniriotec.quizeducacional.aluno;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import br.uniriotec.quizeducacional.R;
import br.uniriotec.quizeducacional.constants.Keys;
import br.uniriotec.quizeducacional.persistance.PersistanceWrapper;
import br.uniriotec.quizeducacional.persistance.domain.Aluno;
import br.uniriotec.quizeducacional.persistance.domain.Turma;
import br.uniriotec.quizeducacional.persistance.domain.Usuario;
import butterknife.ButterKnife;

/**
 * A placeholder fragment containing a simple view.
 */
public class AlunoHomeFragment extends Fragment {
  private static AlunoHomeFragment instance;
  private Usuario usuario;
  private Aluno aluno;
  private Turma turma;
  private PersistanceWrapper perst;

  public AlunoHomeFragment() {
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_aluno_home, container, false);
    ButterKnife.inject(this, view);
    perst = PersistanceWrapper.getInstance();
    getAlunoData();
    return view;
  }

  public static AlunoHomeFragment getInstance(String username) {
    instance = new AlunoHomeFragment();
    Bundle args = new Bundle();
    args.putString(Keys.KEY_USERNAME, username);
    instance.setArguments(args);
    return instance;
  }

  private void getAlunoData() {
    Bundle args = getArguments();
    if (args.containsKey(Keys.KEY_USERNAME)) {
      String username = args.getString(Keys.KEY_USERNAME);
      if (username != null && username.length() > 0) {
        usuario = perst.getUsuario(username);
        aluno = perst.getAluno(usuario.getId());
        turma = aluno.turma;
      }
    }
  }

  private String getName() {
    return String.valueOf(usuario.nome) + " " + String.valueOf(usuario.sobrenome);
  }
}
