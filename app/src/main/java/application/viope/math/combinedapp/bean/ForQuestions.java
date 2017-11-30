package application.viope.math.combinedapp.bean;

public class ForQuestions {

    public String mQuestions[]={

                /*"which formula would you use on the following expression: 20 + 50 * 100",
                "which formula would you use on the following expression: 10 + 20 * 300",
                "which one of these is the biggest number?",
                "which one of these is the smallest number?",
                "which one is the correct answer to the following expression: 12+24 ?", */
            "Qual é a fórmula correta para somar custo total?",
            "Qual é a fórmula correta para somar rendimento total?",
            "Qual é a fórmula correta para somar produto médio?",
            "Qual é a fórmula correta para somar produto total?",
            "Qual é a fórmula correta para somar o teorema de Pitágoras?",
            "Qual é a fórmula correta para somar quadrado área?",
            "Qual é a fórmula correta para somar triângulo área?",
            "Qual é a fórmula correta para somar círculo área?"


    };

    private String mChoices[][] = {

                /*{"a+b*c","c*a+b","c*b+a","c*(a+b)"},
                {"a+b*c","c*(a+b)","c*a+b","c*b+a"},
                {"2","5","7","9"},
                {"2","5","7","9"},
                {"1","5","48","36"}*/
            {"custo total*quantidade","custo total/quantidade", "quantidade+custo total", "quantidade-custo total"},
            {"preço / quantidade", "quantidade + preço * 2","preço x quantidade", "quantidade / preço"},
            {"produto total * Variável", "produto total / Variável",  "produto total + Variável", "produto total - Variável"},
            {"produto médio * Variável", "produto médio / Variável","produto médio + Variável","produto médio - Variável"},
            {"a^2/pi+b^2=c^2", "a^2+b^2=c^2", "a^2/b^2=c^2", "a^2*b^2=c^2"},
            {"Largura*Comprimento", "Comprimento*Largura*4", "Largura/Comprimento", "4*Largura/Comprimento"},
            {"Largura*Comprimento/2","Comprimento*Largura/3","Largura/Comprimento*3","Comprimento/Largura*2"},
            {"r^2/Pi+2", "2/Pi r^2", "2*Pi r^2", "Pi*r^2"}





    };

    private String mCorrectAnswers[]={"custo total*quantidade","preço x quantidade", "produto total / Variável", "produto médio * Variável","a^2+b^2=c^2","Largura*Comprimento","Largura*Complimento/2","Pi*r^2"};
                /*{ "c*b+a", "c*b+a", "9", "2","36"};*/

    private String mQuestionImage[]={"","","","","for_pythagoras","for_nelionpintala", "for_kolmiopintala", "for_ympyrapintala"};


    public String getQuestion(int a){

        String question=mQuestions[a];
        return question;

    }

    public String getChoice1(int a){

        String choice= mChoices[a][0];
        return choice;
    }

    public String getChoice2(int a){

        String choice= mChoices[a][1];
        return choice;
    }

    public String getChoice3(int a){

        String choice= mChoices[a][2];
        return choice;
    }

    public String getChoice4(int a){

        String choice= mChoices[a][3];
        return choice;
    }

    public String getCorrectAnswer(int a){

        String answer = mCorrectAnswers[a];
        return answer;
    }
    public String getQuestionImage(int a){

        String qimage=mQuestionImage[a];
        return qimage;

    }

}

