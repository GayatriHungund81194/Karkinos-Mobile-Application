package karkinos.gayatri.com.kankinos;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.json.JSONObject;

public class UserProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_profile);

        final TextView name = (TextView)findViewById(R.id.name);
        final TextView age = (TextView)findViewById(R.id.age);
        final TextView dob = (TextView)findViewById(R.id.dob);
        final TextView phone = (TextView)findViewById(R.id.phone);
        final TextView email = (TextView)findViewById(R.id.email);
        final TextView insurance = (TextView)findViewById(R.id.insurance);
        final TextView bloodgrp = (TextView)findViewById(R.id.bloodgrp);
        Button editpro = (Button)findViewById(R.id.editprofile);
       // getActionBar().hide();
        Firebase.setAndroidContext(this);
        RequestQueue queue = Volley.newRequestQueue(this);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String Uname = user.getDisplayName();

        String names[] =Uname.split(" ");
        String dbUser = names[0].toLowerCase();

        Log.d("tag","UserName:"+dbUser);
        String url = "https://karkinos-d46f8.firebaseio.com/"+dbUser+"/.json?auth=IWQoL0bqVw3axRIrgkOg9EICbk0tU7dZdDAlurQX";

        StringRequest req = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {



                String arr[] =response.split(",");

                try {
                    JSONObject j = new JSONObject(response.toString());
                    String pName = j.getString("Name").toString();
                    String pAge = j.getString("Age").toString();
                    String pDob = j.getString("Date of Birth").toString();
                    String pPhone = j.getString("Phone Number").toString();
                    String pEmail = j.getString("E-mail").toString();
                    String pInsurance = j.getString("Insurance Card").toString();
                    String pBloodgrp = j.getString("Blood Group").toString();
//
//                   // Log.d("###########Name:",pName);
//
                    name.setText(pName);
                    age.setText(pAge);
                    dob.setText(pDob);
                    phone.setText(pPhone);
                    email.setText(pEmail);
                    insurance.setText(pInsurance);
                    bloodgrp.setText(pBloodgrp);
                }catch (Exception e){
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("Error","Error Occured in fetching user details");
            }
        });

        queue.add(req);


        editpro.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserProfile.this,EditProfile.class));
            }
        });



    }
}
