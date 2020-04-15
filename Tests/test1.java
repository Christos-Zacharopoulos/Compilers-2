class TestEx1 {
    public static void main(String[] args) {
        System.out.println(new A().foo());
    }
}

class A {
    int i;
    boolean flag;
    int j;
    public int foo() { return 2; }
    public boolean fa() { return 3; }
}

class B extends A{
    A type;
    int k;
    public int foo() { return 4; }
    public boolean bla() { return 5; }
}