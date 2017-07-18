package br.unirio.dsw.crud.configuration;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.PropertySource;
import org.springframework.context.support.PropertySourcesPlaceholderConfigurer;
import org.springframework.context.support.ResourceBundleMessageSource;
 
@Configuration
@ComponentScan(basePackages = {"br.unirio.dsw.crud.service"})
@Import({SpringConfiguration.class, SecurityContext.class, SocialContext.class})
@PropertySource("classpath:configuration.properties")
public class ApplicationContext 
{ 
    @Bean
    public MessageSource messageSource() 
    {
        ResourceBundleMessageSource messageSource = new ResourceBundleMessageSource();
        messageSource.setBasename("i18n/messages");
        messageSource.setUseCodeAsDefaultMessage(true);
        return messageSource;
    }
 
    @Bean
    public PropertySourcesPlaceholderConfigurer propertyPlaceHolderConfigurer() 
    {
        return new PropertySourcesPlaceholderConfigurer();
    }
}