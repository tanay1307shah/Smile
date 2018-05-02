package itp341.shah.tanay.smile;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;



import java.util.ArrayList;
import java.util.List;

/**
 * Created by tanay on 4/24/2018.
 */

public class feelHappy extends AppCompatActivity {

    private final static String API_KEY = "3AhJ5dMBuLH8S0CPIPMPWrdmKjy3rK58";
    private final static String API_URL = "http://api.giphy.com/v1/gifs/search?q=";

    private EditText query;
    private ImageButton send;
    private ListView list;

    private RequestQueue rq;
    private ArrayList<String> gifs;

  //  private GifImageView gif;
    private GIFAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.list_layout);
        Intent i = getIntent();

        query = findViewById(R.id.query);
        send = findViewById(R.id.searchBtn);
        list = findViewById(R.id.list);
       // GifImageView gif = (GifImageView) findViewById(R.id.gif);

        gifs = new ArrayList<>();
        adapter = new GIFAdapter(this,0,gifs);
        list.setAdapter(adapter);

        send.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String q = query.getText().toString().replace(' ','+');
                requestJSONParse(API_URL + q + "&api_key=" + API_KEY + "&limit=5");
            }
        });


        rq = Volley.newRequestQueue(this);
    }


    public void requestJSONParse(String reqURL) {
        String url = reqURL;

        JsonObjectRequest jsonObjectRequest = new JsonObjectRequest
                (Request.Method.GET, url, null, new Response.Listener<JSONObject>() {

                    @Override
                    public void onResponse(JSONObject response) {
                       // Log.d(TAG, response.toString());
                        try {
                            gifs.clear();
                            JSONArray gifJson = response.getJSONArray("data");
                            for(int i=0; i < gifJson.length();i++){
                                JSONObject currGifs = gifJson.getJSONObject(i);
                                JSONObject images = currGifs.getJSONObject("images");
                                JSONObject currImage = images.getJSONObject("downsized");
                                String url = currImage.getString("url");
                                gifs.add(url);
                            }

                            adapter.notifyDataSetChanged();


                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                }, new Response.ErrorListener() {

                    @Override
                    public void onErrorResponse(VolleyError error) {
                        // TODO: Handle error

                    }
                });
        rq.add(jsonObjectRequest);


    }

    private class GIFAdapter extends ArrayAdapter<String> {
        public GIFAdapter(Context context, int resource, List<String> objects){
            super(context, resource, objects);
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if(convertView == null){
                convertView = getLayoutInflater().inflate(R.layout.list_item,null);
            }
            ImageView img = convertView.findViewById(R.id.gif);
            String gifURL = getItem(position);

           // GlideDrawableImageViewTarget imageViewTarget = new GlideDrawableImageViewTarget(img);
            //Glide.with(getApplicationContext()).load(gifURL).into(imageViewTarget);
            Glide.with(getApplicationContext()).load(gifURL).into(img);

            //Picasso.get().load(gifURL).into(img);
            //Uri uri = Uri.parse(gifURL);
            //gif.setGifImageUri(uri);
            return convertView;
        }

    }
}
