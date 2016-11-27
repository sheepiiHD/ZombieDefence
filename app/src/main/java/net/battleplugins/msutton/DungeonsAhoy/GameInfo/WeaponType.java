package net.battleplugins.msutton.DungeonsAhoy.GameInfo;

/**
 * Created by mts01060 on 11/14/2016.
 */

public class WeaponType {



    //Tier 1 Weapons
    public static final WeaponType MYSTICAL_KATANA = new WeaponType("Mystical Katana", 0, 1);
    public static final WeaponType KATANA = new WeaponType("Katana", 0.16, 1);
    public static final WeaponType TRAINING_SWORD = new WeaponType("Training Sword", .1, 1);
    public static final WeaponType BUTTERKNIFE = new WeaponType("Butterknife", .04, 1);
    public static final WeaponType DULL_SPOON = new WeaponType("Dull Spoon", .02, 1);


    String weaponName = "";
    double damage = 0;
    int tier = 0;

    public WeaponType(String weaponName, double damage, int tier){
        this.weaponName = weaponName;
        this.damage = damage;
        this.tier = tier;
    }

    public String getWeaponName(){
        return weaponName;
    }
    public double getDamage(){
        return damage;
    }
}
