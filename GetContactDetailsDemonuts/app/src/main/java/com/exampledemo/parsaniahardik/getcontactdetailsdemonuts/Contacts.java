package com.exampledemo.parsaniahardik.getcontactdetailsdemonuts;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Color;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.maps.GoogleMap;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import Contact.ContactsPhone;
import Sql.SqlHelper;


public class Contacts extends AppCompatActivity {

    private FloatingActionButton btnContacts, btnMap, btnQR;
    private ListView mListView;
    private GoogleMap mMap;
    Menu Mymenu;

    private String[] ids;

    private String[] email;
    /**
     * Array de Strings para el manejo de los nombres de los logros
     */
    private String[] names;
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

    private AutoCompleteTextView aname;
    private EditText aphone, amail, apostal, acity, adoor, astate, acode;
    private TextView tvname, tvphone, tvmail, tvpostal, t2, t3, t4, t5, t6, t7, t8;
    private ArrayAdapter<String> adapter;
    private SqlHelper sqlHelper;
    private MenuItem accept;
    int set = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_INDETERMINATE_PROGRESS);
        setContentView(R.layout.show_contacts);

        sqlHelper = new SqlHelper(this);

        set = 0;

        try {
            sqlHelper.getData();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        btnMap = (FloatingActionButton) findViewById(R.id.map);
        aname = (AutoCompleteTextView) findViewById(R.id.at_name);
        aphone = (EditText) findViewById(R.id.at_phone);
        amail = (EditText) findViewById(R.id.at_mail);
        apostal = (EditText) findViewById(R.id.at_address);
        acity = (EditText) findViewById(R.id.at_city);
        astate = (EditText) findViewById(R.id.at_state);
        acode = (EditText) findViewById(R.id.at_postalcode);

        t2 = (TextView) findViewById(R.id.t2);
        t3 = (TextView) findViewById(R.id.t3);
        t4 = (TextView) findViewById(R.id.t4);
        t6 = (TextView) findViewById(R.id.t6);
        t7 = (TextView) findViewById(R.id.t7);
        t8 = (TextView) findViewById(R.id.t8);

        new Contac().execute();


        aname.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Log.e("Nombre: ", String.valueOf(aname.getText()));
                JSONObject contactlist = null;
                try {
                    contactlist = ContactsPhone.id_return(getContentResolver(), String.valueOf(aname.getText()));
                    aname.setText(contactlist.getString("name"));
                    aphone.setText(contactlist.getString("phone"));
                    amail.setText(contactlist.getString("email"));
                    apostal.setText(contactlist.getString("address"));
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                aphone.setVisibility(1);
                amail.setVisibility(1);
                apostal.setVisibility(1);
                astate.setVisibility(1);
                acity.setVisibility(1);
                acode.setVisibility(1);

                t2.setVisibility(1);
                t3.setVisibility(1);
                t4.setVisibility(1);
                t6.setVisibility(1);
                t7.setVisibility(1);
                t8.setVisibility(1);
                btnMap.setVisibility(1);

                Mymenu.findItem(R.id.accept).setVisible(true);


            }
        });


        aname.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button

                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press


                    //SAVE THE CONTACT IN THE DB
                    if (!String.valueOf(aname.getText()).equals("") && !String.valueOf(amail.getText()).equals("") && !String.valueOf(aphone.getText()).equals("")
                            && !String.valueOf(apostal.getText()).equals("") && !String.valueOf(acity.getText()).equals("") && !String.valueOf(astate.getText()).equals("")
                            && !String.valueOf(acode.getText()).equals("")) {
                        //ContactsPhone.sqlContact(sqlHelper, aname, aphone, amail, astate, acity, apostal, acode);

                        boolean success = ContactsPhone.insertContact(getContentResolver(), String.valueOf(aname.getText())
                                , String.valueOf(aphone.getText()), String.valueOf(amail.getText()), String.valueOf(apostal.getText()));

                        if (success)
                            Toast.makeText(getApplicationContext(), "Contact modify!",
                                    Toast.LENGTH_LONG).show();
                    }


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

                    //SAVE THE CONTACT IN THE DB
                    if (!String.valueOf(aname.getText()).equals("") && !String.valueOf(amail.getText()).equals("") && !String.valueOf(aphone.getText()).equals("")
                            && !String.valueOf(apostal.getText()).equals("") && !String.valueOf(acity.getText()).equals("") && !String.valueOf(astate.getText()).equals("")
                            && !String.valueOf(acode.getText()).equals("")) {
                        //ContactsPhone.sqlContact(sqlHelper, aname, aphone, amail, astate, acity, apostal, acode);

                        boolean success = ContactsPhone.insertContact(getContentResolver(), String.valueOf(aname.getText())
                                , String.valueOf(aphone.getText()), String.valueOf(amail.getText()), String.valueOf(apostal.getText()));

                        if (success)
                            Toast.makeText(getApplicationContext(), "Contact modify!",
                                    Toast.LENGTH_LONG).show();
                    }
                    return true;
                }
                return false;
            }
        });


        amail.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press

                    //SAVE THE CONTACT IN THE DB
                    if (!String.valueOf(aname.getText()).equals("") && !String.valueOf(amail.getText()).equals("") && !String.valueOf(aphone.getText()).equals("")
                            && !String.valueOf(apostal.getText()).equals("") && !String.valueOf(acity.getText()).equals("") && !String.valueOf(astate.getText()).equals("")
                            && !String.valueOf(acode.getText()).equals("")) {
                        //ContactsPhone.sqlContact(sqlHelper, aname, aphone, amail, astate, acity, apostal, acode);

                        boolean success = ContactsPhone.insertContact(getContentResolver(), String.valueOf(aname.getText())
                                , String.valueOf(aphone.getText()), String.valueOf(amail.getText()), String.valueOf(apostal.getText()));

                        if (success)
                            Toast.makeText(getApplicationContext(), "Contact modify!",
                                    Toast.LENGTH_LONG).show();
                    }
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


                    //SAVE THE CONTACT IN THE DB
                    if (!String.valueOf(aname.getText()).equals("") && !String.valueOf(amail.getText()).equals("") && !String.valueOf(aphone.getText()).equals("")
                            && !String.valueOf(apostal.getText()).equals("") && !String.valueOf(acity.getText()).equals("") && !String.valueOf(astate.getText()).equals("")
                            && !String.valueOf(acode.getText()).equals("")) {
                        //ContactsPhone.sqlContact(sqlHelper, aname, aphone, amail, astate, acity, apostal, acode);

                        boolean success = ContactsPhone.insertContact(getContentResolver(), String.valueOf(aname.getText())
                                , String.valueOf(aphone.getText()), String.valueOf(amail.getText()), String.valueOf(apostal.getText()));

                        if (success)
                            Toast.makeText(getApplicationContext(), "Contact modify!",
                                    Toast.LENGTH_LONG).show();
                    }
                    return true;
                }
                return false;
            }
        });

        acity.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    boolean success = true;

                    //SAVE THE CONTACT IN THE DB
                    if (!String.valueOf(aname.getText()).equals("") && !String.valueOf(amail.getText()).equals("") && !String.valueOf(aphone.getText()).equals("")
                            && !String.valueOf(apostal.getText()).equals("") && !String.valueOf(acity.getText()).equals("") && !String.valueOf(astate.getText()).equals("")
                            && !String.valueOf(acode.getText()).equals(""))
                        //ContactsPhone.sqlContact(sqlHelper, aname, aphone, amail, astate, acity, apostal, acode);

                        if (success)
                            Toast.makeText(getApplicationContext(), "Contact modify!",
                                    Toast.LENGTH_LONG).show();
                    return true;
                }
                return false;
            }
        });


        astate.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    boolean success = true;

                    //SAVE THE CONTACT IN THE DB
                    if (!String.valueOf(aname.getText()).equals("") && !String.valueOf(amail.getText()).equals("") && !String.valueOf(aphone.getText()).equals("")
                            && !String.valueOf(apostal.getText()).equals("") && !String.valueOf(acity.getText()).equals("") && !String.valueOf(astate.getText()).equals("")
                            && !String.valueOf(acode.getText()).equals(""))
                        //ContactsPhone.sqlContact(sqlHelper, aname, aphone, amail, astate, acity, apostal, acode);

                        if (success)
                            Toast.makeText(getApplicationContext(), "Contact modify!",
                                    Toast.LENGTH_LONG).show();
                    return true;
                }
                return false;
            }
        });

        acode.setOnKeyListener(new View.OnKeyListener() {
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                // If the event is a key-down event on the "enter" button
                if ((event.getAction() == KeyEvent.ACTION_DOWN) &&
                        (keyCode == KeyEvent.KEYCODE_ENTER)) {
                    // Perform action on key press
                    boolean success = true;

                    //SAVE THE CONTACT IN THE DB
                    if (!String.valueOf(aname.getText()).equals("") && !String.valueOf(amail.getText()).equals("") && !String.valueOf(aphone.getText()).equals("")
                            && !String.valueOf(apostal.getText()).equals("") && !String.valueOf(acity.getText()).equals("") && !String.valueOf(astate.getText()).equals("")
                            && !String.valueOf(acode.getText()).equals(""))
                        //ContactsPhone.sqlContact(sqlHelper, aname, aphone, amail, astate, acity, apostal, acode);

                        if (success)
                            Toast.makeText(getApplicationContext(), "Contact modify!",
                                    Toast.LENGTH_LONG).show();
                    return true;
                }
                return false;
            }
        });

        if (!String.valueOf(aname.getText()).equals("") && !String.valueOf(amail.getText()).equals("") && !String.valueOf(aphone.getText()).equals("")
                && !String.valueOf(apostal.getText()).equals("") && !String.valueOf(acity.getText()).equals("") && !String.valueOf(astate.getText()).equals("")
                && !String.valueOf(acode.getText()).equals("")) {
            btnQR.setVisibility(1);

        }


        btnMap.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), MapsActivity.class);
                intent.putExtra("address", String.valueOf(astate.getText() + " " + acity.getText() + " " + apostal.getText()) + " " + acode.getText());
                startActivityForResult(intent, 1);
            }
        });


    }


    /**
     *
     */
    public class Contac extends AsyncTask<Void, Void, Void> {

        private ProgressDialog dialog = new ProgressDialog(Contacts.this);

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            setProgressBarIndeterminateVisibility(true);
            dialog = ProgressDialog.show(Contacts.this, "Loading", "", false);

        }

        protected Void doInBackground(Void... params) {

            try {
                contactList = ContactsPhone.getContacts(getContentResolver());
                names = new String[contactList.length()];
                JSONObject jsonObject = new JSONObject();
                for (int i = 0; i < contactList.length(); i++) {
                    jsonObject = contactList.getJSONObject(i);

                    names[i] = jsonObject.getString("name");
                    Log.e("NAMES", names.toString());
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            setProgressBarIndeterminateVisibility(true);
            if (dialog.isShowing()) {
                dialog.dismiss();
            }

            // If array list is not null and is contains value
            if (names != null && names.length > 0) {

                // then set total contacts to subtitle
                getSupportActionBar().setSubtitle(
                        names.length + " ContactsPhone");
                ArrayAdapter<String> adapter = null;
                if (adapter == null) {
                    Log.e("NAMES", names.toString());
                    adapter = new ArrayAdapter<String>(getApplicationContext(), android.R.layout.select_dialog_item, names) {
                        @Override
                        public View getView(int position, View convertView, ViewGroup parent) {
                            TextView textView = (TextView) super.getView(position, convertView, parent);
                            // Set the color here
                            textView.setTextColor(Color.parseColor("#000000"));
                            return textView;
                        }
                    };
                    aname.setThreshold(1);
                    aname.setAdapter(adapter);
                }
                adapter.notifyDataSetChanged();
            } else {

                // If adapter is null then show toast
                Toast.makeText(getApplication(), "There are no contacts.",
                        Toast.LENGTH_LONG).show();
            }


        }
    }


    public boolean onPrepareOptionsMenu(Menu menu) {
        MenuItem accept = menu.findItem(R.id.accept);
        if (set == 0) {
            accept.setVisible(false);
        } else if (set == 1) {
            accept.setVisible(true);
        }
        return true;
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == Activity.RESULT_OK) {
            Uri contactData = data.getData();
            Cursor c = getContentResolver().query(contactData, null, null, null, null);
            if (c.moveToFirst()) {

                String phoneNumber = "", emailAddress = "", postalAddress = "";
                String name = c.getString(c.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                String contactId = c.getString(c.getColumnIndex(ContactsContract.Contacts._ID));
                contactid = contactId;


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


                aphone.setVisibility(1);
                amail.setVisibility(1);
                apostal.setVisibility(1);
                astate.setVisibility(1);
                acity.setVisibility(1);
                acode.setVisibility(1);

                t2.setVisibility(1);
                t3.setVisibility(1);
                t4.setVisibility(1);
                t6.setVisibility(1);
                t7.setVisibility(1);
                t8.setVisibility(1);
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
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu, menu);
        menu.getItem(1).setVisible(false);
        Mymenu = menu;
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menu) {
        Intent intent;

        switch (menu.getItemId()) {
            case R.id.accept:
                if (!String.valueOf(aname.getText()).equals("") && !String.valueOf(amail.getText()).equals("") && !String.valueOf(aphone.getText()).equals("")
                        && !String.valueOf(apostal.getText()).equals("") && !String.valueOf(acity.getText()).equals("") && !String.valueOf(astate.getText()).equals("")
                        && !String.valueOf(acode.getText()).equals(""))


                if (String.valueOf(aname.getText()).equals("")) {

                    aname.setError("Name can't be empty!");

                } else if (String.valueOf(amail.getText()).equals("")) {

                    amail.setError("Email can't be empty!");

                } else if (!amail.getText().toString().matches("[a-zA-Z0-9._-]+@[a-z]+.[a-z]+")) {

                    amail.setError("Invalid Email Address");

                } else if (String.valueOf(aphone.getText()).equals("")) {

                    aphone.setError("Phone can't be empty!");

                } else if (String.valueOf(apostal.getText()).equals("")) {

                    apostal.setError("Street can't be empty!");

                } else if (String.valueOf(acity.getText()).equals("")) {

                    acity.setError("City can't be empty!");

                } else if (String.valueOf(astate.getText()).equals("")) {

                    astate.setError("State can't be empty!");

                } else if (String.valueOf(acode.getText()).equals("")) {
                    acode.setText("Code can't be empty!");

                } else {
                    intent = new Intent(getApplication(), MapsActivity.class);
                    intent.putExtra("street", String.valueOf(apostal.getText()));
                    intent.putExtra("city", String.valueOf(acity.getText()));
                    intent.putExtra("state", String.valueOf(astate.getText()));
                    intent.putExtra("code", String.valueOf(acode.getText()));
                    intent.putExtra("name", String.valueOf(aname.getText()));
                    intent.putExtra("phone", String.valueOf(aphone.getText()));
                    intent.putExtra("email", String.valueOf(amail.getText()));
                    intent.putExtra("address", String.valueOf(astate.getText() + " " + acity.getText() + " " + apostal.getText()) + " " + acode.getText());
                    startActivityForResult(intent, 1);
                }

                return true;
            case R.id.contacts:
                intent = new Intent(Intent.ACTION_PICK, ContactsContract.Contacts.CONTENT_URI);
                startActivityForResult(intent, 1);
                return true;
            default:
                return super.onOptionsItemSelected(menu);
        }
    }

    private boolean isValidEmail(String email) {
        String EMAIL_PATTERN = "^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
                + "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$";

        Pattern pattern = Pattern.compile(EMAIL_PATTERN);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

        Intent intent = new Intent(getApplication(), Profile.class);
    }


}
