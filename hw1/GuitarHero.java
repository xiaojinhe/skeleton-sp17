/** A client that uses the synthesizer package to replicate a plucked guitar string sound */
public class GuitarHero {

    public static void main(String[] args) {
        /* create two guitar strings, for concert A and C */
        synthesizer.GuitarString[] strings = new synthesizer.GuitarString[37];
        String keyboard = "q2we4r5ty7u8i9op-[=zxdcfvgbnjmk,.;/' ";

        for (int i = 0; i < strings.length; i++) {
            strings[i] = new synthesizer.GuitarString(440 * Math.pow(2, ((i - 24) / 12)));
        }

        while (true) {

            /* check if the user has typed a key; if so, process it */
            if (StdDraw.hasNextKeyTyped()) {
                char key = StdDraw.nextKeyTyped();
                if (!(keyboard.indexOf(key) == -1)) {
                    strings[keyboard.indexOf(key)].pluck();
                }
            }
            double sample = 0;

            for (int i = 0; i < 37; i++) {
                double old = sample;
                sample = old + strings[i].sample();
            }

            // send the result to standard audio
            StdAudio.play(sample);

            for (int i = 0; i < 37; i++) {
                strings[i].tic();
            }
        }
    }

}

