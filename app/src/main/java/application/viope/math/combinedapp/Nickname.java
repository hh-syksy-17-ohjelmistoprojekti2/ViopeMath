package application.viope.math.combinedapp;


import java.util.Random;

public class Nickname {
    /*
        Stretch Beans Prince Kiki Landslide Blade Pogo Butcher Smiley Vulture Buddy Growl Magica Bull Fuzz Reaper Beard Rebel Hound Robin Pipi Gonzo Oracle Jade Handsome Lightning Snake Torch Assassin Rebel
    */
    public static String getName(){
        String[] names = {"Stretch","Pasianssi","Cupcake","Excalibur","Juhis","Beans","Prince","Kiki","Landslide","Blade","Pogo","Butcher","Smiley","Vulture","Buddy","Growl","Magica","Bull","Fuzz","Reaper","Beard","Hound","Robin","Pipi","Gonzo","Oracle","Jade","Handsome","Lightning","Snake","Torch","Assassin","Rebel"};
        Random r = new Random();
        return names[r.nextInt(names.length)];
    }
}
