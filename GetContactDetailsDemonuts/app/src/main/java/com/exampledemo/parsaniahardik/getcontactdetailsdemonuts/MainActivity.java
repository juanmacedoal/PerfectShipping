package com.exampledemo.parsaniahardik.getcontactdetailsdemonuts;

import android.app.Activity;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.common.api.Status;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlaceAutocompleteFragment;
import com.google.android.gms.location.places.ui.PlaceSelectionListener;
import com.google.android.gms.maps.CameraUpdateFactory;
import com.google.android.gms.maps.GoogleMap;
import com.google.android.gms.maps.OnMapReadyCallback;
import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.MarkerOptions;

import org.json.JSONArray;

public class MainActivity extends AppCompatActivity implements OnMapReadyCallback {

    private FloatingActionButton btnContacts, btnMap;
    private ListView mListView;
    private GoogleMap mMap;

    private String[] ids;

    private String[] email;
    /**
     * Array de Strings para el manejo de los nombres de los logros
     */
    private String[] name;
    /**
     * Array de Strings para el manejo de la descripcion de los logros
     */
    private String[] address;
    /**
     * Array de Strings para el manejo de los puntos de los logros
     */
    private String[] phone;
    String contactid;


    JSONArray contactList;

    private EditText aname, aphone, amail, apostal;
    private TextView tvname, tvphone, tvmail, tvpostal, t2, t3, t4;
    private ArrayAdapter<String> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.show_contacts);


        btnContacts = (FloatingActionButton) findViewById(R.id.fab);
        btnMap = (FloatingActionButton) findViewById(R.id.map);


        aname = (EditText) findViewById(R.id.at_name);
        aphone = (EditText) findViewById(R.id.at_phone);
        amail = (EditText) findViewById(R.id.at_mail);
        apostal = (EditText) findViewById(R.id.at_address);

        t2 = (TextView) findViewById(R.id.t2);
        t3 = (TextView) findViewById(R.id.t3);
        t4 = (TextView) findViewById(R.id.t4);


        aname.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    boolean success = Contact.Contacts.insertContact(getContentResolver(), String.valueOf(aname.getText())
                            , String.valueOf(aphone.getText()), String.valueOf(amail.getText()), String.valueOf(apostal.getText()));
                    if (success)
                        Toast.makeText(getApplicationContext(), "Contact modify!",
                                Toast.LENGTH_LONG).show();
                    return true;
                }
                return false;
            }
        });

        apostal.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    boolean success = Contact.Contacts.insertContact(getContentResolver(), String.valueOf(aname.getText())
                            , String.valueOf(aphone.getText()), String.valueOf(amail.getText()), String.valueOf(apostal.getText()));
                    if (success)
                        Toast.makeText(getApplicationContext(), "Contact modify!",
                                Toast.LENGTH_LONG).show();
                    return true;
                }
                return false;
            }
        });

        apostal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

            }
        });

        amail.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    boolean success = Contact.Contacts.insertContact(getContentResolver(), String.valueOf(aname.getText())
                            , String.valueOf(aphone.getText()), String.valueOf(amail.getText()), String.valueOf(apostal.getText()));
                    if (success)
                        Toast.makeText(getApplicationContext(), "Contact modify!",
                                Toast.LENGTH_LONG).show();
                    return true;
                }
                return false;
            }
        });

        aphone.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    boolean success = Contact.Contacts.insertContact(getContentResolver(), String.valueOf(aname.getText())
                            , String.valueOf(aphone.getText()), String.valueOf(amail.getText()), String.valueOf(apostal.getText()));
                    if (success)
                        Toast.makeText(getApplicationContext(), "Contact modify!",
                                Toast.LENGTH_LONG).show();
                    return true;
                }
                return false;
            }
        });


        btnContacts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent, 1);
            }
        });
        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), MapsActivity.class);
                intent.putExtra("address", String.valueOf(apostal.getText()));
                startActivityForResult(intent, 1);
            }
        });

     /*   PlaceAutocompleteFragment places= (PlaceAutocompleteFragment)
                getFragmentManager().findFragmentById(R.id.place_autocomplete_fragment);
        places.setOnPlaceSelectedListener(new PlaceSelectionListener() {
            @Override
            public void onPlaceSelected(Place place) {

                Toast.makeText(getApplicationContext(),place.getName(),Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onError(Status status) {

                Toast.makeText(getApplicationContext(),status.toString(),Toast.LENGTH_SHORT).show();

            }
        });*/



    }




        @Override
        public void onActivityResult ( int requestCode, int resultCode, Intent data){
            if (resultCode == Activity.RESULT_OK) {
                Uri contactData = data.getData();
                Cursor c = getContentResolver().query(contactData, null, null, null, null);
                if (c.moveToFirst()) {

                    String phoneNumber = "", emailAddress = "", postalAddress = "";
                    String name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                    String contactId = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));
                    contactid = contactId;
                    //http://stackoverflow.com/questions/866769/how-to-call-android-contacts-list   our upvoted answer

                    String hasPhone = c.getString(c.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER));

                    if (hasPhone.equalsIgnoreCase("1"))
                        hasPhone = "true";
                    else
                        hasPhone = "false";

                    if (Boolean.parseBoolean(hasPhone)) {
                        Cursor phones = getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, ContactsContract.CommonDataKinds.Phone.CONTACT_ID + " = " + contactId, null, null);
                        while (phones.moveToNext()) {
                            phoneNumber = phones.getString(phones.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        }
                        phones.close();
                    }

                    // Find Email Addresses
                    Cursor emails = getContentResolver().query(ContactsContract.CommonDataKinds.Email.CONTENT_URI, null, ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = " + contactId, null, null);
                    while (emails.moveToNext()) {
                        emailAddress = emails.getString(emails.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                    }
                    emails.close();

                    //Find Postal
                    Cursor postal = getContentResolver().query(ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_URI, null, ContactsContract.CommonDataKinds.StructuredPostal.CONTACT_ID + " = " + contactId, null, null);
                    while (postal.moveToNext()) {
                        postalAddress = postal.getString(postal.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.DATA));
                    }
                    postal.close();

                    //mainActivity.onBackPressed();
                    // Toast.makeText(mainactivity, "go go go", Toast.LENGTH_SHORT).show();
                    aphone.setVisibility(1);
                    amail.setVisibility(1);
                    apostal.setVisibility(1);
                    t2.setVisibility(1);
                    t3.setVisibility(1);
                    t4.setVisibility(1);
                    btnMap.setVisibility(1);


                    aname.setText(name);
                    aphone.setText(phoneNumber);
                    amail.setText(emailAddress);
                    apostal.setText(postalAddress);
                    Log.d("curs", name + " num" + phoneNumber + " " + "mail" + emailAddress);
                }
                c.close();
            }
        }

        @Override
        public void onMapReady (GoogleMap googleMap){
            mMap = googleMap;
            LatLng sydney = new LatLng(-34, 151);
            mMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
            mMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
        }
    }
