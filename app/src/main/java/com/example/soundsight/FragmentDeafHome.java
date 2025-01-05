package com.example.soundsight;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

public class FragmentDeafHome extends Fragment {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Optional: Put any specific initialization logic here
    }

    // Inflate your specific layout (fragment_deaf_home.xml) here
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Ensure you're using the correct layout XML name (fragment_deaf_home)
        return inflater.inflate(R.layout.fragment_deaf_home, container, false);
    }
}
