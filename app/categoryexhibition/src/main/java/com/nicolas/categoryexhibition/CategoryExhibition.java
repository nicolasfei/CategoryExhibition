package com.nicolas.categoryexhibition;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.nicolas.categoryexhibition.component.ChoiceStatus;
import com.nicolas.categoryexhibition.component.HomeAdapter;
import com.nicolas.categoryexhibition.component.MenuAdapter;
import com.nicolas.categoryexhibition.component.RootAdapter;
import com.nicolas.categoryexhibition.data.Node;
import com.nicolas.categoryexhibition.data.NodeAttr;

import java.util.ArrayList;
import java.util.List;

public class CategoryExhibition extends Dialog {

    private final static String TAG = "CategoryExhibition";

    private Context context;
    private Node tree;          //树结构
    private List<String> rootData = new ArrayList<>();       //一层数据
    private List<Node> menuData = new ArrayList<>();       //二层数据
    private List<Node> homeData = new ArrayList<>();       //三层数据
    private List<Integer> showTitle;

    private ImageView gImageView;
    private TextView gTextView;
    private ImageView rootImageView;
    private Spinner rootView;        //一层数据
    private ListView menuView;       //二层数据
    private ListView homeView;       //三层数据
    private TextView menuTitle;      //二级标签

    private ArrayAdapter<String> rootAdapter;        //一层适配器
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
            lp.gravity = Gravity.BOTTOM;
            lp.width = WindowManager.LayoutParams.MATCH_PARENT;
            lp.height = WindowManager.LayoutParams.MATCH_PARENT;
            window.setAttributes(lp);
        }
        //跟数据
        gImageView = mCategoryDialog.findViewById(R.id.imageView0);
        gImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                switch (tree.getStatus()) {
                    case All:
                        tree.setStatus(ChoiceStatus.None);
                        break;
                    case None:
                    case Part:
                        tree.setStatus(ChoiceStatus.All);
                        break;
                }
                updateRootViewStatus();
                menuAdapter.notifyDataSetChanged();
                homeAdapter.notifyDataSetChanged();
            }
        });
        gTextView = mCategoryDialog.findViewById(R.id.textView);
        //一层数据
        rootImageView = mCategoryDialog.findViewById(R.id.imageView);
        rootImageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int status = 0;
                for (Node node : tree.getSub()) {
                    switch (node.getStatus()) {
                        case Part:
                            status |= 1;
                            break;
                        case None:
                            break;
                        case All:
                            status |= 2;
                            break;
                    }
                }
                switch (status) {
                    case 2:
                        for (Node node : tree.getSub()) {
                            node.setStatus(ChoiceStatus.None);
                        }
                        break;
                    default:
                        for (Node node : tree.getSub()) {
                            node.setStatus(ChoiceStatus.All);
                        }
                        break;
                }
                tree.setStatus(ChoiceStatus.Part);
                updateRootViewStatus();
                menuAdapter.notifyDataSetChanged();
                homeAdapter.notifyDataSetChanged();
            }
        });
        rootView = mCategoryDialog.findViewById(R.id.spinner);
        rootAdapter = new ArrayAdapter<>(this.context, R.layout.simple_spinner_item, rootData);       //new RootAdapter(this.context, rootData);
        rootView.setAdapter(rootAdapter);

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
                Log.d(TAG, "onScrollStateChanged: ");
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem,
                                 int visibleItemCount, int totalItemCount) {
                Log.d(TAG, "onScroll: scrollState " + scrollState);
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_IDLE) {
                    return;
                }
                Log.d(TAG, "onScroll: ------------------------");
                int current = showTitle.indexOf(firstVisibleItem);
//                homeView.setSelection(current);
                if (currentItem != current && current >= 0) {
                    currentItem = current;
                    menuTitle.setText(menuData.get(currentItem).getAttr().getName());
                    menuAdapter.setSelectItem(currentItem);
                    menuAdapter.notifyDataSetInvalidated();
                }
                //滑倒了最下面--------------？？？？？？
                if (Tool.isListViewReachBottomEdge(view)) {  //最后一个item出现，并不是滑倒最底部
//                if (firstVisibleItem + visibleItemCount == totalItemCount){     //最后一个item出现，并不是滑倒最底部
                    currentItem = totalItemCount - 1;
                    menuTitle.setText(menuData.get(currentItem).getAttr().getName());
                    menuAdapter.setSelectItem(currentItem);
                    menuAdapter.notifyDataSetInvalidated();
                }
            }
        });
        //设置home和menu的联动
        homeAdapter.setOnSubNodeChoiceListener(new HomeAdapter.OnSubNodeChoiceListener() {
            @Override
            public void OnSubNodeChoice(Node node, boolean isChoice) {
                menuAdapter.notifyDataSetInvalidated();
                updateRootViewStatus();
            }
        });

        //设置menu和home的联动
        menuAdapter.setOnMenuItemClickListener(new MenuAdapter.OnMenuItemClickListener() {
            @Override
            public void onChoiceChanged(int position, ChoiceStatus status) {
                menuAdapter.notifyDataSetChanged();
                homeAdapter.notifyDataSetChanged();
                updateRootViewStatus();

                //listView调整位置
                menuAdapter.setSelectItem(position);
                menuAdapter.notifyDataSetInvalidated();
                menuTitle.setText(menuData.get(position).getAttr().getName());
                homeView.setSelection(showTitle.get(position));
            }

            @Override
            public void onClick(int position, View v) {
                menuAdapter.setSelectItem(position);
                menuAdapter.notifyDataSetInvalidated();
                menuTitle.setText(menuData.get(position).getAttr().getName());
                homeView.setSelection(showTitle.get(position));
            }
        });
