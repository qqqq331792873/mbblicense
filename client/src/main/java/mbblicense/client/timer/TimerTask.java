package mbblicense.client.timer;

import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;

/**
 * 定时器
 *
 * @author 马冰冰
 */
@Configuration
@EnableScheduling
public class TimerTask {
	/**
	 * 定时任务 每天零点执行一次
	 */
	@Scheduled(cron = "0 0 0 * * ?")
	private void timerTask() {
	}
}
