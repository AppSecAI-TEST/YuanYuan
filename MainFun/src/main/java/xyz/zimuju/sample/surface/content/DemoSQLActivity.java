package xyz.zimuju.sample.surface.content;

import android.app.Activity;
import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ScrollView;
import android.widget.TextView;

import java.util.List;

import xyz.zimuju.common.base.BaseActivity;
import xyz.zimuju.common.defination.OnBottomDragListener;
import xyz.zimuju.common.util.StringUtils;
import xyz.zimuju.sample.R;
import xyz.zimuju.sample.helper.SQLHelper;

public class DemoSQLActivity extends BaseActivity implements View.OnClickListener, OnBottomDragListener {
    private static final String TAG = "DemoSQLActivity";
    private TextView tvDemoSQLInfo;
    private ScrollView svDemoSQL;
    private TextView tvDemoSQLShow0;
    private TextView tvDemoSQLShow1;
    private EditText etDemoSQLQueryColumn;
    private EditText etDemoSQLQueryValue;
    private EditText etDemoSQLEditColumn;
    private EditText etDemoSQLEditValue;
    private SQLHelper sqlHelper;

    public static Intent createIntent(Context context) {
        return new Intent(context, DemoSQLActivity.class);
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.demo_sql_activity, this);
        initView();
        initData();
        initEvent();
        showShortToast("点击[重置]按钮会恢复数据");
    }

    @Override
    public void initView() {
        tvDemoSQLInfo = (TextView) findViewById(R.id.tvDemoSQLInfo);
        svDemoSQL = (ScrollView) findViewById(R.id.svDemoSQL);
        tvDemoSQLShow0 = (TextView) findViewById(R.id.tvDemoSQLShow0);
        tvDemoSQLShow1 = (TextView) findViewById(R.id.tvDemoSQLShow1);
        etDemoSQLQueryColumn = (EditText) findViewById(R.id.etDemoSQLQueryColumn);
        etDemoSQLQueryValue = (EditText) findViewById(R.id.etDemoSQLQueryValue);
        etDemoSQLEditColumn = (EditText) findViewById(R.id.etDemoSQLEditColumn);
        etDemoSQLEditValue = (EditText) findViewById(R.id.etDemoSQLEditValue);
    }

    private void printAll() {
        runUiThread(new Runnable() {

            @Override
            public void run() {
                etDemoSQLQueryColumn.setText("");
            }
        });
        runThread(TAG + "printAll", new Runnable() {

            @Override
            public void run() {
                print("", getString(sqlHelper));
            }
        });
    }

    private void print(String s) {
        print(StringUtils.getTrimedString(tvDemoSQLShow1), s);
    }

    private void print(final String s0, final String s1) {
        runUiThread(new Runnable() {

            @Override
            public void run() {
                dismissProgressDialog();
                tvDemoSQLShow0.setText("" + s0);
                tvDemoSQLShow1.setText("" + s1);
                svDemoSQL.smoothScrollTo(0, 0);
            }
        });
    }

    @Override
    public void initData() {
        sqlHelper = new SQLHelper(context);
        tvDemoSQLInfo.setText(SQLHelper.TABLE_NAME + "(" + SQLHelper.COLUMN_ID + ", " + SQLHelper.COLUMN_NAME + ", " + SQLHelper.COLUMN_PHONE + ", " + SQLHelper.COLUMN_MAIL + ", " + SQLHelper.COLUMN_OTHER + ")");
        etDemoSQLQueryColumn.setText(SQLHelper.COLUMN_ID);
        etDemoSQLQueryValue.setText("1");
        etDemoSQLEditColumn.setText(SQLHelper.COLUMN_NAME);
        etDemoSQLEditValue.setText("xxx");
        printAll();
    }


    private void reset() {
        showProgressDialog("Resetting...");
        runThread(TAG + "reset", new Runnable() {

            @Override
            public void run() {
                sqlHelper.onUpgrade(sqlHelper.getWritableDatabase(), SQLHelper.TABLE_VERSION, SQLHelper.TABLE_VERSION + 1);

                for (int i = 0; i < 10; i++) {
                    ContentValues values = new ContentValues();
                    values.put(SQLHelper.COLUMN_NAME, "name_" + i);
                    values.put(SQLHelper.COLUMN_PHONE, "" + (13000000 + i * i));
                    values.put(SQLHelper.COLUMN_MAIL, (13000000 + i * i) + "@qq.com");
                    sqlHelper.insert(values);
                }

                runUiThread(new Runnable() {

                    @Override
                    public void run() {
                        printAll();
                    }
                });
            }
        });

    }


    private void insert() {
        showProgressDialog("Inserting...");
        runThread(TAG + "insert", new Runnable() {

            @Override
            public void run() {
                sqlHelper.insert(getContentValues());
                runUiThread(new Runnable() {

                    @Override
                    public void run() {
                        etDemoSQLQueryColumn.setText(StringUtils.getString(etDemoSQLEditColumn));
                        etDemoSQLQueryValue.setText(StringUtils.getString(etDemoSQLEditValue));
                        query();
                    }
                });
            }
        });
    }

    private void delete() {
        showProgressDialog("Deleting...");
        runThread(TAG + "delete", new Runnable() {

            @Override
            public void run() {
                sqlHelper.delete(getQueryColumn(), getQueryValue());
                runUiThread(new Runnable() {

                    @Override
                    public void run() {
                        etDemoSQLQueryColumn.setText("");
                        query();
                    }
                });
            }
        });
    }

    private void update() {
        showProgressDialog("Updating...");
        runThread(TAG + "update", new Runnable() {

            @Override
            public void run() {
                sqlHelper.update(getQueryColumn(), getQueryValue(), getContentValues());
                runUiThread(new Runnable() {

                    @Override
                    public void run() {
                        etDemoSQLQueryColumn.setText(StringUtils.getString(etDemoSQLEditColumn));
                        etDemoSQLQueryValue.setText(StringUtils.getString(etDemoSQLEditValue));
                        query();
                    }
                });
            }
        });
    }

    private void query() {
        showProgressDialog("Querying...");
        runThread(TAG + "query", new Runnable() {

            @Override
            public void run() {
                print(getString(sqlHelper));
            }
        });
    }


    private String getQueryColumn() {
        return StringUtils.getTrimedString(etDemoSQLQueryColumn);
    }

    private String getQueryValue() {
        return StringUtils.getTrimedString(etDemoSQLQueryValue);
    }

    private String getEditColumn() {
        return StringUtils.getTrimedString(etDemoSQLEditColumn);
    }

    private String getEditValue() {
        return StringUtils.getTrimedString(etDemoSQLEditValue);
    }

    private ContentValues getContentValues() {
        ContentValues values = new ContentValues();
        values.put(getQueryColumn(), getQueryValue());
        values.put(getEditColumn(), getEditValue());
        return values;
    }


    private String getString(SQLHelper sqlHelper) {
        List<ContentValues> list = sqlHelper.getList(getQueryColumn(), getQueryValue());
        if (list == null || list.isEmpty()) {
            return "";
        }

        String s = "{\n";
        for (ContentValues values : list) {
            s += getString(values) + ",";
        }
        return s += "\n}";
    }


    private String getString(ContentValues values) {
        if (values == null || values.size() <= 0) {
            return "";
        }

        String s = "{\n";
        for (String key : values.keySet()) {
            s += ("    " + key + ":" + values.get(key) + ",\n");
        }
        return s += "}";
    }

    @Override
    public void initEvent() {//必须在onCreate方法内调用

        findViewById(R.id.btnDemoSQLInsert).setOnClickListener(this);
        findViewById(R.id.btnDemoSQLDelete).setOnClickListener(this);
        findViewById(R.id.btnDemoSQLUpdate).setOnClickListener(this);
        findViewById(R.id.btnDemoSQLQuery).setOnClickListener(this);
    }

    @Override
    public void onDragBottom(boolean rightToLeft) {
        if (rightToLeft) {
            reset();
            return;
        }

        finish();
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btnDemoSQLInsert:
                insert();
                break;
            case R.id.btnDemoSQLDelete:
                delete();
                break;
            case R.id.btnDemoSQLUpdate:
                update();
                break;
            case R.id.btnDemoSQLQuery:
                query();
                break;
            default:
                break;
        }
    }
}