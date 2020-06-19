package com.nicolas.categoryexhibition;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.nicolas.categoryexhibition.data.Node;
import com.nicolas.categoryexhibition.data.NodeAttr;
import com.nicolas.categoryexhibition.data.TreeUtil;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private CategoryExhibition exhibitionDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Button textView = findViewById(R.id.textView);
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCategoryExhibitionDialog();
            }
        });
    }

    private void showCategoryExhibitionDialog() {
        if (exhibitionDialog == null) {
            exhibitionDialog = new CategoryExhibition(this);
            Node all = new Node(new NodeAttr(1, 0, "综合", ""));

            Node man = new Node(new NodeAttr(2, 1, "男装", ""));
            Node woman = new Node(new NodeAttr(3, 1, "女装", ""));

            Node clothes = new Node(new NodeAttr(4, 2, "衣服", ""));
            Node kuZi = new Node(new NodeAttr(5, 2, "裤子", ""));
            Node qunZi = new Node(new NodeAttr(6, 2, "裙子", ""));

            Node waiTao = new Node(new NodeAttr(7, 3, "外套", ""));
            Node chenSan = new Node(new NodeAttr(8, 3, "衬衫", ""));
            Node duanXiu = new Node(new NodeAttr(9, 3, "短袖", ""));
            Node waiTao1 = new Node(new NodeAttr(7, 3, "外套1", ""));
            Node chenSan1 = new Node(new NodeAttr(8, 3, "衬衫1", ""));
            Node duanXiu1 = new Node(new NodeAttr(9, 3, "短袖1", ""));
            Node waiTao2 = new Node(new NodeAttr(7, 3, "外套2", ""));
            Node chenSan2 = new Node(new NodeAttr(8, 3, "衬衫2", ""));
            Node duanXiu2 = new Node(new NodeAttr(9, 3, "短袖2", ""));
            Node waiTao3 = new Node(new NodeAttr(7, 3, "外套3", ""));
            Node chenSan3 = new Node(new NodeAttr(8, 3, "衬衫3", ""));
            Node duanXiu3 = new Node(new NodeAttr(9, 3, "短袖3", ""));

            Node changKu = new Node(new NodeAttr(10, 3, "长裤", ""));
            Node duanKu = new Node(new NodeAttr(11, 3, "短裤", ""));
            Node zhongKu = new Node(new NodeAttr(12, 3, "中裤", ""));
            Node changKu1 = new Node(new NodeAttr(10, 3, "长裤", ""));
            Node duanKu1 = new Node(new NodeAttr(11, 3, "短裤", ""));
            Node zhongKu1 = new Node(new NodeAttr(12, 3, "中裤", ""));
            Node changKu2 = new Node(new NodeAttr(10, 3, "长裤", ""));
            Node duanKu2 = new Node(new NodeAttr(11, 3, "短裤", ""));
            Node zhongKu2 = new Node(new NodeAttr(12, 3, "中裤", ""));
            Node changKu3 = new Node(new NodeAttr(10, 3, "长裤", ""));
            Node duanKu3 = new Node(new NodeAttr(11, 3, "短裤", ""));
            Node zhongKu3 = new Node(new NodeAttr(12, 3, "中裤", ""));


            Node duanQun = new Node(new NodeAttr(13, 3, "短裙", ""));
            Node changQun = new Node(new NodeAttr(14, 3, "长裙", ""));
            Node chaoDQ = new Node(new NodeAttr(15, 3, "超短裙", ""));
            Node duanQun1 = new Node(new NodeAttr(13, 3, "短裙", ""));
            Node changQun1 = new Node(new NodeAttr(14, 3, "长裙", ""));
            Node chaoDQ1 = new Node(new NodeAttr(15, 3, "超短裙", ""));
            Node duanQun2 = new Node(new NodeAttr(13, 3, "短裙", ""));
            Node changQun2 = new Node(new NodeAttr(14, 3, "长裙", ""));
            Node chaoDQ2 = new Node(new NodeAttr(15, 3, "超短裙", ""));

            List<Node> l1 = new ArrayList<>();
            l1.add(man);
            l1.add(woman);

            List<Node> l2 = new ArrayList<>();
            l2.add(clothes);
            l2.add(kuZi);
            l2.add(qunZi);

            List<Node> l3 = new ArrayList<>();
            l3.add(waiTao);
            l3.add(chenSan);
            l3.add(duanXiu);
            l3.add(waiTao1);
            l3.add(chenSan1);
            l3.add(duanXiu1);
            l3.add(waiTao2);
            l3.add(chenSan2);
            l3.add(duanXiu2);
            l3.add(waiTao3);
            l3.add(chenSan3);
            l3.add(duanXiu3);

            List<Node> l3_1 = new ArrayList<>();
            l3_1.add(changKu);
            l3_1.add(duanKu);
            l3_1.add(zhongKu);
            l3_1.add(changKu1);
            l3_1.add(duanKu1);
            l3_1.add(zhongKu1);
            l3_1.add(changKu2);
            l3_1.add(duanKu2);
            l3_1.add(zhongKu2);
            l3_1.add(changKu3);
            l3_1.add(duanKu3);
            l3_1.add(zhongKu3);

            List<Node> l3_2 = new ArrayList<>();
            l3_2.add(duanQun);
            l3_2.add(changQun);
            l3_2.add(chaoDQ);
            l3_2.add(duanQun1);
            l3_2.add(changQun1);
            l3_2.add(chaoDQ1);
            l3_2.add(duanQun2);
            l3_2.add(changQun2);
            l3_2.add(chaoDQ2);

            clothes.setSub(l3);
            kuZi.setSub(l3_1);
            qunZi.setSub(l3_2);

            man.setSub(l2);
            woman.setSub(l2);

            all.setSub(l1);


//            List<Node> nodes = new ArrayList<>();
//            nodes.add(all);
//            nodes.add(man);
//            nodes.add(woman);
//
//            nodes.add(clothes);
//            nodes.add(kuZi);
//            nodes.add(qunZi);
//
//            nodes.add(waiTao);
//            nodes.add(chenSan);
//            nodes.add(duanXiu);
//
//            nodes.add(changKu);
//            nodes.add(duanKu);
//            nodes.add(zhongKu);
//
//            nodes.add(duanQun);
//            nodes.add(changQun);
//            nodes.add(chaoDQ);
//
//            List<Node> tree = TreeUtil.buildTree(nodes);
//            System.out.println(JSON.toJSONString(tree));
//
            List<Node> tree = new ArrayList<>();
            tree.add(all);
            exhibitionDialog.setTreeData(tree);
        }

        exhibitionDialog.show();
    }
}
