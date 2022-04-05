package com.company.core;

import com.company.data.HumanBeing;
import com.company.data.Mood;
import com.company.data.WeaponType;
import com.company.exceptions.FileErrorException;
import com.company.exceptions.NoNameException;

import javax.sound.midi.SysexMessage;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.company.core.Collection.humanQue;

public class Parser {
    private Pattern XMLinfo = Pattern.compile("<\\?([\\s\\S]+?)\\?>");
    private Pattern basic = Pattern.compile("<([\\s\\S]+?)>");
    private Pattern value = Pattern.compile(">([\\s\\S]*?)<");
    private boolean infoFlag = true;
    private boolean coordFlag = false;
    private static String info;
    private int count = 0;
    private int carFlag = 0;
    private boolean idFlag = true;
    private Map<String, Boolean> checklist = new HashMap<>();
    private boolean sygnal;
    private boolean deleteFlag = false;
    private int counter;
    TagList tags = new TagList();

    public void start(String fileName) throws IOException {
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        if(lineCounter(fileName)>2) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.equals(TagList.herolist)) {
                    this.execute(line);
                }
            }
        } else {
            System.out.println("Файл пуст или содержит недостаточно информации.");
        }
        }

       public int lineCounter(String filename) throws IOException {
           BufferedReader reader = new BufferedReader(new FileReader(filename));
           String lin;
           int counter = 0;
           while ((lin = reader.readLine()) != null) {
                counter += 1;
           }
           reader.close();
           this.counter = counter;
           return counter;
       }

    public void execute(String line){
        count += 1;
        if(infoFlag) {
            info = line;
            System.out.println(info);
            infoFlag = false;
        }
        else{
            Matcher matcherA = basic.matcher(line);
            Matcher matcherB = value.matcher(line);
            if(matcherA.find()) {
                String tag = matcherA.group().replace("<", "").replace(">", "");
                if (idFlag&&!tag.equals(TagList.herolistOver)&&count<counter-1) {
                    if (check()) {
                        HumanBeing human = new HumanBeing();
                        Collection.addHuman(human);
                        sygnal = false;
                        idFlag = false;
                    }
                }
                    else if(sygnal&&!tag.equals(TagList.herolistOver)&&!check()) {
                        humanQue.removeLast();
                        sygnal = false;
                        throw new FileErrorException("В файле ошибка. Проверьте целостность данных и количество аргументов.");
                    }
                 else {
                    switch (tag) {
                        case TagList.id:
                            this.idParse(matcherB);
                            break;
                        case TagList.name:
                            this.nameParse(matcherB);
                            break;
                        case TagList.coordinates:
                            if(checklist.get("coordinates") ==null) {
                                coordFlag = true;
                                checklist.put("coordinates", true);
                                break;
                            }
                            break;
                        case TagList.x:
                            if(checklist.get("coordinates")) {
                                this.coordinatesXParse(matcherB);
                                break;
                            }
                            break;
                        case TagList.y:
                            if(checklist.get("coordinates")) {
                                this.coordinatesYParse(matcherB);
                                break;
                            }
                            break;
                        case TagList.coordinatesOver:
                            coordFlag = false;
                            break;
                        case TagList.creationDate:
                            if(checklist.get("creationdate")==null) {
                                this.creationDateParse(matcherB);
                                break;
                            }
                            break;
                        case TagList.realHero:
                            if(checklist.get("realhero")==null) {
                                this.realHeroParse(matcherB);
                                break;
                            }
                            break;
                        case TagList.hasToothpick:
                            if(checklist.get("hastoothpick")==null) {
                                this.hasToothpickParse(matcherB);
                                break;
                            }
                            break;
                        case TagList.impactSpeed:
                            if(checklist.get("impactspeed")==null) {
                                this.impactSpeedParse(matcherB);
                                break;
                            }
                            break;
                        case TagList.weaponType:
                            if(checklist.get("weapontype")==null) {
                                this.weaponTypeParse(matcherB);
                                break;
                            }
                            break;
                        case TagList.mood:
                            if(checklist.get("mood")==null) {
                                this.moodParse(matcherB);
                            break;
                            }

                            break;
                        case TagList.car:
                            if(checklist.get("car")==null) {
                                carFlag = carFlag + 1;
                                checklist.put("car", true);
                                break;
                            }
                            break;
                        case TagList.carCool:
                            if(checklist.get("carcool")==null) {
                                this.carCoolParse(matcherB);
                                break;
                            }
                            break;
                        case TagList.carOver:
                            carFlag = carFlag - 1;
                            break;
                        case TagList.humanbeingOver:
                            sygnal = true;
                            idFlag = true;
                            break;
                        default:
                                //sygnal = true;
                                //idFlag = true;
                            break;
                    }
                    // }
                }
            }
            }
        }
    public static String getInfo(){
        return info;
    }
    public boolean check(){
        if(checklist.size()==14){
            checklist.clear();
            return (true);
        } else if(checklist.size()==0){
            return(true);
        }
        else if(checklist.size()<13){
            checklist.clear();
            return (false);
        }
        return (false);
    }

    private void idParse(Matcher matcherB){
        if (matcherB.find()) {
            String idString = matcherB.group().replace("<", "").replace(">", "");
            try {
                long id = Long.parseLong(idString);
                checklist.put("ID", true);
                Collection.getHuman().setId(id);
            } catch (NumberFormatException e){
                System.out.println("ID не читается!");
                deleteFlag = true;
            }
        }
    }

    private void nameParse(Matcher matcherB){
        if (matcherB.find()) {
            String name = matcherB.group().replace("<", "").replace(">", "");
            if (carFlag == 0) {
                if(checklist.get("name")==null) {
                    if (!name.equals("") && name != null) {
                        Collection.getHuman().setName(name);
                        checklist.put("name", true);
                    } else {
                        System.out.println("Имя не должно быть пустым! Объект не будет создан.");
                    }
                }
            } else if (carFlag == 1&&checklist.get("carname")==null) {
                Collection.getHuman().setCarName(name);
                checklist.put("carname", true);
            }
        }
    }

    private void coordinatesXParse(Matcher matcherB){
        if (matcherB.find()) {
            String x = matcherB.group().replace("<", "").replace(">", "");
            try {
                float FloatX = Float.parseFloat(x);
                if (FloatX > -316) {
                    Collection.getHuman().setCoordinatesX(FloatX);
                    checklist.put("x", true);
                }
            } catch (NumberFormatException e){
                System.out.println("Координаты не читаются.");
            }
        }
    }

    private void coordinatesYParse(Matcher matcherB){
        if (matcherB.find()) {
            String y = matcherB.group().replace("<", "").replace(">", "");
            try {
                double DoubleY = Double.parseDouble(y);
                Collection.getHuman().setCoordinatesY(DoubleY);
                checklist.put("y", true);
            } catch (NumberFormatException e){
                System.out.println("Координаты не читаются.");
            }
        }
    }

    private void creationDateParse(Matcher matcherB){
        if (matcherB.find()) {
            String date = matcherB.group().replace("<", "").replace(">", "");
            Date data = new Date(Long.parseLong(date));
            Collection.getHuman().setCreationDate(data);
            System.out.println(humanQue.toArray().toString());
            checklist.put("creationdate", true);
        }
    }

    private void realHeroParse(Matcher matcherB){
        if (matcherB.find()) {
            String hero = matcherB.group().replace("<", "").replace(">", "");
            if (hero.equals("true")) {
                Collection.getHuman().setRealHero(true);
                checklist.put("realhero", true);
            } else if (hero.equals("false")) {
                Collection.getHuman().setRealHero(false);
                checklist.put("realhero", true);
            }
        }
    }

    private void hasToothpickParse(Matcher matcherB){
        if (matcherB.find()) {
            String toothPick = matcherB.group().replace("<", "").replace(">", "");
            if (toothPick.equals("true")) {
                Collection.getHuman().setHasToothPick(true);
                checklist.put("hastoothpick", true);
            } else if (toothPick.equals("false")) {
                Collection.getHuman().setHasToothPick(false);
                checklist.put("hastoothpick", true);
            }
        }
    }

    private void impactSpeedParse(Matcher matcherB){
        if (matcherB.find()) {
            String speed = matcherB.group().replace("<", "").replace(">", "");
            if (!speed.equals("")) {
                Long impactSpeed = Long.parseLong(speed);
                Collection.getHuman().setImpactSpeed(impactSpeed);
            } else {
                Collection.getHuman().setImpactSpeed(null);
            }
            checklist.put("impactspeed", true);
        }
    }

    private void weaponTypeParse(Matcher matcherB){
        if (matcherB.find()) {
            String type = matcherB.group().replace("<", "").replace(">", "");
            switch (type) {
                case "AXE":
                    Collection.getHuman().setWeaponType(WeaponType.AXE);
                    checklist.put("weapontype", true);
                    break;
                case "PISTOL":
                    Collection.getHuman().setWeaponType(WeaponType.PISTOL);
                    checklist.put("weapontype", true);
                    break;
                case "SHOTGUN":
                    Collection.getHuman().setWeaponType(WeaponType.SHOTGUN);
                    checklist.put("weapontype", true);
                    break;
                case "RIFLE":
                    Collection.getHuman().setWeaponType(WeaponType.RIFLE);
                    checklist.put("weapontype", true);
                    break;
            }
        }
    }

    private void moodParse(Matcher matcherB) {
        if (matcherB.find()) {
            String mood = matcherB.group().replace("<", "").replace(">", "");
            switch (mood) {
                case "SADNESS":
                    Collection.getHuman().setMood(Mood.SADNESS);
                    checklist.put("mood", true);
                    break;
                case "GLOOM":
                    Collection.getHuman().setMood(Mood.GLOOM);
                    checklist.put("mood", true);
                    break;
                case "APATHY":
                    Collection.getHuman().setMood(Mood.APATHY);
                    checklist.put("mood", true);
                    break;
                case "CALM":
                    Collection.getHuman().setMood(Mood.CALM);
                    checklist.put("mood", true);
                    break;
                case "RAGE":
                    Collection.getHuman().setMood(Mood.RAGE);
                    checklist.put("mood", true);
                    break;
            }
        }
    }

    private void carCoolParse(Matcher matcherB){
        if (matcherB.find()) {
            String cool = matcherB.group().replace("<", "").replace(">", "");
            if (cool.equals("true")) {
                Collection.getHuman().setCarCool(true);
                checklist.put("carcool", true);
            } else if (cool.equals("false")) {
                Collection.getHuman().setCarCool(false);
                checklist.put("carcool", true);
            }

        }
    }

}

