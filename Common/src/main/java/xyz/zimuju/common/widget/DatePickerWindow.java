/*Copyright ©2015 TommyLemon(https://github.com/TommyLemon)

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

    http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.*/

package xyz.zimuju.common.widget;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemSelectedListener;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import xyz.zimuju.common.base.BaseViewBottomWindow;
import xyz.zimuju.common.entity.Entry;
import xyz.zimuju.common.entity.PickerConfig;
import xyz.zimuju.common.util.StringUtils;
import xyz.zimuju.common.util.TimeUtils;

public class DatePickerWindow extends BaseViewBottomWindow<List<Entry<Integer, String>>, GridPickerView> {
    public static final String INTENT_MIN_DATE = "INTENT_MIN_DATE";

    public static final String INTENT_MAX_DATE = "INTENT_MAX_DATE";
    public static final String INTENT_DEFAULT_DATE = "INTENT_DEFAULT_DATE";
    public static final String RESULT_DATE = "RESULT_DATE";
    public static final String RESULT_TIME_IN_MILLIS = "RESULT_TIME_IN_MILLIS";
    public static final String RESULT_DATE_DETAIL_LIST = "RESULT_DATE_DETAIL_LIST";
    private static final String TAG = "DatePickerWindow";
    private List<Entry<Integer, String>> list;
    private int[] minDateDetails;
    private int[] maxDateDetails;

    private int[] defaultDateDetails;
    private ArrayList<PickerConfig> configList;


