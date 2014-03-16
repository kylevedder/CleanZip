/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package unzip;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import static unzip.Main.sleep;

/**
 *
 * @author Kyle
 */
public class UnZipObject
{
    /**
     * Unzip it
     *
     * @param zipFile input zip file
     * @param output zip file output folder
     */
    public void unZipIt(String zipFile, String outputFolder, String extensionlessFileName)
    {

        byte[] buffer = new byte[1024];

        try
        {

            //create output directory is not exists
            File folder = new File(outputFolder);
            if (!folder.exists())
            {
                folder.mkdir();
            }

            //get the zip file content
            ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile));
            //get the zipped file list entry
            ZipEntry ze = zis.getNextEntry();

            //check the very first entry in the zip to see if it is the same as the zip name
            if (ze != null)
            {
                System.out.println("not null");
                //if not a dir, then must be a file, and needs wrapper dir
                if (!ze.isDirectory()
                        //is a dir that is not the same name as the zip, needs a wrapper dir
                        //ze.getName().substring(0, ze.getName().length() - 1) trims off the last / in the filename (I can't get around it w/out this hack... oh well...
                        || !extensionlessFileName.equals(ze.getName().substring(0, ze.getName().length() - 1)))
                {
                    System.out.println("Making wrapper dir");
                    System.out.println("Old ouput " + outputFolder);
                    //change output to now include a wrapper folder
                    outputFolder = outputFolder + File.separator + extensionlessFileName;
                    System.out.println("New ouput " + outputFolder + " \n ========================");
                    //make a wrapper dir                    
                    new File(outputFolder).mkdir();
                }
//                new File(inputFile).getName()
            }
            //no entries found, locked or unintelligable
            else
            {
                System.out.println("Unable to read zip. It is locked or in unrecognized format.\nNow exiting...");
                sleep(2500);
            }

            while (ze != null)
            {
                if (!ze.isDirectory())
                {
                    String fileName = ze.getName();
                    File newFile = new File(outputFolder + File.separator + fileName);

                    System.out.println("file unzip : " + newFile.getAbsoluteFile());

                    //create all non exists folders
                    //else you will hit FileNotFoundException for compressed folder
                    new File(newFile.getParent()).mkdirs();

                    FileOutputStream fos = new FileOutputStream(newFile);

                    int len;
                    while ((len = zis.read(buffer)) > 0)
                    {
                        fos.write(buffer, 0, len);
                    }

                    fos.close();
                }
                else
                {
                    System.out.println("folder unzip : " + outputFolder + File.separator + ze.getName());
                    new File(outputFolder + File.separator + ze.getName()).mkdir();
                }
                ze = zis.getNextEntry();
            }

            zis.closeEntry();
            zis.close();

            System.out.println("Done");

        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }
}
