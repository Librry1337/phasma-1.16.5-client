
package dev.solo.player.module;


import java.util.ArrayList;
import java.util.Arrays;

public class CategoryManager
{
    private final ArrayList<char[]> categories;
    
    public CategoryManager() {
        (this.categories = new ArrayList<char[]>()).addAll(Arrays.asList(new char[][] { Category.client, Category.combat, Category.move, Category.player, Category.visual }));
    }
    
    public ArrayList<char[]> getCategories() {
        return this.categories;
    }
}
