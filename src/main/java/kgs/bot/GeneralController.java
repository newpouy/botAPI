package kgs.bot;

import java.util.concurrent.atomic.AtomicLong;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.json.simple.JSONObject;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class GeneralController {

    private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();

    @RequestMapping(value = "/keyboard", method = RequestMethod.GET, produces = "application/json; charset=utf-8")
    public JSONObject keyboard() {
    	JSONObject result = new JSONObject();
    	result.put("type", "text");
        return result; 
    }
    
    @RequestMapping(value = "/message", method = RequestMethod.POST, produces = "application/json; charset=utf-8")
    public JSONObject message(@RequestBody YIData data) {
    	System.out.println(data);
    	String req = data.getContent();
    	String res = req + " 반사";
    	String imgPath = "";
    	if(isReserveDateFormat(req)){
            String year = req.substring(0,4);
            String month = req.substring(4,6);
            String day = req.substring(6);
            res = year+"년 "+ month+"월 "+day+"일에 예약을 진행합니다.";
        } else {
            if (req.equals("안내")) {
                res = "공간예약을 안내해 드릴게요. \n안내 없이 바로 진행하시려면 '예약'이라고 입력해주세요.\n";
                res += "►목록: 어떤 공간이 있는지 가격과 함께 보여드려요.\n";
                res += "►룸1: 룸1의 예약을 진행해 드릴게요.\n";
                res += "►룸2: 룸2의 예약을 진행해 드릴게요.\n";
                res += "►160506: 2016년 5월 6일 예약하시게요? \n";
                res += "►1605061000 2.5 10: 2016년 5월 6일 10시부터 12시30분까지 2.5시간동안 10명이 쓰실 공간을 찾으세요?.\n";
                res += "►\n";
            } else if (req.equals("목록")) {
                res = "공간목록이에요.\n";
                res += "►룸1\n";
                res += "►룸2\n";
            } else if (req.equals("룸1")) {
                imgPath = "http://image.inews24.com/image_gisa/201207/1342744956519_2_094858.jpg";
            } else if (req.equals("아몰랑")) {
                res = "114";
            } else if (req.equals("예약")){
                res = "예약을 진행할게요.\n";
                res += "다음 형식으로 입력하면 가장 빨리 예약할 수 있어요.\n";
                res += "  일자  시작시간 인원 공간번호\n";
                res += "160506 1000 2.5 10 1  \n";
                // 공간예약상황조회 //구글스프레드시트 트랜잭션 체크
                // 리턴값으로 예약가부 메시지 분기
                // 공간예약 구글캘린더에 regist
                // 공간예약 구글시트에 insert
            }
        }
    	JSONObject jsonMessage = new JSONObject();
    	JSONObject jsonText = new JSONObject();
    	jsonText.put("text", res);
    	jsonMessage.put("message", jsonText);
    	if(!imgPath.equals("")){
	    	JSONObject jsonPhoto = new JSONObject();
	    	jsonPhoto.put("url", imgPath);
	    	jsonPhoto.put("width", 641);
	    	jsonPhoto.put("height", 480);
	    	jsonMessage.put("photo", jsonPhoto);
    	}
    	
        return jsonMessage;
    }
    
    @RequestMapping("/friend")
    public String friend(@RequestParam(value="name", defaultValue="World") String name) {
        return "";
    }
    
    public boolean isReserveDateFormat(String req){
    	boolean result = false;
    	String pattern = "(19|20)\\d{2}(0[1-9]|1[0-2])(0[1-9]|[12][0-9]|3[01])";
    	Pattern p = Pattern.compile(pattern);
    	Matcher m = p.matcher(req);
    	if (m.matches()) {
    		result = true;
    	}
    	return result;
    }
 
}
