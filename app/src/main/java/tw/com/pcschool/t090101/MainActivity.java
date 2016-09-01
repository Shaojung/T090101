package tw.com.pcschool.t090101;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class MainActivity extends AppCompatActivity {
    ListView lv;
    ArrayAdapter<String> adapter;
    String ani[];
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        lv = (ListView) findViewById(R.id.listView);
        RequestQueue queue = Volley.newRequestQueue(MainActivity.this);
        final StringRequest request = new StringRequest("http://data.ntpc.gov.tw/od/data/api/BF90FA7E-C358-4CDA-B579-B6C84ADC96A1?$format=json",
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        // Log.d("NET", response);

                        try {
                            JSONArray array = new JSONArray(response);
                            ani = new String[array.length()];
                            for (int i=0;i<array.length();i++)
                            {
                                JSONObject obj = array.getJSONObject(i);
                                String str = obj.getString("district");
                                ani[i] = str;
                                Log.d("NET", str);
                            }
                            adapter = new ArrayAdapter<String>(MainActivity.this,
                                                    android.R.layout.simple_list_item_1,
                                                    ani);
                            lv.setAdapter(adapter);

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("NET", "ERROR");
            }
        });
        queue.add(request);
        queue.start();

    }
}
