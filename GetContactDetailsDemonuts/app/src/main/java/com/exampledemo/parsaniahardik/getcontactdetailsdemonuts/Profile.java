package com.exampledemo.parsaniahardik.getcontactdetailsdemonuts;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Toast;

import com.baoyz.swipemenulistview.SwipeMenu;
import com.baoyz.swipemenulistview.SwipeMenuCreator;
import com.baoyz.swipemenulistview.SwipeMenuItem;
import com.baoyz.swipemenulistview.SwipeMenuListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import Contact.ContactsData;
import CustomAdapter.CustomAdapter;
import Sql.SqlHelper;

public class Profile extends AppCompatActivity {

    private SqlHelper sqlHelper = new SqlHelper(this);
    ArrayList<ContactsData> contactsDataArrayList;
    CustomAdapter adapter;
    FloatingActionButton add;
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        JSONArray contactList = null;
        try {
           contactList = sqlHelper.getData();
            contactsDataArrayList = new ArrayList<>();

            JSONObject jsonObject = new JSONObject();
            for (int i = 0; i < contactList.length(); i++) {
                try {
                    jsonObject = contactList.getJSONObject(i);
                    contactsDataArrayList.add(new ContactsData(jsonObject.getString("name"), jsonObject.getString("phone"), jsonObject.getString("email"),
                            jsonObject.getString("state"), jsonObject.getString("city"), jsonObject.getString("street"), jsonObject.getString("code")));
                    Log.e("NAMES", contactsDataArrayList.toString());
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }



        adapter= new CustomAdapter(contactsDataArrayList,getApplicationContext());



        final SwipeMenuListView listView = (SwipeMenuListView) findViewById(R.id.simpleListView);
        listView.setAdapter(adapter);
        SwipeMenuCreator creator = new SwipeMenuCreator() {

            @Override
            public void create(SwipeMenu menu) {

                // create "delete" item
                SwipeMenuItem deleteItem = new SwipeMenuItem(
                        getApplicationContext());
                // set item background
                deleteItem.setBackground(new ColorDrawable(Color.rgb(0xF9,
                        0x3F, 0x25)));
                // set item width
                deleteItem.setWidth(170);
                // set a icon
                deleteItem.setIcon(R.drawable.ic_delete);
                // add to menu
                menu.addMenuItem(deleteItem);
            }
        };

        listView.setMenuCreator(creator);

        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch(index){
                    case 0:
                        contactsDataArrayList.remove(position);
                        adapter.notifyDataSetChanged();
                        Log.d("ITEM", contactsDataArrayList.get(position).getPhone());
                        sqlHelper.delete_contact(contactsDataArrayList.get(position).getPhone(),
                                contactsDataArrayList.get(position).getName(), contactsDataArrayList.get(position).getEmail());
                    break;

                }
                return false;
            }
        });


        add = (FloatingActionButton) findViewById(R.id.add);
        add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplication(), Contacts.class);
                startActivity(intent);
            }
        });




    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_profile, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();


        if (id == R.id.config) {
            return true;
        }
        if (id == R.id.logout) {
            finish();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
