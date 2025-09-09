import java.util.*;

class Rule {
    List<String> conditions; // IF part
    String result;           // THEN part

    Rule(List<String> conditions, String result) {
        this.conditions = conditions;
        this.result = result;
    }
}

public class BackwardChainingEasy {

    // Build an index: result -> list of rules producing it
    static Map<String, List<Rule>> buildIndex(List<Rule> rules) {
        Map<String, List<Rule>> index = new HashMap<>();
        for (Rule r : rules) {
            index.computeIfAbsent(r.result, k -> new ArrayList<>()).add(r);
        }
        return index;
    }

    /*
     backwardProve:
     - goal: symbol to prove
     - knownFacts: current set of known facts
     - rulesByResult: rules indexed by result
     - provedSet: cache of already proved symbols
     - inProgress: set of symbols currently being checked (cycle detection)
     - steps: trace of steps for printing
    */
    static boolean backwardProve(String goal,
                                 Set<String> knownFacts,
                                 Map<String, List<Rule>> rulesByResult,
                                 Set<String> provedSet,
                                 Set<String> inProgress,
                                 List<String> steps) {

        // 1) If already known
        if (knownFacts.contains(goal)) {
            steps.add("KNOWN FACT: " + goal);
            provedSet.add(goal);
            return true;
        }

        // 2) If proved earlier
        if (provedSet.contains(goal)) {
            steps.add("ALREADY PROVED: " + goal);
            return true;
        }

        // 3) Cycle detection
        if (inProgress.contains(goal)) {
            steps.add("CYCLE DETECTED: " + goal);
            return false;
        }

        // 4) Check rules that can give the goal
        if (!rulesByResult.containsKey(goal)) {
            steps.add("NO RULES PRODUCE: " + goal);
            return false;
        }

        inProgress.add(goal);
        steps.add("TRY PROVING: " + goal);

        for (Rule rule : rulesByResult.get(goal)) {
            // Show rule being tried
            StringBuilder s = new StringBuilder(" Try rule: ");
            for (int i = 0; i < rule.conditions.size(); i++) {
                if (i > 0) s.append(" ∧ ");
                s.append(rule.conditions.get(i));
            }
            s.append(" ⇒ ").append(rule.result);
            steps.add(s.toString());

            boolean allOk = true;
            for (String cond : rule.conditions) {
                boolean condProved = backwardProve(cond, knownFacts, rulesByResult, provedSet, inProgress, steps);
                if (!condProved) {
                    steps.add(" -> condition FAILED: " + cond);
                    allOk = false;
                    break;
                } else {
                    steps.add(" -> condition PROVED: " + cond);
                }
            }

            if (allOk) {
                // All conditions proved
                knownFacts.add(goal);
                provedSet.add(goal);
                steps.add("RULE SUCCEEDED -> proved: " + goal);
                inProgress.remove(goal);
                return true;
            } else {
                steps.add("Rule failed for: " + goal);
            }
        }

        inProgress.remove(goal);
        steps.add("ALL RULES FAILED for: " + goal);
        return false;
    }

    public static void main(String[] args) {
        // Example Rules:
        // 1. Fever ∧ Cough ⇒ ViralInfection
        // 2. ViralInfection ∧ BodyAche ⇒ FluSymptoms
        // 3. FluSymptoms ⇒ Flu
        List<Rule> rules = new ArrayList<>();
        rules.add(new Rule(Arrays.asList("Fever", "Cough"), "ViralInfection"));
        rules.add(new Rule(Arrays.asList("ViralInfection", "BodyAche"), "FluSymptoms"));
        rules.add(new Rule(Arrays.asList("FluSymptoms"), "Flu"));

        Set<String> knownFacts = new HashSet<>(Arrays.asList("Fever", "Cough", "BodyAche"));
        Map<String, List<Rule>> rulesByResult = buildIndex(rules);

        String goal = "Flu";
        List<String> steps = new ArrayList<>();
        Set<String> provedSet = new HashSet<>();
        Set<String> inProgress = new HashSet<>();

        boolean result = backwardProve(goal, knownFacts, rulesByResult, provedSet, inProgress, steps);

        System.out.println("Backward Chaining Trace:");
        for (String line : steps) {
            System.out.println(line);
        }

        System.out.println("\nResult: " + (result ? "PROVED" : "NOT PROVED") + " for goal: " + goal);
        System.out.print("Final facts: ");
        for (String f : knownFacts) System.out.print(f + " ");
        System.out.println();
    }
}

//javac BackwardChainingEasy.java
//java BackwardChainingEasy