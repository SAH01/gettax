package com.example.tax;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private EditText et_money;
    private TextView tv_result1;
    private TextView tv_result2;
    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        et_money = findViewById(R.id.et_money);
        tv_result1 = findViewById(R.id.tv_result1);
        tv_result2 =findViewById(R.id.tv_result2);
    }
    @Override
    public void onClick(View v){
        Double dou_money=null;
        String money = et_money.getText().toString().trim();
        try {
            dou_money=Double.parseDouble(money);
        }catch (Exception exception){
            Log.v("mess:   ","输入的数据格式有误！");
        }
        double temp=0;      //需要缴税的金额
        if(dou_money!=null){
            if(dou_money<=3000){
                tv_result1.setText(money);
            }
            if(dou_money>3000&&dou_money<=12000){
                temp= (double) ((dou_money-3000)*0.1+3000*0.03);
            }else if(dou_money>12000&&dou_money<=25000){
                temp=(double) (3000*0.03+(9000*0.1+(dou_money-12000)*0.2));
            }else if(dou_money>25000&&dou_money<=35000){
                temp= (double) (3000*0.03+9000*0.1+13000*0.2+(dou_money-25000)*0.25);
            }else if(dou_money>35000&&dou_money<=55000){
                temp= (double) (3000*0.03+9000*0.1+13000*0.2+10000*0.25+(dou_money-35000)*0.3);
            }else if(dou_money>55000&&dou_money<=80000){
                temp= (double) (3000*0.03+9000*0.1+13000*0.2+10000*0.25+20000*0.3+(dou_money-55000)*0.35);
            }else if(dou_money>80000){
                temp= (double) (3000*0.03+9000*0.1+13000*0.2+10000*0.25+20000*0.3+25000*0.35+(dou_money-80000)*0.45);
            }
            double result=(double)(dou_money)-temp;
            String res=String.valueOf(result);
            tv_result1.setText("￥"+res);
            tv_result2.setText("￥"+temp);
        }else{
            tv_result1.setText("您输入的金额格式有误,请重新输入！");
        }
    }
}