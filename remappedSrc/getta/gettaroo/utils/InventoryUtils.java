package getta.gettaroo.utils;


public class InventoryUtils {

    public static boolean isHotbarSlot(int slot){
        if(slot >= 36 && slot <= 44){
            return true;
        }
        return false;
    }
}
