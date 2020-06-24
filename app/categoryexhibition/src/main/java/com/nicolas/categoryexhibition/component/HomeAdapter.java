package com.nicolas.categoryexhibition.component;

import android.content.Context;
import android.util.Log;
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
    private static final String TAG = "HomeAdapter";
    private Context context;
    private List<Node> foodDatas;
    private OnSubNodeChoiceListener listener;

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
        List<Node> dataList = dataBean.getSub();        //叶子节点List
        ViewHold viewHold;
        if (convertView == null) {
            convertView = View.inflate(context, R.layout.item_home, null);
            viewHold = new ViewHold(convertView);
            convertView.setTag(viewHold);
        } else {
            viewHold = (ViewHold) convertView.getTag();
        }
        HomeItemAdapter adapter = new HomeItemAdapter(context, dataList);
        adapter.setOnItemChoiceListener(new HomeItemAdapter.OnItemChoiceListener() {
            @Override
            public void OnItemChoice(Node node, boolean isChoice) {
                //找到父节点
                Node fNode = null;
                for (Node item : foodDatas) {
                    if (item.getAttr().getId() == node.getAttr().getPid()) {
                        fNode = item;
                        break;
                    }
                }
                if (fNode != null) {
                    if (isChoice) {
                        boolean isAllChoice = true;
                        //判断是否其他子节点都被选中了
                        for (Node item : fNode.getSub()) {
                            if (item.getStatus() != ChoiceStatus.All) {
                                isAllChoice = false;
                                break;
                            }
                        }
                        fNode.setStatus(isAllChoice ? ChoiceStatus.All : ChoiceStatus.Part);
                    } else {
                        boolean isAllNone = true;
                        //判断是否其他子节点都没有被选中
                        for (Node item : fNode.getSub()) {
                            if (item.getStatus() != ChoiceStatus.None) {
                                isAllNone = false;
                                break;
                            }
                        }
                        fNode.setStatus(isAllNone ? ChoiceStatus.None : ChoiceStatus.Part);
                    }
                }
                if (listener != null) {
                    listener.OnSubNodeChoice(node, isChoice);
                }
            }
        });
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

    public void setOnSubNodeChoiceListener(OnSubNodeChoiceListener listener) {
        this.listener = listener;
    }

    public interface OnSubNodeChoiceListener {
        void OnSubNodeChoice(Node node, boolean isChoice);
    }
}
