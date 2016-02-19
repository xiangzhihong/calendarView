package com.xzh.calendarview;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

public class FirstActivity extends Activity {

    private Button btn;
    private TextView tv_in, tv_out;
    SharedPreferences sp;
    String inday, outday;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.first_activity);
        sp = getSharedPreferences("date", Context.MODE_PRIVATE);
        btn = (Button) findViewById(R.id.btn);
        tv_in = (TextView) findViewById(R.id.tv_in);
        tv_out = (TextView) findViewById(R.id.tv_out);
        btn.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Intent intent = new Intent(FirstActivity.this, MainActivity.class);
                startActivity(intent);
            }
        });

    }

    @Override
    protected void onStart() {
        super.onStart();
        inday = sp.getString("dateIn", "");
        outday = sp.getString("dateOut", "");
        if (!"".equals(inday)) {
            tv_in.setText(inday);
        }
        if (!"".equals(outday)) {
            tv_out.setText(outday);
        }
    }

}
