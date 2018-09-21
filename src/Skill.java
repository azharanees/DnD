import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.Scanner;

public class Skill {

    private String name;

    private String short_desc;
    private int stat_affinity;
    private Skill next_Skill;
    private Skill head;
    public int rank;


    public int getStat_affinity() {
        return stat_affinity;
    }
    public void setStat_affinity(int stat_affinity) {
        this.stat_affinity = stat_affinity;
    }

    public String getName() {
        return name;
    }

    private void add(Skill list) {
        this.name = list.name;
        this.short_desc = list.short_desc;
        list.next_Skill = null;

        if (head == null) {
            head = list;
        } else {
            Skill s = head;
            while (s.next_Skill != null) {
                s = s.next_Skill;
            }
            s.next_Skill = list;
        }
    }

    private void printList() {

        Skill list = head;

        int i = 1;
        while (list != null) {
            System.out.println(i + ". " + list.name + list.short_desc);
            list = list.next_Skill;
            i++;
        }
    }

    public void skillPrinter() {
        URL url = getClass().getResource("Skills.txt");
        File sklfile = new File(url.getPath());
        //File sklfile = new File("C:\\Users\\Azhar\\IdeaProjects\\FinalCw2Final\\src\\Skills.txt");
        Scanner fReader;
        try {
            fReader = new Scanner(sklfile);

            while (fReader.hasNext()) {
                Skill newSkill = new Skill();
                String skl = fReader.next();
                String des = fReader.nextLine();
                newSkill.name = skl;
                newSkill.short_desc = des;
                add(newSkill);
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        printList();
    }

    public Skill getSkill(int index) {
        Skill skill;
        skill = head;

        for (int i = 1; i < index; i++) {
            skill = skill.next_Skill;
        }
        return skill;
    }

}











