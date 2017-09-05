package CustomAdapter;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.design.widget.Snackbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.exampledemo.parsaniahardik.getcontactdetailsdemonuts.R;

import java.util.ArrayList;

import Contact.ContactsData;

/**
 * Created by juanmacedo on 1/9/17.
 */

public class CustomAdapter extends ArrayAdapter<ContactsData> implements View.OnClickListener {


        private ArrayList<ContactsData> contactsDataArrayList;
        Context mContext;

        // View lookup cache
        private static class ViewHolder {
            TextView txtName;
            TextView txtPhone;
            TextView txtEmail;
            TextView txtState;
            TextView txtCity;
            TextView txtStreet;
            TextView txtCode;
        }

        public CustomAdapter(ArrayList<ContactsData> data, Context context) {
            super(context, R.layout.activity_shippings, data);
            this.contactsDataArrayList = data;
            this.mContext=context;

        }

        @Override
        public void onClick(View v) {

            int position=(Integer) v.getTag();
            Object object= getItem(position);
            ContactsData contactsData = (ContactsData) object;

            switch (v.getId())
            {

            }
        }

        private int lastPosition = -1;

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            // Get the data item for this position
            ContactsData contactsData = getItem(position);
            // Check if an existing view is being reused, otherwise inflate the view
            ViewHolder viewHolder; // view lookup cache stored in tag

            final View result;

            if (convertView == null) {

                viewHolder = new ViewHolder();
                LayoutInflater inflater = LayoutInflater.from(getContext());
                convertView = inflater.inflate(R.layout.activity_shippings, parent, false);
                viewHolder.txtName = (TextView) convertView.findViewById(R.id.name);
                viewHolder.txtPhone = (TextView) convertView.findViewById(R.id.phone);
                viewHolder.txtEmail = (TextView) convertView.findViewById(R.id.email);
                viewHolder.txtState = (TextView) convertView.findViewById(R.id.state);
                viewHolder.txtCity = (TextView) convertView.findViewById(R.id.city);
                viewHolder.txtStreet = (TextView) convertView.findViewById(R.id.Street);
                viewHolder.txtCode = (TextView) convertView.findViewById(R.id.code);


                result=convertView;

                convertView.setTag(viewHolder);
            } else {
                viewHolder = (ViewHolder) convertView.getTag();
                result = convertView;
            }

            lastPosition = position;

            viewHolder.txtName.setText(contactsData.getName());
            viewHolder.txtPhone.setText(contactsData.getPhone());
            viewHolder.txtEmail.setText(contactsData.getEmail());
            viewHolder.txtState.setText(contactsData.getState());
            viewHolder.txtCity.setText(contactsData.getCity());
            viewHolder.txtStreet.setText(contactsData.getStreet());
            viewHolder.txtState.setText(contactsData.getState());
            viewHolder.txtCode.setText(contactsData.getCode());

            // Return the completed view to render on screen
            return convertView;
        }
    }