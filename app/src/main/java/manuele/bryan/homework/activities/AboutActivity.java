package manuele.bryan.homework.activities;

import android.app.Activity;
import android.content.Intent;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.suredigit.inappfeedback.FeedbackDialog;

import manuele.bryan.homework.R;


public class AboutActivity extends Activity {

    private FeedbackDialog feedBack;

    LinearLayout githubButton;
    LinearLayout googlePlayButton;
    LinearLayout feedbackButton;

    TextView tv1;
    TextView tv2;
    TextView tv3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);

        getActionBar().setDisplayHomeAsUpEnabled(true);

        githubButton = (LinearLayout) findViewById(R.id.githubButton);
        googlePlayButton = (LinearLayout) findViewById(R.id.googlePlayButton);
        feedbackButton = (LinearLayout) findViewById(R.id.feedBackButton);

        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);
        tv3 = (TextView) findViewById(R.id.tv3);

        Typeface robotoFont = Typeface.createFromAsset(getBaseContext().getAssets(),
                "fonts/Roboto-Light.ttf");

        tv1.setTypeface(robotoFont);
        tv2.setTypeface(robotoFont);
        tv3.setTypeface(robotoFont);

        githubButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://github.com/StarZero";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        googlePlayButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String url = "https://play.google.com/store/apps/developer?id=User001";
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(url));
                startActivity(intent);
            }
        });

        feedBack = new FeedbackDialog(this, "AF-2E2A9AF077C1-12");


        feedbackButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //TODO: feedback

                feedBack.show();

            }
        });

    }

    @Override
    protected void onPause() {
        super.onPause();
        feedBack.dismiss();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                onBackPressed();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
