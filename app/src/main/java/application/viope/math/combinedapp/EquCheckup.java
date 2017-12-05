package application.viope.math.combinedapp;

public class EquCheckup {

    public double eval(final String str) {
        return new Object() {
            int pos = -1, ch;

            void nextChar() {
                ch = (++pos < str.length()) ? str.charAt(pos) : -1;
            }

            boolean eat(int charToEat) {
                while (ch == ' ') nextChar();
                if (ch == charToEat) {
                    nextChar();
                    return true;
                }
                return false;
            }

            double parse() {
                nextChar();
                double x = parseExpression();
                if (pos < str.length()) throw new RuntimeException("Unexpected: " + (char)ch);
                return x;
            }

            // Grammar:
            // expression = term | expression `+` term | expression `-` term
            // term = factor | term `*` factor | term `/` factor
            // factor = `+` factor | `-` factor | `(` expression `)`
            //        | number | functionName factor | factor `^` factor

            double parseExpression() {
                double x = parseTerm();
                for (;;) {
                    if      (eat('+')) x += parseTerm(); // addition
                    else if (eat('-')) x -= parseTerm(); // subtraction
                    else return x;
                }
            }

            double parseTerm() {
                double x = parseFactor();
                for (;;) {
                    if      (eat('*')) x *= parseFactor(); // multiplication
                    else if (eat('/')) x /= parseFactor(); // division
                    else return x;
                }
            }

            double parseFactor() {
                if (eat('+')) return parseFactor(); // unary plus
                if (eat('-')) return -parseFactor(); // unary minus

                double x;
                int startPos = this.pos;
                if (eat('(')) { // parentheses
                    x = parseExpression();
                    eat(')');
                } else if ((ch >= '0' && ch <= '9') || ch == '.') { // numbers
                    while ((ch >= '0' && ch <= '9') || ch == '.') nextChar();
                    x = Double.parseDouble(str.substring(startPos, this.pos));
                } else if (ch >= 'a' && ch <= 'z') { // functions
                    while (ch >= 'a' && ch <= 'z') nextChar();
                    String func = str.substring(startPos, this.pos);
                    x = parseFactor();
                } else {
                    throw new RuntimeException("Unexpected: " + (char)ch);
                }

                if (eat('^')) x = Math.pow(x, parseFactor()); // exponentiation

                return x;
            }
        }.parse();
    }

    public int testi (final String stagex, final String answer) {

        String inputAnswer = "x="+answer;
        String inputAnswer2 = answer+"=x";

        String stage = stagex; //input becomes stage variable that we will check
        String stage1;  //stage1 and stage2 are half of the same stage
        String stage2;

        try {
            for (int i = 0; i < stage.length(); i++) {
                if (stage.charAt(i) == 'x') {
                    try {
                        if (String.valueOf(stage.charAt(i - 1)).matches("[-+]?\\d*\\.?\\d+")) {
                            stage = new StringBuffer(stage).insert(i, "*").toString();

                        }
                    } catch (StringIndexOutOfBoundsException ignored) {
                    }
                    try {
                        if (String.valueOf(stage.charAt(i + 1)).matches("[-+]?\\d*\\.?\\d+")) {
                            stage = new StringBuffer(stage).insert(i + 1, "*").toString();
                        }
                    } catch (StringIndexOutOfBoundsException ignored) {
                    }


                }
                if (stage.charAt(i) == '(') {
                    try {
                        if (String.valueOf(stage.charAt(i - 1)).matches("[-+]?\\d*\\.?\\d+")) {
                            stage = new StringBuffer(stage).insert(i, "*").toString();
                        }
                    } catch (StringIndexOutOfBoundsException ignored) {
                    }


                }
                if (stage.charAt(i) == ')') {
                    try {
                        if (String.valueOf(stage.charAt(i + 1)).matches("[-+]?\\d*\\.?\\d+")) {
                            stage = new StringBuffer(stage).insert(i + 1, "*").toString();
                        }
                    } catch (StringIndexOutOfBoundsException ignored) {
                    }
                }
            }

            //We test if the input is the correct answer and we remove spaces
            if(stage.replaceAll("\\s+","").equals(inputAnswer)|stage.replaceAll("\\s+","").equals(inputAnswer2)){
                return 1;
            }

            //When user gives their input we put it in the stage variable and instantly change the calculation to make it checkable
            String stage3 = stage.replace("x", answer);
            String[] separated = stage3.split("="); // String gets divided at "="
            stage1 = separated[0];
            stage2 = separated[1];
            if (eval(stage1) == eval(stage2)) { //Comparing if both sides are the same so we know if the phase is still correct
                return 2;
            } else {
                return 3;
            }
        }catch(Exception e){
            return 4;
        }
    }
}
