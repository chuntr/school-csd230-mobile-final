package com.example.basicactivity;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class FoodData {

    private final String[] initialFoods = {"tomato"};
    public final ArrayList<String> initFoodList;
    private int foodPtr = 0;

    ArrayList<String> allFoods = new ArrayList<>();

    public FoodData(InputStream stream) {
        initFoodList = new ArrayList<>(Arrays.asList(initialFoods));
        Scanner fin = new Scanner(stream);
        while (fin.hasNextLine()) {
            allFoods.add(fin.nextLine());
        }
    }

    public String getNextFood() {
        if (foodPtr >= allFoods.size()) {
            return "No more data";
        }
        return allFoods.get(foodPtr++);
    }

    public int getLastPosition() { return foodPtr; }

    public boolean reachedEnd() { return foodPtr >= allFoods.size(); }
}
