package com.company.core;

import com.company.data.HumanBeing;

import java.io.IOException;
import java.util.Locale;

public class RemoveGreater {
    private Core core;
    private String value;
    private String tag;
    private Collection collection;
    public RemoveGreater(Core core, String value, String tag, Collection collection){
        this.core = core;
        this.value = value;
        this.collection = collection;
        this.tag = tag;
    }
    public void splitter() throws IOException {
        switch(tag){
            case COORDSX:
                searchmin(0);
                break;
            case COORDSY:
                searchmin(1);
                break;
            case REALHERO:
                searchmin(2);
                break;
            case HASTOOTHPICK:
                searchmin(3);
                break;
            case IMPACTSPEED:
                searchmin(4);
                break;
            case CARCOOL:
                searchmin(5);
                break;
            default:
               System.out.println("Вы ввели неправильный аргумент. Попробуйте один из этих: coordinates_x, coordinates_y,\n" +
                       "realhero, hastoothpick, impactspeed, carcool.");
               core.script();
        }
        }
        private void searchmin(int flag) throws IOException {
            switch (flag){
                case 0:
                    try {
                        float x = Float.parseFloat(value);
                        collection.getCollection().removeIf(humanBeing -> humanBeing.getCoordinatesX() > x);
                        break;
                    } catch (NumberFormatException e){
                        System.out.println("Аргумент не распознан."); core.script();
                    }
                case 1:
                    try {
                        double y = Double.parseDouble(value);
                        collection.getCollection().removeIf(humanBeing -> humanBeing.getCoordinatesY() > y);
                        break;
                    } catch (NumberFormatException e){
                        System.out.println("Аргумент не распознан."); core.script();
                    }
                case 2:
                    if(value.equalsIgnoreCase("true")){
                        break;
                    } else if(value.equalsIgnoreCase("false")){
                        collection.getCollection().removeIf(humanBeing -> humanBeing.getRealHero());
                        break;
                    } else{System.out.println("Аргумент не распознан."); core.script();}
                case 3:
                    if(value.equalsIgnoreCase("true")){
                        break;
                    } else if(value.equalsIgnoreCase("false")){
                        collection.getCollection().removeIf(humanBeing -> humanBeing.getHasToothPick());
                        break;
                    } else if(value.equals("")){
                        collection.getCollection().removeIf(humanBeing -> humanBeing.getHasToothPick()!=null);
                        break;
                    } else{System.out.println("Аргумент не распознан."); core.script();}
                case 4:
                    try {
                        if(!value.equals("")) {
                            long speed = Long.parseLong(value);
                            collection.getCollection().removeIf(humanBeing -> humanBeing.getImpactSpeed() > speed);
                        } else{
                            collection.getCollection().removeIf(humanBeing -> humanBeing.getImpactSpeed() != null);
                        }
                        break;
                    } catch(NumberFormatException e){
                        System.out.println("Аргумент не распознан."); core.script();
                    }
                case 5:
                    if(value.equalsIgnoreCase("true")){
                        break;
                    } else if(value.equalsIgnoreCase("false")){
                        collection.getCollection().removeIf(humanBeing -> humanBeing.getCarCool());
                        break;
                    } else{System.out.println("Аргумент не распознан."); core.script();}
            }
        }
    private static final String REALHERO = "realhero";
    private static final String HASTOOTHPICK = "hastoothpick";
    private static final String IMPACTSPEED = "impactspeed";
    private static final String COORDSX = "coordinates_x";
    private static final String COORDSY = "coordinates_y";
    private static final String CARCOOL = "carcool";
}
