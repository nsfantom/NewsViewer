package tm.fantom.newsviewer.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.widget.TextView;

import tm.fantom.newsviewer.R;

/**
 * Created by fantom on 23-May-17.
 */

public class SplashActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        String titleText = "News<b><font color=#ff1010>Viewer</font></b>";
        ((TextView) findViewById(R.id.splash_title)).setText(Html.fromHtml(titleText));

        new Handler().postDelayed(() -> {
            Intent i = new Intent(SplashActivity.this, MainActivity.class);
            startActivity(i);
            finish();
        }, 1000);
    }
}
