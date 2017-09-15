package chengjie.base;

import android.app.Activity;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.example.chengjie.ui.R;


public class RxDialogEditSureCancel extends RxDialog {

    private TextView mTvSure;
    private TextView mTvCancel;
    private TextView mTvContent;
    private EditText editText;
    private TextView mTvTitle;
    private TextView time;
    private Context context;
    private Activity activityContext;
    public void setTitle(String title) {
        mTvTitle.setText(title);
    }

    public TextView getTvTitle() {
        return mTvTitle;
    }

    public EditText getEditText() {
        return editText;
    }

    public TextView getTvContent() {
        return mTvContent;
    }

    public void setContent(String content) {
        this.mTvContent.setText(content);
    }

    public TextView getTvSure() {
        return mTvSure;
    }

    public void setSure(String strSure) {
        this.mTvSure.setText(strSure);
    }


    public TextView getTvCancel() {
        return mTvCancel;
    }

    public void setCancel(String strCancel) {
        this.mTvCancel.setText(strCancel);
    }

    public TextView getTime() {
        return time;
    }

    public void setTime(TextView time) {
        this.time = time;
    }

    private void initView() {
        View dialog_view = LayoutInflater.from(getContext()).inflate(R.layout.dialog_edittext_sure_false, null);
        mTvTitle = (TextView) dialog_view.findViewById(R.id.dialog_title);
        mTvSure = (TextView) dialog_view.findViewById(R.id.tv_sure);
        mTvCancel = (TextView) dialog_view.findViewById(R.id.tv_cancle);
        editText = (EditText) dialog_view.findViewById(R.id.editText);
        mTvContent = (TextView) dialog_view.findViewById(R.id.tv_title);
        time=(TextView)dialog_view.findViewById(R.id.time);
        time.setVisibility(TextView.INVISIBLE);
        setContentView(dialog_view);
    }

    public TextView getTileTextView() {
        return this.mTvTitle;
    }



    public RxDialogEditSureCancel(Context context) {
        super(context);
        initView();
        this.context=context;
        this.activityContext=(Activity)context;
    }


    public void start(final int waitTime){
        time.setVisibility(TextView.VISIBLE);
        new Thread(new Runnable() {
            @Override
            public void run() {
                for(int i=waitTime;i>=0;i--){
                    try {
                        Thread.sleep(1000);
                        final int t=i;
                        activityContext.runOnUiThread(new Runnable() {
                            @Override
                            public void run() {
                                time.setText("重新发送("+t+") ");
                                if(t==0){
                                    time.setText("重新发送 ");
                                    time.setTextColor(Color.argb(255,255,105,180));
                                }
                            }
                        });
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }
        }).start();
    }






}
