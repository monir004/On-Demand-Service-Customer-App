package com.dhakasetup.sakib.dhakasetupprototype.model.datamodel;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.dhakasetup.sakib.dhakasetupprototype.SplashActivity;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Data {

    private static Data data = null;

    private List<Category> categories = new ArrayList<>();

    public static synchronized Data getInstance(Context context){
        if (data == null){
            data = new Data();
            Log.d("testdata","new instance, non static");
        }
        return data;
    }

    public List<Category> getData() {
        return categories;
    }

    public void test(){
        for (int i = 0; i < categories.size(); i++){
            Category testCat = categories.get(i);
            for (int j = 0; j < testCat.getSubcats().size(); j++){
                Subcat testSub = testCat.getSubcats().get(j);
                for (int k = 0; k < testSub.getServices().size(); k++){
                    Service testsrv = testSub.getServices().get(k);
                    Log.d("testdata","\n"+testCat.getCat_name()+" ** "+testSub.getSubCat_name()+" ** "+testsrv.getSrvice());
                }
            }
        }
    }

    public  void load(final Context context) {

        StringRequest request = new StringRequest(Request.Method.GET,
                "http://www.dhakasetup.com/api/repo.php",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            List<Category> categoryList = new ArrayList<>();
                            JSONObject rootObject = new JSONObject(response);
                            int categoryCounter = rootObject.getInt("categoryCounter");
                            JSONArray categoryArray = rootObject.getJSONArray("categoryArray");
                            for (int i = 0; i < categoryArray.length(); i++){
                                JSONObject categoryObj = categoryArray.getJSONObject(i);
                                Category category = new Category();
                                category.setCat_id(categoryObj.getString("cat_id"));
                                category.setCat_name(categoryObj.getString("cat_name"));
                                //category.setSer_counter(categoryObj.getInt("serviceCounter"));
                                //Log.d("categoryOld","*** "+ category.getCat_name()+" has "+ category.getSer_counter()+" services ****");
                                List<Subcat> subcatList = new ArrayList<>();
                                JSONArray subcatArray = categoryObj.getJSONArray("subcatArray");

                                for (int j = 0; j< subcatArray.length(); j++){
                                    JSONObject subcatObj = subcatArray.getJSONObject(j);
                                    String subcat_id = subcatObj.getString("subcat_id");
                                    String subcat_name =subcatObj.getString("subcat_name");
                                    Subcat subcat = new Subcat(subcat_id,subcat_name);
                                    List<Service> srvList = new ArrayList<>();
                                    int srvCounter = subcatObj.getInt("srvCounter");
                                    if (srvCounter == 0);
                                        //continue;
                                    //Log.d("srvCounter", "srvCounter: "+srvCounter+" name "+subcat_name);
                                    JSONArray srvArray = subcatObj.getJSONArray("srvArray");

                                    for (int k = 0; k < srvArray.length(); k++){
                                        JSONObject srvObject = srvArray.getJSONObject(k);
                                        String srv_sl = srvObject.getString("srv_sl");
                                        String srvice = srvObject.getString("srvice");
                                        String srvDetails = srvObject.getString("srvDetails");
                                        String srvPrice = srvObject.getString("srvPrice");
                                        String srvImage = srvObject.getString("srvImage");
                                        Service srv = new Service(srv_sl,srvice,srvDetails,srvPrice,srvImage);
                                        srvList.add(srv);
                                    }

                                    subcat.setServices(srvList);
                                    subcatList.add(subcat);
                                }
                                category.setSubcats(subcatList);
                                categoryList.add(category);

                            }

                            categories = categoryList;
                            Log.d("testdata",""+categories.size());
                            SplashActivity.LOAD = true;

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });

        RequestQueue queue = Volley.newRequestQueue(context);
        queue.add(request);
    }


}
