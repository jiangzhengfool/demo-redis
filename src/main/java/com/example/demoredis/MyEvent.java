package com.example.demoredis;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEvent;

/**
 *
 * @author daify
 * @date 2019-08-19
 **/
@Slf4j
public class MyEvent extends ApplicationEvent {

	private Task task;

	public MyEvent(Task task) {
		super(task);
		this.task = task;
	}

	public Task getTask() {
		return task;
	}
}