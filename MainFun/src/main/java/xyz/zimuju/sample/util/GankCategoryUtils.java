package xyz.zimuju.sample.util;

import java.util.ArrayList;
import java.util.List;

import xyz.zimuju.sample.entity.gank.Category;

public class GankCategoryUtils {
    public static List<Category> getCategoryList() {
        List<Category> categoryList = new ArrayList<>();
        categoryList.add(new Category("所有"));
        categoryList.add(new Category("福利"));
        categoryList.add(new Category("Android"));
        categoryList.add(new Category("iOS"));
        categoryList.add(new Category("休息视频"));
        categoryList.add(new Category("拓展资源"));
        categoryList.add(new Category("前端"));
        return categoryList;
    }
}
