import java.util.Scanner;

public class Ability {
    private static String[] stats = {"STRENGTH", "DEXTERITY", "CONSTITUTION", "INTELLIGENCE", "WISDOM", "CHARISMA"}; //an array for all stats names
    static int[][] vals = new int [stats.length][2];


    public static void ptintAbl(){


        System.out.printf("\n\n%-20s[%s][%s]\t\n", "ABILITY", "VALUE","BONUS");
        System.out.println("---------------------------------");
        for (int i = 0; i <stats.length; i++) {
            if (vals[i][0] < 12) {
                System.out.printf("*%-20s: [%d][%d]\t\n", stats[i], vals[i][0],vals[i][1]);
            } else {
                System.out.printf("*%-20s: [%d][%+d]\t\n",stats[i],vals[i][0], vals[i][1]);
            }
        }
        System.out.println("__________________________________");
    }


    public static void calBonus(){
        for (int i = 0; i < vals.length; i++) {
            vals[i][1] = (vals[i][0]/2-5);
        }
    }


    public static void set9(Class c){
        c.setStatDice();
        vals[0][0]=dropLowestOf(c.getStrDice());
        vals[1][0]=dropLowestOf(c.getDexDice());
        vals[2][0]=dropLowestOf(c.getConDice());
        vals[3][0]=dropLowestOf(c.getIntDice());
        vals[4][0]=dropLowestOf(c.getWisDice());
        vals[5][0]=dropLowestOf(c.getChaDice());

    }



    public static void set4d6() {

        for (int i = 0; i < vals.length; i++) {
            vals[i][0] = dropLowestOf(4);
        }
    }
    public static void set4d6_16(){

        for (int i = 0; i < vals.length; i++) {
            vals[i][0] = dropLowest16();
        }

    }

    public static void getUser() {

        Scanner scan = new Scanner(System.in);
        for (int i = 0; i < stats.length; i++) {  //Looping through the array prompting inputs
            System.out.print("Enter " + stats[i] + " value :");
            if ((scan.hasNextInt())) {
                vals[i][0] = scan.nextInt();
                if (vals[i][0] <= 0) {          //checking for illegal inputs for the game and terminating with a message
                    System.out.println("Invalid input\nThe game will exit now");
                    System.exit(0);
                }
            } else {                         //Terminating the game if invalid entries(NaN)are submitted with a message
                System.out.println("Invalid input\nThe game will exit now");
                System.exit(0);
            }
        }

    }

    private static int dropLowestOf(int dice) {
        int sum = 0;
        int[] val= diceRoll(dice) ;
        for(int i = 0;i <3;i++){
            sum+=val[i];
            // System.out.println(vals[i]);
        }

        return sum;
    }

    private static int dropLowest16(){

        int val = dropLowestOf(4);
        int sum = 0;
        if(val>=16){
            sum=val + dice(1,6);
            return sum;
        }else
            return val;
    }

    private static int dice(int times, int face){

        int sum = 0;
        int dice=0;
        for (int i = 0; i < times; i++) {
            dice = (int) (Math.random() * 1000 % face + 1);
            sum+=dice;
        }
        return sum;
    }

    static int[] diceRoll(int dices) {

        int dice[] = new int[dices];
        for (int i = 0; i < dice.length; i++) {
            dice[i] = dice(1,6);
        }
        for (int i = 0; i < dice.length - 1; i++) {
            int minI = i;
            for (int j = i + 1; j < dice.length; j++) {
                if (dice[j] > dice[minI]) {
                    minI = j;
                }
            }
            int temp = dice[minI];
            dice[minI] = dice[i];
            dice[i] = temp;
        }

        return dice;
    }



}
