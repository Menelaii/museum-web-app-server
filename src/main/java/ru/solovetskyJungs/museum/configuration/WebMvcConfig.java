package ru.solovetskyJungs.museum.configuration;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import ru.solovetskyJungs.museum.converters.ArtifactTypeConverter;
import ru.solovetskyJungs.museum.converters.ValueCategoryConverter;

import java.io.File;

@EnableWebMvc
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {
    @Value("${file.storage.directory}")
    private String storageDirectory;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry
                .addResourceHandler("/files/**")
                .addResourceLocations("file:///" + storageDirectory + File.separator);
    }

    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new ArtifactTypeConverter());
        registry.addConverter(new ValueCategoryConverter());
    }
}
