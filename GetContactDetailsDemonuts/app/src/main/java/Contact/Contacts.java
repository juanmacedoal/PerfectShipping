package Contact;

import android.content.ContentProviderOperation;
import android.content.ContentResolver;
import android.content.Context;
import android.content.OperationApplicationException;
import android.database.Cursor;
import android.net.Uri;
import android.os.RemoteException;
import android.provider.ContactsContract;
import android.text.Editable;
import android.util.Log;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by juanmacedo on 15/8/17.
 */

public class Contacts {




    public static JSONArray readContacts(ContentResolver contactHelper) throws JSONException {
        JSONObject contactList = new JSONObject();
        JSONArray contacts = new JSONArray();
        ContentResolver cr = contactHelper;
        Cursor cur = cr.query(ContactsContract.Contacts.CONTENT_URI,
                null, null, null, null);
        int i = 0;
        if (cur.getCount() > 0) {
            while (cur.moveToNext()) {
                String id = cur.getString(cur.getColumnIndex(ContactsContract.Contacts._ID));
                String name = cur.getString(cur.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME));
                if (Integer.parseInt(cur.getString(cur.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER))) > 0) {
                    System.out.println("name : " + name + ", ID : " + id);
                    contactList.put("name", name);

                    // get the phone number
                    Cursor pCur = cr.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,
                            ContactsContract.CommonDataKinds.Phone.CONTACT_ID +" = ?",
                            new String[]{id}, null);
                    while (pCur.moveToNext()) {
                        String phone = pCur.getString(
                                pCur.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                        System.out.println("phone" + phone);
                        contactList.put("phone", phone);

                    }
                    pCur.close();


                    // get email and type

                    Cursor emailCur = cr.query(
                            ContactsContract.CommonDataKinds.Email.CONTENT_URI,
                            null,
                            ContactsContract.CommonDataKinds.Email.CONTACT_ID + " = ?",
                            new String[]{id}, null);
                    while (emailCur.moveToNext()) {
                        // This would allow you get several email addresses
                        // if the email addresses were stored in an array
                        String email = emailCur.getString(
                                emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA));
                        String emailType = emailCur.getString(
                                emailCur.getColumnIndex(ContactsContract.CommonDataKinds.Email.TYPE));

                        System.out.println("Email " + email + " Email Type : " + emailType);
                        contactList.put("email", email);

                    }
                    emailCur.close();



                    //Get Postal Address....

                    String addrWhere = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?";
                    String[] addrWhereParams = new String[]{id,
                            ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE};
                    Cursor addrCur = cr.query(ContactsContract.Data.CONTENT_URI,
                            null, null, null, null);

                    while(addrCur.moveToNext()) {
                        contactList.put("address", addrCur.getString(
                                addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.DATA1)));
                        //String street = addrCur.getString(
                               // addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.STREET));
                       // String city = addrCur.getString(
                               // addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.CITY));
                        //String state = addrCur.getString(
                               // addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.REGION));
                       // String postalCode = addrCur.getString(
                               // addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.POSTCODE));
                       // String country = addrCur.getString(
                              //  addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.COUNTRY));
                       // String type = addrCur.getString(
                              //  addrCur.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.TYPE));

                        // Do something with these....

                    }
                    addrCur.close();
/*
                    // Get Instant Messenger.........
                    String imWhere = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?";
                    String[] imWhereParams = new String[]{id,
                            ContactsContract.CommonDataKinds.Im.CONTENT_ITEM_TYPE};
                    Cursor imCur = cr.query(ContactsContract.Data.CONTENT_URI,
                            null, imWhere, imWhereParams, null);
                    if (imCur.moveToFirst()) {
                        String imName = imCur.getString(
                                imCur.getColumnIndex(ContactsContract.CommonDataKinds.Im.DATA));
                        String imType;
                        imType = imCur.getString(
                                imCur.getColumnIndex(ContactsContract.CommonDataKinds.Im.TYPE));
                    }
                    imCur.close();

                    // Get Organizations.........

                    String orgWhere = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?";
                    String[] orgWhereParams = new String[]{id,
                            ContactsContract.CommonDataKinds.Organization.CONTENT_ITEM_TYPE};
                    Cursor orgCur = cr.query(ContactsContract.Data.CONTENT_URI,
                            null, orgWhere, orgWhereParams, null);
                    if (orgCur.moveToFirst()) {
                        String orgName = orgCur.getString(orgCur.getColumnIndex(ContactsContract.CommonDataKinds.Organization.DATA));
                        String title = orgCur.getString(orgCur.getColumnIndex(ContactsContract.CommonDataKinds.Organization.TITLE));
                    }
                    orgCur.close();
                    */
                    contacts.put(i, contactList);
                    i++;

                }
            }
        }
        return contacts;
    }


    /**
     * Retorna un ID del contacto due;o del nro recibido number
     *
     * @param contactHelper
     * @param number
     * @return
     */
    private static long getContactID(ContentResolver contactHelper,
                                     String number) {
        Uri contactUri = Uri.withAppendedPath(ContactsContract.PhoneLookup.CONTENT_FILTER_URI,
                Uri.encode(number));

        String[] projection = {ContactsContract.PhoneLookup._ID};
        Cursor cursor = null;

        try {
            cursor = contactHelper.query(contactUri, projection, null, null,
                    null);

            if (cursor.moveToFirst()) {
                int personID = cursor.getColumnIndex(ContactsContract.PhoneLookup._ID);
                return cursor.getLong(personID);
            }

            return -1;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) {
                cursor.close();
                cursor = null;
            }
        }

        return -1;
    }

    /**
     * Insertar contacto nuevo con nombre y nro
     *
     * @param contactAdder
     * @param firstName
     * @param mobileNumber
     * @return
     */
    public static boolean insertContact(ContentResolver contactAdder,
                                        String firstName, String mobileNumber, String email, String address) {

        deleteContact(contactAdder, mobileNumber);

        ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
        ops.add(ContentProviderOperation
                .newInsert(ContactsContract.RawContacts.CONTENT_URI)
                .withValue(ContactsContract.RawContacts.ACCOUNT_TYPE, null)
                .withValue(ContactsContract.RawContacts.ACCOUNT_NAME, null)
                .build());
        ops.add(ContentProviderOperation
                .newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(
                        ContactsContract.Data.MIMETYPE,
                        ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE)
                .withValue(
                        ContactsContract.CommonDataKinds.StructuredName.GIVEN_NAME,
                        firstName).build());
        ops.add(ContentProviderOperation
                .newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(
                        ContactsContract.Data.MIMETYPE,
                        ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER,
                        mobileNumber)
                .withValue(ContactsContract.CommonDataKinds.Phone.TYPE,
                        ContactsContract.CommonDataKinds.Phone.TYPE_MOBILE).build());
        ops.add(ContentProviderOperation
                .newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(
                        ContactsContract.Data.MIMETYPE,
                        ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.Email.DATA1,
                        email)
                .withValue(ContactsContract.CommonDataKinds.Email.TYPE,
                        ContactsContract.CommonDataKinds.Email.TYPE_MOBILE).build());
        ops.add(ContentProviderOperation
                .newInsert(ContactsContract.Data.CONTENT_URI)
                .withValueBackReference(ContactsContract.Data.RAW_CONTACT_ID, 0)
                .withValue(
                        ContactsContract.Data.MIMETYPE,
                        ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE)
                .withValue(ContactsContract.CommonDataKinds.StructuredPostal.DATA1,
                        address)
                .withValue(ContactsContract.CommonDataKinds.StructuredPostal.TYPE,
                        ContactsContract.CommonDataKinds.StructuredPostal.TYPE_HOME).build());

        try {
            contactAdder.applyBatch(ContactsContract.AUTHORITY, ops);
        } catch (Exception e) {
            return false;
        }

        return true;
    }

    /**
     * Borra un contacto dependiendo del numero que se escoja
     *
     * @param contactHelper
     * @param number
     */
    public static void deleteContact(ContentResolver contactHelper,
                                     String number) {

        ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();
        String[] args = new String[]{String.valueOf(getContactID(
                contactHelper, number))};

        ops.add(ContentProviderOperation.newDelete(ContactsContract.RawContacts.CONTENT_URI)
                .withSelection(ContactsContract.RawContacts.CONTACT_ID + "=?", args).build());
        try {
            contactHelper.applyBatch(ContactsContract.AUTHORITY, ops);
        } catch (RemoteException e) {
            e.printStackTrace();
        } catch (OperationApplicationException e) {
            e.printStackTrace();
        }
    }


    /**
     * Validez del email
     *
     * @param email
     * @return
     */
    private static boolean isEmailValid(String email) {
        String emailAddress = email.toString().trim();
        if (emailAddress == null)
            return false;
        else if (emailAddress.equals(""))
            return false;
        else if (emailAddress.length() <= 6)
            return false;
        else {
            String expression = "^[a-z][a-z|0-9|]*([_][a-z|0-9]+)*([.][a-z|0-9]+([_][a-z|0-9]+)*)?@[a-z][a-z|0-9|]*\\.([a-z][a-z|0-9]*(\\.[a-z][a-z|0-9]*)?)$";
            CharSequence inputStr = emailAddress;
            Pattern pattern = Pattern.compile(expression,
                    Pattern.CASE_INSENSITIVE);
            Matcher matcher = pattern.matcher(inputStr);
            if (matcher.matches())
                return true;
            else
                return false;
        }
    }

    private static boolean match(String stringToCompare, String regularExpression) {
        boolean success = false;
        Pattern pattern = Pattern.compile(regularExpression);
        Matcher matcher = pattern.matcher(stringToCompare);
        if (matcher.matches())
            success = true;
        return success;
    }

    /**
     * Actualiza el contacto
     *
     * @param contactHelper
     * @param name
     * @param number
     * @param email
     * @param address
     *@param ContactId  @return
     */
    public static boolean updateContact(ContentResolver contactHelper, String name, String number, String email, String address, String ContactId) {

        boolean success = true;
        String phnumexp = "^[0-9]*$";

        try {
            name = name.trim();
            email = email.trim();
            number = number.trim();
            address = address.trim();


            if (name.equals("") && number.equals("") && email.equals("")) {
                success = false;
                Log.d("ENTRO", "1");
            } else if ((number.equals(""))) {
                success = false;
                Log.d("ENTRO", "2");
            } else if ((email.equals("")) && (!isEmailValid(email))) {
                success = false;
                Log.d("ENTRO", "3");
            } else if ((address.equals(""))) {
                success = false;
                Log.d("ENTRO", "4");
            } else {
                ContentResolver contentResolver = contactHelper;

                String where = ContactsContract.Data.CONTACT_ID + " = ? AND " + ContactsContract.Data.MIMETYPE + " = ?";

                String[] emailParams = new String[]{ContactId, ContactsContract.CommonDataKinds.Email.CONTENT_ITEM_TYPE};
                String[] nameParams = new String[]{ContactId, ContactsContract.CommonDataKinds.StructuredName.CONTENT_ITEM_TYPE};
                String[] numberParams = new String[]{ContactId, ContactsContract.CommonDataKinds.Phone.CONTENT_ITEM_TYPE};
                String[] addressParams = new String[]{ContactId, ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_ITEM_TYPE};

                ArrayList<ContentProviderOperation> ops = new ArrayList<ContentProviderOperation>();

                if (!email.equals("")) {

                    ops.add(android.content.ContentProviderOperation.newUpdate(android.provider.ContactsContract.Data.CONTENT_URI)
                            .withSelection(where, emailParams)
                            .withValue(ContactsContract.CommonDataKinds.Email.DATA, email)
                            .build());
                }

                if (!name.equals("")) {
                    ops.add(android.content.ContentProviderOperation.newUpdate(android.provider.ContactsContract.Data.CONTENT_URI)
                            .withSelection(where, nameParams)
                            .withValue(ContactsContract.CommonDataKinds.StructuredName.DISPLAY_NAME, name)
                            .build());
                }

                if (!number.equals("")) {

                    ops.add(android.content.ContentProviderOperation.newUpdate(android.provider.ContactsContract.Data.CONTENT_URI)
                            .withSelection(where, numberParams)
                            .withValue(ContactsContract.CommonDataKinds.Phone.NUMBER, number)
                            .build());
                }
                if (!address.equals("")) {

                    ops.add(android.content.ContentProviderOperation.newUpdate(android.provider.ContactsContract.Data.CONTENT_URI)
                            .withSelection(where, addressParams)
                            .withValue(ContactsContract.CommonDataKinds.StructuredPostal.DATA1, address)
                            .build());
                }
                contentResolver.applyBatch(ContactsContract.AUTHORITY, ops);
            }
        } catch (Exception e) {
            e.printStackTrace();
            success = false;
        }
        return success;



    }

    /** No se usa
     * @param contactHelper
     * @return
     */
    public static JSONArray getContacts(ContentResolver contactHelper) throws JSONException {


        Cursor cursor;
        int counter;


        JSONObject contactList = new JSONObject();
        JSONArray contacts = new JSONArray();
        JSONObject mainObj = new JSONObject();

        String phoneNumber = "";
        String email = "";
        String postal = "";

        Uri CONTENT_URI = ContactsContract.Contacts.CONTENT_URI;
        String _ID = ContactsContract.Contacts._ID;
        String DISPLAY_NAME = ContactsContract.Contacts.DISPLAY_NAME;
        String HAS_PHONE_NUMBER = ContactsContract.Contacts.HAS_PHONE_NUMBER;

        Uri PhoneCONTENT_URI = ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String Phone_CONTACT_ID = ContactsContract.CommonDataKinds.Phone.CONTACT_ID;
        String NUMBER = ContactsContract.CommonDataKinds.Phone.NUMBER;

        Uri EmailCONTENT_URI = ContactsContract.CommonDataKinds.Email.CONTENT_URI;
        String EmailCONTACT_ID = ContactsContract.CommonDataKinds.Email.CONTACT_ID;
        String DATA = ContactsContract.CommonDataKinds.Email.DATA;

        Uri PostalCONTENT_URI = ContactsContract.CommonDataKinds.StructuredPostal.CONTENT_URI;
        String PostalCONTACT_ID = ContactsContract.CommonDataKinds.StructuredPostal.CONTACT_ID;
        String DATA1 = ContactsContract.CommonDataKinds.StructuredPostal.DATA;


        ContentResolver contentResolver = contactHelper;

        cursor = contentResolver.query(CONTENT_URI, null, null, null, null);
        int i = 0, confirm = 0;
        // Iterate every contact in the phone
        if (cursor.getCount() > 0) {

            counter = 0;
            while (cursor.moveToNext()) {


                String contact_id = cursor.getString(cursor.getColumnIndex(_ID));
                String name = cursor.getString(cursor.getColumnIndex(DISPLAY_NAME));


                int hasPhoneNumber = Integer.parseInt(cursor.getString(cursor.getColumnIndex(HAS_PHONE_NUMBER)));

                if (hasPhoneNumber > 0) {
                    contactList = new JSONObject();

                    contactList.put("id", contact_id);


                    if (name == null)
                        contactList.put("name", "");

                    contactList.put("name", name);


                    //This is to read multiple phone numbers associated with the same contact
                    Cursor phoneCursor = contentResolver.query(PhoneCONTENT_URI, null, Phone_CONTACT_ID + " = ?", new String[]{contact_id}, null);


                    while (phoneCursor.moveToNext()) {

                        phoneNumber = phoneCursor.getString(phoneCursor.getColumnIndex(NUMBER));
                        contactList.put("phone", phoneNumber);
                        confirm = 1;

                    }

                    if (confirm == 0)
                        contactList.put("phone", "");

                    phoneCursor.close();


                    // Read every email id associated with the contact
                    Cursor emailCursor = contentResolver.query(EmailCONTENT_URI, null, EmailCONTACT_ID + " = ?", new String[]{contact_id}, null);


                    while (emailCursor.moveToNext()) {

                        email = emailCursor.getString(emailCursor.getColumnIndex(DATA));
                        contactList.put("email", email);
                        confirm = 2;

                    }

                    if (confirm == 1 || confirm == 0)
                        contactList.put("email", "");

                    emailCursor.close();

                    Cursor postalCursor = contentResolver.query(PostalCONTENT_URI, null, PostalCONTACT_ID + " = ?", new String[]{contact_id}, null);


                    while (postalCursor.moveToNext()) {

                        postal = postalCursor.getString(postalCursor.getColumnIndex(DATA1));
                        contactList.put("postal", postal);
                        confirm = 3;
                    }

                    if (confirm == 2 || confirm == 1 || confirm == 0)
                        contactList.put("postal", "");

                    postalCursor.close();

                    Log.d("Contactlist", contactList.toString());
                    contacts.put(counter, contactList);
                    counter++;


                }
                i++;
                name = "";
                email = "";
                postal = "";
                phoneNumber = "";
                confirm = 0;
            }

            mainObj.put("contact", contacts);


        }
        return contacts;
    }


    /**
     * Funcion que retorna objeto cursos con info de los contactos
     *
     * @param contactHelper
     * @return
     */
    public static JSONArray getContactCursor(ContentResolver contactHelper) throws JSONException {
        int i = 0;
        ArrayList<String> conNames;
        ArrayList<String> conNumbers;
        ArrayList<String> conDir;
        ArrayList<String> conPic;
        JSONObject contactList = new JSONObject();
        JSONArray contacts = new JSONArray();
        Cursor crContacts;

        String[] projection = {
                ContactsContract.CommonDataKinds.Email.CONTACT_ID,
                ContactsContract.Contacts.DISPLAY_NAME,
                ContactsContract.CommonDataKinds.Email.DATA,
                ContactsContract.CommonDataKinds.StructuredPostal.DATA,
                ContactsContract.CommonDataKinds.Phone.DATA
        };
        Cursor cursor = contactHelper.query(ContactsContract.
                CommonDataKinds.Email.CONTENT_URI, projection, null, null, null);



        if (cursor != null) {
            try {
                final int contactIdIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.CONTACT_ID);
                final int displayNameIndex = cursor.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);
                final int emailIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Email.DATA);
                final int addressIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.DATA);
                final int phoneIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA);
                long contactId = 0;

                String displayName, address, email;
                while (cursor.moveToNext()) {
                    contactList.put("id",cursor.getLong(contactIdIndex));
                    contactList.put("name",cursor.getString(displayNameIndex));
                    contactList.put("email",cursor.getString(emailIndex));


                }
                contacts.put(i, contactList);
                i++;

            } finally {
                cursor.close();
            }
        }

        projection = new String[]{
                ContactsContract.CommonDataKinds.StructuredPostal.CONTACT_ID,
                ContactsContract.CommonDataKinds.StructuredPostal.DATA,
        };
        cursor = contactHelper.query(ContactsContract.
                CommonDataKinds.StructuredPostal.CONTENT_URI, projection, null, null, null);



        if (cursor != null) {
            try {
                final int contactIdIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.CONTACT_ID);
                final int addressIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.DATA);
                final int displayNameIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.StructuredPostal.DISPLAY_NAME);
                while (cursor.moveToNext()) {
                    contactList.put("id",cursor.getLong(contactIdIndex));
                    contactList.put("postal",cursor.getString(addressIndex));
                    contactList.put("name",cursor.getString(displayNameIndex));
                    contacts.put(i, contactList);
                    i++;

                }
            } finally {
                cursor.close();
            }
        }

        projection = new String[]{
                ContactsContract.CommonDataKinds.Phone.CONTACT_ID,
                ContactsContract.CommonDataKinds.Phone.DATA,
        };
        cursor = contactHelper.query(ContactsContract.
                CommonDataKinds.StructuredPostal.CONTENT_URI, projection, null, null, null);



        if (cursor != null) {
            try {
                final int contactIdIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.CONTACT_ID);
                final int phoneIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DATA);
                final int displayNameIndex = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME);
                while (cursor.moveToNext()) {
                    contactList.put("id",cursor.getLong(contactIdIndex));
                    contactList.put("phone",cursor.getString(phoneIndex));
                    contactList.put("name",cursor.getString(displayNameIndex));
                    contacts.put(i, contactList);
                    i++;

                }
            } finally {
                cursor.close();
            }
        }

        return contacts;
    }

}
