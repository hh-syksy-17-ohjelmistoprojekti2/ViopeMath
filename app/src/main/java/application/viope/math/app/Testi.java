package application.viope.math.app;

/**
 * Created by a1600547 on 26.9.2017.
 */

public class Testi {

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
        //String answer = "2"; //Murtolukuiset vastaukset pitää kirjoittaa sulkuihin
        String inputAnswer = "x="+answer;
        String inputAnswer2 = answer+"=x";

        String stage = stagex; //inputista tulisi stage variable jonka tarkistamme
        String stage1;  //stage1 ja 2 ovat stagen puolikkaita
        String stage2;

        try {
            for (int i = 0; i < stage.length(); i++) {
                if (stage.charAt(i) == 'x') {
                    try {
                        if (String.valueOf(stage.charAt(i - 1)).matches("[-+]?\\d*\\.?\\d+")) {
                            System.out.println("Numero edessä");
                            stage = new StringBuffer(stage).insert(i, "*").toString();
                            System.out.println(stage);

                        }
                    } catch (StringIndexOutOfBoundsException ignored) {
                    }
                    try {
                        if (String.valueOf(stage.charAt(i + 1)).matches("[-+]?\\d*\\.?\\d+")) {
                            System.out.println("Numero takana");
                            stage = new StringBuffer(stage).insert(i + 1, "*").toString();
                            System.out.println(stage);
                        }
                    } catch (StringIndexOutOfBoundsException ignored) {
                    }


                }
                if (stage.charAt(i) == '(') {
                    try {
                        if (String.valueOf(stage.charAt(i - 1)).matches("[-+]?\\d*\\.?\\d+")) {
                            System.out.println("Numero edessä");
                            stage = new StringBuffer(stage).insert(i, "*").toString();
                            System.out.println(stage);

                        }
                    } catch (StringIndexOutOfBoundsException ignored) {
                    }


                }
                if (stage.charAt(i) == ')') {
                    try {
                        if (String.valueOf(stage.charAt(i + 1)).matches("[-+]?\\d*\\.?\\d+")) {
                            System.out.println("Numero takana");
                            stage = new StringBuffer(stage).insert(i + 1, "*").toString();
                            System.out.println(stage);
                        }
                    } catch (StringIndexOutOfBoundsException ignored) {
                    }


                }
            }

            //Testataan onko inputti oikea vastaus, poistetaan stage kohdasta välilyönnit
            if(stage.replaceAll("\\s+","").equals(inputAnswer)|stage.replaceAll("\\s+","").equals(inputAnswer2)){
                return 1;
            }

            //kun käyttäjä antaa inputin otamme sen stage variableen ja heti muutamme laskun tarkistettavaksi
            String stage3 = stage.replace("x", answer);
            String[] separated = stage3.split("="); // String jaetaan kaheteen osaan "=" kohdalta
            //System.out.println(eval(stage3));
            stage1 = separated[0];
            stage2 = separated[1];
            System.out.println(eval(stage1));
            System.out.println(eval(stage2));
            if (eval(stage1) == eval(stage2)) { // vertaamme että ovatko molemmat samat tiedämme onko tehtävän vaihe vielä oikein.
                return 2;
            } else {
                return 3;
            }
        }catch(Exception e){
            return 4;
        }
    }
}
