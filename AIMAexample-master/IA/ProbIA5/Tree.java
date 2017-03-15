/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IA.ProbIA5;

import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author xarax
 */   
public class Tree<Integer> {
        
        private Node<Integer> root;

        public Tree(Integer rootData) {
            root = new Node<Integer>();
            root.data = rootData;
            root.children = new ArrayList<Node<Integer>>();
        }
        public class Node<Integer> {
            private Integer data;
            private Node<Integer> parent;
            private List<Node<Integer>> children;
        }
        
        public void copy(Node<Integer> oldNode, Node<Integer> newNode ){
            newNode.data=oldNode.data;
            for(Node<Integer> a: oldNode.children){
                newNode.children.add(new Node<Integer>());
                //per cada fill que es faci una copia
                copy(a,newNode.children.get(newNode.children.size()));
                //i es copii
            }
        }
        
        public Tree copy(){
            Tree b= new Tree(this.root.data);
            b.root = new Node<Integer>();
            copy(this.root,b.root);
            return b; 
    }
}
