// WeaponStore.java
package RPG;

import java.util.ArrayList;

public class WeaponStore {
    private ArrayList<Weapon> weapons;

    public WeaponStore() {
        this.weapons = new ArrayList<>();
        this.weapons.add(new Axe());
        this.weapons.add(new Swords());
        this.weapons.add(new Bow());
    }

    public ArrayList<Weapon> getWeapons() {
        return this.weapons;
    }

    public void printWeapons() {
        int i = 0;
        for (Weapon w : this.weapons) {
            System.out.println("[" + i + "]" + w.toString() + "\n" + w.asciiArt());
            i++;
        }
    }

    public void buyWeapon(int weaponIndex, Player player) {
        Weapon chosenWeapon = weapons.get(weaponIndex);
        if (player.getMoney() >= chosenWeapon.getPrice()) {
            player.subtractMoney(chosenWeapon.getPrice());
            System.out.println("Weapon purchased: " + chosenWeapon.getName());
        } else {
            System.out.println("Not enough money!");
        }
    }
}
