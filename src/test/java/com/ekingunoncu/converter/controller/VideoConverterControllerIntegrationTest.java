package com.ekingunoncu.converter.controller;

import static com.ekingunoncu.converter.utils.TestUtils.loadInputBytesFromFile;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@AutoConfigureMockMvc
@ExtendWith(SpringExtension.class)
class VideoConversionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Test
    void convertVideo_returnsOkResponseWithFile() throws Exception {
        // Load a sample mp4 video file from the resources folder
        MockMultipartFile file = new MockMultipartFile("file", "jimmy_page_solo.mp4", "video/mp4",
                loadInputBytesFromFile("jimmy_page_solo.mp4"));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.multipart("/convert")
                .file(file)
                .param("videoFormat", "FLV")
                .param("audioProfile", "AAC_LOW")
                .param("videoProfile", "MP4_H264_LOW"))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andReturn();

        // Verify the response content type, content length, and content disposition
        String contentDispositionHeader = result.getResponse().getHeader(HttpHeaders.CONTENT_DISPOSITION);
        assertNotNull(contentDispositionHeader);
        assertEquals("attachment; filename=jimmy_page_solo.flv", contentDispositionHeader);

        String contentType = result.getResponse().getContentType();
        assertNotNull(contentType);
        assertEquals(MediaType.APPLICATION_OCTET_STREAM_VALUE, contentType);

        int contentLength = result.getResponse().getContentLength();
        assertTrue(contentLength > 0);
    }
}