    private OnItemSelectedListener onItemSelectedListener = new OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> parent, View view, final int position, long id) {
            containerView.doOnItemSelected(containerView.getCurrentTabPosition()
                    , position, containerView.getCurrentSelectedItemName());
            int tabPosition = containerView.getCurrentTabPosition() + 1;
            setPickerView(tabPosition);
        }

        @Override
        public void onNothingSelected(AdapterView<?> parent) {
        }
    };
    private GridPickerView.OnTabClickListener onTabClickListener = new GridPickerView.OnTabClickListener() {

        @Override
        public void onTabClick(int tabPosition, TextView tvTab) {
            setPickerView(tabPosition);
        }
    };

    public static Intent createIntent(Context context, int[] limitYearMonthDay) {
        return createIntent(context, limitYearMonthDay, null);
    }


    public static Intent createIntent(Context context, int[] limitYearMonthDay, int[] defaultYearMonthDay) {
        int[] selectedDate = TimeUtils.getDateDetail(System.currentTimeMillis());
        int[] minYearMonthDay = null;
        int[] maxYearMonthDay = null;
        if (TimeUtils.fomerIsBigger(limitYearMonthDay, selectedDate)) {
            minYearMonthDay = selectedDate;
            maxYearMonthDay = limitYearMonthDay;
        } else {
            minYearMonthDay = limitYearMonthDay;
            maxYearMonthDay = selectedDate;
        }
        return createIntent(context, minYearMonthDay, maxYearMonthDay, defaultYearMonthDay);
    }
    public static Intent createIntent(Context context, int[] minYearMonthDay, int[] maxYearMonthDay, int[] defaultYearMonthDay) {
        return new Intent(context, DatePickerWindow.class).
                putExtra(INTENT_MIN_DATE, minYearMonthDay).
                putExtra(INTENT_MAX_DATE, maxYearMonthDay).
                putExtra(INTENT_DEFAULT_DATE, defaultYearMonthDay);
    }

    @Override
    public Activity getActivity() {
        return this;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        initView();
        initData();
        initEvent();

    }

    @Override
    public void initView() {
        super.initView();

    }

    private void setPickerView(final int tabPosition) {
        runThread(TAG + "setPickerView", new Runnable() {
            @Override
            public void run() {

                final ArrayList<Integer> selectedItemList = new ArrayList<Integer>();
                for (PickerConfig gpcb : configList) {
                    selectedItemList.add(0 + Integer.valueOf(StringUtils.getNumber(gpcb.getSelectedItemName())));
                }

                list = getList(tabPosition, selectedItemList);

                runUiThread(new Runnable() {
                    @Override
                    public void run() {
                        containerView.bindView(tabPosition, list);

                        //防止选中非闰年2月29日
                        if (tabPosition < 2) {
                            ArrayList<String> selectedList = containerView.getSelectedItemList();
                            if (selectedList != null && selectedList.size() >= 3) {

                                if (TimeUtils.isLeapYear(0 + Integer.valueOf(StringUtils.getNumber(selectedList.get(0)))) == false) {
                                    if ("2".equals(StringUtils.getNumber(selectedList.get(1))) && "29".equals(StringUtils.getNumber(selectedList.get(2)))) {
                                        onItemSelectedListener.onItemSelected(null, null, containerView.getCurrentSelectedItemPosition(), 0);
                                    }
                                }
                            }
                        }
                    }
                });
            }
        });
    }

    @Override
    public void initData() {//必须调用
        super.initData();

        intent = getIntent();

        minDateDetails = intent.getIntArrayExtra(INTENT_MIN_DATE);
        maxDateDetails = intent.getIntArrayExtra(INTENT_MAX_DATE);
        defaultDateDetails = intent.getIntArrayExtra(INTENT_DEFAULT_DATE);

        if (minDateDetails == null || minDateDetails.length <= 0) {
            minDateDetails = new int[]{1970, 1, 1};
        }
        if (maxDateDetails == null || maxDateDetails.length <= 0) {
            maxDateDetails = new int[]{2020, 11, 31};
        }
        if (minDateDetails == null || minDateDetails.length <= 0
                || maxDateDetails == null || minDateDetails.length != maxDateDetails.length) {
            finish();
            return;
        }
        if (defaultDateDetails == null || defaultDateDetails.length < 3) {
            defaultDateDetails = TimeUtils.getDateDetail(System.currentTimeMillis());
        }


        runThread(TAG + "initData", new Runnable() {

            @Override
            public void run() {
                final ArrayList<Integer> selectedItemList = new ArrayList<Integer>();
                selectedItemList.add(defaultDateDetails[0]);
                selectedItemList.add(defaultDateDetails[1]);
                selectedItemList.add(defaultDateDetails[2]);

                list = getList(selectedItemList.size() - 1, selectedItemList);

                runUiThread(new Runnable() {

                    @Override
                    public void run() {
                        containerView.init(configList, list);
                    }
                });
            }
        });

    }

    @SuppressLint("ResourceAsColor")
    private synchronized List<Entry<Integer, String>> getList(int tabPosition, ArrayList<Integer> selectedItemList) {
        int level = TimeUtils.LEVEL_YEAR + tabPosition;
        if (selectedItemList == null || selectedItemList.size() != 3 || TimeUtils.isContainLevel(level) == false) {
            return null;
        }

        list = new ArrayList<Entry<Integer, String>>();
        Calendar calendar = Calendar.getInstance();
        calendar.set(selectedItemList.get(0), selectedItemList.get(1) - 1, 1);
        switch (level) {
            case TimeUtils.LEVEL_YEAR:
                for (int i = 0; i < maxDateDetails[0] - minDateDetails[0]; i++) {
                    list.add(new Entry<Integer, String>(GridPickerAdapter.TYPE_CONTNET_ENABLE, String.valueOf(i + 1 + minDateDetails[0])));
                }
                break;
            case TimeUtils.LEVEL_MONTH:
                for (int i = 0; i < 12; i++) {
                    list.add(new Entry<Integer, String>(GridPickerAdapter.TYPE_CONTNET_ENABLE, String.valueOf(i + 1)));
                }
                break;
            case TimeUtils.LEVEL_DAY:
                for (int i = calendar.get(Calendar.DAY_OF_WEEK) - 1; i < 7; i++) {
                    list.add(new Entry<Integer, String>(GridPickerAdapter.TYPE_TITLE, TimeUtils.Day.getDayNameOfWeek(i)));
                }
                for (int i = 0; i < calendar.get(Calendar.DAY_OF_WEEK) - 1; i++) {
                    list.add(new Entry<Integer, String>(GridPickerAdapter.TYPE_TITLE, TimeUtils.Day.getDayNameOfWeek(i)));
                }
                for (int i = 0; i < calendar.getActualMaximum(Calendar.DATE); i++) {
                    list.add(new Entry<Integer, String>(GridPickerAdapter.TYPE_CONTNET_ENABLE, String.valueOf(i + 1)));
                }
                break;
            default:
                break;
        }

        if (configList == null || configList.size() < 3) {
            configList = new ArrayList<PickerConfig>();

            configList.add(new PickerConfig(TimeUtils.NAME_YEAR, "" + selectedItemList.get(0)
                    , selectedItemList.get(0) - 1 - minDateDetails[0], 5, 4));
            configList.add(new PickerConfig(TimeUtils.NAME_MONTH, "" + selectedItemList.get(1)
                    , selectedItemList.get(1) - 1, 4, 3));
            configList.add(new PickerConfig(TimeUtils.NAME_DAY, "" + selectedItemList.get(2)
                    , selectedItemList.get(2) - 1 + 7, 7, 6));
        }

        return list;
    }

    @Override
    public String getTitleName() {
        return "选择日期";
    }

    @Override
    public String getReturnName() {
        return null;
    }

    @Override
    public String getForwardName() {
        return null;
    }


    @Override
    protected GridPickerView createView() {
        return new GridPickerView(context, getResources());
    }

    @Override
    protected void setResult() {
        intent = new Intent();

        List<String> list = containerView.getSelectedItemList();
        if (list != null && list.size() >= 3) {
            ArrayList<Integer> detailList = new ArrayList<Integer>();
            for (int i = 0; i < list.size(); i++) {
                detailList.add(0 + Integer.valueOf(StringUtils.getNumber(list.get(i))));
            }
            detailList.set(1, detailList.get(1) - 1);

            Calendar calendar = Calendar.getInstance();
            calendar.set(detailList.get(0), detailList.get(1), detailList.get(2));
            intent.putExtra(RESULT_TIME_IN_MILLIS, calendar.getTimeInMillis());
            intent.putIntegerArrayListExtra(RESULT_DATE_DETAIL_LIST, detailList);
        }

        setResult(RESULT_OK, intent);
    }

    @Override
    public void initEvent() {
        super.initEvent();

        containerView.setOnTabClickListener(onTabClickListener);
        containerView.setOnItemSelectedListener(onItemSelectedListener);
    }

}