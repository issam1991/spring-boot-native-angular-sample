package com.example.demo;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("native")
public class NativeImageConfiguration {
    
    // This configuration class helps with native image compilation
    // by providing explicit configuration for GraalVM native image
    
}
