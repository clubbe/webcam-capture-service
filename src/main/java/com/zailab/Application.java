package com.zailab;

import java.awt.Dimension;
import java.util.List;
import java.util.concurrent.ScheduledThreadPoolExecutor;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

import com.github.sarxos.webcam.Webcam;

@SpringBootApplication
@EnableConfigurationProperties(WebcamSettings.class)
public class Application {

	private final static Logger logger = LoggerFactory.getLogger(Application.class);

	@Autowired
	private WebcamSettings settings;

	@Bean
	public ScheduledThreadPoolExecutor executor() {
		return new ScheduledThreadPoolExecutor(1);
	}

	@Bean
	public Webcam webcam() {

		logger.info("settings = {}", settings);

		Dimension res = new Dimension(settings.getWidth(), settings.getHeight());

		Webcam webcam = getWebcam();
		logger.info("webcam = {}", webcam);
		logger.info("webcam.getDevice().getResolutions() = {}", (Object) webcam.getDevice().getResolutions());

		if (webcam.getLock().isLocked()) {
			logger.warn("webcam is locked");
		}
		while (webcam.getLock().isLocked()) {
			// wait
		}
		logger.warn("webcam is ready");

		webcam.setCustomViewSizes(new Dimension[] { res });
		webcam.setViewSize(res);
		return webcam;
	}

	private Webcam getWebcam() {

		List<Webcam> webcams = Webcam.getWebcams();
		logger.info("webcams = {}", webcams);

		if (settings.getName() != null && "".equals(settings.getName()) == false) {

			for (Webcam webcam : webcams) {

				logger.info("webcam.getName() = {}", webcam.getName());

				if (webcam.getName().equals(settings.getName())) {
					return webcam;
				}
			}
		}

		logger.warn("Returning default because preferred webcam was not found for = {}", settings.getName());

		return Webcam.getDefault();
	}

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}

	@PostConstruct
	public void keepalive() {

		try {

			new Thread(new Runnable() {

				public void run() {

					try {

						logger.info("service started");
						while (true) {
							// wait
						}
					} catch (Throwable e) {

						logger.error("service crashed", e);
					} finally {

						logger.info("service stopped");
					}
				}
			}).start();

		} catch (Throwable e) {

			e.printStackTrace();
		}
	}
}
