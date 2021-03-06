package com.codingwithjames.ekminuterukquotes.fragments;


import android.content.Context;
import android.graphics.Color;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v4.app.Fragment;
import android.support.v7.widget.CardView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.codingwithjames.ekminuterukquotes.R;

import org.json.JSONException;
import org.json.JSONObject;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.Random;


public class PageFragment extends Fragment {

    TextView mQuotes, mAuthor, mSwipeLeft;
    ConstraintLayout mParentLayout;
    ProgressBar mProgressBar;
    CardView mQuoteCard;


    public PageFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_page, container, false);
        mQuotes = view.findViewById(R.id.quote);
        mAuthor = view.findViewById(R.id.author);
        mParentLayout = view.findViewById(R.id.parentLayout);
        mProgressBar = view.findViewById(R.id.progressBar);
        mQuoteCard = view.findViewById(R.id.quoteCard);
        mSwipeLeft = view.findViewById(R.id.swipeLeft);

        mQuoteCard.setVisibility(View.GONE);
        mSwipeLeft.setVisibility(View.INVISIBLE);

        if (getUserVisibleHint()) {
            if(isNetworkAvailable()){
                new JsonTask().execute();
            }else{
                mProgressBar.setVisibility(View.INVISIBLE);
                mQuoteCard.setVisibility(View.VISIBLE);
                mQuotes.setText(R.string.no_internet);
                mAuthor.setText(R.string.no_internet_author);
                mSwipeLeft.setVisibility(View.VISIBLE);
                mParentLayout.setBackgroundResource(R.drawable.bg);
                Toast.makeText(getContext(), "Please check your internet connection",
                        Toast.LENGTH_SHORT).show();
            }
        }



        return view;
    }

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if(isVisibleToUser && isResumed()){
            new JsonTask().execute();
        }
    }

    public class JsonTask extends AsyncTask<Void, Void, JSONObject> {

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mProgressBar.setVisibility(View.VISIBLE);
            mQuotes.setText("");
            mAuthor.setText("");
        }

        @Override
        protected JSONObject doInBackground(Void... params) {

            String str="https://api.forismatic.com/api/1.0/?method=getQuote&format=json&lang=en";

            URLConnection connection = null;
            BufferedReader reader = null;

            try {
                URL url = new URL(str);
                connection = url.openConnection();
                connection.connect();

                reader = new BufferedReader(new InputStreamReader(connection.getInputStream()));

                StringBuffer stringBuffer = new StringBuffer();
                String line;
                while ((line = reader.readLine()) != null)
                {
                    stringBuffer.append(line);
                }

                return new JSONObject(stringBuffer.toString());

            } catch (Exception ex) {
                Log.e("App: ", "JsonTask", ex);
                return null;
            } finally {
                if(reader != null)
                {
                    try {
                        reader.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }
        }

        @Override
        protected void onPostExecute(JSONObject response) {
            super.onPostExecute(response);
            mProgressBar.setVisibility(View.INVISIBLE);
            mSwipeLeft.setVisibility(View.VISIBLE);
            if(response != null)
            {
                try {
                    String quote = response.getString("quoteText");
                    String author = response.getString("quoteAuthor");
                    mQuotes.setText(quote);
                    if(author.isEmpty()){
                        mAuthor.setText("- Anonymous");
                    }else{
                        mAuthor.setText("- "+author);
                    }

                    Random rnd = new Random();
                    int color = Color.argb(255, rnd.nextInt(256), rnd.nextInt(256), rnd.nextInt(256));
                    mParentLayout.setBackgroundColor(color);
                    mQuoteCard.setVisibility(View.VISIBLE);

                    Log.e("App", "Success");
                } catch (JSONException ex) {
                    Log.e("App", "Failure", ex);
                }
            }
        }
    }

    private boolean isNetworkAvailable() {
        ConnectivityManager connectivityManager
                = (ConnectivityManager) getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetworkInfo = connectivityManager.getActiveNetworkInfo();
        return activeNetworkInfo != null && activeNetworkInfo.isConnected();
    }
}
