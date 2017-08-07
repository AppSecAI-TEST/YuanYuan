package xyz.zimuju.sample.util;

import android.content.Context;
import android.content.Intent;
import android.view.View;

import com.afollestad.materialdialogs.MaterialDialog;
import com.avos.avoscloud.AVObject;
import com.avos.avoscloud.GetCallback;

import xyz.zimuju.sample.R;
import xyz.zimuju.sample.entity.bomb.CollectTable;
import xyz.zimuju.sample.surface.user.LoginActivity;

public class DialogUtils {

    public static void showActionDialog(final Context context, final View itemView, final CollectTable bean) {
        new MaterialDialog.Builder(context)
                .items(R.array.action)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        CharSequence[] action = context.getResources().getTextArray(R.array.action);
                        if (text.equals(action[0])) {
                            SinaShareUtils share = new SinaShareUtils(context);
                            share.setTextObj(bean.getDesc() + "\n" + context.getString(R.string.app_download_url));
                            share.setWebpageObj("来自干货IO的分享", bean.getUrl(), bean.getDesc());
                            share.sendMultiMessage();
                        } else if (text.equals(action[1])) {
                            doCollect(bean, context, itemView);
                        }
                    }
                })
                .show();
    }

    private static void doCollect(CollectTable collectTable, final Context context, final View view) {
        if (AuthorityUtils.isLogin()) {
            collectTable.setUsername(AuthorityUtils.getUserName());
             /*
            SnackBarUtils.makeShort(view, "收藏成功").success();
            RxBus.getInstance().send(new CollectChangeEvent());

            if (e.getErrorCode() == 401) {
                SnackBarUtils.makeShort(view, "你已经收藏过了").info();
            } else {
                SnackBarUtils.makeShort(view, "收藏失败").danger();
            }
             */
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

    public static void showUnDoCollectDialog(final View itemView, final CollectTable bean, final GetCallback<AVObject> getCallback) {
        new MaterialDialog.Builder(itemView.getContext())
                .items(R.array.deleteCollect)
                .itemsCallback(new MaterialDialog.ListCallback() {
                    @Override
                    public void onSelection(MaterialDialog dialog, View view, int which, CharSequence text) {
                        unDoCollect(bean, itemView, getCallback);
                    }
                })
                .show();

    }

    private static void unDoCollect(CollectTable collectTable, View view, GetCallback<AVObject> getCallback) {
        collectTable.deleteInBackground();
    }
}
