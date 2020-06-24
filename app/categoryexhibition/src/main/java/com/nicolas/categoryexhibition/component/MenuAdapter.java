package com.nicolas.categoryexhibition.component;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nicolas.categoryexhibition.R;
import com.nicolas.categoryexhibition.data.Node;

import java.util.List;

public class MenuAdapter extends BaseAdapter {

    private Context context;
    private int selectItem = 0;
    private List<Node> list;

    public MenuAdapter(Context context, List<Node> list) {
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

        final Node node = list.get(position);

        holder.tv_name.setText(node.getAttr().getName());
        holder.tv_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.onClick(position, v);
                }
            }
        });
        holder.tv_name.setClickable(true);
        switch (node.getStatus()) {
            case All:
                holder.chip.setImageDrawable(context.getDrawable(R.drawable.ic_checkbox_all));
                break;
            case None:
                holder.chip.setImageDrawable(context.getDrawable(R.drawable.ic_checkbox));
                break;
            case Part:
                holder.chip.setImageDrawable(context.getDrawable(R.drawable.ic_checkbox_part));
                break;
            default:
                break;
        }
        holder.chip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (node.getStatus()) {
                    case All:
                        node.setStatus(ChoiceStatus.None);
                        holder.chip.setImageDrawable(context.getDrawable(R.drawable.ic_checkbox));
                        break;
                    case None:
                    case Part:
                        holder.chip.setImageDrawable(context.getDrawable(R.drawable.ic_checkbox_all));
                        node.setStatus(ChoiceStatus.All);
                        break;
                    default:
                        break;
                }

                if (listener != null) {
                    listener.onChoiceChanged(position, node.getStatus());
                }
            }
        });
        return convertView;
    }

    private static class ViewHolder {
        private TextView tv_name;
        private ImageView chip;

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
        void onChoiceChanged(int position, ChoiceStatus status);

        void onClick(int position, View v);
    }
}
