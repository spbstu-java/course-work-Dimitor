package ui;

import javafx.application.Platform;
import javafx.scene.control.TextArea;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.Charset;
import java.util.Objects;

public class TextAreaOutputStream extends OutputStream {
    private final TextArea area;
    private final Charset charset;

    public TextAreaOutputStream(TextArea area, Charset charset) {
        this.area = Objects.requireNonNull(area);
        this.charset = Objects.requireNonNull(charset);
    }

    @Override
    public void write(int b) throws IOException {
        write(new byte[] { (byte) b });
    }

    @Override
    public void write(byte[] b, int off, int len) throws IOException {
        String s = new String(b, off, len, charset);
        Platform.runLater(() -> area.appendText(s));
    }
}
