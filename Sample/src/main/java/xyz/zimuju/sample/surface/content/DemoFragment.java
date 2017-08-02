package xyz.zimuju.sample.surface.content;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import xyz.zimuju.common.base.BaseFragment;
import xyz.zimuju.common.entity.Entry;
import xyz.zimuju.sample.R;
import xyz.zimuju.sample.adapter.content.DemoAdapter;


public class DemoFragment extends BaseFragment {
	public static final String ARGUMENT_USER_ID = "ARGUMENT_USER_ID";
	public static final String ARGUMENT_USER_NAME = "ARGUMENT_USER_NAME";
	private static final String TAG = "DemoFragment";
	private long userId = 0;
	private String userName = null;
	private ListView lvDemoFragment;
	private DemoAdapter adapter;
	private List<Entry<String, String>> list;

	public static DemoFragment createInstance(long userId) {
		return createInstance(userId, null);
	}

	public static DemoFragment createInstance(long userId, String userName) {
		DemoFragment fragment = new DemoFragment();

		Bundle bundle = new Bundle();
		bundle.putLong(ARGUMENT_USER_ID, userId);
		bundle.putString(ARGUMENT_USER_NAME, userName);

		fragment.setArguments(bundle);
		return fragment;
	}

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
		super.onCreateView(inflater, container, savedInstanceState);
		//TODO demo_fragment改为你所需要的layout文件
		setContentView(R.layout.demo_fragment);

		argument = getArguments();
		if (argument != null) {
			userId = argument.getLong(ARGUMENT_USER_ID, userId);
			userName = argument.getString(ARGUMENT_USER_NAME, userName);
		}


		initView();
		initData();
		initEvent();


		return view;
	}

	@Override
	public void initView() {
		lvDemoFragment = findViewById(R.id.lvDemoFragment);
	}

	private void setList(List<Entry<String, String>> list) {
		if (adapter == null) {
			adapter = new DemoAdapter(context);
			lvDemoFragment.setAdapter(adapter);
		}
		adapter.refresh(list);
	}

	@Override
	public void initData() {
		showShortToast(TAG + ": userId = " + userId + "; userName = " + userName);
		showProgressDialog(R.string.loading);
		runThread(TAG + "initData", new Runnable() {
			@Override
			public void run() {

				list = getList(userId);
				runUiThread(new Runnable() {
					@Override
					public void run() {
						dismissProgressDialog();
						setList(list);
					}
				});
			}
		});
	}

	protected List<Entry<String, String>> getList(long userId) {
		list = new ArrayList<>();
		for (int i = 0; i < 64; i++) {
			list.add(new Entry<>("联系人" + i, String.valueOf(1311736568 + i * i)));
		}
		return list;
	}

	@Override
	public void initEvent() {
		lvDemoFragment.setOnItemClickListener(new AdapterView.OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
				toActivity(UserActivity.createIntent(context, position));
			}
		});
	}

}