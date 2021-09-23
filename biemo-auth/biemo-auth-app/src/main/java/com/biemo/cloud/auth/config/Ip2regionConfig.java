package com.biemo.cloud.auth.config;

import com.biemo.cloud.auth.modular.sso.util.IpAddressService;
import org.lionsoul.ip2region.DbConfig;
import org.lionsoul.ip2region.DbMakerConfigException;
import org.lionsoul.ip2region.DbSearcher;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * ip配置
 *
 *
 * @Date 2019/12/4 21:07
 */
@Configuration
public class Ip2regionConfig {

    @Bean
    public DbSearcher dbSearcher() throws IOException, DbMakerConfigException {
        ClassPathResource resource = new ClassPathResource("ip2region.db");
        InputStream fis = resource.getInputStream();
        return new DbSearcher(new DbConfig(), read(fis));
    }

    @Bean
    public IpAddressService addressService(DbSearcher dbSearcher) {
        return new IpAddressService(dbSearcher);
    }

    public byte[] read(InputStream inputStream) throws IOException {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            byte[] buffer = new byte[1024];
            int num = inputStream.read(buffer);
            while (num != -1) {
                baos.write(buffer, 0, num);
                num = inputStream.read(buffer);
            }
            baos.flush();
            return baos.toByteArray();
        } finally {
            if (inputStream != null) {
                inputStream.close();
            }
        }
    }
}
