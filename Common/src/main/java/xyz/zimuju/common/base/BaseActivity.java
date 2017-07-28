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

package xyz.zimuju.common.base;

import android.annotation.SuppressLint;
import android.annotation.TargetApi;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.view.GestureDetector;
import android.view.GestureDetector.OnGestureListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.View.OnTouchListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import xyz.zimuju.common.R;
import xyz.zimuju.common.defination.ActivityPresenter;
import xyz.zimuju.common.defination.OnBottomDragListener;
import xyz.zimuju.common.manager.SystemBarTintManager;
import xyz.zimuju.common.manager.ThreadManager;
import xyz.zimuju.common.util.ScreenUtils;
import xyz.zimuju.common.util.StringUtils;

public abstract class BaseActivity extends FragmentActivity implements ActivityPresenter, OnGestureListener {
    protected BaseActivity context = null;
    protected View view = null;
    protected LayoutInflater inflater = null;
    protected FragmentManager fragmentManager = null;
    @Nullable
    protected TextView tvBaseTitle;
    protected Intent intent = null;
    protected int enterAnim = R.anim.fade;
    protected int exitAnim = R.anim.right_push_out;
    protected ProgressDialog progressDialog = null;
    protected List<String> threadNameList;
    private boolean isAlive = false;
    private boolean isRunning = false;
    private OnBottomDragListener onBottomDragListener;
    private GestureDetector gestureDetector;
    private boolean isOnKeyLongPress = false;

