package com.company.core;

import com.company.data.HumanBeing;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

public class Core {
    private final Pattern pSpace = Pattern.compile(" ");
    private List<String> lastCommands = new ArrayList<>();
    private String fileXML;
    private Collection collection;

    public Core(Collection collection){
        this.collection = collection;
    }
    public void history(String command){
        lastCommands.add(command);
        if(lastCommands.size()>9){
            lastCommands.remove(0);
        }
    }

    public void searchFile() throws FileNotFoundException {
        System.out.println("Введите название файла: ");
        Scanner in = new Scanner(System.in);
        String filename = in.nextLine();
        File file = new File(filename);
            try {
                if(file.exists()&&file.canRead()) {
                    Parser parser = new Parser(collection);
                    parser.start(filename);
                    fileXML = filename;

                } else {System.out.println("Данного файла не существует. Пожалуйста, повторите ввод."); searchFile();}
            } catch (FileNotFoundException e) {
                if(file.exists()){
                    System.out.println("Файл заблокирован для чтения.");
                    this.searchFile();}
                else{
               // System.out.println("Имя файла не распознано. Пожалуйста, повторите ввод.");
               // this.searchFile();}
            }} catch (IOException e) {
                e.printStackTrace();
            }

        }


    public void script() throws IOException {
        System.out.println("Введите команду: ");
        String currentProgram = InputCore.input();
        currentProgram = currentProgram.trim();
        String[] values = pSpace.split(currentProgram);


        if(values[0].equals(CommandList.HELP)){
            System.out.println("""
                    help : вывести справку по доступным командам\s
                    info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)
                    show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении\s
                    add {element} : добавить новый элемент в коллекцию\s
                    update id {element} : обновить значение элемента коллекции, id которого равен заданному\s
                    remove_by_id id : удалить элемент из коллекции по его id\s
                    clear : очистить коллекцию\s
                    save : сохранить коллекцию в файл\s
                    execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме.\s
                    exit : завершить программу (без сохранения в файл)\s
                    add_if_max {element} : добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции. Доступные для сравнения элементы:\s
                    coordinates_x, coordinates_y, realhero, hastoothpick, impactspeed, carcool;\s
                    remove_greater {element} : удалить из коллекции все элементы, превышающие заданный\s
                    history : вывести последние 9 команд (без их аргументов)\s
                    max_by_real_hero : вывести любой объект из коллекции, значение поля realHero которого является максимальным\s
                    filter_contains_name name : вывести элементы, значение поля name которых содержит заданную подстроку\s
                    print_descending : вывести элементы коллекции в порядке убывания""");
            history("help");
            script();
        }
        else if(values[0].equals(CommandList.INFO)){
            System.out.println("Тип коллекции: ArrayDeque \n" + "Текущий размер коллекции: " + collection.getCollection().size() + "\n" + "Дата инициализации: " + collection.getData().toString());
            history("info");
            script();
        }
        else if(values[0].equals(CommandList.SHOW)){
            for(HumanBeing o : collection.getCollection()){
                System.out.println(o.toString());
            }
            history("show");
            script();
        }
        else if(values[0].equals(CommandList.ADD)){
            AddCore addCore = new AddCore(collection, this);
            addCore.add();
            history("add");
            script();
        }
        else if(values[0].equals(CommandList.EXECUTE)) {
            if (values.length > 1) {
                this.execute(values[1]);
            } else {
                System.out.println("Вы не ввели аргумент.");
            }
            script();
        }
        else if(values[0].equals(CommandList.UPDATE)) {
            if (values.length > 2) {
                this.update(values[1], values[2]);
            } else {
                System.out.println("Вы не ввели аргумент.");
                script();
            }
        }
        else if(values[0].equals(CommandList.REMOVE_BY_ID)){
            if(values.length>1) {
                this.remove_by_id(values[1]);
            } else {
            System.out.println("Вы не ввели аргумент.");
            script();
        }
        }
        else if(values[0].equals(CommandList.CLEAR)){
            collection.getCollection().clear();
            System.out.println("Коллекция успешно очищена.");
            history("clear");
            script();
        }
        else if(values[0].equals(CommandList.SAVE)){
            OutputCore.save(fileXML, this, collection);
            history("save");
            script();
        }
        else if(values[0].equals(CommandList.EXIT)){
            System.out.println("До скорой встречи!");
            history("exit");
            System.exit(1);
        }
        else if(values[0].equals(CommandList.ADD_IF_MAX)){
            if(values.length>1) {
                this.add_if_max(values[1]);
            } else {
                System.out.println("Вы не ввели аргумент.");
            }
            script();
        }
        else if(values[0].equals(CommandList.MAX_BY_REAL_HERO)){
            collection.maxByRealHero();
            history("max_by_real_hero");
            script();
        }
        else if(values[0].equals(CommandList.FILTER)){
            if(values.length>1) {
                history("filter_name");
                collection.searchName(values[1]);
            } else {
            System.out.println("Вы не ввели аргумент.");
            }
            script();
        }
        else if(values[0].equals(CommandList.PRINT_DESCENDING)){
            collection.descendingSort();
            history("print_descending");
            script();
        }
        else if(values[0].equals(CommandList.HISTORY)){
            System.out.println(lastCommands.toString());
            history("history");
            script();
        }
        else if(values[0].equals(CommandList.REMOVE_GREATER)){
            if(values.length>2) {
                RemoveGreater removeGreater = new RemoveGreater(this, values[2], values[1], collection);
                removeGreater.splitter();
                script();
            } else {
                System.out.println("Вы не ввели аргумент.");
            }
            script();

        }
        else {
            System.out.println("Команда не распознана.");
            script();
        }
    }

    private void add_if_max(String values) throws IOException {
        String element = values.toLowerCase();
        AddCore addCore = new AddCore(collection, this);
        addCore.setAddIfMaxFlag(true);
        addCore.setCore(this);
        history("add_if_max");
        addCore.setAddIfMaxElement(element);
        addCore.add();
    }
    private void remove_by_id(String values) throws IOException {
        try {
            long ID = Long.parseLong(values);
            for (HumanBeing human : collection.getCollection()) {
                if (human.getId() == ID) {
                    collection.getCollection().remove(human);
                    System.out.println("Объект с заданным ID успешно удалён.");
                }
            }
            history("remove_by_id");
            script();
        } catch (NumberFormatException e) {
            System.out.println("ID введён неверно. Пожалуйста, повторите ввод.");
            script();
        }
    }
    private void update(String values1, String values2) throws IOException {
        try {
            Long ID = Long.parseLong(values1);
            String element = values2.toLowerCase();
            AddCore addCore = new AddCore(collection, this);
            addCore.update(ID, element);
            history("update");
            script();
        } catch (NumberFormatException | IOException e) {
            System.out.println("ID введён неверно. Пожалуйста, повторите ввод.");
            script();
        }
    }
    private void execute(String values) throws IOException {
        File file = new File(values);
        try {
                BufferedReader reader = new BufferedReader(new FileReader(values));
                InputCore.setScriptFlag(true);
                String line;
                while ((line = reader.readLine()) != null) {
                    InputCore.getScriptList().add(line);
                    InputCore.incScriptCounter();
                }
                history("execute_script");
                script();
        } catch (FileNotFoundException e) {
            if(!file.exists()){
            System.out.println("Имя файла введено неверно. Пожалуйста, повторите ввод.");
            } else {System.out.println("Файл заблокирован для чтения.");
            }
            script();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
