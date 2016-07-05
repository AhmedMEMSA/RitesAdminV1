package amemsa.ritesadmin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.support.design.widget.FloatingActionButton;


import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import amemsa.ritesadmin.Adapters.RVAdapter;
import amemsa.ritesadmin.Adapters.RecyclerItemClickListener;
import amemsa.ritesadmin.Adapters.Requests;


public class Home extends AppCompatActivity {

    private RecyclerView fRecyclerView;
    private RVAdapter fAdapter;
    private RecyclerView.LayoutManager fLayoutManager;

    private ArrayList<Requests> data;


    @Override
    protected void onCreate(final Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getSupportActionBar().setTitle("Rites Customer Service");
        data = new ArrayList<>();
        fRecyclerView = (RecyclerView) findViewById(R.id.recycler_view);
        fRecyclerView.setHasFixedSize(true);
        fLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        fRecyclerView.setLayoutManager(fLayoutManager);
        fAdapter = new RVAdapter();
        fRecyclerView.setAdapter(fAdapter);

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               showData();
            }
        });

        fRecyclerView.addOnItemTouchListener(
                new RecyclerItemClickListener(this, new RecyclerItemClickListener.SimpleOnItemClickListener() {
                    @Override
                    public void onItemClick(View childView, int position) {
                        Requests request = data.get(position);
                        Intent i = new Intent(Home.this, Details.class);
                        i.putExtra("request", request);
                        startActivity(i);
                    }

                    @Override
                    public void onItemLongPress(View childView, int position) {
                        super.onItemLongPress(childView, position);

                    }
                }));

    }

    @Override
    protected void onResume() {
        super.onResume();
        showData();


    }

    private void showData() {
        data.clear();
        RequestQueue queue = Volley.newRequestQueue(this);
        final String url = "http://192.168.2.2:8080/Services/resources/service/selectrequests";
        System.out.println(url);
        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        System.out.println("=====================");
                        JSONArray jsonArray = null;
                        try {
                            jsonArray = new JSONArray(response);
                        } catch (JSONException e) {
                            e.printStackTrace();
                            System.out.println("+++++++++++++++++++");

                        }
                        System.out.println(jsonArray);
                        System.out.println("==========================");
                        for (int i = 0; i < jsonArray.length(); i++) {
                            int id = 0;
                            int sender = 0;
                            String message = "";
                            String status = "";
                            String answer = "";
                            JSONObject jsonObject = null;
                            try {
                                jsonObject = jsonArray.getJSONObject(i);
                                id = jsonObject.getInt("id");
                                sender = jsonObject.getInt("sender");
                                message = jsonObject.getString("message");
                                status = jsonObject.getString("status");
                                answer = jsonObject.getString("answer");
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            Requests request = new Requests(id, sender, message, status, answer);
                            data.add(request);
                        }
                        System.out.println(data);
                        fAdapter.setItems(data);
                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(stringRequest);
    }


}

