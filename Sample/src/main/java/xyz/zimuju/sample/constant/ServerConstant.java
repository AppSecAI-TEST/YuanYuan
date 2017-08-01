package xyz.zimuju.sample.constant;

/*
 * @description ServerConstant
 * @author Nathaniel
 * @time 2017/8/1 - 15:43
 * @version 1.0.0
 */
public class ServerConstant {
    public static final String GANK_BASE = "http://gank.io/api/data/Android/10/1";
    /*
     * category 后面可接受参数 all | Android | iOS | 休息视频 | 福利 | 拓展资源 | 前端 | 瞎推荐 | App
     * count 最大 50
     */
    public static final String GANK_SEARCH = "http://gank.io/api/search/query/listview/category/Android/count/10/page/1";
}
