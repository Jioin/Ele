package com.example.youjingjing.myelectronicbalance.utils;

import android.content.ContentValues;
import android.view.View;

import com.example.youjingjing.myelectronicbalance.beans.PLU;
import com.github.promeg.pinyinhelper.Pinyin;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Created by MQ on 2017/5/3.
 */

public class CommonUtil {
    /**
     * 测量View的宽高
     *
     * @param view View
     */
    public static void measureWidthAndHeight(View view) {
        int widthMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        int heightMeasureSpec = View.MeasureSpec.makeMeasureSpec(0, View.MeasureSpec.UNSPECIFIED);
        view.measure(widthMeasureSpec, heightMeasureSpec);
    }

    /**
     * 对数据进行排序
     *
     * @param list 要进行排序的数据源
     */
    public static void sortData(List<PLU> list) {
        if (list == null || list.size() == 0) return;
        ContentValues values = new ContentValues();
        for (int i = 0; i < list.size(); i++) {
            PLU bean = list.get(i);
            String tag = Pinyin.toPinyin(bean.getName().substring(0, 1).charAt(0)).substring(0, 1);
            if(bean.isSticky()){
                bean.setIndexTag("aaaaaa");
            }
            if (tag.matches("[a-z]")) {
                bean.setIndexTag(tag);
                values.put("indextag",tag);
            } else {
                bean.setIndexTag("#");
                values.put("indextag","#");
            }
//            DataSupport.update(PLU.class,values,bean.getId());
        }
        Collections.sort(list, new Comparator<PLU>() {
            @Override
            public int compare(PLU o1, PLU o2) {
                if ("#".equals(o1.getIndexTag())) {
                    return 1;//正数表示比较大，升序，往后排
                } else if ("#".equals(o2.getIndexTag())) {
                    return -1;//负数表示比较小
                } else {
                    return o1.getIndexTag().compareTo(o2.getIndexTag());
                }
            }
        });

    }

    /**
     * @param beans 数据源
     * @return tags 返回一个包含所有Tag字母在内的字符串
     */
    public static String getTags(List<PLU> beans) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < beans.size(); i++) {
            if (!builder.toString().contains(beans.get(i).getIndexTag())) {
                builder.append(beans.get(i).getIndexTag());
            }
        }
        return builder.toString();
    }
}
