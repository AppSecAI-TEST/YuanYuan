

package xyz.zimuju.common.widget;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.EditText;
import android.widget.TextView;

import java.util.List;

import xyz.zimuju.common.R;
import xyz.zimuju.common.base.BaseBottomWindow;
import xyz.zimuju.common.contrant.UserFieldsConstants;
import xyz.zimuju.common.util.CommonUtils;
import xyz.zimuju.common.util.EditTextUtil;
import xyz.zimuju.common.util.StringUtils;


/**
 * 通用编辑个人资料文本界面
 * @use <br> toActivity或startActivityForResult (EditTextInfoWindow.createIntent(...), requestCode);
 * <br> 然后在onActivityResult方法内
 * <br> data.getStringExtra(EditTextInfoWindow.RESULT_EDIT_TEXT_INFO); 可得到输入框内容
 */
public class EditTextInfoWindow extends BaseBottomWindow implements OnClickListener {
    //	private static final String TAG = "EditTextInfoWindow";

    public static final String INTENT_PACKAGE_NAME = "INTENT_PACKAGE_NAME";
    public static final int TYPE_NICK = 200 + UserFieldsConstants.TYPE_NICK;
    public static final int TYPE_NAME = 200 + UserFieldsConstants.TYPE_NAME;
    public static final int TYPE_PHONE = 200 + UserFieldsConstants.TYPE_PHONE;
    public static final int TYPE_WEBSITE = 200 + UserFieldsConstants.TYPE_WEBSITE;
    public static final int TYPE_EMAIL = 200 + UserFieldsConstants.TYPE_EMAIL;


    //UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    public static final int TYPE_FAX = 200 + UserFieldsConstants.TYPE_FAX;
    public static final int TYPE_USUALADDRESS = 200 + UserFieldsConstants.TYPE_USUALADDRESS;
    public static final int TYPE_MAILADDRESS = 200 + UserFieldsConstants.TYPE_MAILADDRESS;
    public static final int TYPE_SCHOOL = 200 + UserFieldsConstants.TYPE_SCHOOL;
    public static final int TYPE_COMPANY = 200 + UserFieldsConstants.TYPE_COMPANY;


    //UI显示区(操作UI，但不存在数据获取或处理代码，也不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //Data数据区(存在数据获取或处理代码，但不存在事件监听代码)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<
    public static final int TYPE_PROFESSION = 200 + UserFieldsConstants.TYPE_PROFESSION;
    public static final int TYPE_NOTE = 200 + UserFieldsConstants.TYPE_NOTE;
    public static final String INTENT_TYPE = "INTENT_TYPE";
    public static final String INTENT_KEY = "INTENT_KEY";
    public static final String INTENT_VALUE = "INTENT_VALUE";
    public static final String RESULT_TYPE = "RESULT_TYPE";
    public static final String RESULT_KEY = "RESULT_KEY";
    public static final String RESULT_VALUE = "RESULT_VALUE";
    public static final String RESULT_URL = "RESULT_URL";
    public static final String RESULT_ID = "RESULT_ID";
    public static final String RESULT_IMAGE_URL = "RESULT_IMAGE_URL";
    public static final int REQUEST_TO_PLACE_PICKER = 11;
    private TextView tvEditTextInfoPlace;
    //	public static final int TYPE_OTHER = 200 + ContactUtil.TYPE_OTHER;
    private EditText etEditTextInfo;
    private View ivEditTextInfoClear;
    private TextView tvEditTextInfoRemind;
    private String packageName;
    private int intentType = 0;
    private int maxEms = 30;
    private String inputedString;
    private String editedValue;


    //Data数据区(存在数据获取或处理代码，但不存在事件监听代码)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //Event事件区(只要存在事件监听代码就是)<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    /**
     * @param context
     * @param key
     * @param value
     * @return
     */
    public static Intent createIntent(Context context, String key, String value) {
        return createIntent(context, key, value, "zuo.biao.library");
    }

    /**
     * @param context
     * @param key
     * @param value
     * @param packageName
     * @return
     */
    public static Intent createIntent(Context context, String key, String value, String packageName) {
        return createIntent(context, 0, key, value, packageName);
    }

    /**
     * @param context
     * @param type
     * @param key
     * @param value
     * @return
     */
    public static Intent createIntent(Context context, int type, String key, String value) {
        return createIntent(context, type, key, value, "zuo.biao.library");
    }

    /**
     * @param context
     * @param type
     * @param key
     * @param value
     * @param packageName type == TYPE_MAILADDRESS || type == TYPE_USUALADDRESS时必须不为空
     * @return
     */
    public static Intent createIntent(Context context, int type, String key, String value, String packageName) {
        return new Intent(context, EditTextInfoWindow.class).
                putExtra(INTENT_TYPE, type).
                putExtra(INTENT_KEY, key).
                putExtra(INTENT_VALUE, value).
                putExtra(INTENT_PACKAGE_NAME, packageName);
    }

    //系统自带监听方法<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.edit_text_info_window);

