package com.skyler.calculator;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    TextView tv = null;
    StringBuffer buf = new StringBuffer();
    String result = "";
    static MathUtil ms = new MathUtil(20);

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        tv = (TextView) findViewById(R.id.result);
    }

    public void clickMe(View v) {
        Button btn = (Button) v;
        switch (btn.getId()) {
            case R.id.num_0:
                buf.append(0);
//			str += "0";
//			tv.setText(str);
                break;
            case R.id.num_1:
                buf.append(1);
                break;
            case R.id.num_2:
                buf.append(2);
                break;
            case R.id.num_3:
                buf.append(3);
                break;
            case R.id.num_4:
                buf.append(4);
                break;
            case R.id.num_5:
                buf.append(5);
                break;
            case R.id.num_6:
                buf.append(6);
                break;
            case R.id.num_7:
                buf.append(7);
                break;
            case R.id.num_8:
                buf.append(8);
                break;
            case R.id.num_9:
                buf.append(9);
                break;
            case R.id.num_point:
                if (buf.length() != 0) {
                    char c = buf.charAt(buf.length() - 1);
                    if (c >= '0' && c <= '9') {
                        buf.append(".");
                    }
                }
                break;
            case R.id.brackets_left:
                if (buf.length() == 0) {
                    buf.append("(");
                } else {
                    char c = buf.charAt(buf.length() - 1);
                    if (c == '+' || c == '-' || c == '*' || c == '/' || c == '(') {
                        buf.append("(");
                    }
                }
                break;
            case R.id.brackets_right:
                if (buf.length() != 0) {
                    char c = '\0';
                    int n1 = 0;// '('的个数
                    int n2 = 0;// ')'的个数
                    for (int i = 0; i < buf.length(); i++) {
                        c = buf.charAt(i);
                        if (c == '(') {
                            n1++;
                        } else if (c == ')') {
                            n2++;
                        }
                    }
                    if (n2 < n1 && (c >= '0' && c <= '9' || c == ')')) {
                        buf.append(")");
                    }
                }
                break;
            case R.id.back_space:
                if (buf.length() != 0) {
                    buf.deleteCharAt(buf.length() - 1);
                }
                break;
            case R.id.operator_0:
                buf = new StringBuffer();
                result = "";
                break;
            case R.id.operator_1:
                if (result.length() != 0) {
                    buf = new StringBuffer(result);
                    result = "";
                }
                if (buf.length() == 0) {
                    buf.append("+");
                } else {
                    char c = buf.charAt(buf.length() - 1);
                    if (c >= '0' && c <= '9' || c == ')') {
                        buf.append("+");
                    }
                }
                break;
            case R.id.operator_2:
                if (result.length() != 0) {
                    buf = new StringBuffer(result);
                    result = "";
                }
                if (buf.length() == 0) {
                    buf.append("-");
                } else {
                    int len = buf.length();
                    char c = buf.charAt(len - 1);
                    if (c >= '0' && c <= '9' || c == '(' || c == ')') {
                        buf.append("-");
                    } else if (len >= 2) {
                        c = buf.charAt(len - 2);
                        if (c >= '0' && c <= '9' || c == '(' || c == ')') {
                            buf.append("-");
                        }
                    }
                }
                break;
            case R.id.operator_3:
                if (result.length() != 0) {
                    buf = new StringBuffer(result);
                    result = "";
                }
                if (buf.length() != 0) {
                    char c = buf.charAt(buf.length() - 1);
                    if (c >= '0' && c <= '9' || c == ')') {
                        buf.append("*");
                    }
                }
                break;
            case R.id.operator_4:
                if (result.length() != 0) {
                    buf = new StringBuffer(result);
                    result = "";
                }
                if (buf.length() != 0) {
                    char c = buf.charAt(buf.length() - 1);
                    if (c >= '0' && c <= '9' || c == ')') {
                        buf.append("/");
                    }
                }
                break;
            case R.id.operator_5:
                if (buf.length() != 0) {
                    char c = '\0';
                    int n1 = 0;// '('的个数
                    int n2 = 0;// ')'的个数
                    for (int i = 0; i < buf.length(); i++) {
                        c = buf.charAt(i);
                        if (c == '(') {
                            n1++;
                        } else if (c == ')') {
                            n2++;
                        }
                    }
                    if (n1 == n2 && (c >= '0' && c <= '9' || c == ')')) {
                        buf.append("=");
                        try {
                            result = ms.getResult(buf.toString());
                            buf.append(result);
                        } catch (Exception e1) {
                            buf.append("  IS ERROR");
                        }

                    }
                }
                break;
            default:
                break;
        }
        tv.setText(buf.toString());
    }
}
