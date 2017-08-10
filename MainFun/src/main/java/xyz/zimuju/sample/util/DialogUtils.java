package xyz.zimuju.sample.util;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;

import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.SaveListener;
import cn.bmob.v3.listener.UpdateListener;
import xyz.zimuju.sample.R;
import xyz.zimuju.sample.entity.bomb.CollectTable;
import xyz.zimuju.sample.event.CollectChangeEvent;
import xyz.zimuju.sample.rx.RxBus;
import xyz.zimuju.sample.surface.user.LoginActivity;


/**
 * Created by _SOLID
 * Date:2016/5/19
 * Time:10:02
 */
public class DialogUtils {

    public static void showActionDialog(final Context context, final View itemView, final CollectTable collectTable) {
        new MaterialDialog.Builder(context)
                .items(R.array.action)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        CharSequence[] action = context.getResources().getTextArray(R.array.action);
                        if (text.equals(action[0])) {
                            SinaShareUtils sinaShareUtils = new SinaShareUtils(context);
                            sinaShareUtils.setTextObj(collectTable.getDesc() + "\n" + context.getString(R.string.app_download_url));
                            sinaShareUtils.setWebpageObj("来自干货IO的分享", collectTable.getUrl(), collectTable.getDesc());
                            sinaShareUtils.sendMultiMessage();
                        } else if (text.equals(action[1])) {
                            doCollect(collectTable, context, itemView);
                        }
                    }
                })
                .show();
    }

    @SuppressWarnings("unchecked")
    private static void doCollect(CollectTable collectTable, final Context context, final View view) {
        if (AuthorityUtils.isLogin()) {
            collectTable.setUsername(AuthorityUtils.getUserName());
            collectTable.save(new SaveListener() {
                @Override
                public void done(Object o, BmobException e) {
                    if (e == null) {
                        SnackBarUtils.makeShort(view, "收藏成功").success();
                        RxBus.getInstance().send(new CollectChangeEvent());
                    } else {
                        if (e.getErrorCode() == 401) {
                            SnackBarUtils.makeShort(view, "你已经收藏过了").info();
                        } else {
                            SnackBarUtils.makeShort(view, "收藏失败").danger();
                        }
                    }
                }
            });
        } else {
            SnackBarUtils.makeLong(view, context.getResources().getString(R.string.mine_no_login))
                    .warning(context.getString(R.string.mine_click_login)
                            , new View.OnClickListener() {
                                @Override
                                public void onClick(View v) {
                                    context.startActivity(new Intent(context, LoginActivity.class));
                                }
                            });
        }
    }

    public static void showUnDoCollectDialog(final View itemView, final CollectTable bean, final UpdateListener updateListener) {
        new MaterialDialog.Builder(itemView.getContext())
                .items(R.array.deleteCollect)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        unDoCollect(bean, itemView, updateListener);
                    }
                })
                .show();

    }

    private static void unDoCollect(CollectTable collectTable, final View view, final UpdateListener updateListener) {
        collectTable.delete(collectTable.getObjectId(), updateListener);
    }
}
