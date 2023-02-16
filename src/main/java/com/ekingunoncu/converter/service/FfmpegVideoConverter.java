package com.ekingunoncu.converter.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.bytedeco.ffmpeg.avcodec.AVCodec;
import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.FFmpegLogCallback;
import org.bytedeco.javacv.Frame;
import org.springframework.stereotype.Service;

import com.ekingunoncu.converter.enums.AudioProfile;
import com.ekingunoncu.converter.enums.VideoProfile;
import com.ekingunoncu.converter.exception.VideoConversionException;
import com.ekingunoncu.converter.model.ConvertionOptions;

@Service
public class FfmpegVideoConverter implements VideoConverter {

    {
        FFmpegLogCallback.set();
    }

    @Override
    public byte[] convert(ByteArrayInputStream inputStream, ConvertionOptions convertionOptions)
            throws VideoConversionException, FileNotFoundException {
        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(inputStream);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(outputStream,
                convertionOptions.getVideoProfile().getWidth(),
                convertionOptions.getVideoProfile().getHeight());

        try {
            grabber.start();
            setAudioSettings(recorder, convertionOptions.getAudioProfile());
            setVideoSettings(recorder, convertionOptions.getVideoProfile());
            recorder.setFormat(convertionOptions.getOutputVideoFormat().getValue());
            recorder.setVideoOption("preset", "ultrafast");
            recorder.setVideoOption("tune", "zerolatency");
            // Set the MOV muxer flags
            recorder.setOption("movflags", "frag_keyframe+empty_moov");

            convertFrames(grabber, recorder);
            grabber.stop();
            recorder.stop();
            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new VideoConversionException("Failed to convert video", e);
        } finally {
            closeResources(grabber, recorder);
        }
    }

    private void convertFrames(FFmpegFrameGrabber grabber, FFmpegFrameRecorder recorder)
            throws IOException, VideoConversionException {
        AVCodec codec = avcodec.avcodec_find_decoder(avcodec.AV_CODEC_ID_AAC);
        if (codec == null) {
            throw new VideoConversionException("Unsupported audio codec");
        }
        recorder.start();
        Frame frame;
        while ((frame = grabber.grabFrame()) != null) {
            recorder.record(frame);
        }
    }

    private void setVideoSettings(FFmpegFrameRecorder recorder,
            VideoProfile videoProfile) {
        recorder.setVideoCodec(videoProfile.getVideoCodec().getCodec());
        recorder.setVideoBitrate(videoProfile.getBitrate());
        recorder.setFrameRate(videoProfile.getFrameRate());
    }

    private void setAudioSettings(FFmpegFrameRecorder recorder,
            AudioProfile audioProfile) {
        recorder.setAudioCodec(audioProfile.getAudioCodec().getCodec());
        recorder.setAudioBitrate(audioProfile.getBitRate());
        recorder.setAudioChannels(audioProfile.getChannels());
        recorder.setAudioQuality(audioProfile.getQuality());
        recorder.setSampleRate(audioProfile.getSampleRate());
    }

    private void closeResources(FFmpegFrameGrabber grabber, FFmpegFrameRecorder recorder)
            throws VideoConversionException {
        try {
            grabber.close();
            recorder.close();
        } catch (IOException e) {
            System.out.println("Failed to close resources");
        }
    }
}
