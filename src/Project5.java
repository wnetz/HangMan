package project5;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Project5
{
    Random rand;
    File dict;
    Scanner scan;
    String word;
    String root = "./src/Word lists in csv/";

    public static void main(String[] args) {
        new Project5(new Scanner(System.in)).run();
    }
    public Project5(Scanner s)
    {
        rand = new Random();
        word = pickWord();
        scan = s;
    }
    public String pickWord()
    {
        String file = getFile();
        dict = new File(file);
        String w = "";
        try
        {
            Scanner fileIn = new Scanner(dict);
            file = "";
            if(fileIn.hasNext())
            {
                file = fileIn.nextLine();
            }
            while (fileIn.hasNext())
            {
                file = file +","+ fileIn.nextLine();
            }
            String [] words = file.split(",");
            w = words[rand.nextInt(words.length)];
            System.out.println(w);
        }
        catch(FileNotFoundException e)
        {
            System.out.println("file not found");
        }
        return w.replaceAll(" ", "");
    }
    public String getFile()
    {
        char letter = (char)(rand.nextInt(26)+65);
        return root + letter + "word.csv";
    }
    public int run()
    {
        int score = 0;
        System.out.println("H A N G M A N");
        String s1 = " +---+\n";
        String s2 = "     |\n";
        String s3 = "     |\n";
        String s4 = "     |\n";
        String s5 = "     |\n";
        String s6 = "########";
        boolean again = false;
        do
        {
            score = 0;
            word = pickWord();
            ArrayList<Character> missed = new ArrayList<>();
            ArrayList<Character> guessed = new ArrayList<>();
            int remaining = word.length();
            while (score < 7 && remaining > 0)
            {
                System.out.println(s1 + s2 + s3 + s4 + s5 + s6);
                System.out.println("Missed: " + missed);
                String hidden = "";
                for(char c : word.toCharArray())
                {
                    if(guessed.contains(c))
                    {
                        hidden = hidden + c;
                    }
                    else
                    {
                        hidden = hidden + "_";
                    }
                }
                System.out.println(hidden);
                System.out.println("Guess: ");
                char guess = scan.nextLine().charAt(0);
                if(word.indexOf(guess)==-1)
                {
                    score++;
                    missed.add(guess);
                    switch (score)
                    {
                        case 1: s2 = " O   |\n";break;
                        case 2: s3 = " |   |\n";break;
                        case 3: s3 = "/|   |\n";break;
                        case 4: s3 = "/|\\  |\n";break;
                        case 5: s4 = "/    |\n";break;
                        case 6: s4 = "/ \\  |\n";break;
                        default:
                    }
                }
                else if(guessed.indexOf(guess) == -1)
                {
                    guessed.add(guess);
                    remaining--;
                }
                remaining = 0;
                for(char c : word.toCharArray())
                {
                    if(!guessed.contains(c))
                    {
                        remaining ++;
                    }
                }
            }
            if(score == 7)
            {
                System.out.println("you lose");
                System.out.println("the word was " + word);
            }
            System.out.println("again?(y/n)");
            again = scan.nextLine().charAt(0) == 'y';
        }while(again);
        return 0;
    }
}