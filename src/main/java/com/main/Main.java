package com.main;

import com.readfiles.ReadFile;
import com.machine.Machine;
import java.io.*;
import java.util.*;

public class Main {

    public static void main(String[] args)  throws IOException {
        System.out.println("Put in the name of the file: ");

        Scanner in = new Scanner(System.in);

        String path = in.nextLine();
        String filePath = path + ".txt";
        InputStream ins = null;
        Reader r = null;
        BufferedReader br = null;
        try {
            String currLine;
            ins = new FileInputStream(filePath);
            r = new InputStreamReader(ins, "UTF-8");
            br = new BufferedReader(r);
            String states = br.readLine();
            System.out.println(states);
            List<String> allStates = Arrays.asList(states.split(" "));
            String alphabet = br.readLine();
            List<String> alphabetList = Arrays.asList(alphabet.split(" "));
            System.out.println(alphabet);
            String finalStates = br.readLine();
            List<String> allFinalStates = Arrays.asList(finalStates.split(" "));
            System.out.println(finalStates);
            String startingState = br.readLine();
            Map<String, Map<String, String>> transitionTable = new HashMap<String, Map<String, String>>();
            System.out.println(startingState);
            int i = 4;
            while ((currLine = br.readLine()) != null) {
                System.out.println(currLine);
                String[] allInfo = currLine.split(" ");
                String currState = allStates.get(i - 4);
                Map<String, String> currStateTransitions = new HashMap<>();
                int j = 0;
                for (String letter : alphabetList) {
                    currStateTransitions.put(letter, allInfo[j]);
                    j++;
                }
                transitionTable.put(currState, currStateTransitions);
                i++;
            }
            int id = 0;
            Machine finiteUndefinedMachine = new Machine(allStates, alphabetList, allFinalStates, startingState, transitionTable, id);
            List<Machine> allMachines = new ArrayList<Machine>();
            allMachines.add(finiteUndefinedMachine);
            System.out.println("Input a line made of alphabet");
            String str = in.nextLine();
            boolean flagIfAnyNoResult = false;
            boolean flagIfAnyFalse = false;
            int counter = 0;
            String[] strDiv = str.split("");
            for (String symbol : strDiv) {
                if (!flagIfAnyNoResult) {
                    List<Machine> newMachines = new ArrayList<Machine>();
                    for (Machine machine : allMachines) {
                        if (!machine.isDone()) {
                            HashSet<String> statesAlreadyMet = new HashSet<>();
                            if (machine.getTableOfTransitions().get(machine.getCurrentState()).containsKey("eps")) {
                                String newStates = machine.getTableOfTransitions().get(machine.getCurrentState()).get("eps");
                                while (!newStates.equals("-")) {
                                    boolean ifLast = counter == str.length() - 1;
                                    if (newStates.indexOf("_") != -1) {
                                        String[] changingStates = newStates.split("_");
                                        for (int j = 1; j < changingStates.length; j++) {
                                            id++;
                                            Machine newMachine = new Machine(machine, id, changingStates[j], ifLast);
                                            if (ifLast) {
                                                newMachine.setDone(true);
                                            }
                                            newMachines.add(newMachine);
                                        }
                                    }

                                    machine.workWithSymb("eps", ifLast);
                                    String currState = machine.getCurrentState();
                                    if (!statesAlreadyMet.contains(currState)) {
                                        statesAlreadyMet.add(currState);
                                    } else {
                                        break;
                                    }
                                    if (machine.getCurrentState().equals("-")) {
                                        flagIfAnyNoResult = true;
                                    }

                                    newStates = machine.getTableOfTransitions().get(machine.getCurrentState()).get("eps");
                                }
                            }
                        }
                    }
                    allMachines.addAll(newMachines);
                    List newMachines1 = new ArrayList<Machine>();

                    for (Machine machine : allMachines) {
                        String newStates = machine.getTableOfTransitions().get(machine.getCurrentState()).get(symbol);
                        System.out.println(newStates);
                        boolean ifLast = counter == str.length() - 1;
                        if (newStates.indexOf("_") != -1) {
                            String[] changingStates = newStates.split("_");
                            for (int j = 1; j < changingStates.length; j++) {
                                id++;
                                Machine newMachine = new Machine(machine, id, changingStates[j], ifLast);
                                if (ifLast) {
                                    newMachine.setDone(true);
                                }
                                newMachines1.add(newMachine);
                            }
                        }

                        machine.workWithSymb(symbol, ifLast);
                        if (machine.getCurrentState().equals("-")) {
                            flagIfAnyNoResult = true;
                        }
                    }
                    allMachines.addAll(newMachines1);
                    counter++;
                }
            }

            for (Machine machine : allMachines) {
                if (!machine.isDone()) {
                    System.out.println("Last run for machine #" + machine.getId() + " with state " + machine.getCurrentState());
                    machine.workWithSymb(strDiv[strDiv.length - 1], true);
                }
                if (!machine.getFinalStates().contains(machine.getCurrentState())) {
                    flagIfAnyFalse = true;
                }
            }



            if (flagIfAnyNoResult) {
                System.out.println("NoResult for the machine");
            }
            else if(flagIfAnyFalse) {
                System.out.println("false for the machine");
            }
            else {
                System.out.println("true for the machine");
            }
        }
        catch (Exception e)
        {
            System.err.println(e.getMessage());
        }
        finally {
            if (br != null) { try { br.close(); } catch(Throwable t) {  } }
            if (r != null) { try { r.close(); } catch(Throwable t) { } }
            if (ins != null) { try { ins.close(); } catch(Throwable t) {  } }
        }


    }


}