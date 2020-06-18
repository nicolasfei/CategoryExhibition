package com.nicolas.categoryexhibition.component;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.nicolas.categoryexhibition.R;
import com.nicolas.categoryexhibition.data.NodeAttr;

import java.util.List;

public class RootAdapter extends BaseAdapter {

    private Context context;
    private List<NodeAttr> attrs;

    public RootAdapter(Context context, List<NodeAttr> attrs) {
        this.context = context;
        this.attrs = attrs;
    }

    @Override
    public int getCount() {
        return this.attrs == null ? 0 : this.attrs.size();
    }

    @Override
    public Object getItem(int position) {
        return this.attrs == null ? null : this.attrs.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(this.context).inflate(R.layout.item_root, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        NodeAttr attr = this.attrs.get(position);
        holder.textView.setText(attr.getName());

        return convertView;
    }

    private static class ViewHolder {
        private TextView textView;

        private ViewHolder(View root) {
            this.textView = root.findViewById(R.id.rootName);
        }
    }
}
