import java.util.*;

class Node implements Comparable<Node> {
    int x, y;          // Position of the node on the grid
    int g, h, f;       // Costs for A* algorithm:
                       // g = cost from start to this node
                       // h = heuristic estimate to goal
                       // f = total cost (g + h)
    Node parent;       // To track the path, keep the node we came from

    Node(int x, int y) {
        this.x = x;
        this.y = y;
    }

    //  (lowest first)
    public int compareTo(Node other) {
        return this.f - other.f;
    }
}

public class AStarSimple {

    // Heuristic function estimates the distance from current node to goal.
  
    static int heuristic(Node a, Node b) {
        return Math.abs(a.x - b.x) + Math.abs(a.y - b.y);
    }

   
    static List<Node> findPath(Node start, Node goal, int[][] grid) {
        // PriorityQueue stores nodes to explore ordered by lowest f cost (f = g + h)
        
        PriorityQueue<Node> openList = new PriorityQueue<>();

        //stores nodes we have already visited
       
        Set<String> closedList = new HashSet<>();

        start.g = 0;                             
        start.h = heuristic(start, goal);        
        start.f = start.g + start.h;             

        openList.add(start);                      

        while (!openList.isEmpty()) {
            
            Node current = openList.poll();

            
            if (current.x == goal.x && current.y == goal.y) {
                List<Node> path = new ArrayList<>();
                while (current != null) {
                    path.add(current);
                    current = current.parent;
                }
                Collections.reverse(path);
                return path;
            }

            // Mark this node as visited by adding it to closedList
            closedList.add(current.x + "," + current.y);

            // Check the node's neighbors (up, down, left, right)
            int[][] directions = { {1,0}, {-1,0}, {0,1}, {0,-1} };
            for (int[] d : directions) {
                int nx = current.x + d[0];
                int ny = current.y + d[1];

                // Check if neighbor is inside the grid bounds
                if (nx >= 0 && ny >= 0 && nx < grid.length && ny < grid[0].length) {
                  
                    if (grid[nx][ny] == 1) continue;

                    
                    Node neighbor = new Node(nx, ny);

                    // Skip this neighbor if already visited
                    if (closedList.contains(nx + "," + ny)) continue;

                    // Calculate tentative g score from start to neighbor through current
                    int tentative_g = current.g + 1;

                    // Check if neighbor is already in openList and if this path is better
                    boolean inOpen = openList.contains(neighbor);

                    if (!inOpen || tentative_g < neighbor.g) {
                        neighbor.g = tentative_g;
                        neighbor.h = heuristic(neighbor, goal);
                        neighbor.f = neighbor.g + neighbor.h;
                        neighbor.parent = current; 

                        // Add neighbor to openList if not already there
                        if (!inOpen) {
                            openList.add(neighbor);
                        }
                    }
                }
            }
        }

        return null; 
    }

    public static void main(String[] args) {
        int[][] grid = {
            {0, 0, 0, 0},
            {1, 1, 0, 1},
            {0, 0, 0, 0},
            {0, 1, 1, 0}
        };

        Node start = new Node(0, 0);
        Node goal = new Node(3, 3);

        List<Node> path = findPath(start, goal, grid);
        if (path != null) {
            System.out.println("Path found:");
            for (Node n : path) {
                System.out.println("(" + n.x + "," + n.y + ")");
            }
        } else {
            System.out.println("No path found");
        }
    }
}
// javac AStarSimple.java 
// java AStarSimple