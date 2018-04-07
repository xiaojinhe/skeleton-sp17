public class Planet {
    public double xxPos; // Its current x position
    public double yyPos; // Its current y position
    public double xxVel; // Its current velocity in the x direction
    public double yyVel; // Its current velocity in the y direction
    public double mass; // Its mass
    public String imgFileName; // The name of an image in the images directory that depicts the planet

    public Planet(double xxPos, double yyPos, double xxVel, double yyVel, double mass, String imgFileName) {
        /** Planet constructor to initialize an instance of the Planet class. */
        this.xxPos = xxPos;
        this.yyPos = yyPos;
        this.xxVel = xxVel;
        this.yyVel = yyVel;
        this.mass = mass;
        this.imgFileName = imgFileName;
    }

    public Planet(Planet P) {
        /** Takes in a Planet object and initialize an identical Planet object, i.e. a copy. */
        xxPos = P.xxPos;
        yyPos = P.yyPos;
        xxVel = P.xxVel;
        yyVel = P.yyVel;
        mass = P.mass;
        imgFileName = P.imgFileName;
    }

    public double calcDistance(Planet P) {
        /** Takes in a single Planet and returns a double equal to the distance between the two planets. */
        double r = Math.sqrt((P.xxPos - xxPos) * (P.xxPos - xxPos) + (P.yyPos - yyPos) * (P.yyPos - yyPos));
        return r;
    }

    public double calcForceExertedBy(Planet P) {
        /** Takes in a Planet and returns the force exerted on this planet by the given planet. */
        double r = calcDistance(P);
        double force = 6.67e-11 * mass * P.mass / (r * r);
        return force;
    }

    public double calcForceExertedByX(Planet P) {
        /** Takes in a Planet and returns the force exerted in the X direction on this planet by the given planet. */
        double forceX = calcForceExertedBy(P) * (P.xxPos - xxPos) / calcDistance(P);
        return forceX;
    }

    public double calcForceExertedByY(Planet P) {
        /** Takes in a Planet and returns the force exerted in the Y direction on this planet by the given planet. */
        double forceY = calcForceExertedBy(P) * (P.yyPos - yyPos) / calcDistance(P);
        return forceY;
    }

    public double calcNetForceExertedByX(Planet[] allplanets) {
        /** Takes in an array of Planets and returns the net X force exerted by all planets on this planet. */
        double netForceX = 0;
        for (Planet p : allplanets) {
            if (this.equals(p)) {
                continue;
            }
            netForceX += calcForceExertedByX(p);
        }
        return netForceX;
    }

    public double calcNetForceExertedByY(Planet[] allplanets) {
        /** Takes in an array of Planets and returns the net Y force exerted by all planets on this planet. */
        double netForceY = 0;
        for (Planet p : allplanets) {
            if (this.equals(p)) {
                continue;
            }
            netForceY += calcForceExertedByY(p);
        }
        return netForceY;
    }    

    public void update(double dt, double fX, double fY) {
        /** Updates the planet's new velocity and position in time dt caused by the forces exerted on the planet. */
        double aX = fX / mass;
        double aY = fY / mass;
        xxVel += dt * aX;
        yyVel += dt * aY;
        xxPos += dt * xxVel;
        yyPos += dt * yyVel;
    }

    public void draw() {
        /** Draw the planet on the coresponding position on the background. */
        StdDraw.picture(xxPos, yyPos, "images/" + imgFileName);
    }
}
