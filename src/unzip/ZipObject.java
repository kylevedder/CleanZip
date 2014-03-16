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
import java.util.ArrayList;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

/**
 *
 * @author Kyle
 */
public class ZipObject
{

    List<String> fileList;

    ZipObject()
    {
        fileList = new ArrayList<String>();
    }

    /**
     * 
     * @param zipFile outputFolder where the zip is to go
     * @param inputFolder inputFolder where the last file in the path is to be the name of the zip
     */
    public void zipItUp(String zipFile, String inputFolder)
    {

        System.out.println(zipFile);
        System.out.println(inputFolder);
        byte[] buffer = new byte[1024];

        try
        {

            FileOutputStream fos = new FileOutputStream(zipFile + File.separator + new File(inputFolder).getName() + ".zip");
            ZipOutputStream zos = new ZipOutputStream(fos);

            System.out.println("Output to Zip : " + zipFile);

            for (String file : this.fileList)
            {

                System.out.println("File Added : " + file);
                ZipEntry ze = new ZipEntry(file);
                zos.putNextEntry(ze);

                FileInputStream in = new FileInputStream(inputFolder + File.separator + file);

                int len;
                while ((len = in.read(buffer)) > 0)
                {
                    zos.write(buffer, 0, len);
                }

                in.close();
            }

            zos.closeEntry();
            //remember close it
            zos.close();

            System.out.println("Done");
        }
        catch (IOException ex)
        {
            ex.printStackTrace();
        }
    }

    /**
     * Traverse a directory and get all files, and add the file into fileList
     *
     * @param node file or directory
     */
    public void generateFileList(File node, File baseDirectory)
    {

//        System.out.println(node.getAbsoluteFile().toString());
        //add file only
        if (node.isFile())
        {
            fileList.add(generateZipEntry(node.getAbsoluteFile().toString(), baseDirectory.getAbsoluteFile().toString()));
        }

        if (node.isDirectory())
        {
            String[] subNote = node.list();
            for (String filename : subNote)
            {
                generateFileList(new File(node, filename), baseDirectory);
            }
        }

    }

    /**
     * Format the file path for zip
     *
     * @param file file path
     * @return Formatted file path
     */
    private String generateZipEntry(String file, String baseFilePath)
    {
        System.out.println(file.substring(baseFilePath.length() + 1, file.length()));
        return file.substring(baseFilePath.length() + 1, file.length());
    }
}
