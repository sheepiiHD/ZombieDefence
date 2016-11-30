package net.battleplugins.msutton.DungeonsAhoy.Tools.GameInfo.Variables;

/**
 * Created by mts01060 on 11/14/2016.
 */

public class Difficulty {
    public static final Difficulty Extreme = new Difficulty(4);
    public static final Difficulty Hard = new Difficulty(3);
    public static final Difficulty Medium = new Difficulty(2);
    public static final Difficulty Easy = new Difficulty(1);
    public static final Difficulty Passive = new Difficulty(0);

    private int difficulty;
    private WeaponType wt;
    public Difficulty(int dif){
        this.difficulty = dif;

        InitializeDamage(dif);

    }

    private void InitializeDamage(int dif){
        switch(dif){
            case 0:
                wt = WeaponType.MYSTICAL_KATANA;
                break;
            case 1:
                wt = WeaponType.KATANA;
                break;
            case 2:
                wt = WeaponType.TRAINING_SWORD;
                break;
            case 3:
                wt = WeaponType.BUTTERKNIFE;
                break;
            case 4:
                wt = WeaponType.DULL_SPOON;
        }
    }
}
