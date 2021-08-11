package edu.idgs902.macrobios.sistemainventario.controller;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.Lifecycle;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import edu.idgs902.macrobios.sistemainventario.R;
import edu.idgs902.macrobios.sistemainventario.view.tabs.ClienteFragment;
import edu.idgs902.macrobios.sistemainventario.view.tabs.ProductoFragment;
import edu.idgs902.macrobios.sistemainventario.view.tabs.ProveedorFragment;
import edu.idgs902.macrobios.sistemainventario.view.tabs.VendedorFragment;

public class FragmentAdapter extends FragmentStateAdapter {


    public FragmentAdapter(@NonNull FragmentManager fragmentManager, @NonNull Lifecycle lifecycle) {
        super(fragmentManager, lifecycle);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {

        switch (position) {
            case 1:
                return new ClienteFragment();
            case 2:
                return new ProveedorFragment();
            case 3:
                return new ProductoFragment();
            default:
                return new VendedorFragment();
        }

    }

    @Override
    public int getItemCount() {
        return 4;
    }
}
