package com.arkr.hexe;

import com.arkr.hene.data.config.DataConfig;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by hztanhuayou on 2017/8/27
 */
@Configuration
@Import(DataConfig.class)
public class ApplicationConfig {
}
