package amemsa.ritesadmin;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class MainActivity extends AppCompatActivity {

    private Button login;
    private EditText id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        login = (Button)findViewById(R.id.login);
        id = (EditText)findViewById(R.id.id);

        getSupportActionBar().hide();


        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (id.getText().toString().equals("1")){
                    startActivity(new Intent(MainActivity.this,Home.class));
                }
                if (id.getText().toString().equals("2")){
                    startActivity(new Intent(MainActivity.this, Manager.class));
                }
            }
        });

    }
}
