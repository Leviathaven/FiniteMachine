package com.machine;

import java.util.*;

public class Machine {
    private boolean isWithEpsilon;
    private boolean isDone;
    private int id;
    private List<String> states;
    private List<String> alphabet;
    private List<String> finalStates;
    private String currentState;
    private String startingState;
    private Map<String, Map<String, String>> tableOfTransitions;

    public Machine() {}

    public boolean isDone() {
        return isDone;
    }

    public void setDone(boolean done) {
        isDone = done;
    }

    public Machine(List<String> states, List<String> alphabet, List<String> finalStates, String startingState, Map<String, Map<String, String>> tableOfTransitions, int id) {
        if (alphabet.contains("eps")) {
            isWithEpsilon = true;
        }
        else {
            isWithEpsilon = false;
        }
        this.isDone = false;
        this.states = states;
        this.alphabet = alphabet;
        this.finalStates = finalStates;
        this.startingState = startingState;
        this.currentState = startingState;
        this.tableOfTransitions = tableOfTransitions;
        this.id = id;
    }



    public Machine(Machine original, int id, String currentState, boolean ifFinal) {
        this.isWithEpsilon = original.isWithEpsilon();
        this.isDone = ifFinal;
        this.states = original.getStates();
        this.alphabet = original.getAlphabet();
        this.finalStates = original.getFinalStates();
        this.startingState = original.getStartingState();
        this.currentState = currentState;
        this.tableOfTransitions = original.getTableOfTransitions();
        this.id = id;
    }

    public List<String> getStates() {
        return states;
    }

    public void setStates(List<String> states) {
        this.states = states;
    }

    public List<String> getAlphabet() {
        return alphabet;
    }

    public void setAlphabet(List<String> alphabet) {
        this.alphabet = alphabet;
    }

    public List<String> getFinalStates() {
        return finalStates;
    }

    public void setFinalStates(List<String> finalStates) {
        this.finalStates = finalStates;
    }

    public String getCurrentState() {
        return currentState;
    }

    public void setCurrentState(String state) {
        this.currentState = state;
    }

    public Map<String, Map<String, String>> getTableOfTransitions() {
        return tableOfTransitions;
    }

    public void setTableOfTransitions(Map<String, Map<String, String>> tableOfTransitions) {
        this.tableOfTransitions = tableOfTransitions;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getStartingState() {
        return startingState;
    }

    public void setStartingState(String startingState) {
        this.startingState = startingState;
    }

    public boolean isWithEpsilon() {
        return isWithEpsilon;
    }

    public void setWithEpsilon(boolean withEpsilon) {
        isWithEpsilon = withEpsilon;
    }

    public void workWithSymb(String symbol, boolean ifFinal) {
        System.out.println("Currently working is machihe " + this.getId());
        System.out.println(this.currentState + " " + symbol);
        String currState = this.currentState;

        Map<String, String> dataForCurrState = this.tableOfTransitions.get(currState);
        String infoForState = dataForCurrState.get(symbol);
        if (infoForState.indexOf("_") != -1) {
            this.setCurrentState(infoForState.split("_")[0]);
        }
        else {
            this.setCurrentState(infoForState);
        }
        if(this.currentState.equals("-")) {
            this.setDone(true);
        }
        if (ifFinal) {
            this.setDone(true);
        }
    }

}