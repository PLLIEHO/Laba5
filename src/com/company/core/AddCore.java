package com.company.core;

import com.company.data.HumanBeing;
import com.company.data.Mood;
import com.company.data.WeaponType;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class AddCore {
    private boolean addIfMaxFlag = false;
    private String addIfMaxElement;
    private Core core;
    private List<HumanBeing> maxList = new ArrayList<>();
    private Collection collection;

    public AddCore(Collection collection, Core core){
        this.collection = collection;
        this.core = core;
    }

    public void add() throws IOException {
        maxList.clear();
        maxList.addAll(collection.getCollection());
        HumanBeing human = new HumanBeing();
        if (collection.getCollection().size() > 0) {
            human.setId(collection.getHuman().getId() + 1);
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
        collection.getCollection().add(human);
    }

    public String addName() {
        String name = InputCore.input();
        if (!name.equals("")) {
            return (name);
        } else {
            System.out.println("Имя не должно быть пустым. Повторите ввод.");
            this.addName();
            return(null);
        }
    }

    public Mood mood() {
        String moodType = InputCore.input().toLowerCase();
        switch (moodType) {
            case "sadness":
                return (Mood.SADNESS);
            case "gloom":
                return (Mood.GLOOM);
            case "apathy":
                return (Mood.APATHY);
            case "calm":
                return (Mood.CALM);
            case "rage":
                return (Mood.RAGE);
            default:
                System.out.println("Данные не распознаны. Повторите ввод.");
                mood();
                return (null);
        }
    }

    public WeaponType weaponType() {
        String weapon = InputCore.input().toLowerCase();
        switch (weapon) {
            case "axe":
                return (WeaponType.AXE);
            case "pistol":
                return (WeaponType.PISTOL);
            case "shotgun":
                return (WeaponType.SHOTGUN);
            case "rifle":
                return (WeaponType.RIFLE);
            default:
                System.out.println("Данные не распознаны. Повторите ввод.");
                weaponType();
                return (null);
        }
    }

    public boolean isRealHero() throws IOException {
        String hero = InputCore.input().toUpperCase();
        if (!addIfMaxFlag || maxCheck(REALHERO, hero)) {
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
            float floatX = Float.parseFloat(x);
            if (!addIfMaxFlag || maxCheck(COORDSX, x)) {
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
            if (!addIfMaxFlag || maxCheck(COORDSY, y)) {
                return (Double.parseDouble(y));
            }
        } catch (NumberFormatException | IOException e) {
            System.out.println("Введены неправильные данные. Повторите ввод.");
            addCoordsY();
        }
        return (0);
    }

    public Boolean hasToothPick() throws IOException {
        String toothPick = InputCore.input().toUpperCase();
        if (!addIfMaxFlag || maxCheck(HASTOOTHPICK, toothPick)) {
            switch (toothPick) {
                case "ДА":
                    return (true);
                case "НЕТ":
                    return (false);
                case "":
                    return (null);
                default:
                    System.out.println("Введены неправильные данные. Повторите ввод.");
                    hasToothPick();
                    break;
            }
        }
        return (false);
    }

    public Long impactSpeed() {
        String speed = InputCore.input();
        if (!speed.equals("")) {
            try {
                if (!addIfMaxFlag || maxCheck(IMPACTSPEED, speed)) {
                    long longSpeed = Long.parseLong(speed);
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

    public void update(Long id, String element) throws IOException {
        for (HumanBeing humanBeing : collection.getCollection()) {
            if (humanBeing.getId() == id) {
                switch (element) {
                    case NAME:
                        System.out.println("Введите новое имя: ");
                        humanBeing.setName(addName());
                        break;
                    case COORDS:
                        System.out.println("Введите новые координаты (X): ");
                        humanBeing.setCoordinatesX(addCoordsX());
                        System.out.println("Введите новые координаты (Y): ");
                        humanBeing.setCoordinatesY(addCoordsY());
                        break;
                    case REALHERO:
                        System.out.println("Этот человек герой?: (да/нет)");
                        humanBeing.setRealHero(isRealHero());
                        break;
                    case HASTOOTHPICK:
                        System.out.println("У человека есть зубочистка?: (да/нет)");
                        humanBeing.setHasToothPick(hasToothPick());
                        break;
                    case IMPACTSPEED:
                        System.out.println("Введите новую скорость воздействия: ");
                        humanBeing.setImpactSpeed(impactSpeed());
                        break;
                    case WEAPONTYPE:
                        System.out.println("Введите новый тип оружия: (AXE, PISTOL, SHOTGUN, RIFLE)");
                        humanBeing.setWeaponType(weaponType());
                        break;
                    case MOOD:
                        System.out.println("Введите новое настроение: (SADNESS, GLOOM, APATHY, CALM, RAGE)");
                        humanBeing.setMood(mood());
                        break;
                    case CAR:
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
        if (maxList.size() > 0) {
            for (HumanBeing humanBeing : maxList) {
                switch (addIfMaxElement) {
                    case COORDSX:
                        if (humanBeing.getCoordinatesX() > coordsMinX) {
                            coordsMinX = humanBeing.getCoordinatesX();
                        }
                        break;
                    case COORDSY:
                        if (humanBeing.getCoordinatesY() > coordsMinY) {
                            coordsMinY = humanBeing.getCoordinatesY();
                        }
                        break;
                    case REALHERO:
                        if (humanBeing.getRealHero()) {
                            realHero = humanBeing.getRealHero();
                        }
                        break;
                    case HASTOOTHPICK:
                        if (humanBeing.getHasToothPick()) {
                            hasToothpick = humanBeing.getHasToothPick();
                        }
                        break;
                    case IMPACTSPEED:
                        if (humanBeing.getImpactSpeed() > humanBeing.getImpactSpeed()) {
                            impactSpeed = humanBeing.getImpactSpeed();
                        }
                        break;
                    case CARCOOL:
                        if (humanBeing.getCarCool()) {
                            carCool = humanBeing.getCarCool();
                        }
                        break;
                    default:
                        System.out.println("Вы ввели неправильный элемент.");
                        core.script();
                }
            }
        }
        switch (element) {
            case COORDSX:
                if (coordsMinX >= Float.parseFloat(value)) {
                    this.addIfMaxFailure();
                } else {
                    addIfMaxFlag = false;
                    return (true);
                }
                break;
            case COORDSY:
                if (coordsMinY >= Double.parseDouble(value)) {
                    this.addIfMaxFailure();
                } else {
                    addIfMaxFlag = false;
                    return (true);
                }
                break;
            case REALHERO:
                if (!realHero && value.equals("false")) {
                    this.addIfMaxFailure();
                } else if (realHero) {
                    this.addIfMaxFailure();
                } else {
                    addIfMaxFlag = false;
                    return (true);
                }
                break;
            case HASTOOTHPICK:
                if (!hasToothpick && value.equals("false")) {
                    this.addIfMaxFailure();
                } else if (hasToothpick) {
                    this.addIfMaxFailure();
                } else if (value.equals("")) {
                    this.addIfMaxFailure();
                } else {
                    addIfMaxFlag = false;
                    return (true);
                }
                break;
            case IMPACTSPEED:
                if (impactSpeed > Long.parseLong(element)) {
                    this.addIfMaxFailure();
                } else {
                    addIfMaxFlag = false;
                    return (true);
                }
                break;
            case CARCOOL:
                if (!carCool && value.equals("false")) {
                    this.addIfMaxFailure();
                } else if (carCool) {
                    this.addIfMaxFailure();
                } else {
                    addIfMaxFlag = false;
                    return (true);
                }
                break;
            default:
                addIfMaxFlag = false;
                return (true);
        }
        return (true);
    }

    public void addIfMaxFailure() throws IOException {
        System.out.println("Данные, введенные ранее, не превышают значения максимального элемента. Новый элемент не будет добавлен.");
        addIfMaxFlag = false;
        core.script();
    }

    public void setAddIfMaxFlag(boolean flag) {
        addIfMaxFlag = flag;
    }

    public void setAddIfMaxElement(String addIfMaxElement) {
        this.addIfMaxElement = addIfMaxElement;
    }

    public void setCore(Core newcore) {
        core = newcore;
    }

    private static final String NAME = "name";
    private static final String COORDS = "coordinates";
    private static final String REALHERO = "realhero";
    private static final String HASTOOTHPICK = "hastoothpick";
    private static final String IMPACTSPEED = "impactspeed";
    private static final String WEAPONTYPE = "weapontype";
    private static final String MOOD = "mood";
    private static final String CAR = "car";
    private static final String COORDSX = "coordinates_x";
    private static final String COORDSY = "coordinates_y";
    private static final String CARCOOL = "carcool";
}

