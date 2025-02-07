package Utils;

import Controller.UserEndpoints;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

public class RestAPIUtils {

    @PostConstruct
    public static void initialization(Properties serverProps){
        try{
            serverProps.load(UserEndpoints.class.getResourceAsStream("/fileProp.properties"));
            System.out.println("Server properties set");
            serverProps.list(System.out);
        } catch (IOException ex){
            System.out.println("fileProp.properties not found: " + ex);
        }
    }

    public static String getJWT(String str){
        return Jwts.builder()
                .setSubject(str)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + 1000L * 60 * 60 * 24 * 365)) // 365 days
                .signWith(SignatureAlgorithm.HS256, "secretKey")
                .compact();
    }
}

