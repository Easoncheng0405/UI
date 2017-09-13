package chengjie.aty;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;

import com.example.chengjie.ui.R;


public class Login extends Activity {
    private EditText passWord, info;
    private Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        context = this;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            getWindow().setStatusBarColor(Color.GRAY);
        }
        setContentView(R.layout.login);
        init();
        findViewById(R.id.btn_login).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(context,MainActivity.class));
                finish();
            }
        });

        findViewById(R.id.iv_show_pwd).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (passWord.getInputType() != InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD)
                    passWord.setInputType(InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                else
                    passWord.setInputType(InputType.TYPE_CLASS_TEXT|InputType.TYPE_TEXT_VARIATION_PASSWORD);
                String pwd = passWord.getText().toString();
                if (!TextUtils.isEmpty(pwd))
                    passWord.setSelection(pwd.length());
            }
        });

        passWord.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (s.toString().isEmpty())
                    return;
                if (!s.toString().matches("[A-Za-z0-9]+")) {
                    String temp = s.toString();
                    s.delete(temp.length() - 1, temp.length());
                    passWord.setSelection(s.length());
                }
            }
        });
    }

    private void init() {
        passWord = (EditText) findViewById(R.id.et_password);
        info = (EditText) findViewById(R.id.et_mobile);

    }
}
