package com.DATN.PhanAnhSuCoDoThi.service.implement;

import com.google.genai.Client;
import com.google.genai.types.GenerateContentResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class GeminiService {

    @Value("${gemini.api.key}")
    private String apiKey; private Client client;
    public String generateTitle(String noiDung, String diaDiem) {

        this.client = Client.builder()
                .apiKey(apiKey)
                .build();

        String prompt = "Tạo tiêu đề ngắn gọn, chuyên nghiệp cho sự cố đô thị. Chỉ trả về tiêu đề.\nNội dung: %s\nĐịa điểm: %s.Tôi muốn kết quả cuối cùng là tiêu đề rồi bạn không cần đưa ra lựa chọn đâu."
                .formatted(noiDung, diaDiem);

        GenerateContentResponse response =
                client.models.generateContent(
                        "gemini-2.5-flash",
                        prompt,
                        null
                );

        return response.text();
    }
}