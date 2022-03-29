package com.company.core;

import com.company.data.HumanBeing;
import com.company.data.Mood;
import com.company.data.WeaponType;
import com.company.exceptions.NoNameException;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.lang.constant.Constable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Scanner;

import static com.company.core.Collection.humanQue;

public class AddCore {
    private boolean updateFlag = false;
    private Long ID;
    private HumanBeing human;
    private boolean addIfMaxFlag = false;
    private String addIfMaxElement;
    private Core core;
    private ArrayList<HumanBeing> maxList = new ArrayList<>();

    public void add() throws IOException {
        maxList.clear();
        maxList.addAll(humanQue);
        human = new HumanBeing();
        if (humanQue.size() > 0) {
            human.setId(Collection.getHuman().getId() + 1);
        } else {
            human.setId(1);
        }
        System.out.println("Введите имя: ");
        human.setName(addName());
        System.out.println("Введите координату X: ");
        human.setCoordinatesX(addCoordsX());
        System.out.println("Введите координату Y: ");
        human.setCoordinatesY(addCoordsY());
        Date date = new Date();
        human.setCreationDate(date);
        System.out.println("Этот персонаж - герой? (да/нет)");
        human.setRealHero(isRealHero());
        System.out.println("У него есть зубочистка? (да/нет)");
        human.setHasToothPick(hasToothPick());
        System.out.println("Введите скорость воздействия:");
        human.setImpactSpeed(impactSpeed());
        System.out.println("Выберите тип оружия: (AXE, PISTOL, SHOTGUN, RIFLE)");
        human.setWeaponType(weaponType());
        System.out.println("Выберите настроение: (SADNESS, GLOOM, APATHY, CALM, RAGE)");
        human.setMood(mood());
        System.out.println("Введите название машины: ");
        human.setCarName(addName());
        System.out.println("Машина крутая? (да/нет)");
        human.setCarCool(isRealHero());
        addIfMaxFlag = false;
        humanQue.add(human);
    }

    public String addName() {
        String name = InputCore.input();
        if (!name.equals("") && name != null) {
            return (name);
        } else {
            throw new NoNameException("Имя не должно быть пустым!");
        }
    }

    public Mood mood() {
        String moodType = InputCore.input().toLowerCase();
        if (moodType.equals("sadness")) {
            return (Mood.SADNESS);
        } else if (moodType.equals("gloom")) {
            return (Mood.GLOOM);
        } else if (moodType.equals("apathy")) {
            return (Mood.APATHY);
        } else if (moodType.equals("calm")) {
            return (Mood.CALM);
        } else if (moodType.equals("rage")) {
            return (Mood.RAGE);
        } else {
            System.out.println("Данные не распознаны. Повторите ввод.");
            mood();
            return (null);
        }
    }

    public WeaponType weaponType() {
        String weapon = InputCore.input().toLowerCase();
        if (weapon.equals("axe")) {
            return (WeaponType.AXE);
        } else if (weapon.equals("pistol")) {
            return (WeaponType.PISTOL);
        } else if (weapon.equals("shotgun")) {
            return (WeaponType.SHOTGUN);
        } else if (weapon.equals("rifle")) {
            return (WeaponType.RIFLE);
        } else {
            System.out.println("Данные не распознаны. Повторите ввод.");
            weaponType();
            return (null);
        }
    }

    public boolean isRealHero() throws IOException {
        String hero = InputCore.input().toUpperCase();
        if(!addIfMaxFlag || maxCheck("isrealhero", hero)) {
            if (hero.equals("ДА")) {
                return (true);
            } else if (hero.equals("НЕТ")) {
                return (false);
            } else {
                System.out.println("Данные неверны. Повторите ввод.");
                isRealHero();
            }
        }
        return (false);
    }

    public float addCoordsX() {
        String x = InputCore.input();
        try {
            Float floatX = Float.parseFloat(x);
            if(!addIfMaxFlag || maxCheck("coordinates_x", x)) {
                if (floatX > -316) {
                    return (floatX);
                } else {
                    System.out.println("Введены неправильные данные. Повторите ввод.");
                    addCoordsX();
                }
            }
        } catch (NumberFormatException | IOException e) {
            System.out.println("Введены неправильные данные. Повторите ввод.");
            addCoordsX();
        }
        return (0);
    }

