import java.util.*;

class Rule {
    List<String> conditions;  // IF part
    String result;            // THEN part
    boolean used = false;     // to prevent reusing once fired

    Rule(List<String> conditions, String result) {
        this.conditions = conditions;
        this.result = result;
    }
}

public class ForwardChainingEasy {
    // Function to check if all conditions of a rule are in facts
    static boolean canFire(Rule rule, List<String> facts) {
        for (String cond : rule.conditions) {
            if (!facts.contains(cond)) {
                return false; // one condition missing
            }
        }
        return true;
    }

    public static void main(String[] args) {
        // Example Rules:
        // 1. P ∧ Q ⇒ R
        // 2. R ∧ S ⇒ T
        // 3. T ⇒ U
        List<Rule> rules = new ArrayList<>();
        rules.add(new Rule(Arrays.asList("P", "Q"), "R"));
        rules.add(new Rule(Arrays.asList("R", "S"), "T"));
        rules.add(new Rule(Arrays.asList("T"), "U"));

        // Initial facts
        List<String> facts = new ArrayList<>(Arrays.asList("P", "Q", "S"));

        String goal = "U"; // final thing we want
        boolean derived = false;

        System.out.print("Initial facts: ");
        for (String f : facts) System.out.print(f + " ");
        System.out.println("\n");

        // Forward chaining loop
        boolean addedNew;
        do {
            addedNew = false;
            for (Rule rule : rules) {
                if (!rule.used && canFire(rule, facts)) {
                    // fire the rule
                    System.out.print("Rule fired: ");
                    for (String c : rule.conditions) System.out.print(c + " ");
                    System.out.println("=> " + rule.result);

                    facts.add(rule.result);
                    rule.used = true;
                    addedNew = true;

                    if (rule.result.equals(goal)) {
                        derived = true;
                        break;
                    }
                }
            }
        } while (addedNew && !derived);

        System.out.print("\nFinal facts: ");
        for (String f : facts) System.out.print(f + " ");
        System.out.println();

        if (derived)
            System.out.println("SUCCESS: Goal " + goal + " is achieved!");
        else
            System.out.println("FAIL: Goal " + goal + " cannot be achieved.");
    }
}
//javac ForwardChainingEasy.java
//java ForwardChainingEasy