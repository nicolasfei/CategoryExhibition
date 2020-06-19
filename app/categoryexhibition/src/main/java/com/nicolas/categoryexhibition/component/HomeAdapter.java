package com.nicolas.categoryexhibition.component;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nicolas.categoryexhibition.R;
import com.nicolas.categoryexhibition.data.Node;

import java.util.List;

/**
 * 右侧主界面ListView的适配器
 *
 * @author brush
 */
public class HomeAdapter extends BaseAdapter {

    private Context context;
    private List<Node> foodDatas;

    public HomeAdapter(Context context, List<Node> foodDatas) {
        this.context = context;
        this.foodDatas = foodDatas;
    }

    @Override
    public int getCount() {
        if (foodDatas != null) {
            return foodDatas.size();
        } else {
            return 10;
        }
    }

    @Override
    public Object getItem(int position) {
        return foodDatas.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }


    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Node dataBean = foodDatas.get(position);
        List<Node> dataList = dataBean.getSub();
        ViewHold viewHold;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_home, null);
            viewHold = new ViewHold(convertView);
            convertView.setTag(viewHold);
        } else {
            viewHold = (ViewHold) convertView.getTag();
        }
        HomeItemAdapter adapter = new HomeItemAdapter(context, dataList);
        viewHold.blank.setText(dataBean.getAttr().getName());
        viewHold.gridView.setAdapter(adapter);
        return convertView;
    }

    private static class ViewHold {
        private GridViewForScrollView gridView;
        private TextView blank;

        private ViewHold(View root) {
            this.gridView = root.findViewById(R.id.gridView);
            this.blank = root.findViewById(R.id.blank);
        }
    }
}
