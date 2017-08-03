package xyz.zimuju.common.util;

import android.annotation.SuppressLint;
import android.content.Context;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import xyz.zimuju.common.R;

public class EnglishButtonUtil {


    private static List<PositionItem> positionList;
    private static Context mContext2;
    private static TextView textview2;
    private static String content2;
    private static ButtonClick inter2;
    private static SpannableString span;


    @SuppressLint("NewApi")
    public static void textViewSpan(Context mContext, TextView textview, String content, ButtonClick inter) {
        mContext2 = mContext;
        textview2 = textview;
        content2 = content;
        inter2 = inter;
        content2 = splitString(content);
        span = SpannableString.valueOf(content2);
        positionList = paresString(content2);
        getpositionglist(mContext2, textview2, inter2, span);
    }

    private static void getpositionglist(Context mContext, TextView textview, ButtonClick inter, SpannableString span) {
        for (int i = 0; i < positionList.size(); i++) {
            PositionItem pi = positionList.get(i);
            span.setSpan(new TextClickSapn(mContext, pi, inter), pi.start, pi.end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            span.setSpan(new ForegroundColorSpan(mContext.getResources().getColor(R.color.common_blue)), pi.start, pi.end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
            span.setSpan(new NoUnderlineSpan(), pi.start, pi.end, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        }

        textview.setText(span);
        textview.setMovementMethod(LinkMovementMethod.getInstance());
    }

    private static String splitString(String content) {
        content = content.replace("<span></span>", "#");
        String[] temp = content.split("#");
        String tempString = "";
        for (int i = 0; i < temp.length; i++) {
            if (i + 1 == temp.length) {
                tempString += temp[i];
                break;
            }
            tempString += temp[i];
            tempString += "  " + "[" + "   " + (i + 1) + "   " + "]" + "  ";
        }
        return tempString;
    }

    public static List<PositionItem> paresString(String content) {
//		String regex = "@[^\\s:：《]+([\\s:：《]|$)|#(.+?)#|(http://|https://){1}[\\w\\.\\-/:]+|\\[(.*?)\\]";
        String regex = "\\[(.*?)\\]";
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(content);
        boolean b = m.find();
        List<PositionItem> list = new ArrayList<PositionItem>();
        int count = 0;
        int lastIndex = 0;
        int index = 0;
        while (b) {
            System.out.println(m.start());
            System.out.println(m.end());
            System.out.println(m.group());
            int start = m.start();
            int end = m.end();
            String str = m.group();
            if (str.startsWith("#")) {
                count++;
                if (count % 2 == 0) {
                    b = m.find(lastIndex);
                    continue;
                }
            }
            list.add(new PositionItem(start, end, str, content.length(), index));
            b = m.find(m.end() - 1);
            try {
                lastIndex = m.start() + 1;
            } catch (Exception e) {
                e.printStackTrace();
            }
            index++;
        }


        return list;
    }

    public static void ChangeView(int CurrentPageIndex, String str) {

        if (EmptyUtil.isNotEmpty(positionList) && CurrentPageIndex < positionList.size()) {
            PositionItem item = positionList.get(CurrentPageIndex);
            String content = item.content;
            item.content = str;
            content2 = content2.replace(content, "[" + (CurrentPageIndex + 1) + " " + str + " ]");
            span = SpannableString.valueOf(content2);
            positionList = paresString(content2);
            getpositionglist(mContext2, textview2, inter2, span);
        }

    }

    public interface ButtonClick {

        void onClick(int index);
    }

    private static class TextClickSapn extends ClickableSpan {
        private PositionItem item;
        private Context mContext;
        private ButtonClick button;

        public TextClickSapn(Context context, PositionItem item, ButtonClick inter) {
            this.item = item;
            this.mContext = context;
            button = inter;
        }

        @Override
        public void onClick(View widget) {
            button.onClick(item.index);
        }


    }

    public static class PositionItem {
        public int start;
        public int end;
        public String content;
        public int index;
        private int prefixType;
        private int strLenght;

        public PositionItem(int start, int end, String content, int strLenght, int index) {
            this.start = start;
            this.end = end;
            this.content = content;
            this.strLenght = strLenght;
            this.index = index;
        }

        public PositionItem(int start, int end, String content) {
            this.start = start;
            this.end = end;
            this.content = content;
        }

        public String getContent() {
            return content;
        }

        public String getContentWithoutPrefix() {
            switch (getPrefixType()) {
                case 1:
                    if (end == strLenght)
                        return content.substring(1, strLenght);
                    return content.substring(1, content.length() - 1);
                case 2:
                    return content.substring(1, content.length() - 1);
                case 3:
                    return content;
                default:
                    return content;
            }
        }

        /**
         * 1 @ 人物 2 # 话题 3 http://t.cn/ 短链 4 [ 表情
         *
         * @return
         */
        public int getPrefixType() {
            if (content.startsWith("@"))
                return 1;
            if (content.startsWith("#"))
                return 2;
            if (content.startsWith("http://"))
                return 3;
            if (content.startsWith("["))
                return 4;
            return -1;
        }
    }

    @SuppressLint("ParcelCreator")
    private static class NoUnderlineSpan extends UnderlineSpan {

        @Override
        public void updateDrawState(TextPaint ds) {
            ds.setUnderlineText(false);
        }
    }

}
