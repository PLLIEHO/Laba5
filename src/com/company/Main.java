package com.company;

import com.company.core.Collection;
import com.company.core.Core;
import com.company.core.Parser;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        Collection collection = new Collection();
        Core core = new Core(collection);
        core.searchFile();
        core.script();
    }
}
