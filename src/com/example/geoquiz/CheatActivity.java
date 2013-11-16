package com.example.geoquiz;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class CheatActivity extends Activity {
	
	private TextView mAnswerTV;
	private Button mAnswerBT;
	public static final String EXTRA_ANSWER_SHOWN = "com.example.geoquiz.answer_shown";
	
	private boolean mAnswer;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_cheat);
		
		mAnswer = getIntent().getBooleanExtra("answerKey", false);

		mAnswerTV = (TextView) findViewById(R.id.answerTv);
		mAnswerBT = (Button) findViewById(R.id.showAnswerBt);
		
		mAnswerBT.setOnClickListener(new View.OnClickListener() {
			
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				if(mAnswer)
				{
					mAnswerTV.setText(R.string.true_button);
				}
				else
				{
					mAnswerTV.setText(R.string.false_button);
				}
				
				//Send data back to MainActivity to indicate that answer has been shown
				Intent intent = new Intent();
				intent.putExtra(EXTRA_ANSWER_SHOWN, true);
				setResult(RESULT_OK, intent);
			}
		});
		
		
	}

}
