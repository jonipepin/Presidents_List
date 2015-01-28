package edu.usna.mobileos.presidentslist;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Arrays;
import java.util.List;


public class MainActivity extends ActionBarActivity implements AdapterView.OnItemClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ListView presidentsListView = (ListView) findViewById(R.id.president_list);

        List presidentsList =
                Arrays.asList(getResources().getStringArray(R.array.presidents));

        ArrayAdapter presidentsListAdapter = new ArrayAdapter(this,
                android.R.layout.simple_list_item_1, presidentsList);

        presidentsListView.setAdapter(presidentsListAdapter);

        presidentsListView.setOnItemClickListener(this);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int pos, long id) {
        String presidentClicked = ((TextView)view).getText().toString();
        Toast.makeText(getBaseContext(), presidentClicked, Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(getBaseContext(), WebviewActivity.class);
        intent.putExtra("president", presidentClicked);
        startActivity(intent);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
