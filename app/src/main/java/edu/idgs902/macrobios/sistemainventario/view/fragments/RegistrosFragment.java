package edu.idgs902.macrobios.sistemainventario.view.fragments;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.android.material.tabs.TabLayout;

import edu.idgs902.macrobios.sistemainventario.R;
import edu.idgs902.macrobios.sistemainventario.controller.adapters.FragmentAdapter;

public class RegistrosFragment extends Fragment {

    private TabLayout tabs;
    private ViewPager2 page2;
    private FragmentAdapter adapter;


    public RegistrosFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_registros, container, false);

        tabs = view.findViewById(R.id.tabs);
        page2 = view.findViewById(R.id.view_page2);


        FragmentManager fm = getChildFragmentManager();
        adapter = new FragmentAdapter(fm, getLifecycle());
        page2.setAdapter(adapter);

        tabs.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                page2.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        page2.registerOnPageChangeCallback(new ViewPager2.OnPageChangeCallback() {
            @Override
            public void onPageSelected(int position) {
                tabs.selectTab(tabs.getTabAt(position));
            }
        });

        return view;

    }
}