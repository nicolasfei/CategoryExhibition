package com.nicolas.categoryexhibition;

import android.view.View;
import android.widget.AbsListView;
import android.widget.ListView;

public class Tool {

    //判断listView滑动到最后一个Item的内容的底部（即listview不能再向下滑)
    public static boolean isListViewReachBottomEdge(final AbsListView listView) {
        boolean result = false;
        if (listView.getLastVisiblePosition() == (listView.getCount() - 1)) {
            final View bottomChildView = listView.getChildAt(listView.getLastVisiblePosition() - listView.getFirstVisiblePosition());
            result = (listView.getHeight() >= bottomChildView.getBottom());
        };
        return result;
    }

    //判断listView滑动到最后一个Item的内容的顶部
    public static boolean isListViewReachTopEdge(final ListView listView) {
        boolean result = false;
        if (listView.getFirstVisiblePosition() == 0) {
            final View topChildView = listView.getChildAt(0);
            result = topChildView.getTop() == 0;
        }
        return result;
    }

}
