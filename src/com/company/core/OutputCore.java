package com.company.core;

import com.company.data.HumanBeing;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

import static com.company.core.Collection.humanQue;
import static com.company.core.Parser.getInfo;

public class OutputCore {
    private static String body = "";
    public static void save(String fileName) throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream(fileName);
        body="<?xml version=\"1.0\" encoding=\"UTF-8\"?>" + "\n";
        body=body +"<HeroList>" + "\n";
        for(HumanBeing human : humanQue){
            body = body + "<"+ TagList.herolist + ">" + "\n";
            body=body +"<" + TagList.id + ">" + human.getId() + "<" + TagList.idOver + ">" +"\n";
            body=body +"<" + TagList.name + ">" + human.getName() + "<" + TagList.nameOver + ">" + "\n";
            body=body +"<" + TagList.coordinates + ">" + "\n";
            body=body +"<" + TagList.x + ">" + human.getCoordinatesX() + "<" + TagList.xOver + ">" + "\n";
            body=body +"<" + TagList.y + ">" + human.getCoordinatesY() + "<" + TagList.yOver + ">" + "\n";
            body=body +"<" + TagList.coordinatesOver + ">" + "\n";
            body=body +"<" + TagList.creationDate + ">" + human.getCreationDate().getTime() + "<" + TagList.creationDateOver + ">" + "\n";
            body=body +"<" + TagList.realHero + ">" + human.getRealHero().toString() + "<" + TagList.realHeroOver + ">" + "\n";
            if(human.getHasToothPick().toString().equals("true")||human.getHasToothPick().equals("false")) {
                body=body +"<" + TagList.hasToothpick + ">" + human.getHasToothPick().toString() + "<" + TagList.hasToothpickOver + ">" + "\n";
            } else if (human.getHasToothPick()==null){
                body=body +"<" + TagList.hasToothpick + ">" + "" + "<" + TagList.hasToothpickOver + ">" + "\n";
            }
            if(human.getImpactSpeed()!=null) {
                body=body +"<" + TagList.impactSpeed + ">" + human.getImpactSpeed() + "<" + TagList.impactSpeedOver + ">" + "\n";
            } else {
                body=body +"<" + TagList.impactSpeed + ">" + "" + "<" + TagList.impactSpeedOver + ">" + "\n";
            }
            body=body +"<" + TagList.weaponType + ">" + human.getWeaponType().toString() + "<" + TagList.weaponTypeOver + ">" + "\n";
            body=body +"<" + TagList.mood + ">" + human.getMood().toString() + "<" + TagList.moodOver + ">" + "\n";
            body=body +"<" + TagList.car + ">" + "\n";
            body=body +"<" + TagList.name + ">" + human.getCarName() + "<" + TagList.nameOver + ">" + "\n";
            body=body +"<" + TagList.carCool + ">" + human.getCarCool() + "<" + TagList.carCoolOver + ">" + "\n";
            body=body +"<" + TagList.carOver + ">" + "\n";
            body = body + "<" + TagList.humanbeingOver + ">" + "\n";
            }
        body=body + "<" + TagList.herolistOver + ">";
        fileOutputStream.write(body.getBytes(StandardCharsets.UTF_8));
        fileOutputStream.close();
        }
    }
