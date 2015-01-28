package edu.usna.mobileos.presidentslist;

import android.app.ListActivity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;


public class MainActivity extends ListActivity {

//    final static String TAG = "PEPIN";

//	private ListView presidentsListView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

//      setContentView(R.layout.activity_main);
//      presidentsListView = (ListView) findViewById(R.id.president_list);

        List presidentsList =
                Arrays.asList(getResources().getStringArray(R.array.presidents));

//      presidentsListView.setAdapter(
//          new ArrayAdapter(this, android.R.layout.simple_list_item_1, presidentsList));
        setListAdapter(
                new ArrayAdapter(this, android.R.layout.simple_list_item_1, presidentsList));

//      presidentsListView.setOnItemClickListener(this);
    }

    @Override
    public void onListItemClick(ListView parent, View view, int pos, long id) {
        String presidentClicked = ((TextView)view).getText().toString();
        Toast.makeText(getBaseContext(), presidentClicked, Toast.LENGTH_SHORT).show();

        Intent i = new Intent(getBaseContext(), WebviewActivity.class);
        i.putExtra("president", presidentClicked);
        startActivity(i);
    }
}
