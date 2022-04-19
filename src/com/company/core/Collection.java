package com.company.core;

import com.company.data.HumanBeing;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Date;
import java.util.Deque;

/**
 * Класс отвечает за управление и хранение коллекции
 */
public class Collection {
    Deque<HumanBeing> humanQue = new ArrayDeque<>();
    Date data = new Date();

    /**
     *
     * @param human новый объект класса HumanBeing
     */
    public void addHuman(HumanBeing human){
        humanQue.add(human);
    }

    /**
     *
     * @return возвращает последний в очереди объект коллекции
     */
    public HumanBeing getHuman(){
        return humanQue.getLast();
    }

    public Deque<HumanBeing> getCollection(){
        return humanQue;
    }
    /**
     *
     * @return возвращает дату инициализации коллекции
     */
    public Date getData(){return data;}

    /**
     * Пишет в консоль первый попавшийся объект, у которого поле RealHero = true.
     */
    public void maxByRealHero(){
        for(HumanBeing humanBeing : humanQue){
            if(humanBeing.getRealHero()) {
                System.out.println(humanBeing.toString());
                return;
            }
        }
    }

    /**
     * Ищет все объекты коллекции, поле Name которых содержит данную подстроку
     * @param value Подстрока, которая должна содержаться в имени
     */
    public void searchName(String value){
        for(HumanBeing humanBeing : humanQue){
            if(humanBeing.getName().contains(value)){
                System.out.println(humanBeing);
            }
        }
    }

    /**
     * Производит сортировку
     */
    public void descendingSort(){
        ArrayList<HumanBeing> sortList = new ArrayList<>(humanQue);
        if(sortList.size()>0) {
            for (int i = 0; i < sortList.size(); i++) {
                System.out.println(sortList.get(sortList.size() - 1 - i));
            }
        } else {
            System.out.println("Коллекция пуста.");
        }
    }
}
