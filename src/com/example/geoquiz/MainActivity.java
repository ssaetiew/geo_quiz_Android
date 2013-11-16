package com.example.geoquiz;

import java.util.Random;

import android.annotation.TargetApi;
import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity {

	private Button mTrueBt;
	private Button mFalseBt;
	private Button mCheatBt;
	private TextView mQuestionTv;
	private Button mNextBt;
	private Button mPrevBt;
	private int mPrev;
	private int question;
	private boolean isCheat = false;

	private static final String KEY_INDEX = "index";
	private static final String EXTRA_ANSWER_SHOWN = "com.example.geoquiz.answer_shown";
	private static final String KEY = "answerShown";
	// Create an array and instance of TrueFalse
	private TrueFalse[] mQuestionBank = new TrueFalse[] {
			new TrueFalse(R.string.question_ocean, true),
			new TrueFalse(R.string.question_mideast, false),
			new TrueFalse(R.string.question_africa, false),
			new TrueFalse(R.string.question_america, true),
			new TrueFalse(R.string.question_asia, true) };

	// Generate random number of index. The questions will be randomized
	Random rand = new Random();
	private int mIndex = -1;

	@TargetApi(11) //Suppress Lint's error explicitly because code below
					//is already check compatibility
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		// Get widgets
		mTrueBt = (Button) findViewById(R.id.true_bt);
		mFalseBt = (Button) findViewById(R.id.false_bt);
		mQuestionTv = (TextView) findViewById(R.id.questiont_tv);
		mNextBt = (Button) findViewById(R.id.next_bt);
		mPrevBt = (Button) findViewById(R.id.prev_bt);
		mCheatBt = (Button) findViewById(R.id.cheat_bt);
		
		//Run below codes if device is at least HoneyComb
		if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB)
		{
		ActionBar ab = getActionBar();
		ab.setSubtitle("Quiz of the Day");
		}
		
		// Populate question and display it in text view
		mIndex = rand.nextInt(mQuestionBank.length);
		updateQuestion(mIndex);
		
		mTrueBt.setOnClickListener(new View.OnClickListener() {
				
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				checkAnswer(true);
			}

		});
		mFalseBt.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				checkAnswer(false);
			}
		});

		mNextBt.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				mIndex = rand.nextInt(mQuestionBank.length);
				updateQuestion(mIndex);
			}

		});
		mQuestionTv.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				updateQuestion(mIndex);
			}
		});
		mPrevBt.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if (mPrev < 0) {
					mQuestionTv.setText("No previous question");
				} else {
					question = mQuestionBank[mPrev].getQuestion();
					mQuestionTv.setText(question);
				}
			}
		});
		mCheatBt.setOnClickListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				Intent intent = new Intent(MainActivity.this,
						CheatActivity.class);
				boolean answerIsTrue = mQuestionBank[mIndex].isTrueQuestion();
				intent.putExtra("answerKey", answerIsTrue);
				startActivityForResult(intent, 0);
			}
		});
		// Check to see if mIndex is saved when configuration changed
		if (savedInstanceState != null) {
			mIndex = savedInstanceState.getInt(KEY_INDEX, 0);
			updateQuestion(mIndex);
			mQuestionTv.setText(question);
			boolean isTrue = savedInstanceState.getBoolean(KEY,false);
			if(isTrue)
			{
				isCheat = true;
			}
			
		}

	
	}

	@Override
	public void onSaveInstanceState(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onSaveInstanceState(savedInstanceState);
		Log.i("MainActivity", "onSaveInstanceState");
		savedInstanceState.putInt(KEY_INDEX, mIndex);
		savedInstanceState.putBoolean(KEY, isCheat);
	}
		
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
			if (data == null) {
		return;
		} else {
			isCheat = data.getBooleanExtra(CheatActivity.EXTRA_ANSWER_SHOWN,
					false);
		}
	}

	private void updateQuestion(int mIndex) {
		mPrev = mIndex;
	
		question = mQuestionBank[mIndex].getQuestion();
		mQuestionTv.setText(question);

	}

	private void checkAnswer(boolean userPressed) {
		boolean isTrue = mQuestionBank[mIndex].isTrueQuestion();
		int messageResId = 0;

		if (isCheat) {
			messageResId = R.string.judgment_toast;
			isCheat = false;

		} else {
			if (userPressed == isTrue) {
				messageResId = R.string.correct;
			} else {
				messageResId = R.string.incorrect;
			}
		}
		Toast.makeText(this, messageResId, Toast.LENGTH_SHORT).show();
	}

	


	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.main, menu);
		return true;
	}

}
