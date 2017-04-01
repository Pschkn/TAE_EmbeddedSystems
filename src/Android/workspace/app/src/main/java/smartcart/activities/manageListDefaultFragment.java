package smartcart.activities;

import android.app.ActionBar;
import android.app.ListActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;

import com.scalified.fab.ActionButton;

import java.util.ArrayList;
import java.util.List;

import smartcart.model.shoppingList;

/**
 * Created by admin on 25.03.2017.
 */

public class manageListDefaultFragment extends Fragment {
    ListView mainListView;
    View rootView;
    ArrayAdapter<String> adapter;
    addProductFragment addProductFragment;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout resource that'll be returned
        super.onCreateView(inflater, container, savedInstanceState);

        rootView = inflater.inflate(R.layout.manage_list_default, container, false);
        addProductFragment = new addProductFragment();

        // Get the arguments that was supplied when
        // the fragment was instantiated in the
        // CustomPagerAdapter
        Bundle args = getArguments();

        mainListView = (ListView)rootView.findViewById(R.id.listView);
        if(adapter!=null){
            mainListView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }
        return rootView;
    }

    @Override
    public void onStart(){
        super.onStart();

        ActionButton addButton = (ActionButton)getActivity().findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RelativeLayout tl = (RelativeLayout) getActivity().findViewById(R.id.addProductFragmentOrange);
                tl.setVisibility(View.VISIBLE);
            }
        });

        Button confirmButton = (Button)getActivity().findViewById(R.id.btnConfirm);
        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RelativeLayout tl = (RelativeLayout) getActivity().findViewById(R.id.addProductFragmentOrange);
                tl.setVisibility(View.INVISIBLE);

                TextView productName = (TextView) getActivity().findViewById(R.id.et_name);
                TextView productQuantity = (TextView) getActivity().findViewById(R.id.et_quantity);

                String name = productName.getText().toString();
                String quantity = productQuantity.getText().toString();

                shoppingList.AddListItem(name + " (" + quantity + ")");
                adapter.notifyDataSetChanged();
            }
        });

        Button cancelButton = (Button)getActivity().findViewById(R.id.btnCancel);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RelativeLayout tl = (RelativeLayout) getActivity().findViewById(R.id.addProductFragmentOrange);
                tl.setVisibility(View.INVISIBLE);
            }
        });
    }



    public void SetAdapter(ArrayAdapter<String> adapter){
        this.adapter = adapter;
    }
/*
    public void Initialize(){
        super.onResume();
        myList = new ArrayList<String>();
        myList.add("apfel");
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_list_item_1, android.R.id.text1, values);
        ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1,android.R.id.text1, (String[])myList.toArray());
    }
*/
    public class ListHandler extends ListActivity {
        ArrayAdapter<String> arrayAdapter;
        ArrayList<String> myList;

        @Override
        protected void onCreate(Bundle state){
            super.onCreate(state);
            myList = new ArrayList<String>();
            initList();
            arrayAdapter = new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, myList);
            ((ListView)(getActivity().findViewById(R.id.listView))).setAdapter(arrayAdapter);
            arrayAdapter.notifyDataSetChanged();
        }

        @Override
        protected void onResume(){
            super.onResume();
            arrayAdapter.notifyDataSetChanged();
        }

        private void initList(){
            myList.add("Apple");
            myList.add("Birne");
            myList.add("Peach");
            myList.add("Banano");
        }
    }
}
