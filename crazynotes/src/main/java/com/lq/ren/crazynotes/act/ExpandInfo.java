package com.lq.ren.crazynotes.act;

import java.util.ArrayList;
import java.util.List;

/**
 * Author lqren on 17/3/13.
 */

public class ExpandInfo {

    private List<String> group;
    private List<List<String>> child;

    private void initData() {
        group = new ArrayList<>();
        child = new ArrayList<>();

    }

    public void addGroup(String str) {
        group.add(str);
    }

    public void addChild(String g, String[] c) {
        if (!group.contains(g)) {
            group.add(g);
        }
        List<String> item = new ArrayList<>();
        for (int i = 0; i < c.length; i++) {
            item.add(c[i]);
        }
        child.add(item);
    }

    public List<String> getGroup() {
        return group;
    }

    public List<List<String>> getChild() {
        return child;
    }
}
