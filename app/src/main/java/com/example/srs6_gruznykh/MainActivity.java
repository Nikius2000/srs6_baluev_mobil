package com.example.srs6_baluev;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.ImageView;
import androidx.recyclerview.widget.RecyclerView;
import com.android.volley.RequestQueue;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.squareup.picasso.Picasso;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    TextView city, temp, condition;
    ImageView icon;
    ArrayList<Weather> weatherList = new ArrayList<>();
    WeatherAdapter adapter;
    RecyclerView recyclerView;


    private static final String API_KEY = "YOUR_API_KEY"; // Replace with your API key

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        recyclerView = findViewById(R.id.list);
        city = findViewById(R.id.city);
        temp = findViewById(R.id.temp);
        condition = findViewById(R.id.condition);
        icon = findViewById(R.id.icon);

        getData();
    }

    public void getData() {
        RequestQueue queue = Volley.newRequestQueue(this);
        String URL = "https://api.weatherapi.com/v1/forecast.json?key=" + API_KEY + "&q=London&days=7";

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                try {
                    JSONObject location = response.getJSONObject("location");
                    String name = location.getString("name");

                    JSONObject current = response.getJSONObject("current");
                    JSONObject condition = current.getJSONObject("condition");
                    String text = condition.getString("text");
                    String url = "https:" + condition.getString("icon");

                    Picasso.get().load(url).fit().centerInside().into(icon);
                    condition.get(text);

                    String temp_c = current.getString("temp_c");
                    temp.setText(temp_c);

                    city.setText(name);

                    JSONObject forecast = response.getJSONObject("forecast");
                    JSONArray forecastday = forecast.getJSONArray("forecastday");

                    weatherList.clear(); // Clear the weatherList ArrayList before adding new data

                    for (int i = 0; i < forecastday.length(); i++) {
                        JSONObject forecastDayElement = forecastday.getJSONObject(i);
                        String date = forecastDayElement.getString("date");

                        JSONObject day = forecastDayElement.getJSONObject("day");
                        String maxtemp_c = day.getString("maxtemp_c");

                        JSONObject condition_c = day.getJSONObject("condition");
                        String urlDay = "https:" + condition_c.getString("icon");

                        weatherList.add(new Weather(date, maxtemp_c, urlDay));
                    }

                    adapter = new WeatherAdapter(MainActivity.this, weatherList);
                    recyclerView.setAdapter(adapter);
                } catch (JSONException e) {
                    throw new RuntimeException(e);
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                Log.isLoggable("mylog", Integer.parseInt(error.getMessage()));
            }
        });

        queue.add(request);
    }
}