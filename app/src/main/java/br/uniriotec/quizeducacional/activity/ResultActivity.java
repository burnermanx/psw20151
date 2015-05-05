package br.uniriotec.quizeducacional.activity;

import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import br.uniriotec.quizeducacional.R;
import br.uniriotec.quizeducacional.constants.Keys;
import br.uniriotec.quizeducacional.model.QuestionResultBean;
import br.uniriotec.quizeducacional.model.QuizResultBean;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class ResultActivity extends AppCompatActivity {

    private int score;

    @InjectView(R.id.result_score_text) TextView txtScore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        ButterKnife.inject(this);

        Bundle extras = getIntent().getExtras();
        if (extras != null) {
            QuizResultBean resultBean = (QuizResultBean) extras.getSerializable(Keys.KEY_QUIZ_RESULT);
            if (resultBean != null) {
                for (QuestionResultBean questionResult : resultBean.questionResults) {
                    if (questionResult.selectedAnswer.contentEquals(questionResult.rightAnswer)) {
                        score = score + questionResult.questionValue;
                    }
                }
                txtScore.setText("Você fez " + String.valueOf(score) + " pontos!");
            }
        }
    }
}
