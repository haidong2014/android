package com.infodeliver.ui;

import android.view.View;

public interface NSOnClickListener {

	void onClick(View item, int position, int which);
	
	void onPrivateEvent(int position, int which, Object eventObject);
}
