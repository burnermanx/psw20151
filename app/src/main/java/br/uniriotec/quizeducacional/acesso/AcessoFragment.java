package br.uniriotec.quizeducacional.acesso;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import br.uniriotec.quizeducacional.R;
import br.uniriotec.quizeducacional.aluno.AlunoActivity;
import br.uniriotec.quizeducacional.constants.Keys;
import br.uniriotec.quizeducacional.persistance.PersistanceWrapper;
import br.uniriotec.quizeducacional.persistance.domain.Usuario;
import br.uniriotec.quizeducacional.professor.ProfessorActivity;
import br.uniriotec.quizeducacional.test.UserInitialData;
import br.uniriotec.quizeducacional.utils.CryptUtils;
import br.uniriotec.quizeducacional.utils.PreferencesWrapper;
import br.uniriotec.quizeducacional.utils.UiUtils;
import butterknife.ButterKnife;
import butterknife.InjectView;
import butterknife.OnClick;
import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;

/**
 * A placeholder fragment containing a simple view.
 */
public class AcessoFragment extends Fragment {
  @InjectView(R.id.access_ed_login) EditText mLogin;
  @InjectView(R.id.access_ed_password) EditText mPassword;

  public AcessoFragment() {
  }

  public static AcessoFragment getInstance() {
    return new AcessoFragment();
  }

  @Override public View onCreateView(LayoutInflater inflater, ViewGroup container,
      Bundle savedInstanceState) {
    View view = inflater.inflate(R.layout.fragment_acesso, container, false);
    ButterKnife.inject(this, view);
    if (PreferencesWrapper.getInstance(getActivity()).isFirstTimeUse()) {
      PreferencesWrapper.getInstance(getActivity()).setFirstTimeUse(false);
      UserInitialData.generateInitialData();
    }
    return view;
  }

  @OnClick(R.id.access_btn_action)
  public void onClick() {
    String login = getText(mLogin);
    String password = getText(mPassword);
    if (login == null | password == null) {
      showEmptyFieldDialog();
    } else {
      Usuario usuario = PersistanceWrapper.getInstance().getUsuario(login);
      if (usuario != null) {
        password = CryptUtils.sha1Hash(password);
        if ((usuario.matricula.contentEquals(login) || usuario.email.contentEquals(login)) && usuario.senha.contentEquals(password)) {
          if (usuario.aluno) {
            launchAlunoActivity(login);
          } else if (usuario.professor) {
            launchProfessorActivity(login);
          }
        } else {
          showWrongUserPassDialog();
        }
      } else {
        showWrongUserPassDialog();
      }
    }
  }

  private String getText(EditText editText) {
    if (editText.getText() == null || editText.getText().toString().length() == 0) {
      return null;
    } else {
      return editText.getText().toString();
    }
  }

  private void launchAlunoActivity(String username) {
    Intent intent = new Intent(getActivity(), AlunoActivity.class);
    intent.putExtras(putUsernameBundle(username));
    getActivity().startActivity(intent);
    getActivity().finish();
  }

  private void launchProfessorActivity(String username) {
    Intent intent = new Intent(getActivity(), ProfessorActivity.class);
    intent.putExtras(putUsernameBundle(username));
    getActivity().startActivity(intent);
    getActivity().finish();
  }

  private void showEmptyFieldDialog() {
    final AlertDialog userNotFoundDialog = new AlertDialog.Builder(getActivity())
        .setTitle("Erro")
        .setMessage("Usuário ou senha não preenchidos")
        .setCancelable(true)
        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
          @Override public void onClick(DialogInterface dialogInterface, int i) {
            dialogInterface.dismiss();
          }
        })
        .show();
  }

  private void showWrongUserPassDialog() {
    final AlertDialog wrongUserPasswordDialog = new AlertDialog.Builder(getActivity())
        .setTitle("Erro")
        .setMessage("Usuário ou senha incorretos")
        .setCancelable(true)
        .setPositiveButton("OK", new DialogInterface.OnClickListener() {
          @Override public void onClick(DialogInterface dialogInterface, int i) {
            dialogInterface.dismiss();
          }
        })
        .show();
  }

  private Bundle putUsernameBundle(String username) {
    Bundle bundle = new Bundle();
    bundle.putString(Keys.KEY_USERNAME, username);
    return bundle;
  }

}
