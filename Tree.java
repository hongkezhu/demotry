// this is a fake comments on Tree
import java.util.*;
    class Node{
        public int data;
        public Node left, right, parent;
        public int position;
        Node(int v){
            this.data = v; this.left=null; this.right=null; this.parent=null;
            this.position = -1;
        }
        public void print(int skip){
            int space = this.position - skip-2;   // -2; // 2 is for -- --
            String s  = new String(new char[space]).replace('\0', ' ');
            System.out.format("%s-%2d-", s,this.data);
            //System.out.format("--%2d--",this.data);
        }
    }
public class Tree{
    private Node root = null;
    public Tree(){  }
    public void addVal(int v) throws Exception{
        if(root==null) {
            root = new Node(v);
            return;
        }
        Node p   = root;
        Node pre = null;
        while(p!=null){
            pre = p;
            if(p.data==v){ System.out.println("Existing already!"); throw new Exception("Existing!");  }
            if(p.data>v) p = p.left;
            else         p = p.right;
        }
        if(pre.data > v) pre.left = new Node(v);
        else             pre.right = new Node(v);
    }
    public boolean findVal(int v){
        return find(root, v);
    }
    private boolean find(Node p, int v){
        if(p==null) return false;
        if(p.data==v) return true;
        return find(p.left,v) && find(p.right,v);
    }
    public void inOrder(){
        Node p = root;
        Stack<Node> stk = new Stack<Node>();
        while(p!=null || !stk.empty()){
            if(p!=null){
                stk.push(p);
                p = p.left;
            }else{
                p = stk.pop();
                System.out.format("%3d, ",p.data);
                p = p.right;
            }
        }
        System.out.println();
    }
    
    public void getPos(){
        inOrder_recursive(root, new int[]{6});
    }
    private void inOrder_recursive(Node p, int[] pos){
        if(p!=null){
            inOrder_recursive(p.left, pos);
            p.position = pos[0];
            pos[0]     = pos[0]+4;
            inOrder_recursive(p.right,pos);
        }else{
            //pos[0]     = pos[0]+4;
        }
    }
    public void levelPrint(){
        if(root==null) return;
        LinkedList<Node> q = new LinkedList<Node>();
        int l1 = 0, l2=0;
        q.addLast(root); l1++;
        int skip = 0;
        while(!q.isEmpty()){
            Node tmp = q.removeFirst();
            tmp.print(skip);
            skip = tmp.position + 2;
            l1--;
            if(tmp.left!=null){ q.addLast(tmp.left); l2++;}
            if(tmp.right!=null){ q.addLast(tmp.right); l2++;}
            if(l1==0){
                l1= l2; l2=0;
                System.out.println("\n");
                skip = 0;
            }
        }
    }
    
    public void remove(int x){
        Node par = null;
        Node p   = root;
        while(p !=null){
            if(p.data == x) break;
            par = p;
            if(p.data > x) p = p.left;
            else           p = p.right;
        }
        if(p==null) { System.out.println(" Can find the target to delete"); return;}
        if(p.left==null && p.right==null) { this.delete0(par, p); return; }
        if(p.left==null) { this.delete_r(par, p); return; }
        if(p.right==null) { this.delete_l(par, p); return; }
        this.delete2(p);
    }
    public void delete0(Node par, Node n){
        if(par == null) { root = null; return;}
        if(par.left == n) { par.left = null ;}
        else               par.right = null;
    }
    public void delete_r(Node par, Node n){
        if(par==null) { root = n.right;  return; }
        if(par.left == n){ par.left = n.right; return ;}
        if(par.right == n){ par.right = n.right; return;}
    }
    public void delete_l(Node par, Node n){
        if(par==null){root= n.left; return;}
        if(par.left==n){par.left =n.left; return;}
        if(par.right==n ){par.right =n.left; return;}
    }
    public void delete2(Node n){
        Node par = n;
        Node x = n.left;
        while(x.right != null){ par = x;  x = x.right;}
        n.data = x.data;
        this.delete_l(par, x);
    }
    
    public static void main(String[] args){
        Tree tree1 = new Tree();
        int[] a = {10, 42, 9,5, 43, 3, 2, 22, 81,35};
        for(int i=0; i<a.length; i++){
            try{
                tree1.addVal(a[i]);
            }catch(Exception e){
                
            }
        }
        tree1.inOrder();
        tree1.remove(42);
        tree1.getPos();
        tree1.levelPrint();

        
        
        TreeIterator ite = tree1.toTreeIterator();
        while(ite.hasNext()){
            System.out.format(" %d ", ite.next().data);
        }
        System.out.println("\n");
    }
    
    
    
    public TreeIterator toTreeIterator(){
        return new TreeIterator(root);
    }
    private class TreeIterator implements Iterator{
        private Stack<Node> stk;
        private Node p;
        public TreeIterator(Node root){
            this.p   = root;
            this.stk = new Stack<Node>();
        }
        public boolean hasNext(){
            return (p!=null || !stk.empty());
        }
        public Node next(){
            while(this.p!=null){
                stk.push(p);
                p = p.left;
            }
            Node n = stk.pop();
            this.p = n.right;
            return n;
        }
        public void remove(){
            
        }
    }
}
