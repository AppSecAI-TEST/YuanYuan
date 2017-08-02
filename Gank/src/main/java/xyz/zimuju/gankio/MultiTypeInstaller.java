package xyz.zimuju.gankio;

import me.drakeet.multitype.GlobalMultiTypePool;
import xyz.zimuju.gankio.bean.CategoryList;
import xyz.zimuju.gankio.bean.Daily;
import xyz.zimuju.gankio.bean.DailyHeader;
import xyz.zimuju.gankio.bean.DailyTitle;
import xyz.zimuju.gankio.bean.GanHuoData;
import xyz.zimuju.gankio.bean.SearchResult;
import xyz.zimuju.gankio.bean.XianDuItem;
import xyz.zimuju.gankio.module.home.provider.CategoryViewProvider;
import xyz.zimuju.gankio.module.home.provider.DailyViewItemHeaderProvider;
import xyz.zimuju.gankio.module.home.provider.DailyViewItemProvider;
import xyz.zimuju.gankio.module.home.provider.DailyViewItemTitleProvider;
import xyz.zimuju.gankio.module.home.provider.DailyViewProvider;
import xyz.zimuju.gankio.module.home.provider.GanHuoImageViewProvider;
import xyz.zimuju.gankio.module.home.provider.GanHuoTextViewProvider;
import xyz.zimuju.gankio.module.home.provider.MeizhiViewProvider;
import xyz.zimuju.gankio.module.read.XianDuViewProvider;
import xyz.zimuju.gankio.module.search.SearchResultViewProvider;

/**
 * Created by _SOLID
 * Date:2016/12/1
 * Time:13:06
 * Desc:
 */

public class MultiTypeInstaller {
    static void install() {
        GlobalMultiTypePool.register(GanHuoData.Text.class, new GanHuoTextViewProvider());
        GlobalMultiTypePool.register(GanHuoData.Image.class, new GanHuoImageViewProvider());
        GlobalMultiTypePool.register(GanHuoData.Meizhi.class, new MeizhiViewProvider());
        GlobalMultiTypePool.register(GanHuoData.DailyItem.class, new DailyViewItemProvider());
        GlobalMultiTypePool.register(DailyTitle.class, new DailyViewItemTitleProvider());
        GlobalMultiTypePool.register(DailyHeader.class, new DailyViewItemHeaderProvider());
        GlobalMultiTypePool.register(SearchResult.class, new SearchResultViewProvider());
        GlobalMultiTypePool.register(CategoryList.class, new CategoryViewProvider());
        GlobalMultiTypePool.register(Daily.class, new DailyViewProvider());
        GlobalMultiTypePool.register(XianDuItem.class, new XianDuViewProvider());
    }
}
