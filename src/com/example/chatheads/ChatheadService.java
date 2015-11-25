package com.example.chatheads;

import android.animation.Animator;
import android.app.Service;
import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.ClipboardManager.OnPrimaryClipChangedListener;
import android.content.Intent;
import android.graphics.PixelFormat;
import android.os.IBinder;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.RelativeLayout;

public class ChatheadService extends Service{
	WindowManager windowManager;
	RelativeLayout imageview;
	WindowManager.LayoutParams params;
	LayoutInflater inflater;
	ClipboardManager clipboardManager;
	@Override
	public void  onCreate() {
		
		super.onCreate();
		
		Log.i("com.example.Chatheads", "Service Starts");   
		
		//get the window to draw the image view on
		
		windowManager=(WindowManager)getSystemService(WINDOW_SERVICE);
		inflater=(LayoutInflater)getSystemService(LAYOUT_INFLATER_SERVICE);
		 imageview=(RelativeLayout) inflater.inflate(R.layout.box,null);
		 
		 View view = imageview.findViewById(R.id.button1);
		 view.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				// TODO Auto-generated method stub
				
				((View) v.getParent()).setVisibility(View.GONE);
				}
		});
		 
		 params = new WindowManager.LayoutParams(
					WindowManager.LayoutParams.WRAP_CONTENT,
					WindowManager.LayoutParams.WRAP_CONTENT,
					WindowManager.LayoutParams.TYPE_PHONE,
					WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
					PixelFormat.TRANSLUCENT);
//LayoutParams(width, height, _type, _flags, _format)

			params.gravity=Gravity.TOP|Gravity.LEFT;
			params.x=0;
			params.y=100;
			
			windowManager.addView(imageview, params);
			
			imageview.setOnTouchListener(new View.OnTouchListener() {
					private int initialX;
					private int initialY;
					private float initialTouchX;
					private float initialTouchY;
					
					
					@Override public boolean onTouch(View v, MotionEvent event) {
					switch (event.getAction()) {
					case MotionEvent.ACTION_DOWN:
					initialX = params.x;													//store the value of the view where it is drawn
					initialY = params.y;													
					initialTouchX = event.getRawX();										//where it is touched at first
					initialTouchY = event.getRawY();
					return true;
					case MotionEvent.ACTION_UP:
					return true;
					case MotionEvent.ACTION_MOVE:
					params.x = initialX + (int) (event.getRawX() - initialTouchX);			//get where the finger is moving to
					params.y = initialY + (int) (event.getRawY() - initialTouchY);
					windowManager.updateViewLayout(imageview, params);						//update for each step to  be continuous
					return true;
					}
					return false;
					}
					});
			
			
			clipboardManager=(ClipboardManager)getSystemService(CLIPBOARD_SERVICE);
			clipboardManager.addPrimaryClipChangedListener(new OnPrimaryClipChangedListener() {
			ClipData clipdata;
			String data;
			@Override
			public void onPrimaryClipChanged() {
				
				clipdata=clipboardManager.getPrimaryClip();
				Log.i("com.kloudier.chatheads", clipdata.getItemAt(0).getText().toString());
				data=clipdata.getItemAt(0).getText().toString();
			
			
			}
			
			
			
			
			});
		 
		 
		

		
		
		

	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {

		

		return super.onStartCommand(intent, flags, startId);
	}
	
	
	public void closeBox(View view)
	{
		Animation animation=AnimationUtils.loadAnimation(this, R.anim.disappear_animation);
		imageview.findViewById(R.id.box).startAnimation(animation);
		
		
		
		
	}
	
	@Override
	public void onDestroy()
	{
		super.onDestroy();
		if(imageview!=null)
			windowManager.removeView(imageview);
	
	}

	@Override
	public IBinder onBind(Intent arg0) {
	
		return null;
	}

}


