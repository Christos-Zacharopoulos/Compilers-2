import java.util.*;

class ClassInfo extends Base {

    ClassInfo parent;
    String name;
    String type;

    HashMap<String, ClassInfo> variables;
    HashMap<String, ClassInfo> functions;

    ArrayList<ClassInfo> arguments;

    ClassInfo() {
        this.variables = new HashMap<>();
        this.functions = new HashMap<>();
        this.arguments = new ArrayList<>();
    }

    public ClassInfo getRoot() {
        ClassInfo info = this;
        while (info.parent != null) info = info.parent;

        return info;
    }


    public boolean isValidType(String type) {

        if (!isPrimitiveType(type)) {
            ClassInfo root = getRoot();

            if ( !root.hasVariable(type) ) return false;
        }
        return true;
    }


    public boolean notEqualTypes(String a, String b) {
        return (!areEqual(a, b) && !areEqual(this.getVariableType(a), b));
    }

    public void addArguments(ClassInfo arg) { this.arguments.add(arg); }



    public ClassInfo addVariables(ClassInfo info, String name,String type, Integer offset) {

        ClassInfo child = new ClassInfo();
        child.parent = this;
        child.name = name;
        child.type = type;

        if ( offset != null ) {
            child.variableOffset = offset;
            child.offset = offset;
        }

        info.variables.put(name, child);

        return child;
    }

    public boolean hasVariable (String name) {
        return (this.getVariable(name) != null);
    }

    public ClassInfo getVariable (String name) {
        return this.variables.get(name);
    }

    public void addVariableOffset (ClassInfo info, String type) {

        if( areEqual(type, INT) ) info.variableOffset += 4;

        else if( areEqual(type, BOOLEAN) ) info.variableOffset += 1;

        else info.variableOffset += 8;

    }

    public String getVariableType (String name) {
        ClassInfo info = this;


        while(info != null) {

            if(info.hasVariable(name)) return info.getVariable(name).type;

            else info = info.parent;
        }

        return null;
    }

    public ClassInfo addFunctions(ClassInfo info, String name,String type, Integer offset, Boolean overriden) {
        ClassInfo child = new ClassInfo();
        child.parent = this;
        child.name = name;
        child.type = type;

        if ( offset != null ) {
            child.functionOffset = offset;
            child.offset = offset;
        }

        if ( overriden != null ) child.overriden = overriden;

        info.functions.put(name, child);

        return child;
    }

    public boolean hasFunction (ClassInfo info, String name) { return (info.functions.get(name) != null); }

    public ClassInfo getFunction (ClassInfo info, String name) { return info.functions.get(name); }

    public void addFunctionOffset (ClassInfo info) { info.functionOffset += 8; }

    public ClassInfo findFunction (ClassInfo info, String name) {

        while(info != null) {

            if(info.hasFunction(info, name)) return info.getFunction(info, name);

            else info = info.parent;
        }

        return null;
    }

    public String getFunctionType (ClassInfo info, String name) {
        ClassInfo fun = findFunction(info, name);

        return fun != null ? fun.type : null;
    }


    public Boolean isMethodOverloaded(ClassInfo info, String method, ClassInfo subFunc) {
        ClassInfo superFunc = findFunction(info, method);

        if(superFunc != null) {
            if(subFunc.arguments.size() != superFunc.arguments.size()) return true;

            for(int i = 0 ; i < subFunc.arguments.size() ; i++) {
                if(!areEqual(subFunc.arguments.get(i).type, superFunc.arguments.get(i).type)) return true;
            }
        }

        return false;
    }

    public String thisRefersTo ( ClassInfo info, String type) {

        while (  info != null ) {
            if(info.type.equals(info.name)) return info.type;

            info = info.parent;
        }

        return type;
    }

    public ClassInfo findSuperClass ( ClassInfo info, String type1, String type2) {
        ClassInfo curent = getRoot().getVariable(info.getVariableType(type1));

        while (curent != null) {
            if(curent.name.equals(type2)) return curent;

            curent = curent.parent;
        }

        return null;
    }


    public boolean inheritanceCheck(ClassInfo info, String type1, String type2) {

        if ( info.isThis(type1) ) type1 = info.thisRefersTo(info, type1);

        if ( info.isThis(type2) ) type2 = info.thisRefersTo(info, type2);

        return info.findSuperClass(info, type1, type2) != null;
    }

    public void printOffsets() {

        for(String s : this.variables.keySet()) {

            ClassInfo n = this.getVariable(s);

            if(!n.type.equals("class")) {
                for(int i = 0 ; i < n.declarationsOffset.size() ; i++) {
                    if(n.declarationsOffset.get(i).overriden == false) {
                        System.out.println(n.name + "." + n.declarationsOffset.get(i).name + " : " + n.declarationsOffset.get(i).offset);
                    }
                }
                System.out.println();
            }
        }
    }
}