package com.ekingunoncu.converter.controller;

import static com.ekingunoncu.converter.utils.TestUtils.loadInputBytesFromFile;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

/**
 * 
 * Integration tests for {@link VideoConversionController}.
 */
@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
class VideoConversionControllerIntegrationTest {

    @Autowired
    private MockMvc mockMvc;

    /**
     * Test converting a video file with specified audio and video profiles, and
     * output format.
     * 
     * @throws Exception if an error occurs during the test.
     */
    @Test
    void testConvertVideo() throws Exception {
        // Load a sample mp4 video file from the resources folder
        byte[] fileBytes = loadInputBytesFromFile("jimmy_page_solo.flv");
        MockMultipartFile file = new MockMultipartFile("file", "jimmy_page_solo.flv", "video/flv", fileBytes);

        mockMvc.perform(multipart("/convert")
                .file(file)
                .param("audioProfile", "AAC_LOW")
                .param("videoProfile", "MP4_H264_LOW"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_OCTET_STREAM));
    }

}
