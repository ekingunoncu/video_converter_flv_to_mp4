package com.ekingunoncu.converter.service;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

import org.bytedeco.ffmpeg.avcodec.AVCodec;
import org.bytedeco.ffmpeg.global.avcodec;
import org.bytedeco.javacv.FFmpegFrameGrabber;
import org.bytedeco.javacv.FFmpegFrameRecorder;
import org.bytedeco.javacv.Frame;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.ekingunoncu.converter.enums.AudioProfile;
import com.ekingunoncu.converter.enums.VideoProfile;
import com.ekingunoncu.converter.exception.VideoConversionException;

import lombok.RequiredArgsConstructor;

/**
 * Service class for converting video files using FFmpeg.
 */
@Service
@RequiredArgsConstructor
public class FfmpegVideoConverter implements VideoConverter {

    private final S3UploadService s3UploadService;

    private final SlackService slackService;

    /**
     * Converts a video file to the specified format and returns the converted file
     * as a byte array.
     * 
     * @param inputStream       The input stream for the video file to be converted.
     * @param audioProfile      The audio profile to use for the conversion.
     * @param videoProfile      The video profile to use for the conversion.
     * @return A byte array containing the converted video file.
     * @throws VideoConversionException If an error occurs during the video
     *                                  conversion process.
     * @throws FileNotFoundException    If the input file is not found.
     */
    @Override
    public byte[] convert(ByteArrayInputStream inputStream, AudioProfile audioProfile,
            VideoProfile videoProfile)
            throws VideoConversionException, FileNotFoundException {
        FFmpegFrameGrabber grabber = new FFmpegFrameGrabber(inputStream);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(outputStream,
                videoProfile.getWidth(),
                videoProfile.getHeight());

        try {
            grabber.start();
            setAudioSettings(recorder, audioProfile);
            setVideoSettings(recorder, videoProfile);
            recorder.setFormat(videoProfile.getVideoFormat().getValue());
            recorder.setVideoOption("preset", "ultrafast");
            recorder.setVideoOption("tune", "zerolatency");
            // Set the MOV muxer flags
            recorder.setOption("movflags", "frag_keyframe+empty_moov");
            convertFrames(grabber, recorder);
            grabber.stop();
            recorder.stop();
            return outputStream.toByteArray();
        } catch (Exception e) {
            throw new VideoConversionException("Failed to convert video " + e.getMessage(), e);
        } finally {
            closeResources(grabber, recorder);
        }
    }

    /**
     * Converts a video file asynchronously using the fileConversionExecutor thread
     * pool.
     * 
     * @param inputStream       The input stream for the video file to be converted.
     * @param audioProfile      The audio profile to use for the conversion.
     * @param videoProfile      The video profile to use for the conversion.
     * @throws VideoConversionException If an error occurs during the video
     *                                  conversion process.
     * @throws IOException
     */
    @Async("fileConversionExecutor")
    public void asyncConvert(MultipartFile file, AudioProfile audioProfile,
            VideoProfile videoProfile)
            throws VideoConversionException, IOException {
        ByteArrayInputStream inputStream = new ByteArrayInputStream(file.getBytes());
        String originalFileName = file.getOriginalFilename();
        byte[] output = convert(inputStream, audioProfile, videoProfile);
        String fileUrl = s3UploadService.uploadFileToS3(output, generateFileName(originalFileName, videoProfile.getVideoFormat().getValue()));
        slackService.sendMessage("Your file is ready " + fileUrl);
    }

    private String generateFileName(String originalFileName, String outputVideoFormat) {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmssSSS");
        String filename = Objects.nonNull(originalFileName)
                ? originalFileName.substring(0, originalFileName.lastIndexOf('.'))
                : "converted";
        String formattedDateTime = now.format(formatter);
        return String.format("%s_%s.%s", filename, formattedDateTime, outputVideoFormat);
    }

    /**
     * Converts each frame of the video using the specified grabber and recorder.
     * 
     * @param grabber  The FFmpeg frame grabber to use.
     * @param recorder The FFmpeg frame recorder to use.
     * @throws VideoConversionException If an error occurs during the video
     *                                  conversion process.
     * @throws IOException              If an I/O error occurs.
     */
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

    /**
     * Sets the video settings of the given FFmpegFrameRecorder using the provided
     * VideoProfile.
     *
     * @param recorder     the FFmpegFrameRecorder whose video settings will be set
     * @param videoProfile the VideoProfile to use for setting the video settings of
     *                     the recorder
     */
    private void setVideoSettings(FFmpegFrameRecorder recorder,
            VideoProfile videoProfile) {
        recorder.setVideoCodec(videoProfile.getVideoCodec().getCodec());
        recorder.setVideoBitrate(videoProfile.getBitrate());
        recorder.setFrameRate(videoProfile.getFrameRate());
    }

    /**
     * Sets the audio settings of the given FFmpegFrameRecorder using the provided
     * AudioProfile.
     *
     * @param recorder     the FFmpegFrameRecorder whose audio settings will be set
     * @param audioProfile the AudioProfile to use for setting the audio settings of
     *                     the recorder
     */
    private void setAudioSettings(FFmpegFrameRecorder recorder,
            AudioProfile audioProfile) {
        recorder.setAudioCodec(audioProfile.getAudioCodec().getCodec());
        recorder.setAudioBitrate(audioProfile.getBitRate());
        recorder.setAudioChannels(audioProfile.getChannels());
        recorder.setAudioQuality(audioProfile.getQuality());
        recorder.setSampleRate(audioProfile.getSampleRate());
    }

    /**
     * Closes the given FFmpegFrameGrabber and FFmpegFrameRecorder.
     *
     * @param grabber  the FFmpegFrameGrabber to close
     * @param recorder the FFmpegFrameRecorder to close
     * @throws VideoConversionException if an error occurs while closing the
     *                                  resources
     */
    private void closeResources(FFmpegFrameGrabber grabber, FFmpegFrameRecorder recorder)
            throws VideoConversionException {
        try {
            grabber.close();
            recorder.close();
        } catch (IOException e) {
            throw new VideoConversionException("Failed to close resources", e);
        }
    }

}
