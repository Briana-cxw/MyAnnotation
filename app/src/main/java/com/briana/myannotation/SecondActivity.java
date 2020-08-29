package com.briana.myannotation;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.briana.myannotation.myAnnotation.AnnotationUtils;
import com.briana.myannotation.myAnnotation.GetExtra;
import com.briana.myannotation.myAnnotation.InjectView;
import com.briana.myannotation.myAnnotation.User;

public class SecondActivity extends AppCompatActivity {
    @GetExtra("name")
    String name;
    @GetExtra("age")
    int age;
    @GetExtra("gender")
    String gender;
    @GetExtra("user")
    User user;
    @GetExtra("hoby")
    String[] hoby;
    @InjectView(R.id.tv1)
    TextView textView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_second);

        AnnotationUtils.init(this);


//        textView.setText("姓名："+name+'\n'+"年龄："+age+'\n'+"性别："+gender);
        textView.setText("姓名："+user.getName()+'\n'+"年龄："+user.getAge()+'\n'+"性别："+user.getGender()+'\n'+
                "姓名："+name+'\n'+"年龄："+age+'\n'+"性别："+gender+'\n'+"爱好："+ hoby[0]+hoby[1]+hoby[2]);
    }
}