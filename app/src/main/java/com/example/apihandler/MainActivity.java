package com.example.apihandler;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class MainActivity extends AppCompatActivity {

    private TextView textview;
    private Button button;
    private RequestQueue request_queue;
    private EditText city;
    private ImageView image;
    private TextView tempo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        textview = findViewById(R.id.Textview);
        button= findViewById(R.id.button);
        request_queue = Volley.newRequestQueue(this);
        city = findViewById(R.id.city);
        image = findViewById(R.id.imageView2);
        tempo=findViewById(R.id.temp);

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                callAPI();
            }
        });



    }

    public void callAPI(){
            String base = "https://api.openweathermap.org/data/2.5/weather?q=";
            String citi = city.getText().toString();
            String id = "&appid=9738271cc02563b8d1eda1742560ff13";

            String url=base+citi+id;


        JsonObjectRequest json= new JsonObjectRequest(Request.Method.GET, url, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                try {
                    String name = response.getString("name");

                    JSONArray weather = response.getJSONArray("weather");
                    JSONObject object = weather.getJSONObject(0);
                    String main = object.getString("main");
                    String additional = object.getString("description");
                    JSONObject values = response.getJSONObject("main");
                    int temp = values.getInt("temp")-273;
                    int humidity = values.getInt("humidity");
                    int realfeel = values.getInt("feels_like")-273;

                textview.setText(name +"\n"+main+"\n"+additional+"\n"+"Feel like :"+realfeel+"\n"
                        + "humidity :"+humidity+" %");

                tempo.setText(temp+"°C");

                if(main.equals("Clear"))
                    image.setImageResource(R.drawable.sunny);
                if(main.equals("Clouds"))
                    image.setImageResource(R.drawable.cloudy);
                if(main.equals("Haze"))
                    image.setImageResource(R.drawable.haze);
                if(main.equals("Rain") || main.equals("Drizzle") )
                    image.setImageResource(R.drawable.rainy);
                if(main.equals("Mist"))
                    image.setImageResource(R.drawable.mist);
                if(main.equals("Thunderstorm"))
                    image.setImageResource(R.drawable.thunderstorm);


                image.setVisibility(View.VISIBLE);


                } catch (JSONException e) {
                    Toast toast = Toast.makeText( getApplicationContext(), "error 1", Toast.LENGTH_LONG);
                    toast.show();
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast toast = Toast.makeText( getApplicationContext(), error.getMessage(), Toast.LENGTH_LONG);
                toast.show();


            }
        });
            request_queue.add(json);

    }


}
