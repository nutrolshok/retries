package net.nutrolshok.retries.applause;

import static net.nutrolshok.retries.util.PseudoCodeUtil.print;

public class Applause {

    public void applause() {
        try {
            print("👏");
        } catch (SilenceException e) {
            applause();
        }
    }
}
