package elocindev.item_obliterator.fabric_quilt.util;

import elocindev.item_obliterator.fabric_quilt.ItemObliterator;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;

public class Utils {

    public static String getItemId(Item item) {
        return Registries.ITEM.getId(item).toString();
    }  

    public static boolean shouldRecipeBeDisabled(Item item) {
        return isDisabled(getItemId(item))
        || ItemObliterator.Config.only_disable_recipes.contains(getItemId(item));
    }

    public static boolean isDisabled(String itemid) {
        for (String blacklisted_id : ItemObliterator.Config.blacklisted_items) {
            if (blacklisted_id.startsWith("//")) continue;
            
            if (blacklisted_id.equals(itemid)) return true;

            if (blacklisted_id.startsWith("!")) {
                blacklisted_id = blacklisted_id.substring(1);

                if (itemid.matches(blacklisted_id)) return true;
            }
        }
        
        return false;
    }
}
