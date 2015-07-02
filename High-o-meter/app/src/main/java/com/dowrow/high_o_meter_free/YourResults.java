package com.dowrow.high_o_meter_free;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;

import com.dowrow.high_o_meter_free.R;
import com.dowrow.high_o_meter_free.model.HighOMeter;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.InterstitialAd;


public class YourResults extends ActionBarActivity {

    private InterstitialAd mInterstitialAd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_results);
        double score = 0;


        mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId(getString(R.string.banner_ad_unit_id_big));

        AdRequest adRequest = new AdRequest.Builder().build();

        mInterstitialAd.loadAd(adRequest);
        mInterstitialAd.setAdListener(new AdListener() {
            public void onAdLoaded() {
                mInterstitialAd.show();
            }
        });


        // Get score
        score = getIntent().getDoubleExtra(RedEyes.SCORE_EXTRA, score);
        int status = 0;
        int buzzword = R.string.buzzed;
        int desc = R.string.buzzed_desc;
        switch (HighOMeter.percentToScore(score)) {
            case BUZZED:
                buzzword = R.string.buzzed;
                status = R.drawable.buzzed;
                desc = R.string.buzzed_desc;
                break;
            case HIGH:
                buzzword = R.string.high;
                status = R.drawable.high;
                desc = R.string.high_desc;
                break;
            case STONED:
                buzzword = R.string.stoned;
                status = R.drawable.veryhigh;
                desc = R.string.stoned_desc;
                break;
            case BLAZED:
                buzzword = R.string.blazed;
                status = R.drawable.going;
                desc = R.string.blazed_desc;
                break;
            default:
                buzzword = R.string.gone;
                status = R.drawable.gone;
                desc = R.string.gone_desc;
                break;
        }

        // Set bar title
        setTitle(getString(R.string.you_scored) + " " + Math.round(score) + "%");

        // Set bagde title
        ((TextView)findViewById(R.id.title)).setText(getString(R.string.you_are) + " " + getString(buzzword) + "!");

        // Set bagge
        ((ImageView)findViewById(R.id.badge)).setImageDrawable(getResources().getDrawable(status));

        // Set desc
        ((TextView)findViewById(R.id.description)).setText(getString(desc));

        // Rating bar callbacks
        RatingBar ratingBar = (RatingBar) findViewById(R.id.ratingBar);

        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float rating, boolean fromUser) {
                final float numStars = ratingBar.getRating();
                if (numStars >= 4) {
                    goToGooglePlay();
                }
            }
        });

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        return super.onOptionsItemSelected(item);
    }

    public void goToGooglePlay () {
        final String appPackageName = getPackageName(); // getPackageName() from Context or Activity object
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appPackageName)));
        }

    }

    public void tryAgain (View v) {
        Intent i  = new Intent(this, MainActivity.class);
        startActivity(i);
    }

    public  void getFull (View v) {
        final String appPackageName = "com.dowrow.high_o_meter";
        try {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("market://details?id=" + appPackageName)));
        } catch (android.content.ActivityNotFoundException anfe) {
            startActivity(new Intent(Intent.ACTION_VIEW, Uri.parse("http://play.google.com/store/apps/details?id=" + appPackageName)));
        }
    }
}
