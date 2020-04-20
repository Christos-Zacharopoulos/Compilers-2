import java.util.HashMap;
import java.util.ArrayList;

class ClassInfo {

    ClassInfo parent;

    String name;
    String type;

    int memberOffset;
    int functionOffset;
    int overriden;

    HashMap<String, ClassInfo> members;
    HashMap<String, ClassInfo> functions;

    ArrayList<ClassInfo> membersOffset;
    ArrayList<ClassInfo> functionsOffset;

    ClassInfo() {
        this.members = new HashMap<>();
        this.functions = new HashMap<>();
        this.membersOffset = new ArrayList<>();
        this.functionsOffset = new ArrayList<>();
    }

    public ClassInfo addMembers(ClassInfo info, ClassInfo parent, String name,String type) {
        ClassInfo child = new ClassInfo();
        child.parent = parent;
        child.name = name;
        child.type = type;

        info.members.put(name, child);

        return child;
    }

    public boolean hasMember (ClassInfo info, String name) {
        return (info.members.get(name) != null);
    }

    public ClassInfo getMember (ClassInfo info, String name) {
        return info.members.get(name);
    }

    public ClassInfo addFunctions(ClassInfo info, ClassInfo parent, String name,String type) {
        ClassInfo child = new ClassInfo();
        child.parent = parent;
        child.name = name;
        child.type = type;

        info.functions.put(name, child);

        return child;
    }

}