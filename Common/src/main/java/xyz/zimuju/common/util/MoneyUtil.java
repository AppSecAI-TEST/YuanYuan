package xyz.zimuju.common.util;

import java.math.BigDecimal;

/*
 * @description MoneyUtil
 * @author Nathaniel - nathanwriting@126.com
 * @time 2017/06/10 - 21:09
 * @version 1.0.0
 */
public class MoneyUtil {

    /**
     * 人民币为分，转为元
     *
     * @param value
     * @return
     */
    public static String getFormatMoney(long value) {
        String result = "0";
        if (value == 0) {
            return result;
        }
        BigDecimal mBigDecimal = bigDecimalRoundDown(value, 100);
        result = getDecimalData(mBigDecimal.toPlainString());
        return result;
    }

    public static String getFormatMoney(long value, double ratio) {
        String result = "0";
        if (value == 0) {
            return result;
        }
        BigDecimal mBigDecimal = bigDecimalRoundDown(value, ratio);
        result = getDecimalData(mBigDecimal.toPlainString());
        return result;
    }

    /**
     * 除法计算保留两位小数
     *
     * @param value
     * @param ratio
     * @return
     */
    public static BigDecimal bigDecimalRoundDown(double value, double ratio) {
        BigDecimal bigDecimal1 = new BigDecimal(value);
        BigDecimal bigDecimal2 = new BigDecimal(ratio);
        return bigDecimal1.divide(bigDecimal2, 2, BigDecimal.ROUND_DOWN);
    }

    public static BigDecimal bigDecimalRoundDown(double value, double ratio, int point) {
        BigDecimal bigDecimal1 = new BigDecimal(value);
        BigDecimal bigDecimal2 = new BigDecimal(ratio);
        return bigDecimal1.divide(bigDecimal2, point, BigDecimal.ROUND_UP);
    }

    /**
     * 小数位数处理，100.00转换为100,100.11仍返回100.11，去掉小数点后面的0
     *
     * @param v
     * @return
     */
    public static String getDecimalData(String v) {
        String result = "";
        if (v == null) {
            return result;
        }

        BigDecimal mBigDecimal = new BigDecimal(v);
        String value = mBigDecimal.toPlainString();
        int pointIndex = value.indexOf(".");
        if (pointIndex == -1) {
            result = value;
        } else {
            int len = value.length();
            String strStart = value.substring(0, pointIndex);
            String strEnd = value.substring(pointIndex + 1, len);

            String strPoint = "";
            boolean isExistZero = false;
            for (int i = len; i >= pointIndex + 1; i--) {
                String strIndex = value.substring(i - 1, i);
                if ("0".equals(strIndex)) {
                    isExistZero = true;
                    strPoint = value.substring(pointIndex + 1, i - 1);
                } else {
                    if (!isExistZero) {
                        strPoint = strEnd;
                    }
                    break;
                }
            }
            if (EmptyUtil.isEmpty(strPoint)) {
                result = strStart;
            } else {
                result = strStart + "." + strPoint;
            }
        }
        return result;
    }

}
