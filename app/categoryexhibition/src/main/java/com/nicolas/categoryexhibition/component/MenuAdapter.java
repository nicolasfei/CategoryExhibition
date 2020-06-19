package com.nicolas.categoryexhibition.component;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.google.android.material.chip.Chip;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_menu, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        if (position == selectItem) {
            holder.tv_name.setBackgroundColor(Color.WHITE);
            holder.tv_name.setTextColor(Color.GREEN);
        } else {
            holder.tv_name.setBackgroundColor(Color.GREEN);
            holder.tv_name.setTextColor(Color.BLACK);
        }
        holder.tv_name.setText(list.get(position).getName());
        holder.tv_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(position, v);
                }
            }
        });
        holder.tv_name.setClickable(true);
        holder.chip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if (listener != null) {
                    listener.onChoiceChanged(position, buttonView, isChecked);
                }
            }
        });
        return convertView;
    }

    private static class ViewHolder {
        private TextView tv_name;
        private CheckBox chip;

        private ViewHolder(View root) {
            this.tv_name = root.findViewById(R.id.item_name);
            this.chip = root.findViewById(R.id.chip);
        }
    }

    private OnMenuItemClickListener listener;

    public void setOnMenuItemClickListener(OnMenuItemClickListener listener) {
        this.listener = listener;
    }

    public interface OnMenuItemClickListener {
        void onChoiceChanged(int position, CompoundButton buttonView, boolean isChecked);

        void onClick(int position, View v);
    }
}
