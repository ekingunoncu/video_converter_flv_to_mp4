# Converter Application

Converter Application is a simple Spring Boot application that uses the JavaCV library to convert flv files to mp4. The application provides a REST API for uploading a video file and converting it to a different format, codec, or resolution.

* [![Quality Gate Status](https://sonarcloud.io/api/project_badges/measure?project=ekingunoncu_video_converter_flv_to_mp4&metric=alert_status)](https://sonarcloud.io/summary/overall?id=ekingunoncu_video_converter_flv_to_mp4)
 
* POST /convert
  
  This method is an HTTP POST endpoint that takes in a video file, along with conversion options, and returns a converted video file as a byte array resource. The conversion is done synchronously, and the result is returned in the HTTP response.
  
    - Parameters
        - The following parameters are expected by the method:
          - file - A MultipartFile representing the input video file to convert.
          - audioProfile - An AudioProfile enum representing the audio profile to use in the output video.
          - videoProfile - A VideoProfile enum representing the video profile to use in the output video.
          - outputVideoFormat - A VideoFormat enum representing the output video format to use.
  
    - Example Request
    ```
        curl -X POST -F "file=@jimmy_page_solo.flv" \
            -F "audioProfile=AAC_HIGH" \
            -F "videoProfile=MP4_MPEG4_HIGH" \
            -F "outputVideoFormat=MP4" \
            "http://localhost:8080/convert" \
            -o output.mp4
    ```

* POST /convert/async
  
  This method is an HTTP POST endpoint that takes in a video file, along with conversion options, and starts the conversion process in a separate thread. The result of the conversion is not returned in the HTTP response, but a message is returned to the user indicating that the conversion process has started and the result link will be send through slack channel when process is done.
  
    - Parameters
        - The following parameters are expected by the method:
          - file - A MultipartFile representing the input video file to convert.
          - audioProfile - An AudioProfile enum representing the audio profile to use in the output video.
          - videoProfile - A VideoProfile enum representing the video profile to use in the output video.
          - outputVideoFormat - A VideoFormat enum representing the output video format to use.
  
    - Example Request
    ```
        curl -X POST -F "file=@jimmy_page_solo.flv" \
            -F "audioProfile=AAC_HIGH" \
            -F "videoProfile=MP4_MPEG4_HIGH" \
            -F "outputVideoFormat=MP4" \
            "http://localhost:8080/convert/async" 
    ```

Here is an example of converted video in MP4 format:

[![Jimmy Page Solo MP4](https://video-converter-flv-to-mp4.s3.eu-west-1.amazonaws.com/thumbnail.png)](https://video-converter-flv-to-mp4.s3.eu-west-1.amazonaws.com/output.mp4)

And here is the input video in FLV format:

[![Jimmy Page Solo FLV](https://video-converter-flv-to-mp4.s3.eu-west-1.amazonaws.com/thumbnail.png)](https://video-converter-flv-to-mp4.s3.eu-west-1.amazonaws.com/jimmy_page_solo.flv)