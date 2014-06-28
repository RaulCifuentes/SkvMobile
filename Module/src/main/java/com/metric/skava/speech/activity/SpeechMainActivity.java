package com.metric.skava.speech.activity;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.speech.RecognizerIntent;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.metric.skava.R;
import com.metric.skava.app.activity.SkavaFragmentActivity;

import java.util.ArrayList;

public class SpeechMainActivity extends SkavaFragmentActivity {

    private static final int REQUEST_CODE = 1234;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.speech_main_activity);
        // savedInstanceState is non-null when there is fragment state
        // saved from previous configurations of this activity
        // (e.g. when rotating the screen from portrait to landscape).
        // In this case, the fragment will automatically be re-added
        // to its container so we don't need to manually add it.
        // For more information, see the Fragments API guide at:
        //
        // http://developer.android.com/guide/components/fragments.html
        //
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.speech_main_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }


    public  boolean isConnected()
    {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo net = cm.getActiveNetworkInfo();
        if (net!=null && net.isAvailable() && net.isConnected()) {
            return true;
        } else {
            return false;
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK) {

        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public class PlaceholderFragment extends Fragment {

        Button Start;
        TextView Speech;
        Dialog match_text_dialog;
        ListView textlist;
        ArrayList<String> matches_text;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.speech_main_fragment, container, false);

            Start = (Button)rootView.findViewById(R.id.start_reg);
            Speech = (TextView)rootView.findViewById(R.id.speech);
            Start.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if(((SpeechMainActivity)getActivity()).isConnected()){
                        Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
                        intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                                RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
                        startActivityForResult(intent, REQUEST_CODE);
                    }
                    else{
                        Toast.makeText(getActivity().getApplicationContext(), "Plese Connect to Internet", Toast.LENGTH_LONG).show();
                    }}
            });

            return rootView;
        }

        @Override
        public void onActivityResult(int requestCode, int resultCode, Intent data) {
            match_text_dialog = new Dialog(SpeechMainActivity.this);
            match_text_dialog.setContentView(R.layout.speech_dialog_fragment);
            match_text_dialog.setTitle("Select Matching Text");
            textlist = (ListView)match_text_dialog.findViewById(R.id.list);
            matches_text = data
                    .getStringArrayListExtra(RecognizerIntent.EXTRA_RESULTS);
            ArrayAdapter<String> adapter =    new ArrayAdapter<String>(getActivity(),
                    android.R.layout.simple_list_item_1, matches_text);
            textlist.setAdapter(adapter);
            textlist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view,
                                        int position, long id) {
                    Speech.setText("You have said " +matches_text.get(position));
                    match_text_dialog.hide();
                }
            });
            match_text_dialog.show();
            super.onActivityResult(requestCode, resultCode, data);
        }
    }

    public void onPreExecuteImportAppData(){
//        mMainContainedFragment.getBackgroudImage().setVisibility(View.GONE);
    }

    public void onPreExecuteImportUserData(){
//        mMainContainedFragment.getBackgroudImage().setVisibility(View.GONE);
    }

    public void onPostExecuteImportAppData(){
//        mMainContainedFragment.getBackgroudImage().setVisibility(View.VISIBLE);
    }

    public void onPostExecuteImportUserData(){
//        mMainContainedFragment.getBackgroudImage().setVisibility(View.VISIBLE);
    }

    public void showProgressBar(final boolean show, String text, boolean longTime) {

    }
}
