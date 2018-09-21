import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Scanner;

public class Class {

    private String name;

    private String diceType;
    private int hitDice;
    private int strDice;
    private int dexDice;
    private int conDice;
    private int intDice;
    private int wisDice;
    private int chaDice;
    private int skillPoints;
    private int bABType;
    private int index;
    private Class nextClass;
    private Class head;

    public String getDiceType() {
        return diceType;
    }

    public String getName() {
        return name;
    }

    public int getHitDice() {

        String []d= diceType.split("d");
        this.hitDice = Integer.parseInt(d[1]);
        return hitDice;
    }

    public int getStrDice() {
        return strDice;
    }

    public void setStatDice(){
        switch (this.index){
            case 1: case 8: case 10:
                setStrDice(9);setConDice(8);
                setWisDice(7);setChaDice(6);
                setDexDice(5);setIntDice(4);
                break;
            case 6: case 5: case 9:
                setDexDice(9);setWisDice(8);
                setIntDice(7);setChaDice(6);
                setStrDice(5);setConDice(4);
                break;
            case 12: case 3: case 4:
                setWisDice(9);setIntDice(8);
                setConDice(7);setChaDice(6);
                setDexDice(5);setStrDice(4);
                break;
            case 2: case 11: case 7:
                setChaDice(9);setDexDice(8);
                setConDice(7);setWisDice(6);
                setIntDice(5);setStrDice(4);
                break;
        }
    }


    private void setStrDice(int strDice) {
        this.strDice = strDice;
    }

    public int getDexDice() {
        return dexDice;
    }

    private void setDexDice(int dexDice) {
        this.dexDice = dexDice;
    }

    public int getConDice() {
        return conDice;
    }

    private void setConDice(int conDice) {
        this.conDice = conDice;
    }

    public int getIntDice() {
        return intDice;
    }

    private void setIntDice(int intDice) {
        this.intDice = intDice;
    }

    public int getWisDice() {
        return wisDice;
    }

    private void setWisDice(int wisDice) {
        this.wisDice = wisDice;
    }

    public int getChaDice() {
        return chaDice;
    }

    private void setChaDice(int chaDice) {
        this.chaDice = chaDice;
    }

    public int getSkillPoints(CharacterDnD c) {
        int skillpoints = 0;
        switch (c.classIndex){
            case 1: case 4: case 5:
                skillpoints = ((4+Ability.vals[3][1]));
                break;
            case 2: case 9:
                skillpoints = ((6+Ability.vals[3][1]));
                break;
            case 3: case 8: case 10: case 11: case 12:
                skillpoints = ((2+Ability.vals[3][1]));
                break;
            case 6:
                skillpoints = ((8+Ability.vals[3][1]));
                break;
        }
        if(skillpoints<1){
            skillpoints =1;
        }

        int lvl1 = skillpoints*4;
        int perlev=0;
        for (int i = 1; i <c.getLevel() ; i++) {
            perlev+=skillpoints;
        }

        skillPoints = perlev+lvl1;
        return skillPoints;
    }



    public int getbABType() {
        switch (index) {
            case 1: case 8:
            case 10: case 9:
                bABType = 1;
                break;
            case 2: case 3:
            case 4: case 5:
                bABType = 2;
                break;
            default:
                bABType = 3;
                break;
        }
        return bABType;
    }





    private void add(Class cClass) {
        this.name = cClass.name;
        this.nextClass = null;

        if (head == null) {
            head = cClass;
        } else {
            Class temp = head;
            while (temp.nextClass != null) {
                temp = temp.nextClass;
            }
            temp.nextClass = cClass;
        }
    }

    public  void printClasses(){
        addClassFile();
        Class clas = head;
        int index=1;

        while (clas != null){
            clas.index = index;
            System.out.println(index +"."+clas.name);
            clas = clas.nextClass;
            index++;
        }
    }

    private void addClassFile(){
        URL url = getClass().getResource("Classes.txt");
        File clsFile = new File(url.getPath());
        //File clsFile = new File("C:\\Users\\Azhar\\IdeaProjects\\FinalCw2Final\\src\\Classes.txt");
        Scanner fReader;
        try {
            fReader = new Scanner(clsFile);

            while (fReader.hasNext()) {
                Class c = new Class();
                c.name = fReader.next();
                c.diceType = (fReader.nextLine());
                add(c);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Class getClass(int index) {
        Class cClass;
        cClass = head;
        for (int i = 1; i < index; i++) {
            cClass = cClass.nextClass;
        }
        return cClass;
    }






}
