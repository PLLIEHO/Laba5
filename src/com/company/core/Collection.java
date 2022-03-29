package com.company.core;

import com.company.data.HumanBeing;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Date;
import java.util.Deque;

public class Collection {
    static Deque<HumanBeing> humanQue = new ArrayDeque<>();
    static Date data = new Date();

    public static void addHuman(HumanBeing human){
        humanQue.add(human);
    }
    public static HumanBeing getHuman(){
        return humanQue.getLast();
    }
    public static Date getData(){return data;}
    public static void maxByRealHero(){
        for(HumanBeing humanBeing : humanQue){
            if(humanBeing.getRealHero()) {
                System.out.println(humanBeing.toString());
                return;
            }
        }
    }
    public static void searchName(String value){
        for(HumanBeing humanBeing : humanQue){
            if(humanBeing.getName().contains(value)){
                System.out.println(humanBeing.toString());
            }
        }
    }
    public static void descendingSort(){
        ArrayList<HumanBeing> sortList = new ArrayList<>();
        sortList.addAll(humanQue);
        if(sortList.size()>0) {
            for (int i = 0; i < sortList.size(); i++) {
                System.out.println(sortList.get(sortList.size() - 1 - i));
            }
        } else {
            System.out.println("Коллекция пуста.");
        }
    }
}
