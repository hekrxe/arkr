package com.arkr.hene.data.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * Created by hztanhuayou on 2017/8/21
 */

@Configuration
@Import({MyBatisConfig.class, MyBatisMapperScannerConfig.class, DataCachingConfig.class})
public class DataConfig {
}
