package de.serversenke.lxd.client.core.model;

import java.util.Arrays;

import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import okhttp3.Headers;
import okhttp3.MediaType;

@Builder
@Getter
@Setter
@EqualsAndHashCode
@ToString
public class LxdFile {
    public enum FileType {
        FILE("file"), DIRECTORY("directory"), SYMLINK("symlink");

        private final String type;

        FileType(String type) {
            this.type = type;
        }

        public String getFileType() {
            return this.type;
        }

        public static FileType fromFileType(String type) {
            return Arrays.stream(values())
              .filter(bl -> bl.type.equals(type))
              .findFirst()
              .orElse(null);
        }
    };

    public enum WriteType {
        APPEND("append"), OVERWRITE("overwrite");

        private final String type;

        WriteType(String type) {
            this.type = type;
        }

        public String getWriteType() {
            return this.type;
        }

        public static WriteType fromWriteType(String type) {
            return Arrays.stream(values())
              .filter(bl -> bl.type.equals(type))
              .findFirst()
              .orElse(null);
        }
    }

    private Headers headers;

    private long length;
    private MediaType contentType;
    private byte[] content;

    private FileType fileType;
    private WriteType writeType;

    private int uid;
    private int gid;
    private String mode;

    private String filename;
}
