package fishes;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

public class Fish extends Thread{
    static Random random = new Random();
    private String gender;
    private Date lifeTime;
    private volatile Integer x;
    private volatile Integer y;

    public Fish(String gender, Date lifeTime) {
        this.gender = gender;
        this.lifeTime = lifeTime;
        this.x = random.nextInt(4)+1;
        this.y = random.nextInt(4)+1;
    }

    @Override
    public void run() {
        do {
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            String name = Thread.currentThread().getName();
            switch (random.nextInt(4)) {
                case 0:
                    if (this.getX() != 5) this.setX(this.getX() + 1);
                    break;
                case 1:
                    if (this.getX() != 1) this.setX(this.getX() - 1);
                    break;
                case 2:
                    if (this.getY() != 5) this.setY(this.getY() + 1);
                    break;
                case 3:
                    if (this.getY() != 1) this.setY(this.getY() - 1);
                    break;
            }
        } while (this.getLifeTime().getTime() > System.currentTimeMillis());
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Date getLifeTime() {
        return lifeTime;
    }

    public void setLifeTime(Date lifeTime) {
        this.lifeTime = lifeTime;
    }

    public Integer getX() {
        return x;
    }

    public void setX(Integer x) {
        this.x = x;
    }

    public Integer getY() {
        return y;
    }

    public void setY(Integer y) {
        this.y = y;
    }

    public synchronized String toString() {
        return "Gender = " + getGender() + ", " +
                "LifeTime = " + new SimpleDateFormat("dd-M-yyyy hh:mm:ss").format(getLifeTime()) + ", " +
                "Coordinates of the location in the aquarium x:" + getX() + ", y:" + getY() + ".";
    }
}
