package com.example.javaproject;

public abstract class Lutemon {
    protected String name;
    protected String color;
    protected int attack;
    protected int defense;
    protected int experience;
    protected int health;
    protected int maxHealth;
    protected static int nextId = 1;
    protected final int id;

    // constructor for creating new lutemon
    public Lutemon(String name, String color, int attack, int defense, int maxHealth) 
    {
        this.name = name;
        this.color = color;
        this.attack = attack;
        this.defense = defense;
        this.experience = 0;
        this.maxHealth = maxHealth;
        this.health = maxHealth;
        this.id = nextId++;
    }

    // getters for lutemon properties
    public String getName() {return name;}
    public String getColor() {return color;}
    public int getAttack() { return attack; }
    public int getDefense() { return defense; }
    public int getExperience() { return experience; }
    public int getHealth() { return health; }
    public int getMaxHealth() { return maxHealth; }
    public int getId() { return id; }

    // adds experience points and potentially increases stats
    public void addExperience(int amount) 
    {
        experience += amount;
        if (experience % 2 == 0) 
        {
            if (Math.random() < 0.5) 
            {
                attack++;
            } 
            else 
            {
                defense++;
            }
        }
    }

    // handles defense against an attack from another lutemon
    public void defense(Lutemon attacker) 
    {
        int damage = Math.max(0, attacker.getAttack() - this.defense);
        this.health = Math.max(0, this.health - damage);
    }

    // heals lutemon to full health
    public void heal() 
    {
        this.health = this.maxHealth;
    }

    // trains lutemon, increasing experience
    public void train() 
    {
        this.experience++;
        if (this.experience % 10 == 0) 
        {
            if (Math.random() < 0.5) 
            {
                this.attack++;
            } 
            else 
            {
                this.defense++;
            }
        }
    }
}
