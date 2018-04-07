package me.sheiun.dice.ability;

/**
 *
 * @author SheiUn
 */
public abstract class Ability {
    protected String name;
    protected int level;
    protected int cost;
    protected boolean learned;
    protected String getName() {
        return name;
    }
    protected int getLevel() {
        return level;
    }
    protected int cost() {
        return cost;
    }
    protected boolean hasSkill() {
        return learned;
    }
    protected abstract void useSkill();
    protected abstract void upgradeSkill(); /*  */
}
