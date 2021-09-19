package ir.fanniherfei.tolid;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class BillObj {
    public int index;
    public List<BillNode> nodes;
    public BillObj(int index){
        this.index = index;
        nodes = new ArrayList<>();
    }
    public void put(BillNode ... nodes){
        this.nodes.addAll(Arrays.asList(nodes));
    }
    public void put(BillNode node){
        nodes.add(node);
    }
}
