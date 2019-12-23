package com.mloine.msuser;

import com.mloine.msuser.rabbit.MySink;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.annotation.EnableBinding;
import org.springframework.cloud.stream.messaging.Sink;

@EnableBinding({Sink.class, MySink.class})
@SpringBootApplication
public class MsUserApplication {

    public static void main(String[] args) {
        SpringApplication.run(MsUserApplication.class, args);
    }

}
