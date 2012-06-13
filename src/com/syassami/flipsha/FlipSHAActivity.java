package com.syassami.flipsha;

import java.util.List;

import android.app.Activity;
import android.content.Intent;

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
	private Button emailButton;
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
                    		String result = CoinFlip.init(friendName, headsTailsSpinner.getSelectedItem().toString());
                    }
            	else {
                    Toast.makeText(getApplicationContext(),
                    		"Please select one of your friends", 0).show();
            	}
            }
        });
        /*
         * Dealing with email button
         */
        
        emailButton = (Button)this.findViewById(R.id.emailButton);
        emailButton.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            	String result = CoinFlip.init(friendName, headsTailsSpinner.getSelectedItem().toString());
            	sendEmail(result);
            }
        });
    }
    private void sendEmail(String result){
    	/* Create the Intent */
    	
    	Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
    	emailIntent.setType("plain/text");
    	
    	//emailIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

    	/* Fill it with Data */
    	emailIntent.setType("plain/text");
    	//emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL, new String[]{"to@email.com"});
    	emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Here's the code from FlipSHA");
    	emailIntent.putExtra(android.content.Intent.EXTRA_TEXT, "After the coin is flipped and" +
    			" you don't believe in your friend, check this hash "+ result);

    	/* Send it off to the Activity-Chooser */
    	startActivity(emailIntent);  
    }
}