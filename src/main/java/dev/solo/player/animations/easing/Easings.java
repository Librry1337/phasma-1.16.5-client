package dev.solo.player.animations.easing;

public class Easings
{
    public static final double c1 = 1.70158;
    public static final double c2 = 2.5949095;
    public static final Easing BACK_BOTH;
    public static final double c3 = 2.70158;
    public static final Easing BACK_IN;
    public static final Easing BACK_OUT;
    public static final double c4 = 2.0943951023931953;
    public static final double c5 = 1.3962634015954636;
    public static final Easing LINEAR;
    public static final Easing QUAD_IN;
    public static final Easing QUAD_OUT;
    public static final Easing QUAD_BOTH;
    public static final Easing CUBIC_IN;
    public static final Easing CUBIC_OUT;
    public static final Easing CUBIC_BOTH;
    public static final Easing QUART_IN;
    public static final Easing QUART_OUT;
    public static final Easing QUART_BOTH;
    public static final Easing QUINT_IN;
    public static final Easing QUINT_OUT;
    public static final Easing QUINT_BOTH;
    public static final Easing SINE_IN;
    public static final Easing SINE_OUT;
    public static final Easing SINE_BOTH;
    public static final Easing CIRC_IN;
    public static final Easing CIRC_OUT;
    public static final Easing CIRC_BOTH;
    public static final Easing ELASTIC_IN;
    public static final Easing ELASTIC_OUT;
    public static final Easing ELASTIC_BOTH;
    public static final Easing EXPO_IN;
    public static final Easing EXPO_OUT;
    public static final Easing EXPO_BOTH;
    public static final Easing BOUNCE_OUT;
    public static final Easing BOUNCE_IN;
    public static final Easing BOUNCE_BOTH;
    
    private Easings() {
    }
    
    public static Easing powIn(final double n) {
        return value -> Math.pow(value, n);
    }
    
    public static Easing powIn(final int n) {
        return powIn((double)n);
    }
    
    public static Easing powOut(final double n) {
        return value -> 1.0 - Math.pow(1.0 - value, n);
    }
    
    public static Easing powOut(final int n) {
        return powOut((double)n);
    }
    
    public static Easing powBoth(final double n) {
        return value -> {
            if (value < 0.5) {
                return Math.pow(2.0, n - 1.0) * Math.pow(value, n);
            }
            else {
                return 1.0 - Math.pow(-2.0 * value + 2.0, n) / 2.0;
            }
        };
    }
    
    static {
        BACK_BOTH = (value -> {
            if (value < 0.5) {
                return Math.pow(2.0 * value, 2.0) * (7.189819 * value - 2.5949095) / 2.0;
            }
            else {
                return (Math.pow(2.0 * value - 2.0, 2.0) * (3.5949095 * (value * 2.0 - 2.0) + 2.5949095) + 2.0) / 2.0;
            }
        });
        BACK_IN = (value -> 2.70158 * Math.pow(value, 3.0) - 1.70158 * Math.pow(value, 2.0));
        BACK_OUT = (value -> 1.0 + 2.70158 * Math.pow(value - 1.0, 3.0) + 1.70158 * Math.pow(value - 1.0, 2.0));
        LINEAR = (value -> value);
        QUAD_IN = powIn(2);
        QUAD_OUT = powOut(2);
        QUAD_BOTH = powBoth(2.0);
        CUBIC_IN = powIn(3);
        CUBIC_OUT = powOut(3);
        CUBIC_BOTH = powBoth(3.0);
        QUART_IN = powIn(4);
        QUART_OUT = powOut(4);
        QUART_BOTH = powBoth(4.0);
        QUINT_IN = powIn(5);
        QUINT_OUT = powOut(5);
        QUINT_BOTH = powBoth(5.0);
        SINE_IN = (value -> 1.0 - Math.cos(value * 3.141592653589793 / 2.0));
        SINE_OUT = (value -> Math.sin(value * 3.141592653589793 / 2.0));
        SINE_BOTH = (value -> -(Math.cos(3.141592653589793 * value) - 1.0) / 2.0);
        CIRC_IN = (value -> 1.0 - Math.sqrt(1.0 - Math.pow(value, 2.0)));
        CIRC_OUT = (value -> Math.sqrt(1.0 - Math.pow(value - 1.0, 2.0)));
        CIRC_BOTH = (value -> {
            if (value < 0.5) {
                return (1.0 - Math.sqrt(1.0 - Math.pow(2.0 * value, 2.0))) / 2.0;
            }
            else {
                return (Math.sqrt(1.0 - Math.pow(-2.0 * value + 2.0, 2.0)) + 1.0) / 2.0;
            }
        });
        ELASTIC_IN = (value -> {
            if (value == 0.0 || value == 1.0) {
                return value;
            }
            else {
                return Math.pow(-2.0, 10.0 * value - 10.0) * Math.sin((value * 10.0 - 10.75) * 2.0943951023931953);
            }
        });
        ELASTIC_OUT = (value -> {
            if (value == 0.0 || value == 1.0) {
                return value;
            }
            else {
                return Math.pow(2.0, -10.0 * value) * Math.sin((value * 10.0 - 0.75) * 2.0943951023931953) + 1.0;
            }
        });
        ELASTIC_BOTH = (value -> {
            if (value == 0.0 || value == 1.0) {
                return value;
            }
            else if (value < 0.5) {
                return -(Math.pow(2.0, 20.0 * value - 10.0) * Math.sin((20.0 * value - 11.125) * 1.3962634015954636)) / 2.0;
            }
            else {
                return Math.pow(2.0, -20.0 * value + 10.0) * Math.sin((20.0 * value - 11.125) * 1.3962634015954636) / 2.0 + 1.0;
            }
        });
        EXPO_IN = (value -> {
            if (value != 0.0) {
                return Math.pow(2.0, 10.0 * value - 10.0);
            }
            else {
                return value;
            }
        });
        EXPO_OUT = (value -> {
            if (value != 1.0) {
                return 1.0 - Math.pow(2.0, -10.0 * value);
            }
            else {
                return value;
            }
        });
        EXPO_BOTH = (value -> {
            if (value == 0.0 || value == 1.0) {
                return value;
            }
            else if (value < 0.5) {
                return Math.pow(2.0, 20.0 * value - 10.0) / 2.0;
            }
            else {
                return (2.0 - Math.pow(2.0, -20.0 * value + 10.0)) / 2.0;
            }
        });
        BOUNCE_OUT = (x -> {
            final double n17 = 7.5625;
            final double d1 = 2.75;
            if (x < 1.0 / d1) {
                return n17 * Math.pow(x, 2.0);
            }
            else if (x < 2.0 / d1) {
                return n17 * Math.pow(x - 1.5 / d1, 2.0) + 0.75;
            }
            else if (x < 2.5 / d1) {
                return n17 * Math.pow(x - 2.25 / d1, 2.0) + 0.9375;
            }
            else {
                return n17 * Math.pow(x - 2.625 / d1, 2.0) + 0.984375;
            }
        });
        BOUNCE_IN = (value -> 1.0 - Easings.BOUNCE_OUT.ease(1.0 - value));
        BOUNCE_BOTH = (value -> {
            if (value < 0.5) {
                return (1.0 - Easings.BOUNCE_OUT.ease(1.0 - 2.0 * value)) / 2.0;
            }
            else {
                return (1.0 + Easings.BOUNCE_OUT.ease(2.0 * value - 1.0)) / 2.0;
            }
        });
    }
}
