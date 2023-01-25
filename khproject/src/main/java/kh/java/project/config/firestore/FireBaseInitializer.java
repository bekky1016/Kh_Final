package kh.java.project.config.firestore;

import com.google.auth.oauth2.GoogleCredentials;
import com.google.firebase.FirebaseApp;
import com.google.firebase.FirebaseOptions;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Slf4j
@Service
@PropertySource({"classpath:properties/firestore.properties"})
public class FireBaseInitializer {

    @Value("${fs.json}")
    private String firebaseConfigPath;

    @PostConstruct
    public void initialize() {
        try{
            FirebaseOptions options = FirebaseOptions.builder().setCredentials(
                    GoogleCredentials.fromStream(
                            new ClassPathResource(firebaseConfigPath).getInputStream())).build();
            if (FirebaseApp.getApps().isEmpty()) {
                FirebaseApp.initializeApp(options);
                log.info("Firebase initialized!!");
            }
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }
}
