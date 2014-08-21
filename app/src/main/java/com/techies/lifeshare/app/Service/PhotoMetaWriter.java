package com.techies.lifeshare.app.Service;

import android.app.Service;
import android.content.Intent;
import android.os.Bundle;
import android.os.Environment;
import android.os.IBinder;
import android.util.Log;

import org.apache.commons.imaging.ImageReadException;
import org.apache.commons.imaging.ImageWriteException;
import org.apache.commons.imaging.Imaging;
import org.apache.commons.imaging.common.bytesource.ByteSourceFile;
import org.apache.commons.imaging.formats.jpeg.xmp.JpegXmpRewriter;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class PhotoMetaWriter extends Service {

    private File newFile;

    public PhotoMetaWriter() {

    }

    public File metaWrite(File imageFile,Bundle metaBundle){
        double[] coordinates = metaBundle.getDoubleArray("photoCoordinates");
        float[] orientation = metaBundle.getFloatArray("photoOrientation");
        try {
            String xmpXml = "<x:xmpmeta>" +
                    "\n<Lifeshare>" +
                    "\n\t<Date>"+metaBundle.getString("photoDate")+"</Date>" +
                    "\n\t<Latitude>"+String.valueOf(coordinates[0])+"</Latitude>" +
                    "\n\t<Longitude>"+String.valueOf(coordinates[1])+"</Longitude>" +
                    "\n\t<Altitude>"+String.valueOf(coordinates[2])+"</Altitude>" +
                    "\n\t<Z>"+String.valueOf(orientation[0])+"</Z>" +
                    "\n\t<X>"+String.valueOf(orientation[1])+"</X>" +
                    "\n\t<Y>"+String.valueOf(orientation[2])+"</Y>" +
                    "\n</Lifeshare>" +
                    "\n</x:xmpmeta>";
            newFile = new File(Environment.getExternalStorageDirectory().getPath()+"/DCIM/newImage.jpg");
            JpegXmpRewriter rewriter = new JpegXmpRewriter();
            rewriter.updateXmpXml(new ByteSourceFile(imageFile),new BufferedOutputStream(new FileOutputStream(newFile)), xmpXml);
            String newXmpXml = Imaging.getXmpXml(newFile);
            //newFile.delete();
            Log.e("", "");
        } catch (final NullPointerException npe) {
            npe.printStackTrace();
        }catch (IOException e) {
            e.printStackTrace();
        }catch (ImageReadException e){
            e.printStackTrace();
        }catch (ImageWriteException e){
            e.printStackTrace();
        }
        return newFile;
    }

    @Override
    public IBinder onBind(Intent intent) {
        // TODO: Return the communication channel to the service.
        throw new UnsupportedOperationException("Not yet implemented");
    }
}
