package csv.write;

import com.google.common.base.Joiner;
import csv.write.exception.CsvWriteException;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.util.List;

/**
 * Basic csv writer.
 */
public class BasicCsvWriter {

    private final BufferedWriter bw;
    private final String separator;

    public BasicCsvWriter(final OutputStream os, final String separator) {

        this.bw = prepareBufferedWriter(os);
        this.separator = separator;
    }

    /**
     * Method writes list of elements into the stream, separated by given separator.
     * @param elements list of elements that will be saved into the file.
     *
     */
    public void write(final List<String> elements) {

        try {
            final String csvLine = prepareLine(elements);
            this.bw.write(csvLine);

        } catch (final IOException ioe) {

            throw new CsvWriteException("Error occur when writing to the stream ", ioe);
        }
    }

    String prepareLine(final List<String> elements) {

        return Joiner.on(this.separator).join(elements);
    }

    BufferedWriter prepareBufferedWriter(final OutputStream os) {

        return new BufferedWriter(new PrintWriter(os));
    }


}
