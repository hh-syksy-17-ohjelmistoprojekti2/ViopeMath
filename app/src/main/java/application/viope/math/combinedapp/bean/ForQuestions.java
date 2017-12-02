package application.viope.math.combinedapp.bean;

public class ForQuestions {


    public String question;
    public int qid;
    public String answer;
    public String offeredanswerstring;
    public String questionimage;
    public String[][] offeredanswer;
    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public int getQid() {
        return qid;
    }

    public void setQid(int qid) {
        this.qid = qid;
    }
    public void setQuestionimage(String questionimage) {
        this.questionimage = questionimage;
    }
    //    public String getQuestionimage() {
//        return questionimage;
//    }
    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public String[] getmQuestions() {
        return mQuestions;
    }

    public void setmQuestions(String[] mQuestions) {
        this.mQuestions = mQuestions;
    }

    public String[][] getmChoices() {
        return mChoices;
    }

    public void setmChoices(String[][] mChoices) {
        this.mChoices = mChoices;
    }
    public void setOfferedanswer(String offeredanswerstring) {

        this.offeredanswerstring = offeredanswerstring;
    }
    public String[][] getOfferedanswer() {
        return offeredanswer;
    }
//    public String[] getmCorrectAnswers() {
//        return mCorrectAnswers;
//    }
//
//    public void setmCorrectAnswers(String[] mCorrectAnswers) {
//        this.mCorrectAnswers = mCorrectAnswers;
//    }
//
//    public String[] getmQuestionImage() {
//        return mQuestionImage;
//    }
//
//    public void setmQuestionImage(String[] mQuestionImage) {
//        this.mQuestionImage = mQuestionImage;
//    }

    public String mQuestions[]={

                /*"which formula would you use on the following expression: 20 + 50 * 100",
                "which formula would you use on the following expression: 10 + 20 * 300",
                "which one of these is the biggest number?",
                "which one of these is the smallest number?",
                "which one is the correct answer to the following expression: 12+24 ?", */

    };

    private String mChoices[][] = {
            /// {"{"+ this.offeredanswerstring.toString() + "}"}
    };

    private String mCorrectAnswers[]={};
                /*{ "c*b+a", "c*b+a", "9", "2","36"};*/

    private String mQuestionImage[]={};




    public String getChoice1(int a){
        // String[][] offered =  {{ "{"+this.offeredanswerstring.toString()+"}"}};    // String[] splitted = this.offeredanswerstring.split("\"").toString();
        String[] optionarr = this.offeredanswerstring.split(",");
        String choicetest = optionarr[0];
        String choicetrimmed =choicetest.replace("\"", "");
        //String choice= offered[0][0];
        return choicetrimmed;
    }

    public String getChoice2(int a){
        String[] optionarr = this.offeredanswerstring.split(",");
        String choicetest = optionarr[1];
        String choicetrimmed =choicetest.replace("\"", "");
        //String choice= offered[0][0];
        return choicetrimmed;
        //    String choice= offeredanswer[a][1];
        //    return choice;
    }

    public String getChoice3(int a){
        String[] optionarr = this.offeredanswerstring.split(",");
        String choicetest = optionarr[2];
        String choicetrimmed =choicetest.replace("\"", "");
        //String choice= offered[0][0];
        return choicetrimmed;
        //    String choice= offeredanswer[a][2];
        //    return choice;
    }

    public String getChoice4(int a){
        String[] optionarr = this.offeredanswerstring.split(",");
        String choicetest = optionarr[3];
        String choicetrimmed =choicetest.replace("\"", "");
        //String choice= offered[0][0];
        return choicetrimmed;
        //   String choice= offeredanswer[a][3];
        //   return choice;
    }

    public String getCorrectAnswer(int a){

        String answer = this.answer;
        return answer;
    }
    public String getQuestionImage(int a){

        String qimage=this.questionimage;
        return qimage;

    }

}

