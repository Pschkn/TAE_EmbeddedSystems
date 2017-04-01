package smartcart.model;

import java.lang.reflect.Array;
import java.util.ArrayList;

/**
 * Created by admin on 25.03.2017.
 */

public class shoppingList {
    private static ArrayList<String> myList = new ArrayList<String>();

    public static void InitShoppingList(){
        if(!myList.isEmpty()) return;
        myList.add("Apfel");
        myList.add("Banana");
        myList.add("Baum1");
        myList.add("Baum2");
        myList.add("Baum3");
        myList.add("Baum4");
        myList.add("Baum5");
        myList.add("Baum6");
        myList.add("Baum7");
        myList.add("Baum8");
        myList.add("Baum9");
        myList.add("Baum10");
        myList.add("Baum11");
        myList.add("Baum12");
        myList.add("Baum13");
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
}
