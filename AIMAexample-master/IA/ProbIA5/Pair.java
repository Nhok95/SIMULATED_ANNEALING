/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package IA.ProbIA5;

/**
 *
 * @author xarax
 */
//creo esta clase porque no se pensar recursivo
public class Pair {
    Integer  volume;
    Float cost;
    Pair(Integer volume,Float cos){
    this.volume=volume;
    this.cost=cost;
    }
    Float getCost(){return this.cost;}
    Integer getVolume(){return this.volume;}
}
