package smartcart.model;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by admin on 25.03.2017.
 */

public class shoppingList {
    private static ArrayList<String> myList = new ArrayList<String>();
    private static ArrayList<String> myBoughtList = new ArrayList<>();

    public static void InitShoppingList(){
        if(!myList.isEmpty()) return;
        myList.add("Apple");
        myList.add("Banana");
        myList.add("Onion");
        myList.add("Milk");
        myList.add("Cheddar");
        myList.add("Pizza");
        myList.add("Water");

    }
    public static ArrayList<String> GetShoppingList(){
        return myList;
    }

    public static void AddListItem(String item){
        myList.add(item);
    }

    public static void DeleteListItem(String item) {
        myList.remove(item);
    }

    public static void DeleteListItemAt(int index) { myList.remove(index); }

    public static String GetCurrentItem(){
        if(myList.size() <= 0) return "";
        return myList.get(0);
    }

    public static ArrayList<String> GetBoughtList(){
        return myBoughtList;
    }

    //BoughtList
    public static void BuyItem(int index){
        if (myList.size() <= 0) return;

        myBoughtList.add(myList.get(index));
        myList.remove(index);

        // order list -> new item on top
        for(int i = myBoughtList.size() - 1; i > 0; i--){
            String temp = myBoughtList.get(i);
            myBoughtList.set(i, myBoughtList.get(i-1));
            myBoughtList.set(i-1, temp);
        }
    }

    public static void UnBuyItem(int index){
        if(myBoughtList.size()<=0) return;
        myList.add(myBoughtList.get(index));
        myBoughtList.remove(index);

        // order list -> new item on top
        for(int i = myList.size() - 1; i > 0; i--){
            String temp = myList.get(i);
            myList.set(i, myList.get(i-1));
            myList.set(i-1, temp);
        }
    }

    public static void SwitchOutItem(){
        if(myList.size() < 2) return;
        String tempItem = myList.get(0);
        myList.remove(0);
        myList.add(tempItem);
    }
}
