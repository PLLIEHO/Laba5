package com.company.core;

import com.company.data.HumanBeing;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Pattern;

import static com.company.core.Collection.humanQue;


public class Core {
    private String currentProgram;
    private Pattern pSpace = Pattern.compile(" ");
    private List<String> lastCommands = new ArrayList<>();
    private String fileXML;

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
                //if(file.canRead()) {
                    Parser parser = new Parser();
                    parser.start(filename);
                    fileXML = filename;
                //} else {
                //    System.out.println("Файл заблокирован для чтения.");
                //    this.searchFile();
                //}
            } catch (FileNotFoundException e) {
                System.out.println("Имя файла не распознано. Пожалуйста, повторите ввод.");
                this.searchFile();
            } catch (IOException e) {
                e.printStackTrace();
            }

        }


    public void script() throws IOException {
        System.out.println("Введите команду: ");
        currentProgram = InputCore.input();
        currentProgram = currentProgram.trim();
        String[] values = pSpace.split(currentProgram);


        if(values[0].equals(CommandList.HELP)){
            System.out.println("help : вывести справку по доступным командам \n" +
            "info : вывести в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)\n" +
            "show : вывести в стандартный поток вывода все элементы коллекции в строковом представлении \n" +
            "add {element} : добавить новый элемент в коллекцию \n" +
            "update id {element} : обновить значение элемента коллекции, id которого равен заданному \n" +
            "remove_by_id id : удалить элемент из коллекции по его id \n" +
            "clear : очистить коллекцию \n" +
            "save : сохранить коллекцию в файл \n" +
            "execute_script file_name : считать и исполнить скрипт из указанного файла. В скрипте содержатся команды в таком же виде, в котором их вводит пользователь в интерактивном режиме. \n" +
            "exit : завершить программу (без сохранения в файл) \n" +
            "add_if_max {element} : добавить новый элемент в коллекцию, если его значение превышает значение наибольшего элемента этой коллекции. Доступные для сравнения элементы: \n" +
            "coordinates_x, coordinates_y, realhero, hastoothpick, impactspeed, carcool; \n" +
            "remove_greater {element} : удалить из коллекции все элементы, превышающие заданный \n" +
            "history : вывести последние 9 команд (без их аргументов) \n" +
            "max_by_real_hero : вывести любой объект из коллекции, значение поля realHero которого является максимальным \n" +
            "filter_contains_name name : вывести элементы, значение поля name которых содержит заданную подстроку \n" +
            "print_descending : вывести элементы коллекции в порядке убывания");
            history("help");
            script();
        }
        else if(values[0].equals(CommandList.INFO)){
            System.out.println("Тип коллекции: ArrayDeque \n" + "Текущий размер коллекции: " + humanQue.size() + "\n" + "Дата инициализации: " + Collection.getData().toString());
            history("info");
            script();
        }
        else if(values[0].equals(CommandList.SHOW)){
            for(HumanBeing o : humanQue){
                System.out.println(o.toString());
            }
            history("show");
            script();
        }
        else if(values[0].equals(CommandList.ADD)){
            AddCore addCore = new AddCore();
            addCore.add();
            history("add");
            script();
        }
        else if(values[0].equals(CommandList.EXECUTE)) {
            if (values.length > 1) {
                try {
                    BufferedReader reader = new BufferedReader(new FileReader(values[1]));
                    InputCore.setScriptFlag(true);
                    String line;
                    while ((line = reader.readLine()) != null) {
                        InputCore.getScriptList().add(line);
                        InputCore.incScriptCounter();
                    }
                    history("execute_script");
                    script();
                } catch (FileNotFoundException e) {
                    System.out.println("Имя файла введено неверно. Пожалуйста, повторите ввод.");
                    script();
                }
            } else {
                System.out.println("Вы не ввели аргумент.");
                script();
            }
        }
        else if(values[0].equals(CommandList.UPDATE)) {
            if (values.length > 1) {
                try {
                    Long ID = Long.parseLong(values[1]);
                    String element = values[2].toLowerCase();
                    AddCore addCore = new AddCore();
                    addCore.update(ID, element);
                    history("update");
                    script();
                } catch (NumberFormatException e) {
                    System.out.println("ID введён неверно. Пожалуйста, повторите ввод.");
                    script();
                }
            } else {
                System.out.println("Вы не ввели аргумент.");
                script();
            }
        }
        else if(values[0].equals(CommandList.REMOVE_BY_ID)){
            if(values.length>1) {
                try {
                    Long ID = Long.parseLong(values[1]);
                    for (HumanBeing human : humanQue) {
                        if (human.getId() == ID) {
                            humanQue.remove(human);
                            System.out.println("Объект с заданным ID успешно удалён.");
                        }
                    }
                    history("remove_by_id");
                    script();
                } catch (NumberFormatException e) {
                    System.out.println("ID введён неверно. Пожалуйста, повторите ввод.");
                    script();
                }
            } else {
            System.out.println("Вы не ввели аргумент.");
            script();
        }
        }
        else if(values[0].equals(CommandList.CLEAR)){
            humanQue.clear();
            System.out.println("Коллекция успешно очищена.");
            history("clear");
            script();
        }
        else if(values[0].equals(CommandList.SAVE)){
            OutputCore.save(fileXML);
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
                String element = values[1].toLowerCase();
                AddCore addCore = new AddCore();
                addCore.setAddIfMaxFlag(true);
                addCore.setCore(this);
                history("add_if_max");
                addCore.setAddIfMaxElement(element);
                addCore.add();
            } else {
                System.out.println("Вы не ввели аргумент.");
            }
            script();
        }
        else if(values[0].equals(CommandList.MAX_BY_REAL_HERO)){
            Collection.maxByRealHero();
            history("max_by_real_hero");
            script();
        }
        else if(values[0].equals(CommandList.FILTER)){
            if(values.length>1) {
                history("filter_name");
                Collection.searchName(values[1]);
            } else {
            System.out.println("Вы не ввели аргумент.");
            }
            script();
        }
        else if(values[0].equals(CommandList.PRINT_DESCENDING)){
            Collection.descendingSort();
            history("print_descending");
            script();
        }
        else if(values[0].equals(CommandList.HISTORY)){
            System.out.println(lastCommands.toString());
            history("history");
            script();
        } else {
            System.out.println("Команда не распознана.");
            script();
        }
    }
}
