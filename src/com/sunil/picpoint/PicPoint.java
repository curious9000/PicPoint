package com.sunil.picpoint;

import java.io.IOException;
import java.util.ArrayList;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;

import android.app.Activity;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.GridView;
import android.widget.Toast;

public class PicPoint extends Activity {
	/** imGroupList is an arraylist of ImageGroups **/
    private ArrayList<ImageGroup> imgGroupList = new ArrayList<ImageGroup>();
    private int imgGroupIndex = 0; 
	private ImageAdapter imgAdapter;
    private ImageGroup _imgGroup;
	private static String TAG = "PicPoint";
	private MediaPlayer mp;
	
	private String[] img1names = new String[20];
	private String[] img2names = new String[20];;
	private String[] img1sources = new String[20];
	private String[] img2sources = new String[20];
	private String[] img1sounds = new String[20];
	private String[] img2sounds = new String[20];
	private int index = 0;
	private Resources res;
	
    /** Called when the activity is first created. */
	@Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        res = getResources();
        
       // getImageIds();
        try {
			getImageGroupsFromXml();
		} catch (XmlPullParserException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
      
        setContentView(R.layout.main);
        GridView gridView = (GridView) findViewById(R.id.gridView1);
        
        gridView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> arg0, View arg1,
									int position, long id) {
				playSound(id);
			}
		});
        
        imgAdapter = new ImageAdapter(this, imgGroupList.get(imgGroupIndex));
        //Set image adapter that has been created with the first image group
        gridView.setAdapter(imgAdapter);
    }

	
	private void playSound(long id) {
		
		int image_seq = (int) id;
		int sound_file = R.raw.tada ;
		
		switch(image_seq) {
			case 0:
				sound_file = imgGroupList.get(imgGroupIndex).getImageSoundIds()[0];
				break;
			case 1:
				sound_file = imgGroupList.get(imgGroupIndex).getImageSoundIds()[1];
				break;
			default:
			
		}
		
		mp = MediaPlayer.create(this, sound_file);
		mp.setOnCompletionListener(new OnCompletionListener() {
			@Override
			public void onCompletion(MediaPlayer mp) {
				mp.release();
			}
		});
		
	    mp.start();
	   
	}

	public void showNextImageGroup(View v) {
		Log.d(TAG, "ShowNext");
		_imgGroup = getNextImageGroup();
		if (_imgGroup != null) {
			imgAdapter.setImageGroup(_imgGroup);
			imgAdapter.notifyDataSetChanged();
			Log.d(TAG, "After adapter change");
		}
	}
	
	public void showPrevImageGroup(View v) {
		Log.d(TAG, "ShowPrev");
		_imgGroup = getPrevImageGroup();
		if (_imgGroup != null) {
			imgAdapter.setImageGroup(_imgGroup);
			imgAdapter.notifyDataSetChanged();
			Log.d(TAG, "After adapter change");
		}
	}
	
	private ImageGroup getNextImageGroup() {
		Log.d(TAG, "Next " + String.valueOf(imgGroupIndex) + ' '+  String.valueOf(imgGroupList.size()));
		if (imgGroupIndex < (imgGroupList.size() - 1)) {
			imgGroupIndex++;
			Log.d(TAG, "Next after ++ " + String.valueOf(imgGroupIndex));
			return imgGroupList.get(imgGroupIndex);
		}
		else {
			return null;
		}
			
	}
	
	private ImageGroup getPrevImageGroup() {
		if (imgGroupIndex > 0) {
			imgGroupIndex--;
			return imgGroupList.get(imgGroupIndex);
		}
		else {
			return null;
		}
	}

	public void exitPicPoint(View v) {
		finish();
	}
	
	

	private void getImageGroupsFromXml() 
	throws XmlPullParserException, IOException {

		XmlPullParser xpp = res.getXml(R.xml.imagegroups);

		String curr_tag = "";
		index = -1; /* index for the arrays to store data from xml */
		
		xpp.next();
		int eventType = xpp.getEventType();

		while (eventType != XmlPullParser.END_DOCUMENT) {
			if(eventType == XmlPullParser.START_DOCUMENT) {
				System.out.println("Start document");

			} else if(eventType == XmlPullParser.START_TAG) {
				System.out.println("Start tag "+xpp.getName());
				curr_tag = xpp.getName().toLowerCase().trim();
				System.out.println("Start tag lower  "+ curr_tag);
				if (curr_tag.equals("group"))  {
					processTag(curr_tag, " ");
				}

			} else if(eventType == XmlPullParser.END_TAG) {
				System.out.println("End tag "+xpp.getName());

			} else if(eventType == XmlPullParser.TEXT) {
				System.out.println("Text "+xpp.getText());
				processTag(curr_tag, xpp.getText());
			}
			eventType = xpp.next();
		}
		System.out.println("End document");
		processTag("xmlend", " ");
	}   

	
	/*
	 * Store data from xml into a set of arrays. In the end, an array of
	 *  ImageGroup objects is created from the data obtained from xml.
	 */
	private void processTag(String tag, String data) {
		System.out.println(tag + " " + data + " index " + index);
		
		if (tag.equals("group")) {
			index++;
		} else if (tag.equals("image1name")) {
			img1names[index] = data;
		} else if (tag.equals("image2name")) {
			img2names[index] = data;
		} else if (tag.equals("image1source")) {
			img1sources[index] = data;
		} else if (tag.equals("image2source")) {
			img2sources[index] = data;
		} else if (tag.equals("image1sound")) {
			img1sounds[index] = data;
		} else if (tag.equals("image2sound")) {
			img2sounds[index] = data;
		} else if (tag.equals("xmlend")) {
			Resources r = getResources();
			String pkName = getPackageName();
			for (int i= 0; i <= index; i = i + 1) {
			    int imageResource1 = r.getIdentifier(img1sources[i], 
			    		"drawable",	pkName);
			    int imageResource2  = r.getIdentifier(img2sources[i], 
			    		"drawable",	pkName);
			    int soundResource1 = r.getIdentifier(img1sounds[i], 
			    		"raw",	pkName);
			    int soundResource2  = r.getIdentifier(img2sounds[i], 
			    		"raw",	pkName);
				imgGroupList.add(new ImageGroup(img1names[i],
												imageResource1, 
												soundResource1,
												img2names[i],
												imageResource2,
												soundResource2
												));
			}
		}
	}
}

	