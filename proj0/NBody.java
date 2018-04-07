public class NBody {

    public static double readRadius(String fileName) {
        /** Given a file name; returns the radius of the universe in that file. */
        In in;
        double radius = 0;
        int line = 0;
        try {
            in = new In(fileName);
            while(!in.isEmpty() && line < 2) {
                radius = in.readDouble();
                line++;
            }
        }
        catch (Exception e) { System.out.println(e); }
        return radius;
    }

    public static Planet[] readPlanets(String fileName) {
        /** Given a file name, returns an array of Planets in the file. */
        In in = new In(fileName);
        int planetNum = in.readInt();
        in.readDouble();
        Planet[] planets = new Planet[planetNum];
        for (int i = 0; i < planetNum; i++) {
            double xxP = in.readDouble();
            double yyP = in.readDouble();
            double xxV = in.readDouble();
            double yyV = in.readDouble();
            double pMass = in.readDouble();
            String img = in.readString();
            planets[i] = new Planet(xxP, yyP, xxV, yyV, pMass, img);
        }
        in.close();
        return planets;
    } 

    public static void main(String[] args) {
        double T = Double.parseDouble(args[0]);
        double dt = Double.parseDouble(args[1]);
        String filename = args[2];
        Planet[] planets = readPlanets(filename);
        double universeRadius = readRadius(filename);
        StdDraw.setScale(-universeRadius, universeRadius); //set the scale that matches the radius of the universe
        StdDraw.clear(); //clears the screen to default color, white
        StdDraw.picture(0, 0, "images/starfield.jpg"); //draw the background

        for(Planet p : planets) {
            p.draw();
        }  // draw all of the planets in the planets array

        StdAudio.play("audio/2001.mid"); // Play background audio

        /** Create an Animation */
        double time = 0; 
        while(time < T) {
            double[] xForces = new double[planets.length];
            double[] yForces = new double[planets.length];

            for (int i = 0; i < planets.length; i++) {
                xForces[i] = planets[i].calcNetForceExertedByX(planets);
                yForces[i] = planets[i].calcNetForceExertedByY(planets);
            } // Calculate the net x and y forces for each planet and store these in the arrays respectively

            for (int i = 0; i <planets.length; i++) {
                planets[i].update(dt, xForces[i], yForces[i]);
            } // Update each planet's position, velocity, and acceleration

            StdDraw.picture(0, 0, "images/starfield.jpg");

            for (Planet p : planets) {
                p.draw();
            } //Draw all of the planets

            StdDraw.show(10); //Pause the animation for 10 milliseconds
            time += dt; //Increase time variable by dt
        }

        StdAudio.close();
        StdOut.printf("%d\n", planets.length); // Print the final state of the universe in the same formart as the input
        StdOut.printf("%.2e\n", universeRadius);
        for (int i = 0; i < planets.length; i++) {
            StdOut.printf(" %11.4e  %11.4e  %11.4e  %11.4e  %11.4e  %12s\n", 
            planets[i].xxPos, planets[i].yyPos, planets[i].xxVel, planets[i].yyVel, planets[i].mass, planets[i].imgFileName);
        }
        StdOut.close();
    }
}
