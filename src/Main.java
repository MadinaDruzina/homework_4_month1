
import javax.swing.*;
import java.util.Random;

public class Main {
    public static int bossHealth = 700;
    public static int bossDamage = 50;
    public static String bossDefenceType;
    public static int[] heroesHealth = {250, 270, 280, 300, 450, 300, 250, 250};
    public static int[] heroesDamage = {25, 25, 25, 0, 15, 25, 25, 25};
    public static String[] heroesAttackType = {"Physical", "Magical", "Kinetic", "Medic", "Golem", "Berserk", "Thor", "Luccy"};
    public static int roundNumber = 0;

    public static void main(String[] args) {
        printStatistics();
        while (!isGameFinished()) {
            playRound();
        }
    }

    public static void playRound() {
        roundNumber++;
        chooseBossDefence();

        bossHits();

        heroesHit();
        medicHelp();
        golemHelp();
        berserkHits();
        thorHits();
        Luccy();
        printStatistics();
    }

    private static void thorHits() {
        Random random = new Random();
        boolean thors = random.nextBoolean();
        if (heroesHealth[6] > 0 && thors == true){
            bossDamage = 0;
            System.out.println("Воss оглушен в этом раунде " + thors);
        }
        else {
            bossDamage = 50;
        }
    }


    public static void Luccy() {
        Random random = new Random();
        boolean rand = random.nextBoolean();
        if (heroesHealth[7] > 0 && rand == true) {
            heroesHealth[7] += 40;
            System.out.println("Dodged damage - " + rand);
        }
    }
    private static void berserkHits() {
        int gerBerserkDemage = bossDamage / 2;
        if (heroesHealth[5] > 0)
        {
            heroesDamage[5] += gerBerserkDemage;

            System.out.println("Сила удара Берсеркер: " + heroesDamage[5]);


        }

    }

    private static void golemHelp() {
        int getDemage = bossDamage / 5;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[4] > 0 && heroesHealth[i] > 0 && heroesHealth[4] != heroesHealth[i]) {
                heroesHealth[i] = heroesHealth[i] + getDemage;
                heroesHealth[4] = heroesHealth[4] - getDemage;

            }
        }System.out.println("Голем принял на себя удар: " + getDemage);
    }

    private static void medicHelp() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (i == 3) {
                continue;
            }

            if (heroesHealth[i] > 0 && heroesHealth[i] < 100 && heroesHealth[3] > 0) {
                Random random = new Random();
                int treat = random.nextInt(100);
                heroesHealth[i] += treat;
                System.out.println("medic viechil: " + heroesAttackType[i] + treat);
                break;
            }
        }
    }


    public static void printStatistics() {
        System.out.println("ROUND " + roundNumber + " ---------------");
        /*String defence;
        if (bossDefenceType == null) {
            defence = "No defence";
        } else {
            defence = bossDefenceType;
        }*/
        System.out.println("Boss health: " + bossHealth + "; damage: "
                + bossDamage + "; defence: " + (bossDefenceType == null ? "No defence" : bossDefenceType));
        for (int i = 0; i < heroesHealth.length; i++) {
            System.out.println(heroesAttackType[i] + " health: " + heroesHealth[i] + "; damage: "
                    + heroesDamage[i]);
        }
    }

    public static void chooseBossDefence() {
        Random random = new Random();
        int randomIndex = random.nextInt(heroesAttackType.length); // 0,1,2
        bossDefenceType = heroesAttackType[randomIndex];
    }

    public static boolean isGameFinished() {
        if (bossHealth <= 0) {
            System.out.println("Heroes won!!!");
            return true;
        }
        /*if (heroesHealth[0] <= 0 && heroesHealth[1] <= 0 && heroesHealth[2] <= 0) {
            System.out.println("Boss won!!!");
            return true;
        }
        return false;*/
        boolean allHeroesDead = true;
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                allHeroesDead = false;
                break;
            }
        }
        if (allHeroesDead) {
            System.out.println("Boss won!!!");
        }
        return allHeroesDead;
    }

    public static void bossHits() {
        for (int i = 0; i < heroesHealth.length; i++) {
            if (heroesHealth[i] > 0) {
                if (heroesHealth[i] - bossDamage < 0) {
                    heroesHealth[i] = 0;
                } else {
                    heroesHealth[i] = heroesHealth[i] - bossDamage;
                }
            }
        }
    }

    public static void heroesHit() {
        for (int i = 0; i < heroesDamage.length; i++) {
            if (heroesHealth[i] > 0 && bossHealth > 0) {
                int hit = heroesDamage[i];
                if (heroesAttackType[i] == bossDefenceType) {
                    Random random = new Random();
                    int coeff = random.nextInt(6) + 2; // 2,3,4,5,6,7
                    hit = heroesDamage[i] * coeff;
                    System.out.println("Critical damage: " + hit);
                }
                if (bossHealth - hit < 0) {
                    bossHealth = 0;
                } else {
                    bossHealth = bossHealth - hit;
                }

            }
        }
    }
}
