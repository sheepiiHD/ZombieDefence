package net.battleplugins.msutton.DungeonsAhoy.Tools.GameInfo.Variables;

/**
 * Created by mts01060 on 11/14/2016.
 */

public class Direction {

    /**
     *         0
     *       3 + 1
     *         2
     */
    public static Direction NORTH = new Direction(0);
    public static Direction SOUTH = new Direction(2);
    public static Direction EAST = new Direction(1);
    public static Direction WEST = new Direction (3);
    public static Direction NORTHEAST = new Direction (4);
    public static Direction NORTHWEST = new Direction (5);
    public static final Direction SOUTHEAST = new Direction (6);
    public static final Direction SOUTHWEST = new Direction (7);


    private int direction = 0;
    public Direction(int direction){
        this.direction = direction;
    }
    public int getDirectionActual(){
        return direction;
    }
    public String getDirectionAsString(){
        switch(direction){
            case 0:
                return "North";
            case 1:
                return "East";
            case 2:
                return "South";
            case 3:
                return "West";
            case 4:
                return "North East";
            case 5:
                return "North West";
            case 6:
                return "South East";
            case 7:
                return "South West";
            default:
                return "ERROR!!!!";
        }
    }


}
