package xyz.zimuju.sample;

import me.drakeet.multitype.GlobalMultiTypePool;
import xyz.zimuju.sample.entity.content.CategoryList;
import xyz.zimuju.sample.entity.content.Daily;
import xyz.zimuju.sample.entity.content.DailyHeader;
import xyz.zimuju.sample.entity.content.DailyTitle;
import xyz.zimuju.sample.entity.content.GanHuoData;
import xyz.zimuju.sample.entity.content.SearchResult;
import xyz.zimuju.sample.entity.content.XianDuItem;
import xyz.zimuju.sample.module.home.provider.CategoryViewProvider;
import xyz.zimuju.sample.module.home.provider.DailyViewItemHeaderProvider;
import xyz.zimuju.sample.module.home.provider.DailyViewItemProvider;
import xyz.zimuju.sample.module.home.provider.DailyViewItemTitleProvider;
import xyz.zimuju.sample.module.home.provider.DailyViewProvider;
import xyz.zimuju.sample.module.home.provider.GanHuoImageViewProvider;
import xyz.zimuju.sample.module.home.provider.GanHuoTextViewProvider;
import xyz.zimuju.sample.module.home.provider.MeizhiViewProvider;
import xyz.zimuju.sample.module.read.XianDuViewProvider;
import xyz.zimuju.sample.module.search.SearchResultViewProvider;

public class MultiTypeInstaller {
    public static void install() {
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
