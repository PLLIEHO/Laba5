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
            body = body + "<HumanBeing>" + "\n";
            body=body +"<" + "ID" + ">" + human.getId() + "<" + "/ID" + ">" +"\n";
            body=body +"<" + "Name" + ">" + human.getName() + "<" + "/Name" + ">" + "\n";
            body=body +"<" + "Coordinates" + ">" + "\n";
            body=body +"<" + "x" + ">" + human.getCoordinatesX() + "<" + "/x" + ">" + "\n";
            body=body +"<" + "y" + ">" + human.getCoordinatesY() + "<" + "/y" + ">" + "\n";
            body=body +"<" + "/Coordinates" + ">" + "\n";
            body=body +"<" + "CreationDate" + ">" + human.getCreationDate().getTime() + "<" + "/CreationDate" + ">" + "\n";
            body=body +"<" + "RealHero" + ">" + human.getRealHero().toString() + "<" + "/RealHero" + ">" + "\n";
            if(human.getHasToothPick().toString().equals("true")||human.getHasToothPick().equals("false")) {
                body=body +"<" + "HasToothpick" + ">" + human.getHasToothPick().toString() + "<" + "/HasToothpick" + ">" + "\n";
            } else if (human.getHasToothPick()==null){
                body=body +"<" + "HasToothpick" + ">" + "" + "<" + "/HasToothpick" + ">" + "\n";
            }
            if(human.getImpactSpeed()!=null) {
                body=body +"<" + "ImpactSpeed" + ">" + human.getImpactSpeed() + "<" + "/ImpactSpeed" + ">" + "\n";
            } else {
                body=body +"<" + "ImpactSpeed" + ">" + "" + "<" + "/ImpactSpeed" + ">" + "\n";
            }
            body=body +"<" + "WeaponType" + ">" + human.getWeaponType().toString() + "<" + "/WeaponType" + ">" + "\n";
            body=body +"<" + "Mood" + ">" + human.getMood().toString() + "<" + "/Mood" + ">" + "\n";
            body=body +"<" + "Car" + ">" + "\n";
            body=body +"<" + "Name" + ">" + human.getCarName() + "<" + "/Name" + ">" + "\n";
            body=body +"<" + "Cool" + ">" + human.getCarCool() + "<" + "/Cool" + ">" + "\n";
            body=body +"<" + "/Car" + ">" + "\n";
            body = body + "</HumanBeing>" + "\n";
            }
        body=body +"/" + "<HeroList>";
        fileOutputStream.write(body.getBytes(StandardCharsets.UTF_8));
        fileOutputStream.close();
        }
    }
