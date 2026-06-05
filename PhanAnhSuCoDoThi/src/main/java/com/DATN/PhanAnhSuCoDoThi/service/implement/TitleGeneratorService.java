package com.DATN.PhanAnhSuCoDoThi.service.implement;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.LinkedHashMap;
import java.util.Map;


@Service
public class TitleGeneratorService {

    private static final Map<String, String> KEYWORD_MAP = new LinkedHashMap<>() {{

        // ========== CÂY XANH CÔNG CỘNG ==========
        put("cây ngã đổ", "Cây ngã đổ nguy hiểm");
        put("cây bị ngã", "Cây ngã đổ nguy hiểm");
        put("cây bật gốc", "Cây bật gốc nguy hiểm");
        put("cây đổ", "Cây đổ nguy hiểm");
        put("cành cây gãy", "Cành cây gãy nguy hiểm");
        put("cành cây", "Cành cây nguy hiểm");
        put("cây khô chết", "Cây khô chết nguy hiểm");
        put("cây chết", "Cây chết khô nguy hiểm");
        put("cây mục", "Cây mục nguy hiểm");
        put("gốc cây trồi", "Gốc cây trồi hư hỏng vỉa hè");
        put("gốc cây", "Gốc cây hư hỏng vỉa hè");
        put("cây nghiêng", "Cây nghiêng nguy hiểm");
        put("cây ngã", "Cây ngã đổ nguy hiểm");
        put("cây", "Sự cố cây xanh công cộng");
        put("cây xanh", "Sự cố cây xanh công cộng");
        // ========== ĐIỆN ==========
        put("chập điện", "Chập điện nguy hiểm");
        put("mất điện", "Mất điện");
        put("cúp điện", "Cúp điện");
        put("điện giật", "Nguy cơ điện giật");
        put("dây điện đứt", "Dây điện bị đứt nguy hiểm");
        put("dây điện võng", "Dây điện võng nguy hiểm");
        put("dây điện chảy", "Dây điện chảy nhựa nguy hiểm");
        put("dây điện trần", "Dây điện trần nguy hiểm");
        put("dây điện", "Sự cố dây điện");
        put("cột điện nghiêng", "Cột điện bị nghiêng nguy hiểm");
        put("cột điện gãy", "Cột điện gãy đổ");
        put("cột điện mục", "Cột điện mục hỏng");
        put("cột điện", "Cột điện hư hỏng");
        put("hộp điện", "Hộp điện hư hỏng");
        put("tủ điện", "Tủ điện hư hỏng");
        put("trạm điện", "Sự cố trạm điện");
        put("điện", "Sự cố điện");

        // ========== CẤP NƯỚC ==========
        put("vỡ ống nước", "Vỡ ống nước");
        put("ống nước bị vỡ", "Vỡ ống nước");
        put("ống nước rò rỉ", "Rò rỉ ống nước");
        put("rò rỉ nước", "Rò rỉ đường ống nước");
        put("vỡ ống", "Vỡ ống nước");
        put("ống nước", "Hư hỏng ống nước");
        put("đường ống", "Hư hỏng đường ống");
        put("mất nước", "Mất nước sinh hoạt");
        put("thiếu nước", "Thiếu nước sinh hoạt");
        put("nước yếu", "Áp lực nước yếu");
        put("nước bẩn", "Nước sinh hoạt bị ô nhiễm");
        put("nước đục", "Nước sinh hoạt bị đục");
        put("van nước", "Hư hỏng van nước");
        put("đồng hồ nước", "Hư hỏng đồng hồ nước");
        put("trụ nước", "Hư hỏng trụ nước");
        put("nước", "Sự cố cấp nước");

        // ========== THOÁT NƯỚC - NGẬP ÚNG ==========
        put("tắc cống", "Tắc cống thoát nước");
        put("cống bị tắc", "Tắc cống thoát nước");
        put("cống vỡ", "Vỡ cống thoát nước");
        put("cống bị vỡ", "Vỡ cống thoát nước");
        put("cống bị sụt", "Sụt cống thoát nước");
        put("cống tràn", "Cống tràn nước thải");
        put("miệng cống", "Hư hỏng miệng cống");
        put("nắp cống", "Hư hỏng nắp cống");
        put("hố ga bị vỡ", "Vỡ hố ga thoát nước");
        put("hố ga mất nắp", "Mất nắp hố ga nguy hiểm");
        put("hố ga", "Hư hỏng hố ga");
        put("mương tắc", "Tắc nghẽn mương thoát nước");
        put("mương vỡ", "Vỡ mương thoát nước");
        put("mương", "Sự cố mương thoát nước");
        put("rãnh thoát nước", "Tắc nghẽn rãnh thoát nước");
        put("ngập lụt", "Ngập lụt");
        put("ngập nước", "Ngập nước");
        put("ngập úng", "Ngập úng");
        put("cống", "Tắc nghẽn cống");
        put("ngập", "Ngập úng");

        // ========== ĐƯỜNG GIAO THÔNG ==========
        put("ổ gà", "Ổ gà mặt đường");
        put("ổ voi", "Ổ voi mặt đường");
        put("vỉa hè bị vỡ", "Vỡ vỉa hè");
        put("vỉa hè lún", "Lún vỉa hè");
        put("vỉa hè nứt", "Nứt vỉa hè");
        put("vỉa hè", "Hư hỏng vỉa hè");
        put("lề đường", "Hư hỏng lề đường");
        put("dải phân cách bị vỡ", "Vỡ dải phân cách");
        put("dải phân cách", "Hư hỏng dải phân cách");
        put("vạch kẻ đường mờ", "Mờ vạch kẻ đường");
        put("vạch kẻ đường", "Hư hỏng vạch kẻ đường");
        put("taluy sạt", "Sạt lở taluy");
        put("taluy", "Sạt lở taluy");
        put("sạt lở", "Sạt lở đất");
        put("lún đường", "Lún sụt mặt đường");
        put("sụt lún", "Sụt lún mặt đường");
        put("nứt đường", "Nứt mặt đường");
        put("mặt đường hỏng", "Hư hỏng mặt đường");
        put("đường bị ngập", "Đường bị ngập nước");
        put("đường hẹp", "Đường bị thu hẹp");
        put("lún", "Lún sụt mặt đường");
        put("nứt", "Nứt mặt đường");
        put("sụt", "Sụt lún đường");
        put("đường", "Hư hỏng đường");

        // ========== CẦU - HẦM ==========
        put("cầu bị nứt", "Nứt kết cấu cầu");
        put("cầu bị lún", "Lún kết cấu cầu");
        put("cầu bị hỏng", "Hư hỏng cầu");
        put("mặt cầu", "Hư hỏng mặt cầu");
        put("lan can cầu", "Hư hỏng lan can cầu");
        put("trụ cầu", "Hư hỏng trụ cầu");
        put("hầm chui", "Sự cố hầm chui");
        put("hầm bộ hành", "Sự cố hầm bộ hành");
        put("cầu vượt", "Sự cố cầu vượt");
        put("cầu", "Sự cố cầu đường");
        put("hầm", "Sự cố hầm");

        // ========== CHIẾU SÁNG CÔNG CỘNG ==========
        put("đèn tín hiệu hỏng", "Hỏng đèn tín hiệu giao thông");
        put("đèn giao thông hỏng", "Hỏng đèn giao thông");
        put("đèn giao thông", "Sự cố đèn giao thông");
        put("đèn tín hiệu", "Sự cố đèn tín hiệu");
        put("đèn đường hỏng", "Hỏng đèn đường");
        put("đèn đường không sáng", "Đèn đường không sáng");
        put("đèn đường chập", "Chập điện đèn đường");
        put("đèn đường", "Hỏng đèn đường");
        put("bóng đèn", "Hỏng bóng đèn đường");
        put("trụ đèn", "Hư hỏng trụ đèn đường");
        put("đèn", "Hỏng đèn đường");

        // ========== AN TOÀN GIAO THÔNG ==========
        put("biển báo mất", "Mất biển báo giao thông");
        put("biển báo hỏng", "Hỏng biển báo giao thông");
        put("biển báo nghiêng", "Biển báo bị nghiêng");
        put("biển báo", "Sự cố biển báo giao thông");
        put("gương cầu vỡ", "Vỡ gương cầu");
        put("gương cầu mất", "Mất gương cầu");
        put("gương cầu", "Hư hỏng gương cầu");
        put("lan can bị gãy", "Gãy lan can");
        put("lan can bị hỏng", "Hư hỏng lan can");
        put("lan can", "Hư hỏng lan can");
        put("hộ lan bị hỏng", "Hư hỏng hộ lan");
        put("hộ lan", "Hư hỏng hộ lan");
        put("thanh chắn", "Hư hỏng thanh chắn");
        put("rào chắn", "Hư hỏng rào chắn");
        put("gờ giảm tốc", "Hư hỏng gờ giảm tốc");
        put("cọc tiêu", "Hư hỏng cọc tiêu");
        put("tường hộ lan", "Hư hỏng tường hộ lan");



        // ========== CÔNG TRÌNH HẠ TẦNG ==========
        put("nứt tường", "Nứt tường công trình");
        put("tường nứt", "Nứt tường công trình");
        put("tường sụp", "Tường sụp đổ nguy hiểm");
        put("tường đổ", "Tường đổ nguy hiểm");
        put("tường nghiêng", "Tường nghiêng nguy hiểm");
        put("tường bong tróc", "Tường bong tróc");
        put("giàn giáo đổ", "Giàn giáo đổ nguy hiểm");
        put("giàn giáo", "Sự cố giàn giáo");
        put("công trình sụp", "Công trình sụp đổ");
        put("công trình sập", "Công trình sập đổ");
        put("sụp đổ", "Công trình sụp đổ nguy hiểm");
        put("sập đổ", "Nguy cơ sập đổ công trình");
        put("sụp", "Nguy cơ sụp đổ");
        put("sập", "Nguy cơ sập đổ");
        put("nền đất lún", "Lún nền đất");
        put("móng công trình", "Sự cố móng công trình");
        put("trần nhà", "Hư hỏng trần nhà công cộng");
        put("mái che", "Hư hỏng mái che công cộng");
        put("tường", "Tường hư hỏng");
        put("công trình", "Sự cố công trình hạ tầng");

        // ========== HẠ TẦNG KỸ THUẬT NGẦM ==========
        put("cáp ngầm", "Sự cố cáp ngầm");
        put("cáp quang", "Sự cố cáp quang");
        put("đường ống ngầm", "Sự cố đường ống ngầm");
        put("hố kỹ thuật", "Hư hỏng hố kỹ thuật");
        put("hố thăm", "Hư hỏng hố thăm");
        put("nắp hố thăm", "Mất nắp hố thăm nguy hiểm");
        put("nắp hầm", "Mất nắp hầm nguy hiểm");
        put("hố đào", "Hố đào nguy hiểm");

        // ========== TƯỜNG RÀO - HÀNG RÀO ==========
        put("tường rào nghiêng", "Tường rào nghiêng nguy hiểm");
        put("tường rào đổ", "Tường rào đổ nguy hiểm");
        put("tường rào nứt", "Nứt tường rào");
        put("hàng rào gãy", "Hàng rào bị gãy");
        put("hàng rào", "Hư hỏng hàng rào");
        put("tường rào", "Hư hỏng tường rào");

        // ========== BẬC THANG - LỐI ĐI ==========
        put("bậc thang vỡ", "Vỡ bậc thang công cộng");
        put("bậc thang trơn", "Bậc thang trơn trượt nguy hiểm");
        put("bậc thang", "Hư hỏng bậc thang công cộng");
        put("lối đi bộ", "Hư hỏng lối đi bộ");
        put("đường dành cho người đi bộ", "Hư hỏng đường đi bộ");
        put("lối đi", "Hư hỏng lối đi công cộng");
        put("cầu thang", "Hư hỏng cầu thang công cộng");

        // ========== TRANG THIẾT BỊ CÔNG CỘNG ==========
        put("ghế công viên", "Hư hỏng ghế công viên");
        put("thùng rác công cộng", "Hư hỏng thùng rác công cộng");
        put("nhà vệ sinh công cộng", "Hư hỏng nhà vệ sinh công cộng");
        put("vòi nước công cộng", "Hư hỏng vòi nước công cộng");
        put("bồn hoa", "Hư hỏng bồn hoa công cộng");
        put("ghế đá", "Hư hỏng ghế đá công cộng");
        put("trụ bơm nước", "Hư hỏng trụ bơm nước");
        put("nhà chờ xe buýt", "Hư hỏng nhà chờ xe buýt");
        put("mái che xe buýt", "Hư hỏng mái che nhà chờ");
    }};

