package br.uniriotec.quizeducacional.professor;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import br.uniriotec.quizeducacional.base.BaseActivity;
import br.uniriotec.quizeducacional.constants.Keys;

public class ProfessorActivity extends BaseActivity {
  String username;

  @Override protected void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    setTitle("Professor");
    Bundle extras = getIntent().getExtras();
    if (extras.containsKey(Keys.KEY_USERNAME)) {
      username = extras.getString(Keys.KEY_USERNAME);
      ProfessorHomeFragment fragment = ProfessorHomeFragment.getInstance(username);
      setContent(fragment);
    }
  }

  public void setProfessorDisciplinaFragment(long professorId, long turmaId) {
    Fragment fragment = ProfessorDisciplinaFragment.getInstance(professorId, turmaId);
    setContent(fragment);
  }

  public void setProfessorModuloFragment(long professorId, long turmaId, long disicplinaId) {
    Fragment fragment = ProfessorModuloFragment.getInstance(professorId, turmaId, disicplinaId);
    setContent(fragment);
  }

  public void setQuestaoFragment(long professorId, long turmaId, long disicplinaId, long moduloId) {
    Fragment fragment = ProfessorQuestaoFragment.getInstance(professorId, turmaId, disicplinaId, moduloId);
    setContent(fragment);
  }



}
