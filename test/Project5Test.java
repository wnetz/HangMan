
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.*;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
class Project5Test
{
    private static File f;
    private static Scanner scan;
    private project5.Project5 p;
    @BeforeAll
    static void prep()
    {
        f = new File("./test/testInput.txt");
        try
        {
            scan = new Scanner(f);
        }
        catch (FileNotFoundException e)
        {
            System.out.println("file not found");
        }

    }
    @AfterAll
    static void clean()
    {
        scan.close();
    }
}