    public String generateTitle(String noiDung, String diaDiem) {
        noiDung = noiDung.toLowerCase();
        String ruleTitle = matchKeyword(noiDung, diaDiem);

        if (ruleTitle != null) {
            return ruleTitle;
        }
        if (noiDung != null && !noiDung.isBlank()) {
            String[] words = noiDung.trim().split("\\s+");
            int limit = Math.min(8, words.length);
            StringBuilder sb = new StringBuilder("Sự cố: ");
            for (int i = 0; i < limit; i++) {
                sb.append(words[i]).append(" ");
            }
            if (words.length > 8) sb.append("...");
            if (diaDiem != null && !diaDiem.isBlank()) {
                sb.append("tại ").append(diaDiem);
            }
            return sb.toString().trim();
        }
        return "Sự cố hạ tầng đô thị";
    }

    private String matchKeyword(String noiDung, String diaDiem) {
        if (noiDung == null || noiDung.isBlank()) return null;

        String lower = noiDung.toLowerCase();
        String locationSuffix = (diaDiem != null && !diaDiem.isBlank())
                ? " tại " + diaDiem
                : "";

        for (Map.Entry<String, String> entry : KEYWORD_MAP.entrySet()) {
            if (lower.contains(entry.getKey())) {
                return entry.getValue() + locationSuffix;
            }
        }
        return null;
    }

}
