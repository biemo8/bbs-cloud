package com.biemo.cloud.bbs.utils.aliyun;

import lombok.Data;
import lombok.experimental.Accessors;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.Serializable;

/**
 *
 *
 * @author wujing
 */
@Data
@Accessors(chain = true)
@Component
public class Aliyun implements Serializable {

	private static final long serialVersionUID = 1L;

	private String polyvUseid;
	private String polyvWritetoken;
	private String polyvReadtoken;
	private String polyvSecretkey;
	@Value("${aliyun.oss.access-key-id}")
	private String aliyunAccessKeyId;
	@Value("${aliyun.oss.access-key-secret}")
	private String aliyunAccessKeySecret;
	@Value("${aliyun.oss.internet-file-url}")
	private String aliyunOssUrl;
	@Value("${aliyun.oss.bucket-name}")
	private String aliyunOssBucket;
	private String aliyunOasVault;
	private String payUrl;
	private String payKey;
	private String paySecret;
	private String notifyUrl;
	@Value("${aliyun.sms.sms-code}")
	private String smsCode;
	@Value("${aliyun.sms.sign-name}")
	private String signName;
}