    public double addCoordsY() {
        String y = InputCore.input();
        try {
            if(!addIfMaxFlag || maxCheck("coordinates_y", y)) {
                Double doubleY = Double.parseDouble(y);
                return (doubleY);
            }
        } catch (NumberFormatException | IOException e) {
            System.out.println("Введены неправильные данные. Повторите ввод.");
            addCoordsY();
        }
        return (0);
    }

    public Boolean hasToothPick() throws IOException {
        String toothPick = InputCore.input().toUpperCase();
        if(!addIfMaxFlag || maxCheck("hastoothpick", toothPick)) {
            if (toothPick.equals("ДА")) {
                return (true);
            } else if (toothPick.equals("НЕТ")) {
                return (false);
            } else if (toothPick.equals("")) {
                return (null);
            } else {
                System.out.println("Введены неправильные данные. Повторите ввод.");
                hasToothPick();
            }
        }
        return (false);
    }

    public Long impactSpeed() {
        String speed = InputCore.input();
        if (!speed.equals("")) {
            try {
                if(!addIfMaxFlag || maxCheck("impactspeed", speed)) {
                    Long longSpeed = Long.parseLong(speed);
                    if (longSpeed > -902) {
                        return (longSpeed);
                    } else {
                        System.out.println("Введены неправильные данные. Повторите ввод.");
                        impactSpeed();
                    }
                }
            } catch (NumberFormatException | IOException e) {
                System.out.println("Введены неправильные данные. Повторите ввод.");
                impactSpeed();
            }
            return (100000000000L);
        } else {
            return (null);
        }
    }

    public void setID(Long id) {
        this.ID = id;
    }

    public void setUpdateFlag(boolean flag) {
        this.updateFlag = flag;
    }

