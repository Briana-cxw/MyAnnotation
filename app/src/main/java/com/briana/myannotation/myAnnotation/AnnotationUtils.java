package com.briana.myannotation.myAnnotation;

import android.app.Activity;
import android.os.Bundle;
import android.os.Parcelable;
import android.text.TextUtils;
import android.view.View;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

/**
 * @description:
 * @date :2020/8/26 14:43
 */
public class AnnotationUtils {

    public static void init(Activity activity) {
        InjectView(activity);
        OnClick(activity);
        GetExtra(activity);
        GetBean(activity);
    }

    public static void InjectView(Activity activity) {
        Class<? extends Activity> cla = activity.getClass();
        Field[] fields = cla.getDeclaredFields();//获取这个类所有的成员
        for (Field field : fields) {
            //判断属性是否被InjectView注释声明
            if (field.isAnnotationPresent(InjectView.class)) {
                InjectView injectView = field.getAnnotation(InjectView.class);
                int id = injectView.value();
                View view = activity.findViewById(id);
                //反射设置
                field.setAccessible(true);//设置访问权限，允许操作private的属性
                try {
                    field.set(activity, view);//反射赋值
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    //https://blog.csdn.net/qq_35584878/article/details/99717346
    public static void OnClick(final Activity activity) {
        Class<? extends Activity> cla = activity.getClass();
        Method[] methods = cla.getDeclaredMethods();
        for (final Method method : methods) {
            if (method.isAnnotationPresent(OnClick.class)) {
                OnClick onClick = method.getAnnotation(OnClick.class);
                int id = onClick.value();
                View view = activity.findViewById(id);
                view.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View view) {
                        method.setAccessible(true);
                        try {
                            method.invoke(activity, view);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                });
            }
        }
    }

    public static void GetExtra(Activity activity) {
        Class<? extends Activity> cla = activity.getClass();
        Bundle extras = activity.getIntent().getExtras();
        if (extras==null){
            return;
        }
        Field[] fields = cla.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(GetExtra.class)) {
                GetExtra getExtra = field.getAnnotation(GetExtra.class);
                //如果为空，则得到这个属性的名字
                String key = TextUtils.isEmpty(getExtra.value())? field.getName():getExtra.value();
                if (extras.containsKey(key)){
                    Object value = extras.get(key);
                    Class<?> componentType = field.getType().getComponentType();
                    if (field.getType().isArray()&& Parcelable.class.isAssignableFrom(componentType)){
                        Object[] objs = (Object[]) value;
                        Object[] objects = Arrays.copyOf(objs, objs.length, (Class<? extends Object[]>) field.getType());
                        value = objects;
                    }
                    field.setAccessible(true);
                    try {
                        field.set(activity, value);
                    } catch (IllegalAccessException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public static void GetBean(Activity activity) {
        Class<? extends Activity> cla = activity.getClass();
        Field[] fields = cla.getDeclaredFields();
        for (Field field : fields) {
            if (field.isAnnotationPresent(GetBean.class)) {
                GetBean getExtra = field.getAnnotation(GetBean.class);
                String key = getExtra.value();
                User user = activity.getIntent().getParcelableExtra(key);
                field.setAccessible(true);
                try {
                    field.set(activity, user);
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}