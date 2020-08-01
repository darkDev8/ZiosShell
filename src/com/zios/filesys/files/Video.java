package com.zios.filesys.files;

import com.xuggle.xuggler.ICodec;
import com.xuggle.xuggler.IContainer;
import com.xuggle.xuggler.IStream;
import com.xuggle.xuggler.IStreamCoder;
import com.zios.root.ToolBox;

import java.io.IOException;

public class Video extends FileSYS {

    private String duration;
    private String streams;
    private String bitRate;

    private String audioSampleRate;
    private String audioChannels;

    private String width;
    private String height;
    private String videoFrameRate;

    public Video(String path) {
        super(path);

        if (exist()) {
            try {
                IContainer container = IContainer.make();
                int result = container.open(path, IContainer.Type.READ, null);

                if (result < 0) {
                    new ToolBox().print("Unable to open the media.", true);
                    return;
                }

                this.duration = String.valueOf(container.getDuration());
                this.streams = String.valueOf(container.getNumStreams());
                this.bitRate = String.valueOf(container.getBitRate());

                for (int i = 0; i < Integer.parseInt(streams); i++) {
                    IStream stream = container.getStream(i);
                    IStreamCoder coder = stream.getStreamCoder();

                    if (coder.getCodecType() == ICodec.Type.CODEC_TYPE_AUDIO) {

                        this.audioSampleRate = String.valueOf(coder.getSampleRate());
                        this.audioChannels = String.valueOf(coder.getChannels());

                    } else if (coder.getCodecType() == ICodec.Type.CODEC_TYPE_VIDEO) {
                        this.width = String.valueOf(coder.getWidth());
                        this.height = String.valueOf(coder.getHeight());
                        this.videoFrameRate = String.valueOf(coder.getFrameRate().getDouble());
                    }
                }

            } catch (Exception ex) {
              //  ex.printStackTrace();
            }
        }
    }

    public String getDuration() {
        return duration;
    }

    public String getStreams() {
        return streams;
    }

    public String getBitRate() {
        return bitRate;
    }

    public String getAudioSampleRate() {
        return audioSampleRate;
    }

    public String getAudioChannels() {
        return audioChannels;
    }

    public String getWidth() {
        return width;
    }

    public String getHeight() {
        return height;
    }

    public String getVideoFrameRate() {
        return videoFrameRate;
    }
}
