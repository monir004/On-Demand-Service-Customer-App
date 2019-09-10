package com.dhakasetup.sakib.dhakasetupprototype;

import android.Manifest;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.media.Ringtone;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.Fragment;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dhakasetup.sakib.dhakasetupprototype.model.datamodel.Data;
import com.facebook.accountkit.Account;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitCallback;
import com.facebook.accountkit.AccountKitError;
import com.facebook.accountkit.AccountKitLoginResult;
import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;
import com.google.firebase.iid.FirebaseInstanceId;
//import com.google.android.gms.common.GoogleApiAvailability;

import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 69;
    int Permission_All = 1;
    String[] Permissions = {Manifest.permission.READ_PHONE_STATE, Manifest.permission.RECEIVE_SMS};
    TextView tv;
    public SharedPreferences settings, setting;
    Context context;
    public BottomNavigationView bottomNavigationView;
    String fragmentTag;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        context = this;
        //startLoginPage(LoginType.PHONE);
        Data data = Data.getInstance(this);
        data.load(this);



        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_action_notification);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment(),"home").commit();

        String recentToken = FirebaseInstanceId.getInstance().getToken();
        String user_phone = getSharedPreferences("dhakasetup",MODE_PRIVATE).getString("phone",null);
        if (user_phone != null){
            //updateToken(user_phone,recentToken,"online");
        }
        getSharedPreferences("dhakasetup",MODE_PRIVATE).edit().putString("token",recentToken).commit();
        Log.d("profileres", "main activity: "+recentToken);

        bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation_bar);
        //BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                Fragment selectedFragment = null;

                switch (item.getItemId()){
                    case R.id.nav_home:
                        selectedFragment = new HomeFragment();
                        fragmentTag = "home";
                        break;
                    case R.id.nav_order:
                        selectedFragment = new OrderFragment();
                        fragmentTag = "order";
                        break;
                    case R.id.nav_personal:
                        setting = context.getSharedPreferences("customer_app",Context.MODE_PRIVATE);

//                        setting.edit().putString("userid","607905166285584").commit();
//                        setting.edit().putString("phone","+8801521220462").commit();


                        String userid = setting.getString("userid",null);
                        String mobile = setting.getString("mobile",null);
                        if(userid != null){
                            selectedFragment = new MiddleFragment();
                            fragmentTag = "middle";
                        }
                        else {
                            selectedFragment = new ProfileFragment();
                            fragmentTag = "profile";
                        }
                        //startLoginPage(LoginType.PHONE);
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment,fragmentTag).commit();
                updateNavigationBarState(item.getItemId());
                return true;
            }
        });
        //Toast.makeText(this,""+GoogleApiAvailability.getInstance().isGooglePlayServicesAvailable(this),Toast.LENGTH_SHORT).show();
        String languageToLoad = "en";
        Locale locale = new Locale(languageToLoad);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

        if(!hasPermissions(this, Permissions)){
            ActivityCompat.requestPermissions(this, Permissions, Permission_All);
        }
        /*try {
            PackageInfo info = getPackageManager().getPackageInfo("com.example.sakib.update1", PackageManager.GET_SIGNATURES);
            for (Signature signature:info.signatures){
                MessageDigest md = null;
                try {
                    md = MessageDigest.getInstance("SHA");
                } catch (NoSuchAlgorithmException e) {
                    e.printStackTrace();
                }
                md.update(signature.toByteArray());
                Log.d("KEYHASH", Base64.encodeToString(md.digest(),Base64.DEFAULT));
            }
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }*/
        int fragmentIntent = getIntent().getIntExtra("fragmentNumber",0);
        if (fragmentIntent == 1){
            Fragment selectedFragment = new MiddleFragment();
            fragmentTag = "middle";
            bottomNavigationView.getMenu().getItem(1).setChecked(true);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment,fragmentTag).commit();
        }
        else if (fragmentIntent == 2){
            Fragment selectedFragment = new ProfileFragment();
            fragmentTag = "profile";
            bottomNavigationView.getMenu().getItem(1).setChecked(true);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment,fragmentTag).commit();
        }
        else if (fragmentIntent == 3){
            Fragment selectedFragment = new OrderFragment();
            fragmentTag = "order";
            bottomNavigationView.getMenu().getItem(2).setChecked(true);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment,fragmentTag).commit();
        }

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);

        MenuItem menuItem = menu.findItem(R.id.action_cart);
        menuItem.setIcon(MenuPainter.paint(MainActivity.this,Data.getCart(this).size(),R.drawable.ic_action_cart));
        return true;
    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(item.getItemId() == android.R.id.home){
            //
        }
        switch (item.getItemId()) {
            case R.id.action_cart:
                Intent intent = new Intent(this,CartActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_search:
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    private void updateNavigationBarState(int actionId){
        Menu menu = bottomNavigationView.getMenu();

        for (int i = 0, size = menu.size(); i < size; i++) {
            MenuItem item = menu.getItem(i);
            item.setChecked(item.getItemId() == actionId);
        }
    }

    public void startLoginPage(LoginType loginType) {
        Intent intent = new Intent(this, AccountKitActivity.class);
        AccountKitConfiguration.AccountKitConfigurationBuilder builder =
                new AccountKitConfiguration.AccountKitConfigurationBuilder(loginType,AccountKitActivity.ResponseType.TOKEN);
        builder.setReadPhoneStateEnabled(true);
        builder.setReceiveSMS(true);
        builder.setDefaultCountryCode("BD");
        intent.putExtra(AccountKitActivity.ACCOUNT_KIT_ACTIVITY_CONFIGURATION,builder.build());
        startActivityForResult(intent,REQUEST_CODE);
    }

    public static boolean hasPermissions(Context context, String... permissions) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REQUEST_CODE){
            AccountKitLoginResult result = data.getParcelableExtra(AccountKitLoginResult.RESULT_KEY);

            if(result.getError() != null){
                Toast.makeText(this,result.getError().getErrorType().getMessage(),Toast.LENGTH_SHORT);
            }
            else if (result.wasCancelled()){
                Toast.makeText(this,"cancelled",Toast.LENGTH_SHORT);
            }
            else {
                if(result.getAccessToken() != null){
                    AccountKit.getCurrentAccount(new AccountKitCallback<Account>() {
                        @Override
                        public void onSuccess(Account account) {
                            String id = account.getId();
                            String num = account.getPhoneNumber().toString();
                            Toast.makeText(getApplicationContext(),num,Toast.LENGTH_SHORT).show();
                            settings = getSharedPreferences("customer_app", Context.MODE_PRIVATE);
                            settings.edit().putString("userid",id).commit();
                            settings.edit().putString("mobile",num).commit();

                            updateToken(settings.getString("mobile",null),
                                    settings.getString("token",null));

                            Fragment selectedFragment = new MiddleFragment();
                            fragmentTag = "middle";
                            bottomNavigationView.getMenu().getItem(1).setChecked(true);
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment,fragmentTag).commit();

                        }

                        @Override
                        public void onError(AccountKitError accountKitError) {
                            Fragment selectedFragment = new ProfileFragment();
                            fragmentTag = "profile";
                            bottomNavigationView.getMenu().getItem(1).setChecked(true);
                            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment,fragmentTag).commit();
                        }
                    });
                }
            }
        }
    }

    void kitAvoider(){
        String id = "125";
        String num = "+8801534635982";
        Toast.makeText(getApplicationContext(),num,Toast.LENGTH_SHORT).show();
        settings = getSharedPreferences("customer_app", Context.MODE_PRIVATE);
        settings.edit().putString("userid",id).commit();
        settings.edit().putString("mobile",num).commit();
        Fragment selectedFragment = new MiddleFragment();
        fragmentTag = "middle";
        bottomNavigationView.getMenu().getItem(1).setChecked(true);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment,fragmentTag).commit();
    }

    public void updateToken(final String phone, final String token){
        Log.d("profileres", "updateToken: "+phone+token);
        StringRequest request = new StringRequest(Request.Method.POST,
                UrlList.customerToken,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            Log.d("20619",response);
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                String s = "";
                try {
                    s = new String(error.networkResponse.data,"UTF-8");
                } catch (Exception e) {
                    e.printStackTrace();
                }
                Log.d("profileres",String.valueOf(error.networkResponse.statusCode+s));
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String,String> params = new HashMap<String, String>();
                params.put("mobile",phone);
                params.put("token",token);
                return params;
            }
        };

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);
    }


}
