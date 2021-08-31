package com.app.footprintcalculator.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.app.footprintcalculator.R;
import com.app.footprintcalculator.models.FootprintModel;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
//Compare Footprints
public class CovidPointsActivity extends BaseActivity {

    ListView listView;
    TextView textView;
    DatabaseReference databaseReference;
    List<FootprintModel> list;
    FootprintListAdapter adapter;
    public static FootprintModel model;
    public static List<FootprintModel> selectList;
    String userId;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.searchmenu , menu);
        MenuItem searchmenuItem = menu.findItem(R.id.search);
        SearchView searchView = (SearchView) searchmenuItem.getActionView();
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                try {
                    adapter.getFilter().filter(newText);
                }catch (Exception ex){
                    ex.printStackTrace();
                }
                return false;
            }
        });
        return true;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_footprint);

        showProgressDialog();
        databaseReference = FirebaseDatabase.getInstance().getReference("Footprints");
        list = new ArrayList<>();
        selectList = new ArrayList<>();
        userId = FirebaseAuth.getInstance().getCurrentUser().getUid();

        listView = findViewById(R.id.listView);
        textView = findViewById(R.id.textView);

    }

    @Override
    protected void onStart() {
        super.onStart();

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                listView.setAdapter(null);
                list.clear();
                textView.setText("");
                for(DataSnapshot snapshot1 : snapshot.getChildren()){
                    FootprintModel model = snapshot1.getValue(FootprintModel.class);
                    if(userId.equals(model.getUserId())){
                        list.add(model);
                    }
                }
                if(list.size()>0){
                    adapter = new FootprintListAdapter(CovidPointsActivity.this, list);
                    listView.setAdapter(adapter);
                }else {
                    textView.setText("No footprints calculated yet.");
                }
                hideProgressDialog();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                hideProgressDialog();
            }
        });
    }
    //Adapter class to set adpater to listView of footprints list..
    public class FootprintListAdapter extends ArrayAdapter<FootprintModel> implements Filterable {
        private Activity context;
        private List<FootprintModel> list, searchList;

        public FootprintListAdapter(Activity context , List<FootprintModel> list){
            super(context , R.layout.footprint_list_layout, list);
            this.context = context;
            this.list = list;

            searchList = new ArrayList<>();
            searchList.addAll(list);
        }

        //Select footprint comparrisson
        @Override
        public View getView(final int position, final View convertView , ViewGroup parent) {

            final LayoutInflater inflater = context.getLayoutInflater();

            View listViewItem = inflater.inflate(R.layout.footprint_list_layout, null , true);

            TextView tvFootprintName = (TextView) listViewItem.findViewById(R.id.tvFootprintName);
            TextView tvDate = (TextView) listViewItem.findViewById(R.id.tvDate);
            CheckBox cbSelect = (CheckBox) listViewItem.findViewById(R.id.cbSelect);

            cbSelect.setVisibility(View.VISIBLE);

            final FootprintModel footprint = list.get(position);
            tvFootprintName.setText("Name : "+footprint.getName());
            tvDate.setText("Date : "+footprint.getDate());

            cbSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        if(!selectList.contains(footprint)){
                            selectList.add(footprint);
                            if(selectList.size()==2){ // if they have selected 2 footprints then move to next screen

                                startActivity(new Intent(context, CompareFootprintsActivity.class));
                            }
                        }
                    }else {
                        List<FootprintModel> l = selectList;
                        for(FootprintModel u : l){
                            if(footprint.getId().equals(u.getId())){
                                selectList.remove(u);
                                break;
                            }
                        }
                    }
                }
            });

            return listViewItem;
        }

        @Override
        public Filter getFilter() {
            return Dataresult;
        }
        private Filter Dataresult = new Filter() {
            @Override
            protected FilterResults performFiltering(CharSequence constraint) {
                List<FootprintModel> FilterList = new ArrayList<>();
                if(constraint == null && constraint.length()==0){
                    FilterList.addAll(searchList);
                }else {
                    String characters = constraint.toString().toLowerCase().trim();
                    for(FootprintModel footprintModel : searchList){
                        if(footprintModel.getDate().toLowerCase().contains(characters)){
                            FilterList.add(footprintModel);
                        }
                    }
                }
                FilterResults filterResults = new FilterResults();
                filterResults.values = FilterList;
                return  filterResults;
            }

            @Override
            protected void publishResults(CharSequence constraint, FilterResults results) {
                list.clear();
                list.addAll((Collection<? extends FootprintModel>) results.values);
                notifyDataSetChanged();
            }
        };
    }
}
