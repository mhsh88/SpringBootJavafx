package sample.util;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class FileLocation {
    final private static  String Folder ="/Behin-Simulator";
    final private static String TempFolder = "/Temporary";
    final private static String CgsFileName = "/cgs.txt";
    private  String readFile(String path)
            throws IOException
    {
        Paths.get(path);
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, Charset.defaultCharset());
    }
    public  File getFileLocation() throws IOException {
//        String pngpath = StationController.class.getClassLoader().getResource("../../saveFile/cgs").getFile();
//        System.out.println(this.getClass().getClassLoader().getResource("./sample/saveFile/cgs"));

//        String location = readFile(file.getAbsolutePath());
//        System.out.println(ClassLoader.getSystemResource("."));
//        System.out.println(ClassLoader.getSystemResource("./sample/saveFile/cgs"));
//        ClassLoader.getSystemResource("./sample/saveFile/cgs").getFile();
//        String pngpath = ClassLoader.getSystemResource("./sample/saveFile/cgs").getFile();

//        File file1 = new File(pngpath);
//        System.out.println(file1.getAbsolutePath());

        File file = storeFileLocation();

        String location = readFile(file.getAbsolutePath());
        location = location.trim();
        File tempFile = new File(location);
        File recordsDir = new File(System.getProperty("user.home"), Folder);;

        if (!recordsDir.exists()) {
            recordsDir.mkdirs();
        }
        if(tempFile.exists())
            return tempFile;
        else
            return recordsDir;

    }

    public void setFileLocation(String dir) throws IOException {
//        String pngpath = StationController.class.getClassLoader().getResource("../../saveFile/cgs").getFile();
//        String pngpath  = ClassLoader.getSystemResource("./sample/saveFile/cgs").getFile();
//        File file = new File(pngpath);
        File file = storeFileLocation();
        Path path = Paths.get(file.getAbsolutePath());
        Files.write(path, Arrays.asList(dir));
    }

    private File storeFileLocation() throws IOException {
        URL filepath = this.getClass().getClassLoader().getResource(System.getProperty("user.home") + Folder + TempFolder + CgsFileName);

        File file = new File(filepath != null ? filepath.getFile() : System.getProperty("user.home") + Folder + TempFolder + CgsFileName);
        if(!file.exists()){
//            file.mkdir();
//            Paths.get(file.getAbsolutePath());
            Files.createDirectories(Paths.get(file.getParent()));
//            Files.createFile(Paths.get(file.getAbsolutePath()));
//            Files.createDirectory(Paths.get(file.getParent()));

            file.createNewFile();

        }
        return file;

    }
}
