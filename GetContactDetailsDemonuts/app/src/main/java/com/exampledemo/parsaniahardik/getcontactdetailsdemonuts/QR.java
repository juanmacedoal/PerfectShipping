package com.exampledemo.parsaniahardik.getcontactdetailsdemonuts;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;

import org.json.JSONException;
import org.json.JSONObject;

import Contact.ContactsPhone;
import Sql.SqlHelper;

import static com.exampledemo.parsaniahardik.getcontactdetailsdemonuts.R.id.accept;
import static com.exampledemo.parsaniahardik.getcontactdetailsdemonuts.R.id.imageView;

public class QR extends AppCompatActivity {

    private ImageView qr;
    Thread thread;
    public final static int QRcodeWidth = 500;
    Bitmap bitmap;
    String street, city, state, postalcode, email, name, phone;
    TextView tstreet, tcity, tstate, tpostalcode, tname, tphone, temail;
    SqlHelper sqlHelper;
    int set = 1;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qr);

        sqlHelper = new SqlHelper(this);

        Intent iin = getIntent();
        Bundle bundle = iin.getExtras();

        if(bundle.getString("comefrom") != null)
          if(bundle.getString("comefrom").equals("profile"))
            set = 0;

        street = bundle.getString("street");
        city = bundle.getString("city");
        state = bundle.getString("state");
        postalcode = bundle.getString("code");
        name = bundle.getString("name");
        phone = bundle.getString("phone");
        email = bundle.getString("email");



        final JSONObject contactList = new JSONObject();
        try {
            contactList.put("Street", street);
            contactList.put("City", city);
            contactList.put("State", state);
            contactList.put("PostalCode", postalcode);
            contactList.put("Name", name);
            contactList.put("Phone", phone);
            contactList.put("Email", email);
        } catch (JSONException e) {
            e.printStackTrace();
        }


        tstreet = (TextView) findViewById(R.id.Street);
        tcity = (TextView) findViewById(R.id.City);
        tstate = (TextView) findViewById(R.id.State);
        tpostalcode = (TextView) findViewById(R.id.Postalcode);
        tname = (TextView) findViewById(R.id.names);
        tphone = (TextView) findViewById(R.id.phones);
        temail = (TextView) findViewById(R.id.emails);

        tname.setText(name);
        tstreet.setText("Street: " + street);
        tcity.setText("City: " + city);
        tstate.setText("State: " + state);
        tpostalcode.setText("Postal code: " + postalcode);
        tphone.setText("Phone: " + phone);
        temail.setText("Email: " + email);


        qr = (ImageView) findViewById(imageView);

        try {
            //bitmap = TextToImageEncode(street + " " + city + " " + state + " " + postalcode);
            bitmap = TextToImageEncode(contactList);
            qr.setImageBitmap(bitmap);

        } catch (WriterException e) {
            e.printStackTrace();
        }


    }

    Bitmap TextToImageEncode(JSONObject Value) throws WriterException {
        BitMatrix bitMatrix;
        try {
            bitMatrix = new MultiFormatWriter().encode(
                    String.valueOf(Value),
                    BarcodeFormat.DATA_MATRIX.QR_CODE,
                    QRcodeWidth, QRcodeWidth, null
            );

        } catch (IllegalArgumentException Illegalargumentexception) {

            return null;
        }
        int bitMatrixWidth = bitMatrix.getWidth();

        int bitMatrixHeight = bitMatrix.getHeight();

        int[] pixels = new int[bitMatrixWidth * bitMatrixHeight];

        for (int y = 0; y < bitMatrixHeight; y++) {
            int offset = y * bitMatrixWidth;

            for (int x = 0; x < bitMatrixWidth; x++) {

                pixels[offset + x] = bitMatrix.get(x, y) ?
                        getResources().getColor(R.color.black):getResources().getColor(R.color.white);
            }
        }
        Bitmap bitmap = Bitmap.createBitmap(bitMatrixWidth, bitMatrixHeight, Bitmap.Config.ARGB_4444);

        bitmap.setPixels(pixels, 0, 500, 0, 0, bitMatrixWidth, bitMatrixHeight);
        return bitmap;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_map, menu);

        return true;
    }

    public boolean onPrepareOptionsMenu(Menu menu)
    {
        MenuItem accept = menu.findItem(R.id.accept);
        if(set == 0)
        {
            accept.setVisible(false);
        }
        else if(set == 1)
        {
            accept.setVisible(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menu) {
        Intent intent;




        switch (menu.getItemId()) {
            case R.id.accept:

                ContactsPhone.sqlContact(sqlHelper, name, phone, email, state, city, street, postalcode);
                Toast.makeText(getApplicationContext(), "Saved shipping!",
                        Toast.LENGTH_LONG).show();
                intent = new Intent(getApplication(), Profile.class);
                intent.putExtra("saved", "Saved shipping");
                startActivity(intent);


                return true;

            default:
                return super.onOptionsItemSelected(menu);
        }
    }

    @Override
    public void onBackPressed() {
        // Disable going back to the MainActivity
        moveTaskToBack(true);
    }

}





