package com.exampledemo.parsaniahardik.getcontactdetailsdemonuts;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.util.Log;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
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
    TextView center;
    /**
     * The {@link ViewPager} that will host the section contents.
     */
    private ViewPager mViewPager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        center = (TextView) findViewById(R.id.center_text);

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

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        } catch (JSONException e) {
            e.printStackTrace();
        }


        if(contactsDataArrayList.size() == 0) {
            center.setVisibility(1);
            Log.e("TEXTVIEW", "centro" + contactsDataArrayList.toString());
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
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {

                final View view1 = view;
                final ProgressDialog progressDialog = new ProgressDialog(Profile.this,
                        R.style.AppTheme_Dark_Dialog);
                progressDialog.setIndeterminate(true);
                progressDialog.setMessage("Creating QR...");
                progressDialog.show();


                new android.os.Handler().postDelayed(
                        new Runnable() {
                            public void run() {

                                finish();
                                TextView tname = (TextView)view1.findViewById(R.id.name);
                                TextView tphone = (TextView)view1.findViewById(R.id.phone);
                                TextView temail = (TextView)view1.findViewById(R.id.email);
                                TextView tstate = (TextView)view1.findViewById(R.id.state);
                                TextView tcity = (TextView)view1.findViewById(R.id.city);
                                TextView tstreet = (TextView)view1.findViewById(R.id.Street);
                                TextView tcode = (TextView)view1.findViewById(R.id.code);

                                String name = tname.getText().toString();
                                String phone= tphone.getText().toString();
                                String email = temail.getText().toString();
                                String state = tstate.getText().toString();
                                String city = tcity.getText().toString();
                                String street = tstreet.getText().toString();
                                String code = tcode.getText().toString();

                                Intent intent = new Intent(getApplication(), QR.class);
                                intent.putExtra("street", street);
                                intent.putExtra("city", city);
                                intent.putExtra("state", state);
                                intent.putExtra("code", code);
                                intent.putExtra("name", name);
                                intent.putExtra("phone", phone);
                                intent.putExtra("email", email);
                                intent.putExtra("comefrom", "profile");
                                startActivityForResult(intent, 1);
                                progressDialog.dismiss();
                            }
                        }, 3000);


            }
        });
        listView.setOnMenuItemClickListener(new SwipeMenuListView.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(int position, SwipeMenu menu, int index) {
                switch(index){
                    case 0:
                        sqlHelper.delete_contact(contactsDataArrayList.get(position).getPhone(),
                                contactsDataArrayList.get(position).getName(), contactsDataArrayList.get(position).getEmail());
                        contactsDataArrayList.remove(position);
                        adapter.notifyDataSetChanged();
//                        Log.d("ITEM", contactsDataArrayList.get(position).getPhone());

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
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
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
            System.exit(0);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }


}
