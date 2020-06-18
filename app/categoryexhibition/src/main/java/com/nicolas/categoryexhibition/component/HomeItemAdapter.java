package com.nicolas.categoryexhibition.component;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.facebook.drawee.view.SimpleDraweeView;
import com.nicolas.categoryexhibition.R;
import com.nicolas.categoryexhibition.data.Node;
import com.nicolas.categoryexhibition.data.NodeAttr;

import java.util.List;

public class HomeItemAdapter extends BaseAdapter {

    private Context context;
    private List<Node> foodDatas;

    public HomeItemAdapter(Context context, List<Node> foodDatas) {
        this.context = context;
        this.foodDatas = foodDatas;
    }


    @Override
    public int getCount() {
        return foodDatas == null ? 0 : foodDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return foodDatas == null ? null : foodDatas.size();
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        NodeAttr subcategory = foodDatas.get(position).getAttr();
        ViewHold viewHold = null;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_home_category, null);
            viewHold = new ViewHold();
            viewHold.tv_name = (TextView) convertView.findViewById(R.id.item_home_name);
            viewHold.iv_icon = (SimpleDraweeView) convertView.findViewById(R.id.item_album);
            convertView.setTag(viewHold);
        } else {
            viewHold = (ViewHold) convertView.getTag();
        }
        viewHold.tv_name.setText(subcategory.getName());
        Uri uri = Uri.parse(subcategory.getImgURL());
        viewHold.iv_icon.setImageURI(uri);
        return convertView;
    }

    private static class ViewHold {
        private TextView tv_name;
        private SimpleDraweeView iv_icon;
    }
}
