package com.metric.skava.expandedview.activity;

import android.app.Activity;
import android.app.Fragment;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.metric.skava.R;

import java.io.File;
import java.net.URI;

public class TestAutocadMainActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_autocad_main);

        if (savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.test_autocad_main, menu);
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

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_test_autocad_main, container, false);
            return rootView;
        }

        @Override
        public void onViewCreated(View view, Bundle savedInstanceState) {
            super.onViewCreated(view, savedInstanceState);
            ImageView imageView = (ImageView) view.findViewById(R.id.imageView);
            imageView.setImageResource(R.drawable.expanded_tunnel_light);

            Button button = (Button) view.findViewById(R.id.button1);
            button.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    /*
                    * The image Uri is mandatory and it can have the following scheme:
                    * ContentResolver.SCHEMEFILE: absolute local file path ( "file:///mnt/sdcard/download/image.jpg" )
                    * No scheme: absolute local file path ( "/mnt/sdcard/bla/image.jpg" )
                    * ContentResolver.SCHEMECONTENT: database driven file location ( "content://media/external/images/media/112232" )
                    * "http" or "https": remote files.
                    * */
//                    android.resource://[package]/[res type]/[res name]
                    Uri targetUriByName = Uri.parse("android.resource://com.metric.skava/drawable/" + "z_mapping_sections.dwg");
                    Intent intentName = new Intent(Intent.ACTION_VIEW, targetUriByName);
                    startActivity(intentName);
                }
            });

            Button button2 = (Button) view.findViewById(R.id.button2);
            button2.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri targetUriByID = Uri.parse("android.resource://com.metric.skava/" + R.drawable.z_mapping_sections);
                    Intent intentID = new Intent(Intent.ACTION_VIEW, targetUriByID);
                    startActivity(intentID);
                }
            });

            Button button3 = (Button) view.findViewById(R.id.button3);
            button3.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri uriOfFile = Uri.parse("file:///mnt/sdcard/download/mapping_sections.dwg");
                    File file = new File(URI.create(uriOfFile.toString()));
                    if (file.exists()){
                        long lenght = file.length();
                        Intent fileIntent = new Intent(Intent.ACTION_VIEW, uriOfFile);
                        fileIntent.setDataAndType(uriOfFile, "application/autocad_dwg");
                        startActivity(fileIntent);
                    }
                }
            });

            Button button4 = (Button) view.findViewById(R.id.button4);
            button4.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Uri uriOfFile = Uri.parse("file:///android_asset/images/z_mapping_sections.dwg");
//                    File file = new File(URI.create(uriOfFile.toString()));
//                    if (file.exists()){
//                        long lenght = file.length();
                        Intent fileIntent = new Intent(Intent.ACTION_VIEW, uriOfFile);
                        fileIntent.setDataAndType(uriOfFile, "application/autocad_dwg");
                        startActivity(fileIntent);
//                    }
                }
            });

        }

    }

}
