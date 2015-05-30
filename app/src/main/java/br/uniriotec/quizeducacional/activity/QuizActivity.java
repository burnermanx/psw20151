package br.uniriotec.quizeducacional.activity;

import android.graphics.Color;
import android.os.Build;
import android.support.v7.app.AlertDialog;
import android.content.DialogInterface;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.SparseArray;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.ScaleAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

import br.uniriotec.quizeducacional.model.QuestionResultBean;
import br.uniriotec.quizeducacional.model.QuizResultBean;

import butterknife.OnClick;

import com.readystatesoftware.systembartint.SystemBarTintManager;
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
    @InjectView(R.id.quiz_send_fab) FloatingActionButton mSendFab;
    @InjectView(R.id.indicator) CirclePageIndicator mIndicator;
    @InjectView(R.id.quiz_txt_end) TextView mTextEndQuiz;

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
        setToolbarPadding();

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
            if (mSendFab.getVisibility() != View.VISIBLE) {
                showFab();
            }
        } else {
            hideFab();
        }
    }

    private void showFab() {
        ScaleAnimation expandAnimation = new ScaleAnimation(0.1f, 1, 0.1f, 1, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
        expandAnimation.setFillAfter(true);
        expandAnimation.setDuration(200);
        expandAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                mSendFab.setClickable(true);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        mSendFab.setVisibility(View.VISIBLE);
        mSendFab.startAnimation(expandAnimation);
    }

    private void hideFab() {
        if (mSendFab.getVisibility() == View.VISIBLE) {
            ScaleAnimation shrinkAnimation = new ScaleAnimation(1, 0, 1, 0, Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
            shrinkAnimation.setFillAfter(true);
            shrinkAnimation.setDuration(200);
            shrinkAnimation.setAnimationListener(new Animation.AnimationListener() {
                @Override
                public void onAnimationStart(Animation animation) {
                    mSendFab.setClickable(false);
                }

                @Override
                public void onAnimationEnd(Animation animation) {
                    mSendFab.setVisibility(View.GONE);
                    mSendFab.clearAnimation();
                }

                @Override
                public void onAnimationRepeat(Animation animation) {

                }
            });
            mSendFab.startAnimation(shrinkAnimation);
        }
    }

    private void showEmptyQuizView() {
        int finalScore = calculateScore();
        String message = mTextEndQuiz.getText() + "\n Voce fez " + String.valueOf(finalScore) + " pontos.";
        mTextEndQuiz.setText(message);
        hideMenu = true;
        mViewPager.setVisibility(View.GONE);
        hideFab();
        mIndicator.setVisibility(View.GONE);
        invalidateOptionsMenu();
        mEmptyLayout.setVisibility(View.VISIBLE);
    }

    private int calculateScore() {
        int score = 0;
        for (QuestionResultBean result : mQuizResultBean.questionResults) {
            if (result.selectedAnswer.contentEquals(result.rightAnswer)) {
                score += result.questionValue;
            }
        }
        return score;
    }

    private void hideEmptyQuizView() {
        hideMenu = false;
        mEmptyLayout.setVisibility(View.GONE);
        invalidateOptionsMenu();
        mIndicator.setVisibility(View.VISIBLE);
        mSendFab.setVisibility(View.VISIBLE);
        mViewPager.setVisibility(View.VISIBLE);
    }

    private void showConfirmationDialog() {
        DialogInterface.OnClickListener positiveClick = new DialogInterface.OnClickListener() {
            @Override public void onClick(DialogInterface dialogInterface, int i) {
                showEmptyQuizView();
            }
        };

        AlertDialog.Builder alertBuilder= new AlertDialog.Builder(this);
        alertBuilder.setTitle(getResources().getString(R.string.dialog_header_attention));
        alertBuilder.setMessage(getResources().getString(R.string.quiz_dialog_confirm_send));
        alertBuilder.setPositiveButton(getResources().getString(R.string.dialog_button_send),
                positiveClick);
        alertBuilder.setNegativeButton(getResources().getString(R.string.dialog_button_cancel), null);
        alertBuilder.show();
    }

    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = this.getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = this.getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }

    public void setToolbarPadding() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
                // create our manager instance after the content view is set
                SystemBarTintManager tintManager = new SystemBarTintManager(this);
                // enable status bar tint
                tintManager.setStatusBarTintEnabled(true);
                // enable navigation bar tint
                tintManager.setNavigationBarTintEnabled(true);
                // set the transparent color of the status bar, 20% darker
                tintManager.setTintColor(Color.parseColor("#20000000"));
            }
            mToolbar.setPadding(0, getStatusBarHeight(), 0, 0);
        }
    }

    @OnClick(R.id.quiz_send_fab) public void sendQuizResult() {
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