        //必须调用<<<<<<<<<<<
        initView();
        initData();
        initEvent();
        //必须调用>>>>>>>>>>

    }

    @Override
    public void initView() {//必须调用
        super.initView();

        tvEditTextInfoPlace = (TextView) findViewById(R.id.tvEditTextInfoPlace);
        tvEditTextInfoPlace.setVisibility(View.GONE);

        etEditTextInfo = (EditText) findViewById(R.id.etEditTextInfo);
        ivEditTextInfoClear = findViewById(R.id.ivEditTextInfoClear);
        tvEditTextInfoRemind = (TextView) findViewById(R.id.tvEditTextInfoRemind);
    }

    @Override
    public void initData() {//必须调用
        super.initData();

        intent = getIntent();
        packageName = intent.getStringExtra(INTENT_PACKAGE_NAME);

        intentType = intent.getIntExtra(INTENT_TYPE, 0);
        if (StringUtils.isNotEmpty(intent.getStringExtra(INTENT_KEY), true)) {
            tvBaseTitle.setText(StringUtils.getCurrentString());
        }
        etEditTextInfo.setSingleLine(intentType != TYPE_NOTE);

        switch (intentType) {
            case TYPE_NICK:
                maxEms = 20;
                break;
            case TYPE_PHONE:
                etEditTextInfo.setInputType(InputType.TYPE_CLASS_PHONE);
                maxEms = 11;
                break;
            case TYPE_EMAIL:
                etEditTextInfo.setInputType(InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
                maxEms = 60;
                break;
            case TYPE_WEBSITE:
                etEditTextInfo.setInputType(InputType.TYPE_TEXT_VARIATION_WEB_EDIT_TEXT);
                maxEms = 200;
                break;
            case TYPE_MAILADDRESS:
                maxEms = 60;
                break;
            case TYPE_PROFESSION:
                tvEditTextInfoRemind.setText("所属行业");
                maxEms = 15;
            case TYPE_NOTE:
                maxEms = 100;
                break;
            default:
                break;
        }
        etEditTextInfo.setMaxEms(maxEms);
        tvEditTextInfoRemind.setText("限" + maxEms / 2 + "个字（或" + maxEms + "个字符）");

        if (intentType == TYPE_MAILADDRESS || intentType == TYPE_USUALADDRESS) {
            tvEditTextInfoPlace.setVisibility(View.VISIBLE);
            CommonUtils.toActivity(context, PlacePickerWindow.createIntent(
                    context, packageName, 2), REQUEST_TO_PLACE_PICKER, false);
        }

    }

    @Override
    protected void setResult() {
        intent = new Intent();
        intent.putExtra(RESULT_TYPE, getIntent().getIntExtra(INTENT_TYPE, -1));
        intent.putExtra(RESULT_KEY, getIntent().getStringExtra(INTENT_KEY));
        intent.putExtra(RESULT_VALUE, editedValue);
        setResult(RESULT_OK, intent);
    }

    @Override
    public void initEvent() {//必须调用
        super.initEvent();

        tvEditTextInfoPlace.setOnClickListener(this);

        etEditTextInfo.addTextChangedListener(new TextWatcher() {
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                inputedString = StringUtils.getTrimedString(s);
                if (StringUtils.isNotEmpty(inputedString, true) == false) {
                    ivEditTextInfoClear.setVisibility(View.GONE);
                } else {
                    ivEditTextInfoClear.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void afterTextChanged(Editable s) {
            }
        });
        ivEditTextInfoClear.setOnClickListener(new OnClickListener() {
            @Override
            public void onClick(View v) {
                etEditTextInfo.setText("");
            }
        });

        etEditTextInfo.setText(StringUtils.getTrimedString(getIntent().getStringExtra(INTENT_VALUE)));
        etEditTextInfo.setSelection(StringUtils.getLength(etEditTextInfo, true));

    }

    @Override
    public void onForwardClick(View v) {
        editedValue = StringUtils.getTrimedString(tvEditTextInfoPlace) + StringUtils.getTrimedString(etEditTextInfo);
        if (editedValue.equals("" + getIntent().getStringExtra(INTENT_VALUE))) {
            CommonUtils.showShortToast(context, "内容没有改变哦~");
            return;
        }
        super.onForwardClick(v);
    }


    //类相关监听<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<

    @Override
    public void onClick(View v) {
        if (v.getId() == R.id.tvEditTextInfoPlace) {
            CommonUtils.toActivity(context, PlacePickerWindow.createIntent(
                    context, packageName, 2), REQUEST_TO_PLACE_PICKER, false);
        }
    }

    @Override
    public void finish() {
        super.finish();
        EditTextUtil.hideKeyboard(context, etEditTextInfo);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK) {
            return;
        }
        switch (requestCode) {
            case REQUEST_TO_PLACE_PICKER:
                List<String> list = data == null ? null : data.getStringArrayListExtra(PlacePickerWindow.RESULT_PLACE_LIST);
                if (list == null || list.size() < 2) {
                    CommonUtils.showShortToast(context, "请先选择地址哦~");
                    CommonUtils.toActivity(context, PlacePickerWindow.createIntent(
                            context, packageName, 2), REQUEST_TO_PLACE_PICKER, false);
                    return;
                }
                String place = "";
                for (String s : list) {
                    place += s;
                }
                tvEditTextInfoPlace.setText(place);
                break;
            default:
                break;
        }
    }


    //类相关监听>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

    //系统自带监听方法>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //Event事件区(只要存在事件监听代码就是)>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>


    //内部类,尽量少用<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<<


    //内部类,尽量少用>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>

}