    public void update(Long id, String element) throws IOException {
        for (HumanBeing humanBeing : humanQue) {
            if (humanBeing.getId() == id) {
                switch (element) {
                    case "name":
                        System.out.println("Введите новое имя: ");
                        humanBeing.setName(addName());
                        break;
                    case "coordinates":
                        System.out.println("Введите новые координаты (X): ");
                        humanBeing.setCoordinatesX(addCoordsX());
                        System.out.println("Введите новые координаты (Y): ");
                        humanBeing.setCoordinatesY(addCoordsY());
                        break;
                    case "realhero":
                        System.out.println("Этот человек герой?: (да/нет)");
                        humanBeing.setRealHero(isRealHero());
                        break;
                    case "hastoothpick":
                        System.out.println("У человека есть зубочистка?: (да/нет)");
                        humanBeing.setHasToothPick(hasToothPick());
                        break;
                    case "impactspeed":
                        System.out.println("Введите новую скорость воздействия: ");
                        humanBeing.setImpactSpeed(impactSpeed());
                        break;
                    case "weapontype":
                        System.out.println("Введите новый тип оружия: (AXE, PISTOL, SHOTGUN, RIFLE)");
                        humanBeing.setWeaponType(weaponType());
                        break;
                    case "mood":
                        System.out.println("Введите новое настроение: (SADNESS, GLOOM, APATHY, CALM, RAGE)");
                        humanBeing.setMood(mood());
                        break;
                    case "car":
                        System.out.println("Введите новое название машины:");
                        humanBeing.setCarName(addName());
                        System.out.println("Машина-то крутая? (да/нет)");
                        humanBeing.setCarCool(isRealHero());
                        break;
                    default:
                        System.out.println("Введен неверный элемент, повторите ввод.");
                        break;
                }
            }
        }
    }
    public boolean maxCheck(String element, String value) throws IOException {
        float coordsMinX = -316;
        double coordsMinY = -100000000;
        boolean realHero = false;
        Boolean hasToothpick = false;
        long impactSpeed = -100000000L;
        boolean carCool = false;
        if(maxList.size()>0) {
            for (HumanBeing humanBeing : maxList) {
                switch (addIfMaxElement) {
                    case "coordinates_x":
                        if (humanBeing.getCoordinatesX() > coordsMinX) {
                            coordsMinX = humanBeing.getCoordinatesX();
                            break;
                        }
                    case "coordinates_y":
                        if (humanBeing.getCoordinatesY() > coordsMinY) {
                            coordsMinY = humanBeing.getCoordinatesY();
                            break;
                        }
                    case "realhero":
                        if (humanBeing.getRealHero()) {
                            realHero = humanBeing.getRealHero();
                            break;
                        }
                    case "hastoothpick":
                        if (humanBeing.getHasToothPick()) {
                            hasToothpick = humanBeing.getHasToothPick();
                            break;
                        }
                    case "impactspeed":
                        if (humanBeing.getImpactSpeed() > humanBeing.getImpactSpeed()) {
                            impactSpeed = humanBeing.getImpactSpeed();
                            break;
                        }
                    case "carcool":
                        if (humanBeing.getCarCool()) {
                            carCool = humanBeing.getCarCool();
                            break;
                        }
                    default:
                        System.out.println("Вы ввели неправильный элемент.");
                        core.script();
                }
            }
        }
            switch (element) {
                case "coordinates_x":
                    if(coordsMinX>=Float.parseFloat(value)){
                        System.out.println("Данные, введенные ранее, не превышают значения максимального элемента. Новый элемент не будет добавлен.");
                        addIfMaxFlag = false;
                        core.script();
                    } else {return (true);}
                    break;
                case "coordinates_y":
                    if(coordsMinY>=Double.parseDouble(value)){
                        System.out.println("Данные, введенные ранее, не превышают значения максимального элемента. Новый элемент не будет добавлен.");
                        addIfMaxFlag = false;
                        core.script();
                    } else {return (true);}
                    break;
                case "realhero":
                    if(!realHero&&(element.equals("false"))){
                        System.out.println("Данные, введенные ранее, не превышают значения максимального элемента. Новый элемент не будет добавлен.");
                        addIfMaxFlag = false;
                        core.script();
                    } else if (realHero){
                        System.out.println("Данные, введенные ранее, не превышают значения максимального элемента. Новый элемент не будет добавлен.");
                        addIfMaxFlag = false;
                        core.script();
                    } else {return (true);}
                    break;
                case "hastoothpick":
                    if(!hasToothpick&&element.equals("false")){
                        System.out.println("Данные, введенные ранее, не превышают значения максимального элемента. Новый элемент не будет добавлен.");
                        addIfMaxFlag = false;
                        core.script();
                    } else if (hasToothpick){
                        System.out.println("Данные, введенные ранее, не превышают значения максимального элемента. Новый элемент не будет добавлен.");
                        addIfMaxFlag = false;
                        core.script();
                    } else if (element.equals("")){
                        System.out.println("Данные, введенные ранее, не превышают значения максимального элемента. Новый элемент не будет добавлен.");
                        addIfMaxFlag = false;
                        core.script();
                    } else {return (true);}
                    break;
                case "impactspeed":
                    if(impactSpeed>Long.parseLong(element)){
                        System.out.println("Данные, введенные ранее, не превышают значения максимального элемента. Новый элемент не будет добавлен.");
                        addIfMaxFlag = false;
                        core.script();
                    } else {return (true);}
                    break;
                case "carcool":
                    if(!carCool&&(element.equals("false"))){
                        System.out.println("Данные, введенные ранее, не превышают значения максимального элемента. Новый элемент не будет добавлен.");
                        addIfMaxFlag = false;
                        core.script();
                    } else if (carCool){
                        System.out.println("Данные, введенные ранее, не превышают значения максимального элемента. Новый элемент не будет добавлен.");
                        addIfMaxFlag = false;
                        core.script();
                    } else {return (true);}
                    break;
                default:
                    return (true);
            }
        return (true);
        }

    public void setAddIfMaxFlag(boolean flag){
        addIfMaxFlag = flag;
    }

    public void setAddIfMaxElement(String addIfMaxElement) {
        this.addIfMaxElement = addIfMaxElement;
    }

    public void setCore(Core newcore) {
        core = newcore;
    }
}

