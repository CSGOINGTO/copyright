package net.Ken365X.Utils;

import java.io.*;

/**
 * Created by mac on 2018/3/14.
 */
public class StreamUtil {

    // utilF : convert inputstream to String
    public static String inputStream2String(InputStream is) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        int i = -1;
        while ((i = is.read()) != -1) {
            baos.write(i);
        }
        return baos.toString();
    }

}
