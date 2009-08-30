package cmupdaterapp.ui;

import java.util.List;

import cmupdaterapp.service.UpdateInfo;

import android.app.TabActivity;

public abstract class IUpdateProcessInfo extends TabActivity
{
	/**
	 * Switches to the update chooser layout, providing the user with an UI to choose which update
	 * to download
	 * 
	 * @param availableUpdates
	 */
	public abstract void switchToUpdateChooserLayout(List<UpdateInfo> availableUpdates);
	
	public abstract void switchToDownloadingLayout(UpdateInfo downloadingUpdate);
	
	//public abstract void updateDownloadProgress(int downloaded, int total, long StartTime);
	public abstract void updateDownloadProgress(int downloaded, int total, String downloadedText, String speedText, String remainingTimeText);
	
	public abstract void updateDownloadMirror(String mirror);
}