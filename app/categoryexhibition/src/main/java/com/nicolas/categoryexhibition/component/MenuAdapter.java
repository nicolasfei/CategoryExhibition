package com.nicolas.categoryexhibition.component;

import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nicolas.categoryexhibition.R;
import com.nicolas.categoryexhibition.data.NodeAttr;

import java.util.List;

public class MenuAdapter extends BaseAdapter {

    private Context context;
    private int selectItem = 0;
    private List<NodeAttr> list;

    public MenuAdapter(Context context, List<NodeAttr> list) {
        this.list = list;
        this.context = context;
    }

    public int getSelectItem() {
        return selectItem;
    }

    public void setSelectItem(int selectItem) {
        this.selectItem = selectItem;
    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int arg0) {
        return list == null ? null : list.get(arg0);
    }

    @Override
    public long getItemId(int arg0) {
        return arg0;
    }

    @Override
    public View getView(int arg0, View arg1, ViewGroup arg2) {
        ViewHolder holder;
        if (arg1 == null) {
            arg1 = View.inflate(context, R.layout.item_menu, null);
            holder = new ViewHolder(arg1);
            arg1.setTag(holder);
        } else {
            holder = (ViewHolder) arg1.getTag();
        }
        if (arg0 == selectItem) {
            holder.tv_name.setBackgroundColor(Color.WHITE);
            holder.tv_name.setTextColor(Color.GREEN);
        } else {
            holder.tv_name.setBackgroundColor(Color.GREEN);
            holder.tv_name.setTextColor(Color.BLACK);
        }
        holder.tv_name.setText(list.get(arg0).getName());
        return arg1;
    }

    private static class ViewHolder {
        private TextView tv_name;

        private ViewHolder(View root) {
            this.tv_name = root.findViewById(R.id.item_name);
        }
    }
}
