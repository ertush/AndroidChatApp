package com.androidchatapp;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Canvas;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toolbar;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;

public class Users extends AppCompatActivity {
    ListView usersList;
    TextView noUsersText; //txtUserName;
    ActionBarDrawerToggle toggle;
    DrawerLayout drawer;
    //ImageView usr_photo,usr_profile_img;
    ArrayList<String> al = new ArrayList<>();
    int totalUsers = 0;
    ProgressDialog pd;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);


        drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        usersList = (ListView)findViewById(R.id.usersList);
        //txtUserName = (TextView)findViewById(R.id.TxtUserName);

        noUsersText = (TextView)findViewById(R.id.noUsersText);

        //txtUserName.setText(UserDetails.username);


        toggle = new ActionBarDrawerToggle(this, drawer, R.string.nav_open_drawer, R.string.nav_close_drawer);
        drawer.addDrawerListener(toggle);

        //usr_photo.setImageDrawable(usr_profile_img.getDrawable());
        toggle.syncState();
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);



        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);


        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                                                             @Override
                                                             public boolean onNavigationItemSelected(MenuItem item) {

                                                                 switch (item.getItemId()) {
                                                                     case R.id.menu_item_logout:
                                                                         startActivity(new Intent(Users.this, Login.class));
                                                                         finish();
                                                                         break;


                                                                     case R.id.menu_item_userProf:
                                                                        startActivity(new Intent(Users.this, UserProfileActivity.class));

                                                                        break;
                                                                        case R.id.menu_item_about:
                                                                            showAboutDialog();
                                                                            break;

                                                                 }
                                                                 DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
                                                                 drawer.closeDrawer(GravityCompat.START);
                                                                 return true;
                                                             }
                                                         });



        pd = new ProgressDialog(Users.this);
        pd.setMessage("Loading...");
        pd.show();

        String url = "https://androidchatapp-59b99.firebaseio.com/users.json";

        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>(){
            @Override
            public void onResponse(String s) {
                doOnSuccess(s);
            }
        },new Response.ErrorListener(){
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                System.out.println("" + volleyError);
            }
        });

        RequestQueue rQueue = Volley.newRequestQueue(Users.this);
        rQueue.add(request);

        usersList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                UserDetails.chatWith = al.get(position);
                startActivity(new Intent(Users.this, Chat.class));
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(toggle.onOptionsItemSelected(item)) {
        return  true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void showAboutDialog() {
        String[] options = new String[] { "Upload a photo", "Take a photo" };

        AlertDialog.Builder builder = new AlertDialog.Builder(Users.this);
        builder.setIcon(R.drawable.ic_logo_e);
        builder.setMessage("Easy Chat is a one to one real-time chat App powered by firebase\n Developers : Eric, Mitchelle");
        builder.create().show();
    }
    public void doOnSuccess(String s){
        try {
            JSONObject obj = new JSONObject(s);

            Iterator i = obj.keys();
            String key = "";

            while(i.hasNext()){
                key = i.next().toString();

                if(!key.equals(UserDetails.username)) {


                    al.add(key);
                }

                totalUsers++;
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(totalUsers <=1){
            noUsersText.setVisibility(View.VISIBLE);
            usersList.setVisibility(View.GONE);
        }
        else{
            noUsersText.setVisibility(View.GONE);
            usersList.setVisibility(View.VISIBLE);
            usersList.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, al)); //android.R.layout.simple_list_item_1
        }

        pd.dismiss();
    }

   /* @Override
    protected void onStart() {
        super.onStart();
        txtUserName.setText(UserDetails.getUsername());
        txtEmail.setText(UserDetails.getUsername()+"@hotmail.com");
    }*/
}