package com.nicolas.categoryexhibition;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.nicolas.categoryexhibition.data.Node;
import com.nicolas.categoryexhibition.data.NodeAttr;

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

            Node man = new Node(new NodeAttr(2, 0, "男装", ""));
            Node woman = new Node(new NodeAttr(3, 0, "女装", ""));

            Node clothesMan = new Node(new NodeAttr(4, 2, "衣服", ""));
            Node kuZiMan = new Node(new NodeAttr(5, 2, "裤子", ""));
            Node maoZiMan = new Node(new NodeAttr(6, 2, "帽子", ""));

            Node clothesWoman = new Node(new NodeAttr(7, 3, "衣服", ""));
            Node kuZiWoman = new Node(new NodeAttr(8, 3, "裤子", ""));
            Node qunZiWoman = new Node(new NodeAttr(9, 3, "裙子", ""));


            List<Node> l1 = new ArrayList<>();
            l1.add(man);
            l1.add(woman);

            List<Node> l2 = new ArrayList<>();
            l2.add(clothesMan);
            l2.add(kuZiMan);
            l2.add(maoZiMan);

            List<Node> l2Woman = new ArrayList<>();
            l2Woman.add(clothesWoman);
            l2Woman.add(kuZiWoman);
            l2Woman.add(qunZiWoman);

            int sidW = 7;
            List<Node> l3W = new ArrayList<>();
            for (int id = sidW; id < sidW + 16; id++) {
                l3W.add(new Node(new NodeAttr(id++, clothesMan.getAttr().getId(), "吊带" + id, "")));
                l3W.add(new Node(new NodeAttr(id++, clothesMan.getAttr().getId(), "皮肤衣" + id, "")));
                l3W.add(new Node(new NodeAttr(id, clothesMan.getAttr().getId(), "毛衣" + id, "")));
            }
            sidW += 16;

            List<Node> l3_1W = new ArrayList<>();
            for (int id = sidW; id < sidW + 16; id++) {
                l3_1W.add(new Node(new NodeAttr(id++, kuZiMan.getAttr().getId(), "牛仔裤" + id, "")));
                l3_1W.add(new Node(new NodeAttr(id++, kuZiMan.getAttr().getId(), "超短裤" + id, "")));
                l3_1W.add(new Node(new NodeAttr(id, kuZiMan.getAttr().getId(), "紧身裤" + id, "")));
            }
            sidW += 16;

            List<Node> l3_2W = new ArrayList<>();
            for (int id = sidW; id < sidW + 16; id++) {
                l3_2W.add(new Node(new NodeAttr(id++, maoZiMan.getAttr().getId(), "长裙" + id, "")));
                l3_2W.add(new Node(new NodeAttr(id++, maoZiMan.getAttr().getId(), "短裙" + id, "")));
                l3_2W.add(new Node(new NodeAttr(id, maoZiMan.getAttr().getId(), "超短裙" + id, "")));
            }

            clothesWoman.setSub(l3W);
            kuZiWoman.setSub(l3_1W);
            qunZiWoman.setSub(l3_2W);

            int sid = 7;
            List<Node> l3 = new ArrayList<>();
            for (int id = sid; id < sid + 16; id++) {
                l3.add(new Node(new NodeAttr(id++, clothesMan.getAttr().getId(), "外套" + id, "")));
                l3.add(new Node(new NodeAttr(id++, clothesMan.getAttr().getId(), "衬衫" + id, "")));
                l3.add(new Node(new NodeAttr(id, clothesMan.getAttr().getId(), "短袖" + id, "")));
            }
            sid += 16;

            List<Node> l3_1 = new ArrayList<>();
            for (int id = sid; id < sid + 16; id++) {
                l3_1.add(new Node(new NodeAttr(id++, kuZiMan.getAttr().getId(), "长裤" + id, "")));
                l3_1.add(new Node(new NodeAttr(id++, kuZiMan.getAttr().getId(), "短裤" + id, "")));
                l3_1.add(new Node(new NodeAttr(id, kuZiMan.getAttr().getId(), "七分裤" + id, "")));
            }
            sid += 16;

            List<Node> l3_2 = new ArrayList<>();
            for (int id = sid; id < sid + 16; id++) {
                l3_2.add(new Node(new NodeAttr(id++, maoZiMan.getAttr().getId(), "鸭舌帽" + id, "")));
                l3_2.add(new Node(new NodeAttr(id++, maoZiMan.getAttr().getId(), "雷锋帽" + id, "")));
                l3_2.add(new Node(new NodeAttr(id, maoZiMan.getAttr().getId(), "棒球帽" + id, "")));
            }

            clothesMan.setSub(l3);
            kuZiMan.setSub(l3_1);
            maoZiMan.setSub(l3_2);

            man.setSub(l2);
            woman.setSub(l2Woman);

            all.setSub(l1);

            Node tree = all;
            exhibitionDialog.setTreeData(tree);
        }

        exhibitionDialog.show();
    }
}
