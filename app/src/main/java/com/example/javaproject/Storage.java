package com.example.javaproject;

import java.util.ArrayList;
import java.util.HashMap;

public class Storage {
    private static Storage storage = null;
    private final ArrayList<Lutemon> lutemonsInStorage;
    private final ArrayList<Lutemon> lutemonsInGym;
    private final ArrayList<Lutemon> lutemonsInBattle;
    private final HashMap<Integer, Lutemon> lutemons;

    // private constructor for singleton pattern
    private Storage() 
    {
        lutemonsInStorage = new ArrayList<>();
        lutemonsInGym = new ArrayList<>();
        lutemonsInBattle = new ArrayList<>();
        lutemons = new HashMap<>();
    }

    // gets singleton instance of storage
    public static Storage getInstance() 
    {
        if (storage == null) 
        {
            storage = new Storage();
        }
        return storage;
    }

    // adds lutemon to storage
    public void addLutemon(Lutemon lutemon) 
    {
        lutemons.put(lutemon.getId(), lutemon);
        lutemonsInStorage.add(lutemon);
    }

    // gets list of lutemons in storage
    public ArrayList<Lutemon> getLutemonsInStorage() 
    {
        return lutemonsInStorage;
    }

    // gets list of lutemons in gym
    public ArrayList<Lutemon> getLutemonsInGym() 
    {
        return lutemonsInGym;
    }

    // gets list of lutemons in battle
    public ArrayList<Lutemon> getLutemonsInBattle() 
    {
        return lutemonsInBattle;
    }

    // moves lutemon from storage to gym
    public void moveToGym(Lutemon lutemon) 
    {
        lutemonsInStorage.remove(lutemon);
        lutemonsInGym.add(lutemon);
    }

    // moves lutemon from gym to storage
    public void moveToStorage(Lutemon lutemon) 
    {
        lutemonsInGym.remove(lutemon);
        lutemonsInStorage.add(lutemon);
    }

    // moves lutemon to battle area
    public void moveToBattle(Lutemon lutemon) 
    {
        if (lutemonsInStorage.contains(lutemon)) 
        {
            lutemonsInStorage.remove(lutemon);
        } 
        else if (lutemonsInGym.contains(lutemon)) 
        {
            lutemonsInGym.remove(lutemon);
        }
        lutemonsInBattle.add(lutemon);
    }

    // moves lutemon from battle to storage
    public void moveFromBattleToStorage(Lutemon lutemon) 
    {
        lutemonsInBattle.remove(lutemon);
        lutemonsInStorage.add(lutemon);
        lutemon.heal();
    }

    // gets lutemon by id
    public Lutemon getLutemon(int id) 
    {
        return lutemons.get(id);
    }
}
