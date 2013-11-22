package com.mitchbarry.glassware.posttotwitter;

import android.app.Activity;
import android.app.ActionBar;
import android.app.Fragment;
import android.os.AsyncTask;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.mitchbarry.glassware.posttotwitter.util.TwitterService;

import java.util.List;

public class TwitterPostActivity extends Activity {

    private static final int SPEECH_REQUEST = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twitterpost);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new TwitterPostFragment())
                    .commit();
        }
    }

    @Override
    protected void onResume()
    {
        super.onResume();
        List<String> results = getIntent().getExtras()
                .getStringArrayList(RecognizerIntent.EXTRA_RESULTS);
        String spokenText = results.get(0);

        spokenText += " #hackomaha";

        new AsyncTask<String, Void, Void>() {
            @Override
            protected Void doInBackground(String... params) {
                TwitterService service = new TwitterService();
                service.Post(params[0]);
                return null;
            }
        }.execute(spokenText);

    }

    public static class TwitterPostFragment extends Fragment {

        public TwitterPostFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_twitterpost, container, false);
            return rootView;
        }
    }

}
