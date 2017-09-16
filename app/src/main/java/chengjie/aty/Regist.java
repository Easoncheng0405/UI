package chengjie.aty;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Toast;
import chengjie.base.InputDialog;
import chengjie.base.SureDialog;
import chengjie.jsonResult.baseResult;
import chengjie.util.HttpRequest;
import es.dmoral.toasty.Toasty;
import com.example.chengjie.ui.R;
import com.google.gson.Gson;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Regist extends AppCompatActivity {
    private Context context;
    private ScrollView scrollView;
    private int screenHeight = 0;//屏幕高度
    private int keyHeight = 0; //软件盘弹起后所占高度
    private LinearLayout mContent;
    private RelativeLayout service;
    private EditText phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regist);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            Window window = getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
            window.setStatusBarColor(Color.argb(255, 255, 255, 255));
        }
        context = Regist.this;
        scrollView = (ScrollView) findViewById(R.id.scrollView);
        service = (RelativeLayout) findViewById(R.id.service);
        phone = (EditText) findViewById(R.id.phone);
        phone.requestFocus();
        screenHeight = this.getResources().getDisplayMetrics().heightPixels;
        keyHeight = screenHeight / 3;
        mContent = (LinearLayout) findViewById(R.id.content);
        scrollView.addOnLayoutChangeListener(new ViewGroup.OnLayoutChangeListener() {
            @Override
            public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                if (oldBottom != 0 && bottom != 0 && (oldBottom - bottom > keyHeight)) {
                    ObjectAnimator mAnimatorTranslateY = ObjectAnimator.ofFloat(mContent, "translationY", 0.0f, -250);
                    mAnimatorTranslateY.setDuration(800);
                    mAnimatorTranslateY.setInterpolator(new LinearInterpolator());
                    mAnimatorTranslateY.start();
                    service.setVisibility(View.INVISIBLE);
                } else if (oldBottom != 0 && bottom != 0 && (bottom - oldBottom > keyHeight)) {
                    if ((mContent.getBottom() - oldBottom) > 0) {
                        ObjectAnimator mAnimatorTranslateY = ObjectAnimator.ofFloat(mContent, "translationY", mContent.getTranslationY(), 0);
                        mAnimatorTranslateY.setDuration(800);
                        mAnimatorTranslateY.setInterpolator(new LinearInterpolator());
                        mAnimatorTranslateY.start();
                        service.setVisibility(View.VISIBLE);
                    }
                }
            }
        });

        findViewById(R.id.registButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                regist(phone.getText().toString().trim());
            }
        });

        findViewById(R.id.role1).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final SureDialog sureDialog = new SureDialog(context);
                sureDialog.getTvSure().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        sureDialog.cancel();
                    }
                });
                sureDialog.show();
            }
        });

        findViewById(R.id.role2).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                final SureDialog sureDialog = new SureDialog(context);
                sureDialog.getTvSure().setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        sureDialog.cancel();
                    }
                });
                sureDialog.show();
            }
        });
    }

    private boolean isPhoneNum(String phoneNum) {
        Pattern p = Pattern.compile("^((13[0-9])|(15[^4,\\D])|(18[0,5-9]))\\d{8}$");
        Matcher m = p.matcher(phoneNum);
        return m.matches();
    }

    private void regist(final String num) {
        if (isPhoneNum(num)) {
            if (HttpRequest.isNetworkAvailable(context)) {
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        String res = HttpRequest.request(HttpRequest.URL + "/CheckUserAlreadyExist", "phone=" + num);
                        if (res != null) {
                            Gson gson = new Gson();
                            baseResult result = gson.fromJson(res, baseResult.class);
                            if (result.getCode() == 0) {
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        final InputDialog dialog = new InputDialog(context);
                                        dialog.show();
                                        dialog.setCanceledOnTouchOutside(false);
                                        dialog.show();
                                        dialog.getTvSure().setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                if (dialog.getEditText().getText().toString().equals("123456")) {
                                                    startActivity(new Intent(context, RegistNext.class));
                                                    dialog.dismiss();
                                                } else
                                                    Toasty.error(context, "验证码错误", Toast.LENGTH_SHORT, true).show();
                                            }
                                        });
                                        dialog.getTvCancel().setOnClickListener(new View.OnClickListener() {
                                            @Override
                                            public void onClick(View view) {
                                                dialog.dismiss();
                                            }
                                        });
                                        dialog.start(60);
                                        dialog.getEditText().requestFocus();
                                    }
                                });
                            } else
                                runOnUiThread(new Runnable() {
                                    @Override
                                    public void run() {
                                        Toasty.error(context, "此号码已注册", Toast.LENGTH_SHORT, true).show();
                                    }
                                });

                        } else runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                Toasty.warning(context, "哎呀，发生了一些错误", Toast.LENGTH_SHORT, true).show();
                            }
                        });

                    }
                }).start();
            } else Toasty.error(context, "网络链接不可用，检查您的设置", Toast.LENGTH_SHORT, true).show();
        } else Toasty.error(context, "您输入的电话号码不存在", Toast.LENGTH_SHORT, true).show();
    }
}
