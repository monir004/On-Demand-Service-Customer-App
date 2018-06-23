package com.dhakasetup.sakib.dhakasetupprototype;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

import com.dhakasetup.sakib.dhakasetupprototype.model.datamodel.Data;
import com.facebook.accountkit.Account;
import com.facebook.accountkit.AccountKit;
import com.facebook.accountkit.AccountKitCallback;
import com.facebook.accountkit.AccountKitError;
import com.facebook.accountkit.AccountKitLoginResult;
import com.facebook.accountkit.ui.AccountKitActivity;
import com.facebook.accountkit.ui.AccountKitConfiguration;
import com.facebook.accountkit.ui.LoginType;

import java.util.Locale;

public class MainActivity extends AppCompatActivity {

    private static final int REQUEST_CODE = 69;
    TextView tv;
    public SharedPreferences settings;
    public BottomNavigationView bottomNavigationView;
    String fragmentTag;
    Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //startLoginPage(LoginType.PHONE);
        Data data = Data.getInstance(this);
        data.load(this);

        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setNavigationIcon(R.drawable.ic_action_notification);
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,new HomeFragment(),"home").commit();


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
                        selectedFragment = new ProfileFragment();
                        fragmentTag = "profile";
                        //startLoginPage(LoginType.PHONE);
                        break;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectedFragment,fragmentTag).commit();
                return true;
            }
        });

        String languageToLoad = "en";
        Locale locale = new Locale(languageToLoad);
        Configuration config = new Configuration();
        config.locale = locale;
        getBaseContext().getResources().updateConfiguration(config, getBaseContext().getResources().getDisplayMetrics());

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

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
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
                            settings = getSharedPreferences("dhakasetup", Context.MODE_PRIVATE);
                            settings.edit().putString("userid",account.getId()).commit();
                            settings.edit().putString("phone",account.getPhoneNumber().toString()).commit();
                            ((ProfileFragment)getSupportFragmentManager().findFragmentByTag("profile")).setProfile(id,num);
                        }

                        @Override
                        public void onError(AccountKitError accountKitError) {

                        }
                    });
                }
            }
        }
    }
}
