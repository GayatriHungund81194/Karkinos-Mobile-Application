package karkinos.gayatri.com.kankinos;

import android.app.DownloadManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.firebase.client.Firebase;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class EditProfile extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        final EditText phone = (EditText)findViewById(R.id.editphone);
        final EditText email = (EditText)findViewById(R.id.editemail);
        final EditText insurance = (EditText)findViewById(R.id.editinsurance);
        final EditText bgroup = (EditText)findViewById(R.id.editbloodgrp);
        final TextView name = (TextView)findViewById(R.id.setname);
        final TextView age = (TextView)findViewById(R.id.setage);
        final TextView dob = (TextView)findViewById(R.id.setdob);
        Button submit = (Button)findViewById(R.id.submit);

        Firebase.setAndroidContext(this);
        final RequestQueue queue = Volley.newRequestQueue(this);
        FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();
        String Uname = user.getDisplayName();

        String names[] =Uname.split(" ");
        final String dbUser = names[0].toLowerCase();

        Log.d("tag","UserName:"+dbUser);
        String url = "https://karkinos-d46f8.firebaseio.com/"+dbUser+"/.json?auth=IWQoL0bqVw3axRIrgkOg9EICbk0tU7dZdDAlurQX";

        ///
        final StringRequest req = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {



                String arr[] =response.split(",");

                try {
                    JSONObject j = new JSONObject(response.toString());
                    String pName = j.getString("Name").toString();
                    String pAge = j.getString("Age").toString();
                    String pDob = j.getString("Date of Birth").toString();

//
                    Log.d("###########Name:",pName);
//
                    name.setText(pName);
                    age.setText(pAge);
                    dob.setText(pDob);

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


        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                 final String putPhone = phone.getText().toString();
                 final String putEmail = email.getText().toString();
                 final String putInsurance = insurance.getText().toString();
                 final String putBgroup = bgroup.getText().toString();


                final DatabaseReference dataRef;
                dataRef = FirebaseDatabase.getInstance().getReference().child(dbUser);


                dataRef.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                        dataRef.child("Phone Number").setValue(putPhone);
                        dataRef.child("E-mail").setValue(putEmail);
                        dataRef.child("Insurance Card").setValue(putInsurance);
                        dataRef.child("Blood Group").setValue(putBgroup);
                    }

                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {

                    }
                });


            }
        });

    }
}
