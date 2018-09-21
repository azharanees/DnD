import java.io.*;
import java.util.InputMismatchException;
import java.util.Scanner;

public class CharacterDnD {

    private int level;
    int classIndex;
    private int hitPoints;
    private int[] slctSkills;
    private Class clas = new Class();
    private Skill skill = new Skill();
    private String name;
    private Scanner sc = new Scanner(System.in);
    private int bAB;
    private int combat;
    private int dmg;


    public void getName() {
        String name;
        Scanner sc = new Scanner(System.in);
        System.out.printf("Enter your Character Name >>>");
        name = sc.nextLine();
        this.name = name;
    }

    private int getUserLevel() {
        int level;
        System.out.printf("Enter Level >>>");
        while (!sc.hasNextInt()) {
            System.out.print("Invalid input! Enter Level :");
            sc.next();
        }
        level = sc.nextInt();
        if (level <= 0) {
            System.out.printf("Invalid input! ");
            System.exit(0);
        }
        return level;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel() {

        this.level = getUserLevel();
    }
    public void setClass() {
        System.out.print("\n Select A Class for Your Character, \n");
        System.out.println("__________________________________\n\n");
        clas.printClasses();
        System.out.print("\nEnter selected number >>>");
        int classIndex;

        while (!sc.hasNextInt()) {
            System.out.print("Invalid input! Enter a valid NUMBER >>>");
            sc.next();
        }
        classIndex = sc.nextInt();
        if (classIndex <= 0 || classIndex >12) {
            System.out.printf("Invalid input! ");
            System.exit(0);
        }

        this.classIndex = classIndex;
        clas = clas.getClass(this.classIndex);
    }

    private void setHitPoints() {
        int hitPoints = 0;
        int perlev;
        for (int i = 0; i < level; i++) {
            int hitDice = (int) (Math.random() * 1000 % clas.getHitDice() + 1);
            perlev = hitDice + Ability.vals[2][1];
            if (perlev > 0) {
                hitPoints += hitDice + Ability.vals[2][1];
            } else {
                hitPoints += 1;
            }
        }
        this.hitPoints = hitPoints;
    }
    private int getHitPoints() {
        clas.getSkillPoints(this);
        return hitPoints;
    }

    public void rollChoice() {
        Scanner sc = new Scanner(System.in);
        String choice;
        do {
            System.out.println("\n\nSelect a STAT generating method\n" +
                    "---------------------------------\n" +
                    "1.4d6 - Roll 4 and drop the lowest\n" +
                    "2.4d6_16 - if stat is greater than 16 roll and add another 1d6\n" +
                    "3.Manual Entry\n" +
                    "4.IX method\n" +
                    "---------------------------------\n");
            System.out.print("Enter the selected NUMBER >>> ");

            int rollType;
            while (!sc.hasNextInt()) {
                System.out.print("Invalid input! Enter a NUMBER >>> ");
                sc.next();
            }
            rollType = sc.nextInt();
            String diceName;
            switch (rollType) {            //assigns the diceroll type according to user inputs
                case 1:
                    diceName = "4d6";
                    break;
                case 2:
                    diceName = "4d6_16";
                    break;
                case 3:
                    diceName = "get";
                    break;
                case 4:
                    diceName = "9d6";
                    break;
                default:
                    diceName = "default";
                    break;
            }
            generateAttrib(diceName);
            setBns(Ability.vals);
            Ability.ptintAbl();
            System.out.printf("*%-20s: [%d/%d]\n", "HIT-POINTS", getHitPoints(), getHitPoints());
            System.out.println("__________________________________\n\n");
            System.out.println("\nEnter \'Y\' to continue or \'R\' to reroll\n");
            choice = sc.nextLine();
            if (choice.equalsIgnoreCase("y")) {
                break;
            } else if (choice.equalsIgnoreCase("r")) {
                continue;
            } else {
                do {
                    System.out.print("Enter a valid input >>>>");
                    choice = sc.nextLine();
                    //looping until the sentinel value is passed by the user
                } while (!choice.equalsIgnoreCase("y") && !choice.equalsIgnoreCase("r"));
            }
        }
        while (choice.equalsIgnoreCase("r"));
    }

    private void generateAttrib(String type) {

        if (type.equals("get")) {
            Ability.getUser();
        } else if (type.equals("9d6")) {
            Ability.set9(clas);
        } else if (type.equals("4d6")) {
            Ability.set4d6();
        } else if (type.equals("4d6_16")) {
            Ability.set4d6_16();
        } else {
            System.out.println("Error not a valid roll type selection ! ");
            System.out.println("---------- *Roll Type set to default*---------");
            Ability.set4d6();
        }
        Ability.calBonus();
    }

    private void setBns(int[][] vals) {
        for (int i = 0; i < vals.length; i++) {
            vals[i][1] = ((vals[i][0] / 2) - 5);
        }
        setHitPoints();
    }

    public void getSkills() {
        int pts = clas.getSkillPoints(this);
        System.out.println("\n\n");
        skill.skillPrinter();

        System.out.println("\n\nSelect your Skills,");
        Scanner sc = new Scanner(System.in);
        slctSkills = new int[pts];
        int index = 0;
        for (int i = pts; i > 0; i--) {
            System.out.printf(" --> You have %d skill points left >>> ", i);
            try {
                slctSkills[index] = sc.nextInt();
                if (slctSkills[index] <= 0 || slctSkills[index] > 18) {
                    System.out.printf("Invalid input! ");
                    System.exit(0);
                }
                for (int j = index; j > index - 1; j--) {
                    if (slctSkills[index] == slctSkills[j]) {
                        while (skill.getSkill(slctSkills[index]).rank >= level + 3) {
                            System.out.print("Maximum rank reached ! \nSelect a different SKILL >>>");
                            slctSkills[index] = sc.nextInt();
                            if (slctSkills[index] <= 0 || slctSkills[index] > 18) {
                                System.out.println("Invalid selection");
                                System.exit(0);
                            }
                        }
                        skill.getSkill(slctSkills[index]).rank++;
                    }
                }
            } catch (InputMismatchException e) {
                System.err.printf("Error Invalid Input");
                System.exit(0);
            }
            index++;
        }
    }

    private void slctSkillPrinter() {
        int stat_affinity;

        for (int i = 0; i < slctSkills.length; i++) {
            if (slctSkills[i] == 1) {
                stat_affinity = Ability.vals[0][1];
            } else if (slctSkills[i] >= 2 && slctSkills[i] <= 4) {
                stat_affinity = Ability.vals[1][1];
            } else if (slctSkills[i] >= 5 && slctSkills[i] <= 9) {
                stat_affinity = Ability.vals[3][1];
            } else if (slctSkills[i] >= 10 && slctSkills[i] <= 14) {
                stat_affinity = Ability.vals[4][1];
            } else {
                stat_affinity = Ability.vals[5][1];

            }
            boolean dup = false;
            skill.getSkill(slctSkills[i]).setStat_affinity(stat_affinity);

            for (int j = 0; j < i; j++) {
                if (slctSkills[i] == slctSkills[j]) {
                    dup = true;
                    break;
                }
            }
            if (!dup) {
                System.out.printf("-%-20s [%d]\t|\t[%d]\n",
                        skill.getSkill(slctSkills[i]).getName(),
                        skill.getSkill(slctSkills[i]).getStat_affinity(),
                        skill.getSkill(slctSkills[i]).rank);
            }
        }
    }

    private void setCombat() {
        switch (clas.getbABType()) {
            case 1:
                bAB = level;
                break;
            case 2:
                bAB = (level * 3 / 4);
                break;
            default:
                bAB = (level / 2);
                break;
        }
        combat = bAB + Ability.vals[0][1];
        dmg = Ability.vals[0][1];
    }

    private void getCombat() {
        setCombat();
        System.out.printf("*%-10s\t:[%d]\n", "BAB", bAB);
        System.out.printf("*%-10s\t:[%d]\n", "COMBAT", combat);
        System.out.printf("*%-10s\t:[%d]\n\n", "DAMAGE", dmg);
    }


    public void printFormat() {
        System.out.printf("--------------------------------\n");
        System.out.println("\n" +
                "\\ \\/ /  ____   __  __   _____          \n" +
                " \\  /  / __ \\ / / / /  / ___/          \n" +
                " / /  / /_/ // /_/ /  / /              \n" +
                "/_/   \\____/ \\__,_/  /_/               \n" +
                "                                       \n" +
                "           __            __            \n" +
                "   _____  / /_  ____ _  / /_   _____   \n" +
                "  / ___/ / __/ / __ `/ / __/  / ___/   \n" +
                " (__  ) / /_  / /_/ / / /_   (__  )  _ \n" +
                "/____/  \\__/  \\__,_/  \\__/  /____/  ( )\n" +
                "                                    |/");
        System.out.printf("***********************************\n");
        System.out.printf("->Name\t\t:[%s]\n", name);
        System.out.printf("->Level\t\t:[%d]\n", level);
        System.out.printf("->Class\t\t:[%s]\n", clas.getName());
        System.out.printf("->HitDice\t:[%s]\n", clas.getDiceType());
        System.out.printf("-----------------------------------");
        Ability.ptintAbl();
        System.out.printf("*HP\t\t:[%d/%d]\n", getHitPoints(), getHitPoints());
        System.out.println("__________________________________");
        getCombat();
        System.out.printf("\n\n\t\t\t%s(%d)\n", "SKILL-POINTS", clas.getSkillPoints(this));
        System.out.println("-----------------------------------");
        System.out.println("Skills\t\t\t\tAffinity\tRank");
        System.out.println("-----------------------------------");

        slctSkillPrinter();
        System.out.printf("_________________________________________\n");
    }


    public void write() {
        File file = new File("DnD Character - " + name);
        FileOutputStream fos = null;
        try {
            fos = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        PrintStream ps = new PrintStream(fos);
        System.setOut(ps);
        printFormat();
        System.setOut(new PrintStream(new FileOutputStream(FileDescriptor.out)));
        ps.close();
    }

}