package com.example.my2048project.widget;

import android.app.Dialog;
import android.content.Context;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.my2048project.R;
import com.example.my2048project.utils.LogUtils;
import com.example.my2048project.utils.StringUtils;

import org.w3c.dom.Text;

/**
 * Created by 854638 on 2016/4/20.
 */
public class RankDialog extends Dialog implements View.OnClickListener {

    private EditText mEdtName;

    private Button mBtnSure, mBtnCancel;

    private TextView mTxtScore;

    private OnYesOrNotListener onYesOrNotListener;

    private String mStrScore;

    public void setOnYesOrNotListener(OnYesOrNotListener onYesOrNotListener) {
        this.onYesOrNotListener = onYesOrNotListener;
    }

    public RankDialog(Context context, String score) {
        super(context, R.style.dialog);
        mStrScore = score;
        initView();
        initProcess();
    }

    @Override
    public void onAttachedToWindow() {
        super.onAttachedToWindow();
        Window w = getWindow();
        WindowManager.LayoutParams lp = w.getAttributes();
        DisplayMetrics dm = getContext().getResources().getDisplayMetrics();
        lp.width = dm.widthPixels - 182;
        lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
        w.setAttributes(lp);

    }

    public void initView() {
        setContentView(R.layout.dialog_rank);
        mEdtName = (EditText) findViewById(R.id.edtName);
        mBtnSure = (Button) findViewById(R.id.btnOk);
        mBtnCancel = (Button) findViewById(R.id.btnCancel);
        mTxtScore = (TextView) findViewById(R.id.txtScore);
        Log.i("score", mStrScore);
        if(null != mStrScore && StringUtils.isDigitForm(mStrScore)) {
            mTxtScore.setText(mStrScore + "分");
        }
    }

    public void initProcess() {
        mBtnSure.setOnClickListener(this);
        mBtnCancel.setOnClickListener(this);
        setCancelable(false);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnOk:
                String userName = mEdtName.getText().toString().trim();
                if(null == userName || userName.equals("") || userName.equals("NULL")) {
                    Toast.makeText(getContext(), "请输入有效用户名！", Toast.LENGTH_SHORT).show();
                    break;
                } else {
                    if(null != onYesOrNotListener)
                        dismiss();
                        onYesOrNotListener.onSure(userName);
                    break;
                }
            case R.id.btnCancel:
                if(null != onYesOrNotListener)
                    dismiss();
                    onYesOrNotListener.onCancel();
                break;
            default:
                break;
        }
    }

    public interface OnYesOrNotListener {
        void onSure(String userName);

        void onCancel();
    }
}