//        menuView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                menuAdapter.setSelectItem(position);
//                menuAdapter.notifyDataSetInvalidated();
//                menuTitle.setText(menuData.get(position).getName());
//                menuView.setSelection(showTitle.get(position));
//            }
//        });

        //设置树分支数据选择
        rootView.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "onItemSelected: ----------------------------------");
                List<Node> nodeList = tree.getSub().get(position).getSub();
                if (nodeList != null) {
                    menuData.clear();
                    homeData.clear();
                    for (Node node : nodeList) {
                        menuData.add(node);             //二层
                        homeData.add(node);             //三层
                    }
                    menuAdapter.notifyDataSetChanged();
                    menuView.setSelection(0);
                    homeAdapter.notifyDataSetChanged();
                    homeView.setSelection(0);
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

    private void updateRootViewStatus() {
        switch (this.tree.getStatus()) {
            case All:
                this.gImageView.setImageDrawable(this.context.getDrawable(R.drawable.ic_checkbox_all));
                break;
            case None:
                this.gImageView.setImageDrawable(this.context.getDrawable(R.drawable.ic_checkbox));
                break;
            case Part:
                this.gImageView.setImageDrawable(this.context.getDrawable(R.drawable.ic_checkbox_part));
                break;
        }
        int status = 0;
        for (Node node : this.tree.getSub()) {
            switch (node.getStatus()) {
                case Part:
                    status |= 1;
                    break;
                case None:
                    break;
                case All:
                    status |= 2;
                    break;
            }
        }
        switch (status) {
            case 2:
                this.rootImageView.setImageDrawable(this.context.getDrawable(R.drawable.ic_checkbox_all));
                break;
            case 0:
                this.rootImageView.setImageDrawable(this.context.getDrawable(R.drawable.ic_checkbox));
                break;
            case 1:
                this.rootImageView.setImageDrawable(this.context.getDrawable(R.drawable.ic_checkbox_part));
                break;
            default:
                this.rootImageView.setImageDrawable(this.context.getDrawable(R.drawable.ic_checkbox_part));
                break;
        }
    }

    private void initData() {
        if (this.tree == null) {
            return;
        }
        NodeAttr rootAttr = this.tree.getAttr();
        this.gTextView.setText(rootAttr.getName());
        List<Node> level1 = this.tree.getSub();
        Log.d(TAG, "initData: level1 size is " + level1.size());
        for (Node node : level1) {
            Log.d(TAG, "initData: root add " + node.getAttr().getName());
            this.rootData.add(node.getAttr().getName());      //一层
            this.rootAdapter.notifyDataSetChanged();
        }

        //默认加载第一层的第一个节点下的分支节点
        if (level1.size() > 0) {
            Node branch = level1.get(0);             //默认加载第一个节点的数据
            Log.d(TAG, "initData: level1 name is " + branch.getAttr().getName() + "size is " + branch.getSub().size());
            int i = 0;
            if (this.showTitle == null) {
                this.showTitle = new ArrayList<>();
            }
            for (Node subBranch : branch.getSub()) {       //二层
                Log.d(TAG, "initData: menuData add " + subBranch.getAttr().getName());
                this.menuData.add(subBranch);
                this.homeData.add(subBranch);              //三层
                this.showTitle.add(i);
                i++;
            }
            this.menuAdapter.notifyDataSetChanged();
            this.homeAdapter.notifyDataSetChanged();
            this.menuTitle.setText(this.menuData.get(0).getAttr().getName());     //设置二级标签
        }
    }

    public void setTreeData(Node tree) {
        this.tree = tree;
        this.initData();
    }

    public void show() {
        this.mCategoryDialog.show();
    }

    public void setOnCategoryChoiceListener(OnCategoryChoiceListener listener) {
        this.listener = listener;
    }

    public interface OnCategoryChoiceListener {
        void OnCategoryChoice(List<NodeAttr> attrs);
    }
}
