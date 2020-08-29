package com.briana.myannotation;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.briana.myannotation.myAnnotation.AnnotationUtils;
import com.briana.myannotation.myAnnotation.InjectView;
import com.briana.myannotation.myAnnotation.OnClick;
import com.briana.myannotation.myAnnotation.User;


public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @InjectView(R.id.tv1)
    TextView tv;
    @InjectView(R.id.button)
    Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        AnnotationUtils.init(this);
        tv.setText("使用注解绑定组件");
    }

    @OnClick(R.id.button)
    public void onClick(View view) {
        User user = new User("briana",21,"女");

        Intent intent = new Intent(this, SecondActivity.class);
        intent.putExtra("name","Briana");
        intent.putExtra("age",21);
        intent.putExtra("gender","女");
        intent.putExtra("hoby",new String[]{"唱歌","跳舞","rap"});
        intent.putExtra("user",user);
        startActivity(intent);
    }

}