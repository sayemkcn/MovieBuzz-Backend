package net.toracode.moviedb.Commons;

import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * Created by sayemkcn on 10/29/16.
 */
@Component
public class ImageValidator {
    public boolean isImageValid(MultipartFile multipartFile){
        try {
            Image image = ImageIO.read(this.convertToFile(multipartFile));
            if (image!=null) {
                return true;
            }
        } catch (IOException e) {
            System.out.println("IOException: "+e.toString());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    private File convertToFile(MultipartFile multipartFile) throws Exception{
        File file = new File(multipartFile.getOriginalFilename());
        file.createNewFile();
        FileOutputStream fileOutputStream = new FileOutputStream(file);
        fileOutputStream.write(multipartFile.getBytes());
        fileOutputStream.close();
        return file;
    }
}
