package xyz.zimuju.cloudreader.surface.book.child;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.widget.ImageView;

import rx.Observer;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.schedulers.Schedulers;
import xyz.zimuju.cloudreader.R;
import xyz.zimuju.cloudreader.base.BaseHeaderActivity;
import xyz.zimuju.cloudreader.bean.book.BookDetailBean;
import xyz.zimuju.cloudreader.bean.book.BooksBean;
import xyz.zimuju.cloudreader.databinding.ActivityBookDetailBinding;
import xyz.zimuju.cloudreader.databinding.HeaderBookDetailBinding;
import xyz.zimuju.cloudreader.http.HttpClient;
import xyz.zimuju.cloudreader.util.CommonUtils;
import xyz.zimuju.cloudreader.util.DebugUtil;
import xyz.zimuju.cloudreader.widget.webview.WebViewActivity;

public class BookDetailActivity extends BaseHeaderActivity<HeaderBookDetailBinding, ActivityBookDetailBinding> {

    public final static String EXTRA_PARAM = "bookBean";
    private BooksBean booksBean;
    private String mBookDetailUrl;
    private String mBookDetailName;

    /**
     * @param context      activity
     * @param positionData bean
     * @param imageView    imageView
     */
    public static void start(Activity context, BooksBean positionData, ImageView imageView) {
        Intent intent = new Intent(context, BookDetailActivity.class);
        intent.putExtra(EXTRA_PARAM, positionData);
        ActivityOptionsCompat options =
                ActivityOptionsCompat.makeSceneTransitionAnimation(context,
                        imageView, CommonUtils.getString(R.string.transition_book_img));//与xml文件对应
        ActivityCompat.startActivity(context, intent, options.toBundle());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_book_detail);

        if (getIntent() != null) {
            booksBean = (BooksBean) getIntent().getSerializableExtra(EXTRA_PARAM);
        }

        setMotion(setHeaderPicView(), true);
        initSlideShapeTheme(setHeaderImgUrl(), setHeaderImageView());

        setTitle(booksBean.getTitle());
        setSubTitle("作者：" + booksBean.getAuthor());
        bindingHeaderView.setBooksBean(booksBean);
        bindingHeaderView.executePendingBindings();

        loadBookDetail();
    }

    @Override
    protected int setHeaderLayout() {
        return R.layout.header_book_detail;
    }

    private void loadBookDetail() {
        DebugUtil.error("------http2");
        Subscription get = HttpClient.Builder.getDouBanService().getBookDetail(booksBean.getId())
//        Subscription get = HttpUtils.getInstance().getDouBanServer().getBookDetail(booksBean.getId())
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Observer<BookDetailBean>() {
                    @Override
                    public void onCompleted() {
                        showContentView();
                    }

                    @Override
                    public void onError(Throwable e) {
                        showError();
                    }

                    @Override
                    public void onNext(final BookDetailBean bookDetailBean) {

                        mBookDetailUrl = bookDetailBean.getAlt();
                        mBookDetailName = bookDetailBean.getTitle();
                        bindingContentView.setBookDetailBean(bookDetailBean);
                        bindingContentView.executePendingBindings();
                    }
                });
        addSubscription(get);
    }

    @Override
    protected void setTitleClickMore() {
        WebViewActivity.loadUrl(this, mBookDetailUrl, mBookDetailName);
    }

    @Override
    protected String setHeaderImgUrl() {
        if (booksBean == null) {
            return "";
        }
        return booksBean.getImages().getMedium();
    }

    @Override
    protected ImageView setHeaderImageView() {
        return bindingHeaderView.imgItemBg;
    }

    @Override
    protected ImageView setHeaderPicView() {
        return bindingHeaderView.ivOnePhoto;
    }

    @Override
    protected void onRefresh() {
        loadBookDetail();
    }

}
