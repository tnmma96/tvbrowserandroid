package org.tvbrowser.settings;

import org.tvbrowser.tvbrowser.R;

import android.app.Activity;
import android.content.SharedPreferences;
import android.content.SharedPreferences.OnSharedPreferenceChangeListener;
import android.os.Bundle;
import android.preference.CheckBoxPreference;
import android.preference.ListPreference;
import android.preference.PreferenceFragment;
import android.preference.PreferenceManager;
import android.util.Log;

public class TvbPreferenceFragment extends PreferenceFragment implements OnSharedPreferenceChangeListener {
  @Override
  public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    
    addPreferencesFromResource(R.xml.data_download_preferences);
    
    CheckBoxPreference progTable = (CheckBoxPreference) findPreference(getResources().getString(R.string.PROG_TABLE_ACTIVATED));
    
    ListPreference channelLogoName = (ListPreference) findPreference(getResources().getString(R.string.CHANNEL_LOGO_NAME_PROGRAM_TABLE));
    Log.d("info" , " progtable " + progTable + " " + channelLogoName);
    if(progTable != null && channelLogoName != null) {
      channelLogoName.setEnabled(progTable.isChecked());
    }
  }
  
  @Override
  public void onAttach(Activity activity) {
    super.onAttach(activity);
    
    PreferenceManager.getDefaultSharedPreferences(getActivity()).registerOnSharedPreferenceChangeListener(this);
  }
  
  @Override
  public void onDetach() {
    super.onDetach();
    
    PreferenceManager.getDefaultSharedPreferences(getActivity()).unregisterOnSharedPreferenceChangeListener(this);
  }

  @Override
  public void onSharedPreferenceChanged(SharedPreferences sharedPreferences,
      String key) {
    if(key.equals(getResources().getString(R.string.DAYS_TO_DOWNLOAD)) 
        || key.equals(getResources().getString(R.string.CHANNEL_LOGO_NAME_PROGRAMS_LIST))
        || key.equals(getResources().getString(R.string.CHANNEL_LOGO_NAME_PROGRAM_TABLE))
        || key.equals(getResources().getString(R.string.DETAIL_PICTURE_ZOOM))) {
      ListPreference lp = (ListPreference) findPreference(key);
      
      if(lp != null) {
        lp.setSummary("dummy"); // required or will not update
        
        String value = String.valueOf(lp.getEntry());
        
        if(value.endsWith("%")) {
          value += "%";
        }
        
        lp.setSummary(value);
      }
    }
    else if(key.equals(getResources().getString(R.string.PROG_TABLE_ACTIVATED))) {
      CheckBoxPreference progTable = (CheckBoxPreference) findPreference(key);
      
      ListPreference channelLogoName = (ListPreference) findPreference(getResources().getString(R.string.CHANNEL_LOGO_NAME_PROGRAM_TABLE));
      
      if(progTable != null && channelLogoName != null) {
        channelLogoName.setEnabled(progTable.isChecked());
      }
    }
  }
}