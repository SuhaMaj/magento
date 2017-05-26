package com.company;

import java.util.ArrayList;
import java.util.List;

public class Main {

    static List<Vegetables> vegetables = new ArrayList<>();
    static List list = new ArrayList();



    public static void main(String[] args) {

        list.add(new Integer(1));
        list.add("opop");
        Integer integer = (Integer)list.get(0);
        String  string = (String)list.get(1);
        System.out.println(integer);
        System.out.println(string);
        Vegetables [] arrayVeg = new Vegetables[4];
        for( int i=0; i < arrayVeg.length; i++ )
            arrayVeg[i] = new Vegetables();

        //Vegetables emptyVeg = new Vegetables();



        String name = "Tomato";
        String colour = "Red";
        Vegetables tomato = new Vegetables(name,colour);
        vegetables.add(tomato);
        tomato.setName("Suha");
        for( int i=0; i < arrayVeg.length; i++ )
            arrayVeg[i] = tomato;

        System.out.print(arrayVeg[2].getColour());


    }
}
