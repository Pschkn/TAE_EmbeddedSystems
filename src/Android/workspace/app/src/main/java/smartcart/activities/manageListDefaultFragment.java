package smartcart.activities;

import android.app.ActionBar;
import android.app.ListActivity;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TableLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.scalified.fab.ActionButton;

import java.util.ArrayList;
import java.util.List;

import smartcart.model.shoppingList;

import static android.R.attr.data;

/**
 * Created by admin on 25.03.2017.
 */

public class manageListDefaultFragment extends Fragment {
    ListView mainListView;
    View rootView;
    ArrayAdapter<String> adapter;
    goShoppingDefaultFragment fragmentReference;
    InputMethodManager imm;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout resource that'll be returned
        super.onCreateView(inflater, container, savedInstanceState);

        rootView = inflater.inflate(R.layout.manage_list_default, container, false);

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

        // Buttons
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

                imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);

                if (quantity.trim().equals("")) {
                    shoppingList.AddListItem(name);
                }
                else if (Integer.parseInt(quantity) == 1)
                    shoppingList.AddListItem(name);
                else
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
                imm.hideSoftInputFromWindow(getActivity().getCurrentFocus().getWindowToken(), 0);
            }
        });

        // EditTexts
        final EditText etProductName = (EditText)getActivity().findViewById(R.id.et_name);

            etProductName.addTextChangedListener(new TextWatcher(){
            boolean ignore = false;
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if(ignore){
                    ignore = false;
                    return;
                }

                for(int i = s.length(); i > 0; i--) {

                    if(s.subSequence(i-1, i).toString().equals("\n"))
                        s.replace(i-1, i, "");
                }
                ignore = true;
                etProductName.setText(s.toString());
                ignore = true;
                etProductName.setSelection(etProductName.getText().length());

            }
        });

        //ListView
        ListView listView = (ListView)getActivity().findViewById(R.id.listView);

        listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {

            public boolean onItemLongClick(AdapterView<?> arg0, View v,
                                           int index, long arg3) {

                shoppingList.DeleteListItemAt(index);
                adapter.notifyDataSetChanged();
                Toast.makeText(getActivity(), "Item has been deleted!",
                        Toast.LENGTH_LONG).show();

                fragmentReference.UpdateCurrentItem();
                return false;
            }});
    }

    public void SetFragmentReference(goShoppingDefaultFragment fragment){
        fragmentReference = fragment;
    }
    public void SetAdapter(ArrayAdapter<String> adapter){
        this.adapter = adapter;
    }

    public void SetInputMethodManager(InputMethodManager imm){ this.imm = imm; };
}
