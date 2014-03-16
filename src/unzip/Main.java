/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unzip;

import java.io.File;
import java.util.List;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Kyle
 */
public class Main
{

    public static final boolean DEBUG_MODE = false;

    List<String> fileList;
    private static String inputFile = "D:\\Documents\\NetBeansProjects\\CleanZip\\src";
    private static String outputFolder = "D:\\Documents\\NetBeansProjects\\CleanZip";

    public static void main(String[] args)
    {
        for (String s : args)
        {
            System.out.println(s);
        }

        if (args.length == 1 || DEBUG_MODE)
        {
            //true regardless of file or folder
            if (!DEBUG_MODE)
            {
//                System.out.println(args[0]);
                inputFile = args[0];
                outputFolder = new File(inputFile).getParent();
            }

            //to unzip
            if (!new File(inputFile).isDirectory())
            {
                String extensionlessFileName;
                //get the .zip file w/out the .zip
                if (new File(inputFile).getName().contains((CharSequence) "."))
                {
                    extensionlessFileName = new File(inputFile).getName().substring(0, new File(inputFile).getName().lastIndexOf("."));
                }
                //if somehow a .zip doesn't have a .zip extension... or a '.' at all
                else
                {
                    extensionlessFileName = new File(inputFile).getName();
                }
                if (DEBUG_MODE)
                {
                    System.out.println(extensionlessFileName);
                }
                UnZipObject unZipObj = new UnZipObject();
                unZipObj.unZipIt(inputFile, outputFolder, extensionlessFileName);
            }
            //to zip up
            else
            {
                ZipObject zipObj = new ZipObject();
                zipObj.generateFileList(new File(inputFile), new File(inputFile));
                zipObj.zipItUp(outputFolder, inputFile);
            }
        }
        else
        {
            System.out.println("Please enter appropriate arguments.");
            sleep(2000);
        }

    }

    /**
     * Sleep time in milis
     *
     * @param time long in milis
     */
    public static void sleep(long time)
    {
        try
        {
            Thread.sleep(time);
        }
        catch (InterruptedException ex)
        {
            Logger.getLogger(Main.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
