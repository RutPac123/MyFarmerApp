package com.example.sai.com;

import android.app.Activity;
import android.support.v7.app.AppCompatActivity;
import android.view.animation.Animation;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.example.sai.myfarmerapp.R;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Weather extends AppCompatActivity {
    private final String apiKey="d6d247b103e3c90e264ca3256aca15a7";
    private final String cityname="Mumbai";
    private RequestQueue requestQueue;

    public void giveWeather(Activity activity, ImageView image, TextView temptxt, TextView unitxt, Animation anime) {
        requestQueue = Volley.newRequestQueue(activity);
        if (cityname.isEmpty()){
            Toast.makeText(activity, "Empty city name!", Toast.LENGTH_SHORT).show();
        }else{
            String url="https://api.openweathermap.org/data/2.5/weather?q=" + cityname + "&appid=" + apiKey;

            final JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    try {
                        JSONObject mainObj = response.getJSONObject("main");
                        JSONArray jsonArray = response.getJSONArray("weather");
                        JSONObject obj = jsonArray.getJSONObject(0);

                        double tempKel = mainObj.getDouble("temp");
                        double tempInt = tempKel - 273.15;
                        tempInt = Math.round(tempInt);
                        int myTempCel = (int) tempInt;
                        String description = obj.getString("description");
                        temptxt.startAnimation(anime);
                        temptxt.setText(String.valueOf(myTempCel));
                        unitxt.startAnimation(anime);
                        unitxt.setText("C");
//                        city.startAnimation(anime);
//                        city.setText(cityname);
//                        descript.startAnimation(anime);
//                        descript.setText(description);

                        switch (description) {
                            case "clear sky":
                                image.startAnimation(anime);
                                image.setImageResource(R.mipmap.sunny);
                                break;
                            case "scattered clouds":
                                image.startAnimation(anime);
                                image.setImageResource(R.mipmap.cloudy);
                                break;
                            case "few clouds":
                                image.startAnimation(anime);
                                image.setImageResource(R.mipmap.few_clouds);
                                break;
                            case "shower rain":
                                image.startAnimation(anime);
                                image.setImageResource(R.mipmap.shower_rain);
                                break;
                            case "rain":
                                image.startAnimation(anime);
                                image.setImageResource(R.mipmap.rain);
                                break;
                            case "thunderstorm":
                                image.startAnimation(anime);
                                image.setImageResource(R.mipmap.thunderstorm);
                                break;
                            case "mist":
                                image.startAnimation(anime);
                                image.setImageResource(R.mipmap.mist);
                                break;
                            case "smoke":
                                image.startAnimation(anime);
                                image.setImageResource(R.mipmap.mist);
                                break;
                            default:
                                image.startAnimation(anime);
                                image.setImageResource(R.mipmap.sunny);
                                break;
                        }

                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(activity, "Error loading data!", Toast.LENGTH_SHORT).show();
                }
            });
            requestQueue.add(request);
        }

    }
}
