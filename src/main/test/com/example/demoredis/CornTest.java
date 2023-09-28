package com.example.demoredis;

import cn.hutool.cron.CronUtil;
import cn.hutool.cron.task.Task;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
@Slf4j
public class CornTest {
	@Test
	void test() throws InterruptedException {
		String schedule = CronUtil.schedule("789","0/1 * * * * ?", new Task() {

			@Override
			public void execute() {
				log.info(1+"");
			}
		});
		String schedule1 = CronUtil.schedule("0/1 * * * * ?", new Task() {
			@Override
			public void execute() {
				log.info(2+""+schedule);
			}
		});
		CronUtil.setMatchSecond(true);
		CronUtil.start();
		Thread.sleep(30000);



	}
}
