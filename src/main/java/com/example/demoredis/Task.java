package com.example.demoredis;

import lombok.Data;

/**
 *
 * @author daify
 * @date 2019-08-19
 **/
@Data
public class Task {

	private Long id;

	private String taskName;

	private String taskContext;

	private boolean finish;
}