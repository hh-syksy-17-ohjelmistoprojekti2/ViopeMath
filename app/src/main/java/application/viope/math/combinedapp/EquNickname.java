package application.viope.math.combinedapp;


import java.util.Random;

public class EquNickname {
    /*
        Just a filler for usernames. So giving users usernames since they don't have a field to input their own yet. Can be deleted, will work without this but usernames will be saved as empty.
    */
    public static String getName(){
        String[] names = {"Stretch","Pasianssi","Cupcake","Excalibur","Juhis","Beans","Prince","Kiki","Landslide","Blade","Pogo","Butcher","Smiley","Vulture","Buddy","Growl","Magica","Bull","Fuzz","Reaper","Beard","Hound","Robin","Pipi","Gonzo","Oracle","Jade","Handsome","Lightning","Snake","Torch","Assassin","Rebel"};
        Random r = new Random();
        return names[r.nextInt(names.length)];
    }
}
