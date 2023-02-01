package fishes;

import java.util.Date;
import java.util.Random;
import java.util.Vector;

public class Aquarium {
    public static void main(String[] args) {
        Aquarium.starting();
    }

    public static volatile Vector<Fish> maleFishes = new Vector<>();
    public static volatile Vector<Fish> femaleFishes = new Vector<>();
    static Random random = new Random();

    public static void starting() {
        buyFish();
        putFishInAquarium();
        while (maleFishes.size() > 0 && femaleFishes.size() > 0) {
            for (int i = 0; i < maleFishes.size(); i++) {
                for (int j = 0; j < femaleFishes.size(); j++) {

                    if (maleFishes.get(i).getLifeTime().getTime() > System.currentTimeMillis() && femaleFishes.get(j).getLifeTime().getTime() > System.currentTimeMillis()) {
                        if (maleFishes.get(i).getX().equals(femaleFishes.get(j).getX()) && maleFishes.get(i).getY().equals(femaleFishes.get(j).getY())) {
                            System.out.println("Falemale fish location x=" + femaleFishes.get(j).getX() + " y=" + femaleFishes.get(j).getY());
                            System.out.println("    Male fish location x=" + maleFishes.get(i).getX() + " y=" + maleFishes.get(i).getY());
                            System.out.println("----------------------------------");
                            addNewFish();
                        }
                    } else {
                        if (maleFishes.get(i).getLifeTime().getTime() < System.currentTimeMillis()) {
                            System.out.println("MALE FISH DIED. lifeTime - " + maleFishes.get(i).getLifeTime().toString().substring(11, 19) +
                                    ", currentTime - " + new Date(System.currentTimeMillis()).toString().substring(11, 19));
                            maleFishes.remove(i);
                            break;
                        }
                        if (femaleFishes.get(j).getLifeTime().getTime() < System.currentTimeMillis()) {
                            System.out.println("FEMALE FISH DIED. lifeTime - " + femaleFishes.get(j).getLifeTime().toString().substring(11, 19) +
                                    ", currentTime - " + new Date(System.currentTimeMillis()).toString().substring(11, 19));
                            femaleFishes.remove(j);
                            break;
                        }
                    }
                }
            }
            System.out.println("Count Female fish:" + femaleFishes.size());
            System.out.println("  Count Male fish:" + maleFishes.size());
            try {
                Thread.sleep(15000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("----------------------------------");
        }
        System.out.println("Warning:  there are fish of the same gender in the aquarium");
    }

    private static void putFishInAquarium() {
        maleFishes.forEach(Thread::start);
        femaleFishes.forEach(Thread::start);
    }

    public static void addNewFish() {
        Fish fish = new Fish(random.nextBoolean() ? "Male" : "Female", expirationTime());
        if (fish.getGender().equals("Male")) {
            maleFishes.add(fish);
            System.out.println("New MALE fish added");
        }
        if (fish.getGender().equals("Female")) {
            femaleFishes.add(fish);
            System.out.println("New FEMALE fish added");
        }
    }

    public static void buyFish() {
        for (int i = 0; i < random.nextInt(8) + 8; i++) {
            Fish fish = new Fish(random.nextBoolean() ? "Male" : "Female", expirationTime());
            if (fish.getGender().equals("Male")) maleFishes.add(fish);
            if (fish.getGender().equals("Female")) femaleFishes.add(fish);
        }
    }

    public static Date expirationTime() {
        int lifeTime = 50000;
        long date = new Date().getTime() + (long) new Random().nextInt(lifeTime) + lifeTime;
        return new Date(date);
    }
}
