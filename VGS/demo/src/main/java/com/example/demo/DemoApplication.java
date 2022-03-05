package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.ArrayList;
import java.util.List;

@SpringBootApplication
public class DemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
        int arr[][] = {{1, 0, 0}, {0, 0, 0}, {0, 0, 1}};
        mode(arr);
    }

    public static int mode(int[][] arr) {
        List<Integer> list = new ArrayList<>();
        int arrayLength = arr[0].length;
        int numberOfDays = 0;

        //Make the multi-dimensional array a single arraylist
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr[i].length; j++) {
                list.add(arr[i][j]);
            }
        }

        //If there are still 0's in the server change adjacent positions to 1
        //Increase count
        for(int i=0; i < list.size(); i++){
            int left = i - 1;
            int right = i + 1;
            int top = i - arrayLength;
            int bottom = i + arrayLength;
            //avoid out of bound or range exception
            if(list.contains(0) && left >= 0 && left < list.size() && right >= 0 && right < list.size() && top >= 0 && top < list.size() && bottom >= 0 && bottom < list.size()){
                list.set(i - 1, 1);
                list.set(i + 1, 1);
                list.set(i - arrayLength, 1);
                list.set(i + arrayLength, 1);
                numberOfDays++;
            }
        }
        System.out.println(numberOfDays);
        return numberOfDays;
    }

}
