package com.syassami.flipsha;

import java.util.List;

import android.app.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

public class FlipSHAActivity extends Activity {
	private Button sendButton;
	private ListView friendList;
	private Spinner headsTailsSpinner;
    /** Called when the activity is first created. */
	private ArrayAdapter<String> adapter;
	static final String[] FRIENDS = new String[] {"Bob","Alice","Koc","Shayan"};
	static final String[] OPTIONS = new String[] {"HEADS","TAILS"};
	static final String TAG = "flipsha";
	private String friendName;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.main);
        
        /*
         * Dealing with the list of friends you have
         */
        friendList = (ListView)this.findViewById(R.id.friendList);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, 
        		android.R.layout.simple_list_item_1, android.R.id.text1, FRIENDS);
		friendList.setAdapter(adapter);
        friendList.setOnItemClickListener(new OnItemClickListener() {
		@Override
		public void onItemClick(AdapterView<?> parent, View view,
		        int position, long id) {
		      Toast.makeText(getApplicationContext(), "Selected "+((TextView) view).getText(),
		         Toast.LENGTH_SHORT).show();
		         friendName = (friendList.getItemAtPosition(position)).toString();
		    }
		  });
        /*
         * Dealing with the handling of the heads or tails spinner element
         */
        
        headsTailsSpinner = (Spinner)this.findViewById(R.id.headsTailsSpinner);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<String>(this,
        		android.R.layout.simple_spinner_item, OPTIONS);
        dataAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        headsTailsSpinner.setAdapter(dataAdapter);
        
        
        /*
         * Dealing with the sending button
         */
        sendButton = (Button)this.findViewById(R.id.sendButton);
        /*
         * Set button to send to whomever based on selected item in name array
         */
        
        sendButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	if (friendName!=null){
                    Toast.makeText(getApplicationContext(),
                    		"Sending to "+ friendName, 0).show();
                    }
            	else {
                    Toast.makeText(getApplicationContext(),
                    		"Please select one of your friends", 0).show();
            	}
            }
        });
    }
}