package project5;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.*;

public class Project5
{
    Random rand;
    Scanner scan;
    String word;
    String root = "./src/WordListsInCSV/";

    public static void main(String[] args) {
        Project5 q = new Project5(new Scanner(System.in));
        q.run();
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
        String w = "";
        System.out.println(file);
        try
        {
            List<String> words = Files.readAllLines(Paths.get(file), StandardCharsets.UTF_8);
            w = words.get(rand.nextInt(words.size()));
            System.out.println(w);
        }
        catch (IOException e)
        {
            System.out.println("file not found 3");
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

        String man = "";
        System.out.println("H A N G M A N");
        try
        {
            man = Files.readAllLines(Paths.get("./src/hang.txt"), StandardCharsets.UTF_8).get(0);
            man = man.replaceAll(",","\n");
        }
        catch (IOException e)
        {
            System.out.println("file not found 1");
        }
        boolean again = true;
        String missed = "";
        String guessed = "";
        int score = -1;
        int remaining = word.length();
        System.out.print("what is your name: ");
        String name = scan.nextLine();
        do
        {
            if(score < 0)
            {
                score = 0;
                //word = pickWord();
                word = "destitute";
                missed = "";
                guessed = "";
                remaining = word.length();
            }
            else if (score < 7 && remaining > 0)
            {
                String temp = guessed;
                System.out.println(man);
                System.out.println("Missed: " + missed);
                String hidden = "";
                Arrays.stream(word.split(""))
                        .forEach(c->{
                            if(temp.contains(c))
                            {
                                System.out.print(c);
                            }
                            else
                            {
                                System.out.print("_");
                            }
                        });
                System.out.println(hidden);
                System.out.print("Guess: ");
                char guess = scan.nextLine().charAt(0);
                if(word.indexOf(guess)==-1) {
                    score++;
                    if(missed.indexOf(guess) == -1)
                    {
                        missed = missed + guess;
                    }
                    if (score < 7)
                    {
                        try {
                            man = Files.readAllLines(Paths.get("./src/hang.txt"), StandardCharsets.UTF_8).get(score);
                            man = man.replaceAll(",", "\n");
                        } catch (IOException e) {
                            System.out.println("file not found 2");
                        }
                    }
                }
                else if(guessed.indexOf(guess) == -1)
                {
                    guessed = guessed + guess;
                    remaining -= Arrays.stream(word.split("")).reduce("",(a,b) -> b.indexOf(guess)!=-1? a+b:a).length();
                }
            }
            else if(score == 7 || remaining == 0)
            {
                if(score == 7)
                {
                    System.out.println("you lose");
                    System.out.println("the word was " + word);
                }
                else
                {
                    double playerScore = score == 0 ?word.length()*2:word.length()/(double)score;
                    try
                    {
                        List<Double> scores = Files.readAllLines(Paths.get("./src/score.txt"), StandardCharsets.UTF_8)
                                .stream()
                                .map(e -> Double.valueOf(e.substring(e.indexOf(":") + 1)))
                                .sorted()
                                .toList();
                        if(scores.size() > 0 && scores.get(scores.size()-1) < playerScore)
                        {
                            System.out.println("you set a new high score of " + playerScore);
                        }
                        else
                        {
                            System.out.println("score: " + playerScore);
                        }
                        BufferedWriter writer = new BufferedWriter(new FileWriter("./src/score.txt",true));
                        writer.newLine();
                        writer.write(name + ":" + playerScore);
                        writer.close();
                    } catch (IOException e) {
                        System.out.println("file not found 3");
                    }
                }
                System.out.println("again?(y/n)");
                again = scan.nextLine().charAt(0) == 'y';
                score = -1;
            }
        }while(again);
        return 0;
    }
}