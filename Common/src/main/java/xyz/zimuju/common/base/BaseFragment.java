package xyz.zimuju.common.base;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import xyz.zimuju.common.R;
import xyz.zimuju.common.defination.FragmentPresenter;


public abstract class BaseFragment extends Fragment implements FragmentPresenter {
    protected BaseActivity context = null;
    protected View view = null;
    protected LayoutInflater inflater = null;
    @Nullable
    protected ViewGroup container = null;
    protected Bundle argument = null;
    protected Intent intent = null;
    private boolean isAlive = false;
    private boolean alienable;
    private boolean isRunning = false;
    private int position = -1;

    @Override
    @Nullable
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        context = (BaseActivity) getActivity();
        isAlive = true;

        this.inflater = inflater;
        this.container = container;

        return view;
    }


    public void setContentView(int layoutResID) {
        setContentView(inflater.inflate(layoutResID, container, false));
    }

    public void setContentView(View v) {
        setContentView(v, null);
    }

    public void setContentView(View v, ViewGroup.LayoutParams params) {
        view = v;
    }

    public int getPosition() {
        if (position < 0) {
            argument = getArguments();
            if (argument != null) {
                position = argument.getInt(ARGUMENT_POSITION, position);
            }
        }
        return position;
    }

    public <V extends View> V findViewById(int id) {
        return (V) view.findViewById(id);
    }


    public <V extends View> V findViewById(int id, OnClickListener l) {
        V v = findViewById(id);
        v.setOnClickListener(l);
        return v;
    }

    public Intent getIntent() {
        return context.getIntent();
    }

    public final void runUiThread(Runnable action) {
        if (!isAlive()) {
            return;
        }
        context.runUiThread(action);
    }

    public final Handler runThread(String name, Runnable runnable) {
        if (!isAlive()) {
            return null;
        }
        return context.runThread(name + getPosition(), runnable);//name, runnable);同一Activity出现多个同名Fragment可能会出错
    }

    public void showProgressDialog(int stringResId) {
        if (!isAlive()) {
            return;
        }
        context.showProgressDialog(context.getResources().getString(stringResId));
    }

    public void showProgressDialog(String dialogMessage) {
        if (!isAlive()) {
            return;
        }
        context.showProgressDialog(dialogMessage);
    }

    public void showProgressDialog(String dialogTitle, String dialogMessage) {
        if (!isAlive()) {
            return;
        }
        context.showProgressDialog(dialogTitle, dialogMessage);
    }


    public void dismissProgressDialog() {
        if (!isAlive()) {
            return;
        }
        context.dismissProgressDialog();
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
                    context.overridePendingTransition(R.anim.right_push_in, R.anim.hold);
                } else {
                    context.overridePendingTransition(R.anim.null_anim, R.anim.null_anim);
                }
            }
        });
    }

    public void showShortToast(int stringResId) {
        if (!isAlive()) {
            return;
        }
        context.showShortToast(stringResId);
    }

    public void showShortToast(String string) {
        if (!isAlive()) {
            return;
        }
        context.showShortToast(string);
    }

    public void showShortToast(String string, boolean isForceDismissProgressDialog) {
        if (!isAlive()) {
            return;
        }
        context.showShortToast(string, isForceDismissProgressDialog);
    }


    @Override
    public final boolean isAlive() {
        return isAlive && context != null;
    }

    @Override
    public final boolean isRunning() {
        return isRunning & isAlive();
    }

    @Override
    public void onResume() {
        super.onResume();
        isRunning = true;
    }

    @Override
    public void onPause() {
        super.onPause();
        isRunning = false;
    }


    @Override
    public void onDestroy() {
        dismissProgressDialog();
        if (view != null) {
            try {
                view.destroyDrawingCache();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        isAlive = false;
        isRunning = false;
        view = null;
        inflater = null;
        container = null;
        intent = null;
        argument = null;
        context = null;
        super.onDestroy();
    }
}