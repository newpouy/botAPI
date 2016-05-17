package kgs.bot;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.googleapis.auth.oauth2.GoogleCredential;
import com.google.api.client.http.HttpTransport;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.gdata.client.spreadsheet.SpreadsheetService;
import com.google.gdata.data.spreadsheet.SpreadsheetEntry;
import com.google.gdata.data.spreadsheet.SpreadsheetFeed;
import com.google.gdata.util.ServiceException;

public class GoogleSheetTest {
	private static final List<String> SCOPES = Arrays .asList("https://spreadsheets.google.com/feeds");
	// The name of the p12 file you created when obtaining the 
	public static void main(String[] args) throws MalformedURLException, IOException, ServiceException, GeneralSecurityException {
		ClassLoader classloader = Thread.currentThread().getContextClassLoader();
        //URL url = classloader.getResource("다운받은_키_파일.p12");
        //final File file = new File(url.getFile());
 
        HttpTransport HTTP_TRANSPORT = new NetHttpTransport();
        JsonFactory JSON_FACTORY = new JacksonFactory();
 
//        Credential credential = new GoogleCredential.Builder().setTransport(HTTP_TRANSPORT)
//                .setJsonFactory(JSON_FACTORY)
//                .setServiceAccountId("spacebot-1305@appspot.gserviceaccount.com") // 서비스 계정 생성 당시의 정보
//                .setTokenServerEncodedUrl("https://accounts.google.com/o/oauth2/token")
//                .setServiceAccountScopes(Arrays.asList("https://www.googleapis.com/auth/drive", "https://spreadsheets.google.com/feeds", "https://docs.google.com/feeds"))
//                .build();
                //.setServiceAccountPrivateKeyFromP12File(file).build();
        InputStream resourceAsStream = GoogleSheetTest.class.getClassLoader().getResourceAsStream("spaceBot-224968307243.json");
        GoogleCredential credential = GoogleCredential.fromStream(resourceAsStream).createScoped(SCOPES);
        
        // 버전이 v1부터 v3까지 있었는데 사실 그 차이에대해선 아직 알아보지못했다
        SpreadsheetService service = new SpreadsheetService("MySpreadsheetIntegration-v3");
        service.setOAuth2Credentials(credential); // 이거 하려고 위에 저렇게 코딩 한거다. 이게 인증의 핵심
        SpreadsheetFeed feed = service.getFeed(new URL("http://spreadsheets.google.com/feeds/spreadsheets/private/1ZqSaFQAjE5zs9zHqYBTnpxO468iZLvU3SJbG5RdaCvI"), SpreadsheetFeed.class);
 
        List<SpreadsheetEntry> spreadsheets = feed.getEntries();
        for (SpreadsheetEntry s : spreadsheets) {
            System.out.println(s.getTitle().getPlainText());
        }
	}
}
