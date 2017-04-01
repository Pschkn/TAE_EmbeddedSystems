package smartcart.activities;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.ArrayList;

import smartcart.model.shoppingList;

/**
 * Created by admin on 25.03.2017.
 */

public class goShoppingDefaultFragment extends Fragment {
    ListView buyListView, boughtListView;
    TextView currentItemTV;
    View rootView;
    ArrayAdapter<String> toBuyAdapter, boughtAdapter;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        // Inflate the layout resource that'll be returned
        View rootView = inflater.inflate(R.layout.go_shopping_default, container, false);

        // Get the arguments that was supplied when
        // the fragment was instantiated in the
        // CustomPagerAdapter
        Bundle args = getArguments();

        currentItemTV = (TextView)rootView.findViewById(R.id.currentItemTV);

        buyListView = (ListView)rootView.findViewById(R.id.toBuyListView);
        if(toBuyAdapter != null){
            buyListView.setAdapter(toBuyAdapter);
            toBuyAdapter.notifyDataSetChanged();
        }

        boughtListView = (ListView)rootView.findViewById(R.id.boughtListView);
        if(boughtAdapter != null){
            boughtListView.setAdapter(boughtAdapter);
            boughtAdapter.notifyDataSetChanged();
        }

        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();

        currentItemTV.setText(shoppingList.GetCurrentItem());

        buyListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                shoppingList.BuyItem(position);
                toBuyAdapter.notifyDataSetChanged();
                boughtAdapter.notifyDataSetChanged();

                currentItemTV.setText(shoppingList.GetCurrentItem());
            }
        });

        boughtListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position,
                                    long id) {
                shoppingList.UnBuyItem(position);
                toBuyAdapter.notifyDataSetChanged();
                boughtAdapter.notifyDataSetChanged();

                currentItemTV.setText(shoppingList.GetCurrentItem());
            }
        });
    }

    @Override
    public void onResume(){
        super.onResume();
    }

    public void SetAdapters(ArrayAdapter<String> toBuyAdapter, ArrayAdapter<String> boughtAdapter){
        this.toBuyAdapter = toBuyAdapter;
        this.boughtAdapter = boughtAdapter;
    }

    public void UpdateCurrentItem(){
        currentItemTV.setText(shoppingList.GetCurrentItem());
    }
}
