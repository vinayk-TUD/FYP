package com.app.footprintcalculator.activities;

import android.app.Activity;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
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
import com.app.footprintcalculator.models.CountriesModel;
import com.app.footprintcalculator.models.FoodModel;
import com.app.footprintcalculator.models.FootprintModel;
import com.app.footprintcalculator.models.HomeModel;
import com.app.footprintcalculator.models.TransportModel;
import com.github.mikephil.charting.data.BarEntry;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.jsoup.Jsoup;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ComparisonWithOtherCountriesActivity extends BaseActivity {

    ListView listView;
    TextView textView;
    Button btnDone;
    DatabaseReference databaseReference;
    List<CountriesModel> list;
    FootprintListAdapter adapter;
    public static List<CountriesModel> selectList;


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
        setContentView(R.layout.activity_comparison_graph);

        databaseReference = FirebaseDatabase.getInstance().getReference("Footprints");
        list = new ArrayList<>();
        selectList = new ArrayList<>();

        listView = findViewById(R.id.listView);
        textView = findViewById(R.id.textView);
        btnDone = findViewById(R.id.btnDone);

        btnDone.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(selectList.size()<1){
                    Toast.makeText(ComparisonWithOtherCountriesActivity.this, "Please select atleast 1 country", Toast.LENGTH_SHORT).show();
                    return;
                }
                startActivity(new Intent(getApplicationContext(), ShowGraphCountriesActivity.class));
            }
        });

    }




    //public void wikiParse() throws IOException{

