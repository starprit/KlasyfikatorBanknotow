package com.parse;

import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;

/**
 * Created by User on 18.06.2017.
 */
public class ParseInputs {
    List<Double> inputs = new ArrayList<>();
    List<Double> targets = new ArrayList<>();
    double target;
    String input;

    public ParseInputs(String input) {
        this.input = input;
        String newString = input.replaceAll("\\s+", "");
        StringTokenizer st = new StringTokenizer(newString, ",");

        int i = 0;
        while (st.hasMoreTokens()) {
            double d = Double.parseDouble(st.nextToken());
            if (i == 4) {
                target = d;
                for (int j = 0; j < 2; j++) {
                    if (j  == target)
                        targets.add(1.0);
                    else
                        targets.add(0.0);
                }

            } else {
                inputs.add(d);
            }
            i++;
        }
    }


    public List<Double> getValues() {
        return inputs;
    }

    public List<Double> getTargets() {
        return targets;
    }
    public String getInput() {
        return input;
    }

}
