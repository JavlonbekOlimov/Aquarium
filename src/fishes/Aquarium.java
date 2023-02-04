package fishes;

import java.util.Date;
import java.util.Random;
import java.util.Vector;

public class Aquarium {

    private static Integer maxCapasity = 1000;

    public static void main(String[] args) throws InterruptedException {
        Aquarium.starting();
    }

    public static volatile Vector<Fish> maleFishes = new Vector<>();
    public static volatile Vector<Fish> femaleFishes = new Vector<>();
    static Random random = new Random();

    public static void starting() throws InterruptedException {
        buyFish();
        putFishInAquarium();
        while (!maleFishes.isEmpty() || femaleFishes.isEmpty()) {
            System.out.println("Count Female fish:" + femaleFishes.size());
            System.out.println("  Count Male fish:" + maleFishes.size());
            for (int i = 0; i < maleFishes.size(); i++) {
                for (int j = 0; j < femaleFishes.size(); j++) {

                    if (maleFishes.get(i).getLifeTime().getTime() > System.currentTimeMillis() && femaleFishes.get(j).getLifeTime().getTime() > System.currentTimeMillis()) {
                        if (maleFishes.get(i).getX().equals(femaleFishes.get(j).getX()) && maleFishes.get(i).getY().equals(femaleFishes.get(j).getY())) {
                            System.out.println("Falemale fish location x=" + femaleFishes.get(j).getX() + " y=" + femaleFishes.get(j).getY());
                            System.out.println("    Male fish location x=" + maleFishes.get(i).getX() + " y=" + maleFishes.get(i).getY());
                            System.out.println("----------------------------------");
                            addNewFish();
                            if (maleFishes.size() + femaleFishes.size() > maxCapasity) {
                                killFish();
                                Thread.sleep(3000);
                                maleFishes = null;
                                femaleFishes = null;
                                System.err.println("KILL FISHES");
                                System.exit(0);
                            }
                        }
                    } else {
                        if (maleFishes.get(i).getLifeTime().getTime() < System.currentTimeMillis()) {

                            System.out.println("MALE FISH DIED. lifeTime - " + maleFishes.get(i).getLifeTime().toString().substring(11, 19) +
                                    ", currentTime - " + new Date(System.currentTimeMillis()).toString().substring(11, 19));
                            maleFishes.get(i).stop();
                            maleFishes.remove(i);

                            break;
                        }
                        if (femaleFishes.get(j).getLifeTime().getTime() < System.currentTimeMillis()) {
                            System.out.println("FEMALE FISH DIED. lifeTime - " + femaleFishes.get(j).getLifeTime().toString().substring(11, 19) +
                                    ", currentTime - " + new Date(System.currentTimeMillis()).toString().substring(11, 19));
                            femaleFishes.get(i).stop();
                            femaleFishes.remove(j);
                            break;
                        }
                    }
                }

                try {
                    Thread.sleep(1500);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            try {
                Thread.sleep(15000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("----------------------------------");
        }
        System.out.println("Warning:  !!!");
    }

    private static void putFishInAquarium() {
        maleFishes.forEach(Thread::start);
        femaleFishes.forEach(Thread::start);
    }

    public static void addNewFish() {
        for (int i = 0; i < random.nextInt(5) + 1; i++) {
            Fish fish = new Fish(random.nextBoolean() ? "Male" : "Female", expirationTime());
            fish.start();
            if (fish.getGender().equals("Male")) {
                maleFishes.add(fish);
                System.out.println("New MALE fish added");
            }
            if (fish.getGender().equals("Female")) {
                femaleFishes.add(fish);
                System.out.println("New FEMALE fish added");
            }
        }
    }

    public static void killFish() {
        maleFishes.forEach(Thread::stop);
        femaleFishes.forEach(Thread::stop);
    }

    public static void buyFish() {
        for (int i = 0; i < random.nextInt(maxCapasity - 5) + 5; i++) {
            Fish fish = new Fish(random.nextBoolean() ? "Male" : "Female", expirationTime());
            if (fish.getGender().equals("Male")) maleFishes.add(fish);
            if (fish.getGender().equals("Female")) femaleFishes.add(fish);
        }
    }

    public static Date expirationTime() {
        int lifeTime = 500;
        long date = new Date().getTime() + (long) new Random().nextInt(lifeTime) + lifeTime;
        return new Date(date);
    }
}
