package xyz.zimuju.common.widget;

import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import xyz.zimuju.common.R;
import xyz.zimuju.common.util.StringUtils;

public class AlertDialog extends Dialog implements View.OnClickListener {
    private Context context;
    private String title;
    private String message;
    private String strPositive;
    private String strNegative;
    private boolean showNegativeButton = true;
    private int requestCode;
    private OnDialogButtonClickListener listener;
    private TextView tvTitle;
    private TextView tvMessage;
    private Button btnPositive;
    private Button btnNegative;

    /**
     * 带监听器参数的构造函数
     */
    public AlertDialog(Context context, String title, String message, boolean showNegativeButton, int requestCode, OnDialogButtonClickListener listener) {
        super(context, R.style.MyDialog);

        this.context = context;
        this.title = title;
        this.message = message;
        this.showNegativeButton = showNegativeButton;
        this.requestCode = requestCode;
        this.listener = listener;
    }

    public AlertDialog(Context context, String title, String message, boolean showNegativeButton,
                       String strPositive, int requestCode, OnDialogButtonClickListener listener) {
        super(context, R.style.MyDialog);

        this.context = context;
        this.title = title;
        this.message = message;
        this.showNegativeButton = showNegativeButton;
        this.strPositive = strPositive;
        this.requestCode = requestCode;
        this.listener = listener;
    }

    public AlertDialog(Context context, String title, String message,
                       String strPositive, String strNegative, int requestCode, OnDialogButtonClickListener listener) {
        super(context, R.style.MyDialog);

        this.context = context;
        this.title = title;
        this.message = message;
        this.strPositive = strPositive;
        this.strNegative = strNegative;
        this.requestCode = requestCode;
        this.listener = listener;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.alert_dialog);
        setCanceledOnTouchOutside(true);

        tvTitle = (TextView) findViewById(R.id.tvAlertDialogTitle);
        tvMessage = (TextView) findViewById(R.id.tvAlertDialogMessage);
        btnPositive = (Button) findViewById(R.id.btnAlertDialogPositive);
        btnNegative = (Button) findViewById(R.id.btnAlertDialogNegative);

        tvTitle.setVisibility(StringUtils.isNotEmpty(title, true) ? View.VISIBLE : View.GONE);
        tvTitle.setText(StringUtils.getCurrentString());

        if (StringUtils.isNotEmpty(strPositive, true)) {
            btnPositive.setText(StringUtils.getCurrentString());
        }
        btnPositive.setOnClickListener(this);

        if (showNegativeButton) {
            if (StringUtils.isNotEmpty(strNegative, true)) {
                btnNegative.setText(StringUtils.getCurrentString());
            }
            btnNegative.setOnClickListener(this);
        } else {
            btnNegative.setVisibility(View.GONE);
        }

        tvMessage.setText(StringUtils.getTrimedString(message));
    }

    @Override
    public void onClick(final View v) {
        if (v.getId() == R.id.btnAlertDialogPositive) {
            listener.onDialogButtonClick(requestCode, true);
        } else if (v.getId() == R.id.btnAlertDialogNegative) {
            listener.onDialogButtonClick(requestCode, false);
        }

        dismiss();
    }

    public interface OnDialogButtonClickListener {

        void onDialogButtonClick(int requestCode, boolean isPositive);
    }

}