//        String homepage = "https://en.wikipedia.org/wiki/List_of_countries_by_carbon_dioxide_emissions_per_capita";
//
//        org.jsoup.nodes.Document doc = Jsoup.connect(homepage).get();
//
//        Elements tables = doc.getElementsByTag("table");
//
//        for (org.jsoup.nodes.Element t : tables){
//
//            if(t.className().equals("wikitable sortable mw-collapsible")){
//                System.out.println(t.className());
//
//                // Rows
//                Elements rows = t.getElementsByTag("tr");
//
//                for (org.jsoup.nodes.Element row : rows){
//
//                    //Columns
//                    Elements cols = row.getElementsByTag("td");
//
//                    if(!cols.isEmpty()){
//                        System.out.println(cols.first().text()); // Country
//                        System.out.println(cols.get(1).text()); // Co2
//
//                        String country = cols.first().text();
//                        String co2 = cols.get(1).text();
//                        if(co2.equals("-")){
//                            co2 = "0" ;
//                        }//
//
//                        list.add(new CountriesModel(country, Double.parseDouble(co2)));
//
//                    }//end if
//
//                    //list.add(new CountriesModel(country, Double.parseDouble(co2)));
//
//
//
//                }//end for
//
//            }//end if
//
//        }//End for


    //}

    @Override
    protected void onStart() {
        super.onStart();
        Wikiparse w = new Wikiparse();
        w.execute();

//        list.add(new CountriesModel("Europe ",11));
//        list.add(new CountriesModel("US",16));
//        list.add(new CountriesModel("UK",12.7));
//        list.add(new CountriesModel("Ireland",13.3));
//        list.add(new CountriesModel("Germany",9.72));
//        list.add(new CountriesModel("Netherlands",10.37));
//        list.add(new CountriesModel("France",6.32));
//        list.add(new CountriesModel("Canada ",19.56));
//        list.add(new CountriesModel("Australia ",24.63));
//        list.add(new CountriesModel("United Arab Emirates ",27.33));
//        list.add(new CountriesModel("Czech Republic  ",11.56));
//        list.add(new CountriesModel("Poland  ",10.26));
//        list.add(new CountriesModel("Denmark  ",7.92));
//        list.add(new CountriesModel("Italy  ",6.61));
//        list.add(new CountriesModel("Singapore  ",11.82));
//        list.add(new CountriesModel("Saudi Arabia  ",18.94));
//        list.add(new CountriesModel("Belgium   ",9.52));
//        list.add(new CountriesModel("Norway",8.91));

//        String homepage = "https://en.wikipedia.org/wiki/List_of_countries_by_carbon_dioxide_emissions_per_capita";
//
//        org.jsoup.nodes.Document doc = null;
//        try {
//            doc = Jsoup.connect(homepage).get();
//        } catch (IOException e) {
//            e.printStackTrace();
//        }
//
//        Elements tables = doc.getElementsByTag("table");
//
//        for (org.jsoup.nodes.Element t : tables){
//
//            if(t.className().equals("wikitable sortable mw-collapsible")){
//                System.out.println(t.className());
//
//                // Rows
//                Elements rows = t.getElementsByTag("tr");
//
//                for (org.jsoup.nodes.Element row : rows){
//
//                    //Columns
//                    Elements cols = row.getElementsByTag("td");
//
//                    if(!cols.isEmpty()){
//                        System.out.println(cols.first().text()); // Country
//                        System.out.println(cols.get(1).text()); // Co2
//
//                        String country = cols.first().text();
//                        String co2 = cols.get(1).text();
//                        if(co2.equals("-")){
//                            co2 = "0" ;
//                        }//
//
//                        list.add(new CountriesModel(country, Double.parseDouble(co2)));
//
//                    }//end if
//
//                    //list.add(new CountriesModel(country, Double.parseDouble(co2)));
//
//
//
//                }//end for
//
//            }//end if
//
//        }//End for

//        FootprintListAdapter adapter = new FootprintListAdapter(ComparisonWithOtherCountriesActivity.this, list);
//        listView.setAdapter(adapter);


//        try {
//            wikiParse();
//        } catch (IOException e) {
//            e.printStackTrace();
//            Toast.makeText(ComparisonWithOtherCountriesActivity.this, "Jspup not working", Toast.LENGTH_SHORT).show();
//        }

    }
    //Adapter class to set adpater to listView of footprints list..
    public class FootprintListAdapter extends ArrayAdapter<CountriesModel> implements Filterable {
        private Activity context;
        private List<CountriesModel> list, searchList;

        public FootprintListAdapter(Activity context , List<CountriesModel> list){
            super(context , R.layout.footprint_list_layout, list);
            this.context = context;
            this.list = list;

            searchList = new ArrayList<>();
            searchList.addAll(list);
        }

        @Override
        public View getView(final int position, final View convertView , ViewGroup parent) {

            final LayoutInflater inflater = context.getLayoutInflater();

            View listViewItem = inflater.inflate(R.layout.footprint_list_layout, null , true);

            TextView tvFootprintName = (TextView) listViewItem.findViewById(R.id.tvFootprintName);
            TextView tvDate = (TextView) listViewItem.findViewById(R.id.tvDate);
            CheckBox cbSelect = (CheckBox) listViewItem.findViewById(R.id.cbSelect);

            cbSelect.setVisibility(View.VISIBLE);

            final CountriesModel countries = list.get(position);
            tvFootprintName.setText(countries.getName());
            tvDate.setText("Emission : "+countries.getValue() + "g CO₂/kWh");

            cbSelect.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                @Override
                public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                    if(isChecked){
                        if(!selectList.contains(countries)){
                            selectList.add(countries);
                            if(selectList.size()==10){
                               startActivity(new Intent(getApplicationContext(), ShowGraphCountriesActivity.class));
                            }
                        }
                    }else {
                        List<CountriesModel> l = selectList;
                        for(CountriesModel u : l){
                            if(countries.getName().equals(u.getName())){
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
                List<CountriesModel> FilterList = new ArrayList<>();
                if(constraint == null && constraint.length()==0){
                    FilterList.addAll(searchList);
                }else {
                    String characters = constraint.toString().toLowerCase().trim();
                    for(CountriesModel footprintModel : searchList){
                        if(footprintModel.getName().toLowerCase().contains(characters)){
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
                list.addAll((Collection<? extends CountriesModel>) results.values);
                notifyDataSetChanged();
            }
        };
    }
    public class Wikiparse extends AsyncTask<Void,Void,Void>{

        @Override
        protected Void doInBackground(Void... voids) {

            String homepage = "https://en.wikipedia.org/wiki/List_of_countries_by_carbon_dioxide_emissions_per_capita";
            CountriesModel countryModel;

            try {
                org.jsoup.nodes.Document doc = Jsoup.connect(homepage).get();

                Elements tables = doc.getElementsByTag("table");
//
        for (org.jsoup.nodes.Element t : tables){

            if(t.className().equals("wikitable sortable mw-collapsible")){
                System.out.println(t.className());

                // Rows
                Elements rows = t.getElementsByTag("tr");

                for (org.jsoup.nodes.Element row : rows){

                    //Columns
                    Elements cols = row.getElementsByTag("td");

                    if(!cols.isEmpty()){
                        System.out.println(cols.first().text()); // Country
                        System.out.println(cols.get(1).text()); // Co2

                        String country = cols.first().text();
                        String co2 = cols.get(1).text();
                        if(co2.equals("-")){
                            co2 = "0" ;
                        }//

                        list.add(new CountriesModel(country, Double.parseDouble(co2)));

                    }//end if
                    //list.add(new CountriesModel(country, Double.parseDouble(co2)));

                }//end for

            }//end if

        }//End for
            } catch (IOException e) {
                e.printStackTrace();
            }
            return null;
        }//end do in bkg

        @Override
        protected void onPostExecute(Void aVoid) {
            super.onPostExecute(aVoid);
            FootprintListAdapter adapter = new FootprintListAdapter(ComparisonWithOtherCountriesActivity.this, list);
            listView.setAdapter(adapter);

        }
    }

}
