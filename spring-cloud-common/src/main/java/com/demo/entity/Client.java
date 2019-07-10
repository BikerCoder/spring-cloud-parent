package com.demo.entity;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class Client {
	private String id;
	private String clientName;
	private String clientId;
	private String clientSecret;

}