    private BroadcastReceiver receiver = new BroadcastReceiver() {
        @Override
        public void onReceive(Context context, Intent intent) {
            String action = intent == null ? null : intent.getAction();
            if (isAlive() == false || StringUtils.isNotEmpty(action, true) == false) {
                return;
            }

            if (ACTION_EXIT_APP.equals(action)) {
                finish();
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);

        context = (BaseActivity) getActivity();
        isAlive = true;
        fragmentManager = getSupportFragmentManager();

        inflater = getLayoutInflater();

        threadNameList = new ArrayList<String>();

        BaseBroadcastReceiver.register(context, receiver, ACTION_EXIT_APP);
    }

    @TargetApi(Build.VERSION_CODES.KITKAT)
    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS, WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        }
        SystemBarTintManager tintManager = new SystemBarTintManager(this);
        tintManager.setStatusBarTintEnabled(true);
        tintManager.setStatusBarTintResource(R.color.topbar_bg);
        tvBaseTitle = (TextView) findViewById(R.id.tvBaseTitle);
    }

    public void setContentView(int layoutResID, OnBottomDragListener listener) {
        setContentView(layoutResID);

        onBottomDragListener = listener;
        gestureDetector = new GestureDetector(this, this);//初始化手势监听类

        view = inflater.inflate(layoutResID, null);
        view.setOnTouchListener(new OnTouchListener() {

            @SuppressLint("ClickableViewAccessibility")
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return gestureDetector.onTouchEvent(event);
            }
        });
    }

    @SuppressWarnings("unchecked")
    public <V extends View> V findViewById(int id, OnClickListener l) {
        V v = (V) findViewById(id);
        v.setOnClickListener(l);
        return v;
    }

    protected void autoSetTitle() {
        tvBaseTitle = autoSetTitle(tvBaseTitle);
    }

    protected TextView autoSetTitle(TextView tvTitle) {
        if (tvTitle != null && StringUtils.isNotEmpty(getIntent().getStringExtra(INTENT_TITLE), false)) {
            tvTitle.setText(StringUtils.getCurrentString());
        }
        return tvTitle;
    }

    /**
     * 展示加载进度条,无标题
     *
     * @param stringResId
     */
    public void showProgressDialog(int stringResId) {
        try {
            showProgressDialog(null, context.getResources().getString(stringResId));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void showProgressDialog(String message) {
        showProgressDialog(null, message);
    }

    public void showProgressDialog(final String title, final String message) {
        runUiThread(new Runnable() {
            @Override
            public void run() {
                if (progressDialog == null) {
                    progressDialog = new ProgressDialog(context);
                }
                if (progressDialog.isShowing()) {
                    progressDialog.dismiss();
                }
                if (StringUtils.isNotEmpty(title, false)) {
                    progressDialog.setTitle(title);
                }
                if (StringUtils.isNotEmpty(message, false)) {
                    progressDialog.setMessage(message);
                }
                progressDialog.setCanceledOnTouchOutside(false);
                progressDialog.show();
            }
        });
    }


    public void dismissProgressDialog() {
        runUiThread(new Runnable() {
            @Override
            public void run() {
                //把判断写在runOnUiThread外面导致有时dismiss无效，可能不同线程判断progressDialog.isShowing()结果不一致
                if (progressDialog == null || progressDialog.isShowing() == false) {
                    return;
                }
                progressDialog.dismiss();
            }
        });
    }

    public void toActivity(Intent intent) {
        toActivity(intent, true);
    }

    public void toActivity(Intent intent, boolean showAnimation) {
        toActivity(intent, -1, showAnimation);
    }

    public void toActivity(Intent intent, int requestCode) {
        toActivity(intent, requestCode, true);
    }

    public void toActivity(final Intent intent, final int requestCode, final boolean showAnimation) {
        runUiThread(new Runnable() {
            @Override
            public void run() {
                if (intent == null) {
                    return;
                }
                if (requestCode < 0) {
                    startActivity(intent);
                } else {
                    startActivityForResult(intent, requestCode);
                }
                if (showAnimation) {
                    overridePendingTransition(R.anim.right_push_in, R.anim.hold);
                } else {
                    overridePendingTransition(R.anim.null_anim, R.anim.null_anim);
                }
            }
        });
    }

    public void showShortToast(int stringResId) {
        try {
            showShortToast(context.getResources().getString(stringResId));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public void showShortToast(String string) {
        showShortToast(string, false);
    }

    public void showShortToast(final String string, final boolean isForceDismissProgressDialog) {
        runUiThread(new Runnable() {
            @Override
            public void run() {
                if (isForceDismissProgressDialog) {
                    dismissProgressDialog();
                }
                Toast.makeText(context, "" + string, Toast.LENGTH_SHORT).show();
            }
        });
    }

    public final void runUiThread(Runnable action) {
        if (isAlive() == false) {
            return;
        }
        runOnUiThread(action);
    }


    public final Handler runThread(String name, Runnable runnable) {
        if (isAlive() == false) {
            return null;
        }
        name = StringUtils.getTrimedString(name);
        Handler handler = ThreadManager.getInstance().runThread(name, runnable);
        if (handler == null) {
            return null;
        }

        if (threadNameList.contains(name) == false) {
            threadNameList.add(name);
        }
        return handler;
    }

    @Override
    public void onReturnClick(View v) {
        if (onBottomDragListener != null) {
            onBottomDragListener.onDragBottom(false);
        } else {
            onBackPressed();
        }
    }

    @Override
    public void onForwardClick(View v) {
        if (onBottomDragListener != null) {
            onBottomDragListener.onDragBottom(true);
        }
    }

    @Override
    public final boolean isAlive() {
        return isAlive && context != null;
    }

    @Override
    public final boolean isRunning() {
        return isRunning & isAlive();
    }


    public void finishWithError(String error) {
        showShortToast(error);
        enterAnim = exitAnim = R.anim.null_anim;
        finish();
    }

    @Override
    public void finish() {
        super.finish();//必须写在最前才能显示自定义动画
        runUiThread(new Runnable() {
            @Override
            public void run() {
                if (enterAnim > 0 && exitAnim > 0) {
                    try {
                        overridePendingTransition(enterAnim, exitAnim);
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        isRunning = true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        isRunning = false;
    }

    @Override
    protected void onDestroy() {
        dismissProgressDialog();
        BaseBroadcastReceiver.unregister(context, receiver);
        ThreadManager.getInstance().destroyThread(threadNameList);
        if (view != null) {
            try {
                view.destroyDrawingCache();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        isAlive = false;
        isRunning = false;
        super.onDestroy();

        inflater = null;
        view = null;
        tvBaseTitle = null;

        fragmentManager = null;
        progressDialog = null;
        threadNameList = null;
        intent = null;
        context = null;
    }

    @Override
    public boolean onKeyLongPress(int keyCode, KeyEvent event) {
        isOnKeyLongPress = true;
        return true;
    }

    @Override
    public boolean onKeyUp(int keyCode, KeyEvent event) {
        if (isOnKeyLongPress) {
            isOnKeyLongPress = false;
            return true;
        }

        switch (keyCode) {
            case KeyEvent.KEYCODE_BACK:
                if (onBottomDragListener != null) {
                    onBottomDragListener.onDragBottom(false);
                    return true;
                }
                break;
            case KeyEvent.KEYCODE_MENU:
                if (onBottomDragListener != null) {
                    onBottomDragListener.onDragBottom(true);
                    return true;
                }
                break;
            default:
                break;
        }

        return super.onKeyUp(keyCode, event);
    }

    @Override
    public boolean onDown(MotionEvent e) {
        return false;
    }

    @Override
    public void onShowPress(MotionEvent e) {
    }

    @Override
    public boolean onSingleTapUp(MotionEvent e) {
        return false;
    }

    @Override
    public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
        return false;
    }

    @Override
    public void onLongPress(MotionEvent e) {
    }

    @Override
    public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
        
        //底部滑动实现同点击标题栏左右按钮效果
        if (onBottomDragListener != null && e1.getRawY() > ScreenUtils.getScreenSize(this)[1] - ((int) getResources().getDimension(R.dimen.bottom_drag_height))) {

            float maxDragHeight = getResources().getDimension(R.dimen.bottom_drag_max_height);
            float distanceY = e2.getRawY() - e1.getRawY();
            if (distanceY < maxDragHeight && distanceY > -maxDragHeight) {

                float minDragWidth = getResources().getDimension(R.dimen.bottom_drag_min_width);
                float distanceX = e2.getRawX() - e1.getRawX();
                if (distanceX > minDragWidth) {
                    onBottomDragListener.onDragBottom(false);
                    return true;
                } else if (distanceX < -minDragWidth) {
                    onBottomDragListener.onDragBottom(true);
                    return true;
                }
            }
        }

        return false;
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (gestureDetector != null) {
            gestureDetector.onTouchEvent(ev);
        }
        return super.dispatchTouchEvent(ev);
    }


}