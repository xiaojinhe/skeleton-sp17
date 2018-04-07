public class TestPlanet {
    public static void main(String[] args) {
        Planet p1 = new Planet(1.0e12, 2.0e11, 1, 1, 2.0e30, "img1");
        Planet p2 = new Planet(2.3e12, 9.5e11, -1, -1, 6.0e26, "img2");
        Planet p3 = new Planet(1.0e12, -2.0e11, 1, 1, 0, "img3");
        Planet[] allplanets = new Planet[] {p1, p2, p3};
        System.out.println(p1.calcDistance(p2));
        System.out.println(p1.calcForceExertedBy(p2));
        System.out.println(p1.calcForceExertedByX(p2));
        System.out.println(p1.calcForceExertedByY(p2));
        System.out.println(p2.calcForceExertedByX(p1));
        System.out.println(p2.calcForceExertedByY(p1));
        System.out.println(p1.calcNetForceExertedByX(allplanets));
        System.out.println(p1.calcNetForceExertedByY(allplanets));
    }
}