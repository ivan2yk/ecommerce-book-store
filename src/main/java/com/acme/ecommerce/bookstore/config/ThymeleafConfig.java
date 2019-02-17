package com.acme.ecommerce.bookstore.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring5.SpringTemplateEngine;
import org.thymeleaf.templatemode.TemplateMode;
import org.thymeleaf.templateresolver.ClassLoaderTemplateResolver;
import org.thymeleaf.templateresolver.ITemplateResolver;

/**
 * Created by Ivan on 16/02/2019.
 */
@Configuration
public class ThymeleafConfig {

    private static final String EMAIL_TEMPLATE_ENCODING = "UTF-8";

    @Bean
    public TemplateEngine emailTemplateEngine() {
        SpringTemplateEngine springTemplateEngine = new SpringTemplateEngine();
        springTemplateEngine.addTemplateResolver(this.appHtmlTemplateResolver());
        springTemplateEngine.setTemplateEngineMessageSource(this.emailMessageSource());
        return springTemplateEngine;
    }

    @Bean
    public ResourceBundleMessageSource emailMessageSource() {
        ResourceBundleMessageSource resourceBundleMessageSource = new ResourceBundleMessageSource();
        resourceBundleMessageSource.setBasename("templates/mail/MailMessages");
        return resourceBundleMessageSource;
    }

    @Bean
    public ITemplateResolver appHtmlTemplateResolver() {
        ClassLoaderTemplateResolver templateResolver = new ClassLoaderTemplateResolver();
        templateResolver.setOrder(1);
        templateResolver.setPrefix("templates/");
        templateResolver.setCacheable(false);
        templateResolver.setSuffix(".html");
        templateResolver.setTemplateMode(TemplateMode.HTML);
        templateResolver.setCharacterEncoding(EMAIL_TEMPLATE_ENCODING);
        return templateResolver;
    }

}
