package com.nicolas.categoryexhibition;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.nicolas.categoryexhibition.component.HomeAdapter;
import com.nicolas.categoryexhibition.component.MenuAdapter;
import com.nicolas.categoryexhibition.component.RootAdapter;
import com.nicolas.categoryexhibition.data.Node;
import com.nicolas.categoryexhibition.data.NodeAttr;

import java.util.ArrayList;
import java.util.List;

public class CategoryExhibition extends Dialog {

    private Context context;
    private List<Node> tree;        //树结构
    private List<NodeAttr> rootData = new ArrayList<>();       //一层数据
    private List<NodeAttr> menuData = new ArrayList<>();       //二层数据
    private List<Node> homeData = new ArrayList<>();           //三层数据
    private List<Integer> showTitle;

    private Spinner rootView;        //一层数据
    private ListView menuView;       //二层数据
    private ListView homeView;       //三层数据
    private TextView menuTitle;      //二级标签

    private RootAdapter rootAdapter;        //一层适配器
    private MenuAdapter menuAdapter;        //二层适配器
    private HomeAdapter homeAdapter;        //三层适配器

    private int currentItem;    //当前二层item

    private Dialog mCategoryDialog;
    private OnCategoryChoiceListener listener;

    private List<NodeAttr> choiceNodeAttrs = new ArrayList<>();     //记录选择了那些节点

    public CategoryExhibition(@NonNull Context context) {
        super(context);
        this.context = context;
        initView();
    }

    public CategoryExhibition(@NonNull Context context, int themeResId) {
        super(context, themeResId);
        this.context = context;
        initView();
    }

    private void initView() {
        mCategoryDialog = new Dialog(this.context, R.style.date_picker_dialog);
        mCategoryDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        mCategoryDialog.setContentView(R.layout.dialog_category_exhibition);

        Window window = mCategoryDialog.getWindow();
        if (window != null) {
            WindowManager.LayoutParams lp = window.getAttributes();
            lp.gravity = Gravity.START;
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
            window.setAttributes(lp);
        }
        //根数据
        rootView = mCategoryDialog.findViewById(R.id.spinner);
        rootAdapter = new RootAdapter(this.context, rootData);

        //二层数据由listView的形式展示
        menuView = mCategoryDialog.findViewById(R.id.lv_menu);
        menuTitle = mCategoryDialog.findViewById(R.id.tv_titile);
        menuAdapter = new MenuAdapter(this.context, menuData);
        menuView.setAdapter(menuAdapter);

        //三层数据有listView嵌套gridView的格式展示
        homeView = mCategoryDialog.findViewById(R.id.lv_home);
        homeAdapter = new HomeAdapter(this.context, homeData);
        homeView.setAdapter(homeAdapter);

        //设置三层数据与二层View的联动
        homeView.setOnScrollListener(new AbsListView.OnScrollListener() {
            private int scrollState;

            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                this.scrollState = scrollState;
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    return;
                }
                int current = showTitle.indexOf(firstVisibleItem);
//				lv_home.setSelection(current);
                if (currentItem != current && current >= 0) {
                    currentItem = current;
                    menuTitle.setText(menuData.get(currentItem).getName());
                    menuAdapter.setSelectItem(currentItem);
                    menuAdapter.notifyDataSetInvalidated();
                }
            }
        });

        //设置menu和home的联动
        menuView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                menuAdapter.setSelectItem(position);
                menuAdapter.notifyDataSetInvalidated();
                menuTitle.setText(menuData.get(position).getName());
                menuView.setSelection(showTitle.get(position));
            }
        });

        //设置树分支数据选择
        rootView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                List<Node> nodeList = tree.get(position).getSub();
                if (nodeList != null) {
                    for (Node node : nodeList) {
                        menuData.add(node.getAttr());   //二层
                        menuAdapter.notifyDataSetChanged();
                        menuView.setSelection(0);
                        homeData.add(node);             //三层
                        homeAdapter.notifyDataSetChanged();
                        homeView.setSelection(0);
                    }
                }
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Button cancel = mCategoryDialog.findViewById(R.id.cancel);
        Button submit = mCategoryDialog.findViewById(R.id.submit);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mCategoryDialog.isShowing()) {
                    mCategoryDialog.dismiss();
                }
            }
        });
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (listener != null) {
                    listener.OnCategoryChoice(choiceNodeAttrs);
                }
            }
        });
    }

    private void initData() {
        if (this.tree == null) {
            return;
        }
        for (Node node : this.tree) {
            this.rootData.add(node.getAttr());      //一层
            this.rootAdapter.notifyDataSetChanged();
        }

        if (this.tree.size() > 0) {
            Node nodeL2 = this.tree.get(0);             //默认加载第一个节点的数据
            for (Node nodeL3 : nodeL2.getSub()) {       //二层
                this.menuData.add(nodeL3.getAttr());
            }
            this.menuAdapter.notifyDataSetChanged();

            this.homeData.add(nodeL2);                  //三层
            this.homeAdapter.notifyDataSetChanged();
        }
    }

    public void setTreeData(List<Node> tree) {
        this.tree = tree;
        this.initData();
    }

    public void setOnCategoryChoiceListener(OnCategoryChoiceListener listener) {
        this.listener = listener;
    }

    public interface OnCategoryChoiceListener {
        void OnCategoryChoice(List<NodeAttr> attrs);
    }
}
