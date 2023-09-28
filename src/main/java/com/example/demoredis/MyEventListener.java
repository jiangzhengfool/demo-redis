package com.example.demoredis;


import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

import java.util.Objects;

import static org.apache.logging.log4j.message.MapMessage.MapFormat.JSON;

/**
 * @author daify
 * @date 2019-08-19
 **/
@Component
@Slf4j
public class MyEventListener implements ApplicationListener<MyEvent> {

	@Override
	public void onApplicationEvent(MyEvent myEvent) {
		if (Objects.isNull(myEvent)) {
			return;
		}
		Task task = myEvent.getTask();
		log.info("事件接收任务：{}", task);
		task.setFinish(true);
		log.info("此时完成任务");
	}
}