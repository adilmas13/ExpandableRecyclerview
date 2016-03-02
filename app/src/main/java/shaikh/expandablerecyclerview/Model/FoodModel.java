package shaikh.expandablerecyclerview.Model;

import java.util.ArrayList;

/**
 * Created by Adil Shaikh on 3/2/16.
 */
public class FoodModel {
    private String name;
    private ArrayList<ItemModel> itemModels;

    public FoodModel(String name, ArrayList<ItemModel> itemModels) {
        this.name = name;
        this.itemModels = itemModels;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ArrayList<ItemModel> getItemModels() {
        return itemModels;
    }

    public void setItemModels(ArrayList<ItemModel> itemModels) {
        this.itemModels = itemModels;
    }
}
