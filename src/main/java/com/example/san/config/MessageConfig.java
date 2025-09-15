package com.example.san.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.i18n.AcceptHeaderLocaleResolver;

import java.util.Arrays;
import java.util.Locale;

/**
 * پیکربندی بین‌المللی‌سازی (i18n)
 */
@Configuration
public class MessageConfig {

    /**
     * پیکربندی MessageSource برای خواندن فایل‌های properties
     */
    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        
        // مسیر فایل‌های properties
        messageSource.setBasename("classpath:messages");
        
        // تنظیم encoding برای پشتیبانی از فارسی
        messageSource.setDefaultEncoding("UTF-8");
        
        // کش کردن پیام‌ها برای عملکرد بهتر
        messageSource.setCacheSeconds(3600); // 1 ساعت
        
        // استفاده از پیام پیش‌فرض اگر کلید یافت نشد
        messageSource.setUseCodeAsDefaultMessage(true);
        
        // fallback به زبان پیش‌فرض اگر زبان درخواستی یافت نشد
        messageSource.setFallbackToSystemLocale(false);
        
        return messageSource;
    }

    /**
     * پیکربندی LocaleResolver برای تشخیص زبان
     */
    @Bean
    public LocaleResolver localeResolver() {
        AcceptHeaderLocaleResolver localeResolver = new AcceptHeaderLocaleResolver();
        
        // زبان‌های پشتیبانی شده
        localeResolver.setSupportedLocales(Arrays.asList(
            Locale.forLanguageTag("fa"), // فارسی
            Locale.forLanguageTag("en")  // انگلیسی
        ));
        
        // زبان پیش‌فرض
        localeResolver.setDefaultLocale(Locale.forLanguageTag("fa"));
        
        return localeResolver;
    }
}
