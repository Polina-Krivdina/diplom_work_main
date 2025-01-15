package Contacts;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.awt.*;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = "com.example.contacts")
public class AppConfig implements WebMvcConfigurer, AppConfig1 {

    @Override
    public void configureMessageConverters(List converters) {
        converters.add(new MappingJackson2HttpMessageConverter().toString());
    }
}