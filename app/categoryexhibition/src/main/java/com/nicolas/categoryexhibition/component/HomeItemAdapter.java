package com.nicolas.categoryexhibition.component;

import android.content.Context;
import android.net.Uri;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.CompoundButton;

import com.nicolas.categoryexhibition.R;
import com.nicolas.categoryexhibition.data.Node;
import com.nicolas.categoryexhibition.data.NodeAttr;

import java.util.List;

public class HomeItemAdapter extends BaseAdapter {

    private Context context;
    private List<Node> foodDatas;
    private OnItemChoiceListener listener;

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
        Node node = foodDatas.get(position);
        NodeAttr subcategory = node.getAttr();
        ViewHold viewHold;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_home_category, null);
            viewHold = new ViewHold(convertView);
            convertView.setTag(viewHold);
        } else {
            viewHold = (ViewHold) convertView.getTag();
        }
        viewHold.chip.setText(subcategory.getName());
        viewHold.chip.setChecked(node.getStatus() == ChoiceStatus.All);
        viewHold.chip.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                node.setStatus(isChecked ? ChoiceStatus.All : ChoiceStatus.None);
                if (listener != null) {
                    listener.OnItemChoice(node, isChecked);
                }
            }
        });
        Uri uri = Uri.parse(subcategory.getImgURL());
//        viewHold.iv_icon.setImageURI(uri);
        return convertView;
    }

    private static class ViewHold {
        private CheckBox chip;

        private ViewHold(View root) {
            this.chip = root.findViewById(R.id.chip);
        }
    }

    public void setOnItemChoiceListener(OnItemChoiceListener listener) {
        this.listener = listener;
    }

    public interface OnItemChoiceListener {
        void OnItemChoice(Node node, boolean isChoice);
    }
}
