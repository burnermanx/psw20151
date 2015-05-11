package br.uniriotec.quizeducacional.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import br.uniriotec.quizeducacional.model.QuestionResultBean;
import br.uniriotec.quizeducacional.model.QuizResultBean;

import butterknife.OnClick;
import com.viewpagerindicator.CirclePageIndicator;
import java.util.List;

import br.uniriotec.quizeducacional.R;
import br.uniriotec.quizeducacional.fragment.QuestionFragment;
import br.uniriotec.quizeducacional.model.QuestionBean;
import br.uniriotec.quizeducacional.test.QuestionGenerator;
import butterknife.ButterKnife;
import butterknife.InjectView;

public class QuizActivity extends AppCompatActivity {

    @InjectView(R.id.pager) ViewPager mViewPager;
    @InjectView(R.id.activity_quiz_module_name) TextView mTextModuleName;
    @InjectView(R.id.activity_quiz_layout_empty) LinearLayout mEmptyLayout;
    @InjectView(R.id.quiz_toolbar) Toolbar mToolbar;
    @InjectView(R.id.quiz_send_btn) Button mSendButton;
    @InjectView(R.id.indicator) CirclePageIndicator mIndicator;

    private PagerAdapter mPagerAdapter;
    private List<QuestionBean> mQuestionList = QuestionGenerator.generateQuestionList();
    private QuizResultBean mQuizResultBean = new QuizResultBean();
    private boolean hideMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_quiz);
        ButterKnife.inject(this);
        setSupportActionBar(mToolbar);

        mPagerAdapter = new SlidingPagerAdapter(getSupportFragmentManager());
        mViewPager.setAdapter(mPagerAdapter);
        mIndicator.setViewPager(mViewPager);

        mIndicator.setFillColor(getResources().getColor(R.color.math_primary_color));
    }

    @Override public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_quiz, menu);

        if (hideMenu) {
            for (int i = 0; i < menu.size(); i++) {
                menu.getItem(i).setVisible(false);
            }
        }
        return true;
    }

    @Override public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.menu_action_previous_question:
                goToPreviousPage();
                return true;
            case R.id.menu_action_next_question:
                goToNextPage();
                return true;
        }
        return false;
    }

    public void insertAnswer(long qid, String rightAnswer, String selectedAnswer, int questionValue) {
        boolean questionFound = false;
        QuestionResultBean answer = new QuestionResultBean(qid, rightAnswer, selectedAnswer, questionValue);
        if (!mQuizResultBean.questionResults.isEmpty()) {
            for (QuestionResultBean question : mQuizResultBean.questionResults) {
                if (question.qid == qid) {
                    questionFound = true;
                    question.selectedAnswer = selectedAnswer;
                    break;
                }
            }
        }

        if (!questionFound) {
            mQuizResultBean.questionResults.add(answer);
        }
        handleSendButton();
    }

    public void goToNextPage() {
        mViewPager.setCurrentItem(mViewPager.getCurrentItem() + 1);
    }

    public void goToPreviousPage() {
        mViewPager.setCurrentItem(mViewPager.getCurrentItem() - 1);
    }

    public void removeCurrentQuestion() {
        int currentItem = mViewPager.getCurrentItem();
        mQuestionList.remove(currentItem);
        mPagerAdapter.notifyDataSetChanged();
        if (mQuestionList.isEmpty()) {
            showEmptyQuizView();
        } else {
            hideEmptyQuizView();
        }
    }

    private void handleSendButton() {
        if (mQuestionList.size() == mQuizResultBean.questionResults.size()) {
            mSendButton.setVisibility(View.VISIBLE);
        } else {
            mSendButton.setVisibility(View.GONE);
        }
    }

    private void showEmptyQuizView() {
        hideMenu = true;
        mViewPager.setVisibility(View.GONE);
        mSendButton.setVisibility(View.GONE);
        mIndicator.setVisibility(View.GONE);
        invalidateOptionsMenu();
        mEmptyLayout.setVisibility(View.VISIBLE);
    }

    private void hideEmptyQuizView() {
        hideMenu = false;
        mEmptyLayout.setVisibility(View.GONE);
        invalidateOptionsMenu();
        mIndicator.setVisibility(View.VISIBLE);
        mSendButton.setVisibility(View.VISIBLE);
        mViewPager.setVisibility(View.VISIBLE);
    }

    private void showConfirmationDialog() {
        DialogInterface.OnClickListener positiveClick = new DialogInterface.OnClickListener() {
            @Override public void onClick(DialogInterface dialogInterface, int i) {
                showEmptyQuizView();
            }
        };

        AlertDialog.Builder alertBuilder= new AlertDialog.Builder(this, R.style.AlertDialogStyle);
        alertBuilder.setTitle(getResources().getString(R.string.dialog_header_attention));
        alertBuilder.setMessage(getResources().getString(R.string.quiz_dialog_confirm_send));
        alertBuilder.setPositiveButton(getResources().getString(R.string.dialog_button_send),
            positiveClick);
        alertBuilder.setNegativeButton(getResources().getString(R.string.dialog_button_cancel), null);
        alertBuilder.show();
    }

    @OnClick(R.id.quiz_send_btn) public void sendQuizResult() {
        showConfirmationDialog();
    }

    private class SlidingPagerAdapter extends FragmentStatePagerAdapter {
        private SparseArray<QuestionFragment> mPageReferenceMap
            = new SparseArray<QuestionFragment>();

        public SlidingPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            return QuestionFragment.getFragment(mQuestionList.get(position));
        }

        @Override
        public int getCount() {
            return mQuestionList.size();
        }

        @Override
        public int getItemPosition(Object object) {
            return PagerAdapter.POSITION_NONE;
        }

        @Override public Object instantiateItem(ViewGroup container, int position) {
            Object obj = super.instantiateItem(container, position);

            if (obj instanceof QuestionFragment) {
                QuestionFragment fragment = (QuestionFragment) obj;
                mPageReferenceMap.put(position, fragment);
            }
            return obj;
        }

        @Override public void destroyItem(ViewGroup container, int position, Object object) {
            mPageReferenceMap.remove(position);
            super.destroyItem(container, position, object);
        }
    }
}
