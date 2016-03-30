package technologies.andriawan.crudsqlite;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by root on 29/03/16.
 */
public class FilterAction extends AppCompatActivity {

    ArrayAdapter<String> dataAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout2);
        EditText myFilter = (EditText) findViewById(R.id.myFilter);
        displayListView();

    }

    private void displayListView(){

        final EditText myFilter = (EditText) findViewById(R.id.myFilter);
        myFilter.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {
            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                dataAdapter.getFilter().filter(charSequence.toString());

            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });



        List<String> countryList = new ArrayList<String>();

        countryList.add("Indonesia");
        countryList.add("Arab");
        countryList.add("Jepang");
        countryList.add("Belanda");
        countryList.add("Malaysia");
        countryList.add("Brunei");
        countryList.add("Afrika");
        countryList.add("India");

        dataAdapter =  new ArrayAdapter<String>(this,R.layout.country_list, countryList);
        ListView listView = (ListView) findViewById(R.id.listview1);
        listView.setAdapter(dataAdapter);
        listView.setTextFilterEnabled(true);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getApplicationContext(), ((TextView) view).getText(),
                        Toast.LENGTH_LONG).show();
            }
        });


    }
}
