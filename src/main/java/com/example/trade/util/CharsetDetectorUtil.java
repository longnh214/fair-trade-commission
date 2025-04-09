package com.example.trade.util;

import org.mozilla.universalchardet.UniversalDetector;
import org.springframework.stereotype.Component;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;

@Component
public class CharsetDetectorUtil {
    public Charset detectCharset(InputStream inputStream, Charset fallbackCharset) throws IOException {
        if (!inputStream.markSupported()) {
            inputStream = new BufferedInputStream(inputStream);
        }

        inputStream.mark(4096);
        byte[] buf = new byte[4096];
        UniversalDetector detector = new UniversalDetector(null);

        int nread;
        while ((nread = inputStream.read(buf)) > 0 && !detector.isDone()) {
            detector.handleData(buf, 0, nread);
        }

        detector.dataEnd();
        inputStream.reset();

        String encoding = detector.getDetectedCharset();
        detector.reset();

        if (encoding != null) {
            return Charset.forName(encoding);
        } else {
            return fallbackCharset;
        }
    }
}