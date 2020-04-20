import visitor.GJDepthFirst;
import syntaxtree.*;


public class FirstVisitor extends GJDepthFirst<String, String> {


    /**
     * f0 -> "class"
     * f1 -> Identifier()
     * f2 -> "{"
     * f3 -> "public"
     * f4 -> "static"
     * f5 -> "void"
     * f6 -> "main"
     * f7 -> "("
     * f8 -> "String"
     * f9 -> "["
     * f10 -> "]"
     * f11 -> Identifier()
     * f12 -> ")"
     * f13 -> "{"
     * f14 -> ( VarDeclaration() )*
     * f15 -> ( Statement() )*
     * f16 -> "}"
     * f17 -> "}"
     */
    public void visit(MainClass n, ClassInfo info) throws Exception {

        ClassInfo Class = info.addMembers(info, info, n.f1.accept(this, info), "class");
        ClassInfo Main = info.addFunctions(info, Class, "main", "void");
        ClassInfo NameInfo = info.addMembers(info, Main, n.f11.accept(this, info), "String[]");

        for (int i=0; i<n.f14.size(); i++) {
            n.f14.elementAt(i).accept(this,info))
        }
    }

    /**
     * f0 -> "class"
     * f1 -> Identifier()
     * f2 -> "{"
     * f3 -> ( VarDeclaration() )*
     * f4 -> ( MethodDeclaration() )*
     * f5 -> "}"
     */
    public void visit(ClassDeclaration n, ClassInfo info) throws Exception {

        String name = n.f1.accept(this, info);

        if ( hasMember(info, name) ) {
            System.out.println("Class <" + name + "> has already been defined!")
            throw new Exception();
        }

        info.addMembers(info, info, name, name);

        for ( int i=0; i<n.f3.size(); i++ ) {
            n.f3.elementAt(i).accept(this,info))
        }
        for ( int i=0; i<n.f4.size(); i++ ) {
            n.f4.elementAt(i).accept(this,info))
        }
    }

    /**
     * f0 -> "class"
     * f1 -> Identifier()
     * f2 -> "extends"
     * f3 -> Identifier()
     * f4 -> "{"
     * f5 -> ( VarDeclaration() )*
     * f6 -> ( MethodDeclaration() )*
     * f7 -> "}"
     */
    public void visit(ClassExtendsDeclaration n, ClassInfo info) throws Exception {

        String subClass = n.f1.accept(this, argu);
        String superClass = n.f3.accept(this, argu);

        if ( !hasMember(info, superClass) ) {
            System.out.println("Class <" + name + "> is not defined!\nSuperclass must be defined before subclass.")
            throw new Exception();
        }
        ClassInfo subClassNode = info.addMembers(info, info.getMember(superClass) , subClass, subClass);

        if ( n.f5.size() ) {
            subClassNode.membersOffset = subClassNode.parent.membersOffset;
            for ( int i=0; i<n.f5.size(); i++ ) {
                n.f5.elementAt(i).accept(this,info))
            }
        }

        if ( n.f6.size() ) {
            subClassNode.functionsOffset = subClassNode.parent.functionsOffset;
            //TODO: Abstract this ugly thingy.
            for( int i = 0 ; i < subClassNode.parent.functionsOffset.size() ; i++ ) {
                subClassNode.functionsOffset.add(subClassNode.parent.functionsOffset.get(i));
            }

            for ( int i=0; i<n.f6.size(); i++ ) {
                n.f6.elementAt(i).accept(this,info))
            }
        }

    }

    /**
     * f0 -> Type()
     * f1 -> Identifier()
     * f2 -> ";"
     */
    public void visit(VarDeclaration n, ClassInfo info) throws Exception {
        n.f0.accept(this, argu);
        n.f1.accept(this, argu);
    }

}