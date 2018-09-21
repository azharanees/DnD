import java.util.Scanner;

public class Main {

    public static void main(String[] args) {
        String chc2;
        do{
            CharacterDnD character = new CharacterDnD();
            character.getName();
            character.setLevel();
            character.setClass();
            character.rollChoice();
            character.getSkills();
            character.printFormat();
            Scanner sc = new Scanner(System.in);
            System.out.print("\n\nDo you want to save the stats [Y/N]?\n" +
                    "Press 'Y' to save or " +
                    "Any key to continue >>> ");
            String chc1 = sc.next();
            if (chc1.equalsIgnoreCase("y")) {
                character.write();
            }
            System.out.print("\nPress 'N' to create a new Character Or any key to exit >>> ");
            chc2 = sc.next();
        } while (chc2.equalsIgnoreCase("n"));
    }
}
