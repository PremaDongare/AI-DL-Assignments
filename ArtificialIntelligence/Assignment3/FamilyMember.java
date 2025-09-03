import java.util.*;


public class FamilyMember {

    String name;
    int age;
    FamilyMember parent;
    List<FamilyMember> children=new ArrayList<>();

    FamilyMember(String name, int age){
        this.name=name;
        this.age=age;
    }

    void addChild(FamilyMember child){
        children.add(child);
        child.parent =this;
    }

    String getChildrenName(){
        List<String>names= new ArrayList<>();
        for(FamilyMember c : children)
        names.add(c.name);
        return String.join(",", names);
    }

    public static void main(String[] arge){
        // creating family members
        FamilyMember grandparent=new FamilyMember("Grandparent",75);
        FamilyMember parent1=new FamilyMember("Parent1",50);
        FamilyMember parent2=new FamilyMember("Parent2",48);
        FamilyMember child1=new FamilyMember("Child1",20);
        FamilyMember child2=new FamilyMember("Child2",18);
        FamilyMember child3=new FamilyMember("Child3",15);

        //building family relation

        grandparent.addChild(parent1);
        grandparent.addChild(parent2);
        parent1.addChild(child1);
        parent1.addChild(child2);
        parent2.addChild(child3);

        printTree(grandparent,"");

        System.out.println(parent1.name+"'s children:"+ parent1.getChildrenName());
        System.out.println(child1.name+" 's parent:"+(child1.parent !=null ?child1.parent.name:"Unknown"));
    }


    // to print tree in hirichical form
    static void printTree(FamilyMember m, String indent){
System.out.println(indent + m.name +" ("+m.age+")");

for(FamilyMember c:m.children){
    printTree(c, indent + "  ");
}
    }
}
    

// javac FamilyMember.java
// java -cp . FamilyMember