import java.utils.*;

public class MapColor{
    public static void main(String[] args){
        // regions

        String[] regions ={'A','B','C'};

        // Color
        String[] color = {"Red","Green","Blue"};

        //Map to store adj relation

        Map<String, List<String>> neighbors = new HashMap<>();

        // add
        neighbors.put("A",Arrays.asList("B","C"));
         neighbors.put("B",Arrays.asList("A","C"));
          neighbors.put("c",Arrays.asList("A","B"));

          // map to store the current color assign 
          Map<String, String> assignment = new HashMap<>();
          // start backtracking 
          if(assignColors(0, regions, colors, neighbors, assignment)) {
             //  solution  found
            System.out.println("Solution:");
            for (String r : regions) {
                System.out.println(r + " -> " + assignment.get(r)); // Print region and its assigned color
            }
        } else {
            
            System.out.println("No solution found.");
        }
    }
         // Function for assign color
         static boolean assignColors(int index, String[] regions, String[] colors,
                                 Map<String, List<String>> neighbors,
                                 Map<String, String> assignment ){

            if(index == regions.length) return true;

            String region = regions[index];

            // try color
            for(String color:colors){
                if(isValid(region, color, neighbours, assignment)){
                    assignment.put(region, color);

                    if (assignColors(index + 1, regions, colors, neighbors, assignment))
                    return true;

                    assignment.remove(region);
                }
            }
            return false;
                                 }

           // is valid function 

           static boolean isValid(String region, String color,
                            Map<String, List<String>> neighbors,
                            Map<String, String> assignment) {

        for (String neighbor : neighbors.get(region)) {
           
            if (color.equals(assignment.get(neighbor))) return false;
        }
        
        return true;
    }                   

    
}