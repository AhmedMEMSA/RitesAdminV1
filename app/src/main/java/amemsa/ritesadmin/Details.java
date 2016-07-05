package amemsa.ritesadmin;

import android.content.Intent;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONException;
import org.json.JSONObject;

import amemsa.ritesadmin.Adapters.Requests;

public class Details extends AppCompatActivity {

    private TextView requestID;
    private TextView senderID;
    private TextView requestMessage;
    private EditText requestAnswer;
    private Button requestAccept;
    private Button requestReject;
    private Requests request;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        Intent i = getIntent();
        request = i.getParcelableExtra("request");
        requestID = (TextView) findViewById(R.id.request_id);
        senderID = (TextView) findViewById(R.id.sender_id);
        requestMessage = (TextView) findViewById(R.id.request_message);
        requestAnswer = (EditText) findViewById(R.id.request_answer);
        requestAccept = (Button) findViewById(R.id.request_accept);
        requestReject = (Button) findViewById(R.id.request_reject);


        requestID.setText(request.getId()+"");
        senderID.setText(request.getSender()+"");
        requestMessage.setText(request.getMessage());

        requestAccept.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                request.setAnswer(requestAnswer.getText().toString());
                updateRequest("Accepted");
            }
        });
        requestReject.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                request.setAnswer(requestAnswer.getText().toString());
                updateRequest("Rejected");
            }
        });
    }

    private void updateRequest(String status) {
        RequestQueue queue = Volley.newRequestQueue(this);
        final String BASE = "http://192.168.2.2:8080/Services/resources/service/updaterequest";
        Uri uri = Uri.parse(BASE).buildUpon().
                appendQueryParameter("id", request.getId() + "").
                appendQueryParameter("status", status).
                appendQueryParameter("answer", request.getAnswer() + "").
                build();
        String url = uri.toString();
        System.out.println(url);
        StringRequest stringRequest = new StringRequest(com.android.volley.Request.Method.GET, url,
                new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        JSONObject jsonObject;
                        try {
                             jsonObject = new JSONObject(response);
                             if(jsonObject.getInt("result")==1){
                                 finish();
                             }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }


                    }
                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        queue.add(stringRequest);
    }

}